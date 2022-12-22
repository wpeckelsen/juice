package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Domain.CreateDomain;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
import nl.wessel.juice.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping(value = "/juice/publisher")
public class PublisherController {


    CustomerService customerService;
    DomainService domainService;
    BidService bidService;
    DealService dealService;
    PublisherService publisherService;
    PhotoService photoService;


    @Autowired
    public PublisherController(CustomerService customerService, DomainService domainService, BidService bidService, DealService dealService, PublisherService publisherService, PhotoService photoService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
        this.dealService = dealService;
        this.publisherService = publisherService;
        this.photoService = photoService;
    }

    @PostMapping("post/publisher")
    public ResponseEntity<Object> newPublisher(@RequestBody PublisherDto dto) {
        String newPublisherName = publisherService.createPublisher(dto);
        publisherService.addAuthority(newPublisherName, "ROLE_PUBLISHER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newPublisherName}")
                .buildAndExpand(newPublisherName).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("post/{username}")
    public ResponseEntity<PublisherDto> newDomain(@RequestBody CreateDomain createDomain,
                                                  @PathVariable String username) {
        return ResponseEntity.ok().body(publisherService.newDomain(createDomain, username));
    }

    @PutMapping("update/{username}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable("username") String username,
                                                        @RequestBody PublisherDto publisherDto) {
        publisherService.updatePublisher(username, publisherDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{domainID}")
    public ResponseEntity<Object> updateDomain(@PathVariable Long domainID, @RequestBody CreateDomain createDomain) {
        domainService.update(domainID, createDomain);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{username}")
    public ResponseEntity<Object> deletePublisher(@PathVariable String username) {
        publisherService.deletePublisher(username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{domainID}")
    public ResponseEntity<Object> deleteDomain(@PathVariable Long domainID) {
        domainService.deleteById(domainID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get/{photoName}")
    ResponseEntity<byte[]> downloadSinglePhoto(@PathVariable String photoName, HttpServletRequest httpServletRequest) {
        return photoService.DownloadSinglePhoto(photoName, httpServletRequest);
    }


}
