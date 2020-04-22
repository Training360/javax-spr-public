package spring.web.controller;

import org.springframework.web.bind.annotation.*;
import spring.web.backend.EmployeeService;
import spring.web.model.Employee;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeesRestController {

    private EmployeeService employeeService;

    public EmployeesRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> employees() {
        return employeeService.listEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee findEmployeeById(@PathVariable("id") long id) {
        return employeeService.findEmployeeById(id);
    }

    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee.getName());
    }
}
