package locations;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
@PropertySource("classpath:/application.properties")
@EnableAspectJAutoProxy
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Scope("prototype")
    public Location templateLocation() {
        String name = environment.getProperty("template.location.name");
        double lat = Double.parseDouble(environment.getProperty("template.location.lat"));
        double lon = Double.parseDouble(environment.getProperty("template.location.lon"));
        return new Location(0L, name, lat, lon);
    }

//    @Bean
//    public LocationDao locationDao() {
//        return new LocationDao();
//    }
//
//    @Bean
//    public LocationService locationService(ApplicationContext context) {
//        return new LocationService(locationDao(), context);
//    }

}
