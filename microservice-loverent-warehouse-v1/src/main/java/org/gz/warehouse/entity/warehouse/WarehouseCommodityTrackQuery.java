/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * @Title:库存商品关联实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:00:18
 */
public class WarehouseCommodityTrackQuery extends QueryPager {

	private static final long serialVersionUID = 2896739284706794277L;

	private Integer warehouseId;// 商品所在仓库ID

	private String warehouseCode;// 仓库编码;

	private Integer warehouseLocationId;// 商品所在库位ID

	private String locationCode;// 仓位编码

	private Long materielBasicId;// 物料基础信息ID

	private String adjustBatchNo;//调整批次号
	
	private String batchNo;
	
	private String snNo;
	
	private String imieNo;
	
	private String sourceOrderNo;
	
	
	public WarehouseCommodityTrackQuery() {

	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Integer getWarehouseLocationId() {
		return warehouseLocationId;
	}

	public void setWarehouseLocationId(Integer warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getAdjustBatchNo() {
		return adjustBatchNo;
	}

	public void setAdjustBatchNo(String adjustBatchNo) {
		this.adjustBatchNo = adjustBatchNo;
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

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}
}
