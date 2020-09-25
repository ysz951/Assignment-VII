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
public class Team implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    private int rank;

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    @Column(nullable = false)
    private State state;

    @ManyToOne
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Contest contest;

    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "TEAM_STUDENT",
            joinColumns = { @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID") }, //do not forget referencedColumnName if name is different
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID") })
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Person> students = new HashSet();

    public enum State {

        Accepted, Pending, Canceled
    }

    public Set<Person> getStudents() {
        return students;
    }

    public void setStudents(Set<Person> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
