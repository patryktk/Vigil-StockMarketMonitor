package pl.tkaczyk.scraperservice.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tkaczyk.scraperservice.service.impl.StrefaInwestorowClient;

@RestController
@AllArgsConstructor
public class TestController {

    private StrefaInwestorowClient client;

    @GetMapping("/test")
    public void get(){
        client.makeSnapshot("XTB");
    }


}
