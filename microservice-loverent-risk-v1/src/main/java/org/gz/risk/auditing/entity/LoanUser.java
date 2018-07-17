package org.gz.risk.auditing.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gz.order.common.entity.UserHistory;

/**
 * @author JarkimZhu
 *         Created on 2017/2/20.
 * @since jdk1.8
 */
public class LoanUser  implements Serializable {
    private String loanRecordId;
    private Date applyTime;
    private BigDecimal applyAmount;
    private Integer approvalResult;
    private String ip;
    private UserHistory userHistory;

 



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
}
