package spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ModelAndView handleException(EmployeeNotFoundException e) {
        return new ModelAndView("employeecontrolleradviceerror", Map.of(), HttpStatus.NOT_FOUND);
    }
}
