/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

/**
 * @Title:库存商品关联实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:00:18
 */
public class WarehouseCommodityInfo extends BaseEntity {

	private static final long serialVersionUID = 2896739284706794277L;

	private Long id;// 主键

	@NotNull(message = "仓库ID不能为空")
	private Integer warehouseId;// 商品所在仓库ID

	@NotNull(message = "商品所在库位ID不能为空")
	private Integer warehouseLocationId;// 商品所在库位ID

	@NotNull(message = "物料基础信息ID不能为空")
	private Long materielBasicId;// 物料基础信息ID

	@NotBlank(message = "物料基础信息ID不能为空")
	@Length(max = 50, message = "入库批次号长度不超过50个字符")
	private String storageBatchNo;// 入库批次号

	@NotBlank(message = "商品批次号不能为空")
	@Length(max = 50, message = "商品批次号长度不超过50个字符")
	private String batchNo;

	@NotBlank(message = "商品SN不能为空")
	@Length(max = 50, message = "商品SN长度不超过50个字符")
	private String snNo;

	@NotBlank(message = "商品IMIE号不能为空")
	@Length(max = 50, message = "商品IMIE号长度不超过50个字符")
	private String imieNo;

	@NotNull(message = "操作人ID不能为null")
	private Long operatorId;

	@NotBlank(message = "商品IMIE号不能为空")
	private String operatorName;// 操作人名称

	private Date operateOn = new Date();// 创建时间/操作时间

	public WarehouseCommodityInfo() {
		
	}
	
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
