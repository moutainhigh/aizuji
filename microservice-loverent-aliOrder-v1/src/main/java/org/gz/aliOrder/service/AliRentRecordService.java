package org.gz.aliOrder.service;

import java.util.List;
import java.util.Map;

import org.gz.aliOrder.dto.AddOrderReq;
import org.gz.aliOrder.dto.AddOrderResp;
import org.gz.aliOrder.dto.OrderDetailReq;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.OrderDetailRespForWare;
import org.gz.aliOrder.dto.RentLogisticsDto;
import org.gz.aliOrder.dto.RentRecordQuery;
import org.gz.aliOrder.dto.SubmitOrderReq;
import org.gz.aliOrder.dto.UpdateCreditAmountReq;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.entity.RentState;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;

public interface AliRentRecordService {

  /**
   * 添加订单
   * 
   * @param m
   * @return
   */
  ResponseResult<AddOrderResp> add(AddOrderReq addOrderRequest);

  /**
   * 订单创建成功异步通知更新信用免押积分和芝麻单号
   * 
   * @param updateCreditAmountReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月26日
   */
  ResponseResult<String> updateCreditAmount(UpdateCreditAmountReq updateCreditAmountReq);

  /**
   * 确认提交订单将订单状态改为待支付
   * 
   * @param submitOrderReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> submitOrder(SubmitOrderReq submitOrderReq);
    
  /**
   * 取消订单
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> cancleOrder(UpdateOrderStateReq updateOrderStateReq);

  /**
   * 更新订单信息
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> updateOrderState(UpdateOrderStateReq updateOrderStateReq);

  /**
   * 确认签收
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> signedOrder(UpdateOrderStateReq updateOrderStateReq);

  /**
   * 确认发货接口
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> SendOut(UpdateOrderStateReq updateOrderStateReq);

  /**
   * 支付首期款成功 修改订单状态为待拣货
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> paySuccess(UpdateOrderStateReq updateOrderStateReq);

  /**
   * 定时任务执行修改顺丰物流状态
   * 
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  void SfStatusChangeService();

  /**
   * 更新买断
   * 
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2018年1月2日
   */
  ResponseResult<String> buyOut(UpdateOrderStateReq req);

  /**
   * 查询订单详情
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  ResponseResult<OrderDetailResp> queryOrderDetail(String rentRecordNo);

  /**
   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2017年12月26日
   */
  ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageWareOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 履约完成
   * 
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2018年1月2日
   */
  ResponseResult<String> endPerformance(UpdateOrderStateReq req);

  /**
   * 为后台页面提供查询订单详情接口
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  public ResponseResult<OrderDetailResp> queryBackOrderDetail(String rentRecordNo);

  /**
   * 按不同状态查询当前用户订单列表
   * 
   * @param orderDetailReq
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  ResponseResult<List<OrderDetailResp>> queryOrderStateList(OrderDetailReq orderDetailReq);

  /**
   * 查询订单列表
   * @param rentRecordQuery
   * @return
   */
  ResponseResult<ResultPager<OrderDetailResp>> queryRentRecordList(RentRecordQuery rentRecordQuery);

  /**
   * 订单流程列表
   * @param rentRecordNo
   * @return
   */
  ResponseResult<ResultPager<RentState>> selectRentState(String rentRecordNo);

  /**
   * 订单物流列表
   * @param rentRecordNo
   * @return
   */
  ResponseResult<ResultPager<RentLogisticsDto>> selectLogistics(String rentRecordNo);

  /**
   * 合同预览 提供给H5页面
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年1月4日
   */
  ResponseResult<Map<String, Object>> lookContract(String rentRecordNo);
}
