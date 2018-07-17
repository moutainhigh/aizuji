package org.gz.liquidation.common.dto;

import java.math.BigDecimal;

import org.gz.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:售卖订单支付详情
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月29日 	Created
 */
@Setter @Getter
public class SaleOrderRepaymentInfoResp extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 售价
	 */
	private  BigDecimal salePrice;
	/**
	 * 保险费 (碎屏险)
	 */
	private  BigDecimal insurance;
}
