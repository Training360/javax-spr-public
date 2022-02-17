package locations;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("dummy")
@TestPropertySource(properties = "mode=dummy")
public class LocationServiceDummyProfileTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testCreateTemplates() {
        locationService.createLocation("BP", 12.3, 56.34);
        assertNull(locationService.listLocations());
    }
}
