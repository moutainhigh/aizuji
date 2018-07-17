/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.gz.common.entity.BaseEntity;
import org.gz.common.utils.AssertUtils;

/**
 * @Title:库存变化实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:14:22
 */
public class WarehouseChangeRecordVO extends BaseEntity {

	private static final long serialVersionUID = 7851947443500464797L;

	static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>(){
        @Override 
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    
	private Long id;

	private Integer warehouseId;

	private String warehouseName;

	private Integer warehouseLocationId;

	private String locationName;

	private Long materielBasicId;// 物料基础信息ID

	private String materielName;

	private String materielCode;

	private String materielSpecValues;

	private String storageBatchNo;// 入库批次号(warehouse_commodity_info.storageBatchNo)

	private String adjustBatchNo;// 调整批次号

	private Integer adjustType;// 调整类型 1:采购 2:销售 3：人工

	private String adjustType_s;

	private String sourceOrderNo;// 源单号:其值为：采购单号或销售单号

	private Integer changeQuantity;// 变更数量

	private String adjustReason;// 调整原因

	private Long operatorId;// 创建人/操作人/调整人ID

	private String operatorName;// 创建人/操作人/调整人名称

	private Date operateOn;// 创建时间/调整时间/操作时间

	private String operateOn_s;// 操作时间

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
		if (AssertUtils.isPositiveNumber4Int(adjustType)) {
			switch (adjustType.intValue()) {
			case 1:
				this.adjustType_s = "采购";
				break;
			case 2:
				this.adjustType_s = "销售";
				break;
			case 3:
				this.adjustType_s = "人工";
				break;
			default:
				this.adjustType_s = "其它";
				break;
			}
		}
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
		if (operateOn != null) {
			this.operateOn_s = sdf.get().format(operateOn);
		}
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterielSpecValues() {
		return materielSpecValues;
	}

	public void setMaterielSpecValues(String materielSpecValues) {
		this.materielSpecValues = materielSpecValues;
	}

	public String getAdjustType_s() {
		return adjustType_s;
	}

	public void setAdjustType_s(String adjustType_s) {
		this.adjustType_s = adjustType_s;
	}

	public String getOperateOn_s() {
		return operateOn_s;
	}

	public void setOperateOn_s(String operateOn_s) {
		this.operateOn_s = operateOn_s;
	}

}
