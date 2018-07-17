package org.gz.app.hystrix;

import java.util.List;
import java.util.Map;

import org.gz.app.feign.OperationServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.oss.common.entity.MaterielModelQuery;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentProductReq;
import org.springframework.stereotype.Component;

@Component
public class OperationServiceHystrixImpl implements OperationServiceClient {

	@Override
	public ResponseResult<?> querySpecValue(List<Map<String, Object>> list) {
		return buildDefaultFallback();
	}
	
	@Override
	public ResponseResult<?> queryMaterielBrandListByClassId(Integer classId) {
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> findIndexInfo() {
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> getSaleCommodityById(ProductInfo param) {
		return buildDefaultFallback();
	}
	
	@Override
	public ResponseResult<?> queryMaterielModelPicListByBrandId(MaterielModelQuery data) {
		return buildDefaultFallback();
	}
	
	@Override
	public ResponseResult<?> getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(
			RentProductReq vo) {
		return buildDefaultFallback();
	}
	
	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
