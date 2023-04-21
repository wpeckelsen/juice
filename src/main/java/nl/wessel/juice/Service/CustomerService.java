package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Customer.CustomerDto;
import nl.wessel.juice.DTO.Customer.PublicCustomerDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.RecordNotFound;
import nl.wessel.juice.Exception.UsernameNotFound;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Model.Customer;
import nl.wessel.juice.Model.Deal;
import nl.wessel.juice.Repository.BidRepository;
import nl.wessel.juice.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BidRepository bidRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BidRepository bidRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.bidRepository = bidRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static CustomerDto fromCustomer(Customer customer) {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setAuthorities(customer.getAuthorities());
        List<Bid> bids = customer.getBids();
        List<Long> bidIDs = new ArrayList<>();
        if (bids != null) {
            for (Bid bid : bids) {
                bidIDs.add(bid.getBidID());
            }
        }
        customerDto.setBidIDs(bidIDs);
        List<Deal> deals = customer.getDeals();
        List<Long> dealIDs = new ArrayList<>();
        if (deals != null) {
            for (Deal deal : deals) {
                dealIDs.add(deal.getDealID());
            }
        }
        customerDto.setDealIDs(bidIDs);
        return customerDto;
    }


    public Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        String encodedPassword = passwordEncoder.encode(customerDto.getPassword());
        customer.setPassword(encodedPassword);
        customer.setUsername(customerDto.getUsername());
//        customer.setPassword(customerDto.getPassword());

        return customer;

    }


    public List<String> getCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        if (customerList.isEmpty()) {
            Customer customer = new Customer();
            throw new RecordNotFound(customer);
        } else {
            List<String> customerIDs = new ArrayList<>();

            for (Customer customer : customerList) {
                CustomerDto customerDto = fromCustomer(customer);
                customerIDs.add(customerDto.getUsername());
            }

            return customerIDs;
        }
    }

    public CustomerDto getCustomer(String userName) {
        Optional<Customer> customer = customerRepository.findById(userName);
        if (customer.isPresent()) {
            CustomerDto dto;
            dto = fromCustomer(customer.get());
            return dto;
        } else {
            throw new UsernameNotFound(userName);
        }
    }

    public PublicCustomerDto getPublicCustomer(String customerName) {

        Optional<Customer> customer = customerRepository.findById(customerName);
        if (customer.isPresent()) {
            CustomerDto dto;
            dto = fromCustomer(customer.get());

            PublicCustomerDto publicCustomerDto = new PublicCustomerDto();
            publicCustomerDto.setBidIDs(dto.getBidIDs());
            publicCustomerDto.setUsername(dto.getUsername());
            publicCustomerDto.setAuthorities(dto.getAuthorities());
            publicCustomerDto.setDealIDs(dto.getDealIDs());
            return publicCustomerDto;
        } else {
            throw new UsernameNotFound(customerName);
        }
    }

    public String newCustomer(CustomerDto customerDto) {
        Customer customer = toCustomer(customerDto);
        customerRepository.save(customer);
        return customer.getUsername();
    }

    public Long newBid(CreateBidDto createBidDto, String username) {
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
            return BidService.bidDtoMaker(newBid).bidID;
        } else {
            throw new BadRequest("This Customer does not show up in the Database. Are you sure you made it?");
        }
    }

    public void delete(String customerName) {
        customerRepository.deleteById(customerName);
    }

    public void update(String customerName, CustomerDto customerDto) {
        if (!customerRepository.existsById(customerName)) throw new BadRequest();

        Optional<Customer> optionalCustomer = customerRepository.findById(customerName);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setUsername(customerDto.getUsername());
            customer.setPassword(customerDto.getPassword());
            customerRepository.save(customer);
        }
    }

    public void addAuthority(String customerName, String authority) {
        if (!customerRepository.existsById(customerName)) throw new UsernameNotFound(customerName);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerName);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.addAuthority(new Authority(customerName, authority));
            customerRepository.save(customer);
        }
    }

}
