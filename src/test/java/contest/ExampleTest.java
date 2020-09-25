package contest;

import contest.model.Course;
import contest.model.Student;
import contest.model.Teacher;
import contest.model.Room;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
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
    private Student createStudent(String name){
        Student student = new Student();
        student.setName(name);
        entityManager.persist(student);
        return student;
    }
    @Test
    //simple test
    public void deoTest(){
        Student student = createStudent("Joe");
        Student student2 = createStudent("Jofee");
        Room room = new Room();
        room.setLocation("Baylor");
        entityManager.persist(room);

        Teacher teacher = new Teacher();
        teacher.setEmail("email@email.com");
        teacher.setFirstName("John");
        teacher.setLastName("Roe");
        teacher.setTelephoneNumber("+1 (123) 456-0789");

        Course course = new Course();
        course.setName("Software engineering");
        course.setTeacher(teacher);
        course.setRoom(room);
        entityManager.persist(course);
        course.getStudents().add(student);
        course.getStudents().add(student2);



        entityManager.persist(teacher);
//        Teacher dbTeacher = (Teacher)entityManager.getEntityManager()
//                .createQuery("SELECT t FROM Teacher t WHERE t.firstName LIKE 'John' ")
//                .getResultList().get(0);
        Course dbCourse = (Course)entityManager.getEntityManager()
                .createQuery("SELECT c FROM Course c")
                .getResultList().get(0);

        @SuppressWarnings("unchecked")
        String cunt = entityManager.getEntityManager()
                .createQuery("SELECT COUNT(s) FROM Student s").getSingleResult().toString();
//                .getResultList();
        int g = Integer.valueOf(cunt);
        System.out.println("result");
        System.out.println(cunt);
        System.out.println(g);
//        @SuppressWarnings("unchecked")
//        List<Student> dbStudent = (List<Student>)entityManager.getEntityManager()
//                .createQuery("SELECT s FROM Student s")
//                .getResultList();
//        System.out.println("Student name");
//        for (Student s : dbStudent) {
//            System.out.println(s.getName());
//        }

//        System.out.printf("Student number is %1$d\n", dbCourse.getStudents().size());
//        assertThat(teacher.getFirstName()).isEqualToIgnoringCase(dbTeacher.getFirstName());
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
