package spring.di;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
@EnableAspectJAutoProxy
public class AppConfig {

}
