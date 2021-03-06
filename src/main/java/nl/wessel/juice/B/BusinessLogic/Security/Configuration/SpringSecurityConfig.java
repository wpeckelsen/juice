package nl.wessel.juice.B.BusinessLogic.Security.Configuration;


import nl.wessel.juice.B.BusinessLogic.Security.Filter.JwtRequestFilter;
import nl.wessel.juice.B.BusinessLogic.Service.CustomUserDetailsService;
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



    CustomUserDetailsService customUserDetailsService;
    JwtRequestFilter jwtRequestFilter;


    @Autowired
    public SpringSecurityConfig(/*boolean disableDefaults,*/ CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        /*super(disableDefaults);*/
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
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

                .antMatchers(HttpMethod.POST, "/juice/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/juice/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/juice/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/juice/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/juice/customer/**").hasRole("CLIENT")
                .antMatchers(HttpMethod.GET, "/juice/customer/**").hasRole("CLIENT")
                .antMatchers(HttpMethod.PUT, "/juice/customer/**").hasRole("CLIENT")
                .antMatchers(HttpMethod.DELETE, "/juice/customer/**").hasRole("CLIENT")

//                .antMatchers(HttpMethod.POST, "/juice/customer/bid/new").hasRole("CLIENT")
//                .antMatchers(HttpMethod.GET, "/juice/customer/list/domain").hasRole("CLIENT")
//                .antMatchers(HttpMethod.POST, "/juice/deal/new/**").hasRole("CLIENT")


                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                /*alleen dit punt mag toegankelijk zijn voor niet ingelogde gebruikers*/

                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}