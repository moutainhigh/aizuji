package org.gz.app.feign;

import java.util.List;
import java.util.Map;

import org.gz.app.hystrix.OperationServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.MaterielModelQuery;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentProductReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 运营接口
 * 
 * @author yangdx
 *
 */
@FeignClient(value="microservice-loverent-oss-server-v1", fallback=OperationServiceHystrixImpl.class)
public interface OperationServiceClient {

	/**
	 * 获取产品规格信息
	 * @param list
	 * @return
	 */
	@PostMapping(value = "/shopping/getSpecValue")
    public ResponseResult<?> querySpecValue(@RequestParam("list") List<Map<String,Object>> list);
	
	/**
	 * 根据分类ID查询品牌列表
	 * @param classId
	 * @return
	 */
	@GetMapping(value = "/api/queryMaterielBrandListByClassId/{classId}")
    public ResponseResult<?> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId);
	
	/**
	 * 根据品牌ID查询型号列表
	 * @param materielModelQuery
	 * @return
	 */
	@PostMapping(value = "/api/queryMaterielModelPicListByBrandId")
    public ResponseResult<?> queryMaterielModelPicListByBrandId(@RequestBody MaterielModelQuery data);
	
	/**
	 * 首页橱窗位信息
	 * @return
	 */
	@PostMapping(value = "/api/shopwindow/findIndexInfo")
    public ResponseResult<?> findIndexInfo();
	
	/**
	 * 根据首页售卖商品的产品ID获取详细信息
	 * @param param
	 * @return
	 */
	@PostMapping("/api/shopwindow/getSaleCommodityById")
    public ResponseResult<?> getSaleCommodityById(@RequestBody ProductInfo param);
	
	/**
     * 根据品牌ID，型号ID，成色查询商品列表
     * @param vo
     * @return
     */
	@PostMapping(value = "/api/getSaleCommodityInfoByBrandIdAndModelIdAndConfigid")
    public ResponseResult<?> getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(@RequestBody RentProductReq vo);
}
