//package nl.wessel.juice.B.BusinessLogic.Service;
//import nl.wessel.juice.B.BusinessLogic.DTO.CustomerRepo.CustomerDto;
//import nl.wessel.juice.B.BusinessLogic.Model.Authority;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Service
//public class CustomCustomerDetailsService implements UserDetailsService {
//
//
//
//    private final CustomerService customerService;
//
//
//    @Autowired
//    public CustomCustomerDetailsService(CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//
//        CustomerDto customerDto = customerService.getCustomer(username);
//
//
//        String password = customerDto.getPassword();
//
//        Set<Authority> authorities = customerDto.getAuthorities();
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (Authority authority: authorities) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
//    }
//
//}
