/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月22日 上午11:08:53
 */
public class WarehouseRetrieveQuery extends QueryPager {

	private static final long serialVersionUID = 1134714831913031604L;

	private Integer warehouseId;
	
	private Long locationId;
	
	private Long materielBasicId;
	
	private Integer materielClassId;
	
	private String queryContent;//物料名称，物料编码模糊查询
	
	private String materielName;
	
	private String materielCode;
	
	private Integer materielBrandId;
	
	private Integer materielModelId;
	
	private String materielSpecBatchNo;
	
	private Integer materielEnableFlag;

	private Integer displyZero=0;//是否只显示0库存 0:否 1：是
	
	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	
	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

	
	public String getQueryContent() {
		return queryContent;
	}

	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public Integer getMaterielBrandId() {
		return materielBrandId;
	}

	public void setMaterielBrandId(Integer materielBrandId) {
		this.materielBrandId = materielBrandId;
	}

	public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}

	public String getMaterielSpecBatchNo() {
		return materielSpecBatchNo;
	}

	public void setMaterielSpecBatchNo(String materielSpecBatchNo) {
		this.materielSpecBatchNo = materielSpecBatchNo;
	}

	public Integer getMaterielEnableFlag() {
		return materielEnableFlag;
	}

	public void setMaterielEnableFlag(Integer materielEnableFlag) {
		this.materielEnableFlag = materielEnableFlag;
	}

	public Integer getDisplyZero() {
		return displyZero;
	}

	public void setDisplyZero(Integer displyZero) {
		this.displyZero = displyZero;
	}
}
