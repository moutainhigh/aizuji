package org.gz.liquidation.common.dto.repayment;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:芝麻订单还款详情
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月3日 	Created
 */
@Setter @Getter
public class ZmStatementDetailResp {
	/**
	 * 应还总租金
	 */
	private BigDecimal totalRent;
	/**
	 * 已还租金
	 */
	private BigDecimal totalRepayRent;
	/**
	 * 待还租金
	 */
	private BigDecimal totalRentPayable;
}
