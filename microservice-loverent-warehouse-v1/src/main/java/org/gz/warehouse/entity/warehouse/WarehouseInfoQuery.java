package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * @Description:TODO    仓库分页查询
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月16日 	Created
 */
public class WarehouseInfoQuery extends QueryPager {

	private static final long serialVersionUID = 1871098611730395523L;
	private Integer id;
	private String warehouseCode;
	private String warehouseName;
	private Long parentId;
	public WarehouseInfoQuery(){
	}
    public WarehouseInfoQuery(WarehouseInfo m){
          this.id = m.getId();
          this.warehouseCode = m.getWarehouseCode();
          this.warehouseName = m.getWarehouseName();
          this.parentId = m.getParentId();
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getWarehouseCode() {
        return warehouseCode;
    }
    
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
}