package org.gz.warehouse.entity;

import java.util.List;

import org.gz.common.entity.BaseEntity;


/**
 * 
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月16日 上午11:32:19
 */
public class MaterielModel extends BaseEntity {

    private static final long serialVersionUID = -1106265558067694538L;

    private Integer id;//主键id

    private String  materielModelName;//物料型号名称

    private String  materielModelDesc;//物料型号描述

    private Integer materielClassId;//物料分类ID
    
    private Integer materielBrandId;//物料品牌Id

	private String thumbnailUrl;//物料缩略图

	private String materielModelId;
    
    private Integer enableFlag=0;//启用/信用标志 0:禁用 1:启用

    private List<MaterielModelSpec> modelSpecList;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public String getMaterielModelDesc() {
		return materielModelDesc;
	}

	public void setMaterielModelDesc(String materielModelDesc) {
		this.materielModelDesc = materielModelDesc;
	}

	public Integer getMaterielBrandId() {
		return materielBrandId;
	}

	public void setMaterielBrandId(Integer materielBrandId) {
		this.materielBrandId = materielBrandId;
	}

	public String getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(String materielModelId) {
		this.materielModelId = materielModelId;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public List<MaterielModelSpec> getModelSpecList() {
		return modelSpecList;
	}

	public void setModelSpecList(List<MaterielModelSpec> modelSpecList) {
		this.modelSpecList = modelSpecList;
	}

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
}
