package feng.app.repository.util.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import feng.app.repository.util.Utility;

public interface UtilityRepository extends CrudRepository<Utility, Long>{
	
	List<Utility> findByCategoryKey(String categoryKey);
	
}
