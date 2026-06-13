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

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BiznesRadarParserImplTest {

    BiznesRadarParserImpl biznesRadarParser = new BiznesRadarParserImpl();


    @Test
    @DisplayName("Should parse stock price")
    void shouldParseStockPrice() throws IOException {
        File financialDataInput = new ClassPathResource("biznesRadarFinancialDataScrap.html").getFile();
        File rentDataInput = new ClassPathResource("biznesRadarRentDataScrap.html").getFile();
        File debtDataInput = new ClassPathResource("biznesRadarDebtDataScrap.html").getFile();
        File flowDataInput = new ClassPathResource("biznesRadarFlowDataScrap.html").getFile();
        File bilansDataInput = new ClassPathResource("biznesRadarBilansDataScrap.html").getFile();
        File raportBiznesInput = new ClassPathResource("biznesRadarRaportBiznesScrap.html").getFile();
        File raportFlowInput = new ClassPathResource("biznesRadarRaportFlowScrap.html").getFile();

        Document financialData = Jsoup.parse(financialDataInput);
        Document rentData = Jsoup.parse(rentDataInput);
        Document debtData = Jsoup.parse(debtDataInput);
        Document flowData = Jsoup.parse(flowDataInput);
        Document bilansData = Jsoup.parse(bilansDataInput);
        Document raportBiznes = Jsoup.parse(raportBiznesInput);
        Document raportFlow = Jsoup.parse(raportFlowInput);

        BiznesRadarDocuments biznesRadarDocuments = BiznesRadarDocuments.builder()
                .financialData(financialData)
                .rentData(rentData)
                .debtData(debtData)
                .flowData(flowData)
                .bilansData(bilansData)
                .raportBiznes(raportBiznes)
                .raportFlow(raportFlow)
                .build();


        StockSnapshotDto parse = biznesRadarParser.parse(biznesRadarDocuments);


        assertThat(parse.getPrice()).isEqualByComparingTo(new BigDecimal("94.68"));
        assertThat(parse.getPriceToEarnings()).isEqualByComparingTo(new BigDecimal("11.30"));
        assertThat(parse.getPriceToBookValue()).isEqualByComparingTo(new BigDecimal("4.38"));
        assertThat(parse.getPriceToSales()).isEqualByComparingTo(new BigDecimal("4.19"));
        assertThat(parse.getPriceToOperatingIncome()).isEqualByComparingTo(new BigDecimal("9.29"));
        assertThat(parse.getEvToSales()).isEqualByComparingTo(new BigDecimal("4.19"));
        assertThat(parse.getEvToEbit()).isEqualByComparingTo(new BigDecimal("7.10"));
        assertThat(parse.getEvToEbitda()).isEqualByComparingTo(new BigDecimal("6.94"));
        assertThat(parse.getEarningsPerShare()).isEqualByComparingTo(new BigDecimal("8.38"));

        assertThat(parse.getReturnOnEquity_pct()).isEqualByComparingTo(new BigDecimal("38.77"));
        assertThat(parse.getReturnOnAssets_pct()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(parse.getOperatingMargin_pct()).isEqualByComparingTo(new BigDecimal("45.08"));
        assertThat(parse.getNetProfitMargin_pct()).isEqualByComparingTo(new BigDecimal("37.09"));
        assertThat(parse.getSalesMargin_pct()).isEqualByComparingTo(new BigDecimal("46.88"));

        assertThat(parse.getTotalDebt()).isEqualByComparingTo(new BigDecimal("0.74"));
        assertThat(parse.getNetDebt()).isEqualByComparingTo(new BigDecimal("-2441740000"));
        assertThat(parse.getNetFinancialDebt()).isEqualByComparingTo(new BigDecimal("-2633797000"));
        assertThat(parse.getDebtToEquity()).isEqualByComparingTo(new BigDecimal("2.88"));
        assertThat(parse.getNetDebtToEbitda()).isEqualByComparingTo(new BigDecimal("-1.99"));

        assertThat(parse.getCurrentRatio()).isEqualByComparingTo(new BigDecimal("1.34"));

        assertThat(parse.getEquity()).isEqualByComparingTo(new BigDecimal("2541556"));

        assertThat(parse.getRevenue()).isEqualByComparingTo(new BigDecimal("2656451"));
        assertThat(parse.getGrossProfit()).isEqualByComparingTo(new BigDecimal("1245346"));
        assertThat(parse.getEbit()).isEqualByComparingTo(new BigDecimal("1197611"));
        assertThat(parse.getNetIncome()).isEqualByComparingTo(new BigDecimal("985318"));

        assertThat(parse.getDepreciationAndAmortization()).isEqualByComparingTo(new BigDecimal("26424"));
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

    //TODO: Test for getBigDecimal


}