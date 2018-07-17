package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.MaterielSpecServiceClient;
import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 物料规格
 * 
 * @author yangdx
 *
 */
@RestController(value="appMaterielSpecController")
@RequestMapping("/materielSpec")
@Slf4j
public class MaterielSpecController extends AppBaseController {

	@Autowired
	private MaterielSpecServiceClient materielSpecServiceClient;
	
	/**
	 * 根据物料型号id查询所有规格列表
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryAllSpecValueByModelId", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryAllSpecValueByModelId(@RequestBody JSONObject body) {
		Integer modelId = body.getInteger("modelId");
		ResponseResult<?> result = materielSpecServiceClient.queryAllSpecValueByModelId(modelId);
		
		return result;
	}
	
	/**
	 * 根据规格信息查询规格批次号
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSpecBatchNoBySpecInfo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getSpecBatchNoBySpecInfo(@RequestBody JSONObject body) {
		ResponseResult<?> result = materielSpecServiceClient.getSpecBatchNoBySpecInfo(body);
		return result;
	}
	
}
