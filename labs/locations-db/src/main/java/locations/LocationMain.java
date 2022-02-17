package locations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LocationMain {

    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(AppConfig.class)) {
            LocationDao locationDao = context.getBean(LocationDaoJdbc.class);
            locationDao.save("Budapest", 12.34, 87.34);
            System.out.println(locationDao.findAll());
        }
    }
}
