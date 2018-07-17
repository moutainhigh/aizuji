package org.gz.liquidation.common.dto.alipay;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "芝麻订单号", required = true)
	@NotEmpty(message="芝麻订单号不能为空")
	private String zmOrderNo;
	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "不能为空", required = true)
	@NotEmpty(message="订单号不能为空")
	private String orderSN;
	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额", required = true)
	@Positive(message="支付金额:payAmount必须是正数")
	private BigDecimal payAmount;
	/**
	 * 订单完结类型: 取消-->CANCEL 分期扣款-->INSTALLMENT 完结-->FINISH
	 */
	@ApiModelProperty(value = "订单完结类型", required = true)
	@NotEmpty(message="订单完结类型不能为空")
	private String orderOperateType;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名", required = true)
	@NotEmpty(message="姓名不能为空")
	private String realName;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号", required = true)
	@NotEmpty(message="手机号不能为空")
	private String phone;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户芝麻userId", required = true)
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
	
	public ZhimaOrderCreditPayReq() {
		super();
	}

	public ZhimaOrderCreditPayReq(String zmOrderNo, String orderSN, BigDecimal payAmount, String orderOperateType,
			String outTransNo, String remark) {
		super();
		this.zmOrderNo = zmOrderNo;
		this.orderSN = orderSN;
		this.payAmount = payAmount;
		this.orderOperateType = orderOperateType;
		this.outTransNo = outTransNo;
		this.remark = remark;
	}
	
	
}
