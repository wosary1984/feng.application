package feng.app.repository.org.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import feng.app.repository.base.BaseEntity;

@Entity
@Table(name = "T_EMPLOYEE_SALARY")
public class EmployeeSalary extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2394236548808636189L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EMPLOYEE_SALARY_ID_GENERATOR")
	@TableGenerator(name = "EMPLOYEE_SALARY_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "EMPLOYEE_SALARY_PK", allocationSize = 1)
	private Long id;
	
	@Column(name = "salary")
	private double salary;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name="contextcode")
	private String contextcode;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employeeid")
	Employee employee;
	
}
