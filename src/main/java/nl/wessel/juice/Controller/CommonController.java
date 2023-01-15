package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Bid.CreatedBidDTO;
import nl.wessel.juice.DTO.Customer.CreatedCustomerDto;
import nl.wessel.juice.DTO.Deal.CreateDealDto;
import nl.wessel.juice.DTO.Deal.CreatedDealDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;
import nl.wessel.juice.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<CreatedDealDto> newDeal(@RequestBody CreateDealDto createDealDto,
                                                  @PathVariable(value = "bidID") Long bidID,
                                                  @PathVariable(value = "domainID") Long domainID,
                                                  @PathVariable(value = "publisherName") String publisherName,
                                                  @PathVariable(value = "customerName") String customerName) {
        final CreatedDealDto createdDealDTO = dealService.newDeal(createDealDto, bidID, domainID, publisherName, customerName);

        return ResponseEntity.ok().body(createdDealDTO);
    }

    @GetMapping("customers")
    public ResponseEntity<List<CreatedCustomerDto>> customers() {
        List<CreatedCustomerDto> createdCustomerDtos = customerService.getCustomers();
        return ResponseEntity.ok().body(createdCustomerDtos);
    }

    @GetMapping("bids")
    public ResponseEntity<List<CreatedBidDTO>> bids() {
        List<CreatedBidDTO> createdBidDTOList;
        createdBidDTOList = bidService.getList();
        return ResponseEntity.ok().body(createdBidDTOList);
    }

    @GetMapping("publishers")
    public ResponseEntity<List<CreatedPublisherDto>> publishers() {
        List<CreatedPublisherDto> createdPublisherDtos = publisherService.getPublishers();
        return ResponseEntity.ok().body(createdPublisherDtos);
    }

    @GetMapping("domains")
    public ResponseEntity<List<CreatedDomainDto>> domains() {
        List<CreatedDomainDto> createdDomainDtoList;
        createdDomainDtoList = domainService.getList();
        return ResponseEntity.ok().body(createdDomainDtoList);
    }

    @GetMapping("deals")
    public ResponseEntity<List<CreatedDealDto>> deals() {
        List<CreatedDealDto> createdDealDtoList;
        createdDealDtoList = dealService.getList();
        return ResponseEntity.ok().body(createdDealDtoList);
    }

    @GetMapping("{customerName}")
    public ResponseEntity<CreatedCustomerDto> customer(@PathVariable(value = "customerName") String customerName) {
        CreatedCustomerDto createdCustomerDto = customerService.getCustomer(customerName);
        return ResponseEntity.ok().body(createdCustomerDto);
    }

    @GetMapping("bid/{bidID}")
    public ResponseEntity<CreatedBidDTO> bid(@PathVariable(value = "bidID") Long bidID) {
        CreatedBidDTO createdBidDTO = bidService.getByID(bidID);
        return ResponseEntity.ok().body(createdBidDTO);
    }

    @GetMapping("{publisherName}")
    public ResponseEntity<CreatedPublisherDto> publisher(@PathVariable(value = "publisherName") String publisherName) {
        CreatedPublisherDto createdPublisherDto = publisherService.getPublisher(publisherName);
        return ResponseEntity.ok().body(createdPublisherDto);
    }

    @GetMapping("domain/{domainID}")
    public ResponseEntity<CreatedDomainDto> domain(@PathVariable(value = "domainID") Long domainID) {
        CreatedDomainDto createdDomainDTO = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomainDTO);
    }

    @GetMapping("deal/{dealID}")
    public ResponseEntity<CreatedDealDto> deal(@PathVariable(value = "dealID") Long dealID) {
        CreatedDealDto createdDealDTO = dealService.getByID(dealID);
        return ResponseEntity.ok().body(createdDealDTO);
    }

}
