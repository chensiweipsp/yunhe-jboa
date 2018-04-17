package entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
public class SysRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String rolename;
	private String describe;
	private Set<SysPermission> permissions=new HashSet<>();
	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** full constructor */
	public SysRole(String rolename, String describe) {
		this.rolename = rolename;
		this.describe = describe;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Set<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}

	

}