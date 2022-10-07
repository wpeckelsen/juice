package nl.wessel.juice.a.Controller;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Owner.OwnerDto;
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
    @PostMapping("newdeal/{bidID}/{domainID}/{ownerName}/{customerName}")
    public ResponseEntity<CreatedDeal> newDeal(@RequestBody CreateDeal createDeal,
                                               @PathVariable("bidID") Long bidID,
                                               @PathVariable("domainID") Long domainID,
                                               @PathVariable("ownerName") String ownerName,
                                               @PathVariable("customerName") String customerName) {
        final CreatedDeal createdDeal = dealService.newDeal(createDeal, bidID, domainID, ownerName, customerName);

        URI newDeal = ServletUriComponentsBuilder.fromCurrentRequest().path("newdeal")
                .buildAndExpand(createdDeal).toUri();

        return ResponseEntity.created(newDeal).build();

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


    //    7
    @GetMapping("customerbyid/{customername}")
    public ResponseEntity<CustomerDto> customerByID(@PathVariable("customerName") String customerName) {
        CustomerDto customerDto = customerService.getCustomer(customerName);
        return ResponseEntity.ok().body(customerDto);
    }


    //    8
    @GetMapping("bidbyid/{bidID}")
    public ResponseEntity<CreatedBid> bidByID(@PathVariable("bidID") Long bidID) {
        CreatedBid createdBid = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBid);
    }


    //    9
    @GetMapping("ownerbyid/{ownerName}")
    public ResponseEntity<OwnerDto> ownerByID(@PathVariable("ownerName") String ownerName) {
        OwnerDto ownerDto = ownerService.getOwner(ownerName);
        return ResponseEntity.ok().body(ownerDto);
    }


    //    10
    @GetMapping("domainbyid/{domainID}")
    public ResponseEntity<CreatedDomain> domainByID(@PathVariable("domainID") Long domainID) {
        CreatedDomain createdDomain = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomain);
    }

    //    11
    @GetMapping("dealbyid/{dealID}")
    public ResponseEntity<CreatedDeal> dealByID(@PathVariable("dealID") Long dealID) {
        CreatedDeal createdDeal = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDeal);
    }

}
