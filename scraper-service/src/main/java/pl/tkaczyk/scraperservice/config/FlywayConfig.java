package pl.tkaczyk.scraperservice.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        // Ręczne zbudowanie i skonfigurowanie Flywaya
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        // Ręczne wymuszenie uruchomienia migracji w momencie tworzenia beana!
        flyway.migrate();

        return flyway;
    }
}