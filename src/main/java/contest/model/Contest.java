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
public class Contest implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean registration_allowed;

    @Column(nullable = false)
    private Date registration_from;

    @Column(nullable = false)
    private Date registration_to;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contest")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Team> teams = new HashSet();

    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "CONTEST_MANAGER",
            joinColumns = { @JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID") }, //do not forget referencedColumnName if name is different
            inverseJoinColumns = { @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID") })
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Person> managers = new HashSet();

    public Set<Person> getManagers() {
        return managers;
    }

    public void setManagers(Set<Person> managers) {
        this.managers = managers;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRegistration_allowed() {
        return registration_allowed;
    }

    public void setRegistration_allowed(boolean registration_allowed) {
        this.registration_allowed = registration_allowed;
    }

    public Date getRegistration_from() {
        return registration_from;
    }

    public void setRegistration_from(Date registration_from) {
        this.registration_from = registration_from;
    }

    public Date getRegistration_to() {
        return registration_to;
    }

    public void setRegistration_to(Date registration_to) {
        this.registration_to = registration_to;
    }


}
