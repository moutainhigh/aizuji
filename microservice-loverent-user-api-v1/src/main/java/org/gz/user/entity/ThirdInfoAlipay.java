package org.gz.user.entity;

import java.util.Date;

public class ThirdInfoAlipay {
    private Long thirdPartInfoId;
    private Long userId;
    private String alipayUserId;
    private String nickName;
    private String avatar;
    /**M-男 f-女*/
    private String gender;
    /**是否通过实名认证*/
    private String isCertified;
    /**是否是学生*/
    private String isStudentCertified;
    /**省份名称*/
    private String province;
    /**城市名称*/
    private String city;
    private Date createTime;
    private Date updateTime;

	public Long getThirdPartInfoId() {
		return thirdPartInfoId;
	}

	public void setThirdPartInfoId(Long thirdPartInfoId) {
		this.thirdPartInfoId = thirdPartInfoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAlipayUserId() {
		return alipayUserId;
	}

	public void setAlipayUserId(String alipayUserId) {
		this.alipayUserId = alipayUserId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsCertified() {
		return isCertified;
	}

	public void setIsCertified(String isCertified) {
		this.isCertified = isCertified;
	}

	public String getIsStudentCertified() {
		return isStudentCertified;
	}

	public void setIsStudentCertified(String isStudentCertified) {
		this.isStudentCertified = isStudentCertified;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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