package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreatedBid;
import nl.wessel.juice.DTO.Customer.CustomerDto;
import nl.wessel.juice.DTO.Deal.CreateDeal;
import nl.wessel.juice.DTO.Deal.CreatedDeal;
import nl.wessel.juice.DTO.Domain.CreatedDomain;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.RecordNotFound;
import nl.wessel.juice.Model.*;
import nl.wessel.juice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static Deal dealMaker(CreateDeal createDeal) {
        Deal deal = new Deal();
        deal.setDeadline(createDeal.getDeadline());
        deal.setPaymentType(createDeal.getPaymentType());
        deal.setTerms(createDeal.getTerms());
        deal.setPrice(createDeal.getPrice());
        return deal;
    }

    public static CreatedDeal dealDtoMaker(Deal deal) {
        CreatedDeal createdDeal = new CreatedDeal();
        createdDeal.setDealID(deal.getDealID());
        createdDeal.setDeadline(deal.getDeadline());
        createdDeal.setPrice(deal.getPrice());
        createdDeal.setPaymentType(deal.getPaymentType());
        createdDeal.setTerms(deal.getTerms());

        var domain = deal.getDomain();
        var bid = deal.getBid();
        var customer = deal.getCustomer();
        var publisher = deal.getPublisher();

        if (domain != null
                && bid != null
                && customer != null
                && publisher != null
        ) {
            CreatedDomain createdDomain = DomainService.domainDtoMaker(domain);
            createdDeal.setDomain(createdDomain);

            CreatedBid createdBid = BidService.bidDtoMaker(bid);
            createdDeal.setBid(createdBid);

            CustomerDto customerDto = CustomerService.fromCustomer(customer);
            createdDeal.setCustomer(customerDto);

            PublisherDto publisherDto = PublisherService.fromPublisher(publisher);
            createdDeal.setPublisher(publisherDto);
        }


        return createdDeal;
    }

    //    CREATE
    public CreatedDeal newDeal(CreateDeal createDeal, Long bidID, Long domainID, String publisherName, String Customername) {
        if (
                domainRepository.findById(domainID).isPresent()
                        && customerRepository.findById(Customername).isPresent()
                        && bidRepository.findById(bidID).isPresent()
                        && publisherRepository.findById(publisherName).isPresent()
        ) {
            Customer customer = customerRepository.findById(Customername).get();
            Bid bid = bidRepository.findById(bidID).get();
            Publisher publisher = publisherRepository.findById(publisherName).get();
            Domain domain = domainRepository.findById(domainID).get();

            Deal deal = dealMaker(createDeal);

            deal.setCustomer(customer);
            deal.setBid(bid);

            deal.setPublisher(publisher);
            deal.setDomain(domain);

            domain.setDeal(deal);
            bid.setDeal(deal);

            dealRepository.save(deal);
            return dealDtoMaker(deal);
        } else {
            throw new BadRequest(" You must include an ID for an existing Customer, Bid, Publisher and Domain in your URL. " +
                    "Are you sure you are using the correct IDs? And are you sure all these entities exist already?");
        }
    }


    //    READ
    public List<CreatedDeal> getList() {
        List<Deal> dealList = dealRepository.findAll();

        if (dealList.isEmpty()) {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        } else {
            List<CreatedDeal> createdDealList = new ArrayList<>();

            for (Deal deal : dealList) {
                CreatedDeal createdDeal = dealDtoMaker(deal);
                createdDealList.add(createdDeal);
            }
            return createdDealList;
        }
    }


    public CreatedDeal getByID(Long dealID) {
        if (dealRepository.findById(dealID).isPresent()) {
            Deal deal = dealRepository.findById(dealID).get();
            return dealDtoMaker(deal);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }


    //    update
    public CreatedDeal update(Long dealID, CreateDeal createDeal) {
        if (dealRepository.findById(dealID).isPresent()) {
            Deal deal = dealRepository.findById(dealID).get();
            Deal deal1 = dealMaker(createDeal);


            deal1.setDealID(deal.getDealID());
            dealRepository.save(deal1);
            return dealDtoMaker(deal1);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }

    //    delete
    public void deleteById(Long dealID) {
        dealRepository.deleteById(dealID);
    }
}
