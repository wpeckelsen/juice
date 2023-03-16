package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Bid.CreatedBidDto;
import nl.wessel.juice.DTO.Customer.PublicCustomerDto;
import nl.wessel.juice.DTO.Deal.CreateDealDto;
import nl.wessel.juice.DTO.Deal.CreatedDealDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;
import nl.wessel.juice.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/juice/common")
public class CommonController {

    CustomerService customerService;
    DealService dealService;
    BidService bidService;
    DomainService domainService;
    PublisherService publisherService;

    @Autowired
    public CommonController(CustomerService customerService, DealService dealService, BidService bidService, DomainService domainService, PublisherService publisherService) {
        this.customerService = customerService;
        this.dealService = dealService;
        this.bidService = bidService;
        this.domainService = domainService;
        this.publisherService = publisherService;
    }

    @PostMapping("{bidID}/{domainID}/{publisherName}/{customerName}")
    public ResponseEntity<Object> newDeal(@RequestBody CreateDealDto createDealDto,
                                                  @PathVariable(value = "bidID") Long bidID,
                                                  @PathVariable(value = "domainID") Long domainID,
                                                  @PathVariable(value = "publisherName") String publisherName,
                                                  @PathVariable(value = "customerName") String customerName) {
        Long dealID = dealService.newDeal(createDealDto, bidID, domainID, publisherName, customerName);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{dealID}")
                .buildAndExpand(dealID).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("customers")
    public ResponseEntity<List<String>> customers() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @GetMapping("bids")
    public ResponseEntity<List<Long>> bids() {
        return ResponseEntity.ok().body(bidService.getList());
    }

    @GetMapping("publishers")
    public ResponseEntity<List<String>> publishers() {
        return ResponseEntity.ok().body(publisherService.getPublishers());
    }

    @GetMapping("domains")
    public ResponseEntity<List<Long>> domains() {
        return ResponseEntity.ok().body(domainService.getList());
    }

    @GetMapping("deals")
    public ResponseEntity<List<Long>> deals() {
        return ResponseEntity.ok().body(dealService.getList());
    }

    @GetMapping("customer/{customerName}")
    public ResponseEntity<PublicCustomerDto> customer(@PathVariable(value = "customerName") String customerName) {
        var publicCustomerDto = customerService.getPublicCustomer(customerName);
        return ResponseEntity.ok().body(publicCustomerDto);

    }

    @GetMapping("bid/{bidID}")
    public ResponseEntity<CreatedBidDto> bid(@PathVariable(value = "bidID") Long bidID) {
        CreatedBidDto createdBidDTO = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBidDTO);
    }

    @GetMapping("publisher/{publisherName}")
    public ResponseEntity<CreatedPublisherDto> publisher(@PathVariable(value = "publisherName") String publisherName) {
        CreatedPublisherDto createdPublisherDto = publisherService.getPublisher(publisherName);
        return ResponseEntity.ok().body(createdPublisherDto);
    }

    @GetMapping("domain/{domainID}")
    public ResponseEntity<CreatedDomainDto> domain(@PathVariable(value = "domainID") Long domainID) {

//        String u = "localhost8080://domain/{domainID}";
//        int u2 = domain(domainID).getStatusCodeValue();
//        try{
//            URL url = new URL(u);
//        } catch (MalformedURLException e){
//            throw new PathNotFound();
//        }
//
//        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//        huc.setRequestMethod("HEAD");
//        int response = huc.getResponseCode();
//        if(response == 500){
//            throw new PathNotFound();
//        } else{
//            CreatedDomainDto createdDomainDTO = domainService.getByID(domainID);
//            return ResponseEntity.ok().body(createdDomainDTO);
//        }
        CreatedDomainDto createdDomainDTO = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomainDTO);
    }

    @GetMapping("deal/{dealID}")
    public ResponseEntity<CreatedDealDto> deal(@PathVariable(value = "dealID") Long dealID) {
        CreatedDealDto createdDealDTO = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDealDTO);
    }

}
