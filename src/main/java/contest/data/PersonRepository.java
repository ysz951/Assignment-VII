package contest.data;

import org.springframework.data.repository.CrudRepository;

import contest.model.Person;

public interface PersonRepository
        extends CrudRepository<Person, Long> {

}