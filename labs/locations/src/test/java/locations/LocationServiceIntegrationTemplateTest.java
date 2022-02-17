package locations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("normal")
public class LocationServiceIntegrationTemplateTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testCreateTemplates() {
        Location location1 = locationService.createLocationTemplate();
        Location location2 = locationService.createLocationTemplate();
        assertFalse(location1 == location2);
    }
}
