package locations;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LocationDaoTest {

    private LocationDao locationDao = new ListLocationDao();

    @Test
    public void saveThenList() {
        locationDao.save("BP", 1.0, 1.0);
        locationDao.save("SP", 2.5, 1.5);
        assertEquals(Arrays.asList("BP", "SP"), locationDao.findAll().stream()
                .map(Location::getName).collect(Collectors.toList()));
    }
}
