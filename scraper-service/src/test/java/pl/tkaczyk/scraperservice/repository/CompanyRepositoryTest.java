package pl.tkaczyk.scraperservice.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import pl.tkaczyk.scraperservice.config.FlywayConfig;
import pl.tkaczyk.scraperservice.config.TestAuditingConfig;
import pl.tkaczyk.scraperservice.model.Company;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Import(TestAuditingConfig.class)
@ImportAutoConfiguration(FlywayConfig.class)
class CompanyRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should save and retrieve company")
    void shouldSaveAndRetrieveCompany() {
        Company company = Company.builder().id(null).companyName("TSLA SA").ticker("TSL").build();

        Company savedCompany = companyRepository.save(company);

        assertNotNull(savedCompany.getId());
        assertEquals("TSLA SA", savedCompany.getCompanyName());
        assertEquals("TSL", savedCompany.getTicker());
    }

    @Test
    @DisplayName("Should throw exception unique constraint on ticker")
    void shouldThrowExceptionUniqueConstraintOnTicker() {
        Company firstCompany = Company.builder().id(null).companyName("TSLA SA").ticker("TSL").build();

        Company secondCompany = Company.builder().id(null).companyName("TS SA").ticker("TSL").build();

        companyRepository.save(firstCompany);

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> companyRepository.save(secondCompany));
    }

    @Test
    @DisplayName("Should throw exception null constraint on ticker")
    void shouldThrowExceptionNullConstraintOnTicker() {
        Company company = Company.builder().id(null).companyName("TSLA SA").ticker(null).build();

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> companyRepository.save(company));
    }

    @Test
    @DisplayName("Should find company by ticker")
    void shouldFindCompanyByTicker() {
        String ticker = "TSL";
        Company company = Company.builder().id(null).companyName("TSLA SA").ticker(ticker).build();
        companyRepository.save(company);

        entityManager.flush();

        companyRepository.findByTicker(ticker).ifPresent(
                assertCompany -> assertEquals(ticker, assertCompany.getTicker())
        );
    }

    @Test
    @DisplayName("Should return Optional.empty find company by ticker")
    void shouldReturnOptionalEmptyFindCompanyByTicker() {
        String ticker = "TSL";
        Company company = Company.builder().id(null).companyName("TSLA SA").ticker("TSLA").build();
        companyRepository.save(company);

        entityManager.flush();


        org.assertj.core.api.Assertions.assertThat(companyRepository.findByTicker(ticker).isEmpty());
    }
}