package org.gz.risk.auditing.service.outside;

import java.util.HashMap;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.OrderCreditDetail;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.UserHistory;
import org.gz.risk.common.request.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "microservice-loverent-order-api-v1",configuration=FeignFullConfig.class,fallback=IRentRecordServiceFallBack.class)
public interface IRentRecordService {
	/**
	 * 查询指定订单的订单拓展信息
	 * 
	 * @param rentRecordNo 订单号
	 * @return @throws
	 */
	@PostMapping(value = "/api/recordExtends/getByRentRecordNo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RentRecordExtends> getByRentRecordNo(@RequestBody String rentRecordNo);
	/**
     * 插入
     * RentRecord RentState 
     */
    @PostMapping(value = "/api/orderCreditDetail/addOrderCreditDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@RequestBody OrderCreditDetail orderCreditDetail) ;
    /**
     * 根据用户id查询历史审核订单（排除当前订单）
     * RentRecord RentState 
     */
    @PostMapping(value = "/api/orderCreditDetail/queryHistoryCreditListByUserId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryHistoryCreditListByUserId(@RequestBody OrderCreditDetailQvo qvo) ;
	  /**
	   * 更新订单信审状态 {"rentRecordNo":"SO1712220000000005","creditState":2}
	   * 
	   * @param HashMap
	   * @param rentRecordNo String 订单编号
	   * @param creditState int 1进入一审 2进入二审 3进入三审
	   * @return
	   * @throws createBy:临时工 createDate:2017年12月15日
	   */
	  @PostMapping(value = "/api/order/updateCreditState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<String> updateCreditState(@RequestBody HashMap<String, Object> map) ;
	  
	  /**
	   * 信审
	   * {"rentRecordNo":"SO1712220000000005","state":3,"createBy":22,"createMan":"shenpirenyuan","remark":"1111","lng":"","lat":"","sealAgreementUrl":"","evid":""}
	   * 
	   * @param 3 审核通过 BackRentState.WaitPayment.getCode()
	   * @param 2 拒绝 BackRentState.Refuse.getCode()
	   * @return
	   * @throws createBy:临时工 createDate:2017年12月15日
	   */
	  @PostMapping(value = "/api/order/audit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseResult<String> audit(@RequestBody UpdateOrderStateReq updateOrderState) ;
    /**
     * 查询指定订单状态的列表
     * 
     * @param state 订单状态
     * @return
     * @throws 
     */
    @PostMapping(value = "/api/order/queryRentRecordByState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<RentRecord>> queryRentRecordByState(@RequestBody Integer state);
    
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
     * 通过申请时间查询历史用户信息
     * 
     * @param rentRecordQuery
     * @param {"currPage":1,"pageSize":10,"startIndex":0,"endIndex":100,"startApplyTime":"2017-08-11"}
     * @return
     * @throws createBy:临时工 createDate:2018年1月29日
     */
    @PostMapping(value = "/api/order/queryUserHistoryList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<UserHistory>> queryUserHistoryList(@RequestBody RentRecordQuery rentRecordQuery) ;
    /**
     * 通过申请时间查询订单总数
     * 
     * @param rentRecordQuery
     * @return
     * @throws createBy:临时工 createDate:2018年1月29日
     */
    @PostMapping(value = "/api/order/countByapplyTime", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Integer> countByapplyTime(@RequestBody RentRecordQuery rentRecordQuery) ;
    
    /**
     * TODO 通过订单编号查询历史用户信息
     * 
     * @param rentRecordNo
     * @return
     * @throws createDate:2018年1月28日
     */
    @PostMapping(value = "/api/order/queryUserHistory", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<UserHistory> queryUserHistory(@RequestBody String rentRecordNo) ;
}


@Component
class IRentRecordServiceFallBack implements IRentRecordService {
	private ResponseResult<List<RentRecord>> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
    }

	@Override
	public ResponseResult<RentRecordExtends> getByRentRecordNo(String rentRecordNo) {
		   return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<?> add(OrderCreditDetail orderCreditDetail) {
		   return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<?> queryHistoryCreditListByUserId(OrderCreditDetailQvo qvo) {
		   return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo){
		   return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<List<RentRecord>> queryRentRecordByState(Integer state) {
		return defaultBkMethod();
	}

	@Override
	public ResponseResult<UserHistory> queryUserHistory(String rentRecordNo) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}



	@Override
	public ResponseResult<List<UserHistory>> queryUserHistoryList(RentRecordQuery rentRecordQuery) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<Integer> countByapplyTime(RentRecordQuery rentRecordQuery) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<String> updateCreditState(HashMap<String, Object> map) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}

	@Override
	public ResponseResult<String> audit(UpdateOrderStateReq updateOrderState) {
		 return ResponseResult.build(1000, "microservice-loverent-order-api-v1系统异常，已回退", null);
	}



}