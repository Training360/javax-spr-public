package spring.di;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeServiceIntegrationTest {

    @Test
    public void testSaveThanList() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            EmployeeService employeeService = context.getBean(EmployeeService.class);
            employeeService.saveEmployee("  John Doe  ");
            assertEquals(List.of("John Doe"), employeeService.listEmployees());
        }
    }
}
