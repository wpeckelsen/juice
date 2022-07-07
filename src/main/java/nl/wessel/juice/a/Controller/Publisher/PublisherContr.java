package nl.wessel.juice.a.Controller.Publisher;

import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import nl.wessel.juice.B.BusinessLogic.Service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/juice/publisher")
public class PublisherContr {


    CustomerService customerService;
    DomainService domainService;

    @Autowired
    public PublisherContr(CustomerService customerService, DomainService domainService) {
        this.customerService = customerService;
        this.domainService = domainService;
    }


    @PostMapping("/new")
    public ResponseEntity<CustomerDto> newPublisher(@RequestBody CustomerDto dto) {
        String newCustomerName = customerService.createCustomer(dto);
        customerService.addAuthority(newCustomerName, "ROLE_PUBLISHER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("/domain/new")
    public ResponseEntity<CreatedDomain> newDomain(@RequestBody CreateDomain domain) {
        final CreatedDomain createdDomain = domainService.newDomain(domain);
        return ResponseEntity.ok().body(createdDomain);
    }
}
