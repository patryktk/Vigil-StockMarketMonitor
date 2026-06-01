package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BiznesRadarParser {

    public StockSnapshotDto parseStockData(
            Map<String, Document> stringDocumentMap
    ) {
        StockSnapshotDto stockSnapshotDto = new StockSnapshotDto();

        Document financialData = stringDocumentMap.get("financialData");
        parseFinancialData(financialData, stockSnapshotDto);

        Document rentData = stringDocumentMap.get("rentData");
        parseRentData(rentData, stockSnapshotDto);

        Document debtData = stringDocumentMap.get("debtData");
        parseDebtData(debtData, stockSnapshotDto);

        Document flowData = stringDocumentMap.get("flowData");
        parseFlowData(flowData, stockSnapshotDto);

        Document bilansData = stringDocumentMap.get("bilansData");
        parseBilansData(bilansData, stockSnapshotDto);

        Document raportBiznes = stringDocumentMap.get("raportBiznes");
        parseRaportBiznes(raportBiznes, stockSnapshotDto);


        Document raportFlow = stringDocumentMap.get("raportFlow");
        parseRaportFlow(raportFlow, stockSnapshotDto);

        return stockSnapshotDto;
    }

    private void parseRaportFlow(Document raportFlow, StockSnapshotDto stockSnapshotDto) {
        Element depreciationAndAmortizationElement = raportFlow.selectFirst(
                "tr[data-field=CashflowAmortization] td.h.newest .value .pv span");
        BigDecimal depreciationAndAmortization = getBigDecimal(depreciationAndAmortizationElement);
        stockSnapshotDto.setDepreciationAndAmortization(depreciationAndAmortization);
    }

    private void parseRaportBiznes(Document raportBiznes, StockSnapshotDto stockSnapshotDto) {
        Element revenueElement = raportBiznes.selectFirst(
                "tr[data-field=IncomeRevenues] td.h.newest .value .pv span");
        BigDecimal revenue = getBigDecimal(revenueElement);
        stockSnapshotDto.setRevenue(revenue);


        Element grossProfitElement = raportBiznes.selectFirst(
                "tr[data-field=IncomeGrossProfit] td.h.newest .value .pv span");
        BigDecimal grossProfit = getBigDecimal(grossProfitElement);
        stockSnapshotDto.setGrossProfit(grossProfit);


        Element ebitElement = raportBiznes.selectFirst("tr[data-field=IncomeEBIT] td.h.newest .value .pv span");
        BigDecimal ebit = getBigDecimal(ebitElement);
        stockSnapshotDto.setEbit(ebit);

        Element netIncomeElement = raportBiznes.selectFirst("tr[data-field=IncomeNetProfit] td.h.newest .value .pv span");
        BigDecimal netIncome = getBigDecimal(netIncomeElement);
        stockSnapshotDto.setNetIncome(netIncome);
    }

    private void parseBilansData(Document bilansData, StockSnapshotDto stockSnapshotDto) {
        Element equityElement = bilansData.selectFirst(
                "tr[data-field=BalanceCapital] td.h.newest .value .pv span");
        BigDecimal equity = getBigDecimal(equityElement);
        stockSnapshotDto.setEquity(equity);
    }

    private void parseFlowData(Document flowData, StockSnapshotDto stockSnapshotDto) {
        Element currentRatioElement = flowData.selectFirst("tr[data-field=CR] td.h.newest .value .pv span");
        BigDecimal currentRatio = getBigDecimal(currentRatioElement);
        stockSnapshotDto.setCurrentRatio(currentRatio);
    }

    private void parseDebtData(Document debtData, StockSnapshotDto stockSnapshotDto) {
        Element totalDebtElement = debtData.selectFirst("tr[data-field=DTAR] td.h.newest .value .pv span");
        BigDecimal totalDebt = getBigDecimal(totalDebtElement);
        stockSnapshotDto.setTotalDebt(totalDebt);


        Element netDebtElement = debtData.selectFirst("tr[data-field=NetDebt] td.h.newest .value .pv span");
        BigDecimal netDebt = getBigDecimal(netDebtElement);
        stockSnapshotDto.setNetDebt(netDebt);


        Element netFinancialDebtElement = debtData.selectFirst(
                "tr[data-field=DebtFin] td.h.newest .value .pv span");
        BigDecimal netFinancialDebt = getBigDecimal(netFinancialDebtElement);
        stockSnapshotDto.setNetDebtToEbitda(netFinancialDebt);


        Element debtToEquityElement = debtData.selectFirst("tr[data-field=CG] td.h.newest .value .pv span");
        BigDecimal debtToEquity = getBigDecimal(debtToEquityElement);
        stockSnapshotDto.setDebtToEquity(debtToEquity);


        Element netDebtToEbitdaElement = debtData.selectFirst(
                "tr[data-field=NetDebtEBITDA] td.h.newest .value .pv span");
        BigDecimal netDebtToEbitda = getBigDecimal(netDebtToEbitdaElement);
        stockSnapshotDto.setNetDebtToEbitda(netDebtToEbitda);

    }

    private void parseRentData(Document rentData, StockSnapshotDto stockSnapshotDto) {
        Element roeElement = rentData.selectFirst("tr[data-field=ROE] td.h.newest .value .pv span");
        BigDecimal returnOnEquity = getBigDecimal(roeElement);
        stockSnapshotDto.setReturnOnEquity(returnOnEquity);

        Element roaElement = rentData.selectFirst("tr[data-field=ROA] td.h.newest .value .pv span");
        BigDecimal returnOnAssets = getBigDecimal(roaElement);
        stockSnapshotDto.setReturnOnAssets(returnOnAssets);


        Element operatingMarginElement = rentData.selectFirst("tr[data-field=OPM] td.h.newest .value .pv span");
        BigDecimal operatingMargin = getBigDecimal(operatingMarginElement);
        stockSnapshotDto.setOperatingMargin(operatingMargin);


        Element netProfitMarginElement = rentData.selectFirst("tr[data-field=ROS] td.h.newest .value .pv span");
        BigDecimal netProfitMargin = getBigDecimal(netProfitMarginElement);
        stockSnapshotDto.setNetProfitMargin(netProfitMargin);


        Element salesMarginElement = rentData.selectFirst("tr[data-field=RS] td.h.newest .value .pv span");
        BigDecimal salesMargin = getBigDecimal(salesMarginElement);
        stockSnapshotDto.setSalesMargin(salesMargin);
    }

    private void parseFinancialData(Document financialData, StockSnapshotDto stockSnapshotDto) {

        Element priceElement = financialData.selectFirst("tr[data-field=Quote] td.h.newest .value .pv span");
        BigDecimal price = getBigDecimal(priceElement);
        stockSnapshotDto.setPrice(price);

        Element priceToEarningsElement = financialData.selectFirst("tr[data-field=CZ] td.h.newest .value .pv span");
        BigDecimal priceToEarnings = getBigDecimal(priceToEarningsElement);
        stockSnapshotDto.setPriceToEarnings(priceToEarnings);


        Element priceToBookValueElement = financialData.selectFirst("tr[data-field=CWK] td.h.newest .value .pv span");
        BigDecimal priceToBookValue = getBigDecimal(priceToBookValueElement);
        stockSnapshotDto.setPriceToBookValue(priceToBookValue);


        Element priceToSalesElement = financialData.selectFirst("tr[data-field=CP] td.h.newest .value .pv span");
        BigDecimal priceToSales = getBigDecimal(priceToSalesElement);
        stockSnapshotDto.setPriceToSales(priceToSales);


        Element priceToOperatingIncomeElement = financialData.selectFirst(
                "tr[data-field=CZO] td.h.newest .value .pv span");
        BigDecimal priceToOperatingIncome = getBigDecimal(priceToOperatingIncomeElement);
        stockSnapshotDto.setPriceToOperatingIncome(priceToOperatingIncome);


        Element evToSalesElement = financialData.selectFirst("tr[data-field=EVP] td.h.newest .value .pv span");
        BigDecimal evToSales = getBigDecimal(evToSalesElement);
        stockSnapshotDto.setEvToSales(evToSales);


        Element evToEbitElement = financialData.selectFirst("tr[data-field=EVEBIT] td.h.newest .value .pv span");
        BigDecimal evToEbit = getBigDecimal(evToEbitElement);
        stockSnapshotDto.setEvToEbit(evToEbit);


        Element evToEbitdaElement = financialData.selectFirst("tr[data-field=EVEBITDA] td.h.newest .value .pv span");
        BigDecimal evToEbitda = getBigDecimal(evToEbitdaElement);
        stockSnapshotDto.setEvToEbitda(evToEbitda);


        Element earningsPerShareElement = financialData.selectFirst("tr[data-field=Z] td.h.newest .value .pv span");
        BigDecimal earningsPerShare = getBigDecimal(earningsPerShareElement);
        stockSnapshotDto.setEarningsPerShare(earningsPerShare);
    }

    private BigDecimal getBigDecimal(Element kursElement) {
        if (kursElement == null)
            return null;
        return new BigDecimal(kursElement.text());
    }
}
