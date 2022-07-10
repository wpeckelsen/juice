package nl.wessel.juice.a.Controller.Publisher;


import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Service.DomainService;
import nl.wessel.juice.B.BusinessLogic.Service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "juice/publisher")
public class PublisherContr {


    PublisherService publisherService;
    DomainService domainService;

    @Autowired
    public PublisherContr(PublisherService publisherService, DomainService domainService) {
        this.publisherService = publisherService;
        this.domainService = domainService;
    }




    //    CREATE
    @PostMapping("new")
    public ResponseEntity<CreatedPublisher> newPublisher(@RequestBody CreatePublisher publisher) {
        final CreatedPublisher createdPublisher = publisherService.newPublisher(publisher);
        return ResponseEntity.ok().body(createdPublisher);
    }

    @PostMapping("newdomain/{publisherID}")
    public ResponseEntity<CreatedPublisher> newDomain(@RequestBody CreateDomain createDomain,
                                                       @PathVariable Long publisherID) {
        return ResponseEntity.ok().body(publisherService.newDomains(createDomain, publisherID));
    }


    //    READ
    @GetMapping("getpublishers")
    public ResponseEntity<List<CreatedPublisher>> getPublishers() {
        List<CreatedPublisher> createdPublisherList;
        createdPublisherList = publisherService.getList();
        return ResponseEntity.ok().body(createdPublisherList);
    }

    @GetMapping("getbyid/{publisherID}")
    public ResponseEntity<CreatedPublisher> getByID(@PathVariable("publisherID") Long publisherID) {
        CreatedPublisher createdPublisher = publisherService.getByID(publisherID);
        return ResponseEntity.ok().body(createdPublisher);
    }


    //    update
    @PutMapping("updatepublisher/{publisherID}")
    public ResponseEntity<Object> updatePublisher(@PathVariable Long publisherID, @RequestBody CreatePublisher createPublisher) {
        CreatedPublisher createdPublisher = publisherService.update(publisherID, createPublisher);
        return ResponseEntity.ok().body(createdPublisher);
    }


    @PutMapping("updatedomain/{domainID}")
    public ResponseEntity<Object> updateDomain(@PathVariable Long domainID, @RequestBody CreateDomain createDomain) {
        CreatedDomain createdDomain = domainService.update(domainID, createDomain);
        return ResponseEntity.ok().body(createdDomain);
    }


    //    delete
    @DeleteMapping("deletepublisher/{publisherID}")
    public ResponseEntity<Object> deletePublisher(@PathVariable Long publisherID) {
        publisherService.deleteById(publisherID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("deletedomain/{domainID}")
    public ResponseEntity<Object> deleteDomain(@PathVariable Long domainID) {
        domainService.deleteById(domainID);
        return ResponseEntity.noContent().build();
    }



}