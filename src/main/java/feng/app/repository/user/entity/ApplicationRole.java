package feng.app.repository.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "T_ROLE")
public class ApplicationRole {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ROLE_ID_GENERATOR")
	@TableGenerator(name = "ROLE_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "ROLE_PK", allocationSize = 1)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	String name;

	@Column(name = "description", nullable = true)
	String description;

	@OneToMany(mappedBy = "role", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	Set<ApplicationRefRolePrivilege> privileges = new HashSet<ApplicationRefRolePrivilege>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<ApplicationRefRolePrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<ApplicationRefRolePrivilege> privileges) {
		this.privileges = privileges;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
