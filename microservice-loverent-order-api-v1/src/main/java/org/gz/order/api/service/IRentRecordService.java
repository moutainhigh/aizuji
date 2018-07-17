package org.gz.order.api.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.OrderDetailRespForWare;
import org.gz.order.common.dto.QueryInvoiceReq;
import org.gz.order.common.dto.QueryInvoiceRsp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.RentRecordReq;
import org.gz.order.common.dto.SaleOrderReq;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.dto.WorkOrderRsp;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.UserHistory;

public interface IRentRecordService {
	/**
	 * 查询机审订单
	 * @param state
	 * @return 需要机审的订单列表
	 */
	 ResponseResult<List<RentRecord>> queryRentRecordByState(Integer state) ;
    /**
     * 添加订单
     * 
     * @param m
     * @return
     */
        ResponseResult<String> add(AddOrderReq addOrderRequest);

    /**
     * 确认提交订单
     * 
     * @param m
     * @return
     */
    ResponseResult<OrderDetailResp> submitOrder(SubmitOrderReq submitOrderReq);

    /**
     * 更新订单状态
     * 
     * @param m
     * @return
     */
    ResponseResult<String> updateOrderState(UpdateOrderStateReq req);



    /**
   * 申请签约(如果有库存返回合同模板文件路径，如果没有库存告诉用户稍后再签约)
   * 
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2017年12月15日
   */
  ResponseResult<String> applySign(UpdateOrderStateReq req);

  /**
   * 确认签约
   * 
   * @param req
   * @return
   * @throws Exception
   * @throws createBy:临时工 createDate:2017年12月26日
   */
  ResponseResult<String> sureSign(UpdateOrderStateReq req);

  /**
   * 更新买断
   * 
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2018年1月2日
   */
  ResponseResult<String> buyOut(UpdateOrderStateReq req);

    /**
   * 仓库系统确认发货、前端用户确认寄货，调用接口，更新订单号与收货人地址等信息
   * 
   * @param rentRecord 需要传入订单号，状态（9,"发货中 16,"回收中"） 运单号等字段
   * @return
   * @throws createBy:临时工 createDate:2017年12月15日
   */
    ResponseResult<String> SendOut(RentRecordReq rentRecord);


    /**
     * 查询订单详情
     * 
     * @param rentRecordNo
     * @return
     * @throws createBy:临时工 createDate:2017年12月19日
     */
    ResponseResult<OrderDetailResp> queryOrderDetail(String rentRecordNo);

    /**
     * 按不同状态查询当前用户订单列表
     * 
     * @param orderDetailReq
     * @return
     * @throws createBy:临时工 createDate:2017年12月19日
     */
    ResponseResult<List<OrderDetailResp>> queryOrderStateList(OrderDetailReq orderDetailReq);

  /**
   * 按条件查询rent_record表数据 分页
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2017年12月26日
   */
  ResponseResult<ResultPager<RentRecord>> queryPageRentRecord(RentRecordQuery rentRecordQuery);

  /**
   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2017年12月26日
   */
  ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageWareOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 按条件查询订单对应的经纬度列表
   * 
   * @param dto
   * @return
   * @throws createBy:临时工 createDate:2017年12月27日
   */
  ResponseResult<List<RentCoordinate>> queryList(RentCoordinate dto);

  /**
   * 后端查询订单详情
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  ResponseResult<OrderDetailResp> queryBackOrderDetail(String rentRecordNo);

  /**
   * 合同预览 提供给H5页面
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年1月4日
   */
  ResponseResult<Map<String, Object>> lookContract(String rentRecordNo);

  /**
   * 提供给库存系统通过imei串查询订单列表
   * 
   * @param imeilist
   * @return
   * @throws createBy:临时工 createDate:2018年1月16日
   */
  ResponseResult<List<String>> queryRentRecordNoByImei(List<String> imeilist);

  /**
   * 订单归还中接口（16 归还中 30 强制归还中 33退货中）
   * 
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2018年1月16日
   */
  ResponseResult<String> returnIng(UpdateOrderStateReq req);

  /**
   * 按条件查询订单数据 分页 工单系统（不包含申请中0订单）分页
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年1月24日
   */
  ResponseResult<ResultPager<WorkOrderRsp>> queryPageWokerOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 修改订单审批状态
   * 
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2018年1月2日
   */
  ResponseResult<String> audit(UpdateOrderStateReq req);

  /**
   * 更新订单信审状态
   * 
   * @param HashMap
   * @param rentRecordNo String 订单编号
   * @param creditState int 1进入一审 2进入二审 3进入三审
   * @return
   */
  ResponseResult<String> updateCreditState(HashMap<String, Object> map);

  /**
   * 通过订单编号查询历史用户信息
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年1月28日
   */
  ResponseResult<UserHistory> queryUserHistory(String rentRecordNo);

  /**
   * 通过申请时间查询订单数量
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年1月29日
   */
  ResponseResult<Integer> countByapplyTime(RentRecordQuery rentRecordQuery);

  /**
   * 通过申请时间查询订单列表
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年1月29日
   */
  ResponseResult<List<UserHistory>> queryUserHistoryList(RentRecordQuery rentRecordQuery);

  /**
   * 通过申请时间查询当前时间之前所有签约成功的订单
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年1月29日
   */
  ResponseResult<List<String>> queryOrderNoList(RentRecordQuery rentRecordQuery);

  /**
   * 通过订单编号更新历史用户信息表
   * 
   * @param userHistory
   * @return
   * @throws createBy:临时工 createDate:2018年1月30日
   */
  ResponseResult<String> updateUserHistory(UserHistory userHistory);

  /**
   * 按条件查询订单表和扩展表列表信息 不分页
   * 
   * @param rentRecordQuery
   * @return
   */
  public ResponseResult<List<OrderDetailResp>> queryOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 传入订单编号list批量更新订单状态为逾期
   * 
   * @param rentRecordNos
   * @return
   * @throws createBy:临时工 createDate:2018年2月11日
   */
  public ResponseResult<String> batchUpdateOverDue(List<String> rentRecordNos);

  /**
   * 分页查询订单列表提供给清算系统
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年2月11日
   */
  ResponseResult<ResultPager<OrderDetailResp>> queryPageOrderForLiquation(RentRecordQuery rentRecordQuery);

  /**
   * 查询订单详情t提供给工单系统使用
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年2月27日
   */
  ResponseResult<OrderDetailResp> queryOrderDetailForWork(String rentRecordNo);

  /**
   * 分页查询订单还机列表信息返回给库存系统（归还中”、“提前解约中”、“强制归还中”、“退货中）
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年3月7日
   */
  ResponseResult<ResultPager<OrderDetailResp>> queryPageReturnOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 定时任务执行修改顺丰物流状态
   * 
   * @throws createBy:临时工 createDate:2018年3月8日
   */
  void SfStatusChangeService();

  /**
   * 添加售卖订单接口
   * 
   * @param saleOrderReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月12日
   */
  ResponseResult<String> addSaleOrder(SaleOrderReq saleOrderReq);

  /**
   * 售卖订单支付成功，提供给清算系统调用订单系统修改状态
   * 
   * @param UpdateOrderStateReq req
   * @return
   * @throws createBy:临时工 createDate:2018年3月13日
   */
  ResponseResult<String> paySuccess(UpdateOrderStateReq req);

  /**
   * 定时任务 查询redis中存在的未支付订单同时取消。
   * 
   * @throws createBy:临时工 createDate:2018年3月13日
   */
  void checkWatiPaymentJob();

  /**
   * 确认签收
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  ResponseResult<String> signedOrder(UpdateOrderStateReq updateOrderStateReq);

  /**
   * 分页查询开票信息 提供给清算
   * 
   * @param queryInvoiceReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月29日
   */
  ResponseResult<ResultPager<QueryInvoiceRsp>> queryPageInvoice(QueryInvoiceReq queryInvoiceReq);

  /**
   * 开票完成
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年3月30日
   */
  ResponseResult<String> invoiceEnd(String rentRecordNo);
}
