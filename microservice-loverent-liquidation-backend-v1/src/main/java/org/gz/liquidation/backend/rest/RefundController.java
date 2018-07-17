package org.gz.liquidation.backend.rest;

import java.util.Date;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.IRefundLogService;
import org.gz.liquidation.common.dto.RefundLogQueryReq;
import org.gz.liquidation.common.dto.RefundLogReq;
import org.gz.liquidation.common.dto.RefundLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	退款记录控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月7日 	Created
 */
@RestController
@RequestMapping("/refund")
@Slf4j
public class RefundController extends BaseController {
	
	@Autowired
	private IRefundLogService refundService;
	
	/**
	 * 
	 * @Description: 分页查询退款记录
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/page/list")
    public ResponseResult<?> RefundPageList(RefundLogQueryReq req) {
    	try {
    		
    		log.info(LiquidationConstant.LOG_PREFIX+"RefundPageList {}",JsonUtils.toJsonString(req));
    		
    		ResultPager<RefundLogResp> resultPager = refundService.queryPage(req);
    		return ResponseResult.buildSuccessResponse(resultPager);
    		
    	} catch (Exception e) {
    		
    		log.error(LiquidationConstant.LOG_PREFIX+"分页查询退款记录异常:{}",e.getLocalizedMessage());
    		return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
    	}
    }
	
	/**
	 * 
	 * @Description: 新增退款记录
	 * @param refundLogReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/addRefundLog")
    public ResponseResult<?> addRefundLog(RefundLogReq refundLogReq) {
		try {
    		
			log.info(LiquidationConstant.LOG_PREFIX+"RefundPageList {}",JsonUtils.toJsonString(refundLogReq));
			
			// 获取当前登录用户id和用户姓名 暂时写死
			refundLogReq.setCreateBy(1);
			refundLogReq.setOperator("管理员");
			refundLogReq.setCreateOn(new Date());
			return refundService.addRefundLog(refundLogReq);
    		
    	} catch (Exception e) {
    		
    		return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
    	}
	}
	
	@PostMapping(value = "/refundImg/{id}")
    public ResponseResult<String> getRefundImg(@PathVariable("id") Long id) {
		try {

			log.info(LiquidationConstant.LOG_PREFIX + "getRefundImg {}", id);
			return refundService.getRefundImg(id);

		} catch (Exception e) {

			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
					ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
		}
	}
}
