package nl.wessel.juice.a.Controller.Customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.wessel.juice.B.BusinessLogic.Model.Customer;
import nl.wessel.juice.B.BusinessLogic.Security.Utils.JwtUtil;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import nl.wessel.juice.a.Controller.CustomerContr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerContr.class)
@AutoConfigureMockMvc
class EndpointsCust {

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
    @MockBean
    DomainService domainService;
    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("returns HTTP status code 201 for endpoint: new Customer")
    @WithMockUser(username = "CUSTOMER", roles = "CUSTOMER", password = "CUSTOMER")
    void newCustomer() throws Exception {

        Customer customer = new Customer();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customer);


        mvc.perform(
                post("/juice/customer/newcustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)).andExpect(status()
                .isCreated());
    }
}