package nl.wessel.juice.B.BusinessLogic.Service;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import nl.wessel.juice.C.Repository.DealRepo;
import nl.wessel.juice.C.Repository.DomainRepo;
import nl.wessel.juice.C.Repository.PublisherRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepo publisherRepo;
    private final DomainRepo domainRepo;
    private final DomainService domainService;
    private final DealService dealService;
    private final DealRepo dealRepo;


    public PublisherService(PublisherRepo publisherRepo, DomainRepo domainRepo, DomainService domainService, DealService dealService, DealRepo dealRepo) {
        this.publisherRepo = publisherRepo;
        this.domainRepo = domainRepo;
        this.domainService = domainService;
        this.dealService = dealService;
        this.dealRepo = dealRepo;
    }


    //    create
    public CreatedPublisher newPublisher(CreatePublisher createPublisher) {
        Publisher publisher = TransferService.publisherMaker(createPublisher);
        publisherRepo.save(publisher);
        return TransferService.publisherDtoMaker(publisher);
    }


//    read

    public List<CreatedPublisher> getList() {
        List<Publisher> publisherList = publisherRepo.findAll();
        List<CreatedPublisher> createdPublisherList = new ArrayList<>();

        for (Publisher publisher : publisherList) {
            CreatedPublisher createdPublisher = TransferService.publisherDtoMaker(publisher);
            createdPublisherList.add(createdPublisher);
        }
        return createdPublisherList;
    }

    public List<CreatedPublisher> getListByName(String name) {
        List<Publisher> publisherList = publisherRepo.findPublishersByName(name);
        List<CreatedPublisher> createdPublisherList = new ArrayList<>();

        for (Publisher publisher : publisherList) {
            CreatedPublisher createdPublisher = TransferService.publisherDtoMaker(publisher);
            createdPublisherList.add(createdPublisher);
        }
        return createdPublisherList;
    }

    public CreatedPublisher getByID(Long publisherID) {
        if (publisherRepo.findById(publisherID).isPresent()) {
            Publisher publisher = publisherRepo.findById(publisherID).get();
            return TransferService.publisherDtoMaker(publisher);
        } else {
            Publisher publisher = new Publisher();
            throw new RecordNotFound(publisher);
        }
    }


    //    update
    public CreatedPublisher update(Long publisherID, CreatePublisher createPublisher) {
        if (publisherRepo.findById(publisherID).isPresent()) {
            Publisher publisher = publisherRepo.findById(publisherID).get();
            Publisher publisher1 = TransferService.publisherMaker(createPublisher);

            publisher1.setPublisherID(publisher.getPublisherID());
            publisherRepo.save(publisher1);
            return TransferService.publisherDtoMaker(publisher1);
        } else {
            Publisher publisher = new Publisher();
            throw new RecordNotFound(publisher);
        }
    }

    //    delete
    public void deleteById(Long publisherID) {
        publisherRepo.deleteById(publisherID);
    }

    // asign method
//    publisher dto maker if statement
//    controller

//

//    assign domain to publisher
    public CreatedPublisher newDomains(CreateDomain createDomain, Long publisherID){
        Optional<Publisher> optionalPublisher = publisherRepo.findById(publisherID);
        Publisher publisher = optionalPublisher.get();


//        Optional<Domain> optionalDomain = domainRepo.findById(domainID);
//        Domain newDomain = optionalDomain.get();
        Domain newDomain = TransferService.domainMaker(createDomain);

        List<Domain> currentDomains = publisher.getDomains();
        currentDomains.add(newDomain);

        for (Domain domain : currentDomains){
            domain.setPublisher(publisher);
            domainRepo.save(domain);
        }

        publisher.setDomains(currentDomains);
        publisherRepo.save(publisher);
        return TransferService.publisherDtoMaker(publisher);
    }

//    public CreatedPublisher assignDeals(Long publisherID, Long dealID){
//        var publisher = publisherRepo.findById(publisherID).get();
//        var newDeal = dealRepo.findById(dealID).get();
//
//        List<Deal> currentDeals = publisher.getDeals();
//        currentDeals.add(newDeal);
//
//
//        for (Deal deal : currentDeals){
//            deal.setPublisher(publisher);
//            dealRepo.save(deal);
//        }
//
//        publisher.setDeals(currentDeals);
//        publisherRepo.save(publisher);
//        return publisherDtoMaker(publisher);
//    }
}
