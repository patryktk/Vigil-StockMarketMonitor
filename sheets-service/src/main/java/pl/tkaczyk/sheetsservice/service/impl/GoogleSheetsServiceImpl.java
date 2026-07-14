package pl.tkaczyk.sheetsservice.service.impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaczyk.sheetsservice.config.GoogleSheetsProperties;
import pl.tkaczyk.sheetsservice.mapper.GoogleSheetMapper;
import pl.tkaczyk.sheetsservice.model.dto.StockSnapshotDto;
import pl.tkaczyk.sheetsservice.model.dto.TickerDto;
import pl.tkaczyk.sheetsservice.service.GoogleSheetsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleSheetsServiceImpl implements GoogleSheetsService {


    private final Sheets sheetsClient;
    private final GoogleSheetsProperties googleSheetsProperties;
    private final GoogleSheetMapper googleSheetMapper;

    @Override
    public List<TickerDto> readTicker() {

        try {
            ValueRange execute = sheetsClient.spreadsheets().values().get(googleSheetsProperties.getSpreadsheetId(), googleSheetsProperties.getTickerRange()).execute();
            List<List<Object>> tickersValues = execute.getValues();

            return googleSheetMapper.mapToTickerList(tickersValues);

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void writeData(List<StockSnapshotDto> payload) {

    }
}
