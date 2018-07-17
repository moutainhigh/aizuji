/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存调整申请清单
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:19:21
 */
public class WarehouseAdjustList extends BaseEntity {

	private static final long serialVersionUID = -7287225318819989213L;

	private Long id;

	private Long adjustBasicId;// 关联warehouse_adjust_basic表主键

	private Long materielBasicId;// 物料基础信息ID

	private Integer sourceWarehouseId = 0;// 源仓库ID

	private Integer sourceWarehouseLocationId = 0;// 源库位ID

	private Long sortOrder;// 排列顺序

	private Integer destWarehouseId = 0;// 目标仓库ID

	private Integer destWarehouseLocationId = 0;// 目标库位ID

	private Integer expectedQuantity;

	private Integer scanQuantity;// 扫描数量

	private String adjustReason;// 调整原因

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdjustBasicId() {
		return adjustBasicId;
	}

	public void setAdjustBasicId(Long adjustBasicId) {
		this.adjustBasicId = adjustBasicId;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public Integer getSourceWarehouseId() {
		return sourceWarehouseId;
	}

	public void setSourceWarehouseId(Integer sourceWarehouseId) {
		this.sourceWarehouseId = sourceWarehouseId;
	}

	public Integer getSourceWarehouseLocationId() {
		return sourceWarehouseLocationId;
	}

	public void setSourceWarehouseLocationId(Integer sourceWarehouseLocationId) {
		this.sourceWarehouseLocationId = sourceWarehouseLocationId;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getDestWarehouseId() {
		return destWarehouseId;
	}

	public void setDestWarehouseId(Integer destWarehouseId) {
		this.destWarehouseId = destWarehouseId;
	}

	public Integer getDestWarehouseLocationId() {
		return destWarehouseLocationId;
	}

	public void setDestWarehouseLocationId(Integer destWarehouseLocationId) {
		this.destWarehouseLocationId = destWarehouseLocationId;
	}

	public Integer getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(Integer expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public Integer getScanQuantity() {
		return scanQuantity;
	}

	public void setScanQuantity(Integer scanQuantity) {
		this.scanQuantity = scanQuantity;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

}
