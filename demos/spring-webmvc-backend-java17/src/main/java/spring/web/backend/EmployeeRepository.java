package spring.web.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.web.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
