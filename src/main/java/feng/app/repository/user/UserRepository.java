package feng.app.repository.user;


import org.springframework.data.repository.CrudRepository;

import feng.app.repository.user.entity.ApplicationUser;

public interface UserRepository extends CrudRepository<ApplicationUser, Long>{
	
	ApplicationUser findByUsername(String username);
	
	ApplicationUser findByUserid(Long userid);

}
