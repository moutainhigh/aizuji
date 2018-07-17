/**
 * 
 */
package org.gz.warehouse.entity;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月16日 上午11:35:56
 */
public class MaterielModelSpec extends BaseEntity {

	private static final long serialVersionUID = 8126427004632104862L;

	private Integer id;// 主键id

	private Integer materielModelId;// 物料型号id

	private Integer materielSpecId;// 物料规格id

	private String materielSpecValue;// 物料规格值

	private String specBatchNo;// 规格批次号(用于标记每个型号的多个规格)

	public MaterielModelSpec() {

	}

	public MaterielModelSpec(Integer materielSpecId, String materielSpecValue) {
		this.materielSpecId = materielSpecId;
		this.materielSpecValue = materielSpecValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}

	public Integer getMaterielSpecId() {
		return materielSpecId;
	}

	public void setMaterielSpecId(Integer materielSpecId) {
		this.materielSpecId = materielSpecId;
	}

	public String getMaterielSpecValue() {
		return materielSpecValue;
	}

	public void setMaterielSpecValue(String materielSpecValue) {
		this.materielSpecValue = materielSpecValue;
	}

	public String getSpecBatchNo() {
		return specBatchNo;
	}

	public void setSpecBatchNo(String specBatchNo) {
		this.specBatchNo = specBatchNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((materielModelId == null) ? 0 : materielModelId.hashCode());
		result = prime * result + ((materielSpecId == null) ? 0 : materielSpecId.hashCode());
		result = prime * result + ((materielSpecValue == null) ? 0 : materielSpecValue.hashCode());
		result = prime * result + ((specBatchNo == null) ? 0 : specBatchNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaterielModelSpec other = (MaterielModelSpec) obj;
		if (materielModelId == null) {
			if (other.materielModelId != null)
				return false;
		} else if (!materielModelId.equals(other.materielModelId))
			return false;
		if (materielSpecId == null) {
			if (other.materielSpecId != null)
				return false;
		} else if (!materielSpecId.equals(other.materielSpecId))
			return false;
		if (materielSpecValue == null) {
			if (other.materielSpecValue != null)
				return false;
		} else if (!materielSpecValue.equals(other.materielSpecValue))
			return false;
		if (specBatchNo == null) {
			if (other.specBatchNo != null)
				return false;
		} else if (!specBatchNo.equals(other.specBatchNo))
			return false;
		return true;
	}

}
