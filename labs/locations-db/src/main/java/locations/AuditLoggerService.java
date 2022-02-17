package locations;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuditLoggerService {

    private final AuditLoggerRepository auditLoggerRepo;

    public AuditLoggerService(AuditLoggerRepository auditLoggerRepository) {
        this.auditLoggerRepo = auditLoggerRepository;
    }

    public List<AuditLog> listAuditLogs() {
        return auditLoggerRepo.findAll();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void saveAuditLog(String message) {
        auditLoggerRepo.save(new AuditLog(message));
    }
}
