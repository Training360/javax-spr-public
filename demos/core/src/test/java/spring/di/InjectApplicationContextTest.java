package spring.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class InjectApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testGetEmployeeDao() {
        EmployeeDao employeeDao = (EmployeeDao) applicationContext.getBean("simpleEmployeeDao");
        assertTrue(employeeDao instanceof SimpleEmployeeDao);
    }
}
