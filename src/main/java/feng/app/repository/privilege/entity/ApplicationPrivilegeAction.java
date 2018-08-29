package feng.app.repository.privilege.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "T_PRIVILEGE_ACTION")
public class ApplicationPrivilegeAction {

	@Id
	@Column(name = "actionid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long actionid;

	public Long getActionid() {
		return actionid;
	}

	public void setActionid(Long actionid) {
		this.actionid = actionid;
	}

	@Column(name = "actionpath")
	String actionpath;

	@Column(name = "actionmethod")
	String actionmethod;
	
	@Column(name = "description")
	String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fid", insertable = true, nullable = true)
	ApplicationPrivilege privilege;



	@JsonIgnore
	public ApplicationPrivilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(ApplicationPrivilege privilege) {
		this.privilege = privilege;
	}

	public String getActionpath() {
		return actionpath;
	}

	public void setActionpath(String actionpath) {
		this.actionpath = actionpath;
	}

	public String getActionmethod() {
		return actionmethod;
	}

	public void setActionmethod(String actionmethod) {
		this.actionmethod = actionmethod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
