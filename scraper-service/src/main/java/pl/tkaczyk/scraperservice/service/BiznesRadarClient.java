package pl.tkaczyk.scraperservice.service;

import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;

public interface BiznesRadarClient {

    BiznesRadarDocuments makeSnapshot(String ticker);
}
