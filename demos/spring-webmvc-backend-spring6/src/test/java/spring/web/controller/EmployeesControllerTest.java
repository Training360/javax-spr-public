package spring.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;
import spring.web.backend.EmployeeService;
import spring.web.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeesControllerTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeesController employeesController;

    @Test
    void testListEmployees() {
        when(employeeService.listEmployees()).thenReturn(List.of(new Employee("John Doe")));

        ModelAndView modelAndView = employeesController.listEmployees();
        assertEquals("index", modelAndView.getViewName());
        assertEquals(List.of("John Doe"), ((List<Employee>) modelAndView.getModel().get("employees"))
                .stream().map(Employee::getName).collect(Collectors.toList()));

    }
}
