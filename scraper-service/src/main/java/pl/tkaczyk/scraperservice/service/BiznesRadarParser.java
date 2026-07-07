package pl.tkaczyk.scraperservice.service;

import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;

public interface BiznesRadarParser {

    StockSnapshotDto parse(BiznesRadarDocuments documents);
}
