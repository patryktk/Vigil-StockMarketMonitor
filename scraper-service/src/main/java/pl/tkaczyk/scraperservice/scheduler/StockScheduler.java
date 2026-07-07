package pl.tkaczyk.scraperservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tkaczyk.scraperservice.service.impl.ScrapService;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {

    private final ScrapService scrapService;

    @Scheduled(fixedDelay = 300_000)
    public void scrapIntraday(){
        log.info("Starting scrap intraday");
        scrapService.scrape("XTB");
    }

    @Scheduled(cron = "0 0 21 * * MON-FRI", zone = "Europe/Warsaw")
    public void scrapeDaily() {
        log.info("Starting scrap daily");
        scrapService.scrape("XTB");
    }
}
