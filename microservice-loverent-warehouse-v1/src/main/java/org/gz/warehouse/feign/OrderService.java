/**
 * 
 */
package org.gz.warehouse.feign;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.OrderDetailRespForWare;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.RentRecordReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.warehouse.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月26日 上午10:54:15
 */
@FeignClient(value = "microservice-loverent-order-api-v1", configuration = FeignFullConfig.class, fallbackFactory = OrderServiceFallBackFactory.class)
public interface OrderService {
	                          
	  /**
	   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表
	   * 返回结果
	   * 订单状态，申请日期，销售单号，单据状态，物料名称，物料编码，规格值，拣货数量，单位，IMEI号，SN号，拣货日期，
	   * 拣货人，填单日期，填单人,收货人， 收货地址，联系电话，申请时GPS定位，签约时GPS定位，运单号
	   * @param rentRecordQuery
	   * @param bindingResult
	   * @return
	   */
	  @PostMapping(value = "/api/order/queryPageRentRecordList")
	  public ResponseResult<ResultPager<OrderDetailRespForWare>> queryRentRecordList(@RequestBody RentRecordQuery rentRecordQuery);
	  
	  /**
	   * 查询订单经纬度列表信息
	   * {"rentRecordNo":"SO1712220000000005","state":""}
	   * @param rentCoordinate
	   * @return
	   */
	  @PostMapping(value = "/api/order/queryOrderCoordinate")
	  public ResponseResult<List<RentCoordinate>> queryOrderCoordinate(@RequestBody RentCoordinate rentCoordinate);
	  
	  /**
	     * 修改订单状态接口：待拣货-待发货(状态码：8)
	     * 
	     * @param admin
	     * @param bindingResult
	     * @return
	     * @throws createBy:临时工 createDate:2017年12月15日
	     */
	 @PostMapping(value = "/api/order/updateOrderState")
	 public ResponseResult<String> updateOrderState(@RequestBody UpdateOrderStateReq updateOrderState);
	    
     /**
     * 仓库系统确认发货、前端用户确认寄货，待发货-已发货(状态码：9)
     * 
     * @param rentRecordReq
     * @param bindingResult
     * @throws createBy:临时工 createDate:2017年12月19日
     */
      @PostMapping(value = "/api/order/SendOut")
    public ResponseResult<String> sendOut(@RequestBody RentRecordReq rentRecordReq);
      
    /**
     * 
    * @Description: 查询归还订单
    * @param  rentRecordQuery
    * @return ResponseResult<ResultPager<OrderDetailResp>>
     */
    @PostMapping(value = "/api/order/queryPageReturnOrderList")
    public ResponseResult<ResultPager<OrderDetailResp>> queryPageReturnOrderList(@RequestBody RentRecordQuery rentRecordQuery);

    @PostMapping(value = "/api/order/sureReturned")
    public ResponseResult<String> sureReturned(@RequestBody UpdateOrderStateReq updateOrderState);
}


@Component
@Slf4j
class OrderServiceFallBackFactory implements FallbackFactory<OrderService>{
	@Override
	public OrderService create(Throwable cause) {
		return new OrderService() {
			public ResponseResult<ResultPager<OrderDetailRespForWare>> queryRentRecordList(RentRecordQuery rentRecordQuery) {
				log.error("库存系统->订单系统，获取订单数据失败:{}",cause.getMessage());
				return ResponseResult.build(10000, "库存系统->订单系统，获取订单数据失败", null);
			}
			@Override
			public ResponseResult<List<RentCoordinate>> queryOrderCoordinate(RentCoordinate rentCoordinate) {
				log.error("库存系统->订单系统，获取订单经纬度数据失败:{}",cause.getMessage());
				return ResponseResult.build(10001, "库存系统->订单系统，获取订单经纬度数据失败", null);
			}

			@Override
			public ResponseResult<String> updateOrderState(UpdateOrderStateReq updateOrderState) {
				log.error("库存系统->订单系统，更新待拣货-待发货状态失败:{}",cause.getMessage());
				return ResponseResult.build(10001, "库存系统->订单系统，更新待拣货-待发货状态失败", null);
			}

			@Override
			public ResponseResult<String> sendOut(RentRecordReq rentRecordReq) {
				log.error("库存系统->订单系统，更新待发货-已发货状态失败:{}",cause.getMessage());
				return ResponseResult.build(10001, "库存系统->订单系统，更新待发货-已发货状态失败", null);
			}
			
			@Override
			public ResponseResult<ResultPager<OrderDetailResp>> queryPageReturnOrderList(RentRecordQuery rentRecordQuery){
				log.error("库存系统->订单系统，获取归还订单失败:{}",cause.getMessage());
				return ResponseResult.build(10001, "库存系统->订单系统，获取归还订单失败", null);
			}
			@Override
			public ResponseResult<String> sureReturned(UpdateOrderStateReq updateOrderState) {
				log.error("库存系统->订单系统更新入库失败:{}",cause.getMessage());
				return ResponseResult.build(10001, "库存系统->订单系统更新入库失败", null);
			}
			
			
		};
	}
}

