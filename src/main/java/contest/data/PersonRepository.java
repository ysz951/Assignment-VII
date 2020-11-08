package contest.data;

import org.springframework.data.repository.CrudRepository;

import contest.model.Person;

import java.util.List;

public interface PersonRepository
        extends CrudRepository<Person, Long> {
    List<Person> findByNameContains(String name);
}