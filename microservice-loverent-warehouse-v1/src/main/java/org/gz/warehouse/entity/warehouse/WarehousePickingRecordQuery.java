/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月29日 上午9:51:55
 */
public class WarehousePickingRecordQuery extends QueryPager {

	private static final long serialVersionUID = -4557417882850557988L;

	private Long id;

	private String sourceOrderNo;// 销售单号

	private String applyStartTime;// 申请开始日期

	private String applyEndTime;// 申请开始日期

	private Long materielBasicId;// 物料ID

	private String batchNo;// 商品批次号

	private String snNo;// 商品sn号

	private String imieNo;// IMIE号

	private String logisticsNo;// 物流单号

	private String fillReceiptName;// 填单人姓名

	private String fillReceiptStartDate;// 填单开始时间

	private String fillReceiptEndDate;// 填单开始时间
	
	private String operatorName = "管理员";// 操作人姓名

	private String pickStartDate;// 操作时间/拣货开始时间

	private String pickEndDate;// 操作时间/拣货结束时间

	private Integer statusFlag;// 8:待发货 9:已出库
	
	private Long productId;// 产品ID
	
	private Integer productType;//产品类型

	private Integer orderSource;//订单来源 1：APP,2:小程序

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public String getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(String applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public String getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getImieNo() {
		return imieNo;
	}

	public void setImieNo(String imieNo) {
		this.imieNo = imieNo;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getFillReceiptName() {
		return fillReceiptName;
	}

	public void setFillReceiptName(String fillReceiptName) {
		this.fillReceiptName = fillReceiptName;
	}

	public String getFillReceiptStartDate() {
		return fillReceiptStartDate;
	}

	public void setFillReceiptStartDate(String fillReceiptStartDate) {
		this.fillReceiptStartDate = fillReceiptStartDate;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getPickStartDate() {
		return pickStartDate;
	}

	public void setPickStartDate(String pickStartDate) {
		this.pickStartDate = pickStartDate;
	}

	public String getPickEndDate() {
		return pickEndDate;
	}

	public void setPickEndDate(String pickEndDate) {
		this.pickEndDate = pickEndDate;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getFillReceiptEndDate() {
		return fillReceiptEndDate;
	}

	public void setFillReceiptEndDate(String fillReceiptEndDate) {
		this.fillReceiptEndDate = fillReceiptEndDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}
}
