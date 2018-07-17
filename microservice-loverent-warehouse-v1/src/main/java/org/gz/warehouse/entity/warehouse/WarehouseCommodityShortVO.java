/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存商品关联实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:00:18
 */
public class WarehouseCommodityShortVO extends BaseEntity {

	private static final long serialVersionUID = 2896739284706794277L;

	private Long id;
	
	private Integer warehouseId;
	
	private Integer warehouseLocationId;
	
	private Long materielBasicId;
	
	private String storageBatchNo;
	
	protected String batchNo;

	protected String snNo;

	protected String imieNo;

	public WarehouseCommodityShortVO() {

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

	
}
