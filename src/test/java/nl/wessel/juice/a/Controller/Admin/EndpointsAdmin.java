package nl.wessel.juice.a.Controller.Admin;

import nl.wessel.juice.B.BusinessLogic.Security.Utils.JwtUtil;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomUserDetailsService;
import nl.wessel.juice.B.BusinessLogic.Service.CustomerService;
import nl.wessel.juice.B.BusinessLogic.Service.DealService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminContr.class)
@AutoConfigureMockMvc
class EndpointsAdmin {

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


    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: list of customers")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void getCustomers() throws Exception {
        mvc.perform(get("/juice/admin/getcustomers"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: list of deals")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void getdeals() throws Exception{
        mvc.perform(get("/juice/admin/getdeals"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: a deal by ID")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void getDealByID() throws Exception {
        mvc.perform(get("/juice/admin/getdealbyid/42"))
                .andExpect(status().isOk());
    }

    @Test
    void updateBid() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteBid() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void deleteDeal() {
    }

    @Test
    void abcxyz() {
    }
}