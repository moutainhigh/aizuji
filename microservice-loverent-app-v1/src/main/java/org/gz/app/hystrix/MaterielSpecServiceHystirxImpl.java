package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.MaterielSpecServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
@Slf4j
public class MaterielSpecServiceHystirxImpl implements MaterielSpecServiceClient {

	@Override
	public ResponseResult<?> queryAllSpecValueByModelId(Integer xx) {
		log.error("--------------物料服务不可用---------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> getSpecBatchNoBySpecInfo(JSONObject body) {
		log.error("---------------OSS服务不可用-----------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}


}
