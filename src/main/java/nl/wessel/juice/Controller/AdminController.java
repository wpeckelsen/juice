package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Deal.CreateDealDto;
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


    @PutMapping("{dealID}")
    public ResponseEntity<Object> update(@PathVariable Long dealID, @RequestBody CreateDealDto createDealDto) {

        dealService.update(dealID, createDealDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{dealID}")
    public ResponseEntity<Object> delete(@PathVariable Long dealID) {
        dealService.deleteById(dealID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("photos")
    public Collection<Photo> photos() {
        return photoService.findAllPhotos();
    }


    @GetMapping("abcxyz")
    public ResponseEntity<String> abcxyz() {
        String string = "abcxyz";
        return ResponseEntity.ok().body(string);
    }

}
