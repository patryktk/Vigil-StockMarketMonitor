package pl.tkaczyk.sheetsservice.service.impl;

import com.google.api.services.sheets.v4.Sheets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaczyk.sheetsservice.model.dto.TickerDto;
import pl.tkaczyk.sheetsservice.service.SheetsService;

@Service
@RequiredArgsConstructor
public class SheetsServiceImpl implements SheetsService {


    private final Sheets sheetsClient;


    @Override
    public TickerDto readTicker() {
        return null;
    }
}
