package org.gz.liquidation.common.entity;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 * 
 * @Description:TODO	还款计划请求
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
public class RepaymentScheduleReq {
	/**
	 * 订单号
	 */
	@NotNull(message="订单号不能为空")
	private String orderSN;
	private BigDecimal amount;
	
	@NotNull(message="租金不能为空")
	private BigDecimal rent;
	/**
	 * 用户id
	 */
	@NotNull(message="用户id不能为空")
	@Min(value=1)
	private Long userId;
	/**
	 * 分期数
	 */
	@NotNull(message="分期数不能为空")
	private Integer periods;
	/**
     * 产品编号（生成规则：FP+8位自增长）
     */
	@NotNull(message="产品编号不能为空")
    private String productNo;
    /**
     * 保险费
     */
    private BigDecimal insurance;
    /**
     * 溢价金
     */
    private BigDecimal premium;
    /**
     * 交易流水号
     */
    private String transactionSN;
    /**
     * 商品价值
     */
    private BigDecimal goodsValue;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 手机号
     */
    @NotNull(message="手机号不能为空")
    private String phone;
    
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSn) {
		this.orderSN = orderSn;
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
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	public BigDecimal getRent() {
		return rent;
	}
	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}
	public BigDecimal getGoodsValue() {
		return goodsValue;
	}
	public void setGoodsValue(BigDecimal goodsValue) {
		this.goodsValue = goodsValue;
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
