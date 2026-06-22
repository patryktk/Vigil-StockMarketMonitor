package pl.tkaczyk.scraperservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.mapper.DividendAnnouncementMapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.DividendAnnouncement;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.repository.DividendAnnouncementRepository;
import pl.tkaczyk.scraperservice.service.CompanyService;
import pl.tkaczyk.scraperservice.service.Scraper;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowClient;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowParser;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DividendAnnouncementService implements Scraper {

    private final DividendAnnouncementMapper dividendAnnouncementMapper;
    private final DividendAnnouncementRepository dividendAnnouncementRepository;

    private final StrefaInwestorowClient strefaInwestorowClient;
    private final StrefaInwestorowParser strefaInwestorowParser;

    private final CompanyService companyService;


    @Transactional
    @Override
    public void scrape(String ticker) {

        Company company = companyService.findCompanyByTickerOrCreate(ticker);

        StrefaInwestorowDocument strefaInwestorowDocument = strefaInwestorowClient.fetch();
        strefaInwestorowParser.parse(strefaInwestorowDocument, ticker)
                .map(dto -> dividendAnnouncementMapper.toEntity(dto, company))
                .ifPresent(dividendAnnouncementRepository::save);
    }
}
