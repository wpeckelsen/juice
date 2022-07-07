package nl.wessel.juice.B.BusinessLogic.Service;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.UsernameNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Authority;
import nl.wessel.juice.B.BusinessLogic.Model.Bid;
import nl.wessel.juice.B.BusinessLogic.Model.Customer;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Security.Utils.RandomStringGenerator;
import nl.wessel.juice.C.Repository.BidRepo;
import nl.wessel.juice.C.Repository.CustomerRepo;
import nl.wessel.juice.C.Repository.DomainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final BidRepo bidRepo;
    private final DomainRepo domainRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, BidRepo bidRepo, DomainRepo domainRepo) {
        this.customerRepo = customerRepo;
        this.bidRepo = bidRepo;
        this.domainRepo = domainRepo;
    }

    public List<CustomerDto> getCustomers() {
        List<CustomerDto> collection = new ArrayList<>();
        List<Customer> list = customerRepo.findAll();
        for (Customer customer : list) {
            collection.add(fromCustomer(customer));
        }
        return collection;
    }

    public CustomerDto getCustomer(String customerName) {
        CustomerDto dto = new CustomerDto();
        var customer = customerRepo.findById(customerName);
        if (customer.isPresent()) {
            dto = fromCustomer(customer.get());
        } else {
            throw new UsernameNotFound(customerName);
        }
        return dto;
    }

    public boolean CustomerExists(String customerName) {
        return customerRepo.existsById(customerName);
    }

    public String createCustomer(CustomerDto CustomerDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        CustomerDto.setApikey(randomString);
        nl.wessel.juice.B.BusinessLogic.Model.Customer newCustomer = customerRepo.save(toCustomer(CustomerDto));
        return newCustomer.getUsername();
    }

    public void deleteCustomer(String customerName) {
        customerRepo.deleteById(customerName);
    }

    public void updateCustomer(String customerName, CustomerDto newCustomer) {
        if (!customerRepo.existsById(customerName)) throw new BadRequest();
        nl.wessel.juice.B.BusinessLogic.Model.Customer Customer = customerRepo.findById(customerName).get();
        Customer.setPassword(newCustomer.getPassword());
        customerRepo.save(Customer);
    }

    public Set<Authority> getAuthorities(String customerName) {
        if (!customerRepo.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepo.findById(customerName).get();
        CustomerDto CustomerDto = fromCustomer(customer);
        return CustomerDto.getAuthorities();
    }

    public void addAuthority(String customerName, String authority) {

        if (!customerRepo.existsById(customerName)) throw new UsernameNotFound(customerName);
        nl.wessel.juice.B.BusinessLogic.Model.Customer Customer = customerRepo.findById(customerName).get();
        Customer.addAuthority(new Authority(customerName, authority));
        customerRepo.save(Customer);
    }

    public void removeAuthority(String customerName, String authority) {
        if (!customerRepo.existsById(customerName)) throw new UsernameNotFound(customerName);
        nl.wessel.juice.B.BusinessLogic.Model.Customer Customer = customerRepo.findById(customerName).get();
        Authority authorityToRemove = Customer.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        Customer.removeAuthority(authorityToRemove);
        customerRepo.save(Customer);
    }


    public static CustomerDto fromCustomer(Customer customer) {

        var dto = new CustomerDto();
        dto.username = customer.getUsername();
        dto.password = customer.getPassword();
        dto.enabled = customer.isEnabled();
        dto.apikey = customer.getApikey();
        dto.email = customer.getEmail();
        dto.authorities = customer.getAuthorities();


        List<Bid> bids = customer.getBids();
        List<CreatedBid> createdBids = new ArrayList<>();

        if(bids != null){
            for(Bid bid : bids){
                CreatedBid createdBid = BidService.bidDtoMaker(bid);
                createdBids.add(createdBid);
            }
        }

        List<Domain> domains = customer.getDomains();
        List<CreatedDomain> createdDomains = new ArrayList<>();

        if(domains != null){
            for (Domain domain : domains){
                CreatedDomain createdDomain = DomainService.domainDtoMaker(domain);
                createdDomains.add(createdDomain);
            }
        }

        dto.setBids(createdBids);
        dto.setDomains(createdDomains);
        return dto;
    }

    public Customer toCustomer(CustomerDto customerDto) {


        var customer = new Customer();
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setEnabled(customerDto.getEnabled());
        customer.setApikey(customerDto.getApikey());
        customer.setEmail(customerDto.getEmail());

        return customer;
}





    public CustomerDto assignBids(Long idBid, String username) {

        nl.wessel.juice.B.BusinessLogic.Model.Customer customer = customerRepo.findById(username).get();
        Bid newBid = bidRepo.findById(idBid).get();


        List<Bid> currentBids = customer.getBids();
        currentBids.add(newBid);

        for (Bid bid : currentBids) {
            bid.setCustomer(customer);
            bidRepo.save(bid);
        }

        customer.setBids(currentBids);
        customerRepo.save(customer);
        return fromCustomer(customer);
    }

    public CustomerDto assignDomains(Long domainID, String username){
        nl.wessel.juice.B.BusinessLogic.Model.Customer customer = customerRepo.findById(username).get();
        Domain newDomain = domainRepo.findById(domainID).get();

        List<Domain> currentDomains = customer.getDomains();
        currentDomains.add(newDomain);

        for (Domain domain : currentDomains){
            domain.setCustomer(customer);
            domainRepo.save(domain);
        }

        customer.setDomains(currentDomains);
        customerRepo.save(customer);
        return fromCustomer(customer);
    }
}
