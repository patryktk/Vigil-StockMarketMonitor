package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockSnapshotDto {

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
}

