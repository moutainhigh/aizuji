package org.gz.aliOrder.hystrix;

import java.util.List;
import java.util.Map;

import org.gz.aliOrder.config.FeignFullConfig;
import org.gz.aliOrder.dto.AddOrderReq;
import org.gz.aliOrder.dto.AddOrderResp;
import org.gz.aliOrder.dto.OrderDetailReq;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.OrderDetailRespForWare;
import org.gz.aliOrder.dto.RentRecordQuery;
import org.gz.aliOrder.dto.SubmitOrderReq;
import org.gz.aliOrder.dto.UpdateCreditAmountReq;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.entity.RentLogistics;
import org.gz.aliOrder.entity.RentState;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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
@FeignClient(value = "microservice-loverent-aliOrder-v1", configuration = FeignFullConfig.class, fallbackFactory = IAliOrderServiceFallBackFactory.class)
public interface IAliOrderService {
  /**
   * 添加订单接口
   * 
   * @param AddOrderReq
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月26日
   */
  @PostMapping(value = "/api/aliOrder/addOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<AddOrderResp> addOrder(@RequestBody AddOrderReq addOrderRequest) ;
  
  /**
   * 订单创建成功异步通知更新信用免押积分和芝麻单号
   * 
   * @param UpdateCreditAmountReq
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月26日
   */
  @PostMapping(value = "/api/aliOrder/updateCreditAmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> updateCreditAmount(@RequestBody UpdateCreditAmountReq updateCreditAmountReq); 
  
  /**
   * 确认提交订单将订单状态改为待支付
   * 
   * @param submitOrderReq
   * @return
   * @throws
   * createBy:临时工          
   * createDate:2018年3月28日
   */
  @PostMapping(value = "/api/aliOrder/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> submitOrder(@RequestBody SubmitOrderReq submitOrderReq) ;
  
  /**
   * 取消订单
   * 
   * @param rentRecordNo
   * @return
   * @throws
   * createBy:临时工          
   * createDate:2018年3月28日
   */
  @PostMapping(value = "/api/aliOrder/cancleOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> cancleOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) ;
  
  /**
   * 确认签收
   * 
   * @param updateOrderStateReq
   * @return
   * @throws
   * createBy:临时工          
   * createDate:2018年3月28日
   */
  @PostMapping(value = "/api/aliOrder/signedOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> signedOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) ;
  
  /**更新订单状态
   * @description 库存系统发起更新为待发货 传入状态 BackRentState.WaitSend.getCode()
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  @PostMapping(value = "/api/aliOrder/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> updateOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) ;
  
  /**
   * @description 库存系统发起 确认发货 传入状态 BackRentState.SendOut.getCode()
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  @PostMapping(value = "/api/aliOrder/SendOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> SendOut(@RequestBody UpdateOrderStateReq updateOrderStateReq);
/**
 * 支付首期款成功  app回调
 * 
 * @param updateOrderStateReq
 * @return
 * @throws
 * createBy:临时工          
 * createDate:2018年3月28日
 */
  @PostMapping(value = "/api/aliOrder/paySuccess", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> paySuccess(@RequestBody UpdateOrderStateReq updateOrderStateReq);
  
  /**
   * 更新买断状态 EarlyBuyout(17,"提前买断"), NormalBuyout(19,"正常买断") ,ForceBuyout(26,
   * "强制买断") ;
   * 
   * @param updateOrderState
   * @return
   * @throws createBy:临时工 createDate:2018年1月2日
   */
  @PostMapping(value = "/api/aliOrder/buyOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> buyOut(@RequestBody UpdateOrderStateReq updateOrderState);
  
  /**
   * 查询订单详细信息
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  @PostMapping(value = "/api/aliOrder/queryOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<OrderDetailResp> queryOrderDetail(@RequestBody String rentRecordNo);
  
  /**
   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表 * 返回结果 订单状态，申请日期，销售单号，单据状态，物料名称，
   * 物料编码，规格值，拣货数量，单位，IMEI号，SN号，拣货日期，拣货人，填单日期，填单人,收货人，
   * 收货地址，联系电话，申请时GPS定位，签约时GPS定位，运单号
   * 
   * @param rentRecordQuery
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  @PostMapping(value = "/api/aliOrder/queryPageRentRecordList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageRentRecordList(@RequestBody RentRecordQuery rentRecordQuery) ;
 
  /**
   * 清算系统调用履约完成
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @PostMapping(value = "/api/aliOrder/endPerformance", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> endPerformance(@RequestBody UpdateOrderStateReq updateOrderStateReq);
  
  /**
   * 后台查询订单详细信息
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @PostMapping(value = "/api/aliOrder/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo);
  /**
   * 小程序查询用户订单列表信息
   * 
   * @param orderDetailReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @PostMapping(value = "/api/aliOrder/queryOrderStateList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<List<OrderDetailResp>> queryOrderStateList(@RequestBody OrderDetailReq orderDetailReq);
  
  /**
   * 后台查询订单列表
   * @param rentRecordQuery
   * @return
   */
  @PostMapping(value = "/backend/aliOrder/queryRentRecordList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<OrderDetailResp>> queryRentRecordList(@RequestBody RentRecordQuery rentRecordQuery) ;

  /**
   * 订单流程列表
   * @param rentRecordNo
   * @return
   */
  @PostMapping(value = "/backend/aliOrder/selectRentState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<RentState>> selectRentState(@RequestBody String rentRecordNo);

  /**
   * 查看物流信息
   * @param rentRecordNo
   * @return
   */
  @PostMapping(value = "/backend/aliOrder/selectLogistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<RentLogistics>> selectLogistics(@RequestBody String rentRecordNo);
  
  
  /**
   * 查询租赁合同信息
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  @PostMapping(value = "/api/aliOrder/lookContract", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<Map<String, Object>> lookContract(@RequestBody String rentRecordNo);
}
@Component
@Slf4j
class IAliOrderServiceFallBackFactory implements FallbackFactory<IAliOrderService> {
  @Override
  public IAliOrderService create(Throwable cause) {
    return new IAliOrderService() {

      @Override
      public ResponseResult<AddOrderResp> addOrder(AddOrderReq addOrderRequest) {
        log.error("microservice-loverent-aliOrder-v1 addOrder系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> updateCreditAmount(UpdateCreditAmountReq updateCreditAmountReq) {
        log.error("microservice-loverent-aliOrder-v1 updateCreditAmount系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> submitOrder(SubmitOrderReq submitOrderReq) {
        log.error("microservice-loverent-aliOrder-v1 submitOrder系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> cancleOrder(UpdateOrderStateReq updateOrderStateReq) {
        log.error("microservice-loverent-aliOrder-v1 cancleOrder系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> signedOrder(UpdateOrderStateReq updateOrderStateReq) {
        log.error("microservice-loverent-aliOrder-v1 signedOrder系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> updateOrder(UpdateOrderStateReq updateOrderStateReq) {
        log.error("microservice-loverent-aliOrder-v1 updateOrder系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> SendOut(UpdateOrderStateReq updateOrderStateReq) {
        log.error("microservice-loverent-aliOrder-v1 SendOut系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> paySuccess(UpdateOrderStateReq updateOrderStateReq) {
        log.error("microservice-loverent-aliOrder-v1 paySuccess系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> buyOut(UpdateOrderStateReq updateOrderState) {
        log.error("microservice-loverent-aliOrder-v1 buyOut系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<OrderDetailResp> queryOrderDetail(String rentRecordNo) {
        log.error("microservice-loverent-aliOrder-v1 queryOrderDetail系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageRentRecordList(RentRecordQuery rentRecordQuery) {
        log.error("microservice-loverent-aliOrder-v1 queryPageRentRecordList系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<String> endPerformance(UpdateOrderStateReq updateOrderStateReq) {
        log.error("microservice-loverent-aliOrder-v1 endPerformance系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<OrderDetailResp> queryBackOrderDetail(String rentRecordNo) {
        log.error("microservice-loverent-aliOrder-v1 queryBackOrderDetail系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

      @Override
      public ResponseResult<List<OrderDetailResp>> queryOrderStateList(OrderDetailReq orderDetailReq) {
        log.error("microservice-loverent-aliOrder-v1 queryOrderStateList系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }
      
      @Override
      public ResponseResult<ResultPager<OrderDetailResp>> queryRentRecordList(RentRecordQuery rentRecordQuery) {
        log.error("microservice-loverent-aliOrder-v1 queryRentRecordList系统异常，已回退:{}", cause.getMessage());
        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
      }

		@Override
		public ResponseResult<ResultPager<RentState>> selectRentState(String rentRecordNo) {
			log.error("microservice-loverent-aliOrder-v1 selectRentState系统异常，已回退:{}", cause.getMessage());
	        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
		}
	
		@Override
		public ResponseResult<ResultPager<RentLogistics>> selectLogistics(String rentRecordNo) {
			log.error("microservice-loverent-aliOrder-v1 selectLogistics系统异常，已回退:{}", cause.getMessage());
	        return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 系统异常，已回退", null);
		}

    @Override
    public ResponseResult<Map<String, Object>> lookContract(String rentRecordNo) {
      log.error("microservice-loverent-aliOrder-v1 lookContract系统异常，已回退:{}", cause.getMessage());
      return ResponseResult.build(1000, "microservice-loverent-aliOrder-v1 lookContract 系统异常，已回退", null);
    }

    };
  }
}