package locations;

public class LocationMemento {
    private Long id;
    private String name;
    private double lat;
    private double lon;

    public LocationMemento(Location location) {
        if(location != null) {
            this.id = location.getId();
            this.name = location.getName();
            this.lat = location.getLat();
            this.lon = location.getLon();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
