package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.MaterielServiceClient;
import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/materiel")
@Slf4j
public class MaterielController extends AppBaseController {

	@Autowired
	private MaterielServiceClient materielServiceClient;
	
	/**
     * 获取所有物料分类列表  树状返回
     * @param m
     * @return
     */
	@RequestMapping(value="/queryAllMaterielClassList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryAllMaterielClassList() {
		log.info("start execute queryAllMaterielClassList...");
		
		ResponseResult<?> result = materielServiceClient.queryAllMaterielClassList();
		
		log.info("execute queryAllMaterielClassList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
     * 通过物料分类id查询对应的品牌列表
     * @param classId
     * @return
     */
	@RequestMapping(value="/queryMaterielBrandListByClassId/{classId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId) {
		log.info("start execute queryMaterielBrandListByClassId...");
		
		ResponseResult<?> result = materielServiceClient.queryMaterielBrandListByClassId(classId);
		
		log.info("execute queryMaterielBrandListByClassId success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
     * 根据物料品牌id查询所有型号列表
     * @param m
     * @return
     */
	@RequestMapping(value="/queryMaterielModelListByBrandId/{brandId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryMaterielModelListByBrandId(@PathVariable("brandId") Integer brandId) {
		log.info("start execute queryMaterielModelListByBrandId...");
		
		ResponseResult<?> result = materielServiceClient.queryMaterielModelListByBrandId(brandId);
		
		log.info("execute queryMaterielModelListByBrandId success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}

	/**
     * 根据物料型号id查询所有规格列表
     * @param m
     * @return
     */
	@RequestMapping(value="/queryMaterielSpecListByModelId/{modelId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryMaterielSpecListByModelId(@PathVariable("modelId") Integer modelId) {
		log.info("start execute queryMaterielSpecListByModelId...");
		
		ResponseResult<?> result = materielServiceClient.queryMaterielSpecListByModelId(modelId);
		
		log.info("execute queryMaterielSpecListByModelId success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 获取所有新旧配置
	 */
	@RequestMapping(value="/getAllNewConfigs", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getAllNewConfigs() {
		log.info("start execute getAllNewConfigs...");
		
		ResponseResult<?> result = materielServiceClient.getAllNewConfigs();
		
		log.info("execute getAllNewConfigs success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 根据物料ID查询物料规格配置参数
	 */
	@RequestMapping(value="/queryMaterielSpecPara/{materielBasicInfoId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryMaterielSpecPara(@PathVariable("materielBasicInfoId") Long materielBasicInfoId) {
		log.info("start execute queryMaterielSpecPara...");
		
		ResponseResult<?> result = materielServiceClient.queryMaterielSpecPara(materielBasicInfoId);
		
		log.info("execute queryMaterielSpecPara success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
