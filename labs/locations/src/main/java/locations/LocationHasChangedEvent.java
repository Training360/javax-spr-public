package locations;

import org.springframework.context.ApplicationEvent;

public class LocationHasChangedEvent extends ApplicationEvent {
    private LocationMemento oldLocation;
    private LocationMemento newLocation;

    public LocationHasChangedEvent(Object source, LocationMemento oldLocation, LocationMemento newLocation) {
        super(source);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public LocationMemento getOldLocation() {
        return oldLocation;
    }

    public LocationMemento getNewLocation() {
        return newLocation;
    }
}
