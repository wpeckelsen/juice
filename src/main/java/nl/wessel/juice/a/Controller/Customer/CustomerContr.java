package nl.wessel.juice.a.Controller.CustomerContr;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
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

    @Autowired
    CustomerService customerService;

    @Autowired
    DomainService domainService;

    @Autowired
    BidService bidService;

    @Autowired
    DealService dealService;


    public CustomerContr(CustomerService customerService, DomainService domainService, BidService bidService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
    }


//    CREATE

    @PostMapping("/new")
    public ResponseEntity<CustomerDto> newCustomer(@RequestBody CustomerDto dto) {
        String newCustomerName = customerService.createCustomer(dto);
        customerService.addAuthority(newCustomerName, "ROLE_CLIENT");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();

        return ResponseEntity.created(location).build();
    }



    @PostMapping("bid/new")
    public ResponseEntity<CreatedBid> newBid(@RequestBody CreateBid bid) {
        final CreatedBid createdBid = bidService.newBid(bid);
        return ResponseEntity.ok().body(createdBid);
    }





    @PostMapping("deal/{bidID}/{domainID}/{name1}/{name2}")
    public ResponseEntity<CreatedDeal> newDeal(@RequestBody CreateDeal createDeal,
                                               @PathVariable("bidID") Long bidID,
                                               @PathVariable("domainID") Long domainID,
                                               @PathVariable("name1") String name1,
                                               @PathVariable("name2") String name2) {

        final CreatedDeal createdDeal = dealService.newDeal(createDeal, bidID, domainID, name1, name2);
        return ResponseEntity.ok().body(createdDeal);
    }


    //    READ

    @GetMapping("/list")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> list = customerService.getCustomers();
        return ResponseEntity.ok().body(list);
    }

    //    UPDATE
    @PutMapping("update/{username}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
                                                      @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(username, customerDto);
        return ResponseEntity.noContent().build();
    }


    //    DELETE



    //    ASSIGN
    @PutMapping("domain/{domainID}/{username}")
    public ResponseEntity<CustomerDto> assignDomains(@PathVariable Long domainID,
                                                     @PathVariable String username) {
        return ResponseEntity.ok().body(customerService.assignDomains(domainID, username));
    }

    @PutMapping("bid/{bidID}/{username}")
    public ResponseEntity<CustomerDto> assignBids(@PathVariable Long bidID,
                                                  @PathVariable String username) {
        return ResponseEntity.ok().body(customerService.assignBids(bidID, username));
    }

}
