/**
 * 
 */
package org.gz.warehouse.entity;

import org.gz.common.entity.QueryPager;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月16日 上午11:35:56
 */
public class MaterielModelSpecQuery extends QueryPager {

	private static final long serialVersionUID = 8126427004632104862L;

	private Integer id;// 主键id

	private Integer materielModelId;// 物料型号id

	private String specBatchNo;// 规格批次号(用于标记每个型号的多个规格)

	public MaterielModelSpecQuery() {

	}

	public MaterielModelSpecQuery(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}
	
	public MaterielModelSpecQuery(Integer materielModelId,String specBatchNo) {
		this.materielModelId = materielModelId;
		this.specBatchNo=specBatchNo;
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

	public String getSpecBatchNo() {
		return specBatchNo;
	}

	public void setSpecBatchNo(String specBatchNo) {
		this.specBatchNo = specBatchNo;
	}
}
