package feng.app.repository.privilege.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "T_PRIVILEGE")
public class ApplicationPrivilege {

	@Id
	@Column(name = "fid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fid;

	@Column(name = "icon", nullable = false)
	String icon;

	@Column(name = "link")
	String link;

	@Column(name = "name")
	String name;

	@Column(name = "subview")
	Boolean subview;

	@Column(name = "parentid")
	Long parentid;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "parentid")
	// Set<ApplicationFunction> childs = new HashSet<ApplicationFunction>();
	List<ApplicationPrivilege> childs = new ArrayList<ApplicationPrivilege>();
	

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "fid", nullable = true)
	@OrderBy(value = "actionid ASC")
	Set<ApplicationPrivilegeAction> actions = new HashSet<ApplicationPrivilegeAction>();

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSubview() {
		return subview;
	}

	public void setSubview(Boolean subview) {
		this.subview = subview;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public List<ApplicationPrivilege> getChilds() {
		childs.sort((ApplicationPrivilege a1, ApplicationPrivilege a2) -> a1.getFid().compareTo(a2.getFid()));
		return childs;
	}

	public void setChilds(List<ApplicationPrivilege> childs) {
		this.childs = childs;
	}

	public Set<ApplicationPrivilegeAction> getActions() {
		return actions;
	}

	public void setActions(Set<ApplicationPrivilegeAction> actions) {
		this.actions = actions;
	}

}
