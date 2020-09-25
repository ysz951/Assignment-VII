package contest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "must contain valid email address") //again JSR 303 annotation
    @Column
    private String email;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private String university;

    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "CONTEST_MANAGER",
            joinColumns = { @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID") }, //do not forget referencedColumnName if name is different
            inverseJoinColumns = { @JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID") })
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Contest> contests = new HashSet();

    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "TEAM_STUDENT",
            joinColumns = { @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID") }, //do not forget referencedColumnName if name is different
            inverseJoinColumns = { @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID") })
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Person> student_teams = new HashSet();

    public Set<Person> getStudent_teams() {
        return student_teams;
    }

    public void setStudent_teams(Set<Person> student_teams) {
        this.student_teams = student_teams;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
