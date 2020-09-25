package contest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;

    //JPA standard to make not null column
    @Column(nullable = false)
    private String firstName;

    @NotNull
    //Not JPA but JSR 303 Bean Validation annotation, Hibernate translates it to nullable=false in @Column for you
    @Column
    @Size(min = 1, max = 25) //JSR 303 validation annotation that can be used in entities
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "must contain valid email address") //again JSR 303 annotation
    @Column
    private String email;

    @NotNull
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "must contain valid phone number")
    @Column
    private String telephoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Course> courses = new HashSet();

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    public Set getCourses() {
        return courses;
    }

}
