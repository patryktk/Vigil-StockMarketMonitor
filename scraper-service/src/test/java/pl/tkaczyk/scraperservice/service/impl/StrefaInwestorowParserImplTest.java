package pl.tkaczyk.scraperservice.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;
import pl.tkaczyk.scraperservice.service.StrefaInwestorowParser;
import pl.tkaczyk.scraperservice.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tkaczyk.scraperservice.service.impl.StrefaInwestorowParserImpl.CSS_QUERY;
import static pl.tkaczyk.scraperservice.service.impl.StrefaInwestorowParserImpl.formatter;


class StrefaInwestorowParserImplTest {

    private final Utils utils = new Utils();

    StrefaInwestorowParser parser = new StrefaInwestorowParserImpl(utils);

    @Test
    @DisplayName("Should parse dividend data from file")
    void shouldParseDividendDataFromFile() throws IOException {
        String ticker = "LEN";

        File strefaInwestorowInput = new ClassPathResource("strefaInwestorowDywidendy.html").getFile();
        Document strefaInwestorowDocument = Jsoup.parse(strefaInwestorowInput);

        StrefaInwestorowDocument strefaInwestorowDocumentDto = StrefaInwestorowDocument.builder()
                .dividendAnnouncementDocument(strefaInwestorowDocument)
                .build();


        Optional<DividendAnnouncementDto> parse = parser.parse(strefaInwestorowDocumentDto, ticker);
        assertTrue(parse.isPresent());
        parse.ifPresent(dividendAnnouncement -> {
            assertThat(dividendAnnouncement.getLastDateToBuy()).isEqualTo(LocalDate.parse("19.06.2026", formatter));
            assertThat(dividendAnnouncement.getPayDate()).isEqualTo(LocalDate.parse("03.07.2026", formatter));
            assertThat(dividendAnnouncement.getDividendYield_pct()).isEqualByComparingTo("4.39");
            assertThat(dividendAnnouncement.getAmountPerStock()).isEqualByComparingTo("0.10");
        });
    }


    @Test
    @DisplayName("Should parse when dates are empty in file")
    void shouldParseWhenDatesAreEmptyInFile() throws IOException {
        String ticker = "CMP";
        File strefaInwestorowInput = new ClassPathResource("strefaInwestorowDywidendy.html").getFile();
        Document strefaInwestorowDocument = Jsoup.parse(strefaInwestorowInput);

        StrefaInwestorowDocument strefaInwestorowDocumentDto = StrefaInwestorowDocument.builder()
                .dividendAnnouncementDocument(strefaInwestorowDocument)
                .build();


        Optional<DividendAnnouncementDto> parse = parser.parse(strefaInwestorowDocumentDto, ticker);
        assertTrue(parse.isPresent());
        parse.ifPresent(dividendAnnouncement -> {
            assertThat(dividendAnnouncement.getLastDateToBuy()).isNull();
            assertThat(dividendAnnouncement.getPayDate()).isNull();
            assertThat(dividendAnnouncement.getDividendYield_pct()).isEqualByComparingTo("0.00");
            assertThat(dividendAnnouncement.getAmountPerStock()).isEqualByComparingTo("3.41");
        });
    }

    @Test
    @DisplayName("Should parse with mocked data")
    void shouldParseWithMockedData() {
        String ticker = "CMP";
        String dividendYield = "9.93";
        String amountPerStock = "4.08";
        String lastDateToBuy = "11.06.2026";
        String payDate = "24.06.2026";


        Elements rows = new Elements();
        rows.add(new Element("tr"));
        Element tr = rows.selectFirst("tr");
        tr.html("<td>CMP</td>" +
                "<td>CMP</td>" +
                "<td>CMP SA</td>" +
                "<td>" + lastDateToBuy + "</td>" +
                "<td>" + dividendYield + "%</td>" +
                "<td>" + amountPerStock + " zł</td>" +
                "<td>" + payDate + "</td>");


        Document dividendAnnouncementDocument = Mockito.mock(Document.class);
        StrefaInwestorowDocument build = StrefaInwestorowDocument.builder().dividendAnnouncementDocument(dividendAnnouncementDocument).build();

        Mockito.when(build.dividendAnnouncementDocument().select(CSS_QUERY))
                .thenReturn(rows);

        Optional<DividendAnnouncementDto> parse = parser.parse(build, ticker);

        assertTrue(parse.isPresent());
        parse.ifPresent(dividendAnnouncement -> {
            assertThat(dividendAnnouncement.getPayDate()).isEqualTo(LocalDate.parse(payDate, formatter));
            assertThat(dividendAnnouncement.getLastDateToBuy()).isEqualTo(LocalDate.parse(lastDateToBuy, formatter));
            assertThat(dividendAnnouncement.getDividendYield_pct()).isEqualByComparingTo(dividendYield);
            assertThat(dividendAnnouncement.getAmountPerStock()).isEqualByComparingTo(amountPerStock);
        });
    }


    @Test
    @DisplayName("Should parse with mocked data")
    void shouldReturnNullWhenThereIsNoTicker() {
        String ticker = "PSQL";
        String dividendYield = "9.93";
        String amountPerStock = "4.08";
        String lastDateToBuy = "11.06.2026";
        String payDate = "24.06.2026";


        Elements rows = new Elements();
        rows.add(new Element("tr"));
        Element tr = rows.selectFirst("tr");
        tr.html("<td>CMP</td>" +
                "<td>CMP</td>" +
                "<td>CMP SA</td>" +
                "<td>" + lastDateToBuy + "</td>" +
                "<td>" + dividendYield + "%</td>" +
                "<td>" + amountPerStock + " zł</td>" +
                "<td>" + payDate + "</td>");


        Document dividendAnnouncementDocument = Mockito.mock(Document.class);
        StrefaInwestorowDocument build = StrefaInwestorowDocument.builder().dividendAnnouncementDocument(dividendAnnouncementDocument).build();

        Mockito.when(build.dividendAnnouncementDocument().select(CSS_QUERY))
                .thenReturn(rows);

        Optional<DividendAnnouncementDto> parse = parser.parse(build, ticker);

        assertTrue(parse.isEmpty());

    }

    @Test
    @DisplayName("Should parse with mocked data")
    void shouldReturnNullWhenThereIsNotEnoughColumns() {
        String ticker = "CMP";
        String dividendYield = "9.93";
        String amountPerStock = "4.08";
        String lastDateToBuy = "11.06.2026";
        String payDate = "24.06.2026";


        Elements rows = new Elements();
        rows.add(new Element("tr"));
        Element tr = rows.selectFirst("tr");
        tr.html("<td>CMP</td>" +
                "<td>" + lastDateToBuy + "</td>" +
                "<td>" + dividendYield + "%</td>" +
                "<td>" + amountPerStock + " zł</td>" +
                "<td>" + payDate + "</td>");


        Document dividendAnnouncementDocument = Mockito.mock(Document.class);
        StrefaInwestorowDocument build = StrefaInwestorowDocument.builder().dividendAnnouncementDocument(dividendAnnouncementDocument).build();

        Mockito.when(build.dividendAnnouncementDocument().select(CSS_QUERY))
                .thenReturn(rows);

        Optional<DividendAnnouncementDto> parse = parser.parse(build, ticker);

        assertTrue(parse.isEmpty());
    }

}