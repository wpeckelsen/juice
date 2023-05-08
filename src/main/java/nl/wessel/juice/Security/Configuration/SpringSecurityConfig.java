package nl.wessel.juice.Security.Configuration;

import nl.wessel.juice.Security.Filter.JwtRequestFilter;
import nl.wessel.juice.Service.CustomCustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    CustomCustomerDetailsService customCustomerDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customCustomerDetailsService);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/juice/common/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/juice/common/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/juice/common/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/juice/common/**").hasRole("CUSTOMER")

                .antMatchers(HttpMethod.POST, "/juice/customer/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/juice/customer/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/juice/customer/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/juice/customer/**").hasRole("CUSTOMER")

                .antMatchers(HttpMethod.POST, "/juice/common/**").hasRole("PUBLISHER")
                .antMatchers(HttpMethod.GET, "/juice/common/**").hasRole("PUBLISHER")
                .antMatchers(HttpMethod.PUT, "/juice/common/**").hasRole("PUBLISHER")
                .antMatchers(HttpMethod.DELETE, "/juice/common/**").hasRole("PUBLISHER")


                .antMatchers(HttpMethod.POST, "/juice/publisher/**").hasRole("PUBLISHER")
                .antMatchers(HttpMethod.GET, "/juice/publisher/**").hasRole("PUBLISHER")
                .antMatchers(HttpMethod.PUT, "/juice/publisher/**").hasRole("PUBLISHER")
                .antMatchers(HttpMethod.DELETE, "/juice/publisher/**").hasRole("PUBLISHER")

                .antMatchers(HttpMethod.POST, "/juice/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/juice/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/juice/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/juice/admin/**").hasRole("ADMIN")

                .antMatchers("/authentication").permitAll()
                .antMatchers("/authenticated").authenticated()


                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}