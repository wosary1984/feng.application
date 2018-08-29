package feng.app.repository.user;

import org.springframework.data.repository.CrudRepository;

import feng.app.repository.user.entity.ApplicationRole;

public interface ApplicationRoleRepository extends CrudRepository<ApplicationRole, Long>{
	
	//ApplicationRole findByRoleId(Long id);
}
