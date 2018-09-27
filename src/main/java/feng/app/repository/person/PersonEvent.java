package feng.app.repository.person;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;
import feng.app.service.annotation.TransformRecursionIgnore;

@Entity
@Table(name = "T_PERSON_EVENT")
@EntityFiledCheck
public class PersonEvent extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1908961287324955087L;

	/**
	 * 
	 */

	@Id
	@Column(name = "id")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(name="eventdate")
	String eventdate;
	
	@Column(name="title")
	String title;
	
	@Column(name="content")
	String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "personid")
	Person person;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@TransformRecursionIgnore
	@JsonIgnore
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getEventdate() {
		return eventdate;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
