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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Ignore this as it is Spring and not Java EE (Jax-RS) controller
@RestController
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

    @PostMapping("/init")
    public ResponseEntity<Contest> createBook() {

        try {
//            Contest newBook = new Contest();
//            newBook.setName("test1");
//            newBook.setCapacity(1);
//            newBook.setDate(new Date());
//            newBook.setRegistration_allowed(true);
//            newBook.setRegistration_from(new Date());
//            newBook.setRegistration_to(new Date());
//            contestRepo.save(newBook);
            init();
//            Contest _book = contestRepo
//                    .save(new Book(book.getTitle(), book.getDescription(), false, null, 0));
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/populate", method = RequestMethod.POST)
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

    private Team createTeam(String name, int rank, Team.State state) {
        Team team = new Team();
        team.setName(name);
        team.setRank(rank);
        team.setState(state);
        teamRepo.save(team);
        return team;
    }

    private Contest createContest(int capacity, Date date, String name, boolean registration_allowed, Date registration_from, Date registration_to) {
        Contest contest = new Contest();
        contest.setName(name);
        contest.setCapacity(capacity);
        contest.setRegistration_allowed(registration_allowed);
        contest.setRegistration_from(registration_from);
        contest.setRegistration_to(registration_to);
        contest.setDate(date);
        contestRepo.save(contest);
        return contest;
    }

    private Person createPerson(Date birthdate, String email, String name, String university) {
        Person person = new Person();
        person.setBirthdate(birthdate);
        person.setEmail(email);
        person.setName(name);
        person.setUniversity(university);
        personRepo.save(person);
        return person;
    }
    private void init(){
        Date date1 = new Date(2006, 11, 12);
        Date date2 = new Date(2015, 3, 21);
        Date date3 = new Date(2016, 9, 24);

        Team team1 = createTeam("team1", 2, Team.State.Pending);
        Team team2 = createTeam("team2", 3, Team.State.Pending);
        Team team3 = createTeam("team3", 4, Team.State.Pending);

        Contest contest = createContest(2, date1, "contest", true, date2, date3);
        Contest subcontest = createContest(3, date2, "subcontest", true, date2, date3);



        List<Person> personList1= new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String email = String.format("student%1$d@contest.com", i);
            String name = String.format("sutdent%1$d", i);
            String university = String.format("BaylorStu%1$d",i);
            personList1.add(createPerson(date1, email, name, university));
        }
        List<Person> personList2= new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String email = String.format("student%1$d@contest.com", i + 3);
            String name = String.format("sutdent%1$d", i);
            String university = String.format("BaylorStu%1$d",i);
            personList2.add(createPerson(date2, email, name, university));
        }
        List<Person> personList3= new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String email = String.format("student%1$d@contest.com", i + 6);
            String name = String.format("sutdent%1$d", i);
            String university = String.format("BaylorStu%1$d",i);
            personList3.add(createPerson(date3, email, name, university));
        }
        Person manager1 = createPerson(date1, "manager1@contest.com", "manager1", null);
        Person manager2 = createPerson(date1, "manager2@contest.com", "manager2", null);

        Person coach1 = createPerson(date1, "coach1@contest.com", "coach1", null);
        Person coach2 = createPerson(date1, "coach2@contest.com", "coach2", null);
        Person coach3 = createPerson(date1, "coach3@contest.com", "coach3", null);

        team1.setCoach(coach1);
        team2.setCoach(coach2);
        team3.setCoach(coach3);
        team1.getStudents().addAll(personList1);
        team2.getStudents().addAll(personList2);
        team3.getStudents().addAll(personList3);
        team1.setContest(subcontest);
        team2.setContest(subcontest);
        team3.setContest(subcontest);
        contest.getManagers().add(manager1);
        subcontest.getManagers().add(manager2);
        subcontest.setParentContest(contest);
    }
}
