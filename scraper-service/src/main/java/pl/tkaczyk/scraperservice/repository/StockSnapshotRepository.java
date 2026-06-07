package pl.tkaczyk.scraperservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaczyk.scraperservice.model.StockSnapshot;

@Repository
public interface StockSnapshotRepository extends JpaRepository<StockSnapshot, Long> {
}
