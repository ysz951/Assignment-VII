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
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

//Ignore this as it is Spring and not Java EE (Jax-RS) controller
@RestController
@RequestMapping("/contest")
public class ContestController {
    private ContestRepository contestRepository;

    //you should generally favour constructor/setter injection over field injection
    @Autowired
    public ContestController(ContestRepository contestRepository){
        this.contestRepository = contestRepository;
    }

    @Autowired
    private ContestsRepository contestRepo;
    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private PersonRepository personRepo;

    @GetMapping
    public ResponseEntity<List<Contest>> getContest(){
        return new ResponseEntity<>((List<Contest>) contestRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contest> getContestById(@PathVariable long id){
        Contest c = contestRepo.findById(id).orElse(null);
        if (c == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
