package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Customer.CreatedCustomerDto;
import nl.wessel.juice.Model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class CustomCustomerDetailsService implements UserDetailsService {



    private final CustomerService customerService;


    @Autowired
    public CustomCustomerDetailsService(@Lazy CustomerService customerService) {
        this.customerService = customerService;
    }

    //    this method returns a new User as Customer if in controller layer a Customer is picked over a Publisher
    @Override
    public UserDetails loadUserByUsername(String username) {
        CreatedCustomerDto createdCustomerDto = customerService.getCustomer(username);
        String password = createdCustomerDto.getPassword();
        Set<Authority> authorities = createdCustomerDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return new User(username, password, grantedAuthorities);

    }


}