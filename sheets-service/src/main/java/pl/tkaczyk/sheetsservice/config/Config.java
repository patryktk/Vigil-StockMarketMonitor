package pl.tkaczyk.sheetsservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.tkaczyk.sheetsservice.GoogleSheetsProperties;

@EnableConfigurationProperties(
        {GoogleSheetsProperties.class})
@Configuration
public class Config {
}
