package org.gz.order.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 修改订单状态请求对象 Author Version Date Changes 临时工 1.0 2017年12月14日 Created
 */
public class UpdateOrderStateReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品类型 12租赁 以租代购 3出售
	 */
	private Integer productType;

	/**
	 * 租机编号
	 */
	private String rentRecordNo;
	/**
	 * 0提交 1 待审批 2 已拒绝 3 待支付 4 待签约 5 已取消 6 待配货 7 待拣货 8 待发货 9 发货中 10 正常履约（已经签收） 11
	 * 提前解约中 12 提前解约 13 换机订单状态 14 维修订单状态 15 已逾期 16 归还中 17 提前买断 18 提前归还（废弃） 19 正常买断
	 * 20 已归还 21提前买断中 22正常买断中 23提前归还中（废弃） 24履约完成 25 强制买断中 26 强制买断 27定损完成 28 强制履约中 29
	 * 强制履约完成 30 强制归还中 31 强制定损完成 32 强制归还完成 33退货中 34已退货
	 */
	private Integer state;

	/**
	 * 创建人ID
	 */
	private Long createBy;
	/**
	 * 创建人用户名
	 */
	private String createMan;
	/**
	 * 原因
	 */
	private String remark;

	/**
	 * 经度
	 */
	private String lng;
	/**
	 * 纬度
	 */
	private String lat;

	/**
	 * 签约盖章之后的合同地址
	 */
	private String sealAgreementUrl;
	/**
	 * 电子签章存根号
	 */
	private String evid;
	/**
	 * 电子签章传回的唯一id用于第三方上传合同失败记录
	 */
	private String signServiceId;

	/**
	 * 还机时填写的物流单号
	 */
	private String returnLogisticsNo;

	/**
	 * 手机型号
	 */
	private String phoneModel;
	/**
	 * 设备imei号
	 */
	private String imei;

	/**
	 * 设备sn号
	 */
	private String sn;

	/**
	 * 顺丰商户号
	 */
	private String businessNo;

	/**
	 * 折损费
	 */
	private BigDecimal discountFee;

	/**
	 * 发票金额
	 */
	private BigDecimal fee;
	/**
	 * 碎屏保障金额
	 */
	private BigDecimal feeTwo;

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRentRecordNo() {
		return rentRecordNo;
	}

	public void setRentRecordNo(String rentRecordNo) {
		this.rentRecordNo = rentRecordNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getSealAgreementUrl() {
		return sealAgreementUrl;
	}

	public void setSealAgreementUrl(String sealAgreementUrl) {
		this.sealAgreementUrl = sealAgreementUrl;
	}

	public String getEvid() {
		return evid;
	}

	public void setEvid(String evid) {
		this.evid = evid;
	}

	public String getSignServiceId() {
		return signServiceId;
	}

	public void setSignServiceId(String signServiceId) {
		this.signServiceId = signServiceId;
	}

	public String getReturnLogisticsNo() {
		return returnLogisticsNo;
	}

	public void setReturnLogisticsNo(String returnLogisticsNo) {
		this.returnLogisticsNo = returnLogisticsNo;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFeeTwo() {
		return feeTwo;
	}

	public void setFeeTwo(BigDecimal feeTwo) {
		this.feeTwo = feeTwo;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
