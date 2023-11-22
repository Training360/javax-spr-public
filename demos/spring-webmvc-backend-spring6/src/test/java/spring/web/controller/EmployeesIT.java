package spring.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.web.backend.BackendConfig;
import spring.web.backend.EmployeeService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@ContextHierarchy({@ContextConfiguration(classes = BackendConfig.class),
    @ContextConfiguration(classes = WebConfig.class)})
@WebAppConfiguration
@Sql(statements = "delete from employees")
class EmployeesIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    EmployeeService employeeService;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSaveThenListEmployees() throws Exception {
        mockMvc.perform(post("/")
            .param("name", "John Doe"));

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("employees",
                        hasItem(hasProperty("name", equalTo("John Doe")))))
        .andExpect(content().string(containsString("John Doe")))
            ;
    }
}
