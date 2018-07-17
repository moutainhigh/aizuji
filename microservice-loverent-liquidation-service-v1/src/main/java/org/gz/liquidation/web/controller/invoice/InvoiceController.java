package org.gz.liquidation.web.controller.invoice;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.dto.invoice.InvoiceResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.order.OrderService;
import org.gz.order.common.dto.QueryInvoiceReq;
import org.gz.order.common.dto.QueryInvoiceRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:发票控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
@RestController
@RequestMapping("/invoice")
@Slf4j
public class InvoiceController extends BaseController{
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 
	 * @Description: 分页查询开票信息
	 * @param queryInvoiceReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月30日
	 */
	@ApiOperation(value = "分页查询开票信息(清算后台系统调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/query/page", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<InvoiceResp>> queryPageInvoice(@RequestBody QueryInvoiceReq queryInvoiceReq){
		log.info(LiquidationConstant.LOG_PREFIX+"queryPageInvoice:{}",JsonUtils.toJsonString(queryInvoiceReq));
		ResponseResult<ResultPager<QueryInvoiceRsp>> responseResult = orderService.queryPageInvoice(queryInvoiceReq);
		ResultPager<InvoiceResp> rp = new ResultPager<>();
		if(responseResult.isSuccess()){
			ResultPager<QueryInvoiceRsp> resultPager = responseResult.getData();
			List<QueryInvoiceRsp> sourceList = resultPager.getData();
			List<InvoiceResp> dataList = BeanConvertUtil.convertBeanList(sourceList, InvoiceResp.class);
			
			rp = new ResultPager<>(resultPager.getTotalNum(), resultPager.getCurrPage(), resultPager.getPageSize(), dataList);
		}
		return ResponseResult.buildSuccessResponse(rp);
		
	}
	@ApiOperation(value = "开票(清算后台系统调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> invoice(@PathVariable("orderSN") String orderSN){
		log.info(LiquidationConstant.LOG_PREFIX+"invoice:{}",orderSN);
		ResponseResult<?> responseResult = orderService.invoiceEnd(orderSN);
		log.info(LiquidationConstant.LOG_PREFIX+"invoice 调用订单服务开票返回结果:{}",JsonUtils.toJsonString(responseResult));
		return responseResult;
		
	}
}
