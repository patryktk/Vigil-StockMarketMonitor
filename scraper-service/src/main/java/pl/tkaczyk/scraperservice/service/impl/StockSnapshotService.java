package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tkaczyk.scraperservice.mapper.StockMapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.StockSnapshot;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;
import pl.tkaczyk.scraperservice.repository.StockSnapshotRepository;
import pl.tkaczyk.scraperservice.service.BiznesRadarClient;
import pl.tkaczyk.scraperservice.service.BiznesRadarParser;
import pl.tkaczyk.scraperservice.service.CompanyService;
import pl.tkaczyk.scraperservice.service.Scraper;

@Service
@RequiredArgsConstructor
public class StockSnapshotService implements Scraper {

    private final BiznesRadarClient biznesRadarClient;
    private final BiznesRadarParser biznesRadarParser;
    private final StockMapper mapper;
    private final StockSnapshotRepository stockSnapshotRepository;
    private final CompanyService companyService;


    @Transactional
    @Override
    public void scrape(String ticker) {
        Company company = companyService.findCompanyByTickerOrCreate(ticker);
        BiznesRadarDocuments biznesRadarDocuments = biznesRadarClient.makeSnapshot(ticker);

        StockSnapshotDto stockSnapshotDto = biznesRadarParser.parse(biznesRadarDocuments);
        StockSnapshot entity = mapper.toEntity(stockSnapshotDto, company);

        stockSnapshotRepository.save(entity);
    }
}
