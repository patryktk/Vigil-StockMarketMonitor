package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowParser;
import pl.tkaczyk.scraperservice.utils.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrefaInwestorowParserImpl implements StrefaInwestorowParser {

    public static final String CSS_QUERY = "table.table-dividends-desktop tbody tr";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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

    /***
     * Parse stock data from table row
     * 0 - company eg. MOL
     * 1 - ticker eg. XTB
     * 2 - name eg. XTB SA
     * 3 - lastDateToBuy eg. 11.06.2026
     * 4 - yield eg. 3.93%
     * 5 - dividendAmountPerStock eg. 4.07 zł
     * 6 - payDate eg. 24.06.2026
     * @param tds table row
     * @return DividendAnnouncementDto
     */
    private DividendAnnouncementDto parseStock(Elements tds) {

        DividendAnnouncementDto dividendAnnouncementDto = new DividendAnnouncementDto();

//        String company = tds.get(0).text();
//        String ticker = tds.get(1).text();
//        String name = tds.get(2).text();
        String lastDateToBuy = tds.get(3).text();
        String yield = tds.get(4).text();
        String dividendAmountPerStock = tds.get(5).text();
        String payDate = tds.get(6).text();


        dividendAnnouncementDto.setAmountPerStock(utils.getBigDecimal(dividendAmountPerStock));
        dividendAnnouncementDto.setPayDate(payDate.isEmpty() ? null : LocalDate.parse(payDate, formatter));
        dividendAnnouncementDto.setLastDateToBuy(lastDateToBuy.isEmpty() ? null : LocalDate.parse(lastDateToBuy, formatter));
        dividendAnnouncementDto.setDividendYield_pct(utils.getBigDecimal(yield));

        return dividendAnnouncementDto;
    }
}
