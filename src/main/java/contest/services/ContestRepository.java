package contest.services;

import contest.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        Team team1 = createTeam("testName", 1, State.Pending);
        Team team2 = createTeam("testNam2", 3, State.Pending);

        Contest contest1 = createContest(2, date1, "TestContest1", true, date2, date3);
//        contest1.getTeams().add(team1);
        team1.setContest(contest1);
        team2.setContest(contest1);
        Person manager1 = createPerson(date1, "test1@contest.com", "manager1", "Baylor1");
        Person manager2 = createPerson(date2, "test2@contest.com", "manager2", "Baylor2");
        Person manager3 = createPerson(date3, "test3@contest.com", "manager3", "Baylor3");
        Person student1 = createPerson(date1, "student1@contest.com", "student1", "BaylorStu1");
        Person student2 = createPerson(date2, "student2@contest.com", "student2", "BaylorStu2");
        Person student3 = createPerson(date3, "student3@contest.com", "student3", "BaylorStu3");
        team1.getStudents().addAll(Arrays.asList(student1, student2));
        team2.getStudents().addAll(Arrays.asList(student3, student2));
        contest1.getManagers().add(manager1);
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
    public List<Person> getPersonAge(){
        return em.createQuery("SELECT COUNT(DISTINCT ts) AS student_number, ts.birthdate AS birthdate FROM Team t JOIN t.students ts GROUP BY ts.birthdate").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Person> getPersonName(String name){
        return em.createQuery("SELECT s FROM Person s WHERE s.name = :name").setParameter("name",name).getResultList();
    }

    public int getPerson(){
        String cunt = em
                .createQuery("SELECT COUNT(DISTINCT ts) FROM Team t JOIN t.students ts").getSingleResult().toString();
        int g = Integer.valueOf(cunt);
        return g;
    }

    @SuppressWarnings("unchecked")
    public int getCapacity(){
        List<Contest> teamList = em.createQuery("SELECT c FROM Contest c").getResultList();
        Contest contest = teamList.get(0);
        int g = contest.getManagers().size();
        return g;
    }

    @SuppressWarnings("unchecked")
    public int getTeamNumber(){
        List<Contest> teamList = em.createQuery("SELECT c FROM Contest c").getResultList();
        Contest contest = teamList.get(0);
        int g = contest.getTeams().size();
        return g;
    }

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
