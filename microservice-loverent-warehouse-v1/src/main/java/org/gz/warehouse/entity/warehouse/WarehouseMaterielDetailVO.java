/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.util.List;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月22日 下午3:21:46
 */
public class WarehouseMaterielDetailVO extends BaseEntity {

	private static final long serialVersionUID = 7669797871448685085L;

	private Integer warehouseId;// 仓库ID

	private String warehouseName;// 仓库名称

	private List<WarehouseLocationMaterielDetailVO> locationDetailList;// 仓库下每个仓位的数量

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public List<WarehouseLocationMaterielDetailVO> getLocationDetailList() {
		return locationDetailList;
	}

	public void setLocationDetailList(List<WarehouseLocationMaterielDetailVO> locationDetailList) {
		this.locationDetailList = locationDetailList;
	}

}
