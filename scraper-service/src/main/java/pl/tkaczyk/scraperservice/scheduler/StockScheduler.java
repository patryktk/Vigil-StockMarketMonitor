package pl.tkaczyk.scraperservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.InvestDataDto;
import model.dto.StockSnapshotBatchEvent;
import model.dto.TickerDto;
import model.events.StockSnapshotEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tkaczyk.scraperservice.mapper.EventMapper;
import pl.tkaczyk.scraperservice.messaging.StockEventPublisher;
import pl.tkaczyk.scraperservice.service.impl.ScrapService;
import pl.tkaczyk.scraperservice.service.impl.SheetsService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {

    private final ScrapService scrapService;

    private final SheetsService sheetsService;

    private final StockEventPublisher stockEventPublisher;
    private final EventMapper eventMapper;

    @Scheduled(fixedDelay = 300_000)
    public void scrapIntraday(){
        log.info("Starting scrap intraday");

        List<TickerDto> tickersList = sheetsService.getTickersList();
        List<StockSnapshotEvent> snapshots = new ArrayList<>();

        for (TickerDto tickerDto : tickersList) {
            InvestDataDto investDataDto = scrapService.scrape(tickerDto.ticker());
            snapshots.add(eventMapper.toEvent(investDataDto));
        }

        var batchEvent = new StockSnapshotBatchEvent(UUID.randomUUID(), Instant.now(), snapshots);
        stockEventPublisher.publishSnapshotBatch(batchEvent);
    }

    @Scheduled(cron = "0 0 21 * * MON-FRI", zone = "Europe/Warsaw")
    public void scrapeDaily() {
        log.info("Starting scrap daily");
        scrapService.scrape("XTB");
    }
}
