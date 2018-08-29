package feng.app.repository.user.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "T_USER")
public class ApplicationUser {
	@Id
	@Column(name = "userid")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_ID_GENERATOR")
	@TableGenerator(name = "USER_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "USER_PK", allocationSize = 1)
	private Long userid;

	@Column(name = "username", insertable = true, unique = true, nullable = false)
	String username;

	@Column(name = "firstname")
	String firstname;

	@Column(name = "lastname")
	String lastname;

	@Column(name = "password")
	String password;

	@Column(name = "email")
	private String email;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@Column(name = "locked")
	private boolean locked;

	@Column(name = "granttype")
	private String granttype;

	@Column(name = "role")
	private String role;

	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id ASC")
	Set<ApplicationRefRoleUser> roles = new HashSet<ApplicationRefRoleUser>();

	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id ASC")
	List<ApplicationRefUserPrivilege> privileges = new ArrayList<ApplicationRefUserPrivilege>();

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long id) {
		this.userid = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<ApplicationRefRoleUser> getRoles() {
		return roles;
	}

	public void setRoles(Set<ApplicationRefRoleUser> roles) {
		this.roles = roles;
	}

	public List<ApplicationRefUserPrivilege> getPrivileges() {
		// privileges.sort((ApplicationRefUserPrivilege a1, ApplicationRefUserPrivilege
		// a2) -> a1.getId().compareTo(a2.getId()));
		return privileges;
	}

	public void setPrivileges(List<ApplicationRefUserPrivilege> privileges) {
		this.privileges = privileges;
	}

	public String getGranttype() {
		return granttype;
	}

	public void setGranttype(String grantType) {
		this.granttype = grantType;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		return String.format("%s,%s", this.firstname, this.lastname);
	}

}
