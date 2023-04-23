package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Bid.CreatedBidDto;
import nl.wessel.juice.Exception.BadRequestException;
import nl.wessel.juice.Exception.ForbiddenException;
import nl.wessel.juice.Exception.RecordNotFoundException;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Model.Customer;
import nl.wessel.juice.Repository.BidRepository;
import nl.wessel.juice.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;


    @Autowired
    public BidService(BidRepository bidRepository, CustomerService customerService, CustomerRepository customerRepository) {
        this.bidRepository = bidRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    public static Bid bidMaker(CreateBidDto createBidDto) {
        Bid bid = new Bid();
        bid.setWords(createBidDto.getWords());
        bid.setDeadline(createBidDto.getDeadline());
        bid.setTopic(createBidDto.getTopic());
        bid.setAnchor(createBidDto.getAnchor());
        bid.setVernacular(createBidDto.getVernacular());
        return bid;
    }

    public static CreatedBidDto bidDtoMaker(Bid bid) {
        CreatedBidDto createdBidDTO = new CreatedBidDto();

        // gets the current time stamp
        ZonedDateTime rightNow = ZonedDateTime.now();


        createdBidDTO.setCreationTime(rightNow);
        createdBidDTO.setBidID(bid.getBidID());
        createdBidDTO.setWords(bid.getWords());
        createdBidDTO.setDeadline(bid.getDeadline());
        createdBidDTO.setTopic(bid.getTopic());
        createdBidDTO.setAnchor(bid.getAnchor());
        createdBidDTO.setVernacular(bid.getVernacular());
        createdBidDTO.setPrincipal(bid.getPrincipal());
        return createdBidDTO;
    }


    public CreatedBidDto newBid(CreateBidDto createBidDto) {
        // gets the current User's username
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Customer> foundCustomer = customerRepository.findById(currentPrincipalName);

        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            Bid newBid = bidMaker(createBidDto);
            List<Bid> currentBids = customer.getBids();
            currentBids.add(newBid);


            for (Bid bid : currentBids) {
                bid.setCustomer(customer);
                bid.setPrincipal(currentPrincipalName);
                bidRepository.save(newBid);
            }
            customer.setBids(currentBids);
            customerRepository.save(customer);
            return bidDtoMaker(newBid);
        } else {
            throw new BadRequestException("This Customer does not show up in the Database. Are you sure you made it?");
        }
    }

    public List<Long> getList() {

        List<Bid> bidList = bidRepository.findAll();

        if (bidList.isEmpty()) {
            Bid bid = new Bid();
            throw new RecordNotFoundException(bid);
        } else {
            List<Long> bidIDs = new ArrayList<>();

            for (Bid bid : bidList) {
                CreatedBidDto createdBidDTO = bidDtoMaker(bid);
                bidIDs.add(createdBidDTO.bidID);
            }
            return bidIDs;
        }
    }


    public CreatedBidDto getByID(Long bidID) {
        if (bidRepository.findById(bidID).isPresent()) {
            Bid bid = bidRepository.findById(bidID).get();
            return bidDtoMaker(bid);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFoundException(bid);
        }
    }

    public String bidPrincipal(Long bidID) {
        Bid bid = bidRepository.findById(bidID).get();
        CreatedBidDto createdBidDto = bidDtoMaker(bid);
        return createdBidDto.getPrincipal();
    }


    public CreatedBidDto update(Long bidID, CreateBidDto createBidDto) {
//        this string gets the name of the principal. Meaning: it gets the username of the
//        current user (the principal) that's logged in.
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        String bidPrincipal = bidPrincipal(bidID);

        if (bidRepository.findById(bidID).isPresent()) {
            Bid bid = bidRepository.findById(bidID).get();

            if (currentPrincipalName.equalsIgnoreCase(bidPrincipal)) {
                Bid bid1 = bidMaker(createBidDto);
                bid1.setBidID(bid.getBidID());
                bidRepository.save(bid1);
                return bidDtoMaker(bid1);
            } else {
//                if another user tries to update, it will return an exception because the principal name doesn't match
                throw new ForbiddenException();
            }

        } else {
            throw new RecordNotFoundException(new Bid());
        }
    }


    public void delete(Long bidID) {
        bidRepository.deleteById(bidID);
    }
}
