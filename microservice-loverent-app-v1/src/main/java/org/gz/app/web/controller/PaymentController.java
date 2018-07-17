package org.gz.app.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.PaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.TransactionRecordUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController extends AppBaseController {

	@Autowired
	private PaymentServiceClient paymentServiceClient;
	
	@RequestMapping(value="/cancalPayment", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> updateState(@RequestBody TransactionRecordUpdateReq req, HttpServletRequest request) {
		log.info("start execute cancalPayment, params: {}", JSONObject.toJSONString(req));
		List<String> list = getUserFields(request, "phoneNum");
		req.setUpdateUsername(list.get(0));
		req.setState(4);
		
		ResponseResult<String> result = paymentServiceClient.updateState(req);
		
		log.info("cancalPayment success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	} 
	
}
