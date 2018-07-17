package org.gz.user.service;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.entity.AddressInfo;
import org.gz.user.exception.UserServiceException;
import org.gz.user.service.atom.AddressInfoAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@Slf4j
public class AddressInfoServiceImpl implements AddressInfoService {
	
	@Autowired
	private AddressInfoAtomService addressInfoAtomService;
	
	@Override
	public ResponseResult<AddressInfo> queryAddressByUserId(@RequestParam(name="userId") Long userId) {
		log.info("start execute queryUserAddressByUserId");
		ResponseResult<AddressInfo> result = new ResponseResult<>();
		try {
			AddressInfo addressInfo = addressInfoAtomService.queryAddressByUserId(userId);
			result.setData(addressInfo);
		} catch (UserServiceException e) {
			 log.error("queryUserAddressByUserId failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("queryUserAddressByUserId failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<AddressInfo> queryAddressByPrimaryKey(@RequestParam(name="addrId") Long addrId) {
		log.info("start execute queryUserAddressById");
		ResponseResult<AddressInfo> result = new ResponseResult<>();
		try {
			AddressInfo addressInfo = addressInfoAtomService.queryAddressByPrimaryKey(addrId);
			result.setData(addressInfo);
		} catch (UserServiceException e) {
			 log.error("queryUserAddressById failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("queryUserAddressById failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> updateAddressByPrimaryKey(@RequestBody AddressInfo addressInfo) {
		log.info("start execute updateAddressByPrimaryKey");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			addressInfoAtomService.updateAddressByPrimaryKey(addressInfo);
		} catch (UserServiceException e) {
			 log.error("updateAddressByPrimaryKey failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("updateAddressByPrimaryKey failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> updateAddressByUserId(@RequestBody AddressInfo addressInfo) {
		log.info("start execute updateAddressByUserId, params: {}", JSONObject.toJSONString(addressInfo));
		ResponseResult<String> result = new ResponseResult<>();
		try {
			addressInfoAtomService.updateAddressByUserId(addressInfo);
		} catch (UserServiceException e) {
			 log.error("updateAddressByUserId failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("updateAddressByUserId failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> removeAddressByPrimaryKey(@RequestParam(name="addrId") Long addrId) {
		log.info("start execute removeAddressByPrimaryKey");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			addressInfoAtomService.removeAddressByPrimaryKey(addrId);
		} catch (UserServiceException e) {
			 log.error("removeAddressByPrimaryKey failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("removeAddressByPrimaryKey failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> removeAddressByUserId(Long userId) {
		log.info("start execute removeAddressByUserId");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			addressInfoAtomService.removeAddressByUserId(userId);
		} catch (UserServiceException e) {
			 log.error("removeAddressByUserId failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("removeAddressByUserId failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> insertAddress(@RequestBody AddressInfo addressInfo) {
		log.info("start execute insertAddress");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			addressInfoAtomService.insertAddress(addressInfo);
		} catch (UserServiceException e) {
			 log.error("insertAddress failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("insertAddress failed, body: {}", JSONObject.toJSONString(addressInfo));
			log.error("insertAddress failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}
	
}
