package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.MaterielServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MaterielServiceHystrixImpl implements MaterielServiceClient {

	@Override
	public ResponseResult<?> queryAllMaterielClassList() {
		log.error("---> queryAllMaterielClassList failed, execute fall back...");
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> queryMaterielBrandListByClassId(Integer classId) {
		log.error("---> queryMaterielBrandListByClassId failed, execute fall back...");
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> queryMaterielModelListByBrandId(Integer brandId) {
		log.error("---> queryMaterielModelListByBrandId failed, execute fall back...");
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> queryMaterielSpecListByModelId(Integer modelId) {
		log.error("---> queryMaterielSpecListByModelId failed, execute fall back...");
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> getAllNewConfigs() {
		log.error("---> getAllNewConfigs failed, execute fall back...");
		return buildDefaultFallback();
	}
	
	@Override
	public ResponseResult<?> queryMaterielSpecPara(Long materielBasicInfoId) {
		log.error("---> queryMaterielSpecPara failed, execute fall back...");
		return buildDefaultFallback();
	}
	
	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
