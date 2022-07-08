package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import nl.wessel.juice.C.Repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {

    private final DealRepo dealRepo;
    private final BidRepo bidRepo;
    private final BidService bidService;
    private final DomainRepo domainRepo;
    private final DomainService domainService;
    private final CustomerRepo customerRepo;

    private final PublisherRepo publisherRepo;


    public DealService(DealRepo dealRepo, BidRepo bidRepo, BidService bidService, DomainRepo domainRepo, DomainService domainService, CustomerRepo customerRepo, PublisherRepo publisherRepo) {
        this.dealRepo = dealRepo;
        this.bidRepo = bidRepo;
        this.bidService = bidService;
        this.domainRepo = domainRepo;
        this.domainService = domainService;
        this.customerRepo = customerRepo;

        this.publisherRepo = publisherRepo;

    }

    public CreatedDeal newDeal(CreateDeal createDeal, Long bidID, Long domainID, Long publisherID, String name) {

        var customer = customerRepo.findById(name).get();
        var bid = bidRepo.findById(bidID).get();

        var publisher = publisherRepo.findById(publisherID).get();
        var domain = domainRepo.findById(domainID).get();


        if (customer != null
                & bid != null
                & publisher != null
                & domain != null
        ) {

           Deal deal = TransferService.dealMaker(createDeal);

            deal.setCustomer(customer);
            deal.setBid(bid);

            deal.setPublisher(publisher);
            deal.setDomain(domain);

            domain.setDeal(deal);
            bid.setDeal(deal);

            dealRepo.save(deal);
            return TransferService.dealDtoMaker(deal);
        } else {
            throw new BadRequest(" A deal cannot be made unless a CustomerRepo, Bid, Publisher and Domain is present. " +
                    "Make these first before making a new Deal. ");
        }
    }


    //    READ
    public List<CreatedDeal> getList() {
        List<Deal> dealList = dealRepo.findAll();
        List<CreatedDeal> createdDealList = new ArrayList<>();

        for (Deal deal : dealList) {
            CreatedDeal createdDeal = TransferService.dealDtoMaker(deal);
            createdDealList.add(createdDeal);
        }
        return createdDealList;
    }


    public CreatedDeal getByID(Long dealID) {
        if (dealRepo.findById(dealID).isPresent()) {
            Deal deal = dealRepo.findById(dealID).get();
            return TransferService.dealDtoMaker(deal);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }


    //    update
    public CreatedDeal update(Long dealID, CreateDeal createDeal) {
        if (dealRepo.findById(dealID).isPresent()) {
            Deal deal = dealRepo.findById(dealID).get();
            Deal deal1 = TransferService.dealMaker(createDeal);


            deal1.setDealID(deal.getDealID());
            dealRepo.save(deal1);
            return TransferService.dealDtoMaker(deal1);
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
