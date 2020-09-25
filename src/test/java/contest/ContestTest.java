package contest;

import contest.model.*;
import contest.model.Team.State;
import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ContestTest {
    Date date1 = new Date();
    Date date2 = new Date();
    Date date3 = new Date();

    @Autowired
    private TestEntityManager entityManager;

    @Before
//    Build a pdf file by given parameter
    public void initAll() {
        Team team1 = createTeam("testName", 1, State.Pending);
        Team team2 = createTeam("testNam2", 3, State.Pending);

        Contest contest1 = createContest(2, date1, "TestContest1", true, date2, date3);

        Person manager1 = createPerson(date1, "test1@contest.com", "manager1", "Baylor1");
        Person manager2 = createPerson(date1, "test2@contest.com", "manager2", "Baylor2");

        contest1.getTeams().addAll(Arrays.asList(team1, team2));
        contest1.getManagers().addAll(Arrays.asList(manager1, manager2));
    }


    @Test
    //simple test
    public void capacityTest(){
//        Team team1 = createTeam("testName", 1, State.Pending);
//        Team team2 = createTeam("testNam2", 3, State.Pending);
//
//        Contest contest1 = createContest(2, date1, "TestContest1", true, date2, date3);
//
//        Person manager1 = createPerson(date1, "test1@contest.com", "manager1", "Baylor1");
//        Person manager2 = createPerson(date1, "test2@contest.com", "manager2", "Baylor2");
//
//        contest1.getTeams().addAll(Arrays.asList(team1, team2));
//        contest1.getManagers().addAll(Arrays.asList(manager1, manager2));

        Contest dbContest= (Contest)entityManager.getEntityManager().createQuery("SELECT c FROM Contest c").getResultList().get(0);
        Team dbTeam= (Team)entityManager.getEntityManager().createQuery("SELECT t FROM Team t").getResultList().get(0);
        int teamNumber = dbContest.getTeams().size();
        assertThat(teamNumber).isLessThanOrEqualTo(dbContest.getCapacity());

    }

    @Test
    //simple test
    public void demoTest(){
        Person dbPerson = (Person)entityManager.getEntityManager()
                .createQuery("SELECT p FROM Person p WHERE p.name LIKE 'manager1' ")
                .getResultList().get(0);
        assertThat(dbPerson.getName()).isEqualToIgnoringCase(dbPerson.getName());
    }

    private Team createTeam(String name, int rank, State state) {
        Team team = new Team();
        team.setName(name);
        team.setRank(rank);
        team.setState(state);
        entityManager.persist(team);
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
        entityManager.persist(contest);
        return contest;
    }
    private Person createPerson(Date birthdate, String email, String name, String university) {
        Person person = new Person();
        person.setBirthdate(birthdate);
        person.setEmail(email);
        person.setName(name);
        person.setUniversity(university);
        entityManager.persist(person);
        return person;
    }
}
