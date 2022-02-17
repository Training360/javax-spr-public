package locations;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LocationService {

    private AuditLoggerService auditLoggerService;

    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo, AuditLoggerService auditLoggerService) {
        this.locationRepo = locationRepo;
        this.auditLoggerService = auditLoggerService;
    }

    public List<Location> listLocations() {
        return locationRepo.findAll();
    }

    @Transactional()
    public void createLocation(String name, double lat, double lon) throws IllegalArgumentException {
        auditLoggerService.saveAuditLog("Location created: " + name);
        locationRepo.save(new Location(name, lat, lon));

        if(name.length() == 0) {
            throw new IllegalArgumentException();
        }
    }
}
