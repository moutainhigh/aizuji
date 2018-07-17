package org.gz.app.web.controller;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.BannerServiceClient;
import org.gz.app.feign.ProductColumnServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/banner")
@Slf4j
public class BannerController extends AppBaseController {

	@Autowired
	private BannerServiceClient bannerServiceClient;
	
	@Autowired
	private ProductColumnServiceClient productServiceClient;
	
	/**
	 * 获取首页BANNER
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loadBanner", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<String, List<Banner>>> loadBanner() {
		log.info("start execute loadBanner");
		
		ResponseResult<Map<String, List<Banner>>> result = bannerServiceClient.queryAllList();
		
		log.info("loadBanner success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 获取首页BANNER-新版
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryBannerList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryBannerList() {
		log.info("start execute queryBannerList");
		
		ResponseResult<?> result = bannerServiceClient.queryBannerList();
		
		log.info("queryBannerList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}

}
