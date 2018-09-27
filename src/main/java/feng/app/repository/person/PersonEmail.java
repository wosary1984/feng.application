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

import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;

@Entity
@Table(name = "T_PERSON_EMAIL")
@EntityFiledCheck
public class PersonEmail extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2932505360982104887L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@Column(name="email")
	String email;
	
	@Column(name="valide")
	boolean valide;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "personid")
	Person person;


}
