package pl.tkaczyk.scraperservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.mapper.DividendAnnouncementMapper;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.repository.DividendAnnouncementRepository;
import pl.tkaczyk.scraperservice.service.Scraper;

@RequiredArgsConstructor
@Service
public class DividendAnnouncementService implements Scraper {

    private final DividendAnnouncementMapper dividendAnnouncementMapper;
    private final DividendAnnouncementRepository dividendAnnouncementRepository;

    private final StrefaInwestorowClientImpl strefaInwestorowClient;
    private final StrefaInwestorowParserImpl strefaInwestorowParser;

    @Transactional
    @Override
    public void scrape(String ticker) {
        StrefaInwestorowDocument strefaInwestorowDocument = strefaInwestorowClient.fetch();
        strefaInwestorowParser.parse(strefaInwestorowDocument, ticker)
                .map(dividendAnnouncementMapper::toEntity)
                .ifPresent(dividendAnnouncementRepository::save);
    }
}
