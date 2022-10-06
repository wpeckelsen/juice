package nl.wessel.juice.a.Controller;

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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/juice/admin")
public class AdminContr {


    CustomerService customerService;
    DealService dealService;
    BidService bidService;

    @Autowired
    public AdminContr(CustomerService customerService, DealService dealService, BidService bidService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
    }

    //    READ
//    @GetMapping("getcustomers")
//    public ResponseEntity<List<CustomerDto>> getCustomers() {
//        List<CustomerDto> dtos = customerService.getCustomers();
//        return ResponseEntity.ok().body(dtos);
//    }

//    @GetMapping("getdeals")
//    public ResponseEntity<List<CreatedDeal>> getdeals() {
//        List<CreatedDeal> createdDealList;
//        createdDealList = dealService.getList();
//        return ResponseEntity.ok().body(createdDealList);
//    }


//    @GetMapping("getdealbyid/{dealID}")
//    public ResponseEntity<CreatedDeal> getDealByID(@PathVariable("dealID") Long dealID) {
//        CreatedDeal createdDeal = dealService.getByID(dealID);
//        return ResponseEntity.ok().body(createdDeal);
//    }


    //    UPDATE
//    @PutMapping("updatebid/{bidID}")
//    public ResponseEntity<Object> updateBid(@PathVariable Long bidID, @RequestBody CreateBid createBid) {
//        CreatedBid createdBid = bidService.update(bidID, createBid);
//        return ResponseEntity.ok().body(createdBid);
//    }

//    @PutMapping("updatecustomer/{username}")
//    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
//                                                      @RequestBody CustomerDto customerDto) {
//        customerService.updateCustomer(username, customerDto);
//        return ResponseEntity.noContent().build();
//    }


//    //    DELETE
//    @DeleteMapping("deletebid/{bidID}")
//    public ResponseEntity<Object> deleteBid(@PathVariable Long bidID) {
//        bidService.deleteById(bidID);
//        return ResponseEntity.noContent().build();
//    }

//    @DeleteMapping("deletecustomer/{username}")
//    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
//        customerService.deleteCustomer(username);
//        return ResponseEntity.noContent().build();
//    }


    @DeleteMapping("deletedeal/{dealID}")
    public ResponseEntity<Object> deleteDeal(@PathVariable Long dealID) {
        dealService.deleteById(dealID);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("abcxyz")
    public ResponseEntity<String> abcxyz(){
        String string = "abcxyz";
        return ResponseEntity.ok().body(string);
    }



    @PostMapping("collatz")
    public ResponseEntity<Object> collatz(@RequestBody int n) {
//      this is a test request, it is not part of the whole project

//      collatz Conjecture -
//      Start with a number n > 1.

//      Apply the following rule:
//          If n is even, divide it by 2. n/2
//          If n is odd multiply it by 3 and add 1. 3n+1
//      Take the outcome and apply either rule to it again.

//      Find the number of steps it takes to reach 1.

//      you will see how the result will always be 1,
//      but the >amount< of steps it takes to get to 1 is random for each number.

        int input = n;
        List<Integer> list = new ArrayList<>();
        int count;

        count = 0;
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else n = 3 * n + 1;
            count = count + 1;
            list.add(n);

        }
        return ResponseEntity.ok().body("for " + input + " to reach 1, it takes " + count + " steps. this ArrayList shows how: "
                + list);
    }

}
