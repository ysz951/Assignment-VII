package contest.data;


import org.springframework.data.repository.CrudRepository;

import contest.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{

}
