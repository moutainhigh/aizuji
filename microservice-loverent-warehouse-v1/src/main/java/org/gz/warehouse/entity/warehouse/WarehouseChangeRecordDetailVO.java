/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存变化实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:14:22
 */
public class WarehouseChangeRecordDetailVO extends BaseEntity {

	private static final long serialVersionUID = 7851947443500464797L;

	private String batchNo;// 商品批次号

	private String snNo;// 商品SN

	private String imieNo;// 商品IMIE


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
