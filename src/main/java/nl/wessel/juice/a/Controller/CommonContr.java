package nl.wessel.juice.a.Controller;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Owner.OwnerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/juice/common")
public class CommonContr {

    CustomerService customerService;
    DealService dealService;
    BidService bidService;
    DomainService domainService;
    OwnerService ownerService;

    @Autowired
    public CommonContr(CustomerService customerService, DealService dealService, BidService bidService, DomainService domainService, OwnerService ownerService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
        this.domainService = domainService;
        this.ownerService = ownerService;
    }

//    1
    @PostMapping("newdeal/{bidID}/{domainID}/{publisherID}/{name}")
    public ResponseEntity<CreatedDeal> newDeal(@RequestBody CreateDeal createDeal,
                                               @PathVariable("bidID") Long bidID,
                                               @PathVariable("domainID") Long domainID,
                                               @PathVariable("publisherID") String ownerName,
                                               @PathVariable("name") String customerName) {
        final CreatedDeal createdDeal = dealService.newDeal(createDeal, bidID, domainID, ownerName, customerName);
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
    @GetMapping("ownerlist")
    public ResponseEntity<List<OwnerDto>> ownerList() {
        List<OwnerDto> dtos = ownerService.getOwners();
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



//    8
    @GetMapping("bidbyid/{bidID}")
    public ResponseEntity<CreatedBid> bidByID(@PathVariable("bidID") Long bidID) {
        CreatedBid createdBid = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBid);
    }


 //    9
    @GetMapping("getbyid/{publisherID}")
    public ResponseEntity<CreatedPublisher> getByID(@PathVariable("publisherID") Long publisherID) {
        CreatedPublisher createdPublisher = publisherService.getByID(publisherID);
        return ResponseEntity.ok().body(createdPublisher);
    }

//    10
    @GetMapping("domainbyid/{domainID}")
    public ResponseEntity<CreatedDomain> domainByID(@PathVariable("domainID") Long domainID) {
        CreatedDomain createdDomain = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomain);
    }

//    11
    @GetMapping("getdealbyid/{dealID}")
    public ResponseEntity<CreatedDeal> getDealByID(@PathVariable("dealID") Long dealID) {
        CreatedDeal createdDeal = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDeal);
    }

}
