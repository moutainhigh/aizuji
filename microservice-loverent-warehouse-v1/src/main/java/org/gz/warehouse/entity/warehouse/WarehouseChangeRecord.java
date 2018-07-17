/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存变化实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:14:22
 */
public class WarehouseChangeRecord extends BaseEntity {

	private static final long serialVersionUID = 7851947443500464797L;

	private Long id;

	private Integer warehouseId;

	private Integer warehouseLocationId;

	private Long materielBasicId;// 物料基础信息ID

	private String storageBatchNo;// 入库批次号(warehouse_commodity_info.storageBatchNo)

	private String adjustBatchNo;// 调整批次号

	private Integer adjustType;// 调整类型 1:采购 2:销售 3：人工

	private String sourceOrderNo;// 源单号:其值为：采购单号或销售单号

	private Integer changeQuantity;// 变更数量

	private String adjustReason;// 调整原因

	private Long operatorId;// 创建人/操作人/调整人ID

	private String operatorName;// 创建人/操作人/调整人名称

	private Date operateOn;// 创建时间/调整时间/操作时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getStorageBatchNo() {
		return storageBatchNo;
	}

	public void setStorageBatchNo(String storageBatchNo) {
		this.storageBatchNo = storageBatchNo;
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

	public Integer getChangeQuantity() {
		return changeQuantity;
	}

	public void setChangeQuantity(Integer changeQuantity) {
		this.changeQuantity = changeQuantity;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateOn() {
		return operateOn;
	}

	public void setOperateOn(Date operateOn) {
		this.operateOn = operateOn;
	}
}
