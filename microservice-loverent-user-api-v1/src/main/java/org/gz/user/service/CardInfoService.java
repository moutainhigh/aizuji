package org.gz.user.service;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.user.entity.CardInfo;
import org.gz.user.hystrix.CardInfoServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@FeignClient(name="microservice-loverent-user-v1", fallback = CardInfoServiceHystrixImpl.class)
public interface CardInfoService {

	@RequestMapping("/card/addCard")
	public ResponseResult<String> addCard(@RequestBody CardInfo cardInfo);
	
	@RequestMapping("/card/updateCard")
	public ResponseResult<String> updateCard(@RequestBody CardInfo cardInfo);
	
	@RequestMapping("/card/updateCardByUserId")
	public ResponseResult<String> updateCardByUserId(@RequestBody CardInfo cardInfo);
	
	@RequestMapping("/card/loadCard")
	public ResponseResult<List<CardInfo>> loadCard(@RequestParam("userId") Long userId);

	@RequestMapping("/card/selectByCardNo")
	public ResponseResult<CardInfo> selectByCardNo(@RequestBody JSONObject body);

	@RequestMapping("/card/addCardIfNotExist")
	public ResponseResult<String> addCardIfNotExist(@RequestBody CardInfo cardInfo);
}
