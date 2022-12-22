package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Bid.CreatedBid;
import nl.wessel.juice.DTO.Customer.CustomerDto;
import nl.wessel.juice.DTO.Deal.CreateDeal;
import nl.wessel.juice.DTO.Deal.CreatedDeal;
import nl.wessel.juice.DTO.Domain.CreatedDomain;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
import nl.wessel.juice.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/juice/common")
public class CommonController {

    CustomerService customerService;
    DealService dealService;
    BidService bidService;
    DomainService domainService;
    PublisherService publisherService;

    @Autowired
    public CommonController(CustomerService customerService, DealService dealService, BidService bidService, DomainService domainService, PublisherService publisherService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
        this.domainService = domainService;
        this.publisherService = publisherService;
    }


    @PostMapping("post/{bidID}/{domainID}/{publisherName}/{customerName}")
    public ResponseEntity<CreatedDeal> newDeal(@RequestBody CreateDeal createDeal,
                                               @PathVariable(value = "bidID") Long bidID,
                                               @PathVariable(value = "domainID") Long domainID,
                                               @PathVariable(value = "publisherName") String publisherName,
                                               @PathVariable(value = "customerName") String customerName) {
        final CreatedDeal createdDeal = dealService.newDeal(createDeal, bidID, domainID, publisherName, customerName);

        return ResponseEntity.ok().body(createdDeal);
    }



    @GetMapping("get/customers")
    public ResponseEntity<List<CustomerDto>> customerList() {
        List<CustomerDto> customerDtos = customerService.getCustomers();
        return ResponseEntity.ok().body(customerDtos);
    }


    @GetMapping("get/bids")
    public ResponseEntity<List<CreatedBid>> bidList() {
        List<CreatedBid> createdBidList;
        createdBidList = bidService.getList();
        return ResponseEntity.ok().body(createdBidList);
    }


    @GetMapping("get/publishers")
    public ResponseEntity<List<PublisherDto>> publisherList() {
        List<PublisherDto> dtos = publisherService.getPublishers();
        return ResponseEntity.ok().body(dtos);
    }

    //    5
    @GetMapping("get/domains")
    public ResponseEntity<List<CreatedDomain>> domainList() {
        List<CreatedDomain> createdDomainList;
        createdDomainList = domainService.getList();
        return ResponseEntity.ok().body(createdDomainList);
    }

    //    6
    @GetMapping("get/deals")
    public ResponseEntity<List<CreatedDeal>> dealList() {
        List<CreatedDeal> createdDealList;
        createdDealList = dealService.getList();
        return ResponseEntity.ok().body(createdDealList);
    }


    //    7
    @GetMapping("get/customer/{customerName}")
    public ResponseEntity<CustomerDto> customerByID(@PathVariable(value = "customerName") String customerName) {
        CustomerDto customerDto = customerService.getCustomer(customerName);
        return ResponseEntity.ok().body(customerDto);
    }


    //    8
    @GetMapping("get/bid/{bidID}")
    public ResponseEntity<CreatedBid> bidByID(@PathVariable(value = "bidID") Long bidID) {
        CreatedBid createdBid = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBid);
    }


    //    9
    @GetMapping("get/publisjer/{publisherName}")
    public ResponseEntity<PublisherDto> publisherByID(@PathVariable(value = "publisherName") String publisherName) {
        PublisherDto publisherDto = publisherService.getPublisher(publisherName);
        return ResponseEntity.ok().body(publisherDto);
    }


    //    10
    @GetMapping("get/domain/{domainID}")
    public ResponseEntity<CreatedDomain> domainByID(@PathVariable(value = "domainID") Long domainID) {
        CreatedDomain createdDomain = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomain);
    }

    //    11
    @GetMapping("get/deal/{dealID}")
    public ResponseEntity<CreatedDeal> dealByID(@PathVariable(value = "dealID") Long dealID) {
        CreatedDeal createdDeal = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDeal);
    }

}
