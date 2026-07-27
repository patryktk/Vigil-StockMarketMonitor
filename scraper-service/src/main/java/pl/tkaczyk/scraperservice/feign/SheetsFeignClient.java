package pl.tkaczyk.scraperservice.feign;

import model.dto.TickerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "sheets-service", url = "${sheets-service.url}")
public interface SheetsFeignClient {

    @GetMapping("/api/sheets/readTickersList")
    List<TickerDto> getTickersList();

}
