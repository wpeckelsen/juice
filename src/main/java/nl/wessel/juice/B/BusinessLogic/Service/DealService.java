package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
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
    private final PublisherRepo publisherRepo;


    @Autowired
    public DealService(DealRepo dealRepo, BidRepo bidRepo, DomainRepo domainRepo, CustomerRepo customerRepo, PublisherRepo publisherRepo) {
        this.dealRepo = dealRepo;
        this.bidRepo = bidRepo;
        this.domainRepo = domainRepo;
        this.customerRepo = customerRepo;
        this.publisherRepo = publisherRepo;
    }

    public CreatedDeal newDeal(CreateDeal createDeal, Long bidID, Long domainID, Long publisherID, String name) {


        if (customerRepo.findById(name).isPresent()
                && bidRepo.findById(bidID).isPresent()
                && publisherRepo.findById(publisherID).isPresent()
                && domainRepo.findById(domainID).isPresent()) {

            Customer customer = customerRepo.findById(name).get();
            Bid bid = bidRepo.findById(bidID).get();
            Publisher publisher = publisherRepo.findById(publisherID).get();
            Domain domain = domainRepo.findById(domainID).get();

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
            throw new BadRequest(" You must include an ID for an existing Customer, Bid, Publisher and Domain in your URL. " +
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
                CreatedDeal createdDeal = TransferService.dealDtoMaker(deal);
                createdDealList.add(createdDeal);
            }
            return createdDealList;
        }
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
