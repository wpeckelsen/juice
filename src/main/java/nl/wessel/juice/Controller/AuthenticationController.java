package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Customer.CustomerDto;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
import nl.wessel.juice.Exception.UsernameNotFoundException;
import nl.wessel.juice.Security.Payload.AuthenticationRequest;
import nl.wessel.juice.Security.Payload.AuthenticationResponse;
import nl.wessel.juice.Security.Utils.JwtUtil;
import nl.wessel.juice.Service.CustomCustomerDetailsService;
import nl.wessel.juice.Service.CustomPublisherDetailsService;
import nl.wessel.juice.Service.CustomerService;
import nl.wessel.juice.Service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;


@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomCustomerDetailsService customCustomerDetailsService;
    private final CustomPublisherDetailsService customPublisherDetailsService;
    private final CustomerService customerService;
    private final PublisherService publisherService;
    private final JwtUtil jwtUtil;


    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, CustomCustomerDetailsService customCustomerDetailsService, CustomPublisherDetailsService customPublisherDetailsService, CustomerService customerService, PublisherService publisherService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customCustomerDetailsService = customCustomerDetailsService;
        this.customPublisherDetailsService = customPublisherDetailsService;
        this.customerService = customerService;
        this.publisherService = publisherService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("customer")
    public ResponseEntity<Object> newCustomer(@RequestBody CustomerDto customerDto) {
        String newCustomerName = customerService.newCustomer(customerDto);
        customerService.addAuthority(newCustomerName, "ROLE_CUSTOMER");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newCustomerName}")
                .buildAndExpand(newCustomerName).toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping("publisher")
    public ResponseEntity<Object> newPublisher(@RequestBody PublisherDto publisherDto) {
        String newPublisherName = publisherService.newPublisher(publisherDto);
        publisherService.addAuthority(newPublisherName, "ROLE_PUBLISHER");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{newPublisherName}")
                .buildAndExpand(newPublisherName).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "authenticated")
    public ResponseEntity<Object> authenticated(Principal principal) {
        return ResponseEntity.ok().body(principal);
    }


    @GetMapping(value = "authenticated/string")
    public ResponseEntity<String> string(Principal principal) {
        return ResponseEntity.ok().body(principal.getName());
    }


    @PostMapping(value = "authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String type = authenticationRequest.getType();

        if (type.equalsIgnoreCase("Customer")) {
            final UserDetails userDetails = customCustomerDetailsService.loadUserByUsername(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }

        if (type.equalsIgnoreCase("Admin")) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final UserDetails userDetails = customCustomerDetailsService.loadUserByUsername(username);
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }

//        seems as if authenticationRequest is automatically a Customer, so even if application goes into this block below, it does so as Customer.
//        when the application tries to find the user in the Publisher Repository, it cannot find it since Customers are not stored in a Publisher Repository
        if (type.equalsIgnoreCase("Publisher")) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final UserDetails userDetails = customPublisherDetailsService.loadUserByUsername(username);
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            throw new UsernameNotFoundException(authenticationRequest.getUsername());
        }

    }


}