package nl.wessel.juice.B.BusinessLogic.Service;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.UsernameNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Authority;
import nl.wessel.juice.B.BusinessLogic.Model.Bid;
import nl.wessel.juice.B.BusinessLogic.Model.Customer;
import nl.wessel.juice.B.BusinessLogic.Security.Utils.RandomStringGenerator;
import nl.wessel.juice.C.Repository.BidRepo;
import nl.wessel.juice.C.Repository.CustomerRepo;
import nl.wessel.juice.C.Repository.DealRepo;
import nl.wessel.juice.C.Repository.DomainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static nl.wessel.juice.B.BusinessLogic.Service.BidService.bidMaker;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final BidRepo bidRepo;
    private final DomainRepo domainRepo;
    private final DealRepo dealRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, BidRepo bidRepo, DomainRepo domainRepo, DealRepo dealRepo) {
        this.customerRepo = customerRepo;
        this.bidRepo = bidRepo;
        this.domainRepo = domainRepo;
        this.dealRepo = dealRepo;
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

    public boolean customerExists(String customerName) {
        return customerRepo.existsById(customerName);
    }

    public String createCustomer(CustomerDto customerDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        customerDto.setApikey(randomString);
        Customer newCustomer = customerRepo.save(toCustomer(customerDto));
        return newCustomer.getUsername();
    }

    public void deleteCustomer(String customerName) {
        customerRepo.deleteById(customerName);
    }

    public void updateCustomer(String customerName, CustomerDto newCustomer) {
        if (!customerRepo.existsById(customerName)) throw new BadRequest();
        Customer customer = customerRepo.findById(customerName).get();
        customer.setPassword(newCustomer.getPassword());
        customerRepo.save(customer);
    }

    public Set<Authority> getAuthorities(String customerName) {
        if (!customerRepo.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepo.findById(customerName).get();
        CustomerDto CustomerDto = fromCustomer(customer);
        return CustomerDto.getAuthorities();
    }

    public void addAuthority(String customerName, String authority) {

        if (!customerRepo.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepo.findById(customerName).get();
        customer.addAuthority(new Authority(customerName, authority));
        customerRepo.save(customer);
    }

    public void removeAuthority(String customerName, String authority) {
        if (!customerRepo.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepo.findById(customerName).get();
        Authority authorityToRemove = customer.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        customer.removeAuthority(authorityToRemove);
        customerRepo.save(customer);
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

        if (bids != null) {
            for (Bid bid : bids) {
                CreatedBid createdBid = BidService.bidDtoMaker(bid);
                createdBids.add(createdBid);
            }
        }


        dto.setBids(createdBids);
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


    public CustomerDto newBid(CreateBid createBid, String username) {

        var optCustom = customerRepo.findById(username);


        if (optCustom.isPresent()) {
            Customer customer = optCustom.get();
            Bid newBid = bidMaker(createBid);

            List<Bid> currentBids = customer.getBids();
            currentBids.add(newBid);

            for (Bid bid : currentBids) {
                bid.setCustomer(customer);
                bidRepo.save(bid);
            }

            customer.setBids(currentBids);
            customerRepo.save(customer);
            return fromCustomer(customer);

        } else {

            throw new BadRequest("A bid cannot be made without a Customer. Make a Customer first");
        }
    }


}
