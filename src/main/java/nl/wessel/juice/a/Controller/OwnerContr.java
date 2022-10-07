package nl.wessel.juice.a.Controller;


import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Owner.OwnerDto;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/juice/owner")
public class OwnerContr {


    CustomerService customerService;
    DomainService domainService;
    BidService bidService;
    DealService dealService;
    OwnerService ownerService;


    @Autowired
    public OwnerContr(CustomerService customerService, DomainService domainService, BidService bidService, DealService dealService, OwnerService ownerService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
        this.dealService = dealService;
        this.ownerService = ownerService;
    }




//    1
    @PostMapping("newowner")
    public ResponseEntity<Object> newOwner(@RequestBody OwnerDto dto) {
        String newOwnerName = ownerService.createOwner(dto);
        ownerService.addAuthority(newOwnerName, "ROLE_CUSTOMER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newOwnerName}")
                .buildAndExpand(newOwnerName).toUri();

        return ResponseEntity.created(location).build();
    }

    //    2
    @PostMapping("newdomain/{username}")
    public ResponseEntity<OwnerDto> newDomain(@RequestBody CreateDomain createDomain,
                                              @PathVariable String username) {
        return ResponseEntity.ok().body(ownerService.newDomain(createDomain, username));
    }

    //    3
    @PutMapping("updatepublisher/{publisherID}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable("username") String ownerName,
                                                @RequestBody OwnerDto ownerDto) {
        ownerService.updateOwner(ownerName, ownerDto);
        return ResponseEntity.noContent().build();
    }

    //  4
    @PutMapping("updatedomain/{domainID}")
    public ResponseEntity<Object> updateDomain(@PathVariable Long domainID, @RequestBody CreateDomain createDomain) {
        domainService.update(domainID, createDomain);
        return ResponseEntity.noContent().build();
    }


    //    5
    @DeleteMapping("deletepublisher/{username}")
    public ResponseEntity<Object> deletePublisher(@PathVariable String username) {
        ownerService.deleteOwner(username);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("deletecustomer/{username}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
        customerService.deleteCustomer(username);
        return ResponseEntity.noContent().build();
    }


    //    6
    @DeleteMapping("deletedomain/{domainID}")
    public ResponseEntity<Object> deleteDomain(@PathVariable Long domainID) {
        domainService.deleteById(domainID);
        return ResponseEntity.noContent().build();
    }


}
