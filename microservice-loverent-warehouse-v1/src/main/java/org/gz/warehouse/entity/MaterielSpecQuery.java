package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * 物料规格实体
 * 
 * @author hxj
 *
 */
public class MaterielSpecQuery extends QueryPager {

	private static final long serialVersionUID = 1871098611730395523L;

	private Integer id;// 主键

	private String specName;// 规格名称

	private Integer enableFlag;
	
	public MaterielSpecQuery() {

	}

	public MaterielSpecQuery(MaterielSpec spec) {
		this.id = spec.getId();
		this.specName = spec.getSpecName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}
}