package spring.di;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Simple
@Conditional(NotPostfixCondition.class)
public class SimpleEmployeeDao implements EmployeeDao {

    private List<String> employees = Collections.synchronizedList(new ArrayList<>());

    public SimpleEmployeeDao() {
        System.out.println("EmployeeDao constructor");
    }

    @Override
    public void saveEmployee(String name) {
        employees.add(name);
    }

    @Override
    public List<String> listEmployees() {
        return new ArrayList<>(employees);
    }

}
