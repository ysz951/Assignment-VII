package edu.baylor.cs.se.hibernate.services;

import edu.baylor.cs.se.hibernate.model.Course;
import edu.baylor.cs.se.hibernate.model.Student;
import edu.baylor.cs.se.hibernate.model.Teacher;
import edu.baylor.cs.se.hibernate.model.Room;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//Spring annotations, feel free to ignore it
@Repository
@Transactional
public class SuperRepository {

    @PersistenceContext
    private EntityManager em;

    public void populate(){
        Student student = createStudent("Joe");
        Student student2 = createStudent("John");
        Student student3 = createStudent("Bob");
        Student student4 = createStudent("Tim");
        Student student5 = createStudent("Jimmy");


        Teacher teacher = new Teacher();
        teacher.setFirstName("Bob");
        teacher.setLastName("Porter");
        teacher.setEmail("bob@porter.com");
        teacher.setTelephoneNumber("+1 (123) 456-0789");
        em.persist(teacher);

        Room room = new Room();
        room.setLocation("Baylor");
        em.persist(room);

        Course course = new Course();
        course.setName("Software engineering");
        course.setTeacher(teacher);
        course.setRoom(room);
        em.persist(course);
        course.getStudents().add(student);
        course.getStudents().add(student3);
        course.getStudents().add(student4);
//        em.persist(course);

        Course course2 = new Course();
        course2.setName("Boring class");
        course2.setTeacher(teacher);
        course2.setRoom(room);
        course2.getStudents().add(student);
        course2.getStudents().add(student5);
        em.persist(course2);

        //Do you know why this is not working?
//        student2.getCourses().add(course);
        course.getStudents().add(student2);



    }

    /**
     * List of courses with more than 2 students (3 and more)
     */
    @SuppressWarnings("unchecked")
    public List<Course> getCoursesBySize(){
        return em.createQuery("SELECT c FROM Course c WHERE c.students.size > 2 ").getResultList();
    }

    /**
     * List of courses with more than 2 students (3 and more)
     */
    @SuppressWarnings("unchecked")
    public List<Course> getCoursesByStudentName(){
        return em.createNamedQuery("Course.findCoursesByStudentName").setParameter("name","Jimmy").getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Course> getCoursesByStudentNameParam(String name){
        return em.createNamedQuery("Course.findCoursesByStudentName").setParameter("name", name).getResultList();
    }

    private Student createStudent(String name){
        Student student = new Student();
        student.setName(name);
        em.persist(student);
        return student;
    }
}
