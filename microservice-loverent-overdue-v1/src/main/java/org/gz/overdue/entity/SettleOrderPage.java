package org.gz.overdue.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.QueryPager;


public class SettleOrderPage extends QueryPager{ 

	private static final long serialVersionUID = -3791206349486308188L;



 	public Integer getId(){ return id ; } 
	public void setId(Integer id){ this.id=id ; } 
 	/** 
	* 获取 订单编号
	*/ 
	public String getOrderSN(){ return orderSN ; } 
	/** 
	* 设置 订单编号
	*/ 
	public void setOrderSN(String orderSN){ this.orderSN=orderSN ; } 
 	/** 
	* 获取 用户姓名
	*/ 
	public String getUserName(){ return userName ; } 
	/** 
	* 设置 用户姓名
	*/ 
	public void setUserName(String userName){ this.userName=userName ; } 
 	/** 
	* 获取 用户手机号
	*/ 
	public String getPhone(){ return phone ; } 
	/** 
	* 设置 用户手机号
	*/ 
	public void setPhone(String phone){ this.phone=phone ; } 
 	/** 
	* 获取 签约时间
	*/ 
	public Date getSignTime(){ return signTime ; } 
	/** 
	* 设置 签约时间
	*/ 
	public void setSignTime(Date signTime){ this.signTime=signTime ; } 
 	/** 
	* 获取 逾期天数
	*/ 
	public Integer getOverdueDay(){ return overdueDay ; } 
	/** 
	* 设置 逾期天数
	*/ 
	public void setOverdueDay(Integer overdueDay){ this.overdueDay=overdueDay ; } 
 	/** 
	* 获取 应还金额
	*/ 
	public BigDecimal getDueAmount(){ return dueAmount ; } 
	/** 
	* 设置 应还金额
	*/ 
	public void setDueAmount(BigDecimal dueAmount){ this.dueAmount=dueAmount ; } 
 	/** 
	* 获取 开始逾期时间
	*/ 
	public Date getOverdueTime(){ return overdueTime ; } 
	/** 
	* 设置 开始逾期时间
	*/ 
	public void setOverdueTime(Date overdueTime){ this.overdueTime=overdueTime ; } 
 	/** 
	* 获取 租期
	*/ 
	public Integer getLeaseTimes(){ return leaseTimes ; } 
	/** 
	* 设置 租期
	*/ 
	public void setLeaseTimes(Integer leaseTimes){ this.leaseTimes=leaseTimes ; } 
 	/** 
	* 获取 跟进人
	*/ 
	public String getFollowPerson(){ return followPerson ; } 
	/** 
	* 设置 跟进人
	*/ 
	public void setFollowPerson(String followPerson){ this.followPerson=followPerson ; } 
 	/** 
	* 获取 分配时间
	*/ 
	public Date getAllocationTime(){ return allocationTime ; } 
	/** 
	* 设置 分配时间
	*/ 
	public void setAllocationTime(Date allocationTime){ this.allocationTime=allocationTime ; } 
 	/** 
	* 获取 最近联系时间
	*/ 
	public Date getContactTime(){ return contactTime ; } 
	/** 
	* 设置 最近联系时间
	*/ 
	public void setContactTime(Date contactTime){ this.contactTime=contactTime ; } 
 	/** 
	* 获取 最近联系结果
	*/ 
	public Integer getContactResult(){ return contactResult ; } 
	/** 
	* 设置 最近联系结果
	*/ 
	public void setContactResult(Integer contactResult){ this.contactResult=contactResult ; } 
 	/** 
	* 获取 订单状态
	*/ 
	public Integer getOrderStatus(){ return orderStatus ; } 
	/** 
	* 设置 订单状态
	*/ 
	public void setOrderStatus(Integer orderStatus){ this.orderStatus=orderStatus ; } 
 	/** 
	* 获取 结清时间
	*/ 
	public Date getSettleTime(){ return settleTime ; } 
	/** 
	* 设置 结清时间
	*/ 
	public void setSettleTime(Date settleTime){ this.settleTime=settleTime ; } 

 	private Integer id; 

 	/** 
	* 订单编号
	*/ 
 	@NotNull(message = "订单编号不能为null")
	private String orderSN; 

 	/** 
	* 用户姓名
	*/ 
 	@NotNull(message = "用户姓名不能为null")
	private String userName; 

 	/** 
	* 用户手机号
	*/ 
 	@NotNull(message = "用户手机号不能为null")
	private String phone; 

 	/** 
	* 签约时间
	*/ 
	private Date signTime; 

 	/** 
	* 逾期天数
	*/ 
	private Integer overdueDay; 

 	/** 
	* 应还金额
	*/ 
	private BigDecimal dueAmount; 

 	/** 
	* 开始逾期时间
	*/ 
	private Date overdueTime; 

 	/** 
	* 租期
	*/ 
	private Integer leaseTimes; 

 	/** 
	* 跟进人
	*/ 
	private String followPerson; 

 	/** 
	* 分配时间
	*/ 
	private Date allocationTime; 

 	/** 
	* 最近联系时间
	*/ 
	private Date contactTime; 

 	/** 
	* 最近联系结果
	*/ 
	@NotNull(message = "联系结果不能为null")
	private Integer contactResult; 

 	/** 
	* 订单状态
	*/ 
	private Integer orderStatus; 

 	/** 
	* 结清时间
	*/ 
	private Date settleTime; 



} 