package nl.wessel.juice.a.Controller.Admin;

import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/juice/admin")
public class AdminContr {

    @Autowired
    CustomerService customerService;

    public AdminContr(CustomerService customerService) {
        this.customerService = customerService;
    }



        @GetMapping(value = "/list/customers")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> dtos = customerService.getCustomers();
        return ResponseEntity.ok().body(dtos);
    }
}
