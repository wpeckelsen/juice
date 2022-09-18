//package nl.wessel.juice.B.BusinessLogic.Service;
//
//import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
//import nl.wessel.juice.B.BusinessLogic.Model.*;
//import nl.wessel.juice.C.Repository.DomainRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(DomainService.class)
//class DealServiceTest {
//
//    @Autowired
//    DealService dealService;
//
//    @MockBean
//    DomainRepo domainRepo;
//
//    @Mock
//    Deal deal;
//
//    @BeforeEach
//    void setup(){
//        Publisher publisher = new Publisher();
//        Customer customer = new Customer();
//        Domain domain = new Domain();
//        Deal deal = new Deal();
//        Bid bid = new Bid();
//
//        deal.setBid(bid);
//        deal.setDomain(domain);
//        deal.setCustomer(customer);
//        deal.setPublisher(publisher);
//
//
//    }
//
//    @Test
//    @DisplayName("a Deal without a Domain throws exception")
//    void newDeal() {
//        CreateDeal createDeal = new CreateDeal();
//
//        deal.setDomain(null);
//
//
//        ResponseEntity<?> responseEntity = dealService.newDeal(createDeal,, );
//
//
//    }
//}