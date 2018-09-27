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
@Table(name = "T_PERSON_ADDRESS")
@EntityFiledCheck
public class PersonAddress extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2932505360982104887L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="address")
	String address;
	
	@Column(name="country")
	String country;
	
	@Column(name="state")
	String state;
	
	@Column(name="city")
	String city;
	
	@Column(name="postcode")
	String postcode;
	
	@Column(name="xcoordinate")
	String xcoordinate;
	
	@Column(name="ycoordinate")
	String ycoordinate;
		
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getXcoordinate() {
		return xcoordinate;
	}

	public void setXcoordinate(String xcoordinate) {
		this.xcoordinate = xcoordinate;
	}

	public String getYcoordinate() {
		return ycoordinate;
	}

	public void setYcoordinate(String ycoordinate) {
		this.ycoordinate = ycoordinate;
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


}
