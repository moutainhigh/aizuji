package org.gz.liquidation.common.dto.alipay;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:芝麻信用支付异步通知DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月31日 	Created
 */
@Setter @Getter
public class ZhimaOrderCreditPayResponse {
	/**
	 * 通知类型 (订单完结=FINISH 分期扣款=INSTALLMENT)
	 */
	@ApiModelProperty(value = "通知类型 FINISH 或者 INSTALLMENT", required = true)
	@NotEmpty(message="通知类型不能为空")
	private String notifyType;
	/**
	 * 支付宝资金流水号，用于商户与支付宝进行对账
	 */
	@ApiModelProperty(value = "支付宝资金流水号", required = true)
	@NotEmpty(message="支付宝资金流水号不能为空")
	private String alipayFundOrderNo;
	/**
	 * 商户端订单号
	 */
	@ApiModelProperty(value = "订单号", required = true)
	@NotEmpty(message="订单号不能为空")
	private String outOrderNo;
	/**
	 * 商户传入的资金交易号
	 */
	@ApiModelProperty(value = "资金交易号", required = true)
	@NotEmpty(message="资金交易号不能为空")
	private String outTransNo;
	/**
	 * 本次处理支付金额
	 */
	@ApiModelProperty(value = "本次处理支付金额", required = true)
	@NotEmpty(message="本次处理支付金额不能为空")
	private String payAmount;
	/**
	 * 支付结果状态，包括：成功 (PAY_SUCCESS)
	 */
	@ApiModelProperty(value = "支付结果状态", required = true)
	@NotEmpty(message="支付结果状态不能为空")
	private String payStatus;
	/**
	 * 订单完结时间
	 */
	@ApiModelProperty(value = "订单完结时间")
	private Date payTime;
	/**
	 * 芝麻订单号，最长32位
	 */
	@ApiModelProperty(value = "芝麻订单号", required = true)
	@NotEmpty(message="芝麻订单号不能为空")
	private String zmOrderNo;
	/**
	 * 接口名称
	 */
	@ApiModelProperty(value = "接口名称", required = true)
	@NotEmpty(message="接口名称不能为空")
	private String apiName;
	/**
	 * 订单来源
	 */
	@ApiModelProperty(value = "订单来源")
	private String channel;
	/**
	 * 通知目标 APP_ID
	 */
	@ApiModelProperty(value = "通知目标 APP_ID", required = true)
	@NotEmpty(message="通知目标 APP_ID不能为空")
	private String notifyAppId;
}
