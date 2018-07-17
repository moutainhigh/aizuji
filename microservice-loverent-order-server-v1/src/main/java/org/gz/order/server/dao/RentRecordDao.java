package org.gz.order.server.dao;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.QueryInvoiceReq;
import org.gz.order.common.dto.QueryInvoiceRsp;
import org.gz.order.common.dto.RecordAndExtends;
import org.gz.order.common.dto.RecordAndExtendsQuery;
import org.gz.order.common.dto.RentOrderStateStatistics;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.dto.WorkOrderRsp;
import org.gz.order.common.entity.RentRecord;
/**
 * RentRecord Dao
 * 
 * @author pk
 * @date 2017-12-13
 */
@Mapper
public interface RentRecordDao {
	
	 /**
     * 通过订单状态查询RentRecord列表
     * 
     * @param state 订单状态
     * @return  订单列表
     * @throws
     */
	 public List<RentRecord> queryRentRecordByState(Integer state);
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
    public List<RentRecord> queryList(RentRecord dto);

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
     * 查询数量
     * @param recordAndExtendsQuery
     * @return
     */
	public int countRecordAndExtends(RecordAndExtendsQuery recordAndExtendsQuery);

	/**
	 * 分页查询
	 * @param recordAndExtendsQuery
	 * @return
	 */
	public List<RecordAndExtends> queryPageRecordAndExtends(RecordAndExtendsQuery recordAndExtendsQuery);

	/**
	 * 查询租赁记录
	 */
	public RentRecord getById(Long id);
	
	/**
	 * 查询租赁记录 
	 */
	public RecordAndExtends getRecordAndExtends(Long id);

    /**
     * @param RentRecord dto
     * @return
     * @throws createBy:临时工 createDate:2017年12月14日
     */
    int queryCount(RentRecord dto);

    /**
     * 通过订单编号查询RentRecord信息
     * 
     * @param rentRecordNo
     * @return
     * @throws
     * createBy:临时工          
     * createDate:2017年12月18日
     */
    RentRecord getByRentRecordNo(String rentRecordNo);

    /**
     * 通过订单编号查询订单详细信息
     * 
     * @param rentRecordNo
     * @return
     * @throws createBy:临时工 createDate:2017年12月19日
     */
    OrderDetailResp getOrderDetailByRentRecordNo(String rentRecordNo);

    /**
     * 按不同状态查询当前用户订单列表
     * 
     * @param orderDetailReq
     * @return
     * @throws createBy:临时工 createDate:2017年12月19日
     */
    List<OrderDetailResp> queryOrderStateList(OrderDetailReq orderDetailReq);

  /**
   * 查询数量
   * 
   * @param recordAndExtendsQuery
   * @return
   */
  public int countRentRecord(RentRecordQuery rentRecordQuery);

  /**
   * 分页查询
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<RentRecord> queryPageRentRecord(RentRecordQuery rentRecordQuery);

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
     * 根据用户id查询各状态对应的订单数
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月27日
     */
    List<RentOrderStateStatistics> queryRentRecordStatusStatistics(Long userId);

  /**
   * 通过imeilist查询订单编号list<String>
   * 
   * @param imeiList
   * @return
   * @throws createBy:临时工 createDate:2018年1月16日
   */
  List<String> queryRentRecordNoByImei(List<String> imeiList);

  /**
   * 工单系统查询订单列表数量（不包含申请中0订单）
   * 
   * @param recordAndExtendsQuery
   * @return
   */
  public int countWokerOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 按条件查询订单数据 分页 工单系统（不包含申请中0订单）分页
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<WorkOrderRsp> queryPageWokerOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 按大于某个申请时间分页查询订单编号list
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<String> queryPageOrderNos(RentRecordQuery rentRecordQuery);

  /**
   * 通过申请时间查询当前时间之前所有签约成功的订单
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<String> queryOrderNos(RentRecordQuery rentRecordQuery);

  /**
   * 按条件查询订单表和扩展表列表信息 不分页
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<OrderDetailResp> queryOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 库存系统查询订单还机列表数量
   * 
   * @param rentRecordQuery
   * @return
   */
  public int countReturnOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 分页查询订单还机列表信息返回给库存系统（归还中”、“提前解约中”、“强制归还中”、“退货中）
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<OrderDetailResp> queryReturnOrderList(RentRecordQuery rentRecordQuery);

  /**
   * 查询数量
   * 
   * @param queryInvoiceReq
   * @return
   */
  public int countInvoice(QueryInvoiceReq queryInvoiceReq);

  /**
   * 分页查询开票信息提供给清算
   * 
   * @param queryInvoiceReq
   * @return
   */
  public List<QueryInvoiceRsp> queryPageInvoice(QueryInvoiceReq queryInvoiceReq);

}
