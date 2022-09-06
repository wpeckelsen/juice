//package nl.wessel.juice.a.Controller.Customer.CustomerContr;
package nl.wessel.juice.a.Controller.Customer;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import nl.wessel.juice.B.BusinessLogic.Service.DealService;
import nl.wessel.juice.B.BusinessLogic.Service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/juice/customer")
public class CustomerContr {


    CustomerService customerService;
    DomainService domainService;
    BidService bidService;
    DealService dealService;


    @Autowired
    public CustomerContr(CustomerService customerService, DomainService domainService, BidService bidService, DealService dealService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
        this.dealService = dealService;
    }




//    CREATE

    @PostMapping("newcustomer")
    public ResponseEntity<Object> newCustomer(@RequestBody CustomerDto dto) {
        String newCustomerName = customerService.createCustomer(dto);
        customerService.addAuthority(newCustomerName, "ROLE_CUSTOMER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("newbid/{username}")
    public ResponseEntity<CustomerDto> newBid(@RequestBody CreateBid createBid,
                                              @PathVariable String username) {
        return ResponseEntity.ok().body(customerService.newBid(createBid, username));

    }




    @PostMapping("newdeal/{bidID}/{domainID}/{publisherID}/{name}")
    public ResponseEntity<CreatedDeal> newDeal(@RequestBody CreateDeal createDeal,
                                               @PathVariable("bidID") Long bidID,
                                               @PathVariable("domainID") Long domainID,
                                               @PathVariable("publisherID") Long publisherID,
                                               @PathVariable("name") String name) {
        final CreatedDeal createdDeal = dealService.newDeal(createDeal, bidID, domainID, publisherID, name);
        return ResponseEntity.ok().body(createdDeal);
    }


    //    READ

    @GetMapping("bidlist")
    public ResponseEntity<List<CreatedBid>> bidList() {
        List<CreatedBid> createdBidList;
        createdBidList = bidService.getList();
        return ResponseEntity.ok().body(createdBidList);
    }

//    api key
//    @GetMapping("bidlistapi/{username}")
//    public ResponseEntity<List<CreatedBid>> bidListApi(@PathVariable("username") String username,
//                                                       @RequestBody String api) {
//
//
//        List<CreatedBid> createdBidList;
//        createdBidList = bidService.getList();
//        return ResponseEntity.ok().body(createdBidList);
//    }


    @GetMapping("bidbyid/{bidID}")
    public ResponseEntity<CreatedBid> bidByID(@PathVariable("bidID") Long bidID) {
        CreatedBid createdBid = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBid);
    }

    @GetMapping("stringbidbyid/{bidID}")
    public ResponseEntity<CreatedBid> stringBidByID(@PathVariable("bidID") Long bidID) {
        CreatedBid createdBid = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBid);
    }


    @GetMapping("domainlist")
    public ResponseEntity<List<CreatedDomain>> domainList() {
        List<CreatedDomain> createdDomainList;
        createdDomainList = domainService.getList();
        return ResponseEntity.ok().body(createdDomainList);
    }

    @GetMapping("domainbyid/{domainID}")
    public ResponseEntity<CreatedDomain> domainByID(@PathVariable("domainID") Long domainID) {
        CreatedDomain createdDomain = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomain);
    }

}
