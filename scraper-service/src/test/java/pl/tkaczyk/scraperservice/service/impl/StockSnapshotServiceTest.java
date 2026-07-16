package pl.tkaczyk.scraperservice.service.impl;

import model.dto.StockSnapshotDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaczyk.scraperservice.exception.HtmlFetchException;
import pl.tkaczyk.scraperservice.mapper.StockMapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.StockSnapshot;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.repository.StockSnapshotRepository;
import pl.tkaczyk.scraperservice.service.BiznesRadarClient;
import pl.tkaczyk.scraperservice.service.BiznesRadarParser;
import pl.tkaczyk.scraperservice.service.CompanyService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockSnapshotServiceTest {

    @Mock
    CompanyService companyService;

    @Mock
    BiznesRadarClient biznesRadarClient;

    @Mock
    BiznesRadarParser biznesRadarParser;

    @Mock
    StockMapper stockMapper;

    @Mock
    StockSnapshotRepository stockSnapshotRepository;

    @InjectMocks
    StockSnapshotService stockSnapshotService;


    @Test
    @DisplayName("Should scrape")
    void shouldScrape(){
        String ticker = "TSLA";

        Company company = new Company(null, "Tesla", "TSLA");
        when(companyService.findCompanyByTickerOrCreate(ticker)).thenReturn(company);

        BiznesRadarDocuments biznesRadarDocuments = BiznesRadarDocuments.builder()
                .financialData(null)
                .rentData(null)
                .debtData(null)
                .flowData(null)
                .bilansData(null)
                .raportBiznes(null)
                .raportFlow(null)
                .build();
        when(biznesRadarClient.makeSnapshot(ticker)).thenReturn(biznesRadarDocuments);

        StockSnapshotDto stockSnapshotDto = new StockSnapshotDto();
        when(biznesRadarParser.parse(biznesRadarDocuments)).thenReturn(stockSnapshotDto);

        StockSnapshot entity = new StockSnapshot();
        when(stockMapper.toEntity(stockSnapshotDto, company)).thenReturn(entity);

        when(stockSnapshotRepository.save(entity)).thenReturn(entity);

        stockSnapshotService.scrape(ticker);


        Mockito.verify(stockSnapshotRepository).save(entity);;
    }


    @Test
    @DisplayName("Should throw exception when jsoup throws exception")
    void shouldThrowExceptionWhenJsoupThrowsException(){
        String ticker = "TSLA";

        Company company = new Company(null, "Tesla", "TSLA");
        when(companyService.findCompanyByTickerOrCreate(ticker)).thenReturn(company);

        when(biznesRadarClient.makeSnapshot(ticker)).thenThrow(new HtmlFetchException("Error while fetching", new Exception()));

        StockSnapshot entity = new StockSnapshot();

        Assertions.assertThatThrownBy(() -> stockSnapshotService.scrape(ticker)).isInstanceOf(HtmlFetchException.class);
        Mockito.verify(stockSnapshotRepository, Mockito.never()).save(entity);
    }
}