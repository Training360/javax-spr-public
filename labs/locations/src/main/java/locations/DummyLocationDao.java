package locations;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("dummy")
@Conditional(DummyDaoCondition.class)
public class DummyLocationDao implements LocationDao {

    @Override
    public List<Location> findAll() {
        return null;
    }

    @Override
    public Location save(String name, double lat, double lon) {
        return null;
    }

    @Override
    public Optional<Location> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Location> update(long id, String name, double lat, double lon) {
        return Optional.empty();
    }

    @Override
    public Optional<Location> delete(long id) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
