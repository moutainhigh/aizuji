package org.gz.app.feign;

import org.gz.app.hystrix.XcxOperationServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=XcxOperationServiceHystrixImpl.class)
public interface XcxOperationServiceClient {

	/**
	 * 查询所有的Banner信息
	 * @return
	 */
	@GetMapping(value = "/miniapp/api/queryAllBanner")
    public ResponseResult<?> queryBannerAllList();
	
	/**
     * 查询所有的橱窗列表
     * @return
     */
    @GetMapping(value = "/miniapp/api/queryAllShopwindow")
    public ResponseResult<?> queryAllShopwindow();
	
	/**
	 * 根据橱窗iD查询橱窗详情信息
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/miniapp/api/getShopwindowDetailById")
    public ResponseResult<?> getShopwindowDetailById(@RequestParam("id") Integer id);
	
}
