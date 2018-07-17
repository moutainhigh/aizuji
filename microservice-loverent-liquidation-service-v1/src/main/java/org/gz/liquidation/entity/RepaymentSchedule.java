package org.gz.liquidation.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Description:	还款计划实体类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
public class RepaymentSchedule {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 订单号
	 */
	private String orderSN;
	/**
	 * 芝麻订单号
	 */
	private String zmOrderNo;
	/**
	 * 分期数
	 */
	private Integer periods;
	/**
	 * 还款日期
	 */
	private Date paymentDueDate;
	/**
	 * 本期应还租金
	 */
	private BigDecimal due;
	/**
	 * 实还租金
	 */
	private BigDecimal actualRepayment;
	/**
	 * 未还租金
	 */
	private BigDecimal currentBalance;
	/**
	 * 商品官网价值
	 */
	private BigDecimal goodsValue;
	/**
	 * 是否结清 0 否 1是
	 */
	private Integer settleFlag;
	/**
	 * 结清方式
	 */
	private String settleWay;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 结清日期
	 */
	private Date settleDate;
	/**
	 * 逾期天数
	 */
	private Integer overdueDay;
	/**
	 * 违约状态
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createOn;
	/**
	 * 更新人
	 */
	private Long updateBy;
	/**
	 * 更新时间
	 */
	private Date updateOn;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 是否有效 0 否 1是
	 */
	private Integer enableFlag;
	/**
	 * 还款类型 
	 */
	private Integer repaymentType;
	/**
	 * 第几期账期
	 */
	private Integer periodOf;
	/**
	 * 实际还款时间
	 */
	private Date repaymentTime;
	/**
	 * 订单类型 0 APP订单 1 支付宝订单
	 */
	private Integer orderType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public String getZmOrderNo() {
		return zmOrderNo;
	}
	public void setZmOrderNo(String zmOrderNo) {
		this.zmOrderNo = zmOrderNo;
	}
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public BigDecimal getDue() {
		return due;
	}
	public void setDue(BigDecimal due) {
		this.due = due;
	}
	public BigDecimal getActualRepayment() {
		return actualRepayment;
	}
	public void setActualRepayment(BigDecimal actualRepayment) {
		this.actualRepayment = actualRepayment;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public BigDecimal getGoodsValue() {
		return goodsValue;
	}
	public void setGoodsValue(BigDecimal goodsValue) {
		this.goodsValue = goodsValue;
	}
	public Integer getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(Integer settleFlag) {
		this.settleFlag = settleFlag;
	}
	public String getSettleWay() {
		return settleWay;
	}
	public void setSettleWay(String settleWay) {
		this.settleWay = settleWay;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public Integer getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}
	public Integer getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}
	public Integer getPeriodOf() {
		return periodOf;
	}
	public void setPeriodOf(Integer periodOf) {
		this.periodOf = periodOf;
	}
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
}
