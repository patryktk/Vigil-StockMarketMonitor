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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StrefaInwestorowParserImplTest {

    private final Utils utils = new Utils();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


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
            assertThat(dividendAnnouncement.getDividendYield_pct()).isEqualTo(new BigDecimal("4.39"));
            assertThat(dividendAnnouncement.getAmountPerStock()).isEqualTo(new BigDecimal("0.10"));
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
            assertThat(dividendAnnouncement.getDividendYield_pct()).isEqualTo(new BigDecimal("0.00"));
            assertThat(dividendAnnouncement.getAmountPerStock()).isEqualTo(new BigDecimal("3.41"));
        });
    }

    @Test
    @DisplayName("Should parse with mocked data")
    void shouldParseWithMockedData() {
        String ticker = "CMP";



        Elements dividendAnnouncementElement = new Elements();
        dividendAnnouncementElement.add(new Element("td").text("MOL"));
        dividendAnnouncementElement.add(new Element("td").text("MOL"));
        dividendAnnouncementElement.add(new Element("td").text("MOL SA"));
        dividendAnnouncementElement.add(new Element("td").text("11.06.2026"));
        dividendAnnouncementElement.add(new Element("td").text("9.93%"));
        dividendAnnouncementElement.add(new Element("td").text("4.08 zł"));
        dividendAnnouncementElement.add(new Element("td").text("24.06.2026"));

        Elements rows = new Elements();
        rows.add(new Element("tr"));


        Document dividendAnnouncementDocument = Mockito.mock(Document.class);
        StrefaInwestorowDocument build = StrefaInwestorowDocument.builder().dividendAnnouncementDocument(dividendAnnouncementDocument).build();

        Mockito.when(build.dividendAnnouncementDocument().select(StrefaInwestorowParserImpl.CSS_QUERY))
                .thenReturn(dividendAnnouncementElement);

        Optional<DividendAnnouncementDto> parse = parser.parse(build, ticker);

//        parse.ifPresent(dividendAnnouncement -> {
//            assertThat(dividendAnnouncement.getPayDate()).isEqualTo("24.06.2026");
//        });

    }

}