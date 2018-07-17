package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.RecommendServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.dto.RecommendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/recommend")
@Slf4j
public class RecommendController {

	@Autowired
	private RecommendServiceClient recommendServiceClient;
	
	/**
	 * 获取推荐位信息列表
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRecommendList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryRecommendList(@RequestBody RecommendDto dto) {
		log.info("start execute queryRecommendList");
		
		ResponseResult<?> result = recommendServiceClient.queryRecommendList(dto);
		
		log.info("queryRecommendList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 获取推荐位信息列表
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryProductWithCommodityListByRecommendInfo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryProductWithCommodityListByRecommendInfo(@RequestBody RecommendDto dto) {
		log.info("start execute queryProductWithCommodityListByRecommendInfo");
		
		ResponseResult<?> result = recommendServiceClient.queryProductWithCommodityListByRecommendInfo(dto);
		
		log.info("queryProductWithCommodityListByRecommendInfo success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
