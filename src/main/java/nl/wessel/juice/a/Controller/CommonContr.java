package nl.wessel.juice.a.Controller;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.PublisherDto;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/juice/common")
public class CommonContr {

    CustomerService customerService;
    DealService dealService;
    BidService bidService;
    DomainService domainService;
    PublisherService publisherService;

    @Autowired
    public CommonContr(CustomerService customerService, DealService dealService, BidService bidService, DomainService domainService, PublisherService publisherService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
        this.domainService = domainService;
        this.publisherService = publisherService;
    }

    //    1
    @PostMapping("newdeal/{bidID}/{domainID}/{publisherName}/{customerName}")
    public ResponseEntity<CreatedDeal> newDeal(@RequestBody CreateDeal createDeal,
                                               @PathVariable(value = "bidID") Long bidID,
                                               @PathVariable(value = "domainID") Long domainID,
                                               @PathVariable(value = "publisherName") String publisherName,
                                               @PathVariable(value = "customerName") String customerName) {
        final CreatedDeal createdDeal = dealService.newDeal(createDeal, bidID, domainID, publisherName, customerName);

        return ResponseEntity.ok().body(createdDeal);
    }



    //    2
    @GetMapping("customerlist")
    public ResponseEntity<List<CustomerDto>> customerList() {
        List<CustomerDto> dtos = customerService.getCustomers();
        return ResponseEntity.ok().body(dtos);
    }

    //    3
    @GetMapping("bidlist")
    public ResponseEntity<List<CreatedBid>> bidList() {
        List<CreatedBid> createdBidList;
        createdBidList = bidService.getList();
        return ResponseEntity.ok().body(createdBidList);
    }

    //    4
    @GetMapping("publisherlist")
    public ResponseEntity<List<PublisherDto>> publisherList() {
        List<PublisherDto> dtos = publisherService.getPublishers();
        return ResponseEntity.ok().body(dtos);
    }

    //    5
    @GetMapping("domainlist")
    public ResponseEntity<List<CreatedDomain>> domainList() {
        List<CreatedDomain> createdDomainList;
        createdDomainList = domainService.getList();
        return ResponseEntity.ok().body(createdDomainList);
    }

    //    6
    @GetMapping("deallist")
    public ResponseEntity<List<CreatedDeal>> dealList() {
        List<CreatedDeal> createdDealList;
        createdDealList = dealService.getList();
        return ResponseEntity.ok().body(createdDealList);
    }


    //    7
    @GetMapping("customerbyid/{customerName}")
    public ResponseEntity<CustomerDto> customerByID(@PathVariable(value = "customerName") String customerName) {
        CustomerDto customerDto = customerService.getCustomer(customerName);
        return ResponseEntity.ok().body(customerDto);
    }


    //    8
    @GetMapping("bidbyid/{bidID}")
    public ResponseEntity<CreatedBid> bidByID(@PathVariable(value = "bidID") Long bidID) {
        CreatedBid createdBid = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBid);
    }


    //    9
    @GetMapping("publisherbyid/{publisherName}")
    public ResponseEntity<PublisherDto> publisherByID(@PathVariable(value = "publisherName") String publisherName) {
        PublisherDto publisherDto = publisherService.getPublisher(publisherName);
        return ResponseEntity.ok().body(publisherDto);
    }



    //    10
    @GetMapping("domainbyid/{domainID}")
    public ResponseEntity<CreatedDomain> domainByID(@PathVariable(value = "domainID") Long domainID) {
        CreatedDomain createdDomain = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomain);
    }

    //    11
    @GetMapping("dealbyid/{dealID}")
    public ResponseEntity<CreatedDeal> dealByID(@PathVariable(value = "dealID") Long dealID) {
        CreatedDeal createdDeal = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDeal);
    }

}
