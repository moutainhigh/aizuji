package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * 物料品牌查询实体
 * 
 * @author hxj
 */
public class MaterielBrandQuery extends QueryPager {

	private static final long serialVersionUID = -7472820133911799111L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 品牌编码
	 */
	private String brandCode;

	public MaterielBrandQuery() {

	}

	public MaterielBrandQuery(MaterielBrand brand) {
		this.id = brand.getId();
		this.brandName = brand.getBrandName();
		this.brandCode = brand.getBrandCode();
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
