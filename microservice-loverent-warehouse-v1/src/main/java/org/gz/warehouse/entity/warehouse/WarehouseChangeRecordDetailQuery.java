/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * @Title: 库存变化明细查询实体
 * @author hxj
 * @Description:
 * @date 2017年12月23日 上午8:50:14
 */
public class WarehouseChangeRecordDetailQuery extends QueryPager {

	private static final long serialVersionUID = -2666381754098011153L;

	private Long materielBasicId;// 物料ID

	private String storageBatchNo;// 入库批次号

	private String batchNo;// 商品批次号

	private String snNo;// 商品SN

	private String imieNo;// 商品IMIE
	
	private Integer warehouseId;
	
	private Integer warehouseLocationId;

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
}
