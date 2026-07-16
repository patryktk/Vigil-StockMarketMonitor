package pl.tkaczyk.scraperservice.controller;

import lombok.RequiredArgsConstructor;
import model.dto.InvestDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tkaczyk.scraperservice.service.impl.ScrapService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrape")
public class ScrapeController {

    private final ScrapService scrapService;

    @PostMapping("/scrap")
    public ResponseEntity<List<InvestDataDto>> scrap(@RequestBody List<model.dto.TickerDto> tickerList) {
        return ResponseEntity.ok().body(scrapService.scrapeList(tickerList));
    }
}
