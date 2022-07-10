package nl.wessel.juice.a.Controller.Admin;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import nl.wessel.juice.B.BusinessLogic.Service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/juice/admin")
public class AdminContr {


    CustomerService customerService;
    DealService dealService;
    BidService bidService;

    @Autowired
    public AdminContr(CustomerService customerService) {
        this.customerService = customerService;
    }


    //    CREATE


    //    READ
    @GetMapping(value = "getcustomers")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> dtos = customerService.getCustomers();
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("getdeals")
    public ResponseEntity<List<CreatedDeal>> getdeals() {
        List<CreatedDeal> createdDealList;
        createdDealList = dealService.getList();
        return ResponseEntity.ok().body(createdDealList);
    }


    @GetMapping("getdealsbyid/{dealID}")
    public ResponseEntity<CreatedDeal> getDealsByID(@PathVariable("dealID") Long dealID) {
        CreatedDeal createdDeal = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDeal);
    }


    //    UPDATE
    @PutMapping("updatebid/{bidID}")
    public ResponseEntity<Object> updateBid(@PathVariable Long bidID, @RequestBody CreateBid createBid) {
        CreatedBid createdBid = bidService.update(bidID, createBid);
        return ResponseEntity.ok().body(createdBid);
    }

    @PutMapping("updatecustomer/{username}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
                                                      @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(username, customerDto);
        return ResponseEntity.noContent().build();
    }


    //    DELETE
    @DeleteMapping("deletebid/{bidID}")
    public ResponseEntity<Object> deleteBid(@PathVariable Long bidID) {
        bidService.deleteById(bidID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("deletecustomer/{username}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
        customerService.deleteCustomer(username);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("deletedeal/{dealID}")
    public ResponseEntity<Object> deleteDeal(@PathVariable Long dealID) {
        dealService.deleteById(dealID);
        return ResponseEntity.noContent().build();
    }


}
