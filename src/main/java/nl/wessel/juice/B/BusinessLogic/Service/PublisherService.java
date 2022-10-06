package nl.wessel.juice.B.BusinessLogic.Service;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import nl.wessel.juice.C.Repository.DomainRepo;
import nl.wessel.juice.C.Repository.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepo publisherRepo;
    private final DomainRepo domainRepo;


    @Autowired
    public PublisherService(PublisherRepo publisherRepo, DomainRepo domainRepo) {
        this.publisherRepo = publisherRepo;
        this.domainRepo = domainRepo;
    }

    public static CreatedPublisher publisherDtoMaker(Publisher publisher) {
        CreatedPublisher createdPublisher = new CreatedPublisher();
        createdPublisher.setPublisherID(publisher.getPublisherID());
        createdPublisher.setName(publisher.getName());
        createdPublisher.setCountry(publisher.getCountry());
        createdPublisher.setNiche(publisher.getNiche());
//
//
//        List<Domain> domains = publisher.getDomains();
//        List<CreatedDomain> createdDomains = new ArrayList<>();
//
//        if (domains != null) {
//            for (Domain domain : domains) {
//                CreatedDomain createdDomain = DomainService.domainDtoMaker(domain);
//                createdDomains.add(createdDomain);
//            }
//        }
//
//        createdPublisher.setDomains(createdDomains);


        return createdPublisher;
    }

    public static Publisher publisherMaker(CreatePublisher createPublisher) {
        Publisher publisher = new Publisher();
        publisher.setName(createPublisher.getName());
        publisher.setCountry(createPublisher.getCountry());
        publisher.setNiche(createPublisher.getNiche());
        return publisher;
    }

    //    create
    public CreatedPublisher newPublisher(CreatePublisher createPublisher) {
        Publisher publisher = publisherMaker(createPublisher);
        publisherRepo.save(publisher);
        return publisherDtoMaker(publisher);
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
                CreatedPublisher createdPublisher = publisherDtoMaker(publisher);
                createdPublisherList.add(createdPublisher);
            }
            return createdPublisherList;
        }
    }


    public CreatedPublisher getByID(Long publisherID) {
        if (publisherRepo.findById(publisherID).isPresent()) {
            Publisher publisher = publisherRepo.findById(publisherID).get();
            return publisherDtoMaker(publisher);
        } else {
            Publisher publisher = new Publisher();
            throw new RecordNotFound(publisher);
        }
    }


    //    update
    public CreatedPublisher update(Long publisherID, CreatePublisher createPublisher) {
        if (publisherRepo.findById(publisherID).isPresent()) {
            Publisher publisher = publisherRepo.findById(publisherID).get();
            Publisher publisher1 = publisherMaker(createPublisher);

            publisher1.setPublisherID(publisher.getPublisherID());
            publisherRepo.save(publisher1);
            return publisherDtoMaker(publisher1);
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

}
