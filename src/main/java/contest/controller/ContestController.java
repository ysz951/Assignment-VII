package contest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import contest.data.ContestsRepository;
import contest.data.PersonRepository;
import contest.data.TeamRepository;
import contest.model.Contest;
import contest.services.ContestRepository;

//Ignore this as it is Spring and not Java EE (Jax-RS) controller
//@CrossOrigin(origins = "http://localhost:3000")
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
        return new ResponseEntity<>(contestRepository.getContest(), HttpStatus.OK);
//        return new ResponseEntity<>((List<Contest>) contestRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contest> getContestById(@PathVariable long id){
        Contest c = contestRepo.findById(id).orElse(null);
        if (c == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
