package ru.job4j.pepository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Employee;

@ThreadSafe
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
