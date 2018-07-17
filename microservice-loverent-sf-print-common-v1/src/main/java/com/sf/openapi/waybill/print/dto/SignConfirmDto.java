/**
 * 
 */
package com.sf.openapi.waybill.print.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月6日 下午6:17:04
 */
@Getter
@Setter
public class SignConfirmDto extends BaseEntity {


	private static final long serialVersionUID = -8686457869756626908L;

	@NotNull(message="客户姓名不能为null")
	@NotBlank(message="客户姓名不能为null")
	private String cName;//客户姓名

	@NotNull(message="客户身份证号不能为null")
	@NotBlank(message="客户身份证号不能为null")
	private String idCard;//客户身份证号

	@NotNull(message="手机型号不能为null")
	@NotBlank(message="手机型号不能为null")
	private String modelName;//手机型号

	@NotNull(message="订单号不能为null")
	@NotBlank(message="订单号不能为null")
	private String orderNo;//订单号

	@NotNull(message="手机标识不能为null")
	@NotBlank(message="手机标识不能为null")
	private String mobileMark;//手机标识：SN与IMEI
}
