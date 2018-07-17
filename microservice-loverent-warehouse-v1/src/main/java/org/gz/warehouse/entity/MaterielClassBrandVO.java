package org.gz.warehouse.entity;

import org.gz.common.entity.BaseEntity;

/**
 * @author hxj
 */
public class MaterielClassBrandVO extends BaseEntity {

	private static final long serialVersionUID = -1190732354202345675L;

	private Integer id;// 品牌主键

	private String brandName;// 品牌名称

	private Integer count;//指定分类包含的品牌数:0:未包含，1：包含

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
