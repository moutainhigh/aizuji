/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.QueryPager;

/**
 * @Title: 库存变化查询实体
 * @author hxj
 * @Description:
 * @date 2017年12月23日 上午8:50:14
 */
public class WarehouseChangeRecordQuery extends QueryPager {

	private static final long serialVersionUID = -2666381754098011153L;

	private Integer warehouseId;// 仓库ID

	private Integer warehouseLocationId;// 库位ID

	private Long materielBasicId;// 物料ID

	private String adjustBatchNo;// 调整批次号

	private Integer adjustType;// 调整类型 1:采购 2:销售 3：人工

	private String sourceOrderNo;// 调整源单号

	private String operatorName;// 操作人

	private String startOperateTime;// 操作开始时间

	private String endOperateTime;// 操作结束时间

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getWarehouseLocationId() {
		return warehouseLocationId;
	}

	public void setWarehouseLocationId(Integer warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getAdjustBatchNo() {
		return adjustBatchNo;
	}

	public void setAdjustBatchNo(String adjustBatchNo) {
		this.adjustBatchNo = adjustBatchNo;
	}

	public Integer getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(Integer adjustType) {
		this.adjustType = adjustType;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getStartOperateTime() {
		return startOperateTime;
	}

	public void setStartOperateTime(String startOperateTime) {
		this.startOperateTime = startOperateTime;
	}

	public String getEndOperateTime() {
		return endOperateTime;
	}

	public void setEndOperateTime(String endOperateTime) {
		this.endOperateTime = endOperateTime;
	}
}
