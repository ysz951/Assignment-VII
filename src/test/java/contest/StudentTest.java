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

import java.util.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentTest {
    Date date1 = new Date(101, 11, 12);
    Date date2 = new Date(110, 3, 21);
    Date date3 = new Date(119, 9, 24);

    @Autowired
    private TestEntityManager entityManager;

    @Test
    //simple test
    public void groupTest(){
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
        @SuppressWarnings("unchecked")
        List<Object[]> groupList =  entityManager.getEntityManager()
                .createQuery("SELECT COUNT(DISTINCT ts), ts.birthdate FROM Team t JOIN t.students ts GROUP BY ts.birthdate")
                .getResultList();
        for (Object[] result: groupList) {
            int count = ((Number) result[0]).intValue();
            String birthdate = ((Date) result[1]).toString();
            System.out.printf("There are %1$d students grouped by %2$s \n", count,birthdate);
        }
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
