package org.gz.liquidation.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gz.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:TODO	流水记录
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月21日 	Created
 */
public class TransactionRecord extends BaseEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 交易流水号
     */
    private String transactionSN;

    /**
     * 单据编号
     */
    private String orderSN;

    /**
     * 来源类别
     */
    private String sourceType;

    /**
     * 交易方式
     */
    private String transactionWay;
    /**
     * 交易类型 首期款交易/租金/回收/买断/未收货违约(支持数据库可配)
     */
    private String transactionType;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易来源账户
     */
    private String fromAccount;

    /**
     * 交易目标账号
     */
    private String toAccount;

    /**
     * 交易入口（来源:APP、H5、后台系统）
     */
    private String transactionSource;

    /**
     * 交易状态
     */
    private Integer state;

    /**
     * 失败原因
     */
    private String failureDesc;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本
     */
    private String version;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 交易发起人
     */
    private Long createBy;

    /**
     * 交易发起时间
     */
    private Date createOn;

    /**
     * 交易完成时间
     */
    private Date updateOn;

    /**
     * 预留字段1
     */
    private String attr1;

    /**
     * 预留字段1
     */
    private String attr2;

    /**
     * 预留字段1
     */
    private String attr3;
    private String resultCode;
    /**
     * 创建人用户账号
     */
    private String createUsername;
    /**
     * 更新人用户账号
     */
    private String updateUsername;
    /**
     * 交易状态描述
     */
    private String stateDesc;
    @Setter @Getter
    private Date startDate;
    @Setter @Getter
    private Date endDate;
    /**
     * 交易完成时间
     */
    private Date finishTime;
    private List<String> orderSnList; 
    @Setter @Getter
    private BigDecimal couponFee;
    
    private static final long serialVersionUID = 1L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getFailureDesc() {
		return failureDesc;
	}
	public void setFailureDesc(String failureDesc) {
		this.failureDesc = failureDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createby) {
		this.createBy = createby;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public Date getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getUpdateUsername() {
		return updateUsername;
	}
	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public List<String> getOrderSnList() {
		return orderSnList;
	}
	public void setOrderSnList(List<String> orderSnList) {
		this.orderSnList = orderSnList;
	}
	
}