/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;
import org.gz.common.utils.AssertUtils;
import org.hibernate.validator.constraints.Range;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月29日 上午9:51:55
 */
public class WarehousePickingRecord extends BaseEntity {

	private static final long serialVersionUID = -4557417882850557988L;

	private Long id;

	@NotNull(message = "销售单号不能为null")
	@NotBlank(message = "销售单号不能为空")
	private String sourceOrderNo;// 销售单号

	private Date applyDateTime;// 申请日期

	private String applyTimes;// 申请日期字符串

	@NotNull(message = "物料ID不能为null")
	@Positive(message = "物料ID必须为正值")
	private Long materielBasicId;// 物料ID

	private String materielName;// 物料名称

	private String materielCode;// 物料编码

	private String materielModelName ;//物料型号
	
	private String materielSpecValue;// 物料规格值

	private String materielUnitName;// 物料单位名称

	private Integer pickQuantity = 1;// 拣货数量

	private String batchNo;// 商品批次号

	@NotNull(message = "商品sn号不能为null")
	@NotBlank(message = "商品sn号不能为空")
	private String snNo;// 商品sn号

	@NotNull(message = "IMIE号不能为null")
	@NotBlank(message = "IMIE号不能为空")
	private String imieNo;// IMIE号

	private String logisticsNo;// 物流单号

	private Long fillReceiptId;// 填单人ID

	private String fillReceiptName;// 填单人姓名

	private Date fillReceiptOn;// 填单时间

	private String fillReceiptOn_s;

	private Long operatorId = 1L;// 操作人ID

	private String operatorName = "管理员";// 操作人姓名

	private Date operateOn = new Date();// 操作时间

	private String operateOn_s;

	private Integer statusFlag;// 待发货:8 9:已出库

	private String statusFlag_s;

	@NotNull(message="保价金额不能为空",groups= {UpdateRecordGroup.class})
	@Pattern(regexp="0|2000|5000",message="非法保价金额",groups= {UpdateRecordGroup.class})
	private String insuredAmount;// 保价金额

	@NotNull(message="产品ID不能为空",groups= {UpdateRecordGroup.class})
	@Positive(message="非法产品ID",groups= {UpdateRecordGroup.class})
	private Long productId;// 产品ID

	private Integer productType;//产品类型
	
	private String productType_s;
	
	private String monthlyCardNum;// 选用的月结卡号

	/**
	 * 省份
	 */
	@NotNull(message="收件人所在省份不能为空",groups= {UpdateRecordGroup.class})
	@NotBlank(message="收件人所在省份不能为空",groups= {UpdateRecordGroup.class})
	private String prov;
	/**
	 * 城市
	 */
	@NotNull(message="收件人所在城市不能为空",groups= {UpdateRecordGroup.class})
	@NotBlank(message="收件人所在城市不能为空",groups= {UpdateRecordGroup.class})
	private String city;
	/**
	 * 地区
	 */
	@NotNull(message="收件人所在区县不能为空",groups= {UpdateRecordGroup.class})
	@NotBlank(message="收件人所在区县不能为空",groups= {UpdateRecordGroup.class})
	private String area;
	/**
	 * 详细地址
	 */
	@NotNull(message="收件人详细地址不能为空",groups= {UpdateRecordGroup.class})
	@NotBlank(message="收件人详细地址不能为空",groups= {UpdateRecordGroup.class})
	private String address;

	/**
	 * 手机号码
	 */
	@NotNull(message="收件人手机号码不能为空",groups= {UpdateRecordGroup.class})
	@NotBlank(message="收件人手机号码不能为空",groups= {UpdateRecordGroup.class})
	private String phoneNum;

	/**
	 * 真实姓名
	 */
	@NotNull(message="收件人姓名不能为空",groups= {UpdateRecordGroup.class})
	@NotBlank(message="收件人姓名不能为空",groups= {UpdateRecordGroup.class})
	private String realName;
	
	/**
	 * 身份证号
	 */
	private String idNo;

	private String returnTrackingNo;//前端不用传值
	
	@NotNull(message="订单来源不能为空")
	@Range(min=1,max=2,message="非法订单来源")
	private Integer orderSource=1;//订单来源 1：APP,2:小程序
	
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

	public Date getApplyDateTime() {
		return applyDateTime;
	}

	public void setApplyDateTime(Date applyDateTime) {
		this.applyDateTime = applyDateTime;
		if (applyDateTime != null) {
			this.applyTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(applyDateTime);
		}
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

	public String getMaterielSpecValue() {
		return materielSpecValue;
	}

	public void setMaterielSpecValue(String materielSpecValue) {
		this.materielSpecValue = materielSpecValue;
	}

	public String getMaterielUnitName() {
		return materielUnitName;
	}

	public void setMaterielUnitName(String materielUnitName) {
		this.materielUnitName = materielUnitName;
	}

	public Integer getPickQuantity() {
		return pickQuantity;
	}

	public void setPickQuantity(Integer pickQuantity) {
		this.pickQuantity = pickQuantity;
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

	public Long getFillReceiptId() {
		return fillReceiptId;
	}

	public void setFillReceiptId(Long fillReceiptId) {
		this.fillReceiptId = fillReceiptId;
	}

	public String getFillReceiptName() {
		return fillReceiptName;
	}

	public void setFillReceiptName(String fillReceiptName) {
		this.fillReceiptName = fillReceiptName;
	}

	public Date getFillReceiptOn() {
		return fillReceiptOn;
	}

	public void setFillReceiptOn(Date fillReceiptOn) {
		this.fillReceiptOn = fillReceiptOn;
		if (fillReceiptOn != null) {
			this.fillReceiptOn_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fillReceiptOn);
		}
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Date getOperateOn() {
		return operateOn;
	}

	public void setOperateOn(Date operateOn) {
		this.operateOn = operateOn;
		if (operateOn != null) {
			this.operateOn_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operateOn);
		}
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
		if (AssertUtils.isPositiveNumber4Int(statusFlag)) {
			switch (statusFlag.intValue()) {
			case 8:
				this.statusFlag_s = "待发货";
				break;
			case 9:
				this.statusFlag_s = "已出库";
				break;
			default:
				this.statusFlag_s = "";
			}
		}
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getApplyTimes() {
		return applyTimes;
	}

	public void setApplyTimes(String applyTimes) {
		this.applyTimes = applyTimes;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getStatusFlag_s() {
		return statusFlag_s;
	}

	public void setStatusFlag_s(String statusFlag_s) {
		this.statusFlag_s = statusFlag_s;
	}

	public String getFillReceiptOn_s() {
		return fillReceiptOn_s;
	}

	public void setFillReceiptOn_s(String fillReceiptOn_s) {
		this.fillReceiptOn_s = fillReceiptOn_s;
	}

	public String getOperateOn_s() {
		return operateOn_s;
	}

	public void setOperateOn_s(String operateOn_s) {
		this.operateOn_s = operateOn_s;
	}

	public String getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getMonthlyCardNum() {
		return monthlyCardNum;
	}

	public void setMonthlyCardNum(String monthlyCardNum) {
		this.monthlyCardNum = monthlyCardNum;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getReturnTrackingNo() {
		return returnTrackingNo;
	}

	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
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
