package feng.app.repository.org.repository;


import feng.app.repository.base.BaseRepository;
import feng.app.repository.org.entity.Department;

public interface DepartmentRepository extends BaseRepository<Department, Long> {
	Department findByDepartmentid(Long departmentid);
}
