package org.gz.app.hystrix;

import org.gz.app.feign.XcxOperationServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.stereotype.Component;

@Component
public class XcxOperationServiceHystrixImpl implements XcxOperationServiceClient{

	@Override
	public ResponseResult<?> queryBannerAllList() {
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> queryAllShopwindow() {
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> getShopwindowDetailById(Integer id) {
		return buildDefaultFallback();
	}

	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
}
