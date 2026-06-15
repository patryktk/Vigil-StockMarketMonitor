package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowParser;
import pl.tkaczyk.scraperservice.utils.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrefaInwestorowParserImpl implements StrefaInwestorowParser {

    private static final String CSS_QUERY = "table.table-dividends-desktop tbody tr";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Utils utils;

    @Override
    public Optional<DividendAnnouncementDto> parse(StrefaInwestorowDocument document, String ticker) {
        Elements rows = document.dividendAnnouncementDocument().select(CSS_QUERY);
        for (Element row : rows) {

            Elements tds = row.select("td");

            int expectedColumnCount = 7;
            if (tds.size() < expectedColumnCount)
                continue;

            int secondColumnIndex = 1;
            if (tds.get(secondColumnIndex).text().equalsIgnoreCase(ticker)) {
                return Optional.of(parseStock(tds));
            }
        }
        return Optional.empty();
    }

    private DividendAnnouncementDto parseStock(Elements tds) {

        DividendAnnouncementDto dividendAnnouncementDto = new DividendAnnouncementDto();

//        String company = tds.get(0).text();   // MOL, XTB itd.
//        String ticker = tds.get(1).text();   // XTB
//        String name = tds.get(2).text();   // XTB SA
        String exDate = tds.get(3).text();   // 11.06.2026 Ostatni dzień, w którym można kupić akcje z prawem do dywidendy
        String yield = tds.get(4).text();   // 3.93% Stopa dywidendy
        String dividendAmountPerStock = tds.get(5).text();   // 4.07 zł Dywidenda na akcję
        String payDate = tds.get(6).text();   // 24.06.2026 Dzień wypłaty dywidendy


        dividendAnnouncementDto.setAmountPerStock(utils.getBigDecimal(dividendAmountPerStock));
        dividendAnnouncementDto.setPayDate(LocalDate.parse(payDate, formatter));
        dividendAnnouncementDto.setLastDateToBuy(LocalDate.parse(exDate, formatter));
        dividendAnnouncementDto.setDividendYield_pct(utils.getBigDecimal(yield));

        return dividendAnnouncementDto;
    }
}
