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
public class EmployeeServiceIntegrationTest {

    @Autowired
    private CounterAspect counterAspect;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testSaveThanList() {
        counterAspect.reset();
        employeeService.saveEmployee("  John Doe   ");
        employeeService.saveEmployee("  John Doe   ");
        employeeService.saveEmployee("  John Doe   ");
        employeeService.listEmployees();
        System.out.println(employeeService.listEmployees());

        assertEquals(5, counterAspect.getCounter());
    }
}
