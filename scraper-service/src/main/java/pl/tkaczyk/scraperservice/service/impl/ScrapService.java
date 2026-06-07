package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final StockSnapshotService stockSnapshotService;
    private final DividendAnnouncementService dividendAnnouncementService;


    public void scrape(String ticker) {
        stockSnapshotService.scrape(ticker);
        dividendAnnouncementService.scrape(ticker);
    }
}
