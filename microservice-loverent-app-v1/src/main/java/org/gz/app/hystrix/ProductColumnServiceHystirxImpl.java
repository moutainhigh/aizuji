package org.gz.app.hystrix;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.ProductColumnServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.oss.common.entity.ProductColumn;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductColumnServiceHystirxImpl implements ProductColumnServiceClient {

	@Override
	public ResponseResult<List<ProductColumn>> queryColumnList() {
		log.error("-----------------OSS服务不可用------------");
		ResponseResult<List<ProductColumn>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}


}
