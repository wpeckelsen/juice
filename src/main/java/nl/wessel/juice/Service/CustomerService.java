package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Customer.CreatedCustomerDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BidRepository bidRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BidRepository bidRepository) {
        this.customerRepository = customerRepository;
        this.bidRepository = bidRepository;
    }

//    fromCustomer
//       public static CustomerDto fromCustomer(Customer customer) {
//
//        var dto = new CustomerDto();
//        dto.username = customer.getUsername();
    public static CreatedCustomerDto fromCustomer(Customer customer) {
        CreatedCustomerDto createdCustomerDto = new CreatedCustomerDto();
        createdCustomerDto.setUsername(customer.getUsername());
        createdCustomerDto.setPassword(customer.getPassword());
        createdCustomerDto.setAuthorities(customer.getAuthorities());

        List<Bid> bids = customer.getBids();
        List<Long> bidIDs = new ArrayList<>();
        if (bids != null) {
            for (Bid bid : bids) {
                bidIDs.add(bid.getBidID());
            }
        }
        createdCustomerDto.setBidIDs(bidIDs);

        List<Deal> deals = customer.getDeals();
        List<Long> dealIDs = new ArrayList<>();
        if (deals != null) {
            for (Deal deal : deals) {
                dealIDs.add(deal.getDealID());
            }
        }
        createdCustomerDto.setDealIDs(bidIDs);

        return createdCustomerDto;
    }

//    tocustomer
//    public Customer customerMaker(CreateCustomerDto createCustomerDto) {
//        Customer customer = new Customer();
//        customer.setUsername(createCustomerDto.getUsername());
//        String encodedPassword = passwordEncoder.encode(createCustomerDto.getPassword());
//        customer.setPassword(encodedPassword);
//        return customer;
//    }

    public Customer toCustomer(CreatedCustomerDto createdCustomerDto){
        Customer customer = new Customer();
        customer.setUsername(createdCustomerDto.getUsername());
        customer.setPassword(createdCustomerDto.getPassword());
//        customer.setAuthorities(createdCustomerDto.getAuthorities());
        return customer;
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


    public List<String> getCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        if (customerList.isEmpty()) {
            Customer customer = new Customer();
            throw new RecordNotFound(customer);
        } else {
            List<String> customerIDs = new ArrayList<>();

            for (Customer customer : customerList) {
                CreatedCustomerDto createdCustomerDto = fromCustomer(customer);
                customerIDs.add(createdCustomerDto.getUsername());
            }

            return customerIDs;
        }
    }

    public CreatedCustomerDto getCustomer(String customerName) {

        Optional<Customer> customer = customerRepository.findById(customerName);
        if (customer.isPresent()) {
            CreatedCustomerDto dto;
            dto = fromCustomer(customer.get());
            return dto;
        } else {
            throw new UsernameNotFound(customerName);
        }
    }

    public PublicCustomerDto getPublicCustomer(String customerName) {

        Optional<Customer> customer = customerRepository.findById(customerName);
        if (customer.isPresent()) {
            CreatedCustomerDto dto;
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

//create
//    public String createCustomer(CreateCustomerDto createCustomerDto) {
//        Customer customer = toCustomer(createCustomerDto);
//        customerRepository.save(customer);
//        return fromCustomer(customer).getUsername();
//    }

//    createdcustomer
public String newCustomer(CreatedCustomerDto createdCustomerDto) {
    Customer customer = toCustomer(createdCustomerDto);
    customerRepository.save(customer);
    return customer.getUsername();
    }


//        public String createCustomer(CustomerDto customerDto) {
//        Customer newCustomer = customerRepository.save(toCustomer(customerDto));
//        return newCustomer.getUsername();
//    }


    public void delete(String customerName) {
        customerRepository.deleteById(customerName);
    }

//    old
//    public void update(String customerName, CreateCustomerDto createCustomerDto) {
//        if (!customerRepository.existsById(customerName)) throw new BadRequest();
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerName);
//        if (optionalCustomer.isPresent()) {
//            Customer currentCustomer = optionalCustomer.get();
//
//            Customer updatedCustomer = toCustomer(createCustomerDto);
//            updatedCustomer.setUsername(currentCustomer.getUsername());
//            updatedCustomer.setPassword(currentCustomer.getPassword());
//            customerRepository.save(updatedCustomer);
//        }
//    }

    public void update(String customerName, CreatedCustomerDto createdCustomerDto) {
        if (!customerRepository.existsById(customerName)) throw new BadRequest();

        Optional<Customer> optionalCustomer = customerRepository.findById(customerName);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setUsername(createdCustomerDto.getUsername());
            customer.setPassword(createdCustomerDto.getPassword());
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
