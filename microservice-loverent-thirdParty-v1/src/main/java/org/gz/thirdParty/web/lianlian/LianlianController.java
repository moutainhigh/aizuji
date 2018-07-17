package org.gz.thirdParty.web.lianlian;

import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.thirdParty.bean.ResultCode;
import org.gz.thirdParty.bean.lianlian.OrderQueryRespBean;
import org.gz.thirdParty.bean.lianlian.QuerySignRespBean;
import org.gz.thirdParty.bean.lianlian.UnsignRespBean;
import org.gz.thirdParty.service.lianlian.LianlianService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/lianlian")
@Slf4j
public class LianlianController {
	
	/**
	 * 连连订单状态查询接口
	 * @param orderNo 订单编号
	 * @param orderTime 订单时间
	 * @return
	 */
	@RequestMapping("/orderQuery")
	public ResponseResult<?> orderQuery(String orderNo,String orderTime){
		log.info("lianlian orderQuery is begin...orderNo:{}, orderTime:{}",orderNo,orderTime);
		OrderQueryRespBean resp = LianlianService.queryOrder(orderNo, orderTime);
		log.info("lianlian orderQuery is end...resp:{}",JsonUtils.toJsonString(resp));
		return ResponseResult.buildSuccessResponse(resp);
	}
	
	@RequestMapping("/querySign")
	public ResultCode querySign(String userId){
		log.info("lianlian querySign is begin...userId:{}",userId);
		QuerySignRespBean resp = LianlianService.querySign(userId);
		log.info("lianlian querySign is end...resp:{}",JsonUtils.toJsonString(resp));
		return new ResultCode(resp);
	}
	
	@RequestMapping("/unsign")
	public ResultCode unsign(String userId,String noAgree){
		log.info("lianlian unsign is begin...userId:{}",userId);
		UnsignRespBean resp = LianlianService.unsign(userId, noAgree);;
		log.info("lianlian unsign is end...resp:{}",JsonUtils.toJsonString(resp));
		return new ResultCode(resp);
	}
}
