/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存调整申请清单明细
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:19:21
 */
public class WarehouseAdjustListDetail extends BaseEntity {

	private static final long serialVersionUID = -7287225318819989213L;

	private Long id;

	private Long adjustListId;// 关联warehouse_adjust_list表主键

	private String batchNo;// 商品批次号

	private String snNo;// 商品SN

	private String imieNo;// 商品IMIE号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdjustListId() {
		return adjustListId;
	}

	public void setAdjustListId(Long adjustListId) {
		this.adjustListId = adjustListId;
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
