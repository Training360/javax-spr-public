package locations;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    List<Location> findAll();

    Location save(String name, double lat, double lon);

    Optional<Location> findById(long id);

    Optional<Location> update(long id, String name, double lat, double lon);

    Optional<Location> delete(long id);

    void deleteAll();
}
