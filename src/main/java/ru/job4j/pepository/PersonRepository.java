package ru.job4j.pepository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Person;

@ThreadSafe
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
