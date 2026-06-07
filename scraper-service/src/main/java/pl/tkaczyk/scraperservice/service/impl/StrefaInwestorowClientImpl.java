package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StrefaInwestorowClientImpl implements StrefaInwestorowClient {

    private final HtmlDocumentFetcher htmlDocumentFetcher;

    @Override
    public StrefaInwestorowDocument fetch() {
        int year = LocalDate.now().getYear();
        Document document = htmlDocumentFetcher.getDocument("https://strefainwestorow.pl/dane/dywidendy/" + year);
        return StrefaInwestorowDocument.builder()
                .dividendAnnouncementDocument(document)
                .build();
    }
}
