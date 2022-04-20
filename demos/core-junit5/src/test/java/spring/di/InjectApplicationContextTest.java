package spring.di;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringJUnitConfig(classes = AppConfig.class)
class InjectApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testGetEmployeeDao() {
        EmployeeDao employeeDao = (EmployeeDao) applicationContext.getBean("simpleEmployeeDao");
        assertTrue(employeeDao instanceof SimpleEmployeeDao);
    }
}
