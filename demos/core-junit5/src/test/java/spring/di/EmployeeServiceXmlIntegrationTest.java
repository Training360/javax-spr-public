package spring.di;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(locations = "classpath:/application-context.xml")
class EmployeeServiceXmlIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testSaveThanList() {
        employeeService.saveEmployee("  John Doe   ");
        assertEquals(List.of("John Doe"), employeeService.listEmployees());
    }
}
