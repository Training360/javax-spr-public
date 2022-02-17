package locations;

import java.util.List;

public interface LocationDao {
    List<Location> findAll();

    long save(String name, double lat, double lon);

    Location findLocationById(long id);
}
