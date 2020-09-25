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
    @RequestMapping(value = "/initialize", method = RequestMethod.GET)
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

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<Integer> getPerson(){
        return new ResponseEntity(contestRepository.getPerson(),HttpStatus.OK);
    }

    @RequestMapping(value = "/capacity", method = RequestMethod.GET)
    public ResponseEntity<Integer> getCapacity(){
        return new ResponseEntity(contestRepository.getCapacity(),HttpStatus.OK);
    }

    @RequestMapping(value = "/age", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonAge(){
        return new ResponseEntity(contestRepository.getPersonAge(),HttpStatus.OK);
    }

    @RequestMapping(value = "/team_number", method = RequestMethod.GET)
    public ResponseEntity<Person> getTeamNumber(){
        return new ResponseEntity(contestRepository.getTeamNumber(),HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Person> getCoursesByStudentNamePar(@PathVariable String name){
        return new ResponseEntity(contestRepository.getPersonName(name),HttpStatus.OK);
    }
}
