package pl.tkaczyk.scraperservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tkaczyk.scraperservice.exception.CompanyNotFound;
import pl.tkaczyk.scraperservice.mapper.StockMapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.StockSnapshot;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;
import pl.tkaczyk.scraperservice.repository.CompanyRepository;
import pl.tkaczyk.scraperservice.repository.StockSnapshotRepository;

import java.util.Map;

@Service
@AllArgsConstructor
public class ScrapService {

    private BiznesRadarClient biznesRadarClient;
    private BiznesRadarParser biznesRadarParser;
    private StockSnapshotRepository stockSnapshotRepository;
    private CompanyRepository companyRepository;
    private StockMapper mapper;

    @Transactional
    public void scrape(String ticker) {

        Map<String, Document> stringDocumentMap = biznesRadarClient.makeSnapshot(ticker);

        StockSnapshotDto stockSnapshotDto = biznesRadarParser.parseStockData(stringDocumentMap);

        Company company = companyRepository.findCompanyByTicker(ticker)
                .orElseThrow(() -> new CompanyNotFound("Company not found"));

        StockSnapshot entity = mapper.toEntity(stockSnapshotDto, company);
        stockSnapshotRepository.save(entity);

    }


}
