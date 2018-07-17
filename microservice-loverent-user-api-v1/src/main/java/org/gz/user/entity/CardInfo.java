package org.gz.user.entity;

import java.util.Date;

/**
 * 银行卡信息
 * @author yangdx
 *
 */
public class CardInfo {
	/**ID*/
    private Long cardId;
    /**用户ID*/
    private Long userId;
    /**银行卡号*/
    private String cardNo;
    /**银行卡编号*/
    private String cardCode;
    /**发卡行*/
    private String cardIssuing;
    /**预留手机号*/
    private String reservedPhoneNum;
    /**签约编号-第三方*/
    private String noAgree;
    /**1-正常2-停用*/
    private Integer cardStatus;
    /**户名*/
    private String accountName;
    /**身份证*/
    private String idNo;
    
    private Date createTime;

    private Date updateTime;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardIssuing() {
		return cardIssuing;
	}

	public void setCardIssuing(String cardIssuing) {
		this.cardIssuing = cardIssuing;
	}

	public String getReservedPhoneNum() {
		return reservedPhoneNum;
	}

	public void setReservedPhoneNum(String reservedPhoneNum) {
		this.reservedPhoneNum = reservedPhoneNum;
	}

	public String getNoAgree() {
		return noAgree;
	}

	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

}