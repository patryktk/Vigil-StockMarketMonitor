package pl.tkaczyk.scraperservice;

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
import pl.tkaczyk.scraperservice.model.StockSnapshot;
import pl.tkaczyk.scraperservice.repository.CompanyRepository;
import pl.tkaczyk.scraperservice.repository.StockSnapshotRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Import(TestAuditingConfig.class)
@ImportAutoConfiguration(FlywayConfig.class)
public class StockSnapshotRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    StockSnapshotRepository stockSnapshotRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    void shouldSaveAndRetrieveSnapshot() {
        Company company = new Company(null, "Tesla", "TSLA");
        Company savedCompany = companyRepository.save(company);

        StockSnapshot snapshot = new StockSnapshot();
        snapshot.setCompany(savedCompany);
        snapshot.setPrice(new BigDecimal("142.50"));
        snapshot.setPriceToEarnings(new BigDecimal("18.20"));
        snapshot.setPriceToBookValue(new BigDecimal("3.50"));

        stockSnapshotRepository.save(snapshot);

        stockSnapshotRepository.flush();
        StockSnapshot found = stockSnapshotRepository.findById(snapshot.getId()).orElseThrow();

        assertThat(found.getId()).isNotNull();
        assertThat(found.getPrice()).isEqualByComparingTo("142.50");        // isEqualByComparingTo dla BigDecimal!
        assertThat(found.getPriceToEarnings()).isEqualByComparingTo("18.20");
        assertThat(found.getCompany().getTicker()).isEqualTo("TSLA");
    }

}
