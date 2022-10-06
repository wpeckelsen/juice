package nl.wessel.juice.a.Controller.Customer;

import nl.wessel.juice.B.BusinessLogic.Security.Utils.JwtUtil;
import nl.wessel.juice.B.BusinessLogic.Service.*;
import nl.wessel.juice.a.Controller.CustomerContr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerContr.class)
//@AutoConfigureMockMvc
class VernacularIsDutch {

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
    @DisplayName("Gives back 550 as Words in Bid")
    @WithMockUser(username = "CUSTOMER1", roles = "CUSTOMER", password = "CUSTOMER1")
    void bidByID() throws Exception {
        this.mvc.
                perform((RequestBuilder) get("/juice/customer/bidbyid/40")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.words", is(550)))
                .andReturn().getResponse().getContentAsString();

    }
}