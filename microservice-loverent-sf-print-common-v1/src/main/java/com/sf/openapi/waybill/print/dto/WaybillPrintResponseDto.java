package com.sf.openapi.waybill.print.dto;

import org.gz.common.entity.BaseEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class WaybillPrintResponseDto<T> extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final WaybillPrintResponseDto<String> SUCCESS = new WaybillPrintResponseDto<String>("EX_CODE_OPENAPI_0200","电子运单打印成功!");
	
	public static final WaybillPrintResponseDto<String> FAILED = new WaybillPrintResponseDto<String>("EX_CODE_OPENAPI_0500","电子运单打印失败!");
	
	private String code;

	private T result;

	public WaybillPrintResponseDto() {

	}

	public WaybillPrintResponseDto(String code, T result) {
		this.code = code;
		this.result = result;
	}

	public String getCode() {
		return this.code;
	}

	public T getResult() {
		return this.result;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setResult(T result) {
		this.result = result;
	}
}