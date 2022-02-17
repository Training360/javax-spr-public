package locations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLoggerRepository extends JpaRepository<AuditLog, Long> {
}
