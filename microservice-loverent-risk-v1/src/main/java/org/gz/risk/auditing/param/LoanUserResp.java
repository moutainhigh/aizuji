/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.auditing.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * UserHistory 实体类
 * 
 * @author pengk
 * @date 2017-07-12
 */
public class LoanUserResp implements Serializable{
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户历史记录id 
     */
    private Long id;
    /**
     * 用户编号 
     */
    private Long userId;
    /**
     * 手机号码 
     */
    private String phoneNum;
    /**
     * 密码 
     */
    private String password;
    /**
     * 头像 
     */
    private String avatar;
    /**
     * 姓名 
     */
    private String realName;
    /**
     * 身份证号码 
     */
    private String idNum;
    /**
     * 借记卡号码 
     */
    private String debitCard;
    /**
     * 预留手机号码 
     */
    private String reservedPhoneNum;
    /**
     * 学历 
     */
    private Integer education;
    /**
     * 婚姻状况 
     */
    private Integer maritalStatus;
    /**
     * 发卡银行 
     */
    private String cardIssuingBank;
    /**
     * 公司名称 
     */
    private String companyName;
    /**
     * 公司电话 
     */
    private String companyCellNum;
    /**
     * 公司地址 
     */
    private String companyAddress;
    /**
     * 公司详细地址 
     */
    private String companyDetailedAddress;
    /**
     * 入职时间 
     */
    private Date entryTime;
    /**
     * 每月收入 
     */
    private Integer income;
    /**
     * 紧急联系人 
     */
    private String emergencyContact;
    /**
     * 紧急联系人电话 
     */
    private String emergencyContactPhone;
    /**
     * 手持身份证照片 
     */
    private String idcardImage1;
    /**
     * 身份证正面照片 
     */
    private String idcardImage2;
    /**
     * 身份证反面照片 
     */
    private String idcardImage3;
    /**
     * 户籍城市 
     */
    private String nativeCity;
    /**
     * 户籍地址 
     */
    private String nativePlace;
    /**
     * 子女数目 
     */
    private Integer childTotal;
    /**
     * 现住城市 
     */
    private String currentCity;
    /**
     * 现住地址 
     */
    private String currentAdd;
    /**
     * 单位所属行业(行业类别) 
     */
    private String industry;
    /**
     * 单位性质 
     */
    private String companyType;
    /**
     * 职务 
     */
    private String occupation;
    /**
     * 职业 
     */
    private String position;
    /**
     * 部门 
     */
    private String department;
    /**
     * 毕业年份 
     */
    private Date graduateYear;
    /**
     * 家庭收入 
     */
    private Integer familymonthIncome;
    /**
     * 配偶名称 
     */
    private String spouseName;
    /**
     * 配偶电话 
     */
    private String spouseTel;
    /**
     * 亲属姓名 
     */
    private String kinshipName;
    /**
     * 亲属电话 
     */
    private String kinshipTel;
    /**
     * 贷款次数 
     */
    private Integer loanTimes;
    /**
     * 学信网密码 
     */
    private String xuexinPassword;
    /**
     * 上次贷款时间 
     */
    private Date lastloanDate;
    /**
     * 百融生成的唯一标识 
     */
    private String afSwiftNumber;
    /**
     * 信息录入时间 
     */
    private Date inputDate;
    /**
     * 连连签约编号 
     */
    private String noAgree;
    /**
     * qq号 
     */
    private String qqNum;
    /**
     * companyAreaCode 
     */
    private String companyAreaCode;
    /**
     * level 
     */
    private String level;
    
    private String loanRecordId;
    
    private Date applyTime;
    
    private BigDecimal applyAmount;
    
    private Integer approvalResult;
    
    private String ip;
    
    /**
     * 属于哪个历史用户表（user_histroy） 
     */
    private Long historyId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public String getPhoneNum() {
        return this.phoneNum;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public String getAvatar() {
        return this.avatar;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getRealName() {
        return this.realName;
    }
    
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    
    public String getIdNum() {
        return this.idNum;
    }
    
    public void setDebitCard(String debitCard) {
        this.debitCard = debitCard;
    }
    
    public String getDebitCard() {
        return this.debitCard;
    }
    
    public void setReservedPhoneNum(String reservedPhoneNum) {
        this.reservedPhoneNum = reservedPhoneNum;
    }
    
    public String getReservedPhoneNum() {
        return this.reservedPhoneNum;
    }
    
    public void setEducation(Integer education) {
        this.education = education;
    }
    
    public Integer getEducation() {
        return this.education;
    }
    
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    public Integer getMaritalStatus() {
        return this.maritalStatus;
    }
    
    public void setCardIssuingBank(String cardIssuingBank) {
        this.cardIssuingBank = cardIssuingBank;
    }
    
    public String getCardIssuingBank() {
        return this.cardIssuingBank;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyCellNum(String companyCellNum) {
        this.companyCellNum = companyCellNum;
    }
    
    public String getCompanyCellNum() {
        return this.companyCellNum;
    }
    
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    
    public String getCompanyAddress() {
        return this.companyAddress;
    }
    
    public void setCompanyDetailedAddress(String companyDetailedAddress) {
        this.companyDetailedAddress = companyDetailedAddress;
    }
    
    public String getCompanyDetailedAddress() {
        return this.companyDetailedAddress;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
    
    public Date getEntryTime() {
        return this.entryTime;
    }
    
    public void setIncome(Integer income) {
        this.income = income;
    }
    
    public Integer getIncome() {
        return this.income;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public String getEmergencyContact() {
        return this.emergencyContact;
    }
    
    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }
    
    public String getEmergencyContactPhone() {
        return this.emergencyContactPhone;
    }
    
    public void setIdcardImage1(String idcardImage1) {
        this.idcardImage1 = idcardImage1;
    }
    
    public String getIdcardImage1() {
        return this.idcardImage1;
    }
    
    public void setIdcardImage2(String idcardImage2) {
        this.idcardImage2 = idcardImage2;
    }
    
    public String getIdcardImage2() {
        return this.idcardImage2;
    }
    
    public void setIdcardImage3(String idcardImage3) {
        this.idcardImage3 = idcardImage3;
    }
    
    public String getIdcardImage3() {
        return this.idcardImage3;
    }
    
    public void setNativeCity(String nativeCity) {
        this.nativeCity = nativeCity;
    }
    
    public String getNativeCity() {
        return this.nativeCity;
    }
    
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
    
    public String getNativePlace() {
        return this.nativePlace;
    }
    
    public void setChildTotal(Integer childTotal) {
        this.childTotal = childTotal;
    }
    
    public Integer getChildTotal() {
        return this.childTotal;
    }
    
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
    
    public String getCurrentCity() {
        return this.currentCity;
    }
    
    public void setCurrentAdd(String currentAdd) {
        this.currentAdd = currentAdd;
    }
    
    public String getCurrentAdd() {
        return this.currentAdd;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public String getIndustry() {
        return this.industry;
    }
    
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
    
    public String getCompanyType() {
        return this.companyType;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getOccupation() {
        return this.occupation;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getPosition() {
        return this.position;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getDepartment() {
        return this.department;
    }
    
    public void setGraduateYear(Date graduateYear) {
        this.graduateYear = graduateYear;
    }
    
    public Date getGraduateYear() {
        return this.graduateYear;
    }
    
    public void setFamilymonthIncome(Integer familymonthIncome) {
        this.familymonthIncome = familymonthIncome;
    }
    
    public Integer getFamilymonthIncome() {
        return this.familymonthIncome;
    }
    
    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }
    
    public String getSpouseName() {
        return this.spouseName;
    }
    
    public void setSpouseTel(String spouseTel) {
        this.spouseTel = spouseTel;
    }
    
    public String getSpouseTel() {
        return this.spouseTel;
    }
    
    public void setKinshipName(String kinshipName) {
        this.kinshipName = kinshipName;
    }
    
    public String getKinshipName() {
        return this.kinshipName;
    }
    
    public void setKinshipTel(String kinshipTel) {
        this.kinshipTel = kinshipTel;
    }
    
    public String getKinshipTel() {
        return this.kinshipTel;
    }
    
    public void setLoanTimes(Integer loanTimes) {
        this.loanTimes = loanTimes;
    }
    
    public Integer getLoanTimes() {
        return this.loanTimes;
    }
    
    public void setXuexinPassword(String xuexinPassword) {
        this.xuexinPassword = xuexinPassword;
    }
    
    public String getXuexinPassword() {
        return this.xuexinPassword;
    }
    
    public void setLastloanDate(Date lastloanDate) {
        this.lastloanDate = lastloanDate;
    }
    
    public Date getLastloanDate() {
        return this.lastloanDate;
    }
    
    public void setAfSwiftNumber(String afSwiftNumber) {
        this.afSwiftNumber = afSwiftNumber;
    }
    
    public String getAfSwiftNumber() {
        return this.afSwiftNumber;
    }
    
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }
    
    public Date getInputDate() {
        return this.inputDate;
    }
    
    public void setNoAgree(String noAgree) {
        this.noAgree = noAgree;
    }
    
    public String getNoAgree() {
        return this.noAgree;
    }
    
    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }
    
    public String getQqNum() {
        return this.qqNum;
    }
    
    public void setCompanyAreaCode(String companyAreaCode) {
        this.companyAreaCode = companyAreaCode;
    }
    
    public String getCompanyAreaCode() {
        return this.companyAreaCode;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public String getLevel() {
        return this.level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getLoanRecordId() {
        return loanRecordId;
    }

    
    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    
    public Date getApplyTime() {
        return applyTime;
    }

    
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    
    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    
    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    
    public Integer getApprovalResult() {
        return approvalResult;
    }

    
    public void setApprovalResult(Integer approvalResult) {
        this.approvalResult = approvalResult;
    }

    
    public String getIp() {
        return ip;
    }

    
    public void setIp(String ip) {
        this.ip = ip;
    }

    
    public Long getHistoryId() {
        return historyId;
    }

    
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
    
}
