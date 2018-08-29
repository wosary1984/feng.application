package feng.app.repository.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "T_REF_EMPLOYEE_DEPARTMENT")
public class RefEmployeeDepartment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REF_EMPLOYEE_DEPARTMENT_ID_GENERATOR")
	@TableGenerator(name = "REF_EMPLOYEE_DEPARTMENT_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "REF_EMPLOYEE_DEPARTMENT_PK", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employeeid", insertable = true, nullable = false)
	Employee employee;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departmentid", insertable = true, nullable = false)
	Department department;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
