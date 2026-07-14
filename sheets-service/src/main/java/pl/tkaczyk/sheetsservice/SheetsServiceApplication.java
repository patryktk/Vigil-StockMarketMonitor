package pl.tkaczyk.sheetsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.tkaczyk.sheetsservice.config.GoogleSheetsProperties;

@SpringBootApplication
@EnableConfigurationProperties(GoogleSheetsProperties.class)
public class SheetsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SheetsServiceApplication.class, args);
	}

}
