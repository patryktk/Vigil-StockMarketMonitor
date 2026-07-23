package pl.tkaczyk.scraperservice.service.impl;

import model.dto.StockSnapshotDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.model.enums.Fields;
import pl.tkaczyk.scraperservice.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BiznesRadarParserImplTest {

    private final Utils utils = new Utils();

    BiznesRadarParserImpl parser = new BiznesRadarParserImpl(utils);


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


        StockSnapshotDto parse = parser.parse(biznesRadarDocuments);


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


    @ParameterizedTest(name = "Parsing {0} should match expected output")
    @DisplayName("Should parse stock data")
    @CsvSource({
            "biznesRadarFinancialDataScrap.html, financialDataInput",
            "biznesRadarRentDataScrap.html, rentDataInput",
            "biznesRadarDebtDataScrap.html, debtDataInput",
            "biznesRadarFlowDataScrap.html, flowDataInput",
            "biznesRadarBilansDataScrap.html, bilansDataInput",
            "biznesRadarRaportBiznesScrap.html, raportBiznesInput",
            "biznesRadarRaportFlowScrap.html, raportFlowInput",
    })
    void shouldParseStockPriceIndividual(String filePath, String expectedField) throws IOException {
        File file =  new ClassPathResource(filePath).getFile();
        Document document = Jsoup.parse(file);

        BiznesRadarDocuments bzDocument = switch (expectedField) {
            case "financialDataInput" -> BiznesRadarDocuments.builder().financialData(document).build();
            case "rentDataInput" -> BiznesRadarDocuments.builder().rentData(document).build();
            case "debtDataInput" -> BiznesRadarDocuments.builder().debtData(document).build();
            case "flowDataInput" -> BiznesRadarDocuments.builder().flowData(document).build();
            case "bilansDataInput" -> BiznesRadarDocuments.builder().bilansData(document).build();
            case "raportBiznesInput" -> BiznesRadarDocuments.builder().raportBiznes(document).build();
            case "raportFlowInput" -> BiznesRadarDocuments.builder().raportFlow(document).build();
            default -> null;
        };


        assert bzDocument != null;
        StockSnapshotDto parse = parser.parse(bzDocument);


        switch (expectedField) {
            case "financialDataInput":
                assertThat(parse.getPrice()).isEqualByComparingTo(new BigDecimal("94.68"));
                assertThat(parse.getPriceToEarnings()).isEqualByComparingTo(new BigDecimal("11.30"));
                assertThat(parse.getPriceToBookValue()).isEqualByComparingTo(new BigDecimal("4.38"));
                assertThat(parse.getPriceToSales()).isEqualByComparingTo(new BigDecimal("4.19"));
                assertThat(parse.getPriceToOperatingIncome()).isEqualByComparingTo(new BigDecimal("9.29"));
                assertThat(parse.getEvToSales()).isEqualByComparingTo(new BigDecimal("4.19"));
                assertThat(parse.getEvToEbit()).isEqualByComparingTo(new BigDecimal("7.10"));
                assertThat(parse.getEvToEbitda()).isEqualByComparingTo(new BigDecimal("6.94"));
                assertThat(parse.getEarningsPerShare()).isEqualByComparingTo(new BigDecimal("8.38"));
                break;
            case "rentDataInput":
                assertThat(parse.getReturnOnEquity_pct()).isEqualByComparingTo(new BigDecimal("38.77"));
                assertThat(parse.getReturnOnAssets_pct()).isEqualByComparingTo(new BigDecimal("10.00"));
                assertThat(parse.getOperatingMargin_pct()).isEqualByComparingTo(new BigDecimal("45.08"));
                assertThat(parse.getNetProfitMargin_pct()).isEqualByComparingTo(new BigDecimal("37.09"));
                assertThat(parse.getSalesMargin_pct()).isEqualByComparingTo(new BigDecimal("46.88"));
                break;
            case "debtDataInput":
                assertThat(parse.getTotalDebt()).isEqualByComparingTo(new BigDecimal("0.74"));
                assertThat(parse.getNetDebt()).isEqualByComparingTo(new BigDecimal("-2441740000"));
                assertThat(parse.getNetFinancialDebt()).isEqualByComparingTo(new BigDecimal("-2633797000"));
                assertThat(parse.getDebtToEquity()).isEqualByComparingTo(new BigDecimal("2.88"));
                assertThat(parse.getNetDebtToEbitda()).isEqualByComparingTo(new BigDecimal("-1.99"));
                break;
            case "flowDataInput":
                assertThat(parse.getCurrentRatio()).isEqualByComparingTo(new BigDecimal("1.34"));
                break;
            case "bilansDataInput":
                assertThat(parse.getEquity()).isEqualByComparingTo(new BigDecimal("2541556"));
                break;
            case "raportBiznesInput":
                assertThat(parse.getRevenue()).isEqualByComparingTo(new BigDecimal("2656451"));
                assertThat(parse.getGrossProfit()).isEqualByComparingTo(new BigDecimal("1245346"));
                assertThat(parse.getEbit()).isEqualByComparingTo(new BigDecimal("1197611"));
                assertThat(parse.getNetIncome()).isEqualByComparingTo(new BigDecimal("985318"));
                break;
            case "raportFlowInput":
                assertThat(parse.getDepreciationAndAmortization()).isEqualByComparingTo(new BigDecimal("26424"));
                break;
        }


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

        StockSnapshotDto result = parser.parse(biznesRadarDocuments);

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

        StockSnapshotDto parse = parser.parse(biznesRadarDocuments);

        assertThat(parse.getPrice()).isNull();

    }

    @Test
    @DisplayName("Should extract data using mocked document")
    void shouldExtractDataUsingMockedDocuments(){
        Element priceElement = new Element("p");
        priceElement.text("123.45");

        Document financialDoc = Mockito.mock(Document.class);
        Mockito.when(financialDoc.selectFirst(Fields.PRICE.getBzSelector())).thenReturn(priceElement);

        BiznesRadarDocuments documents = BiznesRadarDocuments.builder().financialData(financialDoc).build();
        StockSnapshotDto result = parser.parse(documents);

        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("123.45"));
    }

    @ParameterizedTest(name = " Parsing {0}")
    @MethodSource("provideFinancialData")
    void should(String testDescription, String inputPrice, String expectedPrice){
        Element priceElement = new Element("p");
        priceElement.text(inputPrice);

        Document financialDoc = Mockito.mock(Document.class);
        Mockito.when(financialDoc.selectFirst(Fields.PRICE.getBzSelector())).thenReturn(priceElement);

        BiznesRadarDocuments documents = BiznesRadarDocuments.builder().financialData(financialDoc).build();
        StockSnapshotDto result = parser.parse(documents);
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal(expectedPrice));
    }

    private static Stream<Arguments> provideFinancialData() {
        return Stream.of(
                Arguments.of("Space in Text", "123 45", "12345"),
                Arguments.of("Percent on end", "12345%", "12345"),
                Arguments.of("Comma as Decimal Separator", "123,45", "123.45")
        );
    }

}