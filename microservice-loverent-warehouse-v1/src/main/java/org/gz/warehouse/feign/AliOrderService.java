/**
 * 
 */
package org.gz.warehouse.feign;

import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderDetailRespForWare;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.warehouse.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 调用小程序订单相关服务
 * @author hxj
 * @date 2018年3月29日 下午3:04:02
 */
@FeignClient(value = "microservice-loverent-aliOrder-v1", configuration = FeignFullConfig.class, fallbackFactory = AliOrderServiceFallbackFactory.class)
public interface AliOrderService {

	/**
	   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表 * 返回结果 订单状态，申请日期，销售单号，单据状态，物料名称，
	   * 物料编码，规格值，拣货数量，单位，IMEI号，SN号，拣货日期，拣货人，填单日期，填单人,收货人，
	   * 收货地址，联系电话，申请时GPS定位，签约时GPS定位，运单号
	   * @param rentRecordQuery
	   * @param bindingResult
	   */
	  @PostMapping(value = "/api/aliOrder/queryPageRentRecordList")
	  public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageRentRecordList(@RequestBody RentRecordQuery rentRecordQuery) ;
	  
	  /**更新订单状态
	   * @description 库存系统发起更新为待发货 传入状态 BackRentState.WaitSend.getCode()
	   * @param updateOrderStateReq
	   * @return
	   * @throws createBy:临时工 createDate:2018年3月27日
	   */
	  @PostMapping(value = "/api/aliOrder/updateOrderState")
	  public ResponseResult<String> updateOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) ;
	  
	  /**
	   * @description 库存系统发起 确认发货 传入状态 BackRentState.SendOut.getCode()
	   * @param updateOrderStateReq
	   */
	  @PostMapping(value = "/api/aliOrder/SendOut")
	  public ResponseResult<String> sendOut(@RequestBody UpdateOrderStateReq updateOrderStateReq);
	  
	  /**
	   * 撤销拣货
	   * 
	   * @param updateOrderStateReq
	   * @return
	   * @throws
	   * createBy:临时工          
	   * createDate:2018年4月7日
	   */
	  @PostMapping(value = "/api/aliOrder/canclePick")
	  public ResponseResult<String> canclePick(@RequestBody org.gz.aliOrder.dto.UpdateOrderStateReq updateOrderStateReq);
}

@Component
@Slf4j
class AliOrderServiceFallbackFactory implements FallbackFactory<AliOrderService>{ 
	@Override
	public AliOrderService create(Throwable cause) {
		return new AliOrderService() {

			@Override
			public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageRentRecordList(RentRecordQuery rentRecordQuery) {
				log.error("调用microservice-loverent-aliOrder-v1查询订单数据时出现异常：{}",cause.getMessage());
				return ResponseResult.build(1000, "调用microservice-loverent-aliOrder-v1查询订单数据时出现异常", null);
			}

			@Override
			public ResponseResult<String> updateOrder(UpdateOrderStateReq updateOrderStateReq) {
				log.error("调用microservice-loverent-aliOrder-v1更新订单状态(待拣货->待发货)时出现异常：{}",cause.getMessage());
				return ResponseResult.build(1000, "调用microservice-loverent-aliOrder-v1更新订单状态(待拣货->待发货)时出现异常", null);
			}

			@Override
			public ResponseResult<String> sendOut(UpdateOrderStateReq updateOrderStateReq) {
				log.error("调用microservice-loverent-aliOrder-v1更新订单状态(待发货->已发货)时出现异常：{}",cause.getMessage());
				return ResponseResult.build(1000, "调用microservice-loverent-aliOrder-v1更新订单状态(待发货->已发货)时出现异常", null);
			}

			@Override
			public ResponseResult<String> canclePick(org.gz.aliOrder.dto.UpdateOrderStateReq updateOrderStateReq) {
				log.error("调用microservice-loverent-aliOrder-v1撤销拣货时出现异常：{}",cause.getMessage());
				return ResponseResult.build(1000, "调用microservice-loverent-aliOrder-v1撤销拣货时出现异常", null);
			}};
	}
	
}