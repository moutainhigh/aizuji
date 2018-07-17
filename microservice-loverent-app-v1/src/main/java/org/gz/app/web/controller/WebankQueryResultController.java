/*package org.gz.app.web.controller;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.webank.service.WebankQueryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/webank/query")
@Slf4j
public class WebankQueryResultController extends AppBaseController {

	@Autowired
	private WebankQueryResultService webankQueryResultService;
	
	@RequestMapping(value="/fetchResultByUserIdFromWebankAndReturn/{userId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<String, Object>> fetchResultByUserIdFromWebankAndReturn(@PathVariable("userId") Long userId) {
		ResponseResult<Map<String, Object>> result = new ResponseResult<Map<String, Object>>();
		
		log.info("【人脸识别结果查询】 ===>>> start execute fetchResultFromWebankAndReturn, userId: {}", userId);
		
		Map<String, Object> data =  webankQueryResultService.fetchResultByUserIdFromWebankAndReturn(userId);
		
		log.info("【人脸识别结果查询】 ===>>> execute fetchResultFromWebankAndReturn success, userId: {}, data: {}", userId, JSONObject.toJSONString(data));
		
		result.setData(data);
		return result;
	}
	
	@RequestMapping(value="/fetchResultByTelFromWebankAndReturn/{tel}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<String, Object>> fetchResultByTelFromWebankAndReturn(@PathVariable("tel") String tel) {
		ResponseResult<Map<String, Object>> result = new ResponseResult<Map<String, Object>>();
		
		log.info("【人脸识别结果查询】 ===>>> start execute fetchResultByTelFromWebankAndReturn, tel: {}", tel);
		
		Map<String, Object> data =  webankQueryResultService.fetchResultByTelFromWebankAndReturn(tel);
		
		log.info("【人脸识别结果查询】 ===>>> execute fetchResultByTelFromWebankAndReturn success, tel: {}, data: {}", tel, JSONObject.toJSONString(data));
		
		result.setData(data);
		return result;
	}
	
}
*/