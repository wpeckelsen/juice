//package nl.wessel.juice.a.Controller.CustomerContr;
package nl.wessel.juice.a.Controller;

import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Photo.PhotoDto;
import nl.wessel.juice.B.BusinessLogic.Model.Photo;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value = "/juice/customer")
public class CustomerContr {


    CustomerService customerService;
    DomainService domainService;
    BidService bidService;
    DealService dealService;
    PhotoService photoService;


    @Autowired
    public CustomerContr(CustomerService customerService, DomainService domainService, BidService bidService, DealService dealService, PhotoService photoService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
        this.dealService = dealService;
        this.photoService = photoService;
    }


    //    1
    @PostMapping("newcustomer")
    public ResponseEntity<Object> newCustomer(@RequestBody CustomerDto dto) {
        String newCustomerName = customerService.createCustomer(dto);
        customerService.addAuthority(newCustomerName, "ROLE_CUSTOMER");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();
        return ResponseEntity.created(location).build();
    }


    //    2
    @PostMapping("newbid/{username}")
    public ResponseEntity<CustomerDto> newBid(@RequestBody CreateBid createBid,
                                              @PathVariable String username) {
        return ResponseEntity.ok().body(customerService.newBid(createBid, username));

    }

    //    3
    @PutMapping("updatebid/{bidID}")
    public ResponseEntity<Object> updateBid(@PathVariable Long bidID, @RequestBody CreateBid createBid) {
        bidService.update(bidID, createBid);
        return ResponseEntity.noContent().build();
    }


    //    4
    @PutMapping("updatecustomer/{username}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
                                                      @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(username, customerDto);
        return ResponseEntity.noContent().build();
    }

    //    5
    @DeleteMapping("deletebid/{bidID}")
    public ResponseEntity<Object> deleteBid(@PathVariable Long bidID) {
        bidService.deleteById(bidID);
        return ResponseEntity.noContent().build();
    }


    //    6
    @DeleteMapping("deletecustomer/{username}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
        customerService.deleteCustomer(username);
        return ResponseEntity.noContent().build();
    }

    //    7
    @PostMapping("uploadsinglephoto")
    public PhotoDto UploadSinglePhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Photo photo = photoService.UploadSinglePhoto(file);
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/uploadsinglephoto/")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String contentType = file.getContentType();

        return new PhotoDto(photo.getFileName(), url, contentType);
    }


}
