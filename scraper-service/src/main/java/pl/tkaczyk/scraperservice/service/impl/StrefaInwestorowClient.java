package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;

@Service

public class StrefaInwestorowClient {

    private final HtmlDocumentFetcher htmlDocumentFetcher;

    public StrefaInwestorowClient(HtmlDocumentFetcher htmlDocumentFetcher) {
        this.htmlDocumentFetcher = htmlDocumentFetcher;
    }


    public void makeSnapshot(String tickerName)
    {
        Document document = htmlDocumentFetcher.getDocument("https://strefainwestorow.pl/dane/dywidendy/2026");

        Elements rows = document.select("table.table-dividends-desktop tbody tr");


        for (Element row : rows) {

            Elements tds = row.select("td");

            if (tds.size() < 6) continue;

            String company = tds.get(0).text();   // MOL, XTB itd.
            String ticker  = tds.get(1).text();   // XTB
            String name    = tds.get(2).text();   // XTB SA
            String exDate  = tds.get(3).text();   // 11.06.2026
            String yield   = tds.get(4).text();   // 3.93%
            String dividend= tds.get(5).text();   // 4.07 zł
            String payDate = tds.get(6).text();   // 24.06.2026

            if (tickerName.equalsIgnoreCase(ticker)) {
                System.out.println("Znaleziono XTB!");
                System.out.println(row.text()); // całe dane z wiersza
                break;
            }
        }
    }
}
