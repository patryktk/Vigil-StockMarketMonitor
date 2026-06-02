package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class StrefaInwestorowParser {

    public DividendAnnouncementDto parseStock(Elements tds) {

        DividendAnnouncementDto dividendAnnouncementDto = new DividendAnnouncementDto();

        String company = tds.get(0).text();   // MOL, XTB itd.
        String ticker = tds.get(1).text();   // XTB
        String name = tds.get(2).text();   // XTB SA
        String exDate = tds.get(3).text();   // 11.06.2026 Ostatni dzień, w którym można kupić akcje z prawem do dywidendy
        String yield = tds.get(4).text();   // 3.93% Stopa dywidendy
        String dividendYield = tds.get(5).text();   // 4.07 zł Dywidenda na akcję
        String payDate = tds.get(6).text();   // 24.06.2026 Dzień wypłaty dywidendy

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        dividendAnnouncementDto.setAmount_price(getBigDecimal(dividendYield));
        dividendAnnouncementDto.setPayDate(LocalDate.parse(payDate, formatter));
        dividendAnnouncementDto.setLastDateToBuy(LocalDate.parse(exDate, formatter));
        dividendAnnouncementDto.setDividendYield_pct(getBigDecimal(yield));

        return dividendAnnouncementDto;
    }

    private BigDecimal getBigDecimal(String value) {
        return new BigDecimal(value
                .replace("zł", "")
                .replace(" ", "")
                .replace(",", ".")
                .replace("%", ""));

    }
}
