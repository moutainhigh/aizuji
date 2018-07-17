package org.gz.liquidation.common.dto;

import java.math.BigDecimal;

public class BuyoutResp {
	/**
	 * 滞纳金
	 */
	private BigDecimal lateFees;
	/**
	 * 买断金额
	 */
	private	BigDecimal buyoutAmount;
	public BigDecimal getLateFees() {
		return lateFees;
	}

	public void setLateFees(BigDecimal lateFees) {
		this.lateFees = lateFees;
	}

	public BigDecimal getBuyoutAmount() {
		return buyoutAmount;
	}

	public void setBuyoutAmount(BigDecimal buyoutAmount) {
		this.buyoutAmount = buyoutAmount;
	}

	
}
