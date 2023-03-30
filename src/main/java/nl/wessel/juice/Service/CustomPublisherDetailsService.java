package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;
import nl.wessel.juice.Model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomPublisherDetailsService implements UserDetailsService {




    private final PublisherService publisherService;


    @Autowired
    public CustomPublisherDetailsService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }


//    this method returns a new User as Publisher if in controller layer a Publisher is picked over customer
    @Override
    public UserDetails loadUserByUsername(String username) {
        CreatedPublisherDto createdPublisherDto = publisherService.getPublisher(username);
        String password = createdPublisherDto.getPassword();

        Set<Authority> authorities = createdPublisherDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new User(username, password, grantedAuthorities);
    }

}