package nl.wessel.juice.a.Controller.Customer;

import nl.wessel.juice.B.BusinessLogic.Security.Utils.JwtUtil;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomUserDetailsService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import nl.wessel.juice.B.BusinessLogic.Service.DealService;
import nl.wessel.juice.a.Controller.Admin.AdminContr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import nl.wessel.juice.B.BusinessLogic.Security.Utils.JwtUtil;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomUserDetailsService;
import nl.wessel.juice.B.BusinessLogic.Service.DealService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;

@WebMvcTest(AdminContr.class)
@AutoConfigureMockMvc
class EndpointsCust {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    CustomerService customerService;

    @MockBean
    DealService dealService;

    @MockBean
    BidService bidService;



//    @Test
//    @DisplayName("returns HTTP status code 201 for endpoint: new Customer")
//    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
//    void newCustomer() throws Exception{
//                mvc.perform(get("juice/customer/newcustomer"))
//                .andExpect(status().is(201));
//    }


}