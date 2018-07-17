package org.gz.liquidation.common.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description:TODO	提前买断请求参数
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月23日 	Created
 */
public class BuyoutReq {
	@NotEmpty
	private String orderSN;
	/**
	 * 用户id
	 */
	@NotNull(message="用户id不能为空")
	@Min(value=1)
	private Long userId;
	/**
	 * 支付金额
	 */
	@Positive
	@NotNull
	private BigDecimal amount;
	/**
	 * 交易入口（来源:APP、H5、后台系统）
	 */
	@NotEmpty
	private String transactionSource;
    /**
     * 交易方式
     */
    private String transactionWay;
    /**
     * 买断类型 1 正常 2 逾期
     */
	@Positive
	@NotNull
	@Setter
	@Getter
    private Integer type;
	/**
	 * 用户账号
	 */
	@NotEmpty
	private String userName;
	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}

	public String getTransactionWay() {
		return transactionWay;
	}

	public void setTransactionWay(String transactionWay) {
		this.transactionWay = transactionWay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
