package org.gz.overdue.service;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.overdue.config.OverdueFeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "microservice-loverent-order-api-v1",configuration=OverdueFeignConfig.class, fallback = IOrderServiceFallBack.class)
public interface OrderService {
	/**
	   * 后端查询订单详细信息
	   * 
	   * @param rentRecordNo
	   * @param
	   * @return
	   * @throws createBy:临时工
	   *             createDate:2017年12月19日
	   */
	  @PostMapping(value = "/api/order/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo) ;
	 
	  /**
	   * 查询订单列表信息 不分页
	   * 
	   * @param rentRecordQuery
	   * @param {"userId":22,"orderBy":[{"cloumnName":"rr.id","sequence":"desc"}],"notZero":0}
	   * @return
	   */
	  @PostMapping(value = "/api/order/queryOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<List<OrderDetailResp>> queryOrderList(@RequestBody RentRecordQuery rentRecordQuery);
	  
	  /**
	   * 修改订单状态接口
	   * 
	   * @param admin
	   * @param bindingResult
	   * @return
	   * @throws createBy:临时工
	   *             createDate:2017年12月15日
	   */
	  @PostMapping(value = "/api/order/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<String> updateOrderState(@RequestBody UpdateOrderStateReq updateOrderState);
}
@Component
class IOrderServiceFallBack implements OrderService {

	@Override
	public ResponseResult<OrderDetailResp> queryBackOrderDetail(String rentRecordNo) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1 /api/order/queryBackOrderDetail 系统异常，已回退", null);
	}

	@Override
	public ResponseResult<List<OrderDetailResp>> queryOrderList(RentRecordQuery rentRecordQuery) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1 /api/order/queryOrderList 系统异常，已回退", null);
	}

	@Override
	public ResponseResult<String> updateOrderState(UpdateOrderStateReq updateOrderState) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1 /api/order/updateOrderState 系统异常，已回退", null);
	}


}