package org.gz.liquidation.common.dto.repayment;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:	芝麻订单还款计划请求
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
@Setter @Getter
public class ZmRepaymentScheduleReq {
	/**
	 * 订单号
	 */
	@NotNull(message="订单号不能为空")
	private String orderSN;
	/**
	 * 芝麻订单号
	 */
	@NotNull(message="芝麻订单号不能为空")
	private String zmOrderNo;
	@NotNull(message="租金不能为空")
	private BigDecimal rent;
	/**
	 * 用户id
	 */
	@NotNull(message="用户id不能为空")
	@Min(value=1)
	private Long userId;
	/**
	 * 分期数
	 */
	@NotNull(message="分期数不能为空")
	private Integer periods;
    /**
     * 商品价值
     */
    private BigDecimal goodsValue;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 手机号
     */
    @NotNull(message="手机号不能为空")
    private String phone;
    /**
     * 订单创建日期
     */
    @NotNull(message="订单创建日期不能为空")
    private Date createDate;
}
