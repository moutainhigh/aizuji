package org.gz.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SaleOrderSubmitDto {

	@NotEmpty(message="收获地址【城市】不能为空")
	private String addrProvince;

	@NotEmpty(message="收获地址【市】不能为空")
	private String addrCity;
	
	@NotEmpty(message="收获地址【区域】不能为空")
	private String addrArea;
	
	@NotEmpty(message="收获地址【详细地址】不能为空")
	private String addrDetail;
	
	private String deviceId;
	
	@NotNull(message="是否开发票不能为null")
	private Integer applyInvoice;
	
	/**优惠券id, 多个以逗号隔开*/
	private String couponId;
	
	/**优惠券金额*/
	private String couponAmount;
	
	private String invoiceContent;
	
	private String invoiceFee;
	
	private String invoiceNumber;
	
	private String invoiceTitle;
	
	private Integer titleType;
	
	@NotEmpty(message="【手机sn编码】不能为空")
	private String snCode;
	
	@NotEmpty(message="【手机imei】不能为空")
	private String imei;
	
	@NotEmpty(message="【产品编号】不能为空")
	private String productNo;
	
	/**收货人手机号*/
	private String receiverUserName;
	
	/**收货人联系方式*/
	private String receiverPhoneNum;

	/**是否购买碎屏险 0否 1是*/
	private Integer brokenScreenBuyed;
	
	private String channelNo;
	
	private String channelType;
	
	public String getAddrProvince() {
		return addrProvince;
	}

	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrArea() {
		return addrArea;
	}

	public void setAddrArea(String addrArea) {
		this.addrArea = addrArea;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getApplyInvoice() {
		return applyInvoice;
	}

	public void setApplyInvoice(Integer applyInvoice) {
		this.applyInvoice = applyInvoice;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getInvoiceFee() {
		return invoiceFee;
	}

	public void setInvoiceFee(String invoiceFee) {
		this.invoiceFee = invoiceFee;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Integer getTitleType() {
		return titleType;
	}

	public void setTitleType(Integer titleType) {
		this.titleType = titleType;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String getReceiverPhoneNum() {
		return receiverPhoneNum;
	}

	public void setReceiverPhoneNum(String receiverPhoneNum) {
		this.receiverPhoneNum = receiverPhoneNum;
	}

	public Integer getBrokenScreenBuyed() {
		return brokenScreenBuyed;
	}

	public void setBrokenScreenBuyed(Integer brokenScreenBuyed) {
		this.brokenScreenBuyed = brokenScreenBuyed;
	}

	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
}
