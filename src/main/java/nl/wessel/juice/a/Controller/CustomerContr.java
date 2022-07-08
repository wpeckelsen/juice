//package nl.wessel.juice.a.Controller.CustomerContr;
package nl.wessel.juice.a.Controller;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
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
        customerService.addAuthority(newCustomerName, "ROLE_CUSTOMER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();

        return ResponseEntity.created(location).build();
    }


    //    READ



    //    UPDATE
    @PutMapping("update/{username}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
                                                      @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(username, customerDto);
        return ResponseEntity.noContent().build();
    }


    //    DELETE
    @DeleteMapping( "delete/{username}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
        customerService.deleteCustomer(username);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("bid/{bidID}/{username}")
    public ResponseEntity<CustomerDto> assignBids(@PathVariable Long bidID,
                                                  @PathVariable String username) {
        return ResponseEntity.ok().body(customerService.assignBids(bidID, username));
    }

}
