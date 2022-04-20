package spring.di;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeDaoTest {

    EmployeeDao employeeDao = new SimpleEmployeeDao();

    @Test
    void testSaveThanList() {
        employeeDao.saveEmployee("John Doe");
        assertEquals(List.of("John Doe"), employeeDao.listEmployees());
    }

}
