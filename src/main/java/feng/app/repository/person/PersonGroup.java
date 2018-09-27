package feng.app.repository.person;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;

@Entity
@Table(name = "T_PERSON_GROUP")
@EntityFiledCheck
public class PersonGroup extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2932505360982104887L;

	@Id
	@Column(name = "groupid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long groupid;
	

	@Column(name = "name")
	String name;

	@Column(name = "type")
	String type;
	
	@Column(name = "startdate")
	private Date startdate;
	
	@Column(name = "enddate")
	private String enddate;
	
	
	//@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "group", fetch = FetchType.LAZY)
	Set<Person> person = new HashSet<Person>();

}
