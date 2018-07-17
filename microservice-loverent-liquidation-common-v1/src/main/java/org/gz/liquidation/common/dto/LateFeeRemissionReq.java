package org.gz.liquidation.common.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 
 * @Description:TODO	滞纳金减免请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月5日 	Created
 */
public class LateFeeRemissionReq {
	/**
	 * 订单号
	 */
	@NotEmpty(message="订单号orderSN不能为空")
	private String orderSN;
	/**
	 * 创建人
	 */
	@Positive(message="创建人createBy必须是正数")
	private Long createBy;
	/**
	 * 创建人姓名
	 */
	private String createByRealname;
	/**
	 * 减免金额
	 */
	@Positive(message="减免金额RemissionAmount必须是正数")
	private BigDecimal remissionAmount;
	/**
	 * 减免原因描述
	 */
	@Size(max = 128,message = "减免原因描述不能超过128个字符")
	private String remissionDesc;
	
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getCreateByRealname() {
		return createByRealname;
	}
	public void setCreateByRealname(String createByRealname) {
		this.createByRealname = createByRealname;
	}
	public BigDecimal getRemissionAmount() {
		return remissionAmount;
	}
	public void setRemissionAmount(BigDecimal remissionAmount) {
		this.remissionAmount = remissionAmount;
	}
	public String getRemissionDesc() {
		return remissionDesc;
	}
	public void setRemissionDesc(String remissionDesc) {
		this.remissionDesc = remissionDesc;
	}
	
}
