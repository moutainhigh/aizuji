package org.gz.app.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.CouponServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.CouponRelationQuery;
import org.gz.oss.common.entity.CouponUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController extends AppBaseController {

	@Autowired
	private CouponServiceClient couponServiceClient;

	/**
	 * 我的优惠券使用情况列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/queryCouponList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryCouponList(@RequestBody CouponRelationQuery crq, HttpServletRequest request) {
		log.info("start execute queryCouponList...");
		List<String> userFiles = getUserFields(request, "userId");
		Long userId = Long.valueOf(userFiles.get(0));
		
		crq.setUserId(userId);
		
		ResponseResult<?> result = couponServiceClient.queryCouponList(crq);
		
		log.info("execute queryCouponList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	@RequestMapping(value="/getUserCouponList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getUserCouponList(@RequestBody CouponUserQuery cuq, HttpServletRequest request) {
		log.info("start execute getUserCouponList...");
		List<String> userFiles = getUserFields(request, "userId");
		Long userId = Long.valueOf(userFiles.get(0));
		
		cuq.setUserId(userId);
		
		ResponseResult<?> result = couponServiceClient.getUserCouponList(cuq);
		
		log.info("execute getUserCouponList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	@RequestMapping(value="/queryCouponDetail", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryCouponDetail(@RequestBody CouponUserQuery cuq, HttpServletRequest request) {
		log.info("start execute queryCouponDetail...");
		List<String> userFiles = getUserFields(request, "userId");
		Long userId = Long.valueOf(userFiles.get(0));
		
		cuq.setUserId(userId);
		
		ResponseResult<?> result = couponServiceClient.queryCouponDetail(cuq);
		
		log.info("execute queryCouponDetail success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	@RequestMapping(value="/testAddCoupon", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> testAddCoupon(HttpServletRequest request) {
		log.info("start execute testAddCoupon...");
		List<String> userFiles = getUserFields(request, "userId");
		Long userId = Long.valueOf(userFiles.get(0));
		
		JSONObject body = new JSONObject();
		body.put("userId", userId);
		body.put("phoneNum", "18813579865");
		
		ResponseResult<?> result = couponServiceClient.useRegisterGiveCoupon(body);
		
		log.info("execute testAddCoupon success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
