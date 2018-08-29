package feng.app.repository.org.repository;


import feng.app.repository.base.BaseRepository;
import feng.app.repository.org.entity.Company;

public interface CompanyRepository extends BaseRepository<Company, Long> {
	
	Company findByCompanyid(Long companyid);
}
