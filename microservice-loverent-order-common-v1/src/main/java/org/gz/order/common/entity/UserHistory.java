package org.gz.order.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * UserHistory 实体类
 * 
 * @author pk
 * @date 2018-01-28
 */
public class UserHistory implements Serializable {


    /**
  * 
  */
  private static final long serialVersionUID = 1L;
  /**
   * id
   */
    private Long id;
    /**
     * 用户ID 
     */
    private Long userId;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
    /**
     * 手机号 
     */
    private String phoneNum;
    /**
     * 登录密码 
     */
    private String loginPassword;
    /**
     * 性别 0-未知 1-男2-女 
     */
    private Integer gender;
    /**
     * 用户来源 
     */
    private String channelType;
    /**
     * 设备imei号 
     */
    private String deviceId;
    /**
     * 设备机型 
     */
    private String deviceType;
    /**
     * 操作系统类型 
     */
    private String osType;
    /**
     * 应用版本号 
     */
    private String appVersion;
    /**
     * 用户来源 
     */
    private String sourceType;
    /**
     * 头像地址 
     */
    private String avatar;
    /**
     * 昵称 
     */
    private String nickName;
    /**
     * 真实姓名 
     */
    private String realName;
    /**
     * 身份证 
     */
    private String idNo;
    /**
     * 用户最新芝麻分 
     */
    private Integer zhimaScore;
    /**
     * 身份证正面URL 
     */
    private String cardFaceUrl;
    /**
     * 身份证反面URL 
     */
    private String cardBackUrl;
    /**
   * 人脸识别认证照片url地址
   */
  private String faceAuthUrl;

  /**
   * 实名认证状态 0-未认证 1-已认证
   */
    private Integer realnameCertState;
    /**
     * 户籍地址-ocr 
     */
    private String residenceAddress;
    /**
     * 签发机关-OCR 
     */
    private String issuingAuthority;
    /**
     * 开始有效日期-OCR 
     */
    private Date effectiveStartDate;
    /**
     * 结束有效日期-OCR 
     */
    private Date effectiveEndDate;
    /**
     * 支付宝user_id 
     */
    private String authAlipayUserId;
    /**
     * 微信open_id 
     */
    private String authWeixinOpenId;
    /**
     * 用户状态  1-正常 2-禁用 
     */
    private Integer userStatus;
    /**
     * birthday 
     */
    private Date birthday;
    /**
     * 年龄 
     */
    private Integer age;
    /**
     * 职业 1.学生、2工程师、3行政人员、4自由职业、5公务员、6销售、7个体户、8教师、9医生、10护士、11导游、12教练、13翻译、14产品经理、15会计师、16商务、17快递、18企业高管、19项目经理 
     */
    private Integer job;
    /**
     * 初中及以下-1 高中/中专-2 大专-3 本科-4  硕士-5  博士及以上-6 
     */
    private Integer education;
    /**
     * 行业：1 政府机关、2 医院、3 教育、4 培训、5 IT相关、6 金融、7 保险、8 家政、9 餐饮、10 旅游、11 商业、12 农业、13 其他； 
     */
    private Integer industry;
    /**
     * 月收入 
     */
    private String monthIncome;
    /**
     * 手机使用时长 
     */
    private String phoneUserTime;
    /**
     * 用户邮箱 
     */
    private String userEmail;
    /**
     * 单位名称 
     */
    private String companyName;
    /**
     * 入职时间 
     */
    private Date entryTime;
    /**
     * 职务 
     */
    private String position;
    /**
     * 公司联系电话 
     */
    private String companyContactNumber;
    /**
     * 公司地址 
     */
    private String companyAddress;
    /**
     * 学校名称 
     */
    private String schoolName;
    /**
     * 生活费 
     */
    private String livingExpenses;
    /**
     * 学校地址 
     */
    private String schoolAddress;
    /**
     * 有无贷款 0-无  1-有 
     */
    private Integer hasLoanRecord;
    /**
     * 贷款金额 
     */
    private String loanAmount;
    /**
     * 月还款 
     */
    private String monthRepayment;
    /**
     * 婚姻状况 0未婚、1已婚、2离婚； 
     */
    private Integer marital;
    /**
     * createTime 
     */
    private Date createTime;
    /**
     * updateTime 
     */
    private Date updateTime;
    /**
     * 行业关注名单对应的值	0-不存在逾期,1-逾期轻微,2-逾期中等,3-逾期严重,9-待查询
     */
    private Integer watchListValue;
    /**
     * 芝麻分最后一次刷新时间
     */
    private Date zhimaScoreRefreshTime;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public String getPhoneNum() {
        return this.phoneNum;
    }
    
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    
    public String getLoginPassword() {
        return this.loginPassword;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public Integer getGender() {
        return this.gender;
    }
    
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    
    public String getChannelType() {
        return this.channelType;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getDeviceId() {
        return this.deviceId;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    public String getDeviceType() {
        return this.deviceType;
    }
    
    public void setOsType(String osType) {
        this.osType = osType;
    }
    
    public String getOsType() {
        return this.osType;
    }
    
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
    
    public String getAppVersion() {
        return this.appVersion;
    }
    
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    
    public String getSourceType() {
        return this.sourceType;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public String getAvatar() {
        return this.avatar;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getNickName() {
        return this.nickName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getRealName() {
        return this.realName;
    }
    
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    
    public String getIdNo() {
        return this.idNo;
    }
    
    public void setZhimaScore(Integer zhimaScore) {
        this.zhimaScore = zhimaScore;
    }
    
    public Integer getZhimaScore() {
        return this.zhimaScore;
    }
    
    public void setCardFaceUrl(String cardFaceUrl) {
        this.cardFaceUrl = cardFaceUrl;
    }
    
    public String getCardFaceUrl() {
        return this.cardFaceUrl;
    }
    
    public void setCardBackUrl(String cardBackUrl) {
        this.cardBackUrl = cardBackUrl;
    }
    
    public String getCardBackUrl() {
        return this.cardBackUrl;
    }
    
    public void setRealnameCertState(Integer realnameCertState) {
        this.realnameCertState = realnameCertState;
    }
    
    public Integer getRealnameCertState() {
        return this.realnameCertState;
    }
    
    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }
    
    public String getResidenceAddress() {
        return this.residenceAddress;
    }
    
    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }
    
    public String getIssuingAuthority() {
        return this.issuingAuthority;
    }
    
    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }
    
    public Date getEffectiveStartDate() {
        return this.effectiveStartDate;
    }
    
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    
    public Date getEffectiveEndDate() {
        return this.effectiveEndDate;
    }
    
    public void setAuthAlipayUserId(String authAlipayUserId) {
        this.authAlipayUserId = authAlipayUserId;
    }
    
    public String getAuthAlipayUserId() {
        return this.authAlipayUserId;
    }
    
    public void setAuthWeixinOpenId(String authWeixinOpenId) {
        this.authWeixinOpenId = authWeixinOpenId;
    }
    
    public String getAuthWeixinOpenId() {
        return this.authWeixinOpenId;
    }
    
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
    
    public Integer getUserStatus() {
        return this.userStatus;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Integer getAge() {
        return this.age;
    }
    
    public void setJob(Integer job) {
        this.job = job;
    }
    
    public Integer getJob() {
        return this.job;
    }
    
    public void setEducation(Integer education) {
        this.education = education;
    }
    
    public Integer getEducation() {
        return this.education;
    }
    
    public void setIndustry(Integer industry) {
        this.industry = industry;
    }
    
    public Integer getIndustry() {
        return this.industry;
    }
    
    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }
    
    public String getMonthIncome() {
        return this.monthIncome;
    }
    
    public void setPhoneUserTime(String phoneUserTime) {
        this.phoneUserTime = phoneUserTime;
    }
    
    public String getPhoneUserTime() {
        return this.phoneUserTime;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getUserEmail() {
        return this.userEmail;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
    
    public Date getEntryTime() {
        return this.entryTime;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getPosition() {
        return this.position;
    }
    
    public void setCompanyContactNumber(String companyContactNumber) {
        this.companyContactNumber = companyContactNumber;
    }
    
    public String getCompanyContactNumber() {
        return this.companyContactNumber;
    }
    
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    
    public String getCompanyAddress() {
        return this.companyAddress;
    }
    
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    
    public String getSchoolName() {
        return this.schoolName;
    }
    
    public void setLivingExpenses(String livingExpenses) {
        this.livingExpenses = livingExpenses;
    }
    
    public String getLivingExpenses() {
        return this.livingExpenses;
    }
    
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }
    
    public String getSchoolAddress() {
        return this.schoolAddress;
    }
    
    public void setHasLoanRecord(Integer hasLoanRecord) {
        this.hasLoanRecord = hasLoanRecord;
    }
    
    public Integer getHasLoanRecord() {
        return this.hasLoanRecord;
    }
    
    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }
    
    public String getLoanAmount() {
        return this.loanAmount;
    }
    
    public void setMonthRepayment(String monthRepayment) {
        this.monthRepayment = monthRepayment;
    }
    
    public String getMonthRepayment() {
        return this.monthRepayment;
    }
    
    public void setMarital(Integer marital) {
        this.marital = marital;
    }
    
    public Integer getMarital() {
        return this.marital;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Date getUpdateTime() {
        return this.updateTime;
    }

  public String getFaceAuthUrl() {
    return faceAuthUrl;
  }

  public void setFaceAuthUrl(String faceAuthUrl) {
    this.faceAuthUrl = faceAuthUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
