package org.gz.user.service;

import org.gz.common.resp.ResponseResult;
import org.gz.user.entity.AddressInfo;
import org.gz.user.hystrix.AddressInfoServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="microservice-loverent-user-v1", fallback = AddressInfoServiceHystrixImpl.class)
public interface AddressInfoService {
	
	@RequestMapping("/address/queryAddressByPrimaryKey")
	public ResponseResult<AddressInfo> queryAddressByPrimaryKey(@RequestParam(name="addrId") Long addrId);
	
	@RequestMapping("/address/queryAddressByUserId")
	public ResponseResult<AddressInfo> queryAddressByUserId(@RequestParam(name="userId") Long userId);
	
	@RequestMapping("/address/updateAddressByPrimaryKey")
	public ResponseResult<String> updateAddressByPrimaryKey(@RequestBody AddressInfo addressInfo);
	
	@RequestMapping("/address/updateAddressByUserId")
	public ResponseResult<String> updateAddressByUserId(@RequestBody AddressInfo addressInfo);
	
	@RequestMapping("/address/removeAddressByPrimaryKey")
	public ResponseResult<String> removeAddressByPrimaryKey(@RequestParam(name="addrId") Long addrId);
	
	@RequestMapping("/address/removeAddressByUserId")
	public ResponseResult<String> removeAddressByUserId(@RequestParam(name="userId") Long userId);
	
	@RequestMapping("/address/insertAddress")
	public ResponseResult<String> insertAddress(@RequestBody AddressInfo addressInfo);
	
}
