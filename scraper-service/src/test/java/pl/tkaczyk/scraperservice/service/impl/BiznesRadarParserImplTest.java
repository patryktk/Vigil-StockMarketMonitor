package pl.tkaczyk.scraperservice.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;

import java.io.*;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BiznesRadarParserImplTest {

    BiznesRadarParserImpl biznesRadarParser = new BiznesRadarParserImpl();



    @Test
    @DisplayName("Should parse stock price")
    void shouldParseStockPrice() throws IOException {
       File input = new ClassPathResource("biznesRadarFinancialScrap.html").getFile();


        Document financialData = Jsoup.parse(input);
        BiznesRadarDocuments biznesRadarDocuments = BiznesRadarDocuments.builder()
                .financialData(financialData)
                .rentData(new Document(""))
                .debtData(new Document(""))
                .flowData(new Document(""))
                .bilansData(new Document(""))
                .raportBiznes(new Document(""))
                .raportFlow(new Document(""))
                .build();


        StockSnapshotDto parse = biznesRadarParser.parse(biznesRadarDocuments);


        assertThat(parse.getPrice())
                .isEqualByComparingTo(new BigDecimal("94.68"));
    }

    @Test
    @DisplayName("Should return null when element is missing")
    void shouldReturnNullWhenElementMissing() {
        Document emptyDoc = Jsoup.parse("<html></html>");
        BiznesRadarDocuments biznesRadarDocuments = BiznesRadarDocuments.builder()
                .financialData(emptyDoc)
                .rentData(new Document(""))
                .debtData(new Document(""))
                .flowData(new Document(""))
                .bilansData(new Document(""))
                .raportBiznes(new Document(""))
                .raportFlow(new Document(""))
                .build();

        StockSnapshotDto result = biznesRadarParser.parse(biznesRadarDocuments);

        assertThat(result.getPrice()).isNull();
    }


    @Test
    @DisplayName("Should return null when document is null")
    void shouldReturnNullWhenDocumentIsNull(){
        BiznesRadarDocuments biznesRadarDocuments = BiznesRadarDocuments.builder()
                .financialData(null)
                .rentData(new Document(""))
                .debtData(new Document(""))
                .flowData(new Document(""))
                .bilansData(new Document(""))
                .raportBiznes(new Document(""))
                .raportFlow(new Document(""))
                .build();

        StockSnapshotDto parse = biznesRadarParser.parse(biznesRadarDocuments);

        assertThat(parse.getPrice()).isNull();

    }


}