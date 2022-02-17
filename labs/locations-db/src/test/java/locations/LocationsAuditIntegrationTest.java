package locations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class LocationsAuditIntegrationTest {

    @Autowired
    private LocationService locationServiceJpa;
    @Autowired
    private AuditLoggerService loggerService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSaveThenQuery() {
        locationServiceJpa.createLocation("Budapest", 12.34, 87.34);
        List<Location> locations = locationServiceJpa.listLocations();
        List<AuditLog> logs = loggerService.listAuditLogs();

        assertEquals(1, locations.size());
        assertEquals("Budapest", locations.get(0).getName());
        assertEquals(12.34, locations.get(0).getLat(), 0.005);
        assertEquals(87.34, locations.get(0).getLon(), 0.005);
        assertEquals(1, logs.size());
        assertEquals("Location created: Budapest", logs.get(0).getMessage());
    }

    @Test
    public void testSaveEmptyThenQuery() {
        expectedException.expect(IllegalArgumentException.class);
        locationServiceJpa.createLocation("", 12.34, 87.34);
        List<Location> locations = locationServiceJpa.listLocations();
        List<AuditLog> logs = loggerService.listAuditLogs();

        assertEquals(0, locations.size());
        assertEquals(1, logs.size());
        assertEquals("Location created: ", logs.get(0).getMessage());
    }
}
