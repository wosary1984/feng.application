package feng.app.repository.org.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;
import feng.app.service.annotation.TransformRecursionIgnore;


@Entity
@Table(name = "T_DEPARTMENT")
@EntityFiledCheck
public class Department extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 185937086400015597L;

	@Id
	@Column(name = "departmentid")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DEPARTMENT_ID_GENERATOR")
	@TableGenerator(name = "DEPARTMENT_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "DEPARTMENT_PK", allocationSize = 1)
	private Long departmentid;
	
	@Column(name = "name", insertable = true, nullable = false)
	String name;

	@Column(name = "description")
	String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "companyid")
	Company company;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "parentid")
	Set<Department> departments = new HashSet<Department>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "managerid")
	Employee manager;
	
	@OneToMany(mappedBy = "department", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id ASC")
	List<RefEmployeeDepartment> employees = new ArrayList<RefEmployeeDepartment>();

	public Long getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	@TransformRecursionIgnore
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<RefEmployeeDepartment> getEmployees() {
		return employees;
	}

	public void setEmployees(List<RefEmployeeDepartment> employees) {
		this.employees = employees;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> childs) {
		this.departments = childs;
	}
	
}
