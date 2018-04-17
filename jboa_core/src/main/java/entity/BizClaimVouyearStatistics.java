package entity;

import java.sql.Timestamp;

/**
 * BizClaimVouyearStatistics entity. @author MyEclipse Persistence Tools
 */

public class BizClaimVouyearStatistics implements java.io.Serializable {

	// Fields

	private Long id;
	private Double totalCount;
	private Short year;
	private Timestamp modifyTime;
	private SysEmployee emp;
	private SysEmployee dept;

	// Constructors

	/** default constructor */
	public BizClaimVouyearStatistics() {
	}

	/** full constructor */
	public BizClaimVouyearStatistics(Double totalCount, Short year,
			Timestamp modifyTime, SysEmployee emp) {
		this.totalCount = totalCount;
		this.year = year;
		this.modifyTime = modifyTime;
		this.emp = emp;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Double totalCount) {
		this.totalCount = totalCount;
	}

	public Short getYear() {
		return this.year;
	}

	public void setYear(Short year) {
		this.year = year;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public SysEmployee getEmp() {
		return emp;
	}

	public void setEmp(SysEmployee emp) {
		this.emp = emp;
	}

	public SysEmployee getDept() {
		return dept;
	}

	public void setDept(SysEmployee dept) {
		this.dept = dept;
	}


	

}