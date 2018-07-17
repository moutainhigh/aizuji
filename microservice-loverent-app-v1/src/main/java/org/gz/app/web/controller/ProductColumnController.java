package org.gz.app.web.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.ProductColumnServiceClient;
import org.gz.app.feign.ProductServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.StringUtils;
import org.gz.oss.common.entity.ProductColumn;
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
@RequestMapping("/productColumn")
@Slf4j
public class ProductColumnController extends AppBaseController {

	@Autowired
	private ProductColumnServiceClient productColumnServiceClient;
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	/**
	 * 获取首页产品栏目
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loadProductColumns", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<List<ProductColumn>> loadProduct() {
		log.info("start execute loadProduct");
		ResponseResult<List<ProductColumn>> result = productColumnServiceClient.queryColumnList();
		log.info("loadProduct success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
