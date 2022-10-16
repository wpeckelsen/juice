package nl.wessel.juice.a.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.wessel.juice.B.BusinessLogic.Model.Customer;
import nl.wessel.juice.B.BusinessLogic.Security.Utils.JwtUtil;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerContr.class)
@AutoConfigureMockMvc
class CustomerContrTest {

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
    @MockBean
    PublisherService publisherService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("returns HTTP status code 201 for endpoint: new Customer")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
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