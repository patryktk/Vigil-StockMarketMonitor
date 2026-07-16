package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import model.dto.DividendAnnouncementDto;
import model.dto.InvestDataDto;
import model.dto.StockSnapshotDto;
import model.dto.TickerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final StockSnapshotService stockSnapshotService;
    private final DividendAnnouncementService dividendAnnouncementService;


    public InvestDataDto scrape(String ticker) {
        Optional<StockSnapshotDto> stockSnapshotDtoScrape = stockSnapshotService.scrape(ticker);
        Optional<DividendAnnouncementDto> dividendAnnouncementDtoScrape = dividendAnnouncementService.scrape(ticker);
        return InvestDataDto.builder()
                .stockSnapshot(stockSnapshotDtoScrape.orElse(null))
                .dividendAnnouncementDto(dividendAnnouncementDtoScrape.orElse(null))
                .build();
    }

    public List<InvestDataDto> scrapeList(List<TickerDto> tickerList) {
        List<InvestDataDto> investDataDtoList = new ArrayList<>();
        for (TickerDto tickerDto : tickerList) {
            InvestDataDto scrape = scrape(tickerDto.ticker());
            investDataDtoList.add(scrape);
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("http://localhost:8040/api/sheets/write", investDataDtoList, Void.class);

        return investDataDtoList;
    }
}
