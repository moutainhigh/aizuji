package org.gz.aliOrder.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:信用支付请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月31日 	Created
 */
@Setter @Getter
public class ZhimaOrderCreditPayReq {
	/**
	 * 芝麻订单号
	 */
	@NotEmpty(message="芝麻订单号不能为空")
	private String zmOrderNo;
	/**
	 * 订单号
	 */
	@NotEmpty(message="订单号不能为空")
	private String orderSN;
	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;
	/**
	 * 订单完结类型: 取消-->CANCEL 分期扣款-->INSTALLMENT 完结-->FINISH
	 */
	@NotEmpty(message="订单完结类型不能为空")
	private String orderOperateType;
	/**
	 * 姓名
	 */
	@NotEmpty(message="姓名不能为空")
	private String realName;
	/**
	 * 手机号
	 */
	@NotEmpty(message="手机号不能为空")
	private String phone;
	/**
	 * 用户id
	 */
	@NotEmpty(message="用户芝麻:userId不能为空")
	private String userId;
	/**
	 * 流水号
	 */
	private String outTransNo;
	/**
	 * 备注
	 */
	private String remark;
}
