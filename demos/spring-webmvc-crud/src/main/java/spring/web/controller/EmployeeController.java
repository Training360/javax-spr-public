package spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.web.backend.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ModelAttribute
    public spring.model.Employee employee(@PathVariable("id") long id) {
        return employeeService.findEmployeeById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView findEmployeeById() {
        return new ModelAndView("employee");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute spring.model.Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.updateEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Employee has modified: " + employee.getName());
        return "redirect:/";
    }
}
