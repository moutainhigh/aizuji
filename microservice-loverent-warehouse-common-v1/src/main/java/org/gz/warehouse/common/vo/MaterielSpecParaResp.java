/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月22日 下午3:22:33
 */
public class MaterielSpecParaResp extends BaseEntity {

	private static final long serialVersionUID = -5118099307430839999L;

	private Long id;// 主键ID

	private Long materielBasicId;// 物料基础ID

	private String specDesc;// 规格参数的JSON字符串

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getSpecDesc() {
		return specDesc;
	}

	public void setSpecDesc(String specDesc) {
		this.specDesc = specDesc;
	}

}
