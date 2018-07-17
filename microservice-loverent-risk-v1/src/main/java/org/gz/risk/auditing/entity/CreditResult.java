package org.gz.risk.auditing.entity;

import java.io.Serializable;

/**
 * @author JarkimZhu
 *         Created on 2017/2/7.
 * @since jdk1.8
 */
public class CreditResult implements Serializable{
    private String loanRecordId;
    private int result;
    private String hitRule;
    private Integer refuseCategory;
    
    

    public Integer getRefuseCategory() {
		return refuseCategory;
	}

	public void setRefuseCategory(Integer refuseCategory) {
		this.refuseCategory = refuseCategory;
	}

	public String getLoanRecordId() {
        return loanRecordId;
    }

    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getHitRule() {
        return hitRule;
    }

    public void setHitRule(String hitRule) {
        this.hitRule = hitRule;
    }
}
