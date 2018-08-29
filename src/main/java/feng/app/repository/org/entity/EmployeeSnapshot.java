package feng.app.repository.org.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "T_EMPLOYEE_SNAPSHOT")
public class EmployeeSnapshot {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EMPLOYEESNAPSHOT_ID_GENERATOR")
	@TableGenerator(name = "EMPLOYEESNAPSHOT_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "EMPLOYEESNAPSHOT_PK", allocationSize = 1)
	private Long id;
	
	@Column(name = "employeeid", insertable = true, unique = true, nullable = false)
	String employeeid;
	
}
