package locations;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("normal")
public class LocationChangedEventTest {

    @Autowired
    private NameChangeListener listener;
    @Autowired
    private LocationService locationService;

    @After
    public void clearListenerCache() {
        listener.deleteAll();
    }

    @Test
    public void changedLocationName() {
        locationService.createLocation("BP", 1.0, 1.0);
        locationService.createLocation("SP", 2.5, 1.5);
        List<Location> locations = locationService.listLocations();
        locationService.updateLocation(locations.get(0).getId(), "ERD", 1.2, 1.0);
        List<String> changes = listener.getChanges();
        assertEquals(changes.size(), 1);
        assertEquals(changes.get(0), "BP -> ERD");
    }

    @Test
    public void changedLocationCoordinates() {
        locationService.createLocation("BP", 1.0, 1.0);
        locationService.createLocation("SP", 2.5, 1.5);
        List<Location> locations = locationService.listLocations();
        locationService.updateLocation(locations.get(0).getId(), "BP", 1.2, 2.7);
        List<String> changes = listener.getChanges();
        assertEquals(changes.size(), 0);
    }
}
