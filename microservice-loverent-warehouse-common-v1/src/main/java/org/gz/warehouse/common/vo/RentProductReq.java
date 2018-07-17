/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.QueryPager;

import javax.validation.constraints.NotNull;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月28日 上午11:08:58
 */
public class RentProductReq extends QueryPager {

	private static final long serialVersionUID = 449277951020471972L;

	/**
     * 物料分类id
     */
	@NotNull(message = "物料分类ID不能为空")
    private Integer materielClassId;

    /**
     * 物料品牌id
     */
    private Integer materielBrandId;

    /**
     * 物料型号id
     */
    private Integer materielModelId;

    /**
     * 新旧程度配置Id
     */
    private Integer materielNewConfigId;
    /**
     * 新旧程度配置Id
     */
    private Integer warehouseLocationId;

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public Integer getWarehouseLocationId() {
		return warehouseLocationId;
	}

	public void setWarehouseLocationId(Integer warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
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


	public Integer getMaterielNewConfigId() {
		return materielNewConfigId;
	}

	public void setMaterielNewConfigId(Integer materielNewConfigId) {
		this.materielNewConfigId = materielNewConfigId;
	}


}
