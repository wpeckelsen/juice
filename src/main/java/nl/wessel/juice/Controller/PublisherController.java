package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
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

    @GetMapping("{photoName}")
    ResponseEntity<byte[]> downloadSinglePhoto(@PathVariable String photoName, HttpServletRequest httpServletRequest) {
        return photoService.DownloadSinglePhoto(photoName, httpServletRequest);
    }


    @PostMapping("publisher")
    public ResponseEntity<Object> newPublisher(@RequestBody CreatedPublisherDto createdPublisherDto) {
        String newPublisherName = publisherService.newPublisher(createdPublisherDto);
        publisherService.addAuthority(newPublisherName, "ROLE_PUBLISHER");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newPublisherName}")
                .buildAndExpand(newPublisherName).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("{publisherName}")
    public ResponseEntity<Object> newDomain(@RequestBody CreateDomainDto createDomainDto,
                                            @PathVariable String publisherName) {
        Long domainID = publisherService.newDomain(createDomainDto, publisherName);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{domainID}")
                .buildAndExpand(domainID).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("publisher/{username}")
    public ResponseEntity<CreatedPublisherDto> updatePublisher(@PathVariable("username") String username,
                                                               @RequestBody CreatedPublisherDto createdPublisherDto) {
        publisherService.update(username, createdPublisherDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("domain/{domainID}")
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

}
