package org.gz.app.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.user.entity.CardInfo;
import org.gz.user.service.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * app 首页
 * 
 * @author yangdx
 *
 */
@RestController
@RequestMapping("/card")
@Slf4j
public class BankCardController extends AppBaseController {

	@Autowired
	private CardInfoService cardInfoService;
	
	
	@RequestMapping(value="/testLog", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> testLog(@RequestBody CardInfo cardInfo) {
		log.info("start execute testLog, params: {}", "1");
		
		return null;
	}
	
	/**
	 * 添加
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addCard", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> addCard(@RequestBody CardInfo cardInfo) {
		log.info("start execute addCard, params: {}", JSONObject.toJSONString(cardInfo));
		
		ResponseResult<String> result = cardInfoService.addCard(cardInfo);
		
		log.info("addCard success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 根据主键ID修改
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateCard", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> updateCard(@RequestBody CardInfo cardInfo) {
		log.info("start execute updateCard, params: {}", JSONObject.toJSONString(cardInfo));
		
		ResponseResult<String> result = cardInfoService.updateCard(cardInfo);
		
		log.info("updateCard success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 根据用户ID修改
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateCardByUserId", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> updateCardByUserId(@RequestBody CardInfo cardInfo) {
		log.info("start execute updateCard, params: {}", JSONObject.toJSONString(cardInfo));
		
		ResponseResult<String> result = cardInfoService.updateCardByUserId(cardInfo);
		
		log.info("updateCard success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 查询
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loadCard", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<List<CardInfo>> loadCard(HttpServletRequest request) {
		log.info("start execute loadCard");
		
		List<String> list = getUserFields(request, "userId");
		
		ResponseResult<List<CardInfo>> result = cardInfoService.loadCard(Long.valueOf(list.get(0)));
		
		log.info("loadCard success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	
	@RequestMapping(value="/selectByCardNo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<CardInfo> selectByCardNo(@RequestBody JSONObject body, HttpServletRequest request) {
		log.info("start execute updateCard, params: {}", JSONObject.toJSONString(body));
		List<String> list = getUserFields(request, "userId");
		body.put("userId", Long.valueOf(list.get(0)));
		
		ResponseResult<CardInfo> result = cardInfoService.selectByCardNo(body);
		
		log.info("updateCard success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}

}
