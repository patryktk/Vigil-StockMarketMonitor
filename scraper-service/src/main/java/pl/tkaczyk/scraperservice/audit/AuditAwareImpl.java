package pl.tkaczyk.scraperservice.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware {

    /**
     * Returns the current user.
     * @return
     */
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("admin");
    }
}
