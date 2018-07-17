package org.gz.warehouse.entity.warehouse;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存商品跟踪实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:00:18
 */
public class WarehouseCommodityTrackVO extends BaseEntity {

	private static final long serialVersionUID = 2896739284706794277L;

	private Long id;// 主键

	private Integer warehouseId;// 商品所在仓库ID
	
	private String warehouseName;
	
	private Integer warehouseLocationId;// 商品所在库位ID
	
	private String locationName;
	
	private String ownLocation;//所在位置=warehouseName-locationName

	private Long materielBasicId;// 物料基础信息ID

	private String materielName;// 物料名称
	
	private String materielCode;//物料编码

	private String storageBatchNo;// 入库批次号

	private String adjustBatchNo;// 调整批次号

	private Integer adjustType;// 调整类型 1:采购 2:销售 3：人工

	private String adjustReason;// 调整原因

	private String sourceOrderNo;// 源单号:其值为：采购单号或销售单号

	private String batchNo;

	private String snNo;

	private String imieNo;

	private Long operatorId;

	private String operatorName;// 操作人名称

	private Date operateOn;// 创建时间/操作时间

	private String operateOn_s;

	public WarehouseCommodityTrackVO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getWarehouseLocationId() {
		return warehouseLocationId;
	}

	public void setWarehouseLocationId(Integer warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getStorageBatchNo() {
		return storageBatchNo;
	}

	public void setStorageBatchNo(String storageBatchNo) {
		this.storageBatchNo = storageBatchNo;
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

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateOn() {
		return operateOn;
	}

	public void setOperateOn(Date operateOn) {
		this.operateOn = operateOn;
	}

	public String getAdjustBatchNo() {
		return adjustBatchNo;
	}

	public void setAdjustBatchNo(String adjustBatchNo) {
		this.adjustBatchNo = adjustBatchNo;
	}

	public Integer getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(Integer adjustType) {
		this.adjustType = adjustType;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getOperateOn_s() {
		return operateOn_s;
	}

	public void setOperateOn_s(String operateOn_s) {
		this.operateOn_s = operateOn_s;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getOwnLocation() {
		return ownLocation;
	}

	public void setOwnLocation(String ownLocation) {
		this.ownLocation = ownLocation;
	}
}
