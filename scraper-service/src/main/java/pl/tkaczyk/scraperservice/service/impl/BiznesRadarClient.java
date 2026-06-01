package pl.tkaczyk.scraperservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BiznesRadarClient {

    private HtmlDocumentFetcher fetcher;

    public Map<String, Document> makeSnapshot(String ticker) {

        Document financialData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-wartosci-rynkowej/" + ticker);
        Document rentData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-rentownosci/" + ticker);
        Document debtData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-zadluzenia/" + ticker);
        Document flowData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-plynnosci/" + ticker);
        Document bilansData = fetcher.getDocument("https://www.biznesradar.pl/raporty-finansowe-bilans/" + ticker);
        Document raportBiznes = fetcher.getDocument(
                "https://www.biznesradar.pl/raporty-finansowe-rachunek-zyskow-i-strat/" + ticker + ",Q");
        Document raportFlow = fetcher.getDocument(
                "https://www.biznesradar.pl/raporty-finansowe-przeplywy-pieniezne/" + ticker + ",Q");

        Map<String, Document> mapa = new HashMap<>();
        mapa.put("financialData", financialData);
        mapa.put("rentData", rentData);
        mapa.put("debtData", debtData);
        mapa.put("flowData", flowData);
        mapa.put("bilansData", bilansData);
        mapa.put("raportBiznes", raportBiznes);
        mapa.put("raportFlow", raportFlow);

        return mapa;
    }

}
