package feng.app.repository.privilege;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import feng.app.repository.privilege.entity.ApplicationPrivilege;

public interface PrivilegeRepository extends CrudRepository<ApplicationPrivilege, Long>{
	
	//ApplicationFunctionModule findByPermission(ApplicationPermission permission);
	
	@Query("SELECT t1 FROM ApplicationPrivilege t1 WHERE t1.parentid=null" )
	List<ApplicationPrivilege> getTopPrivileges();
	

	ApplicationPrivilege getByFid(Long fid);
	
}
