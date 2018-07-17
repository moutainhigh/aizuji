/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.aliOrder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.aliOrder.commons.UpdateDto;
import org.gz.aliOrder.dto.OrderDetailReq;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.RentRecordQuery;
import org.gz.aliOrder.entity.RentRecord;

/**
 * RentRecord Dao
 * 
 * @author pk
 * @date 2018-03-26
 */
@Mapper
public interface RentRecordDao {

  /**
   * 插入数据
   * 
   * @param RentRecord m
   * @return
   */
  Long add(RentRecord m);

  /**
   * 更新RentRecord表数据
   * 
   * @param UpdateDto<RentRecord> m
   * @return
   * @throws createBy:临时工 createDate:2017年12月13日
   */
  int update(UpdateDto<RentRecord> m);

  /**
   * 通过单号获取当前订单状态
   * 
   * @param rentRecordNo
   * @return int
   */
  Integer getStateByRentRecordNo(String rentRecordNo);

  /**
   * 通过订单编号查询订单详细信息
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  OrderDetailResp getOrderDetailByRentRecordNo(String rentRecordNo);

  /**
   * 库存系统查询订单列表数量
   * 
   * @param recordAndExtendsQuery
   * @return
   */
  public int countWareOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<OrderDetailResp> queryPageWareOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 按不同状态查询当前用户订单列表
   * 
   * @param orderDetailReq
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  List<OrderDetailResp> queryOrderStateList(OrderDetailReq orderDetailReq);

  /**
   * 查询用户是否有正在租赁中的单
   * 
   * @param orderDetailReq
   * @return
   * @throws createBy:临时工 createDate:2018年4月2日
   */
  int countRentOrders(OrderDetailReq orderDetailReq);
}
