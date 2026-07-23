package pl.tkaczyk.scraperservice.controller;

import lombok.RequiredArgsConstructor;
import model.dto.InvestDataDto;
import model.events.StockSnapshotEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tkaczyk.scraperservice.messaging.StockEventPublisher;
import pl.tkaczyk.scraperservice.service.impl.ScrapService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrape")
public class ScrapeController {

    private final ScrapService scrapService;
    private final StockEventPublisher stockEventPublisher;

    @PostMapping("/scrap")
    public ResponseEntity<List<InvestDataDto>> scrap(@RequestBody List<model.dto.TickerDto> tickerList) {
        return ResponseEntity.ok().body(scrapService.scrapeList(tickerList));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        stockEventPublisher.publishSnapshot(new StockSnapshotEvent("XF"));
        return ResponseEntity.ok().body("test");
    }
}
