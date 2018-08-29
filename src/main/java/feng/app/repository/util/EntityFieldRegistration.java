package feng.app.repository.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_ENTITY_FIELD")
public class EntityFieldRegistration{


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "userid")
	Long userid;

	@Column(name = "entityname", insertable = true, unique = false, nullable = false)
	String entityname;

	@Column(name = "fieldname")
	String fieldname;

	@Column(name = "fieldtype")
	String fieldtype;

	@Column(name = "readable")
	boolean readable;
	
	@Column(name = "fieldrestriction")
	String fieldrestriction;

	@Column(name = "writeable")
	boolean writeable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getEntityname() {
		return entityname;
	}

	public void setEntityname(String entityname) {
		this.entityname = entityname;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}


	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getFieldrestriction() {
		return fieldrestriction;
	}

	public void setFieldrestriction(String fieldrestriction) {
		this.fieldrestriction = fieldrestriction;
	}

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(boolean readable) {
		this.readable = readable;
	}

	public boolean isWriteable() {
		return writeable;
	}

	public void setWriteable(boolean writeable) {
		this.writeable = writeable;
	}

}
