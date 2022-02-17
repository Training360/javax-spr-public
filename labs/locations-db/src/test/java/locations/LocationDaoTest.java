package locations;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
//@Sql(scripts = "classpath:/clear.sql")
public class LocationDaoTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    private LocationDao locationDao;

    @Before
    public void init() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testCreateThenList() {
        locationDao.save("Budapest", 23.14, 67.45);
        List<Location> locations = locationDao.findAll();
        assertEquals(1, locations.size());
        assertEquals("Budapest", locations.get(0).getName());
        assertEquals(23.14, locations.get(0).getLat(), 0.005);
        assertEquals(67.45, locations.get(0).getLon(), 0.005);
    }

    @Test
    public void testThenFind() {
        long id = locationDao.save("Budapest", 23.14, 67.45);
        System.out.println(id);
        Location location = locationDao.findLocationById(id);
        assertEquals("Budapest", location.getName());
        assertEquals(23.14, location.getLat(), 0.005);
        assertEquals(67.45, location.getLon(), 0.005);
    }
}
