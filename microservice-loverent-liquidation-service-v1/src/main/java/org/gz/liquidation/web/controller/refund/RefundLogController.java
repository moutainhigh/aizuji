package org.gz.liquidation.web.controller.refund;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RefundLogQueryReq;
import org.gz.liquidation.common.dto.RefundLogReq;
import org.gz.liquidation.common.dto.RefundLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.RefundLog;
import org.gz.liquidation.service.refund.RefundLogService;
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
 * @Description:TODO	退款记录控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月8日 	Created
 */
@Slf4j
@RestController
@RequestMapping("/refundLog")
public class RefundLogController extends BaseController {
	
	@Resource
	private RefundLogService refundLogService;
	
	@PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResultPager<RefundLogResp> queryPage(@RequestBody RefundLogQueryReq req){
		log.info(LiquidationConstant.LOG_PREFIX+"queryPage:{}",req);
		
		QueryDto queryDto = new QueryDto();
		Page page = new Page();
		page.setStart(req.getCurrPage());
		page.setPageSize(req.getPageSize());
		queryDto.setPage(page);
		queryDto.setQueryConditions(req);
		
		try {
			
			return refundLogService.selectPage(queryDto);
			
		} catch (Exception e) {
			log.error(LiquidationConstant.LOG_PREFIX+"查询退款记录异常：{}",e.getLocalizedMessage());
		}
		
		return new ResultPager<>();
	}
	/**
	 * 
	 * @Description: TODO 新增退款记录
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月8日
	 */
	@PostMapping(value = "/addRefundLog", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> addRefundLog(@RequestBody RefundLogReq refundLogReq){
		log.info(LiquidationConstant.LOG_PREFIX+"addRefundLog:{}",refundLogReq);
		
		RefundLog refundLog = BeanConvertUtil.convertBean(refundLogReq, RefundLog.class);
		
		try {
			refundLogService.insertSelective(refundLog);
		} catch (Exception e) {
			log.error(LiquidationConstant.LOG_PREFIX+"addRefundLog 新增退款记录异常:{}",e.getMessage());
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), 
					ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
		}
		
		return ResponseResult.buildSuccessResponse();
		
	}
	/**
	 * 
	 * @Description: 获取退款截图
	 * @param id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月20日
	 */
	@ApiOperation(value = "获取退款截图(清算后台调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/refundImg/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> getRefundImg(@PathVariable("id") Long id){
		log.info(LiquidationConstant.LOG_PREFIX+"getRefundImg:{}",id);
		
		try {
			String imgUrl = refundLogService.getImgUrl(id);
			return ResponseResult.buildSuccessResponse(imgUrl);
		} catch (Exception e) {
			log.error(LiquidationConstant.LOG_PREFIX+"getRefundImg 获取退款截图异常:{}",e.getMessage());
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), 
					ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
		}
		
	}
}
