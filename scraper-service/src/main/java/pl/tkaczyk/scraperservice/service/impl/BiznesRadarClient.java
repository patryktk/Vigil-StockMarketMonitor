package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;
import pl.tkaczyk.scraperservice.service.SnapshotProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BiznesRadarClient implements SnapshotProvider<BiznesRadarDocuments> {

    private final HtmlDocumentFetcher fetcher;

    @Override
    public Optional<BiznesRadarDocuments> makeSnapshot(String ticker) {

        Document financialData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-wartosci-rynkowej/" + ticker);
        Document rentData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-rentownosci/" + ticker);
        Document debtData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-zadluzenia/" + ticker);
        Document flowData = fetcher.getDocument("https://www.biznesradar.pl/wskazniki-plynnosci/" + ticker);
        Document bilansData = fetcher.getDocument("https://www.biznesradar.pl/raporty-finansowe-bilans/" + ticker);
        Document raportBiznes = fetcher.getDocument(
                "https://www.biznesradar.pl/raporty-finansowe-rachunek-zyskow-i-strat/" + ticker + ",Q");
        Document raportFlow = fetcher.getDocument(
                "https://www.biznesradar.pl/raporty-finansowe-przeplywy-pieniezne/" + ticker + ",Q");


        return Optional.ofNullable(BiznesRadarDocuments.builder()
                .financialData(financialData)
                .rentData(rentData)
                .debtData(debtData)
                .flowData(flowData)
                .bilansData(bilansData)
                .raportBiznes(raportBiznes)
                .raportFlow(raportFlow)
                .build());
    }

}
