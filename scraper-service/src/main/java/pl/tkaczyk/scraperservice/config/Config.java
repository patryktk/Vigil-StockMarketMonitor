package pl.tkaczyk.scraperservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableResilientMethods
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class Config {
}
