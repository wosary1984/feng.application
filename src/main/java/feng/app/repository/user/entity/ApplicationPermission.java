package feng.app.repository.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_PERMISSION")
public class ApplicationPermission {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	String name;
	
	@Column(name = "type", nullable = false)
	String type;
	
	@Column(name = "description", insertable = true)
	String description;
	
	@Column(name = "url", insertable = true)
	String url;
	
	@Column(name = "method", insertable = true)
	String method;
	
	@ManyToOne(cascade = {CascadeType.ALL }, optional = true)
	@JoinColumn(name="pid",  nullable = true)
	ApplicationPermission permisson;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public ApplicationPermission getPermisson() {
		return permisson;
	}

	public void setPermisson(ApplicationPermission permisson) {
		this.permisson = permisson;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
