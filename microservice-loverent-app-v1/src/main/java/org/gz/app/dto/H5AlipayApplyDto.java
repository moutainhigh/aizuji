package org.gz.app.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class H5AlipayApplyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message="订单编号不能为空")
	@NotEmpty(message="订单编号不能为空")
	private String order_no;
	
	@NotNull(message="支付金额不能为空")
	private String total_amount;
	
	@NotNull(message="支付类型不能为空")
	private String transactionType;
	
	@NotNull(message="sourceType不能为空")
	private String sourceType;
	
	@NotNull(message="交易入口不能为空")
	private String transactionSource;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}
	
	
}
