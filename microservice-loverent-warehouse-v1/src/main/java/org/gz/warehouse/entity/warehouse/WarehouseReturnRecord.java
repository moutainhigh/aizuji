package org.gz.warehouse.entity.warehouse;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;
import org.gz.common.utils.AssertUtils;
import org.hibernate.validator.constraints.Range;

/**
 * @Title: 还机记录实体类
 * @author daiqingwen
 * @date 2018年3月6日 上午 10:25
 */
public class WarehouseReturnRecord extends BaseEntity {

	private static final long serialVersionUID = 3171979539373246117L;

	@NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
	private Long id; // 主键

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	private String sourceOrderNo; // 销售单号

	private Long productId;

	private Integer productType;

	private String productType_s;

	private Integer sourceOrderStatus;// 源订单状态

	private Integer transitInType;// 入库类型：10：对于租赁的订单，入库类型为“还机入库”;20:对于售卖的订单，入库类型为“退货入库”；

	private String transitInType_s;

	@NotNull(message = "配置名称不能为空")
	private Long materielBasicId; // 物料ID

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	private String batchNo; // 商品批次号

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	private String snNo; // 商品SN号

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	private String imieNo; // 商品IMIE号

	private Double damagePrice; // 定损价格

	private String damageRemark; // 定损备注

	@NotNull(message = "配置名称不能为空")
	private Integer materielStatus; // 10：新机 20:好机 30：坏机

	@NotNull(message = "配置名称不能为空")
	private Long operatorId; // 操作人ID

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	private String operatorName; // 操作人名称

	@NotNull(message = "配置名称不能为空")
	private Date operateOn; // 操作时间（入库时间）

	private String transitlnTime;// 入库时间

	@NotNull(message = "配置名称不能为空")
	private Date returnApplyTime; // 还机申请时间

	private String applyTime;// 申请时间

	private String materielCode; // 物料编码

	@NotNull(message = "配置名称不能为空")
	private Integer materielModelId; // 物料型号ID

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	private String specBatchNo; // 规格批次号（用于标记每个型号的多个规格）

	@NotNull(message = "配置名称不能为空")
	private Integer materielUnitId; // 物料单位ID

	private String materielSpecValue; // 规格值

	private String materielModelName; // 型号名称

	private String unitName; // 单位名称

	private String materielName; // 物料名称

	private Integer transitlnStatus; // 还机状态：10 还机中 ， 20 已入库

	private String transitlnName; // 还机状态名称

	private String logisticsNo;// 申请还机时，订单系统传递的物流单号

	private List<WarehouseReturnImages> attaList; // 相关附件列表

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public Integer getSourceOrderStatus() {
		return sourceOrderStatus;
	}

	public void setSourceOrderStatus(Integer sourceOrderStatus) {
		this.sourceOrderStatus = sourceOrderStatus;
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

	public Double getDamagePrice() {
		return damagePrice;
	}

	public void setDamagePrice(Double damagePrice) {
		this.damagePrice = damagePrice;
	}

	public String getDamageRemark() {
		return damageRemark;
	}

	public void setDamageRemark(String damageRemark) {
		this.damageRemark = damageRemark;
	}

	public Integer getMaterielStatus() {
		return materielStatus;
	}

	public void setMaterielStatus(Integer materielStatus) {
		this.materielStatus = materielStatus;
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

	public Date getReturnApplyTime() {
		return returnApplyTime;
	}

	public void setReturnApplyTime(Date returnApplyTime) {
		this.returnApplyTime = returnApplyTime;
	}

	public List<WarehouseReturnImages> getAttaList() {
		return attaList;
	}

	public void setAttaList(List<WarehouseReturnImages> attaList) {
		this.attaList = attaList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
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

	public Integer getMaterielUnitId() {
		return materielUnitId;
	}

	public void setMaterielUnitId(Integer materielUnitId) {
		this.materielUnitId = materielUnitId;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielSpecValue() {
		return materielSpecValue;
	}

	public void setMaterielSpecValue(String materielSpecValue) {
		this.materielSpecValue = materielSpecValue;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getTransitlnStatus() {
		return transitlnStatus;
	}

	public void setTransitlnStatus(Integer transitlnStatus) {
		this.transitlnStatus = transitlnStatus;
		if (AssertUtils.isPositiveNumber4Int(transitlnStatus)) {
			switch (transitlnStatus.intValue()) {
			// 10 还机中 ， 20 已入库
			case 10:
				this.transitlnName = "还机中 ";
				break;
			case 20:
				this.transitlnName = "已入库";
				break;
			default:
				this.transitlnName = "未知";
				break;
			}
		}
	}

	public Integer getTransitInType() {
		return transitInType;
	}

	public void setTransitInType(Integer transitInType) {
		this.transitInType = transitInType;
		if (AssertUtils.isPositiveNumber4Int(transitInType)) {
			switch (transitInType) {
			case 10:
				this.transitInType_s = "还机入库";
				break;
			case 20:
				this.transitInType_s = "退货入库";
				break;
			default:
				this.transitInType_s = "未知";
				break;
			}
		}
	}

	public String getTransitlnName() {
		return transitlnName;
	}

	public void setTransitlnName(String transitlnName) {
		this.transitlnName = transitlnName;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getTransitlnTime() {
		return transitlnTime;
	}

	public void setTransitlnTime(String transitlnTime) {
		this.transitlnTime = transitlnTime;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getTransitInType_s() {
		return transitInType_s;
	}

	public void setTransitInType_s(String transitInType_s) {
		this.transitInType_s = transitInType_s;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
		if (AssertUtils.isPositiveNumber4Int(productType)) {
			switch (productType.intValue()) {
			case 1:
				this.productType_s = "租赁";
				break;
			case 2:
				this.productType_s = "以租代购";
				break;
			case 3:
				this.productType_s = "售卖";
				break;
			default:
				this.productType_s = "";
				break;
			}
		} else {
			this.productType_s = "";
		}

	}

	public String getProductType_s() {
		return productType_s;
	}

	public void setProductType_s(String productType_s) {
		this.productType_s = productType_s;
	}

}
