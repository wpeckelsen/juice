package nl.wessel.juice.a.Controller.Customer;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;



@WebMvcTest(CustomerContr.class)
@AutoConfigureMockMvc
class VernacularIsDutch {

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




    @Test
    @DisplayName("Gives back Dutch as vernacular in Bid")
    @WithMockUser(username = "ADMIN", roles = "ADMIN", password = "ADMIN")
    void bidByID() throws Exception {


        mvc
                .perform(MockMvcRequestBuilders.get("/juice/customer/newbid/wimkroket"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vernacular", is("Dutch")));
    }
}