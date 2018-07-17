package org.gz.warehouse.common.vo;

import java.util.List;

import org.gz.common.entity.BaseEntity;

/**
 * 物料基础信息分页实体
 * 
 * @author hxj
 *
 */
public class MaterielBasicInfoResp extends BaseEntity {


	private static final long serialVersionUID = 4999960634634372150L;

	protected Long id;//主键ID
	
	protected String materielName;// 物料名称

	protected String materielCode;// 物料编码

	protected Integer materielClassId;// 物料分类

	protected Integer materielBrandId;// 物料品牌

	protected String brandName;//品牌名称 
	
	protected Integer materielModelId;// 品牌对应型号

	protected String materielModelName;//型号名称 
	
	protected String specBatchNo;// 规格批次号

	protected String specValues;//规格批次值
	
	protected Integer materielUnitId;//物料单位ID

	protected String unitName;//单位名称
	
	protected String materielBarCode;// 物料条形码

	protected Integer enableFlag;//启用/停用标志 0:禁用 1:启用
	
	protected String thumbnailUrl;//列表缩率图

	protected Integer materielFlag;//主物料标志 0：否 1：是
	
	protected String materielIntroduction;//物料详情介绍
	
	protected List<MaterielBasicImagesResp> attaList;//相关附件列表
	
	public MaterielBasicInfoResp() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public String getSpecBatchNo() {
		return specBatchNo;
	}

	public void setSpecBatchNo(String specBatchNo) {
		this.specBatchNo = specBatchNo;
	}

	public String getSpecValues() {
		return specValues;
	}

	public void setSpecValues(String specValues) {
		this.specValues = specValues;
	}

	public Integer getMaterielUnitId() {
		return materielUnitId;
	}

	public void setMaterielUnitId(Integer materielUnitId) {
		this.materielUnitId = materielUnitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getMaterielBarCode() {
		return materielBarCode;
	}

	public void setMaterielBarCode(String materielBarCode) {
		this.materielBarCode = materielBarCode;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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

	public List<MaterielBasicImagesResp> getAttaList() {
		return attaList;
	}

	public void setAttaList(List<MaterielBasicImagesResp> attaList) {
		this.attaList = attaList;
	}
}
