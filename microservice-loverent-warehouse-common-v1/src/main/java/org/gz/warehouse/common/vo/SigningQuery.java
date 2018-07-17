/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 签约查询实体
 * @author hxj
 * @Description:
 * @date 2017年12月27日 上午9:34:37
 */
public class SigningQuery extends BaseEntity {

	private static final long serialVersionUID = 656021237871025397L;

	@NotNull(message = "原始订单号不能为null")
	@NotBlank(message = "原始订单号不能为空")
	private String sourceOrderNo;// 原始订单号

	@NotNull(message = "物料ID不能为null")
	@Positive(message = "非法物料ID")
	private Long materielBasicId;// 物料ID

	@NotNull(message = "产品ID不能为null")
	@Positive(message = "非法产品ID")
	private Long productId;// 产品ID

	private Integer productType;//内部字段，外部调用时不用传递
	
	private String batchNo;

	private String snNo;

	private String imieNo;

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
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
