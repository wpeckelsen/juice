package nl.wessel.juice.Controller;

import nl.wessel.juice.DTO.Bid.CreatedBid;
import nl.wessel.juice.Security.Payload.AuthenticationRequest;
import nl.wessel.juice.Security.Utils.JwtUtil;
import nl.wessel.juice.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@RestController
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final BidService bidService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, BidService bidService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.bidService = bidService;
    }




    @GetMapping(value = "authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }


    @PostMapping(value = "authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);


//           this returns a JSON body containing a jwt token
//      return ResponseEntity.ok(new AuthenticationResponse(jwt));



//        this returns a jwt token as a String
        return ResponseEntity.ok(jwt);
    }


    @GetMapping("bidlisttest")
    public ResponseEntity<List<CreatedBid>> bidList(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);
        final Boolean valid = jwtUtil.validateToken(jwt, userDetails);



        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        List<CreatedBid> createdBidList;
        createdBidList = bidService.getList();
        return ResponseEntity.ok().body(createdBidList);
    }

}