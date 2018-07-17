package org.gz.risk.auditing.entity;

import java.util.Date;

public class AppUser {
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
    /**年龄*/
    private Integer age;
    /**性别*/
    private Integer gender;
    /**出生日期*/
    private Date birthday;
    /**职业*/
    private Integer job;
    /**行业*/
    private Integer industry;
    /**月收入*/
    private Integer monthIncome;
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
    /**支付宝登录user_id*/
    private String authAlipayUserId;
    /**微信登录open_id*/
    private String authWeixinOpenId;
    /**用户创建时间*/
    private Date createTime;
    /**用户更新时间*/
    private Date updateTime;
    
    /**个人地址-省*/
    private String addrProvince;
    /**个人地址-市*/
    private String addrCity;
    /**个人地址-详细*/
    private String addrDetail;
    /**用户来源*/
    private String channelType;
    
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
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public Integer getZhimaScore() {
		return zhimaScore;
	}

	public void setZhimaScore(Integer zhimaScore) {
		this.zhimaScore = zhimaScore;
	}

	public String getCardFaceUrl() {
        return cardFaceUrl;
    }

    public void setCardFaceUrl(String cardFaceUrl) {
        this.cardFaceUrl = cardFaceUrl == null ? null : cardFaceUrl.trim();
    }

    public String getCardBackUrl() {
        return cardBackUrl;
    }

    public void setCardBackUrl(String cardBackUrl) {
        this.cardBackUrl = cardBackUrl == null ? null : cardBackUrl.trim();
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
        this.residenceAddress = residenceAddress == null ? null : residenceAddress.trim();
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority == null ? null : issuingAuthority.trim();
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

	public String getAuthAlipayUserId() {
		return authAlipayUserId;
	}

	public void setAuthAlipayUserId(String authAlipayUserId) {
		this.authAlipayUserId = authAlipayUserId;
	}

	public String getAuthWeixinOpenId() {
		return authWeixinOpenId;
	}

	public void setAuthWeixinOpenId(String authWeixinOpenId) {
		this.authWeixinOpenId = authWeixinOpenId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public Integer getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(Integer monthIncome) {
		this.monthIncome = monthIncome;
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

}