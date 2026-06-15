package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;
import pl.tkaczyk.scraperservice.model.enums.Fields;
import pl.tkaczyk.scraperservice.service.BiznesRadarParser;
import pl.tkaczyk.scraperservice.utils.Utils;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BiznesRadarParserImpl implements BiznesRadarParser {

    private final Utils utils;

    @Override
    public StockSnapshotDto parse(
            BiznesRadarDocuments biznesRadarDocuments
    ) {
        StockSnapshotDto stockSnapshotDto = new StockSnapshotDto();

        if (biznesRadarDocuments.financialData() != null) {
            parseFinancialData(biznesRadarDocuments.financialData(), stockSnapshotDto);
        }

        if (biznesRadarDocuments.rentData() != null) {
            parseRentData(biznesRadarDocuments.rentData(), stockSnapshotDto);
        }

        if (biznesRadarDocuments.debtData() != null) {
            parseDebtData(biznesRadarDocuments.debtData(), stockSnapshotDto);
        }

        if (biznesRadarDocuments.flowData() != null) {
            parseFlowData(biznesRadarDocuments.flowData(), stockSnapshotDto);
        }

        if (biznesRadarDocuments.bilansData() != null) {
            parseBilansData(biznesRadarDocuments.bilansData(), stockSnapshotDto);
        }

        if (biznesRadarDocuments.raportBiznes() != null) {
            parseRaportBiznes(biznesRadarDocuments.raportBiznes(), stockSnapshotDto);

        }

        if (biznesRadarDocuments.raportFlow() != null) {
            parseRaportFlow(biznesRadarDocuments.raportFlow(), stockSnapshotDto);
        }

        return stockSnapshotDto;
    }

    private void parseRaportFlow(Document raportFlow, StockSnapshotDto stockSnapshotDto) {
        stockSnapshotDto.setDepreciationAndAmortization(getValue(raportFlow, Fields.DEPRE_AMORT));
    }

    private void parseRaportBiznes(Document raportBiznes, StockSnapshotDto stockSnapshotDto) {
        stockSnapshotDto.setRevenue(getValue(raportBiznes, Fields.REVENUE));

        stockSnapshotDto.setGrossProfit(getValue(raportBiznes, Fields.GROSS_PROFIT));

        stockSnapshotDto.setEbit(getValue(raportBiznes, Fields.EBIT));

        stockSnapshotDto.setNetIncome(getValue(raportBiznes, Fields.NET_PROFIT));
    }

    private void parseBilansData(Document bilansData, StockSnapshotDto stockSnapshotDto) {
        stockSnapshotDto.setEquity(getValue(bilansData, Fields.EQUITY));
    }

    private void parseFlowData(Document flowData, StockSnapshotDto stockSnapshotDto) {
        stockSnapshotDto.setCurrentRatio(getValue(flowData, Fields.CURRENT_RATIO));
    }

    private void parseDebtData(Document debtData, StockSnapshotDto stockSnapshotDto) {
        stockSnapshotDto.setTotalDebt(getValue(debtData, Fields.DTAR));

        stockSnapshotDto.setNetDebt(getValue(debtData, Fields.NETDEBT));

        stockSnapshotDto.setNetFinancialDebt(getValue(debtData, Fields.DEBTFIN));

        stockSnapshotDto.setDebtToEquity(getValue(debtData, Fields.DEBT_EQUITY));

        stockSnapshotDto.setNetDebtToEbitda(getValue(debtData, Fields.NETDEBT_EBITDA));
    }

    private void parseRentData(Document rentData, StockSnapshotDto stockSnapshotDto) {
        stockSnapshotDto.setReturnOnEquity_pct(getValue(rentData, Fields.ROE));

        stockSnapshotDto.setReturnOnAssets_pct(getValue(rentData, Fields.ROA));

        stockSnapshotDto.setOperatingMargin_pct(getValue(rentData, Fields.OPM));

        stockSnapshotDto.setNetProfitMargin_pct(getValue(rentData, Fields.ROS));

        stockSnapshotDto.setSalesMargin_pct(getValue(rentData, Fields.RS));
    }

    private void parseFinancialData(Document financialData, StockSnapshotDto stockSnapshotDto) {
        if (financialData == null)
            return;

        stockSnapshotDto.setPrice(getValue(financialData, Fields.PRICE));

        stockSnapshotDto.setPriceToEarnings(getValue(financialData, Fields.PRICE_TO_EARNINGS));

        stockSnapshotDto.setPriceToBookValue(getValue(financialData, Fields.PRICE_TO_BOOK_VALUE));

        stockSnapshotDto.setPriceToSales(getValue(financialData, Fields.PRICE_TO_SALES));

        stockSnapshotDto.setPriceToOperatingIncome(getValue(financialData, Fields.PRICE_TO_OPERATING_PROFIT));

        stockSnapshotDto.setEvToSales(getValue(financialData, Fields.PRICE_TO_SALES));

        stockSnapshotDto.setEvToEbit(getValue(financialData, Fields.EVEBIT));

        stockSnapshotDto.setEvToEbitda(getValue(financialData, Fields.EVEBITDA));

        stockSnapshotDto.setEarningsPerShare(getValue(financialData, Fields.EARNINGS_PER_SHARE));
    }

    private BigDecimal getValue(Document document, Fields field) {
        String selector = field.getBzSelector();
        Element element = document.selectFirst(selector);
        if(element == null) return null;
        return utils.getBigDecimal(element.text());
    }
}
