package pl.tkaczyk.sheetsservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class StockDataSheetRow {

    private BigDecimal price;

    private BigDecimal priceToEarnings;
    private BigDecimal priceToBookValue;
    private BigDecimal priceToSales;
    private BigDecimal priceToOperatingIncome;

    private BigDecimal evToSales;
    private BigDecimal evToEbit;
    private BigDecimal evToEbitda;

    private BigDecimal earningsPerShare;


    private BigDecimal totalDebt;
    private BigDecimal netDebt;
    private BigDecimal netFinancialDebt;

    private BigDecimal debtToEquity;
    private BigDecimal netDebtToEbitda;


    private BigDecimal returnOnEquity_pct;
    private BigDecimal returnOnAssets_pct;

    private BigDecimal operatingMargin_pct;
    private BigDecimal netProfitMargin_pct;
    private BigDecimal salesMargin_pct;


    private BigDecimal currentRatio;


    private BigDecimal revenue;
    private BigDecimal grossProfit;
    private BigDecimal ebit;
    private BigDecimal netIncome;
    private BigDecimal depreciationAndAmortization;


    private BigDecimal equity;

    public List<Object> toColumnList() {
        return Arrays.stream(this.getClass().getDeclaredFields())
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return formatValue(String.valueOf((BigDecimal) field.get(this)));
                    } catch (IllegalAccessException e) {
                        return "Error";
                    }
                })
                .collect(Collectors.toList());
    }

    private String formatValue(String val) {
        return (val != null ? val : "No data").replace(",", ".");
    }
}
