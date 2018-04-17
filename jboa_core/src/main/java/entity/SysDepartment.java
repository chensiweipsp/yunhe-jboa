package entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * SysDepartment entity. @author MyEclipse Persistence Tools
 */

@JsonIgnoreProperties(value={"emps"}) 
public class SysDepartment implements java.io.Serializable {
	// Fields
	private Long id;
	private String name;
	private Set<SysEmployee> emps=new HashSet<SysEmployee>();
	// Constructors

	/** default constructor */
	public SysDepartment() {
	}

	/** full constructor */
	public SysDepartment(String name) {
		this.name = name;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SysEmployee> getEmps() {
		return emps;
	}

	public void setEmps(Set<SysEmployee> emps) {
		this.emps = emps;
	}
	
	

}