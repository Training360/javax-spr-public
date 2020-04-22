package spring.di;

import java.util.List;

public interface EmployeeDao {
    void saveEmployee(String name);

    List<String> listEmployees();
}
