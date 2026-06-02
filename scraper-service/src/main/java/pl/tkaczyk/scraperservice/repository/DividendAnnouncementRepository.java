package pl.tkaczyk.scraperservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tkaczyk.scraperservice.model.DividendAnnouncement;

public interface DividendAnnouncementRepository extends JpaRepository<DividendAnnouncement, Long> {
}
