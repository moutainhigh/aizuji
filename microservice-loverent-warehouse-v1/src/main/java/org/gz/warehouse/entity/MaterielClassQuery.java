package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * 物料分类查询实体
 */
public class MaterielClassQuery extends QueryPager {

	private static final long serialVersionUID = 8126106737591140270L;

	/**
	 * 主键id
	 */
	private Integer id;

	/**
	 * 类别名称
	 */
	private String typeName;

	/**
	 * 类别编码
	 */
	private String typeCode;

	/**
	 * 分类层级
	 */
	private Integer typeLevel;

	/**
	 * 父分类ID
	 */
	private Integer parentId;

	public MaterielClassQuery() {

	}

	public MaterielClassQuery(MaterielClass m) {
		this.id = m.getId();
		this.typeName = m.getTypeName();
		this.parentId = m.getParentId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getTypeLevel() {
		return typeLevel;
	}

	public void setTypeLevel(Integer typeLevel) {
		this.typeLevel = typeLevel;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
