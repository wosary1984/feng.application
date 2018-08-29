package feng.app.repository.org.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import feng.app.repository.base.BaseRepository;
import feng.app.repository.org.entity.Employee;


public interface EmployeeRepository extends BaseRepository<Employee, Long> {
	
	@Query(value="select e from  Employee e where e.user.userid = :userid")
	public Employee findByUserid(@Param("userid") Long userid);
	
	public Employee findByEmployeeid(Long employeeid);

}


