package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.ProductServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
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
@RestController(value="appProductController")
@RequestMapping("/product")
@Slf4j
public class ProductController extends AppBaseController {

	@Autowired
	private ProductServiceClient productServiceClient;
	
	/**
	 * 获取产品租期列表
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryAllProductLeaseTerm", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryAllProductLeaseTerm(@RequestBody JSONObject body) {
		Integer materielModelId = body.getInteger("materielModelId");
		ProductInfoQvo vo = new ProductInfoQvo();
		vo.setMaterielModelId(materielModelId);
		ResponseResult<?> result = productServiceClient.queryAllProductLeaseTerm(vo);
		
		return result;
	}
	
	/**
	 * 获取产品租期列表
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value="/getSelectProduct", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> getLeasePriceLowestProduct(@RequestBody JSONObject body) {
		ResponseResult<?> result = productServiceClient.getLeasePriceLowestProduct(body);
		
		return result;
	}
	
	/**
	 * 查询产品信息
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryProductInfo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryProductInfo(@RequestBody JSONObject body) {
		log.info("--->execute queryProductInfo, params: {}", body.toJSONString());
		ResponseResult<JSONObject> rst = new ResponseResult<>();
		
		JSONObject json = new JSONObject();	
		log.info("--->queryProductInfo, start execute queryProductInfo...");
		ResponseResult<MaterielBasicInfoResp> result = productServiceClient.queryMainMaterielInfo(body);
		log.info("--->queryProductInfo, execute queryProductInfo success, result: {}", JSONObject.toJSONString(result));
		MaterielBasicInfoResp resp = result.getData();
		if (resp != null) {
//			body.put("materielId", resp.getId());
			
			log.info("--->queryProductInfo, start execute getLeasePriceLowestProduct, body: {}", body.toJSONString());
//			ResponseResult<ProductInfo> result2 = productServiceClient.getLeasePriceLowestProduct(body);
			
			ProductInfoQvo qvo = new ProductInfoQvo();
			qvo.setMaterielModelId(body.getInteger("materielModelId"));
			qvo.setMaterielId(resp.getId());
			ResponseResult<ProductInfo> result2 = productServiceClient.getLeaseProductInfo(qvo);
			log.info("--->queryProductInfo, execute getLeasePriceLowestProduct success, result2: {}", JSONObject.toJSONString(result2));
			json.put("productData", result2.getData());
		} else {
			json.put("productData", null);
		}
		
		json.put("materieData", resp);
		
		rst.setData(json);
		
		return rst;
	}
	
	/**
	 * 查询产品图文详情信息
	 * @param addOrderReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryProductGraphicInfo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> queryProductGraphicInfo(@RequestBody JSONObject body) {
		
		ResponseResult<MaterielBasicInfoResp> result = productServiceClient.queryMainMaterielBasicIntroductionInfo(body);
			
		return result;
	}
}
