/**
 * 
 */
package com.sf.openapi.waybill.print.controller;

import javax.validation.Valid;

import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.openapi.waybill.print.req.SignConfirmPrintRequest;
import com.sf.openapi.waybill.print.req.WaybillPrintRequest;
import com.sf.openapi.waybill.print.service.SFPrintService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年2月28日 下午2:05:22
 */
@RestController
@RequestMapping("/sf")
@Slf4j
public class WaybillPrintController extends BaseController {

	@Autowired
	private  SFPrintService  printService;
	
	@PostMapping("/waybill/print")
	public ResponseResult<?> printWaybill(@Valid @RequestBody WaybillPrintRequest waybillReq, BindingResult bindingResult) {
		log.info("print parameter:"+waybillReq.toString());
		// 验证数据
		ResponseResult<?> result = super.getValidatedResult(bindingResult);
		System.err.println("validate result:"+result);
		if (result == null) {
			result = this.printService.printWaybill(waybillReq);
		}
		return result;
	}
	
	@PostMapping("/signConfirm/export")
	public ResponseResult<byte[]> exportSignConfirm(@Valid @RequestBody SignConfirmPrintRequest signConfirmPrintReq, BindingResult bindingResult) {
		log.info("print parameter:"+signConfirmPrintReq.toString());
		// 验证数据
		ResponseResult<?> result = super.getValidatedResult(bindingResult);
		if (result == null) {
			return this.printService.exportSignConfirm(signConfirmPrintReq);
		}
		return ResponseResult.build(1000, result.getErrMsg(), null);
	}
	
}
