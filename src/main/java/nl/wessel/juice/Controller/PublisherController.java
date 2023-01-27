package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
import nl.wessel.juice.DTO.Publisher.CreatePublisherDto;
import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;
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
    public ResponseEntity<Object> newPublisher(@RequestBody CreatePublisherDto createPublisherDto) {
        String newPublisherName = publisherService.create(createPublisherDto);
        publisherService.addAuthority(newPublisherName, "ROLE_PUBLISHER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newPublisherName}")
                .buildAndExpand(newPublisherName).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("post/{username}")
    public ResponseEntity<CreatedDomainDto> newDomain(@RequestBody CreateDomainDto createDomainDto,
                                                      @PathVariable String username) {
        return ResponseEntity.ok().body(publisherService.newDomain(createDomainDto, username));
    }

    @PutMapping("update/{username}")
    public ResponseEntity<CreatedPublisherDto> updatePublisher(@PathVariable("username") String username,
                                                               @RequestBody CreatePublisherDto createPublisherDto) {
        publisherService.update(username, createPublisherDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{domainID}")
    public ResponseEntity<Object> updateDomain(@PathVariable Long domainID, @RequestBody CreateDomainDto createDomainDto) {
        domainService.update(domainID, createDomainDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("publisher/{username}")
    public ResponseEntity<Object> deletePublisher(@PathVariable String username) {
        publisherService.delete(username);
        return ResponseEntity.noContent().build();
    }




    @DeleteMapping("domain/{domainID}")
    public ResponseEntity<Object> deleteDomain(@PathVariable Long domainID) {
        domainService.deleteById(domainID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{photoName}")
    ResponseEntity<byte[]> downloadSinglePhoto(@PathVariable String photoName, HttpServletRequest httpServletRequest) {
        return photoService.DownloadSinglePhoto(photoName, httpServletRequest);
    }


}
