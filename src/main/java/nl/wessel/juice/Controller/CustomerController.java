package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Bid.CreateBid;
import nl.wessel.juice.DTO.Customer.CustomerDto;
import nl.wessel.juice.DTO.Photo.PhotoDto;
import nl.wessel.juice.Model.Photo;
import nl.wessel.juice.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/juice/customer")
public class CustomerController {


    CustomerService customerService;
    DomainService domainService;
    BidService bidService;
    DealService dealService;
    PhotoService photoService;


    @Autowired
    public CustomerController(CustomerService customerService, DomainService domainService, BidService bidService, DealService dealService, PhotoService photoService) {
        this.customerService = customerService;
        this.domainService = domainService;
        this.bidService = bidService;
        this.dealService = dealService;
        this.photoService = photoService;
    }

    @PostMapping("post/customer")
    public ResponseEntity<Object> newCustomer(@RequestBody CustomerDto dto) {
        String newCustomerName = customerService.createCustomer(dto);
        customerService.addAuthority(newCustomerName, "ROLE_CUSTOMER");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("post/photo")
    public PhotoDto newPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Photo photo = photoService.UploadSinglePhoto(file);
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String contentType = file.getContentType();
        return new PhotoDto(photo.getFileName(), url, contentType);
    }

    @PostMapping("post/{username}")
    public ResponseEntity<CustomerDto> newBid(@RequestBody CreateBid createBid,
                                              @PathVariable String username) {
        return ResponseEntity.ok().body(customerService.newBid(createBid, username));

    }

    @PutMapping("put/{bidID}")
    public ResponseEntity<Object> updateBid(@PathVariable Long bidID, @RequestBody CreateBid createBid) {
        bidService.update(bidID, createBid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("put/{username}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
                                                      @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(username, customerDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{bidID}")
    public ResponseEntity<Object> deleteBid(@PathVariable Long bidID) {
        bidService.deleteById(bidID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{username}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
        customerService.deleteCustomer(username);
        return ResponseEntity.noContent().build();
    }
}
