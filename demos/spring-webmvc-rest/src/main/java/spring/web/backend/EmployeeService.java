package spring.web.backend;

import org.springframework.stereotype.Service;
import spring.web.controller.EmployeeNotFoundException;
import spring.web.model.Employee;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void saveEmployee(String name) {
        employeeRepository.save(new Employee(name));
    }

    public List<Employee> listEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        Employee employeeToModify = employeeRepository.getOne(employee.getId());
        employeeToModify.setName(employee.getName());
    }

}
