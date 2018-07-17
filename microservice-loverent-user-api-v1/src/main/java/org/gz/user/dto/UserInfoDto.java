package org.gz.user.dto;

import java.util.Date;

/**
 * 用户信息 dto
 * @author yangdx
 */
public class UserInfoDto {
	/**ID*/
    private Long userId;
    /**手机号*/
    private String phoneNum;
    /**登录密码*/
    private String loginPassword;
    /**头像*/
    private String avatar;
    /**昵称*/
    private String nickName;
    /**真实姓名*/
    private String realName;
    /**身份证*/
    private String idNo;
    /**最新芝麻分*/
    private Integer zhimaScore;
    /**性别*/
    private Integer gender;
    /**身份证正面地址*/
    private String cardFaceUrl;
    /**身份证反面地址*/
    private String cardBackUrl;
    /**实名认证状态	0-未认证  1-已认证*/
    private Integer realnameCertState;
    /**户籍地址-ocr*/
    private String residenceAddress;
    /**签发机关-OCR*/
    private String issuingAuthority;
    /**开始有效日期-OCR*/
    private Date effectiveStartDate;
    /**结束有效日期-OCR*/
    private Date effectiveEndDate;
    /**用户状态  1-正常 2-禁用*/
    private Integer userStatus;
    
    /**个人地址-省*/
    private String addrProvince;
    /**个人地址-市*/
    private String addrCity;
    /**个人地址-详细*/
    private String addrDetail;
    /**应用下载渠道*/
    private String channelType;
    
    /**设备imei号*/
    private String deviceId;
    /**设备机型*/
    private String deviceType;
    /**操作系统类型*/
    private String osType;
    /**应用版本号*/
    private String appVersion;
    /**用户来源*/
    private String sourceType;
    
    /**生日*/
    private Date birthday;
    /**年龄*/
    private Integer age;
    /**职业*/
    private Integer job;
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
    /**行业关注名单对应的值*/
    private Integer watchListValue;
    /**芝麻分最后一次刷新时间*/
    private Date zhimaScoreRefreshTime;
    
    public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public Integer getZhimaScore() {
		return zhimaScore;
	}
	public void setZhimaScore(Integer zhimaScore) {
		this.zhimaScore = zhimaScore;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getCardFaceUrl() {
		return cardFaceUrl;
	}
	public void setCardFaceUrl(String cardFaceUrl) {
		this.cardFaceUrl = cardFaceUrl;
	}
	public String getCardBackUrl() {
		return cardBackUrl;
	}
	public void setCardBackUrl(String cardBackUrl) {
		this.cardBackUrl = cardBackUrl;
	}
	public Integer getRealnameCertState() {
		return realnameCertState;
	}
	public void setRealnameCertState(Integer realnameCertState) {
		this.realnameCertState = realnameCertState;
	}
	public String getResidenceAddress() {
		return residenceAddress;
	}
	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	public String getIssuingAuthority() {
		return issuingAuthority;
	}
	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getWatchListValue() {
		return watchListValue;
	}
	public void setWatchListValue(Integer watchListValue) {
		this.watchListValue = watchListValue;
	}
	public Date getZhimaScoreRefreshTime() {
		return zhimaScoreRefreshTime;
	}
	public void setZhimaScoreRefreshTime(Date zhimaScoreRefreshTime) {
		this.zhimaScoreRefreshTime = zhimaScoreRefreshTime;
	}
    
}
