package contest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import contest.data.PersonRepository;
import contest.data.TeamRepository;
import contest.model.Person;
import contest.model.Team;
//import java.util.*;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private PersonRepository personRepo;

    @GetMapping
    public ResponseEntity<List<Team>> getTeam(){
        return new ResponseEntity<>((List<Team>) teamRepo.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}/coach")
    public ResponseEntity<?> getAllBooks(@PathVariable Long id, @RequestParam Long coachId) {
        Team t = teamRepo.findById(id).orElse(null);
        if (t == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Person coach = personRepo.findById(id).orElse(null);
        if (coach == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        t.setCoach(coach);
        teamRepo.save(t);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getBookById(@PathVariable Long id) {
        Team t = teamRepo.findById(id).orElse(null);
        if (t == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }


}
