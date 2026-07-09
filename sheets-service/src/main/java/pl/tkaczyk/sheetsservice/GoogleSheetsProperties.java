package pl.tkaczyk.sheetsservice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.google.sheet")
@Data
@Component
public class GoogleSheetsProperties {
    private String spreadsheetId;
    private String tickerRange;
    private String saveRangeName;
    private String saveRangeStartColNumber;
    private String applicationName = "MyApp";
}
