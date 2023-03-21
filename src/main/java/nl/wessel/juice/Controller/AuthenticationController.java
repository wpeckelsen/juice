package nl.wessel.juice.Controller;

import nl.wessel.juice.Security.Payload.AuthenticationRequest;
import nl.wessel.juice.Security.Payload.AuthenticationResponse;
import nl.wessel.juice.Security.Utils.JwtUtil;
import nl.wessel.juice.Service.CustomCustomerDetailsService;
import nl.wessel.juice.Service.CustomPublisherDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final CustomCustomerDetailsService customCustomerDetailsService;
    private final CustomPublisherDetailsService customPublisherDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, CustomCustomerDetailsService customCustomerDetailsService, CustomPublisherDetailsService customPublisherDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customCustomerDetailsService = customCustomerDetailsService;
        this.customPublisherDetailsService = customPublisherDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping(value = "authenticated")
    public ResponseEntity<Object> authenticated(Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping(value = "authenticationCustomer")
    public ResponseEntity<?> createAuthenticationTokenCustomer(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();



        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }
        final UserDetails userDetails = customCustomerDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @PostMapping(value = "authenticationPublisher")
    public ResponseEntity<?> createAuthenticationTokenPublisher(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();



        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }
        final UserDetails userDetails = customPublisherDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }



}