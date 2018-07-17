package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 物流节点
 * @author phd
 */
public class LogisticsNodes  implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String acceptAddress;
    private Date acceptTime;
    private String addresseeCompName;
    private String consignorContName;
    private String mailNo;
    private String opcode;
    private String orderId;
    private String productCode;
    private String productName;
    private String remark;
	public String getAcceptAddress() {
		return acceptAddress;
	}
	public void setAcceptAddress(String acceptAddress) {
		this.acceptAddress = acceptAddress;
	}
	public Date getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getAddresseeCompName() {
		return addresseeCompName;
	}
	public void setAddresseeCompName(String addresseeCompName) {
		this.addresseeCompName = addresseeCompName;
	}
	public String getConsignorContName() {
		return consignorContName;
	}
	public void setConsignorContName(String consignorContName) {
		this.consignorContName = consignorContName;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
