package pl.tkaczyk.scraperservice.service;

import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;
import pl.tkaczyk.scraperservice.model.dto.StrefaInwestorowDocument;

import java.util.Optional;

public interface StrefaInwestorowParser {

    Optional<DividendAnnouncementDto> parse(StrefaInwestorowDocument document, String ticker);
}
