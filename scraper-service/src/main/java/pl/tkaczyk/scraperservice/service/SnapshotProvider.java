package pl.tkaczyk.scraperservice.service;

import java.util.Optional;

public interface SnapshotProvider<T> {
    Optional<T> makeSnapshot(String ticker);
}
