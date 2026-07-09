package pl.tkaczyk.sheetsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tkaczyk.sheetsservice.model.dto.TickerDto;
import pl.tkaczyk.sheetsservice.service.SheetsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sheets")
public class SheetsController {

    private final SheetsService sheetsService;

    @GetMapping("/read")
    public ResponseEntity<TickerDto> readTicker(){
        return ResponseEntity.ok(sheetsService.readTicker());
    }

}
