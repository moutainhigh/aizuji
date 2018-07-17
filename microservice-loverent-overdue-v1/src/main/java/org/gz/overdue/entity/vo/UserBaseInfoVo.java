package org.gz.overdue.entity.vo;

import org.gz.common.entity.BaseEntity;

public class UserBaseInfoVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7959988904676829980L;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 用户姓名
	 */
	private String realName;
	
	/**
	 * 性别
	 */
	private Integer gender;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 身份证号码
	 */
	private String idNo;
	
	/**
	 * 户籍地址
	 */
	private String residenceAddress;
	
	/**
	 * 职业
	 */
	private Integer job;
	
	/**
	 * 行业
	 */
	private Integer industry;

	/**
	 * 最近联系地址
	 */
	private String nearAddress;
	
	/**
	 * 订单配送地址
	 */
	private String orderAddress;
	
	/**
	 * 紧急联系人
	 */
	private String emergencyContact;
	
	/**
	 * 紧急联系人电话
	 */
	private String emergencyContactPhone;
	
	/**
	 * 订单状态
	 */
	private Integer orderState;
	
	/**
	 * 订单状态备注
	 */
	private String orderStateDesc;
	
	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getOrderStateDesc() {
		return orderStateDesc;
	}

	public void setOrderStateDesc(String orderStateDesc) {
		this.orderStateDesc = orderStateDesc;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public String getNearAddress() {
		return nearAddress;
	}

	public void setNearAddress(String nearAddress) {
		this.nearAddress = nearAddress;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
