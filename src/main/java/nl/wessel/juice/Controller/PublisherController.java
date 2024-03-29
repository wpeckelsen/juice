package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
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

    @GetMapping("{photoName}")
    ResponseEntity<byte[]> downloadSinglePhoto(@PathVariable String photoName, HttpServletRequest httpServletRequest) {
        return photoService.DownloadSinglePhoto(photoName, httpServletRequest);
    }


    @PostMapping("domain")
    public ResponseEntity<Object> newDomain(@RequestBody CreateDomainDto createDomainDto) {
        CreatedDomainDto domain = domainService.newDomain(createDomainDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{domainID}")
                .buildAndExpand(domain).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("publisher/{username}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable("username") String username,
                                                        @RequestBody PublisherDto publisherDto) {
        publisherService.update(username, publisherDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("domain/{domainID}")
    public ResponseEntity<Object> updateDomain(@PathVariable Long domainID, @RequestBody CreateDomainDto createDomainDto) {
        domainService.updateDomain(domainID, createDomainDto);
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
