/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:买断请求实体
 * @author hxj
 * @Description:
 * @date 2018年1月2日 上午9:13:01
 */
public class BuyEndVO extends BaseEntity {

	private static final long serialVersionUID = 8263570241784939183L;

	@NotNull(message = "订单号不能为null")
	@NotBlank(message = "订单号不能为空")
	private String sourceOrderNo;

	@NotNull(message = "物料ID不能为null")
	@Positive(message = "非法物料ID")
	private Long materielBasicId;// 物料ID

	@NotNull(message = "SN号不能为null")
	@NotBlank(message = "SN号不能为空")
	private String snNo;// SN号

	@NotNull(message = "IMIE号不能为null")
	@NotBlank(message = "IMIE号不能为空")
	private String imieNo;// IMIE号

	@NotNull(message = "产品ID不能为null")
	@Positive(message = "非法产品ID")
	private Long productId;// 产品ID

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
