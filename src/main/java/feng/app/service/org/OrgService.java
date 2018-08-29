package feng.app.service.org;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feng.app.controller.exception.def.NullException;
import feng.app.repository.org.entity.Company;
import feng.app.repository.org.entity.Department;
import feng.app.repository.org.entity.Employee;
import feng.app.repository.org.entity.EmployeeStatus;
import feng.app.repository.org.repository.CompanyRepository;
import feng.app.repository.org.repository.DepartmentRepository;
import feng.app.repository.org.repository.EmployeeRepository;
import feng.app.repository.user.UserRepository;
import feng.app.service.BaseService;
import feng.app.service.context.ContextCheckService;

@Service
public class OrgService extends BaseService {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	ContextCheckService service;

	/***
	 * Get Company List
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Iterable<Company> getCompanies() {

		List<Company> dbresult = companyRepository.findAll();
		return (Iterable<Company>) this.ContextCheckObjectList(Company.class, dbresult, service);

		// if (Company.class.isAnnotationPresent(EntityFiledCheck.class)) {
		// List<Company> result = new ArrayList<Company>();
		// for (Company company : dbresult) {
		// Company dto = (Company) new TransformerAdapter(Company.class,
		// this.service).transform2(Company.class,
		// company, service.getObjectAccessRestriction(Company.class));
		// result.add(dto);
		// }
		// return result;
		//
		// } else {
		// return dbresult;
		// }
	}

	/***
	 * Get Company
	 * 
	 * @param companyid
	 * @return
	 */
	public Company getCompany(Long companyid) {

		Company company = companyRepository.findByCompanyid(companyid);

		return (Company) this.ContextCheckObject(Company.class, company, service);
		// field check
		// if (Company.class.isAnnotationPresent(EntityFiledCheck.class)) {
		// Company dto = (Company) new TransformerAdapter(Company.class,
		// this.service).transform2(Company.class,
		// company, service.getObjectAccessRestriction(Company.class));
		// return dto;
		// } else {
		// return company;
		// }

	}

	@SuppressWarnings("unchecked")
	public List<Department> getDepartment(Long departmentid) {
		String sql = "select * from T_DEPARTMENT where departmentid=" + departmentid;
		return (List<Department>) departmentRepository.findListByNativeSql(sql, Department.class);
	}

	public Company getCompany2(Long companyid) {
		return companyRepository.findByCompanyid(companyid);

	}

	public Company updateCompany(Company company) {
		return companyRepository.save(company);
	}

	/**
	 * Create new company
	 * 
	 * @param data
	 * @return
	 */
	public Company createCompany(String data) {
		JSONObject object = new JSONObject(data);
		Company company = new Company();
		company.setName(object.isNull("name") ? "" : object.getString("name"));
		if (company.getName() == "") {
			throw new NullException("name is null");
		}
		company.setDescription(object.isNull("description") ? "" : object.getString("description"));
		companyRepository.save(company);
		return (Company) this.ContextCheckObject(Company.class, company, service);
	}

	public Company updateCompany(Long companyid, String data) {
		Company company = companyRepository.findByCompanyid(companyid);
		return company;
	}

	/**
	 * Create new department
	 * 
	 * @param data
	 * @return
	 */
	public List<Department> createDepartment(String data) {
		JSONObject object = new JSONObject(data);
		String action = object.isNull("action") ? "" : object.getString("action");
		String parentType = object.isNull("parentType") ? "" : object.getString("parentType");
		Long parentid = object.isNull("parentid") ? -1 : object.getLong("parentid");

		if (action.equalsIgnoreCase("create")) {
			Department child = new Department();
			child.setName(object.isNull("name") ? "" : object.getString("name"));
			child.setDescription(object.isNull("description") ? "" : object.getString("description"));
			if (parentType.equalsIgnoreCase("company")) {
				Company parent = companyRepository.findByCompanyid(parentid);
				if (parent == null) {
					throw new NullException("department parent id is null");
				}
				parent.getDepartments().add(child);
				child.setCompany(parent);
				companyRepository.save(parent);
				return companyRepository.save(parent).getDepartments();

			} else if (parentType.equalsIgnoreCase("department")) {
				Department parent = departmentRepository.findByDepartmentid(parentid);
				if (parent == null) {

				}
				parent.getDepartments().add(child);
				return new ArrayList<Department>(departmentRepository.save(parent).getDepartments());

			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Iterable<Employee> getEmployees() {
		List<Employee> dbresult = employeeRepository.findAll();
		return (Iterable<Employee>) this.ContextCheckObjectList(Employee.class, dbresult, service);
	}
	
	public Employee getEmployee(Long employeeid) {
		Employee employee = employeeRepository.findByEmployeeid(employeeid);
		return (Employee) this.ContextCheckObject(Employee.class, employee, service);
	}

	public Employee createEmployee(String data) {

		JSONObject object = new JSONObject(data);
		Employee employee = new Employee();
		String action = object.isNull("action") ? "" : object.getString("action");

		if (action.equalsIgnoreCase("create")) {
			JSONObject eo = object.getJSONObject("employee");
			if (eo == null)
				throw new NullException("employee object is null");
			employee.setFirstname(eo.isNull("firstname") ? "" : eo.getString("firstname"));
			employee.setLastname(eo.isNull("lastname") ? "" : eo.getString("lastname"));
			employee.setNickname(eo.isNull("nickname") ? "" : eo.getString("nickname"));
			employee.setEmail(eo.isNull("email") ? "" : eo.getString("email"));
			employee.setPhonenumber(eo.isNull("phonenumber") ? "" : eo.getString("phonenumber"));
			employee.setNationality(eo.isNull("nationality") ? "" : eo.getString("nationality"));
		}
		employee.setStatus(EmployeeStatus.NOTONBORAD.getName());

		employeeRepository.save(employee);
		return (Employee) this.ContextCheckObject(Employee.class, employee, service);
		// return employee;
	}

}
