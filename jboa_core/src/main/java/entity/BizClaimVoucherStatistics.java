package entity;

import java.sql.Timestamp;

/**
 * BizClaimVoucherStatistics entity. @author MyEclipse Persistence Tools
 */

public class BizClaimVoucherStatistics implements java.io.Serializable {

	// Fields

	private Long id;
	private Double totalCount;
	private Short year;
	private Byte month;
	private Long departmentId;
	private Timestamp modifyTime;

	// Constructors

	/** default constructor */
	public BizClaimVoucherStatistics() {
	}

	/** full constructor */
	public BizClaimVoucherStatistics(Double totalCount, Short year, Byte month,
			Long departmentId, Timestamp modifyTime) {
		this.totalCount = totalCount;
		this.year = year;
		this.month = month;
		this.departmentId = departmentId;
		this.modifyTime = modifyTime;
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

	public Byte getMonth() {
		return this.month;
	}

	public void setMonth(Byte month) {
		this.month = month;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}