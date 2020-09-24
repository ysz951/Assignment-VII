package edu.baylor.cs.se.hibernate;

import edu.baylor.cs.se.hibernate.model.Teacher;
import edu.baylor.cs.se.hibernate.model.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExampleTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    //simple test
    public void demoTest(){
        Teacher teacher = new Teacher();
        teacher.setEmail("email@email.com");
        teacher.setFirstName("John");
        teacher.setLastName("Roe");
        teacher.setTelephoneNumber("+1 (123) 456-0789");
        entityManager.persist(teacher);
        Teacher dbTeacher = (Teacher)entityManager.getEntityManager()
                .createQuery("SELECT t FROM Teacher t WHERE t.firstName LIKE 'John' ")
                .getResultList().get(0);
        assertThat(teacher.getFirstName()).isEqualToIgnoringCase(dbTeacher.getFirstName());
    }

    @Test
    //simple test
    public void phoneTest(){
        Teacher teacher = new Teacher();
        teacher.setEmail("email@email.com");
        teacher.setFirstName("John");
        teacher.setLastName("Roe");
        teacher.setTelephoneNumber("232-456-0789");
        entityManager.persist(teacher);
        Teacher dbTeacher = (Teacher)entityManager.getEntityManager()
                .createQuery("SELECT t FROM Teacher t WHERE t.firstName LIKE 'John' ")
                .getResultList().get(0);
        assertThat(teacher.getTelephoneNumber()).isEqualToIgnoringCase(dbTeacher.getTelephoneNumber());
    }

    @Test
    //tests that email validation works
    public void anotherDemoTest(){
        Teacher teacher = new Teacher();
        teacher.setEmail("hahaWrongEmail");
        teacher.setFirstName("John");
        teacher.setLastName("Roe");
        teacher.setTelephoneNumber("+1 (123) 456-0789");
        assertThatThrownBy(() -> { entityManager.persist(teacher); })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("must contain valid email address");
    }

    @Test
    //tests that email validation works
    public void phoneDemoTest(){
        Teacher teacher = new Teacher();
        teacher.setEmail("hahaWrongEmail");
        teacher.setFirstName("John");
        teacher.setLastName("Roe");
        teacher.setTelephoneNumber("+1 (123) 456-079");
        assertThatThrownBy(() -> { entityManager.persist(teacher); })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("must contain valid phone number");
    }

    @Test
    //tests that email validation works
    public void phoneDemoTest2(){
        Teacher teacher = new Teacher();
        teacher.setEmail("hahaWrongEmail");
        teacher.setFirstName("John");
        teacher.setLastName("Roe");
        teacher.setTelephoneNumber("+1 (12) 456-0799");
        assertThatThrownBy(() -> { entityManager.persist(teacher); })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("must contain valid phone number");
    }

    @Test
    //simple test
    public void roomTest(){
        Room room = new Room();
        room.setLocation("Test");
        entityManager.persist(room);
        Room dbTeacher = (Room)entityManager.getEntityManager()
                .createQuery("SELECT t FROM Room t WHERE t.location LIKE 'Test' ")
                .getResultList().get(0);
        assertThat(room.getLocation()).isEqualToIgnoringCase(dbTeacher.getLocation());
    }
}
