package pl.tkaczyk.sheetsservice.service;

import model.dto.InvestDataDto;
import model.dto.TickerDto;

import java.util.List;

public interface GoogleSheetsService {
    List<TickerDto> readTicker();

    void writeData(List<InvestDataDto> payload);
}
