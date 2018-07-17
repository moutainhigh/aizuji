package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * 
 * @Title: 库位查询实体
 * @Description: 
 * @date 2017年12月21日 下午3:24:57
 */
public class StorageLocationQuery extends QueryPager {

	private static final long serialVersionUID = 1871098611730395523L;
	
	private Integer id;
	
	private String locationCode;// 库位编码

	private String locationName;// 库位名称
	
	private Integer enableFlag;//启用标志 0:禁用，1：启用
	
	private Long purchaseApplyOrderId;
	
	public StorageLocationQuery(){
	}
    public StorageLocationQuery(StorageLocation m){
          this.id = m.getId();
          this.locationCode = m.getLocationCode();
          this.locationName = m.getLocationName();
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Integer getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}
	public Long getPurchaseApplyOrderId() {
		return purchaseApplyOrderId;
	}
	public void setPurchaseApplyOrderId(Long purchaseApplyOrderId) {
		this.purchaseApplyOrderId = purchaseApplyOrderId;
	}
   
}