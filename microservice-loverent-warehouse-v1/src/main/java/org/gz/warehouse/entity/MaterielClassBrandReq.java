/**
 * 
 */
package org.gz.warehouse.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月15日 下午3:34:50
 */
public class MaterielClassBrandReq {

	@NotNull(message="分类ID不能为空")
	@Range(min=1,message="非法分类ID值")
	private Integer materielClassId;//分类ID
	
	private String materielBrandIds;//多个品牌ID以逗号分隔

	public Integer getMaterielClassId() {
		return materielClassId;
	}

	public void setMaterielClassId(Integer materielClassId) {
		this.materielClassId = materielClassId;
	}

	public String getMaterielBrandIds() {
		return materielBrandIds;
	}

	public void setMaterielBrandIds(String materielBrandIds) {
		this.materielBrandIds = materielBrandIds;
	}
}
