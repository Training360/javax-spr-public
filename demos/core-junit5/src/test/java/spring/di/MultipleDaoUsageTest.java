package spring.di;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = AppConfig.class)
class MultipleDaoUsageTest {

    @Autowired
    private MultipleDaoUsage multipleDaoUsage;

    @Test
    void testMultiple() {
        multipleDaoUsage.saveEmployee("John Doe");
        System.out.println(multipleDaoUsage.listEmployees());
    }
}
