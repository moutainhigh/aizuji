/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 仓位数量
 * @author hxj
 * @Description: 
 * @date 2017年12月22日 下午3:26:19
 */
public class WarehouseLocationMaterielDetailVO extends BaseEntity {

	private static final long serialVersionUID = -5106664755174374367L;

	private Integer warehouseLocationId;
	
	private String locationName;
	
	private Integer commodityCount;

	public Integer getWarehouseLocationId() {
		return warehouseLocationId;
	}

	public void setWarehouseLocationId(Integer warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getCommodityCount() {
		return commodityCount;
	}

	public void setCommodityCount(Integer commodityCount) {
		this.commodityCount = commodityCount;
	}
	
	
}
