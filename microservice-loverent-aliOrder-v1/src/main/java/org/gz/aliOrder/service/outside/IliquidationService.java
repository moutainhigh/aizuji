package org.gz.aliOrder.service.outside;

import org.gz.aliOrder.config.FeignFullConfig;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client
 * interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1", configuration = FeignFullConfig.class, fallbackFactory = IliquidationServiceFallFactory.class)
public interface IliquidationService {

	/**
	 * @Description: 签约
	 * @param repaymentScheduleReq
	 * @param
	 * @return
	 * @throws createBy:liaoqingji
	 *             createDate:2017年12月23日
	 */
	@PostMapping(value = "/sign/doSign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doSign(@RequestBody RepaymentScheduleReq repaymentScheduleReq);

	/**
	 * @Description: 订单是否结清
	 * @param orderSN
	 * @return true 是 false 否
	 * @throws createBy:liaoqingji
	 *             createDate:2018年1月2日
	 */
	@PostMapping(value = "/repayment/{orderSN}/state", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Boolean> repaymentState(@PathVariable("orderSN") String orderSN);

	/**
	 * @Description: 查询订单还款详情
	 * @param orderSN
	 *            订单号
	 * @return
	 * @throws createBy:liaoqingji
	 *             createDate:2018年1月2日
	 */
	@PostMapping(value = "/repayment/{orderSN}/detail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RepaymentDetailResp> repaymentDetail(@PathVariable("orderSN") String orderSN);

	/**
	 * 验证是否可以进行交易
	 * 
	 * @param orderSN
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2018年2月2日
	 */
	@PostMapping(value = "/transactionRecord/state/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Boolean> queryTradeState(@PathVariable("orderSN") String orderSN);

	/**
	 * 成芝麻订单还款计划(订单服务调用)
	 * 
	 * @param req
	 * @return
	 * @throws
	 * createBy:临时工          
	 * createDate:2018年3月30日
	 */
@PostMapping(value = "/repayment/add/zmRepaymentSchedule", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public ResponseResult<?> addZmRepaymentSchedule(@RequestBody ZmRepaymentScheduleReq req);
	
}

@Component
@Slf4j
class IliquidationServiceFallFactory implements FallbackFactory<IliquidationService> {
	@Override
	public IliquidationService create(Throwable cause) {
		return new IliquidationService() {

			@Override
			public ResponseResult<?> doSign(RepaymentScheduleReq repaymentScheduleReq) {
				log.error("microservice-loverent-liquidation-api-v1 doSign系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

			@Override
			public ResponseResult<Boolean> repaymentState(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 repaymentState系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

			@Override
			public ResponseResult<RepaymentDetailResp> repaymentDetail(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 repaymentDetail系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

			@Override
			public ResponseResult<Boolean> queryTradeState(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 queryTradeState系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

      @Override
      public ResponseResult<?> addZmRepaymentSchedule(ZmRepaymentScheduleReq req) {
        log.error("microservice-loverent-liquidation-api-v1 addZmRepaymentSchedule系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
      }
		};
	}

}
