package nl.wessel.juice.a.Controller.Publisher;


import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "juice/publisher")
public class PublisherContr {


    PublisherService publisherService;

    @Autowired
    public PublisherContr(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    //    CREATE
    @PostMapping("/new")
    public ResponseEntity<CreatedPublisher> newPublisher(@RequestBody CreatePublisher publisher) {
        final CreatedPublisher createdPublisher = publisherService.newPublisher(publisher);
        return ResponseEntity.ok().body(createdPublisher);
    }


    //    READ
    @GetMapping("/list")
    public ResponseEntity<List<CreatedPublisher>> getList() {
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
    @PutMapping("/update/{publisherID}")
    public ResponseEntity<Object> update(@PathVariable Long publisherID, @RequestBody CreatePublisher createPublisher) {
        CreatedPublisher createdPublisher = publisherService.update(publisherID, createPublisher);
        return ResponseEntity.ok().body(createdPublisher);
    }


    //    delete
    @DeleteMapping("/delete/{publisherID}")
    public ResponseEntity<Object> deleteById(@PathVariable Long publisherID) {
        publisherService.deleteById(publisherID);
        return ResponseEntity.noContent().build();
    }


    //    assign
    @PostMapping("newdomains/{publisherID}")
    public ResponseEntity<CreatedPublisher> newDomains(@RequestBody CreateDomain createDomain,
                                                       @PathVariable Long publisherID) {
        return ResponseEntity.ok().body(publisherService.newDomains(createDomain, publisherID));
    }
}