package spring.di;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "classpath:/clear.sql")
class EmployeeDaoTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveThenList() {
        employeeRepository.save(new Employee("John Doe"));

        List<String> names = StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .map(Employee::getName).collect(Collectors.toList());

        assertEquals(List.of("John Doe"), names);
    }

    @Test
    void testPageable() {
        for (int i = 100; i < 130; i++) {
            String name = "John Doe " + i;
            employeeRepository.save(new Employee(name));
        }

        Page<Employee> page = employeeRepository.findAll(PageRequest.of(2, 10, Sort.by("name")));

        assertEquals(30, page.getTotalElements());
        assertEquals(2, page.getNumber());
        assertEquals(10, page.getNumberOfElements());

        assertEquals("John Doe 120", page.getContent().get(0).getName());
        assertEquals("John Doe 129", page.getContent().get(9).getName());
    }

    @Test
    void testFindByNameIgnoreCase() {
        employeeRepository.save(new Employee("John Doe"));
        employeeRepository.save(new Employee("Jack Doe"));

        List<Employee> employees = employeeRepository.findByNameIgnoreCase("john doe");

        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    void testFindByNameLength() {
        employeeRepository.save(new Employee("John Doe"));
        employeeRepository.save(new Employee("John John Doe"));

        List<Employee> employees = employeeRepository.findByNameLength(8);

        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    void testFindByNameStartingWith() {
        employeeRepository.save(new Employee("John Doe"));

        List<Employee> employees = employeeRepository.findByNameStartingWith("Jo");

        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }
}
