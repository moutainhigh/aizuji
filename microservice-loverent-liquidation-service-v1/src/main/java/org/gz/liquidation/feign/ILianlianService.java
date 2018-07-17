package org.gz.liquidation.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.lianlian.LianlianOrderQueryResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 
 * @Description:连连支付 FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月3日 	Created
 */
@FeignClient(value = "microservice-loverent-thirdParty-v1")
public interface ILianlianService {
	
	/**
	 * 
	 * @Description: 订单交易状态查询
	 * @param orderNo
	 * @param orderTime
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月3日
	 */
	@RequestMapping("/lianlian/orderQuery")
	public ResponseResult<LianlianOrderQueryResp> queryorder(@RequestParam("orderNo")String orderNo,@RequestParam("orderTime")String orderTime);
}
