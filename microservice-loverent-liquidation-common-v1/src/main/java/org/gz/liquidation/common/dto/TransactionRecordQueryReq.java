package org.gz.liquidation.common.dto;

import java.util.Date;
import java.util.List;

import org.gz.common.entity.QueryPager;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:TODO	交易流水请求参数
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月23日 	Created
 */
public class TransactionRecordQueryReq extends QueryPager {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4748900359307688026L;

	/**
     * 交易流水号
     */
    private String transactionSN;
    /**
     * 订单号
     */
    private String orderSN;

    /**
     * 来源类别
     */
    private String sourceType;

    /**
     * 交易方式
     */
    private String transactionWay;
    /**
     * 交易类型
     */
    @Setter @Getter
    private String transactionType;

    /**
     * 交易来源账户
     */
    private String fromAccount;

    /**
     * 交易状态
     */
    private String state;
    /**
     * 姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 交易时间
     */
    @Setter @Getter
    private Date startDate;
    @Setter @Getter
    private Date endDate;
    private List<String> orderSnList; 
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getTransactionWay() {
		return transactionWay;
	}
	public void setTransactionWay(String transactionWay) {
		this.transactionWay = transactionWay;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public List<String> getOrderSnList() {
		return orderSnList;
	}
	public void setOrderSnList(List<String> orderSnList) {
		this.orderSnList = orderSnList;
	}
    
}
