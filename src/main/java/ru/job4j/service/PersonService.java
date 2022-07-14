package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.model.Person;
import ru.job4j.pepository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        return personList;
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public void update(Person person) {
        personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

}
