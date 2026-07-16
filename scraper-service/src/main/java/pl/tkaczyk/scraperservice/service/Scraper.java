package pl.tkaczyk.scraperservice.service;

import java.util.Optional;

public interface Scraper<T> {

    Optional<T> scrape(String ticker);
}
