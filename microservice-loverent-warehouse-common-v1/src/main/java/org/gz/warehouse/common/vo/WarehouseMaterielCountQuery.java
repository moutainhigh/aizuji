/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月26日 下午5:49:56
 */
public class WarehouseMaterielCountQuery extends BaseEntity{

	private static final long serialVersionUID = -8943769431078343897L;
	
	private Integer warehouseId;//仓库ID
	
	private String warehouseCode;//仓库编码
	
	private Integer warehouseLocationId;//仓位ID
	
	private String locationCode;//仓位编码
	
	private Long materielBasicId;// 物料基础信息ID

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
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
}
