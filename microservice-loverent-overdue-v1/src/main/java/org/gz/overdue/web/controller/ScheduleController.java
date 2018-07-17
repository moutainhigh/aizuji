package org.gz.overdue.web.controller;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.overdue.service.overdueOrder.OverdueOrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/schedule")
@Slf4j
public class ScheduleController {

	@Resource
    private OverdueOrderService overdueOrderService;
	
	 /**
	  * 获取滞纳金金额
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/orderSync", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> orderSync(){
		 log.info("/api/order/orderSync is begin.");
		 try {
			boolean flag = overdueOrderService.syncOrderData();
			log.info("/api/order/orderSync is end.flag:{}",flag);
			return ResponseResult.buildSuccessResponse(flag);
		 } catch (Exception e) {
			 return ResponseResult.build(9527, "定时任务同步失败", null);
		 }
	 }
	 
}
