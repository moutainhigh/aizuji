package org.gz.overdue.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.QueryPager;


public class OverdueOrderPage extends QueryPager{ 

	private static final long serialVersionUID = -3791206349486308188L;



 	/** 
	* 获取 id
	*/ 
	public Integer getId(){ return id ; } 
	/** 
	* 设置 id
	*/ 
	public void setId(Integer id){ this.id=id ; } 
 	/** 
	* 获取 订单编号
	*/ 
	public String getOrderSN(){ return orderSN ; } 
	/** 
	* 设置 订单编号
	*/ 
	public void setOrderSN(String orderSN){ this.orderSN=orderSN ; } 

 	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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

 	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Integer getOverdueDayStart() {
		return overdueDayStart;
	}
	public void setOverdueDayStart(Integer overdueDayStart) {
		this.overdueDayStart = overdueDayStart;
	}
	public Integer getOverdueDayEnd() {
		return overdueDayEnd;
	}
	public void setOverdueDayEnd(Integer overdueDayEnd) {
		this.overdueDayEnd = overdueDayEnd;
	}
	public String getAllocationTimeStart() {
		return allocationTimeStart;
	}
	public void setAllocationTimeStart(String allocationTimeStart) {
		this.allocationTimeStart = allocationTimeStart;
	}
	public String getAllocationTimeEnd() {
		return allocationTimeEnd;
	}
	public void setAllocationTimeEnd(String allocationTimeEnd) {
		this.allocationTimeEnd = allocationTimeEnd;
	}
	public String getContactTimeStart() {
		return contactTimeStart;
	}
	public void setContactTimeStart(String contactTimeStart) {
		this.contactTimeStart = contactTimeStart;
	}
	public String getContactTimeEnd() {
		return contactTimeEnd;
	}
	public void setContactTimeEnd(String contactTimeEnd) {
		this.contactTimeEnd = contactTimeEnd;
	}

	/** 
	* id
	*/ 
	private Integer id; 

 	/** 
	* 订单编号
	*/ 
	private String orderSN; 

	/** 
	 * 用户Id
	 */ 
	private String userId; 
	
 	/** 
	* 用户姓名
	*/ 
	private String userName; 

	/** 
	 * 用户手机号
	 */ 
	private String userPhone; 
 	/** 
	* 用户手机号
	*/ 
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
	* 逾期起始天数
	*/ 
	private Integer overdueDayStart; 
	/** 
	* 逾期截止天数
	*/ 
	private Integer overdueDayEnd; 

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
	* 分配开始时间
	*/ 
	private String allocationTimeStart; 
	/** 
	* 分配结束时间
	*/ 
	private String allocationTimeEnd; 

 	/** 
	* 最近联系时间
	*/ 
	private Date contactTime; 
 	/** 
	* 最近联系开始时间
	*/ 
	private String contactTimeStart; 
 	/** 
	* 最近联系结束时间
	*/ 
	private String contactTimeEnd; 

 	/** 
	* 最近联系结果
	*/ 
	private Integer contactResult; 

 	/** 
	* 订单状态
	*/ 
	private Integer orderStatus; 



} 