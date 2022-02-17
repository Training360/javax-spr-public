package locations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class LocationDaoJdbcTemplate implements LocationDao {

    private JdbcTemplate jdbcTemplate;

    public LocationDaoJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Location> findAll() {
        return jdbcTemplate.query("select * from locations order by name",
                (rs, rowNum) -> new Location(rs.getString("name"),
                        rs.getDouble("lat"), rs.getDouble("lon")));
    }

    public long save(String name, double lat, double lon) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement("insert into locations(name,lat,lon) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDouble(2, lat);
            ps.setDouble(3, lon);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Location findLocationById(long id) {
        return jdbcTemplate.queryForObject("select * from locations where id = ?",
                new Object[]{id}, (rs, i) -> new Location(rs.getString("name"),
                        rs.getDouble("lat"), rs.getDouble("lon")));
    }
}
