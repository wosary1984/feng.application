package feng.app.repository.person;


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

import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;
import feng.app.service.annotation.TransformRecursionIgnore;

@Entity
@Table(name = "T_PERSON_PHONE")
@EntityFiledCheck
public class PersonPhone extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2932505360982104887L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="number")
	String number;
	
	@Column(name="valid")
	boolean valid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "personid")
	Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@TransformRecursionIgnore
	@JsonIgnore
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


}
