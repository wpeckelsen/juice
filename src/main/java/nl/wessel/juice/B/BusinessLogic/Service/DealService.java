package nl.wessel.juice.B.BusinessLogic.Service;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Owner.OwnerDto;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.*;
import nl.wessel.juice.C.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {

    private final DealRepo dealRepo;
    private final BidRepo bidRepo;
    private final DomainRepo domainRepo;
    private final CustomerRepo customerRepo;
    private final OwnerRepo ownerRepo;


    @Autowired
    public DealService(DealRepo dealRepo, BidRepo bidRepo, DomainRepo domainRepo, CustomerRepo customerRepo, OwnerRepo ownerRepo) {
        this.dealRepo = dealRepo;
        this.bidRepo = bidRepo;
        this.domainRepo = domainRepo;
        this.customerRepo = customerRepo;
        this.ownerRepo = ownerRepo;
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
        var owner = deal.getOwner();

        if (domain != null
                && bid != null
                && customer != null
                && owner != null
        ) {
            CreatedDomain createdDomain = DomainService.domainDtoMaker(domain);
            createdDeal.setDomain(createdDomain);

            CreatedBid createdBid = BidService.bidDtoMaker(bid);
            createdDeal.setBid(createdBid);

            CustomerDto customerDto = CustomerService.fromCustomer(customer);
            createdDeal.setCustomer(customerDto);

            OwnerDto ownerDto = OwnerService.fromOwner(owner);
            createdDeal.setOwner(ownerDto);
        }


        return createdDeal;
    }

    //    CREATE
    public CreatedDeal newDeal(CreateDeal createDeal, Long bidID, Long domainID, String ownerName, String Customername) {


        if (customerRepo.findById(Customername).isPresent()
                && bidRepo.findById(bidID).isPresent()
                && ownerRepo.findById(Customername).isPresent()
                && domainRepo.findById(domainID).isPresent()) {

            Customer customer = customerRepo.findById(Customername).get();
            Bid bid = bidRepo.findById(bidID).get();
            Owner owner = ownerRepo.findById(ownerName).get();
            Domain domain = domainRepo.findById(domainID).get();

            Deal deal = dealMaker(createDeal);

            deal.setCustomer(customer);
            deal.setBid(bid);

            deal.setOwner(owner);
            deal.setDomain(domain);

            domain.setDeal(deal);
            bid.setDeal(deal);

            dealRepo.save(deal);
            return dealDtoMaker(deal);
        } else {
            throw new BadRequest(" You must include an ID for an existing Customer, Bid, Owner and Domain in your URL. " +
                    "Are you sure you are using the correct IDs? And are you sure all these entities exist already?");
        }
    }


    //    READ
    public List<CreatedDeal> getList() {
        List<Deal> dealList = dealRepo.findAll();

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
        if (dealRepo.findById(dealID).isPresent()) {
            Deal deal = dealRepo.findById(dealID).get();
            return dealDtoMaker(deal);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }


    //    update
    public CreatedDeal update(Long dealID, CreateDeal createDeal) {
        if (dealRepo.findById(dealID).isPresent()) {
            Deal deal = dealRepo.findById(dealID).get();
            Deal deal1 = dealMaker(createDeal);


            deal1.setDealID(deal.getDealID());
            dealRepo.save(deal1);
            return dealDtoMaker(deal1);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }

    //    delete
    public void deleteById(Long dealID) {
        dealRepo.deleteById(dealID);
    }
}
