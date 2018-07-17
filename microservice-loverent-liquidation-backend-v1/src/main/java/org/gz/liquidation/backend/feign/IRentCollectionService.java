package org.gz.liquidation.backend.feign;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.AccountRecordReq;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.AfterRentOrderDetailReq;
import org.gz.liquidation.common.dto.AfterRentOrderDetailResp;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.OrderRepaymentResp;
import org.gz.liquidation.common.dto.RemissionLogReq;
import org.gz.liquidation.common.dto.RemissionLogResp;
import org.gz.liquidation.common.dto.RevenueStatisticsResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:租后收款管理服务客户端接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月11日 	Created
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1",
	configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class,
	fallbackFactory = IliquidationServiceFallBackFactory.class)
public interface IRentCollectionService {
	
	/**
	 * 
	 * @Description: 查询租后清算记录
	 * @param accountRecordReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月11日
	 */
	@PostMapping(value = "/accountRecord/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> chargeOffList(AccountRecordReq accountRecordReq);
	/**
	 * 
	 * @Description: 收入统计
	 * @param type
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月11日
	 */
	@ApiOperation(value = "收入统计", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/rentCollection/revenueStatistics/{type}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RevenueStatisticsResp> queryRevenueStatistics(@PathVariable("type")String type);
	/**
	 * 
	 * @Description: 租后收款>>订单详情
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	@PostMapping(value = "/rentCollection/queryOrderDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<OrderRepaymentResp> queryOrderDetail(@PathVariable("orderSN") String orderSN);
	/**
	 * 
	 * @Description: 租后收款->订单详情->订单还款明细查询
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	@PostMapping(value = "/rentCollection/repmayment/detail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<AccountRecordResp>> queryRepmaymentDetail(@PathVariable("orderSN") String orderSN);
	/**
	 * 
	 * @Description: 租后订单列表分页查询
	 * @param afterRentOrderDetailReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/rentCollection/order/pageList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultPager<AfterRentOrderDetailResp> queryOrderList(@RequestBody AfterRentOrderDetailReq afterRentOrderDetailReq);
	/**
	 * 
	 * @Description: 手动清偿
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/rentCollection/manualSettle", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> manualSettle(@RequestBody ManualSettleReq manualSettleReq);
	/**
	 * 
	 * @Description: 减免滞纳金
	 * @param lateFeeRemissionReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/remissionLog/lateFee/remission", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doLateFeeRemission(@RequestBody LateFeeRemissionReq lateFeeRemissionReq);
	/**
	 * 
	 * @Description: 减免记录分页查询
	 * @param lateFeeRemissionReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	@PostMapping(value = "/remissionLog/queryList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResultPager<RemissionLogResp> queryList(@RequestBody RemissionLogReq remissionLogReq);
}

@Component
@Slf4j
class IliquidationServiceFallBackFactory implements FallbackFactory<IRentCollectionService> {@Override
	public IRentCollectionService create(Throwable arg0) {
		
	return new IRentCollectionService() {
			
			@Override
			public ResponseResult<RevenueStatisticsResp> queryRevenueStatistics(String type) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ResponseResult<List<AccountRecordResp>> queryRepmaymentDetail(String orderSN) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ResultPager<AfterRentOrderDetailResp> queryOrderList(AfterRentOrderDetailReq afterRentOrderDetailReq) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ResponseResult<OrderRepaymentResp> queryOrderDetail(String orderSN) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ResponseResult<String> manualSettle(ManualSettleReq manualSettleReq) {
				log.error("microservice-loverent-liquidation-service-v1,已回退:{}", arg0.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-service-v1 系统异常，已回退", null);
			}
			
			@Override
			public ResponseResult<?> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ResponseResult<List<AccountRecordResp>> chargeOffList(AccountRecordReq accountRecordReq) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ResultPager<RemissionLogResp> queryList(RemissionLogReq remissionLogReq) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
}
