package contest.controller;

import contest.model.*;
import contest.services.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
//Ignore this as it is Spring and not Java EE (Jax-RS) controller
@RestController
public class ContestController {
    private ContestRepository contestRepository;

    //you should generally favour constructor/setter injection over field injection
    @Autowired
    public ContestController(ContestRepository contestRepository){
        this.contestRepository = contestRepository;
    }

    //very bad practise - using GET method to insert something to DB
    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public ResponseEntity populate(){
        contestRepository.populate();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ResponseEntity<Team> getTeam(){
        return new ResponseEntity(contestRepository.getTeam(),HttpStatus.OK);
    }

    @RequestMapping(value = "/contests", method = RequestMethod.GET)
    public ResponseEntity<Contest> getContest(){
        return new ResponseEntity(contestRepository.getContest(),HttpStatus.OK);
    }

    @RequestMapping(value = "/person/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonName(@PathVariable String name){
        return new ResponseEntity(contestRepository.getPersonName(name),HttpStatus.OK);
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<Person> getAllPerson(){
        return new ResponseEntity(contestRepository.getAllPerson(),HttpStatus.OK);
    }
}
