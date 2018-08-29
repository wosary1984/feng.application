package feng.app.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import feng.app.repository.user.entity.ApplicationRefUserPrivilege;
import feng.app.repository.user.entity.ApplicationUser;

public interface RefUserPrivilegeRepository extends CrudRepository<ApplicationRefUserPrivilege, Long> {

	//@Query("SELECT teacher FROM ApplicationRefUserPrivilege t1 JOIN t1.user user WHERE user.userid=:userid")
	List<ApplicationRefUserPrivilege> findByUser(ApplicationUser user);
	
	@Query("SELECT t1 FROM ApplicationRefUserPrivilege t1 WHERE t1.privilege.parentid=null AND t1.user.userid=:userid" )
	List<ApplicationRefUserPrivilege> findUserPrivileges(@Param("userid") Long name);
}
