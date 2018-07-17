package org.gz.risk.common.request;

import java.io.Serializable;

public class RiskReq implements Serializable {
	private static final long serialVersionUID = 2708680782712837058L;
	/*
	 * 订单号
	 */
	private String loanRecordId;
	/**
	 * {@link CreditNode}
	 * 审批阶段，目前只支持1审和3审
	 */
	private String phase;

	public String getLoanRecordId() {
		return loanRecordId;
	}

	public void setLoanRecordId(String loanRecordId) {
		this.loanRecordId = loanRecordId;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

}
