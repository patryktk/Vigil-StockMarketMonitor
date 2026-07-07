package pl.tkaczyk.scraperservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DividendAnnouncementDto {

    private LocalDate lastDateToBuy;
    private BigDecimal dividendYield_pct;
    private BigDecimal amountPerStock;
    private LocalDate payDate;

}
