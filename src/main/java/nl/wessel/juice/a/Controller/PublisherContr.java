package nl.wessel.juice.a.Controller;


import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.PublisherDto;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping(value = "/juice/publisher")
public class PublisherContr {


    CustomerService customerService;
    DomainService domainService;
    BidService bidService;
    DealService dealService;
    PublisherService publisherService;
    PhotoService photoService;


    @Autowired
    public PublisherContr(CustomerService customerService, DomainService domainService, BidService bidService, DealService dealService, PublisherService publisherService, PhotoService photoService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
        this.dealService = dealService;
        this.publisherService = publisherService;
        this.photoService = photoService;
    }


    //    1
    @PostMapping("newpublisher")
    public ResponseEntity<Object> newPublisher(@RequestBody PublisherDto dto) {
        String newPublisherName = publisherService.createPublisher(dto);
        publisherService.addAuthority(newPublisherName, "ROLE_PUBLISHER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newPublisherName}")
                .buildAndExpand(newPublisherName).toUri();

        return ResponseEntity.created(location).build();
    }

    //    2
    @PostMapping("newdomain/{username}")
    public ResponseEntity<PublisherDto> newDomain(@RequestBody CreateDomain createDomain,
                                                  @PathVariable String username) {
        return ResponseEntity.ok().body(publisherService.newDomain(createDomain, username));
    }

    //    3
    @PutMapping("updatepublisher/{username}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable("username") String username,
                                                        @RequestBody PublisherDto publisherDto) {
        publisherService.updatePublisher(username, publisherDto);
        return ResponseEntity.noContent().build();
    }

    //  4
    @PutMapping("updatedomain/{domainID}")
    public ResponseEntity<Object> updateDomain(@PathVariable Long domainID, @RequestBody CreateDomain createDomain) {
        domainService.update(domainID, createDomain);
        return ResponseEntity.noContent().build();
    }


    //    5
    @DeleteMapping("deletepublisher/{username}")
    public ResponseEntity<Object> deletePublisher(@PathVariable String username) {
        publisherService.deletePublisher(username);
        return ResponseEntity.noContent().build();
    }

    //    6
    @DeleteMapping("deletedomain/{domainID}")
    public ResponseEntity<Object> deleteDomain(@PathVariable Long domainID) {
        domainService.deleteById(domainID);
        return ResponseEntity.noContent().build();
    }


    //    7
    @GetMapping("photo/download/{photoName}")
    ResponseEntity<byte[]> downloadSinglePhoto(@PathVariable String photoName, HttpServletRequest httpServletRequest) {
        var photo =  photoService.DownloadSinglePhoto(photoName, httpServletRequest);
        return photo;
    }


}
