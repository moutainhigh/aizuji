package org.gz.app.web.controller;

import org.gz.app.feign.OperationServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.MaterielModelQuery;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 运营接口对接
 * 
 * @author yangdx
 *
 */
@RestController
@RequestMapping("/operation")
@Slf4j
public class OperationController {
	
	@Autowired
	private OperationServiceClient operationServiceClient;
	
	@RequestMapping(value="/querySpecValue", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> querySpecValue() {
		log.info("start execute querySpecValue...");
		
		ResponseResult<?> result = operationServiceClient.querySpecValue(null);
		
		log.info("execute querySpecValue success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 根据分类ID查询品牌列表
	 * @param classId
	 * @return
	 */
	@RequestMapping(value="/queryMaterielBrandListByClassId/{classId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId) {
		log.info("start execute queryMaterielBrandListByClassId...");
		
		ResponseResult<?> result = operationServiceClient.queryMaterielBrandListByClassId(classId);
		
		log.info("execute queryMaterielBrandListByClassId success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 根据品牌ID查询型号列表
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/queryMaterielModelPicListByBrandId", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryMaterielBrandListByClassId(@RequestBody MaterielModelQuery data) {
		log.info("start execute queryMaterielBrandListByClassId...");
		
		ResponseResult<?> result = operationServiceClient.queryMaterielModelPicListByBrandId(data);
		
		log.info("execute queryMaterielBrandListByClassId success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 查询首页三个橱窗位信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/findIndexInfo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> findShopwindowList() {
		log.info("start execute findIndexInfo...");
		
		ResponseResult<?> result = operationServiceClient.findIndexInfo();
		
		log.info("execute findIndexInfo success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 根据产品ID获取售卖商品详情
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/getSaleCommodityById", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getSaleCommodityById(@RequestBody ProductInfo param) {
		log.info("start execute getSaleCommodityById...");
		
		ResponseResult<?> result = operationServiceClient.getSaleCommodityById(param);
		
		log.info("execute getSaleCommodityById success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
     * 根据品牌ID，型号ID，成色查询商品列表
     * @param vo
     * @return
     */
	@RequestMapping(value="/getSaleCommodityInfoByBrandIdAndModelIdAndConfigid", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(@RequestBody RentProductReq vo) {
		log.info("start execute getSaleCommodityInfoByBrandIdAndModelIdAndConfigid...");
		
		ResponseResult<?> result = operationServiceClient.getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(vo);
		
		log.info("execute getSaleCommodityInfoByBrandIdAndModelIdAndConfigid success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
