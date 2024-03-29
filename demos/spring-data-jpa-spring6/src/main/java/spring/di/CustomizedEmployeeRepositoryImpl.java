package spring.di;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class CustomizedEmployeeRepositoryImpl implements CustomizedEmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> findByNameStartingWith(String namePrefix) {
        return entityManager.createQuery("select e from Employee e where e.name like :namePrefix",
                Employee.class).setParameter("namePrefix", namePrefix + "%").getResultList();
    }
}
