/**
 * 
 */
package com.sf.openapi.waybill.print.req;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

import com.sf.openapi.waybill.print.dto.SignConfirmDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月6日 下午4:21:45
 */
@Getter
@Setter
public class SignConfirmPrintRequest extends BaseEntity {

	private static final long serialVersionUID = 3757130038072578837L;

	private String templateType;
	
	@NotNull(message="参数不能为空")
	@Valid
	private List<SignConfirmDto> list;
}
