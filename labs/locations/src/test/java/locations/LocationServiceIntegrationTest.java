package locations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("normal")
public class LocationServiceIntegrationTest {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private LocationService locationService;

    @Before
    public void clearList() {
        locationDao.deleteAll();
    }

    @Test
    public void testFindAll() {
        locationService.createLocation("BP", 1.2, 1.4);
        locationService.createLocation("SP", 2.5, 3.4);
        assertEquals(Arrays.asList("BP", "SP"), locationService.listLocations().stream()
                .map(Location::getName).collect(Collectors.toList()));
    }

    @Test
    public void testFindById() {
        Location saved = locationService.createLocation("BP", 1.2, 1.4);
        Location location = locationService.getLocationById(saved.getId()).orElseThrow(() -> new IllegalStateException("Not found"));
        assertEquals(saved, location);
    }

    @Test
    public void update() {
        Location saved = locationService.createLocation("BP", 1.2, 1.4);
        locationService.updateLocation(saved.getId(), "ERD", 1.4, 1.7);
        Location updated = locationService.getLocationById(saved.getId()).orElseThrow(() -> new IllegalStateException("Not found"));

        assertEquals("ERD", updated.getName());
        assertEquals(1, updated.getLat(), 4);
        assertEquals(1, updated.getLon(),7);
    }

    @Test
    public void delete() {
        Location saved = locationDao.save("BP", 1.2, 1.4);
        locationDao.delete(saved.getId());
        assertTrue(locationDao.findAll().isEmpty());
    }
}
