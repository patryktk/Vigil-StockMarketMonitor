package pl.tkaczyk.scraperservice.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Utils {


    public BigDecimal getBigDecimal(String value) {
        return new BigDecimal(value
                .replace("zł", "")
                .replace(" ", "")
                .replace(",", ".")
                .replace("%", ""));

    }
}
