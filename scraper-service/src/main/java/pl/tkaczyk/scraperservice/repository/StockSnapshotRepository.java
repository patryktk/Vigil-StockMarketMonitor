package pl.tkaczyk.scraperservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tkaczyk.scraperservice.model.StockSnapshot;

@Repository
public interface StockSnapshotRepository extends CrudRepository<StockSnapshot,Long> {
}
