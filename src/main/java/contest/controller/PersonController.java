package contest.controller;

import contest.data.ContestsRepository;
import contest.data.PersonRepository;
import contest.data.TeamRepository;
import contest.model.*;
import contest.services.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
//import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private PersonRepository personRepo;

    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson(){
        return new ResponseEntity<>((List<Person>) personRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getAllPerson(@PathVariable long id){
        Person p = personRepo.findById(id).orElse(null);
        if (p == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Person>> getPersonName(@PathVariable String name){
        List<Person> personList = personRepo.findByNameContains(name);
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

}
