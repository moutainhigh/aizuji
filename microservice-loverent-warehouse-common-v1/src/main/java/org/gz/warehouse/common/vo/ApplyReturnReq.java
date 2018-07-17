/**
 * 
 */
package org.gz.warehouse.common.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Range;

/**
 * @Title: 申请还机请求对象
 * @author hxj
 * @date 2018年3月8日 下午5:57:08
 */
public class ApplyReturnReq extends BaseEntity {

	private static final long serialVersionUID = -3643138023613180389L;

	@NotNull(message = "原始订单号不能为null")
	@NotBlank(message = "原始订单号不能为空")
	private String sourceOrderNo;// 原始订单号

	@NotNull(message = "源订单状态不能为null")
	@Positive(message = "非法源订单状态")
	private Integer sourceOrderStatus;//源订单状态
	
	@NotNull(message = "物料ID不能为null")
	@Positive(message = "非法物料ID")
	private Long materielBasicId;// 物料ID
	
	private String batchNo;//商品批次号

	@NotNull(message = "SN号不能为null")
	private String snNo;//SN号

	@NotNull(message = "IMIE号不能为null")
	private String imieNo;//IMIE号
	
	@NotNull(message = "还机申请时间不能为null")
	private Date returnApplyTime; // 还机申请时间
	
	private String logisticsNo;//物流单号
	
	@NotNull(message = "产品ID不能为null")
	@Positive(message = "非法产品ID")
	private Long productId;
	
	@NotNull(message = "产品类型不能为null")
	@Range(min=1,max=3,message="非法产品类型")
	private Integer productType;
	
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

	public Date getReturnApplyTime() {
		return returnApplyTime;
	}

	public void setReturnApplyTime(Date returnApplyTime) {
		this.returnApplyTime = returnApplyTime;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
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
	}
	
	
}
