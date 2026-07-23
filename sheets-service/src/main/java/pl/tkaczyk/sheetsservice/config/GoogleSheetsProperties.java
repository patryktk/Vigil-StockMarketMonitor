package pl.tkaczyk.sheetsservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.google.sheet")
@Data
public class GoogleSheetsProperties {
    private String spreadsheetId;
    private String tickerRange;
    private String saveRangeName;
    private Integer saveRangeStartColNumber;
    private String applicationName = "MyApp";
}
