package org.gz.liquidation.common.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 * 
 * @Description:TODO	首期下单请求
 * Author	Version		Date		Changes
 * hening  	1.0  		2017年12月29日 	Created
 */
public class DownPaymentReq {
	/**
	 * 用户id
	 */
	@NotNull(message="用户id不能为空")
	@Min(value=1)
	private Long userId;
	
	/**
	 * 订单号
	 */
	@NotNull(message="订单号不能为空")
	private String orderSN;
	
	@NotNull(message="交易方式不能为空")
	private String transactionWay;
	
	@NotNull(message="交易金额不能为空")
	private BigDecimal transactionAmount;
	
	@NotNull(message="交易入口不能为空")
	private String transactionSource;
	@NotNull(message="真实姓名不能为空")
	private String realName;
	
	private String phone;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public String getTransactionWay() {
		return transactionWay;
	}

	public void setTransactionWay(String transactionWay) {
		this.transactionWay = transactionWay;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
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
