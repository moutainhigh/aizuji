package org.gz.app.hystrix;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.BannerServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.oss.common.entity.Banner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BannerServiceHystirxImpl implements BannerServiceClient {

	@Override
	public ResponseResult<Map<String, List<Banner>>> queryAllList() {
		log.error("-----------------OSS服务不可用------------");
		ResponseResult<Map<String, List<Banner>>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> queryBannerList() {
		log.error("-----------------OSS服务不可用------------");
		ResponseResult<Map<String, List<Banner>>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
