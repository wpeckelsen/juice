package nl.wessel.juice.Controller;

import nl.wessel.juice.Security.Utils.JwtUtil;
import nl.wessel.juice.Service.BidService;
import nl.wessel.juice.Service.CustomCustomerDetailsService;
import nl.wessel.juice.Service.CustomerService;
import nl.wessel.juice.Service.DealService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc
class AdminControllerTest {




    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomCustomerDetailsService customCustomerDetailsService;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    CustomerService customerService;

    @MockBean
    DealService dealService;

    @MockBean
    BidService bidService;


    @Test
    @DisplayName("Returns abcxyz as response body")
    @WithMockUser(username = "ADMIN", password = "ADMIN", roles = {"ADMIN"} )
    void abcxyz() throws Exception {
        this.mvc.
                perform((RequestBuilder) get("/juice/admin/abcxyz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().string("abcxyz"));
    }


}