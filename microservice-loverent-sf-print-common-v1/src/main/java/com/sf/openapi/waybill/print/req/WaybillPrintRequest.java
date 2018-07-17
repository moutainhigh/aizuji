/**
 * 
 */
package com.sf.openapi.waybill.print.req;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.gz.common.entity.BaseEntity;

import com.sf.openapi.waybill.print.dto.WaybillDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WaybillPrintRequest extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message="模板类型不能为空")
	@NotBlank(message="模板类型不能为空")
	@Pattern(regexp = "poster_100mm150mm|V3_poster_100mm210mm")
	private String type;

	@NotNull(message="输出类型不能为空")
	@NotBlank(message="输出类型不能为空")
	@Pattern(regexp = "print|noAlertPrint|image")
	private String output;

	@NotNull(message="运单列表不能为空")
	@Valid
	private List<WaybillDto> waybillList;

}
