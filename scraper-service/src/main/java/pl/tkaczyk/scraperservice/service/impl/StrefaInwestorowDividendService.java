package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;
import pl.tkaczyk.scraperservice.service.SnapshotProvider;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrefaInwestorowDividendService {

    private final HtmlDocumentFetcher htmlDocumentFetcher;
    private final StrefaInwestorowParser parser;


    public Optional<DividendAnnouncementDto> makeSnapshot(String tickerName)
    {
        int year = LocalDate.now().getYear();
        Document document = htmlDocumentFetcher.getDocument("https://strefainwestorow.pl/dane/dywidendy/" + year);

        Elements rows = document.select("table.table-dividends-desktop tbody tr");


        for (Element row : rows) {

            Elements tds = row.select("td");

            if (tds.size() < 7) continue;

            if (tds.get(1).text().equalsIgnoreCase(tickerName)) {
                return Optional.ofNullable(parser.parseStock(tds));
            }
        }
        return Optional.empty();
    }
}
