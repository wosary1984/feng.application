package feng.app.repository.util.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import feng.app.repository.base.BaseRepository;
import feng.app.repository.util.EntityFieldRegistration;

public interface EntityFieldRegistrationRepository extends BaseRepository<EntityFieldRegistration, Long> {
	 
	@Query(value="select efr from EntityFieldRegistration efr where efr.readable=:read and efr.userid =:userid and efr.entityname=:entityname")
	public List<EntityFieldRegistration> findEntityFieldsByRead(@Param("userid") Long userid,@Param("entityname") String entityname,@Param("read") boolean read);
	
	@Query(value="select efr from EntityFieldRegistration efr where efr.userid =:userid and efr.entityname=:entityname")
	public List<EntityFieldRegistration> findEntityFields(@Param("userid") Long userid,@Param("entityname") String entityname);
	
	public List<EntityFieldRegistration> findByUseridAndEntitynameAndReadable(Long userid, String entityname, boolean readable);
	
	
}
