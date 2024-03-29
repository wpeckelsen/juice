package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Bid.CreatedBidDto;
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

    @PostMapping("photo")
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

    @PostMapping("bid")
    public ResponseEntity<Object> newBid(@RequestBody CreateBidDto createBidDto) {
        CreatedBidDto bid = bidService.newBid(createBidDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{bidID}")
                .buildAndExpand(bid).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("bid/{bidID}")
    public ResponseEntity<Object> updateBid(@PathVariable Long bidID, @RequestBody CreateBidDto createBidDto) {
        bidService.update(bidID, createBidDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("customer/{username}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("username") String username,
                                                      @RequestBody CustomerDto customerDto) {
        customerService.update(username, customerDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("bid/{bidID}")
    public ResponseEntity<Object> deleteBid(@PathVariable Long bidID) {
        bidService.delete(bidID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("customer/{username}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("username") String username) {
        customerService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("principal")
    public ResponseEntity<String> principal() {
        return ResponseEntity.ok().body(customerService.principal());
    }

    @GetMapping("principal/{bidID}")
    public ResponseEntity<String> bidPrincipal(@PathVariable("bidID") Long bidID) {
        return ResponseEntity.ok().body(bidService.bidPrincipal(bidID));
    }
}
