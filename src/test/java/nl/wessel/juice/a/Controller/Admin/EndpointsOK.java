package nl.wessel.juice.a.Controller.Admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminContr.class)
@AutoConfigureMockMvc
class EndpointsOK {

    @Autowired
    MockMvc mvc;


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
    void getdeals() {
    }

    @Test
    @DisplayName("returns HTTP status code 200 for endpoint: a deal by ID")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void getDealByID() throws Exception {
        mvc.perform(get("/juice/admin/getdealbyid/{dealID}"))
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