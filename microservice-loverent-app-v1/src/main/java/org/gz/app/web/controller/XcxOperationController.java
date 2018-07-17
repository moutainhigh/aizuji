package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.XcxOperationServiceClient;
import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/xcxOperation")
@Slf4j
public class XcxOperationController {

	@Autowired
	private XcxOperationServiceClient operationServiceClient;

	@RequestMapping(value="/queryBannerAllList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryBannerAllList() {
		log.info("start execute queryBannerAllList...");
		
		ResponseResult<?> result = operationServiceClient.queryBannerAllList();
		
		log.info("execute queryBannerAllList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	@RequestMapping(value="/queryAllShopwindow", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryAllShopwindow() {
		log.info("start execute queryAllShopwindow...");
		
		ResponseResult<?> result = operationServiceClient.queryAllShopwindow();
		
		log.info("execute queryAllShopwindow success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	@RequestMapping(value="/getShopwindowDetailById", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getShopwindowDetailById(@RequestBody JSONObject body) {
		log.info("start execute getShopwindowDetailById...");
		
		ResponseResult<?> result = operationServiceClient.getShopwindowDetailById(body.getInteger("id"));
		
		log.info("execute getShopwindowDetailById success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
