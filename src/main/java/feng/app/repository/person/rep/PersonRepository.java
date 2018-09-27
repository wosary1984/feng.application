package feng.app.repository.person.rep;

import feng.app.repository.base.BaseRepository;
import feng.app.repository.person.Person;


public interface PersonRepository extends BaseRepository<Person, Long> {
	
	public Person findByPersonid(Long personid);
	
}


