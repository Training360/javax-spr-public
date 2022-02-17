package locations;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Profile("normal")
@Conditional(ListDaoCondition.class)
public class ListLocationDao implements LocationDao {
    private Long counter = 0L;
    private List<Location> locations = new ArrayList<>();

    @Override
    public List<Location> findAll() {
        return locations;
    }

    @Override
    public Location save(String name, double lat, double lon) {
        Location newLocation = new Location(counter, name, lat, lon);
        locations.add(newLocation);
        counter++;
        return newLocation;
    }

    @Override
    public Optional<Location> findById(long id) {
        return locations.stream().filter(l -> l.getId() == id).findAny();
    }

    @Override
    public Optional<Location> update(long id, String name, double lat, double lon) {
        Optional<Location> location = locations.stream().filter(l -> l.getId() == id).findAny();
        if(location.isPresent()) {
            Location storedLocation = location.get();
            storedLocation.setName(name);
            storedLocation.setLat(lat);
            storedLocation.setLon(lon);
        }
        return location;
    }

    @Override
    public Optional<Location> delete(long id) {
        Optional<Location> location = locations.stream().filter(l -> l.getId() == id).findAny();
        location.ifPresent(value -> locations.remove(value));
        return location;
    }

    @Override
    public void deleteAll() {
        locations.clear();
    }
}
