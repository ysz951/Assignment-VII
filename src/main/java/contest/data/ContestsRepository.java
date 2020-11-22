package contest.data;

import org.springframework.data.repository.CrudRepository;

import contest.model.Contest;

public interface ContestsRepository
        extends CrudRepository<Contest, Long> {

}