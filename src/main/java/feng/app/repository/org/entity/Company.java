package feng.app.repository.org.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import feng.app.annotation.ContextCheckFilter;
import feng.app.repository.base.BaseEntity;
import feng.app.service.annotation.EntityFiledCheck;

@Entity
@Table(name = "T_COMPANY")
@EntityFiledCheck
public class Company extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3379772368228059301L;

	@Id
	@Column(name = "companyid")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "COMPANY_ID_GENERATOR")
	@TableGenerator(name = "COMPANY_ID_GENERATOR", table = "T_ID_GENERATOR", pkColumnName = "pk_name", valueColumnName = "pk_value", pkColumnValue = "COMPANY_PK", allocationSize = 1)
	private Long companyid;

	@Column(name = "name", insertable = true, unique = true, nullable = false)
	String name;

	@Column(name = "description")
	String description;

	@Column(name = "logourl")
	String logourl;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "logo", columnDefinition = "BLOB", nullable = true)
	private byte[] logo;

	@OneToMany(mappedBy = "company", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(value = "id ASC")
	List<Department> departments = new ArrayList<Department>();

	public Long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}

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

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	
	public List<Department> getDepartments() {
		return departments;
	}

	@ContextCheckFilter(cls="Department.class", clazz = Department.class)
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	@JsonIgnore
	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

}
