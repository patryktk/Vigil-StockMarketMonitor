package pl.tkaczyk.scraperservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ScraperServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperServiceApplication.class, args);
    }

}
