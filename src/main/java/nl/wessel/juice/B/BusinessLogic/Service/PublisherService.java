package nl.wessel.juice.B.BusinessLogic.Service;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import nl.wessel.juice.C.Repository.DomainRepo;
import nl.wessel.juice.C.Repository.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepo publisherRepo;
    private final DomainRepo domainRepo;


    @Autowired
    public PublisherService(PublisherRepo publisherRepo, DomainRepo domainRepo) {
        this.publisherRepo = publisherRepo;
        this.domainRepo = domainRepo;
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

        if (publisherList.isEmpty()) {

            Publisher publisher = new Publisher();
            throw new RecordNotFound(publisher);

        } else {

            List<CreatedPublisher> createdPublisherList = new ArrayList<>();
            for (Publisher publisher : publisherList) {
                CreatedPublisher createdPublisher = TransferService.publisherDtoMaker(publisher);
                createdPublisherList.add(createdPublisher);
            }
            return createdPublisherList;
        }
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


    //    assign domain to publisher
    public CreatedPublisher newDomains(CreateDomain createDomain, Long publisherID) {


        Optional<Publisher> optionalPublisher = publisherRepo.findById(publisherID);

        if (optionalPublisher.isPresent()) {

            Publisher publisher = optionalPublisher.get();
            Domain newDomain = TransferService.domainMaker(createDomain);

            List<Domain> currentDomains = publisher.getDomains();
            currentDomains.add(newDomain);

            for (Domain domain : currentDomains) {
                domain.setPublisher(publisher);
                domainRepo.save(domain);
            }

            publisher.setDomains(currentDomains);
            publisherRepo.save(publisher);
            return TransferService.publisherDtoMaker(publisher);
        } else {

            throw new BadRequest("a domain cannot be created without a publisher. Make a new Publisher first");
        }
    }
}
