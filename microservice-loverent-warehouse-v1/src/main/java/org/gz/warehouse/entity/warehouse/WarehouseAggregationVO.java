/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.math.BigDecimal;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存汇总实体
 * @author hxj
 * @Description:
 * @date 2017年12月22日 上午11:10:38
 */
public class WarehouseAggregationVO extends BaseEntity {

	private static final long serialVersionUID = -7963186614515222908L;

	private Integer materielBasicId;// 物料基础信息ID

	private Integer materielClassId;// 物料分类ID

	private String materielClassName;// 物料分类名称

	private String materielName;// 物料名称

	private String materielCode;// 物料编码

	private BigDecimal materielCostPrice;// 物料成本价

	private Integer materielBrandId;// 品牌ID

	private String materielBrandName;// 品牌名称

	private Integer materielModelId;// 型号ID

	private String materielModelName;// 型号名称

	private String materielSpecValues;// 物料规格

	private Integer materielEnableFlag;// 物料启用标志

	private String materielEnableFlag_s;// 物料启用标志说明

	private String materielUnitName;// 物料单位名称

	private Integer storageCount;// 库存数量

	public Integer getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Integer materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

	public String getMaterielClassName() {
		return materielClassName;
	}

	public void setMaterielClassName(String materielClassName) {
		this.materielClassName = materielClassName;
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

	public BigDecimal getMaterielCostPrice() {
		return materielCostPrice;
	}

	public void setMaterielCostPrice(BigDecimal materielCostPrice) {
		this.materielCostPrice = materielCostPrice;
	}

	public Integer getMaterielBrandId() {
		return materielBrandId;
	}

	public void setMaterielBrandId(Integer materielBrandId) {
		this.materielBrandId = materielBrandId;
	}

	public String getMaterielBrandName() {
		return materielBrandName;
	}

	public void setMaterielBrandName(String materielBrandName) {
		this.materielBrandName = materielBrandName;
	}

	public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public String getMaterielSpecValues() {
		return materielSpecValues;
	}

	public void setMaterielSpecValues(String materielSpecValues) {
		this.materielSpecValues = materielSpecValues;
	}

	public Integer getMaterielEnableFlag() {
		return materielEnableFlag;
	}

	public void setMaterielEnableFlag(Integer materielEnableFlag) {
		this.materielEnableFlag = materielEnableFlag;
		if (materielEnableFlag != null) {
			switch (materielEnableFlag.intValue()) {
			case 0:
				this.materielEnableFlag_s = "禁用";
				break;
			case 1:
				this.materielEnableFlag_s = "启用";
				break;
			default:
				this.materielEnableFlag_s = "";
				break;
			}
		}
	}

	public String getMaterielEnableFlag_s() {
		return materielEnableFlag_s;
	}

	public void setMaterielEnableFlag_s(String materielEnableFlag_s) {
		this.materielEnableFlag_s = materielEnableFlag_s;
	}

	public String getMaterielUnitName() {
		return materielUnitName;
	}

	public void setMaterielUnitName(String materielUnitName) {
		this.materielUnitName = materielUnitName;
	}

	public Integer getStorageCount() {
		return storageCount;
	}

	public void setStorageCount(Integer storageCount) {
		this.storageCount = storageCount;
	}

}
