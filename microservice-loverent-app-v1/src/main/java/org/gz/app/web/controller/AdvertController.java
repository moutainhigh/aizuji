package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.AdvertServiceClient;
import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/advert")
@Slf4j
public class AdvertController {
	
	@Autowired
	private AdvertServiceClient advertServiceClient;

	@RequestMapping(value="/queryAdverList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryAllMaterielClassList() {
		log.info("start execute queryAdverList...");
		
		ResponseResult<?> result = advertServiceClient.queryAdverList();
		
		log.info("execute queryAdverList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
