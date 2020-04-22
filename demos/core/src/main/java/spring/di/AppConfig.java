package spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
@PropertySource("classpath:/application.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public String applicationVersion() {
        String version = environment.getProperty("application.version");
        System.out.println("Version: " + version);
        System.out.println("OS: " + environment.getProperty("OS"));
        return version;
    }

//    @Bean
//    public EmployeeService employeeService() {
//        return new EmployeeService(employeeDao());
//    }
//
//    @Bean
//    public EmployeeDao employeeDao() {
//        EmployeeDao employeeDao = new EmployeeDao();
//        return employeeDao;
//    }

}
