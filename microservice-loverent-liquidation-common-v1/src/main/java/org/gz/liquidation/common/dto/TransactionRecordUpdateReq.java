package org.gz.liquidation.common.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * @Description:TODO	APP更新状态请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月31日 	Created
 */
public class TransactionRecordUpdateReq {
	@ApiModelProperty(value = "交易流水号不能为空", required = true)
	@NotEmpty(message="transactionSN 交易流水号不能为空")
    private String transactionSN;
	@ApiModelProperty(value = "更新人账号不能为空", required = true)
	@NotEmpty(message="updateUsername 更新人账号不能为空")
	private String updateUsername;
	@ApiModelProperty(value = "状态不能为空", required = true)
	@Positive(message="state 状态不为0或者负数")
	@NotNull(message="state 状态不为空")
	private Integer state;
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	public String getUpdateUsername() {
		return updateUsername;
	}
	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
