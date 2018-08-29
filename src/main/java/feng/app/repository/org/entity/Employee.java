package feng.app.repository.org.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import feng.app.repository.base.BaseEntity;
import feng.app.repository.user.entity.ApplicationUser;
import feng.app.service.Restrictions;
import feng.app.service.annotation.EntityFieldRestriction;
import feng.app.service.annotation.EntityFiledCheck;

@Entity
@Table(name = "T_EMPLOYEE")
@EntityFiledCheck
public class Employee extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2394236548808636189L;

	@Id
	@Column(name = "employeeid")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EMPLOYEE_ID_GENERATOR")
	@TableGenerator(name = "EMPLOYEE_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "EMPLOYEE_PK", allocationSize = 1)
	private Long employeeid;

	@Column(name = "firstname")
	String firstname;

	@Column(name = "lastname")
	String lastname;
	
	@Column(name="nickname")
	String nickname;

	@Column(name = "email")
	private String email;

	@Column(name = "phonenumber")
	private String phonenumber;

	@Column(name = "status")
	private String status;

	@Column(name = "address")
	private String address;

	@Column(name = "hiredate")
	private Date hiredate;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "contextcode")
	private String contextcode;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "picture", columnDefinition = "BLOB", nullable = true)
	private byte[] picture;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "companyid")
	Company company;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "salaryid")
	EmployeeSalary salary;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	ApplicationUser user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	Employee directManager;

	@OneToMany(mappedBy = "employee", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id ASC")
	Set<RefEmployeeDepartment> roles = new HashSet<RefEmployeeDepartment>();

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phoneNumber) {
		this.phonenumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ApplicationUser getUser() {
		return user;
	}

	public void setUser(ApplicationUser user) {
		this.user = user;
	}

	public Employee getDirectManager() {
		return directManager;
	}

	public void setDirectManager(Employee directManager) {
		this.directManager = directManager;
	}

	public Set<RefEmployeeDepartment> getRoles() {
		return roles;
	}

	public void setRoles(Set<RefEmployeeDepartment> roles) {
		this.roles = roles;
	}

	@EntityFieldRestriction(value = Restrictions.DEPARTMENT)
	public EmployeeSalary getSalary() {
		return salary;
	}

	public void setSalary(EmployeeSalary salary) {
		this.salary = salary;
	}

	public String getContextcode() {
		return contextcode;
	}

	public void setContextcode(String contextcode) {
		this.contextcode = contextcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
