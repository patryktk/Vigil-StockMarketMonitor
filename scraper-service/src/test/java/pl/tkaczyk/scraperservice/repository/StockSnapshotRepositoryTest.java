package pl.tkaczyk.scraperservice.repository;

import jakarta.persistence.EntityManager;
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
import pl.tkaczyk.scraperservice.config.TestAuditingConfig;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.StockSnapshot;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Import(TestAuditingConfig.class)
public class StockSnapshotRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    StockSnapshotRepository stockSnapshotRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should save and retrieve snapshot")
    void shouldSaveAndRetrieveSnapshot() {
        Company company = new Company(null, "Tesla", "TSLA");
        Company savedCompany = companyRepository.save(company);

        StockSnapshot snapshot = new StockSnapshot();
        snapshot.setCompany(savedCompany);
        snapshot.setPrice(new BigDecimal("142.50"));
        snapshot.setPriceToEarnings(new BigDecimal("18.20"));
        snapshot.setPriceToBookValue(new BigDecimal("3.50"));
        snapshot.setPriceToSales(new BigDecimal("5.40"));
        snapshot.setPriceToOperatingIncome(new BigDecimal("15.80"));

        snapshot.setEvToSales(new BigDecimal("4.90"));
        snapshot.setEvToEbit(new BigDecimal("14.30"));
        snapshot.setEvToEbitda(new BigDecimal("11.70"));

        snapshot.setEarningsPerShare(new BigDecimal("7.83"));

        snapshot.setTotalDebt(new BigDecimal("25000000000"));
        snapshot.setNetDebt(new BigDecimal("18000000000"));
        snapshot.setNetFinancialDebt(new BigDecimal("17500000000"));

        snapshot.setDebtToEquity(new BigDecimal("0.65"));
        snapshot.setNetDebtToEbitda(new BigDecimal("1.90"));

        snapshot.setReturnOnEquity_pct(new BigDecimal("22.40"));
        snapshot.setReturnOnAssets_pct(new BigDecimal("11.80"));

        snapshot.setOperatingMargin_pct(new BigDecimal("18.60"));
        snapshot.setNetProfitMargin_pct(new BigDecimal("15.20"));
        snapshot.setSalesMargin_pct(new BigDecimal("21.30"));

        snapshot.setCurrentRatio(new BigDecimal("1.75"));

        snapshot.setRevenue(new BigDecimal("96773000000"));
        snapshot.setGrossProfit(new BigDecimal("17660000000"));
        snapshot.setEbit(new BigDecimal("14520000000"));
        snapshot.setNetIncome(new BigDecimal("12230000000"));
        snapshot.setDepreciationAndAmortization(new BigDecimal("3850000000"));

        snapshot.setEquity(new BigDecimal("73450000000"));

        stockSnapshotRepository.save(snapshot);

        stockSnapshotRepository.flush();
        StockSnapshot found = stockSnapshotRepository.findById(snapshot.getId()).orElseThrow();

        assertThat(found.getId()).isNotNull();
        assertThat(found.getPrice()).isEqualByComparingTo("142.50");
        assertThat(found.getPriceToEarnings()).isEqualByComparingTo("18.20");
        assertThat(found.getPriceToBookValue()).isEqualByComparingTo("3.50");
        assertThat(found.getPriceToSales()).isEqualByComparingTo("5.40");
        assertThat(found.getPriceToOperatingIncome()).isEqualByComparingTo("15.80");

        assertThat(found.getEvToSales()).isEqualByComparingTo("4.90");
        assertThat(found.getEvToEbit()).isEqualByComparingTo("14.30");
        assertThat(found.getEvToEbitda()).isEqualByComparingTo("11.70");

        assertThat(found.getEarningsPerShare()).isEqualByComparingTo("7.83");

        assertThat(found.getTotalDebt()).isEqualByComparingTo("25000000000");
        assertThat(found.getNetDebt()).isEqualByComparingTo("18000000000");
        assertThat(found.getNetFinancialDebt()).isEqualByComparingTo("17500000000");

        assertThat(found.getDebtToEquity()).isEqualByComparingTo("0.65");
        assertThat(found.getNetDebtToEbitda()).isEqualByComparingTo("1.90");

        assertThat(found.getReturnOnEquity_pct()).isEqualByComparingTo("22.40");
        assertThat(found.getReturnOnAssets_pct()).isEqualByComparingTo("11.80");

        assertThat(found.getOperatingMargin_pct()).isEqualByComparingTo("18.60");
        assertThat(found.getNetProfitMargin_pct()).isEqualByComparingTo("15.20");
        assertThat(found.getSalesMargin_pct()).isEqualByComparingTo("21.30");

        assertThat(found.getCurrentRatio()).isEqualByComparingTo("1.75");

        assertThat(found.getRevenue()).isEqualByComparingTo("96773000000");
        assertThat(found.getGrossProfit()).isEqualByComparingTo("17660000000");
        assertThat(found.getEbit()).isEqualByComparingTo("14520000000");
        assertThat(found.getNetIncome()).isEqualByComparingTo("12230000000");
        assertThat(found.getDepreciationAndAmortization()).isEqualByComparingTo("3850000000");

        assertThat(found.getEquity()).isEqualByComparingTo("73450000000");

        assertThat(found.getCompany().getTicker()).isEqualTo("TSLA");

        assertThat(found.getCreatedAt()).isNotNull();
        assertThat(found.getCreatedBy()).isEqualTo("user_test");
        assertThat(found.getUpdatedAt()).isNull();
        assertThat(found.getUpdatedBy()).isNull();
    }

    @Test
    @DisplayName("Should save last modified date")
    void shouldSaveLastModifiedDate() {
        Company company = new Company(null, "Tesla", "TSLA");
        Company savedCompany = companyRepository.save(company);

        StockSnapshot snapshot = new StockSnapshot();
        snapshot.setCompany(savedCompany);
        snapshot.setPrice(new BigDecimal("142.50"));
        snapshot.setPriceToEarnings(new BigDecimal("18.20"));

        StockSnapshot saveStock = stockSnapshotRepository.save(snapshot);
        stockSnapshotRepository.flush();
        entityManager.clear();

        StockSnapshot found = stockSnapshotRepository.findById(saveStock.getId()).orElseThrow();
        found.setPrice(new BigDecimal("142.51"));
        StockSnapshot result = stockSnapshotRepository.save(found);
        stockSnapshotRepository.flush();

        assertThat(saveStock.getCreatedAt()).isEqualTo(result.getCreatedAt());
        assertThat(saveStock.getCreatedBy()).isEqualTo(result.getCreatedBy());
        assertThat(result.getUpdatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isAfter(saveStock.getCreatedAt());
        assertThat(result.getUpdatedBy()).isEqualTo("user_test");
    }

    @Test
    @DisplayName("Should thrown exception when company is null")
    void shouldThrowExceptionWhenCompanyIsNull() {
        StockSnapshot snapshot = new StockSnapshot();
        snapshot.setPrice(new BigDecimal("142.50"));
        snapshot.setPriceToEarnings(new BigDecimal("18.20"));
        assertThatException().isThrownBy(() -> stockSnapshotRepository.save(snapshot));
    }
}
