package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrefaInwestorowClient {

    private final HtmlDocumentFetcher htmlDocumentFetcher;

    public Document makeSnapshot(String tickerName){
        int year = LocalDate.now().getYear();
        Document document = htmlDocumentFetcher.getDocument("https://strefainwestorow.pl/dane/dywidendy/" + year);
        return document;
    }
}
