package pl.tkaczyk.scraperservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.exception.HtmlFetchException;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;

import java.io.IOException;

@Service
@Slf4j
public class JsoupHtmlFetcher implements HtmlDocumentFetcher {

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

    @Override
    @CircuitBreaker(name = "jsoup")
    @Retry(name = "jsoup", fallbackMethod = "fallback")
    @RateLimiter(name = "jsoup")
    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            log.error("Error while fetching {}", url, e);
            throw new HtmlFetchException("Error while fetching: " + url, e);
        }
    }

    private Document fallback(String url, Exception e) {
        log.error("All retries exhausted for {}", url, e);
        throw new HtmlFetchException("Unable to fetch document after retires: " + url, e);
    }
}
