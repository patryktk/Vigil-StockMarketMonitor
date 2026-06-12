package pl.tkaczyk.scraperservice.model.enums;

import lombok.Getter;

@Getter
public enum Fields {
    PRICE("Price", "Cena akcji", "Quote"),

    PRICE_TO_EARNINGS("Price to Earnings", "Wskaźnik cena/zysk", "CZ"),
    PRICE_TO_BOOK_VALUE("Price to Book Value", "Wskaźnik cena/wartość księgowa", "CWK"),
    PRICE_TO_SALES("Price to Sales", "Wskaźnik cena/przychody", "CP"),
    PRICE_TO_OPERATING_PROFIT("Price to Operating Profit", "Wskaźnik cena/zysk operacyjny", "CZO"),

    EVP("EV to Sales", "EV/przychody", "EVP"),
    EVEBIT("EV to EBIT", "EV/EBIT", "EVEBIT"),
    EVEBITDA("EV to EBITDA", "EV/EBITDA", "EVEBITDA"),

    EARNINGS_PER_SHARE("Earnings per Share", "Zysk na akcję (EPS)", "Z"),

    ROE("Return on Equity", "Rentowność kapitału własnego (ROE)", "ROE"),
    ROA("Return on Assets", "Rentowność aktywów (ROA)", "ROA"),
    OPM("Operating Profit Margin", "Marża operacyjna", "OPM"),
    ROS("Net Profit Margin", "Marża netto", "ROS"),
    RS("Sales Margin", "Marża ze sprzedaży", "RS"),

    DTAR("Total Debt", "Zadłużenie ogółem", "DTAR"),
    NETDEBT("Net Debt", "Dług netto", "NetDebt"),
    DEBTFIN("Net Financial Debt", "Dług finansowy netto", "DebtFin"),
    DEBT_EQUITY("Debt to Equity", "Dług do kapitału własnego", "CG"),
    NETDEBT_EBITDA("Net Debt to EBITDA", "Dług netto / EBITDA", "NetDebtEBITDA"),

    CURRENT_RATIO("Current Ratio", "Wskaźnik płynności bieżącej", "CR"),

    EQUITY("Equity", "Kapitał własny", "BalanceCapital"),

    REVENUE("Revenue", "Przychody", "IncomeRevenues"),
    GROSS_PROFIT("Gross Profit", "Zysk brutto", "IncomeGrossProfit"),
    EBIT("EBIT", "Zysk operacyjny (EBIT)", "IncomeEBIT"),
    NET_PROFIT("Net Profit", "Zysk netto", "IncomeNetProfit"),

    DEPRE_AMORT("Depreciation and Amortization", "Amortyzacja", "CashflowAmortization");

    final String nameEN;
    final String namePL;
    final String bzName;

    Fields(String nameEN, String namePL, String bzName) {
        this.nameEN = nameEN;
        this.namePL = namePL;
        this.bzName = bzName;
    }

    public String getBzSelector() {
        return "tr[data-field=" + bzName + "] td.h.newest .value .pv span";
    }
}
