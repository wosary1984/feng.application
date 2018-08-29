package feng.app.repository.user;

import org.springframework.data.repository.CrudRepository;

import feng.app.repository.user.entity.ApplicationPermission;

public interface PermissionRepository extends CrudRepository<ApplicationPermission, Long>{
	
	//ApplicationRole findByRoleId(Long id);
}
