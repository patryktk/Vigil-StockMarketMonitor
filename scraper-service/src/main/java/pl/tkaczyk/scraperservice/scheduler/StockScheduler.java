package pl.tkaczyk.scraperservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.InvestDataDto;
import model.dto.TickerDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tkaczyk.scraperservice.feign.SheetsFeignClient;
import pl.tkaczyk.scraperservice.messaging.StockEventPublisher;
import pl.tkaczyk.scraperservice.service.impl.ScrapService;
import pl.tkaczyk.scraperservice.service.impl.SheetsService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {

    private final ScrapService scrapService;

    private final SheetsService sheetsService;

    private final StockEventPublisher stockEventPublisher;

    @Scheduled(fixedDelay = 300_000)
    public void scrapIntraday(){
        log.info("Starting scrap intraday");

        List<TickerDto> tickersList = sheetsService.getTickersList();
        List<InvestDataDto> result = new ArrayList<>();

        for (TickerDto tickerDto : tickersList) {
            result.add(scrapService.scrape(tickerDto.ticker()));
        }

        stockEventPublisher.publishStockInfo(result);
    }

    @Scheduled(cron = "0 0 21 * * MON-FRI", zone = "Europe/Warsaw")
    public void scrapeDaily() {
        log.info("Starting scrap daily");
        scrapService.scrape("XTB");
    }
}
