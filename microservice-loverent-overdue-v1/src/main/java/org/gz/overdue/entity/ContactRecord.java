package org.gz.overdue.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;


public class ContactRecord extends BaseEntity{ 

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
	* 获取 订单号
	*/ 
	public String getOrderSN(){ return orderSN ; } 
	/** 
	* 设置 订单号
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
	public String getUserPhone(){ return userPhone ; } 
	/** 
	* 设置 用户手机号
	*/ 
	public void setUserPhone(String userPhone){ this.userPhone=userPhone ; } 
 	/** 
	* 获取 联系结果
	*/ 
	public Integer getContactResult(){ return contactResult ; } 
	/** 
	* 设置 联系结果
	*/ 
	public void setContactResult(Integer contactResult){ this.contactResult=contactResult ; } 
 	/** 
	* 获取 联系时间
	*/ 
	public Date getContactTime(){ return contactTime ; } 
	/** 
	* 设置 联系时间
	*/ 
	public void setContactTime(Date contactTime){ this.contactTime=contactTime ; } 
 	/** 
	* 获取 操作人
	*/ 
	public String getOpPerson(){ return opPerson ; } 
	/** 
	* 设置 操作人
	*/ 
	public void setOpPerson(String opPerson){ this.opPerson=opPerson ; } 
 	/** 
	* 获取 备注
	*/ 
	public String getRemark(){ return remark ; } 
	/** 
	* 设置 备注
	*/ 
	public void setRemark(String remark){ this.remark=remark ; } 

 	public String getContactTimeDesc() {
		return contactTimeDesc;
	}
	public void setContactTimeDesc(String contactTimeDesc) {
		this.contactTimeDesc = contactTimeDesc;
	}

	/** 
	* id
	*/ 
	private Integer id; 

 	/** 
	* 订单号
	*/ 
	@NotNull(message = "订单号不能为null")
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
	private String userPhone; 

 	/** 
	* 联系结果
	*/ 
	@NotNull(message = "联系结果不能为null")
	private Integer contactResult; 

 	/** 
	* 联系时间
	*/ 
	private Date contactTime; 
	
	private String contactTimeDesc; 

 	/** 
	* 操作人
	*/ 
	private String opPerson; 

 	/** 
	* 备注
	*/ 
	private String remark; 



} 