package entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value={"roles"}) 
public class SysPermission implements java.io.Serializable {
	// Fields
	private Integer id;
	private String permissionname;
	private String describe;
	private Set<SysRole> roles= new HashSet<>();

	// Constructors

	/** default constructor */
	public SysPermission() {
	}

	/** full constructor */
	public SysPermission(String permissionname, String describe
			) {
		this.permissionname = permissionname;
		this.describe = describe;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermissionname() {
		return this.permissionname;
	}

	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	

}