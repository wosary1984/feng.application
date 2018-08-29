package feng.app.repository.user.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import feng.app.repository.privilege.entity.ApplicationPrivilege;


@Entity
@Table(name = "T_ROLE_REF_PRIVILEGE")
public class ApplicationRefRolePrivilege {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REF_ROLE_PRIVILEGE_ID_GENERATOR")
	@TableGenerator(name = "REF_ROLE_PRIVILEGE_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "REF_ROLE_PRIVILEGE_PK", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleid", insertable = true, nullable = false)
	ApplicationRole role;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fid", insertable = true, nullable = false)
	ApplicationPrivilege privilege;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicationPrivilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(ApplicationPrivilege privilege) {
		this.privilege = privilege;
	}

	@JsonIgnore
	public ApplicationRole getRole() {
		return role;
	}

	public void setRole(ApplicationRole role) {
		this.role = role;
	}
	
}
