package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
/**
 * 
 * @Description:	手动结算请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月5日 	Created
 */
public class ManualSettleReq {
	@NotEmpty(message="订单号orderSN不能为空")
    private String orderSN;
	/**
     * 交易流水号
     */
	@NotEmpty(message="交易流水号transactionSN不能为空")
    private String transactionSN;
	/**
	 * 交易金额
	 */
	@Positive(message="金额amount必须是正数")
	@NotNull(message="金额amount不能为空")
	private BigDecimal amount;
	/**
	 * 交易方式
	 */
	@NotEmpty(message="交易方式transactionWay不能为空")
	private String transactionWay;
	/**
	 * 交易类型
	 */
	@NotEmpty(message="交易类型transactionType不能为空")
	private String transactionType;
	/**
	 * 交易发起时间
	 */
	//@NotNull(message="交易发起时间startTime不能为空")
	private Date startTime;
	/**
	 * 交易完成时间
	 */
	//@NotNull(message="交易完成时间finishTime不能为空")
	private Date finishTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人用户id
	 */
	//@Positive(message="创建人用户id createBy必须是正数")
	private Long createBy;
	//@NotEmpty(message="操作人姓名operatorRealname不能为空")
	/**
	 * 操作人姓名
	 */
	private String operatorRealname;
	/**
	 * 用户姓名
	 */
	private String realName;
	/**
	 * 用户手机号
	 */
	private String phone;

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public String getTransactionSN() {
		return transactionSN;
	}

	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionWay() {
		return transactionWay;
	}

	public void setTransactionWay(String transactionWay) {
		this.transactionWay = transactionWay;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getOperatorRealname() {
		return operatorRealname;
	}

	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
