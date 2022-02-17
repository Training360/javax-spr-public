package locations;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NameChangeListener implements ApplicationListener<LocationHasChangedEvent> {
    private List<String> locationNameChanges = new ArrayList<>();

    @Override
    public void onApplicationEvent(LocationHasChangedEvent locationHasChangedEvent) {
        LocationMemento oldLocation = locationHasChangedEvent.getOldLocation();
        LocationMemento newLocation = locationHasChangedEvent.getNewLocation();
        if(!oldLocation.getName().equals(newLocation.getName())) {
            locationNameChanges.add(oldLocation.getName() + " -> " + newLocation.getName());
        }
    }

    public void deleteAll() {
        locationNameChanges.clear();
    }

    public List<String> getChanges() {
        return locationNameChanges;
    }
}
