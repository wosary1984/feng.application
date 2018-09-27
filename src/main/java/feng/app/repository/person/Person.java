package feng.app.repository.person;

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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;

@Entity
@Table(name = "T_PERSON")
@EntityFiledCheck
public class Person extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2932505360982104887L;

	@Id
	@Column(name = "personid")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PERSON_ID_GENERATOR")
	@TableGenerator(name = "PERSON_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "PERSON_PK", allocationSize = 1)
	private Long personid;

	@Column(name = "identitycard")
	String identitycard;

	@Column(name = "name")
	String name;

	@Column(name = "gender")
	String gender;

	@Column(name = "birthdate")
	private String birthdate;

	@Column(name = "birthplace")
	private String birthplace;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "logo", columnDefinition = "BLOB", nullable = true)
	private byte[] portrait;

	@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id asc")
	Set<PersonAddress> address = new HashSet<PersonAddress>();

	@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id asc")
	Set<PersonEmail> email = new HashSet<PersonEmail>();

	@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id asc")
	Set<PersonPhone> phone = new HashSet<PersonPhone>();
	
	@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id asc")
	Set<PersonEvent> events = new HashSet<PersonEvent>();

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "t_person_group_map", joinColumns = { @JoinColumn(name = "personid") }, inverseJoinColumns = {
			@JoinColumn(name = "groupid") })
	Set<PersonGroup> group = new HashSet<PersonGroup>();

	public Long getPersonid() {
		return personid;
	}

	public void setPersonid(Long personid) {
		this.personid = personid;
	}

	public String getIdentitycard() {
		return identitycard;
	}

	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getPortrait() {
		return new String(portrait);
	}

	public void setPortrait(byte[] portrait) {
		this.portrait = portrait;
	}

	public Set<PersonAddress> getAddress() {
		return address;
	}

	public void setAddress(Set<PersonAddress> address) {
		this.address = address;
	}

	public Set<PersonEmail> getEmail() {
		return email;
	}

	public void setEmail(Set<PersonEmail> email) {
		this.email = email;
	}

	public Set<PersonPhone> getPhone() {
		return phone;
	}

	public void setPhone(Set<PersonPhone> phone) {
		this.phone = phone;
	}

	public Set<PersonGroup> getGroup() {
		return group;
	}

	public void setGroup(Set<PersonGroup> group) {
		this.group = group;
	}

	public Set<PersonEvent> getEvents() {
		return events;
	}

	public void setEvents(Set<PersonEvent> events) {
		this.events = events;
	}

}
