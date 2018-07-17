package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.ProductServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

@Component
@Slf4j
public class ProductServiceHystirxImpl implements ProductServiceClient {

	@Override
	public ResponseResult<?> queryAllProductLeaseTerm(ProductInfoQvo vo) {
		log.error("-----------------物料服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<ProductInfo> getLeasePriceLowestProduct(JSONObject body) {
		log.error("-----------------物料服务不可用------------");
		ResponseResult<ProductInfo> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
	
	@Override
	public ResponseResult<?> queryModelList(JSONObject body) {
		log.error("-----------------物料服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
	
	@Override
	public ResponseResult<MaterielBasicInfoResp> queryMainMaterielInfo(
			JSONObject body) {
		log.error("-----------------物料服务不可用------------");
		ResponseResult<MaterielBasicInfoResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<MaterielBasicInfoResp> queryMainMaterielBasicIntroductionInfo(
			JSONObject body) {
		log.error("-----------------物料服务不可用------------");
		ResponseResult<MaterielBasicInfoResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<ProductInfo> getLeaseProductInfo(ProductInfoQvo qvo) {
		log.error("-----------------物料服务不可用------------");
		ResponseResult<ProductInfo> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
