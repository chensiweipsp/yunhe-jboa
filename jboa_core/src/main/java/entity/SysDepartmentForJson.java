package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysDepartment entity. @author MyEclipse Persistence Tools
 */


public class SysDepartmentForJson implements java.io.Serializable {

	// Fields
	private Long id;
	private String name;
	private Set<SysEmployee> emps=new HashSet<SysEmployee>();
	private int empsLength;	
	// Constructors
	/** default constructor */
	public SysDepartmentForJson() {
	}

	/** full constructor */
	public SysDepartmentForJson(String name) {
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

	public int getEmpsLength() {
		return empsLength;
	}

	public void setEmpsLength(int empsLength) {
		this.empsLength = empsLength;
	}
	
	

}