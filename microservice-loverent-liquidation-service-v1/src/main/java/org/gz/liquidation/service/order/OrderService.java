package org.gz.liquidation.service.order;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.QueryInvoiceReq;
import org.gz.order.common.dto.QueryInvoiceRsp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "microservice-loverent-order-api-v1",configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface OrderService {
	
	 @PostMapping(value = "/api/order/buyOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<String> buyOut(@RequestBody UpdateOrderStateReq updateOrderState);
	 /**
	  * 
	  * @Description: 查询订单详情
	  * @param rentRecordNo	订单号
	  * @return
	  * @throws
	  * createBy:liaoqingji            
	  * createDate:2018年1月10日
	  */
	 @PostMapping(value = "/api/order/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo);
	 /**
	  * 
	  * @Description: 修改订单状态
	  * @param updateOrderState
	  * @return
	  * @throws
	  * createBy:liaoqingji            
	  * createDate:2018年1月26日
	  */
	 @PostMapping(value = "/api/order/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<String> updateOrderState(@RequestBody UpdateOrderStateReq updateOrderState);
	 /**
	  * 
	  * @Description: 通过申请时间查询截止到当前时间之前所有签约成功的订单
	  * @param rentRecordQuery
	  * @return
	  * @throws
	  * createBy:liaoqingji            
	  * createDate:2018年1月30日
	  */
	  @PostMapping(value = "/api/order/queryOrderNoList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<List<String>> queryOrderNoList(@RequestBody RentRecordQuery rentRecordQuery);
	  
	  /**
	   * 
	   * @Description: 传入订单编号list批量更新订单状态为逾期
	   * @param rentRecordNos 订单编号list
	   * @return
	   * @throws
	   * createBy:liaoqingji            
	   * createDate:2018年2月12日
	   */
	  @PostMapping(value = "/api/order/batchUpdateOverDue", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<String> batchUpdateOverDue(@RequestBody List<String> rentRecordNos);
	  
	  /**
	   * 
	   * @Description: 按条件分页查询订单数据
	   * @param rentRecordQuery
	   * @return
	   * @throws
	   * createBy:liaoqingji            
	   * createDate:2018年2月12日
	   */
	  @PostMapping(value = "/api/order/queryPageOrderForLiquation", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<ResultPager<OrderDetailResp>> queryPageOrderForLiquation(@RequestBody RentRecordQuery rentRecordQuery);
	  /**
	   * @description 售卖订单支付成功，提供给清算系统调用订单系统修改状态
	   * @param updateOrderState
	   * @return
	   * @throws createBy:临时工 createDate:2018年3月13日
	   */
	  @PostMapping(value = "/api/order/paySuccess", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<String> paySuccess(@RequestBody UpdateOrderStateReq updateOrderState);

	  /**
	   * 
	   * @Description: 分页查询开票信息
	   * @param queryInvoiceReq
	   * @return
	   * @throws
	   * createBy:liaoqingji            
	   * createDate:2018年3月30日
	   */
	  @PostMapping(value = "/api/order/queryPageInvoice", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<ResultPager<QueryInvoiceRsp>> queryPageInvoice(@RequestBody QueryInvoiceReq queryInvoiceReq);
	  /**
	   * 确认开票
	   * 
	   * @param rentRecordNo
	   * @return
	   * @throws createBy:临时工 createDate:2018年3月30日
	   */
	  @PostMapping(value = "/api/order/invoiceEnd", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<String> invoiceEnd(@RequestBody String rentRecordNo);
}
