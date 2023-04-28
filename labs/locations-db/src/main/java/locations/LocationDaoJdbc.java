package locations;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationDaoJdbc implements LocationDao {

    private DataSource dataSource;

    public LocationDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Location> findAll() {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from locations");
            ResultSet result = ps.executeQuery()) {
            List<Location> locations = new ArrayList<>();
            while(result.next()) {
                String name = result.getString("name");
                double latitude = result.getDouble("lat");
                double longitude = result.getDouble("lon");
                locations.add(new Location(name, latitude, longitude));
            }
            return locations;
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot read", se);
        }
    }

    @Override
    public long save(String name, double lat, double lon) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into locations(name,lat,lon) values (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setDouble(2, lat);
            ps.setDouble(3, lon);
            ps.executeUpdate();
            return executeAndGetGeneratedKey(ps);
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot insert", se);
        }
    }

    private long executeAndGetGeneratedKey(PreparedStatement stmt) {
        try (
                ResultSet rs = stmt.getGeneratedKeys()
        ) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("No key has generated");
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }

    @Override
    public Location findLocationById(long id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from locations");
            ResultSet result = ps.executeQuery()) {
            if(result.next()) {
                String name = result.getString("name");
                double latitude = result.getDouble("lat");
                double longitude = result.getDouble("lon");
                Location location = new Location(name, latitude, longitude);
                return location;
            }
            throw new IllegalStateException("Not found");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot read", se);
        }
    }
}
