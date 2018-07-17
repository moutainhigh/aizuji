/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 库存检货状态结果
 * @author hxj
 * @Description:
 * @date 2017年12月29日 下午3:12:00
 */
public class WarehousePickResult extends BaseEntity {

	private static final long serialVersionUID = 6189202978047127104L;

	private String sourceOrderNo;

	private Integer statusFlag;

	private String statusFlag_s;

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getStatusFlag_s() {
		return statusFlag_s;
	}

	public void setStatusFlag_s(String statusFlag_s) {
		this.statusFlag_s = statusFlag_s;
	}

}
