package org.gz.liquidation.common.dto;

import java.math.BigDecimal;

import org.gz.common.entity.BaseEntity;
/**
 * 
 * @Description:TODO	收入统计DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月1日 	Created
 */
public class RevenueStatisticsResp extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3631404537386713601L;
	/**
	 * 租金
	 */
	private BigDecimal rent;
	 /**
     * 滞纳金
     */
    private BigDecimal lateFees;
    /**
     * 买断金
     */
    private BigDecimal buyoutAmount;
    /**
     * 折旧费
     */
    private BigDecimal depreciationExpense;
    /**
     * 销售金
     */
    private BigDecimal saleAmount;
    
	public BigDecimal getRent() {
		return rent;
	}
	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}
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
	public BigDecimal getDepreciationExpense() {
		return depreciationExpense;
	}
	public void setDepreciationExpense(BigDecimal depreciationExpense) {
		this.depreciationExpense = depreciationExpense;
	}
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
    
}
