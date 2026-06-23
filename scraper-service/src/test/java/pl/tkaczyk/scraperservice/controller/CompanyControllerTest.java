package pl.tkaczyk.scraperservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.tkaczyk.scraperservice.config.FlywayConfig;
import pl.tkaczyk.scraperservice.config.TestAuditingConfig;
import pl.tkaczyk.scraperservice.model.dto.CompanyDTO;
import pl.tkaczyk.scraperservice.service.CompanyService;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CompanyController.class})
@Import(TestAuditingConfig.class)
class CompanyControllerTest {

    @MockitoBean
    private CompanyService companyService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Should add company and return dto object")
    void shouldAddCompanyAndReturnDtoObject() throws Exception {
        CompanyDTO companyDTO = CompanyDTO.builder().ticker("XTB").name("XTB SA").build();

        when(companyService.addCompany(companyDTO)).thenReturn(companyDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/companies/addCompany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(companyDTO)));
    }


    @Test
    @DisplayName("Should throw validation exception")
    void shouldThrowValidationException() throws Exception {
        CompanyDTO companyDTO = CompanyDTO.builder().ticker(null).name("XTB SA").build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/companies/addCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isBadRequest());
    }

}