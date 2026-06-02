package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tkaczyk.scraperservice.mapper.DividendAnnouncementMapper;
import pl.tkaczyk.scraperservice.mapper.StockMapper;
import pl.tkaczyk.scraperservice.model.StockSnapshot;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;
import pl.tkaczyk.scraperservice.repository.DividendAnnouncementRepository;
import pl.tkaczyk.scraperservice.repository.StockSnapshotRepository;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final BiznesRadarClient biznesRadarClient;
    private final BiznesRadarParser biznesRadarParser;
    private final StockSnapshotRepository stockSnapshotRepository;
    private final StockMapper mapper;
    private final StrefaInwestorowDividendService strefaInwestorowDividendService;
    private final DividendAnnouncementMapper dividendAnnouncementMapper;
    private final DividendAnnouncementRepository dividendAnnouncementRepository;

    private final StrefaInwestorowClient strefaInwestorowClient;
    private final StrefaInwestorowParser strefaInwestorowParser;

    @Transactional
    public void scrape(String ticker) {
        scrapeStockSnapshot(ticker);
        scrapeDividendAnnouncement(ticker);
    }

    private void scrapeStockSnapshot(String ticker) {
        BiznesRadarDocuments biznesRadarDocuments = biznesRadarClient.makeSnapshot(ticker).orElseThrow(() -> new RuntimeException("Could not fetch data from BiznesRadar"));

        StockSnapshotDto stockSnapshotDto = biznesRadarParser.parseStockData(biznesRadarDocuments);
        StockSnapshot entity = mapper.toEntity(stockSnapshotDto);

        stockSnapshotRepository.save(entity);
    }

    private void scrapeDividendAnnouncement(String ticker) {
        Document elements = strefaInwestorowClient.makeSnapshot(ticker);

        strefaInwestorowParser.parseStock(elements);

        strefaInwestorowDividendService.makeSnapshot(ticker)
                .map(dividendAnnouncementMapper::toEntity)
                .ifPresent(dividendAnnouncementRepository::save);
    }


}
