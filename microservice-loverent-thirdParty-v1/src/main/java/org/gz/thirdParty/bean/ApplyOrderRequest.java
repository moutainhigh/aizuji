package org.gz.thirdParty.bean;


public class ApplyOrderRequest {
	/**
	 * 客户订单号
	 */
	private String orderId;
	/**
	 * 快件产品类别：
	 * 	类别 描述 
	 * 	1 标准快递
	 * 	2 顺丰特惠
	 * 	3 电商特惠
	 * 	5 顺丰次晨
	 * 	6 顺丰即日
	 * 	7 电商速配
	 * 	15 生鲜速配
	 */
	private short expressType;
	/**
	 * 付款方式：
	 *	类别 描述 
	 *	1 寄方付/寄付月结【默认值】
	 *	2 收方付
	 *	3 第三方付
	 */
	private short payMethod;
	/**
	 * 非必填
	 * 月结卡号对应的网点，如果付款方式为第三方支付，则必填
	 */
	private String payArea;
	/**
	 * 顺丰月结卡号 10 位数字
	 */
	private String custId;
	/**
	 * 要求上门取件开始时间 YYYY-MM-DD HH24:MM:SS
	 */
	private String sendStartTime;
	/**
	 * 是否需要签回单号 1需要 0不需要
	 */
	private short needReturnTrackingNo = 0;
	/**
	 * 备注，最大长度 30 个汉字
	 */
	private String remark;
	/**
	 * 是否下 call（通知收派员上门取件） 1 下call 0不下call
	 */
	private short isDoCall = 0;
	/**
	 * 是否申请运单号 1申请 0不申请
	 */
	private short isGenBillNo = 1;
	/**
	 * 是否生成电子运单图片 1生成 0不生成
	 */
	private short isGenEletricPic = 1;
	
	/**
	 * 收方公司名称
	 */
	private String company;
	/**
	 * 收方联系人
	 */
	private String contact;
	/**
	 * 收方联系电话
	 */
	private String tel;
	/**
	 * 收方手机号码
	 */
	private String mobile;
	/**
	 * 收方国家
	 */
	private String country = "中国";
	/**
	 * 收件人县/区
	 */
	private String county;
	/**
	 * 收件人省份
	 */
  	private String province;
  	/**
	 * 收件人城市
	 */
  	private String city;
  	/**
	 * 收件方详细地址
	 */
  	private String address;
  	/**
	 * 收件方邮政编码
	 */
  	private String shipperCode;
  	
  	/**
  	 * 货物名称
  	 */
  	private String cargo;

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public short getExpressType() {
		return expressType;
	}
	public void setExpressType(short expressType) {
		this.expressType = expressType;
	}
	public short getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(short payMethod) {
		this.payMethod = payMethod;
	}
	public String getPayArea() {
		return payArea;
	}
	public void setPayArea(String payArea) {
		this.payArea = payArea;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSendStartTime() {
		return sendStartTime;
	}
	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}
	public short getNeedReturnTrackingNo() {
		return needReturnTrackingNo;
	}
	public void setNeedReturnTrackingNo(short needReturnTrackingNo) {
		this.needReturnTrackingNo = needReturnTrackingNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public short getIsDoCall() {
		return isDoCall;
	}
	public void setIsDoCall(short isDoCall) {
		this.isDoCall = isDoCall;
	}
	public short getIsGenBillNo() {
		return isGenBillNo;
	}
	public void setIsGenBillNo(short isGenBillNo) {
		this.isGenBillNo = isGenBillNo;
	}
	public short getIsGenEletricPic() {
		return isGenEletricPic;
	}
	public void setIsGenEletricPic(short isGenEletricPic) {
		this.isGenEletricPic = isGenEletricPic;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShipperCode() {
		return shipperCode;
	}
	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}
