package org.gz.warehouse.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 物料基础信息表
 * 
 * @author hxj
 *
 */
public class MaterielBasicInfo extends WarehouseBaseEntity {

	private static final long serialVersionUID = -4377482345197219471L;

	@NotBlank(message="物料名称不能为空")
	@Length(min=1,max=200,message="物料名称必填，且长度不超过200个字符")
	protected String materielName;// 物料名称

	protected String materielCode;// 物料编码

	@NotNull(message="物料分类不能为空")
	@Range(min=1,message="非法物料分类值")
	protected Integer materielClassId;// 物料分类
	
	protected String materielClassName;//物料分类名称

	@NotNull(message="所属品牌不能为空")
	@Range(min=1,message="非法品牌值")
	protected Integer materielBrandId;// 物料品牌
	
	protected String brandName;//品牌名称 
	
	@NotNull(message="品牌型号不能为空")
	@Range(min=1,message="非法品牌型号值")
	protected Integer materielModelId;// 品牌对应型号

	protected String materielModelName;//型号名称 
	
	@NotBlank(message="型号规格不能为空")
	@Length(min=1,max=200,message="型号规格长度不超过200个字符")
	protected String specBatchNo;// 规格批次号

	protected String specValues;//规格批次值
	
	@NotNull(message=" 物料单位不能为空")
	@Range(min=1,message="非法物料单位值")
	protected Integer materielUnitId;// 物料单位ID
	
	protected String unitName;//单位名称;

	protected String materielBarCode;// 物料条形码

	@Length(max=255,message="备注长度不超过255个字符")
	protected String remark;// 备注

	@Range(min=0,max=1,message="非法启用/停用标志值")
	protected Integer enableFlag=0;//启用/停用标志 0:禁用 1:启用
	
	protected BigDecimal materielCostPrice=new BigDecimal("0.00");//物料成本价
	
	protected String thumbnailUrl;//缩略图路径
	
	@Range(min=0,max=1,message="非法主物料标志")
	protected Integer materielFlag=0;//主物料标志 0：否 1：是
	
	protected String materielIntroduction;//物料介绍
	
	protected Integer materielNewConfigId;//新旧程度配置ID
	
	protected String configValue;//新旧程度配置值
	
	protected List<MaterielBasicImages> attaList;//相关附件列表
	
	
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

	public Integer getMaterielClassId() {
		return materielClassId;
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

	public String getSpecBatchNo() {
		return specBatchNo;
	}

	public void setSpecBatchNo(String specBatchNo) {
		this.specBatchNo = specBatchNo;
	}

	public Integer getMaterielUnitId() {
		return materielUnitId;
	}

	public void setMaterielUnitId(Integer materielUnitId) {
		this.materielUnitId = materielUnitId;
	}

	public String getMaterielBarCode() {
		return materielBarCode;
	}

	public void setMaterielBarCode(String materielBarCode) {
		this.materielBarCode = materielBarCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public BigDecimal getMaterielCostPrice() {
		return materielCostPrice;
	}

	public void setMaterielCostPrice(BigDecimal materielCostPrice) {
		this.materielCostPrice = materielCostPrice;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public List<MaterielBasicImages> getAttaList() {
		return attaList;
	}

	public void setAttaList(List<MaterielBasicImages> attaList) {
		this.attaList = attaList;
	}

	public String getSpecValues() {
		return specValues;
	}

	public void setSpecValues(String specValues) {
		this.specValues = specValues;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getMaterielFlag() {
		return materielFlag;
	}

	public void setMaterielFlag(Integer materielFlag) {
		this.materielFlag = materielFlag;
	}

	public String getMaterielIntroduction() {
		return materielIntroduction;
	}

	public void setMaterielIntroduction(String materielIntroduction) {
		this.materielIntroduction = materielIntroduction;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public String getMaterielClassName() {
		return materielClassName;
	}

	public void setMaterielClassName(String materielClassName) {
		this.materielClassName = materielClassName;
	}

	public Integer getMaterielNewConfigId() {
		return materielNewConfigId;
	}

	public void setMaterielNewConfigId(Integer materielNewConfigId) {
		this.materielNewConfigId = materielNewConfigId;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}
