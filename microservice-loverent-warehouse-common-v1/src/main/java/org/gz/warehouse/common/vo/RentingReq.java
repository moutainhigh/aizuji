/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 在租请求实体
 * @author hxj
 * @Description: 
 * @date 2017年12月29日 下午3:36:56
 */
public class RentingReq extends BaseEntity {
	
	private static final long serialVersionUID = -8553348610665061185L;
	
	
	@NotNull(message="订单号不能为null")
	@NotBlank(message="订单号不能为空")
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
	
	
}
