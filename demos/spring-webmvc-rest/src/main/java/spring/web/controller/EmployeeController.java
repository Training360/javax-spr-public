package spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.web.backend.EmployeeService;
import spring.web.model.Employee;

import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ModelAttribute
    public Employee employee(@PathVariable("id") long id) {
        return employeeService.findEmployeeById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView findEmployeeById() {
        return new ModelAndView("employee");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.updateEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Employee has modified: " + employee.getName());
        return "redirect:/";
    }

//    @ExceptionHandler(EmployeeNotFoundException.class)
//    public ModelAndView handleException(EmployeeNotFoundException e) {
//        return new ModelAndView("employeecontrollererror", Map.of(), HttpStatus.NOT_FOUND);
//    }
}
