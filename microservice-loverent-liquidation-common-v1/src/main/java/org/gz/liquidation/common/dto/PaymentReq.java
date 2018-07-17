package org.gz.liquidation.common.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 
 * @Description:TODO	支付回调请求参数
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月27日 	Created
 */
public class PaymentReq {
	/**
	 * 交易流水号
	 */
	@NotEmpty
	private String transactionSN;
	/**
	 * 交易状态 1 成功 2 失败
	 */
	@Positive
	@NotNull
	private Integer state;
	private String remark;
	private Date finishTime; 
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
}
