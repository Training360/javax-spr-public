package spring.di;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class EmployeeDao {

    private List<String> employees = Collections.synchronizedList(new ArrayList<>());

    public void saveEmployee(String name) {
        employees.add(name);
    }

    public List<String> listEmployees() {
        return new ArrayList<>(employees);
    }

}
