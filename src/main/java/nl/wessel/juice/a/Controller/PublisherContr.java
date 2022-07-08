package nl.wessel.juice.a.Controller.UserPublContr;


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

    @GetMapping("/{publisherID}")
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
    @PutMapping("assign/publisher/{publisherID}/domain/{domainID}")
    public ResponseEntity<CreatedPublisher> assignDomains(@PathVariable Long publisherID,
                                                          @PathVariable Long domainID){
        return ResponseEntity.ok().body(publisherService.assignDomains(publisherID, domainID));
    }
}