package entity;

/**
 * BizClaimVoucherDetail entity. @author MyEclipse Persistence Tools
 */

public class BizClaimVoucherDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer state;
	private BizClaimVoucher bizClaimVoucher;

	// Constructors

	/** default constructor */
	public BizClaimVoucherDetail() {
	}

	/** full constructor */
	public BizClaimVoucherDetail(Integer state) {
		this.state = state;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public BizClaimVoucher getBizClaimVoucher() {
		return bizClaimVoucher;
	}

	public void setBizClaimVoucher(BizClaimVoucher bizClaimVoucher) {
		this.bizClaimVoucher = bizClaimVoucher;
	}
	
	

}