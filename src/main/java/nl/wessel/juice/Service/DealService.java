package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Deal.CreateDealDto;
import nl.wessel.juice.DTO.Deal.CreatedDealDto;
import nl.wessel.juice.Exception.BadRequestException;
import nl.wessel.juice.Exception.ForbiddenException;
import nl.wessel.juice.Exception.RecordNotFoundException;
import nl.wessel.juice.Model.*;
import nl.wessel.juice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {

    private final DealRepository dealRepository;
    private final BidRepository bidRepository;
    private final DomainRepository domainRepository;
    private final CustomerRepository customerRepository;
    private final PublisherRepository publisherRepository;


    @Autowired
    public DealService(DealRepository dealRepository, BidRepository bidRepository, DomainRepository domainRepository, CustomerRepository customerRepository, PublisherRepository publisherRepository) {
        this.dealRepository = dealRepository;
        this.bidRepository = bidRepository;
        this.domainRepository = domainRepository;
        this.customerRepository = customerRepository;
        this.publisherRepository = publisherRepository;
    }

    public static Deal dealMaker(CreateDealDto createDealDto) {
        Deal deal = new Deal();
        deal.setDeadline(createDealDto.getDeadline());
        deal.setPayment(createDealDto.getPayment());
        deal.setTerms(createDealDto.getTerms());
        deal.setPrice(createDealDto.getPrice());
        return deal;
    }

    public CreatedDealDto dealDtoMaker(Deal deal) {
        CreatedDealDto createdDealDTO = new CreatedDealDto();
        createdDealDTO.setDealID(deal.getDealID());
        createdDealDTO.setDeadline(deal.getDeadline());
        createdDealDTO.setPrice(deal.getPrice());
        createdDealDTO.setPayment(deal.getPayment());
        createdDealDTO.setTerms(deal.getTerms());
        createdDealDTO.setDone(deal.isDone());
        createdDealDTO.setPrincipal(deal.getPrincipal());
        Domain domain = deal.getDomain();
        Bid bid = deal.getBid();
        Customer customer = deal.getCustomer();
        Publisher publisher = deal.getPublisher();

        if (domain != null
                && bid != null
                && customer != null
                && publisher != null
        ) {
//            Sets the IDs of all relations of a deal to a created deal
            Long domainID = DomainService.domainDtoMaker(domain).getDomainID();
            Long createdBidDTO = BidService.bidDtoMaker(bid).getBidID();
            String customerDto = CustomerService.fromCustomer(customer).getUsername();
            String publisherDto = PublisherService.fromPublisher(publisher).getUsername();
            createdDealDTO.setDomainID(domainID);
            createdDealDTO.setBidID(createdBidDTO);
            createdDealDTO.setCustomerID(customerDto);
            createdDealDTO.setPublisherID(publisherDto);
        }
        return createdDealDTO;
    }


    public CreatedDealDto newDeal(CreateDealDto createDealDto,
                        Long bidID,
                        Long domainID,
                        String publisherName) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (domainRepository.findById(domainID).isPresent()
                && customerRepository.findById(currentPrincipalName).isPresent()
                && bidRepository.findById(bidID).isPresent()
                && publisherRepository.findById(publisherName).isPresent()
        ) {
//            all relations are set for a deal here
            Customer customer = customerRepository.findById(currentPrincipalName).get();
            Bid bid = bidRepository.findById(bidID).get();
            Publisher publisher = publisherRepository.findById(publisherName).get();
            Domain domain = domainRepository.findById(domainID).get();
            Deal deal = dealMaker(createDealDto);

            deal.setCustomer(customer);
            deal.setBid(bid);
            deal.setPublisher(publisher);
            deal.setDomain(domain);
            deal.setPrincipal(currentPrincipalName);
            domain.setDeal(deal);
            bid.setDeal(deal);
            dealRepository.save(deal);
            return dealDtoMaker(deal);
        } else {
            throw new BadRequestException(" You must include an ID for an existing Customer, Bid, Publisher and Domain in your URL. " +
                    "Are you sure you are using the correct IDs? And are you sure all these entities exist already?");
        }
    }

    public String dealPrincipal(Long dealID) {
        Deal deal = dealRepository.findById(dealID).get();
        CreatedDealDto createdDealDto = dealDtoMaker(deal);
        return createdDealDto.getPrincipal();
    }


    public void doneDeal(Long dealID) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        String dealPrincipal = dealPrincipal(dealID);

        if (currentPrincipalName.equalsIgnoreCase(dealPrincipal)) {
            Deal deal = dealRepository.findById(dealID).get();
//            Deal updatedDeal = dealMaker(createDealDto);
//            deal.setDealID(deal.getDealID());
            deal.setDone(true);
            dealRepository.save(deal);
            dealDtoMaker(deal);
        } else{
            throw new ForbiddenException();
        }
    }

    public List<Long> getList() {
        List<Deal> dealList = dealRepository.findAll();

        if (dealList.isEmpty()) {
            Deal deal = new Deal();
            throw new RecordNotFoundException(deal);
        } else {
            List<Long> dealIDs = new ArrayList<>();
            for (Deal deal : dealList) {
                CreatedDealDto createdDealDTO = dealDtoMaker(deal);
                dealIDs.add(createdDealDTO.getDealID());
            }
            return dealIDs;
        }
    }


    public CreatedDealDto getByID(Long dealID) {
        if (dealRepository.findById(dealID).isPresent()) {
            Deal deal = dealRepository.findById(dealID).get();
            return dealDtoMaker(deal);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFoundException(deal);
        }
    }

    public CreatedDealDto update(Long dealID, CreateDealDto createDealDto) {
        if (dealRepository.findById(dealID).isPresent()) {
            Deal deal = dealRepository.findById(dealID).get();
            Deal deal1 = dealMaker(createDealDto);


            deal1.setDealID(deal.getDealID());
            dealRepository.save(deal1);
            return dealDtoMaker(deal1);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFoundException(deal);
        }
    }

    public void deleteById(Long dealID) {
        dealRepository.deleteById(dealID);
    }
}
