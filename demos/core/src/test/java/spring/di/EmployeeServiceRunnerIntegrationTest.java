package spring.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("postfix")
public class EmployeeServiceRunnerIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testSaveThanList() {
        employeeService.saveEmployee("  John Doe   ");
        assertEquals(List.of("John Doe"), employeeService.listEmployees());
    }
}
