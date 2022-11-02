package nl.wessel.juice.a.Controller;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.Model.Photo;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import nl.wessel.juice.B.BusinessLogic.Service.DealService;
import nl.wessel.juice.B.BusinessLogic.Service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/juice/admin")
public class AdminContr {


    CustomerService customerService;
    DealService dealService;
    BidService bidService;
    PhotoService photoService;

    @Autowired
    public AdminContr(CustomerService customerService, DealService dealService, BidService bidService, PhotoService photoService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
        this.photoService = photoService;
    }


    @PutMapping("updatedeal/{dealID}")
    public ResponseEntity<Object> updateDeal(@PathVariable Long dealID, @RequestBody CreateDeal createDeal){

        dealService.update(dealID, createDeal);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("deletedeal/{dealID}")
    public ResponseEntity<Object> deleteDeal(@PathVariable Long dealID) {
        dealService.deleteById(dealID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findallphotos")
    public Collection<Photo> findAllPhotos() {
        return photoService.findAllPhotos();
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
