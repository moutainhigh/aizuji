package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.ShunFengServiceClient;
import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/shunfeng")
@Slf4j
public class ShunFengApiController {

	@Autowired
	private ShunFengServiceClient shunFengServiceClient;
	
	@RequestMapping(value="/routeQuery", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> routeQuery(@RequestBody JSONObject body) {
		//orderId=058207988267&type=1
		String orderId = body.getString("orderId");
		Integer type = body.getInteger("type");
		log.info("query shunfeng order info , orderId: {}, type: {}", orderId, type);
		System.out.println(JSONObject.toJSONString(body));
		ResponseResult<?> result = shunFengServiceClient.routeQuery(orderId, type);
		return result;
	}
}
