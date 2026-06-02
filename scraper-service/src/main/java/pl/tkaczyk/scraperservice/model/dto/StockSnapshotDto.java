package pl.tkaczyk.scraperservice.model.dto;

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

    // =========================
    // Valuation (wycena)
    // =========================
    private BigDecimal price;

    private BigDecimal priceToEarnings;        // P/E
    private BigDecimal priceToBookValue;       // P/BV
    private BigDecimal priceToSales;           // P/S
    private BigDecimal priceToOperatingIncome;  // P/OP

    private BigDecimal evToSales;              // EV/Sales
    private BigDecimal evToEbit;              // EV/EBIT
    private BigDecimal evToEbitda;            // EV/EBITDA

    private BigDecimal earningsPerShare;       // EPS

    // =========================
    // Debt (zadłużenie)
    // =========================
    private BigDecimal totalDebt;
    private BigDecimal netDebt;
    private BigDecimal netFinancialDebt;

    private BigDecimal debtToEquity;
    private BigDecimal netDebtToEbitda;

    // =========================
    // Profitability (rentowność)
    // =========================
    private BigDecimal returnOnEquity_pct; // ROE
    private BigDecimal returnOnAssets_pct;  // ROA

    private BigDecimal operatingMargin_pct;
    private BigDecimal netProfitMargin_pct;
    private BigDecimal salesMargin_pct;

    // =========================
    // Liquidity (płynność)
    // =========================
    private BigDecimal currentRatio;

    // =========================
    // Financial results (wyniki)
    // =========================
    private BigDecimal revenue;
    private BigDecimal grossProfit; // zysk ze sprzedaży
    private BigDecimal ebit;
    private BigDecimal netIncome;
    private BigDecimal depreciationAndAmortization;

    // =========================
    // Equity
    // =========================
    private BigDecimal equity;

}
