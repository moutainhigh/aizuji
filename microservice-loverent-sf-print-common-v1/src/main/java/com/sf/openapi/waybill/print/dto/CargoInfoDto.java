/**
 * 
 */
package com.sf.openapi.waybill.print.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CargoInfoDto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "货物名称不能为空")
	@NotBlank(message = "货物名称不能为空")
	private String cargo;// 货物名称

	private Integer parcelQuantity;

	@NotNull(message = "货物数量不能为空")
	private Integer cargoCount=1;// 数量

	@NotNull(message = "货物单位不能为空")
	@NotBlank(message = "货物单位不能为空")
	private String cargoUnit;// 货物单位

	private BigDecimal cargoWeight;

	private BigDecimal cargoAmount;

	private BigDecimal cargoTotalWeight;

	@NotNull(message = "备注不能为空")
	@NotBlank(message = "备注不能为空")
	private String remark;// 备注

	private String sku;// 货物sku码

}