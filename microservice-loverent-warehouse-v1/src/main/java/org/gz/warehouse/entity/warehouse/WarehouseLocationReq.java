package org.gz.warehouse.entity.warehouse;

/**
 * 
 * @Description:TODO    配置库位请求参数
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月18日 	Created
 */
public class WarehouseLocationReq {
    private Integer warehouseId;
    /**
     * 逗号分隔库位id（1,2,3） 
     */
    private String locationIds;
    
    public Integer getWarehouseId() {
        return warehouseId;
    }
    
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    public String getLocationIds() {
        return locationIds;
    }
    
    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }
    
}
