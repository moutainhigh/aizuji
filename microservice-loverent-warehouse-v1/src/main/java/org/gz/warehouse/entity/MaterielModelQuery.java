package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * 
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月16日 上午11:29:59
 */
public class MaterielModelQuery extends QueryPager {

	private static final long serialVersionUID = 4772240877833004304L;

	private Integer id;// 主键id

	private String materielModelName;// 物料型号名称

	private Integer materielClassId;// 物料分类ID

	private Integer materielBrandId;// 物料品牌Id

	private Integer enableFlag;// 启用/信用标志 0:禁用 1:启用

	private Integer brandId; //品牌ID

	public MaterielModelQuery() {
		
	}
	
	public MaterielModelQuery(MaterielModel m) {
		this.id = m.getId();
		this.materielModelName=m.getMaterielModelName();
		this.materielClassId=m.getMaterielClassId();
		this.materielBrandId=m.getMaterielBrandId();
		this.enableFlag=m.getEnableFlag();
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public Integer getMaterielBrandId() {
		return materielBrandId;
	}

	public void setMaterielBrandId(Integer materielBrandId) {
		this.materielBrandId = materielBrandId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

}
