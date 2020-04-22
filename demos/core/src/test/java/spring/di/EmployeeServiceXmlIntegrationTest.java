package spring.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/application-context.xml")
public class EmployeeServiceXmlIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testSaveThanList() {
        employeeService.saveEmployee("  John Doe   ");
        assertEquals(List.of("John Doe"), employeeService.listEmployees());
    }
}
