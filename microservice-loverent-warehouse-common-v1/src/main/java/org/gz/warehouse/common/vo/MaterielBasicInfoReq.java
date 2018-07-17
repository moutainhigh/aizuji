/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年1月5日 下午2:07:46
 */
public class MaterielBasicInfoReq extends BaseEntity {

	private static final long serialVersionUID = -3395918508778288545L;

	private Integer materielModelId;//物料型号ID

	private Integer materielFlag;//主物料标志
	
	public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}

	public Integer getMaterielFlag() {
		return materielFlag;
	}

	public void setMaterielFlag(Integer materielFlag) {
		this.materielFlag = materielFlag;
	}
}
