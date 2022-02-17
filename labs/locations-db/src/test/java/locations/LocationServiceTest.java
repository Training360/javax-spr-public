package locations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "classpath:/clear.sql")
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testSaveThenQuery() {
        locationService.createLocation("Budapest", 12.34, 87.34);
        List<Location> locations = locationService.listLocations();
        assertEquals(1, locations.size());
        assertEquals("Budapest", locations.get(0).getName());
        assertEquals(12.34, locations.get(0).getLat(), 0.005);
        assertEquals(87.34, locations.get(0).getLon(), 0.005);
    }
}
