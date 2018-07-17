/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 签约返回结果实体类
 * @author hxj
 * @Description:
 * @date 2017年12月27日 上午9:34:47
 */
public class SigningResult extends BaseEntity {

	private static final long serialVersionUID = 2014025655650835610L;

	private String sourceOrderNo;// 原始订单号

	private Long materielBasicId;// 物料ID

	private String batchNo;//商品批次号

	private String snNo;//SN号

	private String imieNo;//IMIE号

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getImieNo() {
		return imieNo;
	}

	public void setImieNo(String imieNo) {
		this.imieNo = imieNo;
	}

}
