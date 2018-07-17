package org.gz.risk.auditing.entity;

import java.io.Serializable;

import org.gz.order.common.entity.UserHistory;
import org.gz.user.entity.AppUser;

/**
 * @author JarkimZhu Created on 2016/11/10.
 * @since jdk1.8
 */
public class User implements Serializable {
	private String loanRecordId;
	private String channelNo;
	/**
	 * 用户ID
	 */
	private Long userId;
	private AppUser appUser;
	private UserHistory userHistory;
	private String emergencyContact;
	private String emergencyContactPhone;
	
	
	
	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public AppUser getAppUser() {
		return appUser;
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

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public UserHistory getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(UserHistory userHistory) {
		this.userHistory = userHistory;
	}

	public String getLoanRecordId() {
		return loanRecordId;
	}

	public void setLoanRecordId(String loanRecordId) {
		this.loanRecordId = loanRecordId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
