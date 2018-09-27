package feng.app.controller.org;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import feng.app.controller.BaseController;
import feng.app.controller.RequestPath;
import feng.app.repository.org.entity.Company;
import feng.app.repository.org.entity.Department;
import feng.app.repository.org.entity.Employee;
import feng.app.service.org.OrgService;

@RestController
public class OrgController extends BaseController implements RequestPath {

	@Autowired
	OrgService orgService;

	/***
	 * Get all companies
	 * 
	 * @return
	 */
	@RequestMapping(path = PATH_GET_ALL_COMPANY, method = RequestMethod.GET)
	public Iterable<Company> getCompanies() {
		return orgService.getCompanies();
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_COMPANY, method = RequestMethod.POST)
	public Company createCompany(@RequestBody String data) throws JSONException {
		return orgService.createCompany(data);
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_COMPANY + "/{companyid}", method = RequestMethod.POST)
	public Company updateCompany(@PathVariable Long companyid, @RequestBody String data) throws JSONException {
		return orgService.updateCompany(companyid, data);
	}

	@RequestMapping(path = PATH_GET_ALL_EMPLOYEES, method = RequestMethod.GET)
	public Iterable<Employee> getEmployees() {
		return orgService.getEmployees();
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_EMPLOYEES + "/{employeeid}", method = RequestMethod.GET)
	public Employee getEmployee(@PathVariable Long employeeid) {
		return orgService.getEmployee(employeeid);
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_EMPLOYEES, method = RequestMethod.POST)
	public Employee createEmployee(@RequestBody String data) throws JSONException {
		return orgService.createEmployee(data);
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_DEPARTMENT, method = RequestMethod.POST)
	public List<Department> createDepartment(@RequestBody String data) throws JSONException {
		return orgService.createDepartment(data);
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_COMPANY + "/{companyid}", method = RequestMethod.GET)
	public Company getCompany(@PathVariable Long companyid) {
		return orgService.getCompany(companyid);
	}

	@RequestMapping(path = PATH_GET_CREATE_EDIT_DEPARTMENT + "/{departmentid}", method = RequestMethod.GET)
	public List<Department> getDepartment(@PathVariable Long departmentid) {
		return orgService.getDepartment(departmentid);
	}
}
