package spring.di;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class EmployeeServiceIntegrationTest {

    @Test
    void testSaveThanList() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            EmployeeService employeeService = context.getBean(EmployeeService.class);
            employeeService.saveEmployee("  John Doe  ");
            assertEquals(List.of("John Doe"), employeeService.listEmployees());
        }
    }
}
