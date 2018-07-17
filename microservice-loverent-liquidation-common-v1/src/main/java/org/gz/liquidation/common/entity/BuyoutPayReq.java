package org.gz.liquidation.common.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

public class BuyoutPayReq {
	@NotEmpty
	private String transactionSN;
	@NotEmpty
	private String orderSN;
	/**
	 * 用户id
	 */
	@NotNull(message="用户id不能为空")
	@Min(value=1)
	private Long userId;
    /**
     * 交易方式
     */
    private String transactionWay;
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
	public String getTransactionSN() {
		return transactionSN;
	}

	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}

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
