/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月26日 下午5:41:28
 */
public class WarehouseMaterielCount extends BaseEntity {

	private static final long serialVersionUID = -7765146501964453905L;

	private Integer materielBasicId;// 物料基础信息ID

	private Integer materielCount;// 物料数量

	public Integer getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Integer materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public Integer getMaterielCount() {
		return materielCount;
	}

	public void setMaterielCount(Integer materielCount) {
		this.materielCount = materielCount;
	}

}
