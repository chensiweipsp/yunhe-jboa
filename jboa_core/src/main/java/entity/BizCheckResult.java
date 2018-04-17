package entity;

import java.sql.Timestamp;

/**
 * BizCheckResult entity. @author MyEclipse Persistence Tools
 */

public class BizCheckResult implements java.io.Serializable {

	// Fields

	private Long id;
	private Long claimId;
	private Timestamp checkTime;
	private String result;
	private String comm;
	
	private SysEmployee creatEmp;
	private SysEmployee nextEmp;


	// Constructors

	/** default constructor */
	public BizCheckResult() {
	}

	/** full constructor */
	public BizCheckResult(Long claimId, Timestamp checkTime, String checkerSn,
			String result, String comm) {
		this.claimId = claimId;
		this.checkTime = checkTime;
		this.result = result;
		this.comm = comm;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClaimId() {
		return this.claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getComm() {
		return this.comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public SysEmployee getCreatEmp() {
		return creatEmp;
	}

	public void setCreatEmp(SysEmployee creatEmp) {
		this.creatEmp = creatEmp;
	}

	public SysEmployee getNextEmp() {
		return nextEmp;
	}

	public void setNextEmp(SysEmployee nextEmp) {
		this.nextEmp = nextEmp;
	}


	

	
}