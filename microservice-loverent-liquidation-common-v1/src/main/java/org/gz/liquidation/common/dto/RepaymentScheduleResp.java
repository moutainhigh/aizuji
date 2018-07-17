package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.liquidation.common.Enum.SettleWayEnum;
import org.gz.liquidation.common.utils.LiquidationConstant;
/**
 * 
 * @Description: 还款计划响应dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
public class RepaymentScheduleResp {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 订单号
	 */
	private String orderSN;
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
	 * 本期折旧违约金
	 */
	private BigDecimal liquidatedDamages;
	/**
	 * 折旧违约状态
	 */
	private Integer breachState;
	/**
	 * 买断金
	 */
	private BigDecimal buyoutAmount;
	/**
	 * 是否结清
	 */
	private String settleDesc;
	/**
	 * 结清方式描述
	 */
	private String settleWayDesc;
	/**
	 * 还款时间
	 */
	private Date repaymentTime;
	
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
	public BigDecimal getLiquidatedDamages() {
		return liquidatedDamages;
	}
	public void setLiquidatedDamages(BigDecimal liquidatedDamages) {
		this.liquidatedDamages = liquidatedDamages;
	}
	public Integer getBreachState() {
		return breachState;
	}
	public void setBreachState(Integer breachState) {
		this.breachState = breachState;
	}
	public BigDecimal getBuyoutAmount() {
		return buyoutAmount;
	}
	public void setBuyoutAmount(BigDecimal buyoutAmount) {
		this.buyoutAmount = buyoutAmount;
	}
	public String getSettleDesc() {
		settleDesc = "否";
		if(LiquidationConstant.SETTLE_FLAG_YES == this.settleFlag){
			settleDesc = "是";
		}
		return settleDesc;
	}
	public void setSettleDesc(String settleDesc) {
		this.settleDesc = settleDesc;
	}
	public String getSettleWayDesc() {
		settleWayDesc = SettleWayEnum.getDesc(this.settleWay);
		return settleWayDesc;
	}
	public void setSettleWayDesc(String settleWayDesc) {
		this.settleWayDesc = settleWayDesc;
	}
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	
}
