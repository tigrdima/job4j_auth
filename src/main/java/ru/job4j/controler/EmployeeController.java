package ru.job4j.controler;

import java.util.Optional;

import net.jcip.annotations.ThreadSafe;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.model.Employee;
import ru.job4j.model.Person;
import ru.job4j.service.EmployeeService;
import java.util.List;

@ThreadSafe
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RestTemplate rt;

    private final static String API_PERSON = "http://localhost:8080/person/";
    private final static String API_PERSON_ID = "http://localhost:8080/person/{id}/";

    public EmployeeController(EmployeeService employeeService, RestTemplate rt) {
        this.employeeService = employeeService;
        this.rt = rt;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable int id) {
        Optional<Employee> regEmployee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(regEmployee.orElse(null),
                regEmployee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
       return new ResponseEntity<>(
               employeeService.saveOrUpdate(employee), HttpStatus.CREATED
       );
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                employeeService.saveOrUpdate(employee), HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}/person/")
    public ResponseEntity<Employee> addPersonToEmployee(@PathVariable int id, @RequestBody Person person) {
        Optional<Employee> regEmployee = employeeService.findEmployeeById(id);
        if (regEmployee.isPresent()) {
            Employee employee = regEmployee.get();
            Person newPerson = rt.postForObject(API_PERSON, person, Person.class);
            employee.getPersons().add(newPerson);
            return new ResponseEntity<>(employeeService.saveOrUpdate(employee), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/person/")
    public ResponseEntity<Void> updatePersonEmployee(@PathVariable int id, @RequestBody Person person) {
        Optional<Employee> regEmployee = employeeService.findEmployeeById(id);
        if (regEmployee.isPresent()) {
            Employee employee = regEmployee.get();
            rt.put(API_PERSON, person, Person.class);
            employee.getPersons().add(person);
            employeeService.saveOrUpdate(employee);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{eId}/person/{pId}/")
    public ResponseEntity<Void> deletePersonFromEmployee(@PathVariable("eId") int eId, @PathVariable("pId") int pId) {
        Optional<Employee> regEmployee = employeeService.findEmployeeById(eId);
        if (regEmployee.isPresent()) {
            rt.delete(API_PERSON_ID, pId, Person.class);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
