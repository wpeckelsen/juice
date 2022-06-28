package nl.wessel.juice.B.BusinessLogic.Service;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import nl.wessel.juice.C.Repository.PublisherRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepo publisherRepo;

    public PublisherService(PublisherRepo publisherRepo) {
        this.publisherRepo = publisherRepo;
    }


    public Publisher publisherMaker(CreatePublisher createPublisher){
        Publisher publisher = new Publisher();
        publisher.setName(createPublisher.getName());
        publisher.setEmail(createPublisher.getEmail());
        publisher.setPassword(createPublisher.getPassword());
        return publisher;
    }

    public CreatedPublisher publisherDtoMaker(Publisher publisher){
        CreatedPublisher createdPublisher = new CreatedPublisher();
        createdPublisher.setPublisherID(publisher.getPublisherID());
        createdPublisher.setName(publisher.getName());
        createdPublisher.setEmail(publisher.getEmail());
        createdPublisher.setPassword(publisher.getPassword());
        return createdPublisher;
    }

//    create

    public CreatedPublisher newPublisher(CreatePublisher createPublisher){
        Publisher publisher = publisherMaker(createPublisher);
        publisherRepo.save(publisher);
        return publisherDtoMaker(publisher);
    }


//    read

public List<CreatedPublisher> getList() {
    List<Publisher> publisherList = publisherRepo.findAll();
    List<CreatedPublisher> createdPublisherList = new ArrayList<>();

    for (Publisher publisher : publisherList) {
        CreatedPublisher createdPublisher = publisherDtoMaker(publisher);
        createdPublisherList.add(createdPublisher);
    }
    return createdPublisherList;
}

    public List<CreatedPublisher> getListByName(String name) {
        List<Publisher> publisherList = publisherRepo.findPublishersByName(name);
        List<CreatedPublisher> createdPublisherList = new ArrayList<>();

        for (Publisher publisher : publisherList) {
            CreatedPublisher createdPublisher = publisherDtoMaker(publisher);
            createdPublisherList.add(createdPublisher);
        }
        return createdPublisherList;
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
}
