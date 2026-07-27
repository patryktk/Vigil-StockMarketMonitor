package pl.tkaczyk.scraperservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import model.dto.TickerDto;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.feign.SheetsFeignClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SheetsService {

    private final SheetsFeignClient sheetsFeignClient;


    @CircuitBreaker(name = "sheetsService")
    @Retry(name = "sheetsService")
    public List<TickerDto> getTickersList(){
        return sheetsFeignClient.getTickersList();
    }
}
