package org.gz.order.backend.feign;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.RepaymentStatisticsReq;
import org.gz.liquidation.common.dto.RepaymentStatisticsResp;
import org.gz.liquidation.common.dto.SaleOrderRepaymentInfoResp;
import org.gz.liquidation.common.dto.coupon.CouponReturnReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.gz.order.backend.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@FeignClient(value = "microservice-loverent-liquidation-service-v1", configuration = FeignFullConfig.class, fallbackFactory = IliquidationServiceFallBackFactory.class)
public interface IliquidationService {

    @RequestMapping(value = "/payment/{orderSN}/downPayment", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)  
  public ResponseResult<Boolean> queryDownPayment(@PathVariable("orderSN") String orderSN);

    @PostMapping(value = "/repayment/repaymentStatistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<RepaymentStatisticsResp>> queryRepaymentStatistics(@RequestBody RepaymentStatisticsReq repaymentStatisticsReq);
    
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
	 * 查询售卖订单销售金和保障金
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/accountRecord/querySaleOrderInfo/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<SaleOrderRepaymentInfoResp> querySaleOrderInfo(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 退回优惠券
	 * 
	 * @param couponReturnReq
	 * @return
	 * @throws
	 * createBy:临时工          
	 * createDate:2018年4月5日
	 */
	 @PostMapping(value = "/returnCoupon", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<?> returnCoupon(@RequestBody CouponReturnReq couponReturnReq);

}
    
@Component
@Slf4j
class IliquidationServiceFallBackFactory implements FallbackFactory<IliquidationService> {
	@Override
	public IliquidationService create(Throwable cause) {
		return new IliquidationService() {
			
			@Override
			public ResponseResult<Boolean> queryDownPayment(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
		    	return ResponseResult.build(1000, "microservice-loverent-liquidation-v1系统异常，已回退", null);
		    }
			@Override
		    public ResponseResult<List<RepaymentStatisticsResp>> queryRepaymentStatistics(RepaymentStatisticsReq repaymentStatisticsReq) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
		        return ResponseResult.build(1000, "microservice-loverent-liquidation-v1系统异常，已回退", null);
		    }
			@Override
			public ResponseResult<?> doSign(RepaymentScheduleReq repaymentScheduleReq) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

			@Override
			public ResponseResult<Boolean> repaymentState(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

			@Override
			public ResponseResult<RepaymentDetailResp> repaymentDetail(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}

			@Override
			public ResponseResult<Boolean> queryTradeState(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}
			
			@Override
			public ResponseResult<SaleOrderRepaymentInfoResp> querySaleOrderInfo(String orderSN) {
				log.error("microservice-loverent-liquidation-api-v1 系统异常，已回退:{}", cause.getMessage());
				return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
			}
			
			@Override
		      public ResponseResult<?> returnCoupon(CouponReturnReq couponReturnReq) {
		        log.error("microservice-loverent-liquidation-api-v1  returnCoupon系统异常，已回退:{}", cause.getMessage());
		        return ResponseResult.build(1000, "microservice-loverent-liquidation-api-v1 系统异常，已回退", null);
		    }
		};
	}

}
