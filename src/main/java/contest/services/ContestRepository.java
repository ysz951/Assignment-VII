package contest.services;

import contest.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import contest.model.Team.State;
//Spring annotations, feel free to ignore it
@Repository
@Transactional
public class ContestRepository {

    @PersistenceContext
    private EntityManager em;

    public void populate(){
        Date date1 = new Date(2006, 11, 12);
        Date date2 = new Date(2015, 3, 21);
        Date date3 = new Date(2016, 9, 24);

        Team team1 = createTeam("team1", 2, State.Pending);
        Team team2 = createTeam("team2", 3, State.Pending);
        Team team3 = createTeam("team3", 4, State.Pending);

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

    @SuppressWarnings("unchecked")
    public List<Team> getTeam(){
        return em.createQuery("SELECT t FROM Team t").getResultList();
    }



    @SuppressWarnings("unchecked")
    public List<Contest> getContest(){
        return em.createQuery("SELECT c FROM Contest c").getResultList();
    }


    @SuppressWarnings("unchecked")
    public List<Person> getPersonName(String name){

        return em.createQuery("SELECT s FROM Person s WHERE s.name LIKE :name").setParameter("name", "%"+name+"%").getResultList();
//        return em.createQuery("SELECT s FROM Person s WHERE s.name = :name").setParameter("name",name).getResultList();
    }

    public List<Person> getAllPerson(){
        return em.createQuery("SELECT s FROM Person s").getResultList();
    }

//    @SuppressWarnings("unchecked")
//    public List<Person> getPersonAge(){
//        return em.createQuery("SELECT COUNT(DISTINCT ts) AS student_number, ts.birthdate AS birthdate FROM Team t JOIN t.students ts GROUP BY ts.birthdate").getResultList();
//    }

//    public int getPerson(){
//        String cunt = em
//                .createQuery("SELECT COUNT(DISTINCT ts) FROM Team t JOIN t.students ts").getSingleResult().toString();
//        int g = Integer.valueOf(cunt);
//        return g;
//    }

//    @SuppressWarnings("unchecked")
//    public int getCapacity(){
//        List<Contest> teamList = em.createQuery("SELECT c FROM Contest c").getResultList();
//        Contest contest = teamList.get(0);
//        int g = contest.getManagers().size();
//        return g;
//    }
//
//    @SuppressWarnings("unchecked")
//    public int getTeamNumber(){
//        List<Contest> teamList = em.createQuery("SELECT c FROM Contest c").getResultList();
//        Contest contest = teamList.get(0);
//        int g = contest.getTeams().size();
//        return g;
//    }

    private Team createTeam(String name, int rank, State state) {
        Team team = new Team();
        team.setName(name);
        team.setRank(rank);
        team.setState(state);
        em.persist(team);
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
        em.persist(contest);
        return contest;
    }

    private Person createPerson(Date birthdate, String email, String name, String university) {
        Person person = new Person();
        person.setBirthdate(birthdate);
        person.setEmail(email);
        person.setName(name);
        person.setUniversity(university);
        em.persist(person);
        return person;
    }
}
