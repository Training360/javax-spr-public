package locations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("normal")
public class LocationServiceAspectTest {

    @Autowired
    private CounterAspect counterAspect;
    @Autowired
    private LocationService locationService;

    @Before
    public void reset() {
        counterAspect.reset();
    }

    @Test
    public void testCountBasedOnFirstChar() {
        locationService.createLocation("BP", 29.47, 34.56);
        locationService.createLocation("Budapest", 29.47, 34.56);
        locationService.createLocation("budapest", 29.47, 34.56);
        locationService.createLocation("SP", 17.43, 98.01);
        assertEquals(counterAspect.getMap().get("b").get(), 3);
        assertEquals(counterAspect.getMap().get("s").get(), 1);
    }
}
