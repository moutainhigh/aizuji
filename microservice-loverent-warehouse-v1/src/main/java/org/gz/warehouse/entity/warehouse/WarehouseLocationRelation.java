package org.gz.warehouse.entity.warehouse;

/**
 * 
 * @Description:TODO    仓库库位关联表实体
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月16日 	Created
 */
public class WarehouseLocationRelation {
    /**
     * 仓库id
     */
    private Integer warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 库位id
     */
    private Integer locationId;
    /**
     * 库位名称
     */
    private String locationName;
    
    public WarehouseLocationRelation(){
    
    }
    
    public WarehouseLocationRelation(Integer warehouseId, Integer locationId){
        this.warehouseId = warehouseId;
        this.locationId = locationId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }
    
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    public Integer getLocationId() {
        return locationId;
    }
    
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
    
}
