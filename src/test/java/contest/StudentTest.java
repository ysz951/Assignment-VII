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

//    @Before
////    Build a pdf file by given parameter
//    public void initAll() {
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
//    }


    @Test
    //simple test
    public void capacityTest(){
        Team team1 = createTeam("testName", 1, State.Pending);
        Person student1 = createPerson(date1, "student1@contest.com", "student1", "BaylorStu1");
        Person student2 = createPerson(date2, "student2@contest.com", "student2", "BaylorStu2");
        Person student3 = createPerson(date3, "student3@contest.com", "student3", "BaylorStu3");
        Person student4 = createPerson(date2, "student4@contest.com", "student4", "BaylorStu4");
        List<Person> personList= Arrays.asList(student1, student2, student3, student4);
        team1.getStudents().addAll(personList);
        String cunt = entityManager.getEntityManager()
                .createQuery("SELECT COUNT(ts) FROM Team t JOIN t.students ts").getSingleResult().toString();
        int g = Integer.valueOf(cunt);
        assertThat(g).isEqualTo(personList.size());

    }

    @Test
    //simple test
    public void groupTest(){
        Team team1 = createTeam("testName", 1, State.Pending);
        Person student1 = createPerson(date1, "student1@contest.com", "student1", "BaylorStu1");
        Person student2 = createPerson(date2, "student2@contest.com", "student2", "BaylorStu2");
        Person student3 = createPerson(date3, "student3@contest.com", "student3", "BaylorStu3");
        Person student4 = createPerson(date2, "student4@contest.com", "student4", "BaylorStu4");
        List<Person> personList= Arrays.asList(student1, student2, student3, student4);
        team1.getStudents().addAll(personList);
        @SuppressWarnings("unchecked")
        List<Object[]> groupList =  entityManager.getEntityManager()
                .createQuery("SELECT COUNT(DISTINCT ts) AS student_number, ts.birthdate AS birthdate FROM Team t JOIN t.students ts GROUP BY ts.birthdate")
                .getResultList();
        for (Object[] result: groupList) {
            int count = ((Number) result[0]).intValue();
            String birthdate = ((Date) result[1]).toString();
            System.out.println(count);
            System.out.printf("birdathe %1$s \n", birthdate);
        }

        System.out.printf("list size is %1$d \n", groupList.size());

//        String cunt = entityManager.getEntityManager()
//                .createQuery("SELECT COUNT(ts) FROM Team t JOIN t.students ts").getSingleResult().toString();
//        int g = Integer.valueOf(cunt);
//        assertThat(g).isEqualTo(personList.size());

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
