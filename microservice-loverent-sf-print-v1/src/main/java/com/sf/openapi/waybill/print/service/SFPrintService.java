/**
 * 
 */
package com.sf.openapi.waybill.print.service;

import org.gz.common.resp.ResponseResult;

import com.sf.openapi.waybill.print.req.SignConfirmPrintRequest;
import com.sf.openapi.waybill.print.req.WaybillPrintRequest;

/**
 * @Title: 打印服务
 * @author hxj
 * @date 2018年3月6日 上午11:41:32
 */
public interface SFPrintService {

	public ResponseResult<?> printWaybill(WaybillPrintRequest waybillReq);

	public ResponseResult<byte[]> exportSignConfirm(SignConfirmPrintRequest signConfirmPrintReq);
	
}
