package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;
/**
 * 
 * @Description	订单响应Dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月19日 	Created
 */
public class AfterRentOrderDetailResp extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5530810119697293430L;
	/**
	 * 订单号
	 */
	private String rentRecordNo;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 身份证
	 */
	private String idNo;
	/**
	 * 签约日期
	 */
	private Date signTime;
	/**
	 * 租期截止日
	 */
	private Date backTime;
	/**
	 * 分期数
	 */
	private int leaseTerm;
	/**
	 * 租金
	 */
	private BigDecimal leaseAmount;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 状态描述
	 */
	private String stateDesc;
	public String getRentRecordNo() {
		return rentRecordNo;
	}
	public void setRentRecordNo(String rentRecordNo) {
		this.rentRecordNo = rentRecordNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	public int getLeaseTerm() {
		return leaseTerm;
	}
	public void setLeaseTerm(int leaseTerm) {
		this.leaseTerm = leaseTerm;
	}
	public BigDecimal getLeaseAmount() {
		return leaseAmount;
	}
	public void setLeaseAmount(BigDecimal leaseAmount) {
		this.leaseAmount = leaseAmount;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	
}
