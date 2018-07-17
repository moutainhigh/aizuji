package org.gz.user.hystrix;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.entity.AddressInfo;
import org.gz.user.service.AddressInfoService;
import org.springframework.stereotype.Component;

/**
 * hystrix impl
 * 
 * @author yangdx
 *
 */
@Component
public class AddressInfoServiceHystrixImpl implements AddressInfoService {

	@Override
	public ResponseResult<AddressInfo> queryAddressByUserId(Long userId) {
		ResponseResult<AddressInfo> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<AddressInfo> queryAddressByPrimaryKey(Long addrId) {
		ResponseResult<AddressInfo> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> updateAddressByPrimaryKey(AddressInfo addressInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
	
	@Override
	public ResponseResult<String> updateAddressByUserId(AddressInfo addressInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> removeAddressByPrimaryKey(Long addrId) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> removeAddressByUserId(Long userId) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> insertAddress(AddressInfo addressInfo) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
