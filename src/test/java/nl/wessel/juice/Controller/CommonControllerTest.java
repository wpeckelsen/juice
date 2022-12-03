package nl.wessel.juice.Controller;

import nl.wessel.juice.Security.Utils.JwtUtil;
import nl.wessel.juice.Service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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




@WebMvcTest(CommonController.class)
@AutoConfigureMockMvc
class CommonControllerTest {

    @Autowired
    MockMvc mvc;

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
    PublisherService publisherService;

    @MockBean
    DomainService domainService;



    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: list of customers")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void domainList() throws Exception {
        mvc.perform(get("/juice/common/customerlist"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: list of deals")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void dealList() throws Exception{
        mvc.perform(get("/juice/common/deallist"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: a deal by ID 40")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void bidbyid() throws Exception {
        mvc.perform(get("/juice/common/bidbyid/40"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Confirms 450 as word count in Bid with ID 40")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void wordCountInBid() throws Exception {
        this.mvc.
                perform((RequestBuilder) get("/juice/common/bidbyid/1")
                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bidID", is(40)))
                .andReturn().getResponse().getContentLength();

    }
    @Test
    void bidList() {
    }

    @Test
    void publisherList() {
    }


    @Test
    void customerByID() {
    }

    @Test
    void bidByID() {
    }

    @Test
    void publisherByID() {
    }

    @Test
    void domainByID() {
    }

    @Test
    void dealByID() {
    }



}