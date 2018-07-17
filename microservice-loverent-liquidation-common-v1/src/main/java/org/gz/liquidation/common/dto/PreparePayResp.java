package org.gz.liquidation.common.dto;
/**
 * 
 * @Description:TODO	预支付订单号返回DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月29日 	Created
 */
public class PreparePayResp {
	private String transactionSN;
	private String timestamp;
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
