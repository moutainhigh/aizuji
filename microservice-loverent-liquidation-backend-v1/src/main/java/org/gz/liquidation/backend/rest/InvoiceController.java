package org.gz.liquidation.backend.rest;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.InvoiceService;
import org.gz.liquidation.common.dto.invoice.InvoiceReq;
import org.gz.liquidation.common.dto.invoice.InvoiceResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @Description:发票控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {
	@Autowired
	private InvoiceService invoiceService;
	
	@PostMapping(value = "/query/page")
	public ResponseResult<ResultPager<InvoiceResp>> queryPageInvoice(InvoiceReq invoiceReq){
		return invoiceService.queryPageInvoice(invoiceReq);
	}
	
	@PostMapping(value = "/{orderSN}")
	public ResponseResult<?> invoice(@PathVariable("orderSN") String orderSN){
		return invoiceService.invoice(orderSN);
		
	}
	
}
