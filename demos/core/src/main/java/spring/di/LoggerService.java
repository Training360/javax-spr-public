package spring.di;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class LoggerService implements ApplicationListener<EmployeeHasCreatedEvent> {

    @Override
    public void onApplicationEvent(EmployeeHasCreatedEvent event) {
        String name = event.getName();
        System.out.println("Employee has created: " + name);
    }
}
