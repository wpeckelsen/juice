package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Bid.CreatedBidDto;
import nl.wessel.juice.DTO.Customer.CreateCustomerDto;
import nl.wessel.juice.DTO.Customer.CreatedCustomerDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.UsernameNotFound;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Model.Customer;
import nl.wessel.juice.Repository.BidRepository;
import nl.wessel.juice.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BidRepository bidRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BidRepository bidRepository/*, @Lazy PasswordEncoder passwordEncoder*/) {
        this.customerRepository = customerRepository;
        this.bidRepository = bidRepository;
//        this.passwordEncoder = passwordEncoder;
    }



    public Customer customerMaker(CreateCustomerDto createCustomerDto){
//        String encodedPassword = passwordEncoder.encode(createCustomerDto.getPassword());
        Customer customer  = new Customer();
        customer.setUsername(createCustomerDto.getUsername());

        customer.setPassword(createCustomerDto.getPassword());
//        customer.setPassword(encodedPassword);
        return customer;
    }

    public static CreatedCustomerDto customerDtoMaker(Customer customer) {
        CreatedCustomerDto createdCustomerDto = new CreatedCustomerDto();
        createdCustomerDto.setUsername(customer.getUsername());
        createdCustomerDto.setPassword(customer.getPassword());
        createdCustomerDto.setPassword(customer.getPassword());

        List<Bid> bids = customer.getBids();
        List<Long> bidIDs = new ArrayList<>();
        if (bids != null) {
            for (Bid bid : bids) {
                bidIDs.add(bid.getBidID());
            }
        }
        createdCustomerDto.setBidIDs(bidIDs);
        return createdCustomerDto;
    }



    public CreatedBidDto newBid(CreateBidDto createBidDto, String username) {
        Optional<Customer> foundCustomer = customerRepository.findById(username);

        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            Bid newBid = BidService.bidMaker(createBidDto);
            List<Bid> currentBids = customer.getBids();
            currentBids.add(newBid);

            for (Bid bid : currentBids) {
                bid.setCustomer(customer);
                bidRepository.save(newBid);
            }
            customer.setBids(currentBids);
            customerRepository.save(customer);
            return BidService.bidDtoMaker(newBid);
        } else {
            throw new BadRequest("This Customer does not show up in the Database. Are you sure you made it?");
        }
    }


    public List<CreatedCustomerDto> getCustomers() {
        List<CreatedCustomerDto> collection = new ArrayList<>();
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list) {
            collection.add(customerDtoMaker(customer));
        }
        return collection;
    }

    public CreatedCustomerDto getCustomer(String customerName) {
        CreatedCustomerDto dto;
        Optional<Customer> customer = customerRepository.findById(customerName);
        if (customer.isPresent()) {
            dto = customerDtoMaker(customer.get());
        } else {
            throw new UsernameNotFound(customerName);
        }
        return dto;
    }

//    public boolean customerExists(String customerName) {
//        return customerRepository.existsById(customerName);
//    }

    public String createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = customerMaker(createCustomerDto);
        customerRepository.save(customer);
        return customerDtoMaker(customer).getUsername();
    }

    public void delete(String customerName) {
        customerRepository.deleteById(customerName);
    }

    public void update(String customerName, CreateCustomerDto createCustomerDto) {
        if (!customerRepository.existsById(customerName)) throw new BadRequest();

        Optional<Customer> optionalCustomer = customerRepository.findById(customerName);
        if(optionalCustomer.isPresent()){
            Customer currentCustomer = optionalCustomer.get();
            Customer updatedCustomer = customerMaker(createCustomerDto);
            updatedCustomer.setUsername(currentCustomer.getUsername());
            customerRepository.save(updatedCustomer);
        }
    }

    public Set<Authority> getAuthorities(String customerName) {
        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);
        Customer customer = customerRepository.findById(customerName).get();
        CreatedCustomerDto CreatedCustomerDto = customerDtoMaker(customer);
        return CreatedCustomerDto.getAuthorities();
    }

    public void addAuthority(String customerName, String authority) {
        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);

        Optional<Customer> optionalCustomer = customerRepository.findById(customerName);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customer.addAuthority(new Authority(customerName, authority));
            customerRepository.save(customer);
        }
    }

//    public void removeAuthority(String customerName, String authority) {
//        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);
//        Customer customer = customerRepository.findById(customerName).get();
//        Authority authorityToRemove = customer.getAuthorities().stream().filter((a)
//                -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
//        customer.removeAuthority(authorityToRemove);
//        customerRepository.save(customer);
//    }


}
