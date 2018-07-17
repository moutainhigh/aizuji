/**
 * 
 */
package org.gz.warehouse.entity;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Range;

/**
 * 分类品牌映射实体
 * @author hxj
 * @Description:
 * @date 2017年12月15日 下午3:28:31
 */
public class MaterielClassBrand extends BaseEntity {

	private static final long serialVersionUID = 2852816188088451930L;

	private Integer id;//主键ID

	@NotNull(message="分类ID不能为空")
	@Range(min=1,message="非法分类ID值")
	private Integer materielClassId;//分类ID

	@NotNull(message="品牌ID不能为空")
	@Range(min=1,message="非法品牌ID值")
	private Integer materielBrandId;//品牌ID

	public MaterielClassBrand() {
		
	}
	
	public MaterielClassBrand(Integer materielClassId, Integer brandId) {
		this.materielClassId=materielClassId;
		this.materielBrandId=brandId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
