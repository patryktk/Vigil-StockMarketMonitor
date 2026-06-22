package pl.tkaczyk.scraperservice.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaczyk.scraperservice.exception.HtmlFetchException;
import pl.tkaczyk.scraperservice.mapper.DividendAnnouncementMapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.DividendAnnouncement;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.repository.DividendAnnouncementRepository;
import pl.tkaczyk.scraperservice.service.CompanyService;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowClient;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowParser;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DividendAnnouncementServiceTest {

    @InjectMocks
    DividendAnnouncementService service;

    @Mock
    StrefaInwestorowClient strefaInwestorowClient;

    @Mock
    CompanyService companyService;

    @Mock
    StrefaInwestorowParser strefaInwestorowParser;

    @Mock
    DividendAnnouncementMapper mapper;

    @Mock
    DividendAnnouncementRepository repository;


    @DisplayName("Should successfully scrape")
    @Test
    void shouldSuccessfullyScrape() {
        String ticker = "TSLA";

        Company company = new Company(null, "Tesla", "TSLA");
        when(companyService.findCompanyByTickerOrCreate(ticker)).thenReturn(company);

        StrefaInwestorowDocument strefaInwestorowDocument = StrefaInwestorowDocument.builder()
                .dividendAnnouncementDocument(null)
                .build();
        when(strefaInwestorowClient.fetch()).thenReturn(strefaInwestorowDocument);

        DividendAnnouncement dividendAnnouncement = new DividendAnnouncement();
        DividendAnnouncementDto dividendAnnouncementDto = new DividendAnnouncementDto();
        Optional<DividendAnnouncementDto> optionalDividendAnnouncementDto = Optional.of(dividendAnnouncementDto);

        when(strefaInwestorowParser.parse(
                strefaInwestorowDocument,
                ticker)).thenReturn(optionalDividendAnnouncementDto);

        when(mapper.toEntity(dividendAnnouncementDto, company)).thenReturn(dividendAnnouncement);

        when(repository.save(dividendAnnouncement)).thenReturn(dividendAnnouncement);


        service.scrape(ticker);

        Mockito.verify(repository).save(dividendAnnouncement);
    }


    @DisplayName("Should not save when optional is empty")
    @Test
    void shouldNotSaveWhenOptionalIsEmpty() {
        String ticker = "TSLA";

        Company company = new Company(null, "Tesla", "TSLA");
        when(companyService.findCompanyByTickerOrCreate(ticker)).thenReturn(company);

        StrefaInwestorowDocument strefaInwestorowDocument = StrefaInwestorowDocument.builder()
                .dividendAnnouncementDocument(null)
                .build();
        when(strefaInwestorowClient.fetch()).thenReturn(strefaInwestorowDocument);

        DividendAnnouncement dividendAnnouncement = new DividendAnnouncement();

        Optional<DividendAnnouncementDto> optionalDividendAnnouncementDto = Optional.empty();

        when(strefaInwestorowParser.parse(
                strefaInwestorowDocument,
                ticker)).thenReturn(optionalDividendAnnouncementDto);


        service.scrape(ticker);

        Mockito.verify(repository, Mockito.never()).save(dividendAnnouncement);
    }

    @Test
    @DisplayName("Should throw exception when jsoup throws exception")
    void shouldThrowExceptionWhenJsoupThrowsException() {
        String ticker = "TSLA";

        Company company = new Company(null, "Tesla", "TSLA");
        when(companyService.findCompanyByTickerOrCreate(ticker)).thenReturn(company);

        when(strefaInwestorowClient.fetch()).thenThrow(new HtmlFetchException("Error while fetching", new Exception()));

        DividendAnnouncement entity = new DividendAnnouncement();

        Assertions.assertThatThrownBy(() -> service.scrape(ticker)).isInstanceOf(HtmlFetchException.class);
        Mockito.verify(repository, Mockito.never()).save(entity);
    }

}