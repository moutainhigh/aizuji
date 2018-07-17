/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.util.List;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:仓库物料汇总包装实体
 * @author hxj
 * @Description:
 * @date 2017年12月22日 上午11:10:38
 */
public class WarehouseMaterielAggregationWrapper extends BaseEntity {

	private static final long serialVersionUID = -7963186614515222908L;

	private Integer materielBasicId;// 物料基础信息ID

	private String materielName;// 物料名称

	private String materielCode;// 物料编码
	
	private String materielClassName;// 物料分类名称

	private String materielBrandName;// 品牌名称

	private String materielModelName;// 型号名称

	private String materielSpecValues;// 物料规格

	private String materielUnitName;// 物料单位名称

	private Integer materielCount;// 物料数量

	private List<WarehouseMaterielDetailVO> warehouseDetailList;//物料所在仓库明细列表
	
	public Integer getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Integer materielBasicId) {
		this.materielBasicId = materielBasicId;
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

	public String getMaterielClassName() {
		return materielClassName;
	}

	public void setMaterielClassName(String materielClassName) {
		this.materielClassName = materielClassName;
	}

	public String getMaterielBrandName() {
		return materielBrandName;
	}

	public void setMaterielBrandName(String materielBrandName) {
		this.materielBrandName = materielBrandName;
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

	public String getMaterielUnitName() {
		return materielUnitName;
	}

	public void setMaterielUnitName(String materielUnitName) {
		this.materielUnitName = materielUnitName;
	}

	public Integer getMaterielCount() {
		return materielCount;
	}

	public void setMaterielCount(Integer materielCount) {
		this.materielCount = materielCount;
	}

	public List<WarehouseMaterielDetailVO> getWarehouseDetailList() {
		return warehouseDetailList;
	}

	public void setWarehouseDetailList(List<WarehouseMaterielDetailVO> warehouseDetailList) {
		this.warehouseDetailList = warehouseDetailList;
	}
}
