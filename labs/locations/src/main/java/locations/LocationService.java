package locations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ApplicationContext applicationContext;
    private final LocationDao locationDao;

    public LocationService(ApplicationContext applicationContext, LocationDao locationDao) {
        this.applicationContext = applicationContext;
        this.locationDao = locationDao;
    }

    public Location createLocationTemplate() {
        return applicationContext.getBean(Location.class);
    }

    public List<Location> listLocations() {
        return locationDao.findAll();
    }

    public Location createLocation(String name, double lat, double lon) {
        return locationDao.save(name, lat, lon);
    }

    public Optional<Location> getLocationById(long id) {
        return locationDao.findById(id);
    }

    public Optional<Location> updateLocation(long id, String name, double lat, double lon) {
        logger.debug("Item updated with new values: {}, {}, {}", name, lat, lon);
        Optional<Location> found = getLocationById(id);
        if (found.isPresent()) {
            LocationMemento oldLocation = new LocationMemento(getLocationById(id).get());
            locationDao.update(id, name, lat, lon);
            LocationMemento newLocation = new LocationMemento(found.get());
            LocationHasChangedEvent event = new LocationHasChangedEvent(this, oldLocation, newLocation);
            applicationContext.publishEvent(event);
        }
        return found;
    }

    public Optional<Location> deleteLocation(long id) {
        return locationDao.delete(id);
    }

}
