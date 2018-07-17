package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * 物料单位查询实体
 * 
 * @author hxj
 */
public class MaterielUnitQuery extends QueryPager {

	private static final long serialVersionUID = -6880380017017128395L;

	private Integer id;// 主键

	private String unitName;// 单位名称

	private String unitCode;// 单位编码

	private Integer enableFlag;
	
	public MaterielUnitQuery() {

	}

	public MaterielUnitQuery(MaterielUnit unit) {
		this.id = unit.getId();
		this.unitName = unit.getUnitName();
		this.unitCode = unit.getUnitCode();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}
}
