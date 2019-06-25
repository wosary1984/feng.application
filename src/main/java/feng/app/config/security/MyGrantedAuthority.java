package feng.app.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import feng.app.repository.privilege.entity.ApplicationPrivilegeAction;

public class MyGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String detail;
	Set<ApplicationPrivilegeAction> actions = new HashSet<ApplicationPrivilegeAction>();

	public MyGrantedAuthority(Long id, String name, String detail) {
		this.id = id;
		this.name = name;
		this.detail = detail;
	}

	@Override
	public String getAuthority() {
		return this.id + ";" + this.name + ";" + this.detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Set<ApplicationPrivilegeAction> getActions() {
		return actions;
	}

	public void setActions(Set<ApplicationPrivilegeAction> set) {
		this.actions = set;
	}

}
