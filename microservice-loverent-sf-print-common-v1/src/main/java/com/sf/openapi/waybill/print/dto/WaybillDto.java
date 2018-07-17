/**
 * 
 */
package com.sf.openapi.waybill.print.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WaybillDto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message="客户APPID不能为空")
	@NotBlank(message="客户APPID不能为空")
	@Pattern(regexp = "^[0-9]{8}$",message="非法客户APPID值")
	private String appId;// 客户APPID

	@NotNull(message="客户APPKEY不能为空")
	@NotBlank(message="客户APPKEY不能为空")
	@Pattern(regexp = "^[A-Z0-9]{32}$",message="非法客户APPKEY值")
	private String appKey;// 客户APPKEY

	@NotNull(message="顺丰运单号码不能为空")
	@NotBlank(message="顺丰运单号码不能为空")
	private String mailNo;// 顺丰运单号码

	@NotNull(message="订单号不能为空")
	@NotBlank(message="订单号不能为空")
	private String orderNo;// 订单号

	@NotNull(message="原寄地代码不能为空")
	@NotBlank(message="原寄地代码不能为空")
	private String zipCode;// 原寄地代码

	private String destCode;// 目的地代码

	private int expressType;// 1 ：标准快递 2.顺丰特惠 3： 电商特惠 5：顺丰次晨 6：顺丰即日 7.电商速配 15：生鲜速配

	private int payMethod;// 付款方式 1 寄付 2到付 3第三方月结卡号支付

	private String payArea;// 月结卡号对应的网点，如果付款方式为第三方月结卡号支付，则必填

	private String monthAccount;// 月结卡号

	private String returnTrackingNo;

	// 寄件人信息
	@NotNull(message="寄件人公司不能为空")
	@NotBlank(message="寄件人公司不能为空")
	private String deliverCompany;// 寄件人公司

	@NotNull(message="寄件人姓名不能为空")
	@NotBlank(message="寄件人姓名不能为空")
	private String deliverName;// 寄件人姓名

	private String deliverMobile;// 寄件人手机号码

	@NotNull(message="寄件人座机不能为空")
	@NotBlank(message="寄件人座机不能为空")
	private String deliverTel;// 寄件人座机

	@NotNull(message="寄件人所在省份不能为空")
	@NotBlank(message="寄件人所在省份不能为空")
	private String deliverProvince;// 寄件人所在省份

	@NotNull(message="寄件人所在城市不能为空")
	@NotBlank(message="寄件人所在城市不能为空")
	private String deliverCity;// 寄件人所在城市

	@NotNull(message="寄件人所在区不能为空")
	@NotBlank(message="寄件人所在区不能为空")
	private String deliverCounty;// 寄件人所在区

	@NotNull(message="寄件人详细地址不能为空")
	@NotBlank(message="寄件人详细地址不能为空")
	private String deliverAddress;// 寄件人详细地址

	private String deliverShipperCode;// 寄件人详细地址

	// 收件人信息
	private String consignerCompany;// 收件人公司

	@NotNull(message="收件人姓名不能为空")
	@NotBlank(message="收件人姓名不能为空")
	private String consignerName;// 收件人姓名

	@NotNull(message="收件人手机号不能为空")
	@NotBlank(message="收件人手机号不能为空")
	private String consignerMobile;// 收件人手机号

	private String consignerTel;// 收件人座机

	@NotNull(message="收件人所在省份不能为空")
	@NotBlank(message="收件人所在省份不能为空")
	private String consignerProvince;// 收件人所在省份

	@NotNull(message="收件人所在城市不能为空")
	@NotBlank(message="收件人所在城市不能为空")
	private String consignerCity;// 收件人所在城市

	@NotNull(message="收件人所在区不能为空")
	@NotBlank(message="收件人所在区不能为空")
	private String consignerCounty;// 收件人所在区

	@NotNull(message="收件人详细地址不能为空")
	@NotBlank(message="收件人详细地址不能为空")
	@Length(max = 35,message="收件人详细地址最大长度不超过35个字符")
	private String consignerAddress;// 收件人详细地址

	private String consignerShipperCode;// 收件人邮编

	private String logo;// 顺丰logo

	private String sftelLogo;// 顺丰95338热线logo

	private String electric;

	private String insureValue;// 保价金额--属于增值服务项 保价

	private String codValue;// 代收货款,单位/元(RMB) --属于增值服务项

	private List<CargoInfoDto> cargoInfoDtoList;
}
