package pl.tkaczyk.scraperservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.tkaczyk.scraperservice.service.CompanyService;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {CompanyController.class})
class CompanyControllerTest {

    @MockitoBean
    private CompanyService companyService;

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("Should add company and return dto object")
    void shouldAddCompanyAndReturnDtoObject(){
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/companies/addCompany"))
    }

}