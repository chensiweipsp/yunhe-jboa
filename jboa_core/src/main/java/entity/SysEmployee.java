package entity;

import java.util.HashSet;
import java.util.Set;


/**
 * SysEmployee entity. @author MyEclipse Persistence Tools
 */
public class SysEmployee implements java.io.Serializable {

	// Fields

	private int sn;
	private SysPosition position;
	private String name;
	private String password;
	private String status;
	private SysDepartment dept;
	private Set<SysRole>  roles=new HashSet<>();
	private String salt ;
    private String rolesnames;
    private String rolesdescribes;
    private String email;
	// Constructors

	/** default constructor */
	public SysEmployee() {
	}
	
	public SysEmployee(int sn) {
		this.sn=sn;
	}
	
	/** full constructor */
	public SysEmployee(SysPosition sysposition, Long departmentId, String name,
			String password, String status) {
		this.position = sysposition;
		this.name = name;
		this.password = password;
		this.status = status;
	}

	// Property accessors

	public int getSn() {
		return this.sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SysDepartment getDept() {
		return dept;
	}

	public void setDept(SysDepartment dept) {
		this.dept = dept;
	}

	public SysPosition getPosition() {
		return position;
	}

	public void setPosition(SysPosition position) {
		this.position = position;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRolesnames() {
		return rolesnames;
	}

	public void setRolesnames(String rolesnames) {
		this.rolesnames = rolesnames;
	}

	public String getRolesdescribes() {
		return rolesdescribes;
	}

	public void setRolesdescribes(String rolesdescribes) {
		this.rolesdescribes = rolesdescribes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}