/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月14日 下午4:50:28
 */
public class WarehouseCommodityResp extends BaseEntity {

	private static final long serialVersionUID = 7339566904717737013L;

	private String batchNo;// 商品批次号

	private String snNo;// 商品SN

	private String imieNo;// 商品IMEI

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
