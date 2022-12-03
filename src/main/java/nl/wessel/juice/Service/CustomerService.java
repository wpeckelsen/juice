package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBid;
import nl.wessel.juice.DTO.Bid.CreatedBid;
import nl.wessel.juice.DTO.Customer.CustomerDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.UsernameNotFound;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Model.Customer;
import nl.wessel.juice.Security.Utils.RandomStringGenerator;
import nl.wessel.juice.Repository.BidRepository;
import nl.wessel.juice.Repository.CustomerRepository;
import nl.wessel.juice.Repository.DealRepository;
import nl.wessel.juice.Repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BidRepository bidRepository;
    private final DomainRepository domainRepository;
    private final DealRepository dealRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BidRepository bidRepository, DomainRepository domainRepository, DealRepository dealRepository) {
        this.customerRepository = customerRepository;
        this.bidRepository = bidRepository;
        this.domainRepository = domainRepository;
        this.dealRepository = dealRepository;
    }


    public List<CustomerDto> getCustomers() {
        List<CustomerDto> collection = new ArrayList<>();
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list) {
            collection.add(fromCustomer(customer));
        }
        return collection;
    }

    public CustomerDto getCustomer(String customerName) {
        CustomerDto dto = new CustomerDto();
        var customer = customerRepository.findById(customerName);
        if (customer.isPresent()) {
            dto = fromCustomer(customer.get());
        } else {
            throw new UsernameNotFound(customerName);
        }
        return dto;
    }

    public boolean customerExists(String customerName) {
        return customerRepository.existsById(customerName);
    }

    public String createCustomer(CustomerDto customerDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        customerDto.setApikey(randomString);
        Customer newCustomer = customerRepository.save(toCustomer(customerDto));
        return newCustomer.getUsername();
    }

    public void deleteCustomer(String customerName) {
        customerRepository.deleteById(customerName);
    }

    public void updateCustomer(String customerName, CustomerDto newCustomer) {
        if (!customerRepository.existsById(customerName)) throw new BadRequest();
        Customer customer = customerRepository.findById(customerName).get();
        customer.setPassword(newCustomer.getPassword());
        customer.setUsername(newCustomer.getUsername());
        customerRepository.save(customer);
    }

    public Set<Authority> getAuthorities(String customerName) {
        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepository.findById(customerName).get();
        CustomerDto CustomerDto = fromCustomer(customer);
        return CustomerDto.getAuthorities();
    }

    public void addAuthority(String customerName, String authority) {

        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepository.findById(customerName).get();
        customer.addAuthority(new Authority(customerName, authority));
        customerRepository.save(customer);
    }

    public void removeAuthority(String customerName, String authority) {
        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepository.findById(customerName).get();
        Authority authorityToRemove = customer.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        customer.removeAuthority(authorityToRemove);
        customerRepository.save(customer);
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

        var optCustom = customerRepository.findById(username);


        if (optCustom.isPresent()) {
            Customer customer = optCustom.get();
            Bid newBid = BidService.bidMaker(createBid);

            List<Bid> currentBids = customer.getBids();
            currentBids.add(newBid);

            for (Bid bid : currentBids) {
                bid.setCustomer(customer);
                bidRepository.save(bid);
            }

            customer.setBids(currentBids);
            customerRepository.save(customer);
            return fromCustomer(customer);

        } else {

            throw new BadRequest("This Customer does not show up in the Database. Are you sure you made it?");
        }
    }


}
