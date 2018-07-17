package org.gz.user.entity;

import java.util.Date;

public class UserInfo {
	/**ID*/
    private Long id;
    /*用户ID**/
    private Long userId;
    /**生日*/
    private Date birthday;
    /**年龄*/
    private Integer age;
    /**职业*/
    private Integer job;
    /**婚姻状况 0未婚、1已婚、2离婚*/
    private Integer marital;
    /**学历*/
    private Integer education;
    /**行业*/
    private Integer industry;
    /**月收入*/
    private String monthIncome;
    /**手机使用时长*/
    private String phoneUserTime;
    /**用户邮箱*/
    private String userEmail;
    /**单位名称*/
    private String companyName;
    /**入职时间*/
    private Date entryTime;
    /**职务*/
    private String position;
    /**公司联系电话*/
    private String companyContactNumber;
    /**公司地址*/
    private String companyAddress;
    /**学校名称*/
    private String schoolName;
    /**生活费*/
    private String livingExpenses;
    /**学校地址*/
    private String schoolAddress;
    /**有无贷款 0-无  1-有*/
    private Integer hasLoanRecord;
    /**贷款金额*/
    private String loanAmount;
    /**月还款*/
    private String monthRepayment;
    /**创建时间*/
    private Date createTime;

    private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public Integer getMarital() {
		return marital;
	}

	public void setMarital(Integer marital) {
		this.marital = marital;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public String getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(String monthIncome) {
		this.monthIncome = monthIncome;
	}

	public String getPhoneUserTime() {
		return phoneUserTime;
	}

	public void setPhoneUserTime(String phoneUserTime) {
		this.phoneUserTime = phoneUserTime;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompanyContactNumber() {
		return companyContactNumber;
	}

	public void setCompanyContactNumber(String companyContactNumber) {
		this.companyContactNumber = companyContactNumber;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getLivingExpenses() {
		return livingExpenses;
	}

	public void setLivingExpenses(String livingExpenses) {
		this.livingExpenses = livingExpenses;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public Integer getHasLoanRecord() {
		return hasLoanRecord;
	}

	public void setHasLoanRecord(Integer hasLoanRecord) {
		this.hasLoanRecord = hasLoanRecord;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getMonthRepayment() {
		return monthRepayment;
	}

	public void setMonthRepayment(String monthRepayment) {
		this.monthRepayment = monthRepayment;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}