package locations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LocationMain {

    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(AppConfig.class)) {

            LocationService locationService = context.getBean(LocationService.class);
            locationService.createLocation("BP", 1.2, 1.5);
            locationService.createLocation("SP", 1.4, 2.6);
            locationService.createLocation("ERD", 0.4, 5.4);

            System.out.println("List all locations: ");
            System.out.println(locationService.listLocations());
            System.out.println("\nGet location 1: ");
            System.out.println(locationService.getLocationById(0).orElse(null));
            System.out.println("\nDelete location 2: ");
            System.out.println(locationService.deleteLocation(1).orElse(null));
            System.out.println("\nList all locations: ");
            System.out.println(locationService.listLocations());
            System.out.println("\nUpdate location 3: ");
            System.out.println(locationService.updateLocation(2, "ERD", 1.2, 5.4).orElse(null));
        }
    }
}
