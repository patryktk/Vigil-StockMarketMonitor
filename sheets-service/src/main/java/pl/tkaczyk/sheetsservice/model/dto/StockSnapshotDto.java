package pl.tkaczyk.sheetsservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockSnapshotDto {
    private String ticker;
    private String name;
    private String sector;

    // Dane z BiznesRadarScraperService
    private BigDecimal kurs; // price
    private BigDecimal cenaZysk; // P/E
    private BigDecimal cenaWartoscKsiegowa; // P/BV
    private BigDecimal cenaPrzychodyZeSprzedazy; // P/S
    private BigDecimal zyskNaAkcje; // EPS
    private BigDecimal cenaZyskOperacyjny; // Price/Operating Income
    private BigDecimal evPrzychodyZeSprzedazy; // EV/Sales
    private BigDecimal evEbit; // EV/EBIT
    private BigDecimal evEbitda; // EV/EBITDA

    private BigDecimal zadluzenieOgolne;
    private BigDecimal zadluzenieKaptialuWlasnego;
    private BigDecimal zadluzenieNetto;
    private BigDecimal zadluzenieNettoEbitda;
    private BigDecimal zadluzenieFinansoweNetto;

    private BigDecimal kapitalWlasny;

    private BigDecimal roe;
    private BigDecimal roa;
    private BigDecimal marzaZyskuOperacyjnego;
    private BigDecimal marzaZyskuNetto;
    private BigDecimal marzeZyskuZeSprzedazy;
    private BigDecimal roic;

    private BigDecimal plynnoscBiezaca;

    private BigDecimal przychodyZSprzedazy;
    private BigDecimal zyskZeSprzedaz;
    private BigDecimal ebit;
    private BigDecimal amortyzacja;
    private BigDecimal zyskNetto;
    private BigDecimal ebitPrzychody;

    private BigDecimal zyskNettoNaAkcje3Lata;
    private BigDecimal zyskNettoNaAkcjeO4K;
    private BigDecimal przychodyZSprzedazyO4K;
    private BigDecimal zyskNettoO4K;
    private BigDecimal ebitda;
    private BigDecimal ebitdaPrzychody;
    private BigDecimal peg;
    private BigDecimal sma50;
    private BigDecimal sma200;
    private BigDecimal momentum;
    private BigDecimal rsi;
}
