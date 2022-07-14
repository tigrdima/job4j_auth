package ru.job4j.controler;

import net.jcip.annotations.ThreadSafe;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Person;
import ru.job4j.pepository.PersonRepository;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
       Optional<Person> person = personService.findById(id);
       return new ResponseEntity<>(person.orElse(new
               Person()), person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
       );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                personService.create(person), HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        personService.update(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        personService.delete(person);
        return ResponseEntity.ok().build();
    }

}
