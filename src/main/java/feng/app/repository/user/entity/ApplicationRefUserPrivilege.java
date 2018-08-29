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
@Table(name = "T_USER_REF_PRIVILEGE")
public class ApplicationRefUserPrivilege {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REF_USER_PRIVILEGE_ID_GENERATOR")
	@TableGenerator(name = "REF_USER_PRIVILEGE_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "REF_USER_PRIVILEGE_PK", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid", insertable = true, nullable = false)
	ApplicationUser user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fid", insertable = true, nullable = true)
	ApplicationPrivilege privilege;

	public ApplicationPrivilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(ApplicationPrivilege privilege) {
		this.privilege = privilege;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public ApplicationUser getUser() {
		return user;
	}

	public Long getUserId() {
		return user.getUserid();
	}

	public void setUser(ApplicationUser user) {
		this.user = user;
	}

}
