package nl.wessel.juice.B.BusinessLogic.Service;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.C.Repository.DomainRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@WebMvcTest(DomainService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DomainServiceTest {

    @Autowired
    DomainService domainService;

    @MockBean
    DomainRepo domainRepo;


    @Mock
    Domain domain;


    @Test
    void getList() {
        Domain domain = new Domain();

        Mockito.
                when(domainRepo.findDomainsByName(domain.getName()))
                .thenReturn((List<Domain>) domain);

        String name = "frikandellen-thee.nl";
        String expected = "frikandellen-thee.nl";

        Domain found = domainService.
    }
}
