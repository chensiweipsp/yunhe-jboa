package entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * BizClaimVoucher entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value = { "bizCheckResults" }) 

public class BizClaimVoucher implements java.io.Serializable {

	// Fields
	private final String createSnTitle ="申请人";
	private final String nextDealSnTitle ="待审核人";
	private final String createTimeTitle ="创建时间";
	private final String eventTitle ="事由";
	private final String totalAccountTitle ="总费用";
	private final String statusTitle ="状态";
	private final String modifyTimeTitle ="修改日期";



	private Long id;
	private SysEmployee nextDealSn;
	private SysEmployee createSn;
	private SysEmployee createName;
	private Date createTime;
	private String event;
	private Double totalAccount;
	private String status;
	private Date modifyTime;
	private BizClaimVoucherDetail bizClaimVoucherDetail;
	private Set<BizCheckResult> bizCheckResults=new HashSet<BizCheckResult>();
	private int schedule;
	private String taskid;
	// Constructors

	
	public BizClaimVoucher() {
	}
	public BizClaimVoucher(long id) {
		this.id=id;
	}
	
	/** minimal constructor */
	public BizClaimVoucher(SysEmployee createSn, Timestamp createTime, String event,
			Double totalAccount, String status) {
		this.createSn = createSn;
		this.createTime = createTime;
		this.event = event;
		this.totalAccount = totalAccount;
		this.status = status;
	}

	/** full constructor */
	public BizClaimVoucher(SysEmployee nextDealSn, SysEmployee createSn,
			Timestamp createTime, String event, Double totalAccount,
			String status, Timestamp modifyTime) {
		this.nextDealSn = nextDealSn;
		this.createSn = createSn;
		this.createTime = createTime;
		this.event = event;
		this.totalAccount = totalAccount;
		this.status = status;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Double getTotalAccount() {
		return this.totalAccount;
	}

	public void setTotalAccount(Double totalAccount) {
		this.totalAccount = totalAccount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public SysEmployee getNextDealSn() {
		return nextDealSn;
	}

	public void setNextDealSn(SysEmployee nextDealSn) {
		this.nextDealSn = nextDealSn;
	}

	public SysEmployee getCreateSn() {
		return createSn;
	}

	public void setCreateSn(SysEmployee createSn) {
		this.createSn = createSn;
	}
	public String getCreateSnTitle() {
		return createSnTitle;
	}
	public String getNextDealSnTitle() {
		return nextDealSnTitle;
	}
	public String getCreateTimeTitle() {
		return createTimeTitle;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public String getTotalAccountTitle() {
		return totalAccountTitle;
	}
	public String getStatusTitle() {
		return statusTitle;
	}
	public String getModifyTimeTitle() {
		return modifyTimeTitle;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public BizClaimVoucherDetail getBizClaimVoucherDetail() {
		return bizClaimVoucherDetail;
	}
	public void setBizClaimVoucherDetail(BizClaimVoucherDetail bizClaimVoucherDetail) {
		this.bizClaimVoucherDetail = bizClaimVoucherDetail;
	}
	public int getSchedule() {
		return schedule;
	}
	public void setSchedule(int schedule) {
		this.schedule = schedule;
	}
	public Set<BizCheckResult> getBizCheckResults() {
		return bizCheckResults;
	}
	public void setBizCheckResults(Set<BizCheckResult> bizCheckResults) {
		this.bizCheckResults = bizCheckResults;
	}
	public SysEmployee getCreateName() {
		this.createName=this.createSn;
		return createName;
	}
	public void setCreateName(SysEmployee createName) {
		this.createName = createName;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	

}