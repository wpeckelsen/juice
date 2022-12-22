package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Deal.CreateDeal;
import nl.wessel.juice.Model.Photo;
import nl.wessel.juice.Service.BidService;
import nl.wessel.juice.Service.CustomerService;
import nl.wessel.juice.Service.DealService;
import nl.wessel.juice.Service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/juice/admin")
public class AdminController {


    CustomerService customerService;
    DealService dealService;
    BidService bidService;
    PhotoService photoService;

    @Autowired
    public AdminController(CustomerService customerService, DealService dealService, BidService bidService, PhotoService photoService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
        this.photoService = photoService;
    }


    @PutMapping("put/{dealID}")
    public ResponseEntity<Object> updateDeal(@PathVariable Long dealID, @RequestBody CreateDeal createDeal) {

        dealService.update(dealID, createDeal);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{dealID}")
    public ResponseEntity<Object> deleteDeal(@PathVariable Long dealID) {
        dealService.deleteById(dealID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get/photos")
    public Collection<Photo> findAllPhotos() {
        return photoService.findAllPhotos();
    }


    @GetMapping("abcxyz")
    public ResponseEntity<String> abcxyz() {
        String string = "abcxyz";
        return ResponseEntity.ok().body(string);
    }

}
