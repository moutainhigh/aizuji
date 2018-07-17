/**
 * 
 */
package org.gz.warehouse.web.controller.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.Validator;

import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.common.vo.MaterielSpecParaResp;
import org.gz.warehouse.common.vo.ProductMaterielIntroReq;
import org.gz.warehouse.common.vo.ProductMaterielIntroResp;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.gz.warehouse.service.materiel.MaterielBasicInfoService;
import org.gz.warehouse.service.materiel.MaterielSpecParaService;
import org.gz.warehouse.service.warehouse.WarehouseCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterielCommodityController extends BaseController {

	
	@Autowired
	private WarehouseCommodityService warehouseCommodityService;
	
	@Autowired
	private MaterielSpecParaService materielSpecParaService;
	
	@Autowired
	private MaterielBasicInfoService materielBasicInfoService;

	@Autowired
	private Validator validator;
	
	/**
	 * 
	* @Description: 根据物料ID,仓库编码，仓位编码获取商品信息
	 */
	@PostMapping(value = "/api/materielCommodity/queryWarehouseLocationCommoditys")
    public ResponseResult<?> queryWarehouseLocationCommoditys(@Valid @RequestBody WarehouseCommodityReq q, BindingResult bindingResult) {
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			return this.warehouseCommodityService.queryWarehouseLocationCommoditys(q);
		}
		return validateResult;
    }
	
	/**
	 * 
	* @Description: 根据物料ID查询物料规格配置参数
	 */
	@GetMapping(value = "/api/materiel/queryMaterielSpecPara/{materielBasicInfoId}")
    public ResponseResult<?> queryMaterielSpecPara(@PathVariable("materielBasicInfoId") Long materielBasicInfoId) {
		if(AssertUtils.isPositiveNumber4Long(materielBasicInfoId)) {
			MaterielSpecParaResp resp = materielSpecParaService.queryMaterielSpecPara(materielBasicInfoId);	
			return ResponseResult.buildSuccessResponse(resp);
		}
		return ResponseResult.build(1000, "materielBasicInfoId参数不能为空", null);
    }
	
	/**
	 * 根据产品ID列表获取物料对应的图文介绍和主图地址
	* @Description: 规则：若产品为租赁，则获取相应主物料的图文介绍和附件图片，若产品为售卖，则取自身物料的图文介绍和附件图片
	* @param  q List<Long> productIds：产品ID列表
	* @return ResponseResult<List<ProductMaterielIntroResp>>
	 */
	@PostMapping(value = "/api/materiel/queryProductMaterielIntros")
	public ResponseResult<List<ProductMaterielIntroResp>> queryProductMaterielIntros(ProductMaterielIntroReq q){
		ResponseResult<String> vr = super.getValidatedResult(validator, q);
		if (vr == null) {
			return ResponseResult.buildSuccessResponse(this.materielBasicInfoService.queryProductMaterielIntros(q));
		}
		return ResponseResult.build(vr.getErrCode(), vr.getErrMsg(), null);
	}
}
