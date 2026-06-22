package pl.tkaczyk.scraperservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {

    @Mock
    StockSnapshotService stockSnapshotService;

    @Mock
    DividendAnnouncementService dividendAnnouncementService;

    @InjectMocks
    ScrapService service;


    @Test
    @DisplayName("Should call scrapers")
    void shouldCallScrapers() {
        String ticker = "XTB";

        service.scrape(ticker);

        Mockito.verify(stockSnapshotService).scrape(ticker);
        Mockito.verify(dividendAnnouncementService).scrape(ticker);
    }
}