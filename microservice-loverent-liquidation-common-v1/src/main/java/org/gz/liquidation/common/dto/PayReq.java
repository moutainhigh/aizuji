package org.gz.liquidation.common.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description:	生成支付请求参数
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月10日 	Created
 */
public class PayReq {
	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="订单号不能为空")
	private String orderSN;
	/**
	 * 交易类型（首期款交易/租金/回收/买断/未收货违约）
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="交易类型不能为空")
	private String transactionType;
	/**
	 * 交易方式（支付宝/微信/银行卡代扣 等支付方式）
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="交易方式不能为空")
	private String transactionWay;
	/**
	 * 交易入口（来源:APP、H5、后台系统）
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="交易入口不能为空")
	private String transactionSource;
	/**
	 * 交易金额
	 */
	@ApiModelProperty(value = "必须是正数", required = true)
	@Positive(message="amount必须是正数")
	private BigDecimal amount;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "必须是正数", required = true)
	@Positive(message="用户id必须是正数")
	private Long userId;
	/**
	 * 来源类型 销售单(用户前端下单租机产生的订单)、销售退回(对用户订单做退款)、采购单(采购入库)、采购退回(对采购单做退回)
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="来源类型不能为空")
	private String sourceType;
	/**
	 * 用户账号
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="用户账号不能为空")
	private String username;
	
	@ApiModelProperty(value = "不能为空", required = true)
	//@NotEmpty(message="姓名realName不能为空")
	@Setter @Getter
	private String realName;
	/**
	 * 用户手机号
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="手机号phone不能为空")
	@Setter @Getter
	private String phone;
	@ApiModelProperty(value = "流水号")
	private String transactionSN;
	@ApiModelProperty(value = "交易来源账户")
	private String fromAccount;
	/**
	 * 优惠券(格式: 优惠券id@CCY优惠金额;优惠券id@CCY优惠金额)
	 */
	@Setter @Getter
	private String coupons;
	/**
	 * 优惠券id
	 */
	@Setter @Getter
	private Long couponId;
	/**
	 * 优惠券使用数量
	 */
	@Setter @Getter
	private int couponCount;
	/**
	 * 优惠券总金额
	 */
	@Setter @Getter
	private BigDecimal couponFee;
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionWay() {
		return transactionWay;
	}
	public void setTransactionWay(String transactionWay) {
		this.transactionWay = transactionWay;
	}
	public String getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	
}
