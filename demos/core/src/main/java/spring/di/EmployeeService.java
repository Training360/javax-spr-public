package spring.di;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeDao employeeDao;

    private MailService mailService;

    private ApplicationEventPublisher applicationEventPublisher;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public EmployeeService(EmployeeDao employeeDao) {
        System.out.println("EmployeeService constructor: " + employeeDao);
        this.employeeDao = employeeDao;
    }

    public void saveEmployee(String name) {
        logger.info("Employee has created with name: {}", name);
        // BL
        String trimmedName = name.trim();
        if (mailService != null) {
            mailService.sendMail();
        }
        if (applicationEventPublisher != null) {
            EmployeeHasCreatedEvent event = new EmployeeHasCreatedEvent(this, name);
            applicationEventPublisher.publishEvent(event);
        }
        employeeDao.saveEmployee(trimmedName);
    }

    public List<String> listEmployees() {
        return employeeDao.listEmployees();
    }

    @Autowired(required = false)
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired(required = false)
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
