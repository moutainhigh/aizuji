package org.gz.order.server.service;


import java.util.HashMap;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RecordAndExtends;
import org.gz.order.common.dto.RecordAndExtendsQuery;
import org.gz.order.common.dto.RentOrderStateStatistics;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentState;

public interface RentRecordService {



    
    /**
     * 查询订单
     * @param id
     * @return
     */
    RentRecord getById(Long id);
    
    /**
     * 查询订单 
     * rentRecord rentRecordExtends
     * @param id
     * @return
     */
    RecordAndExtends getRecordAndExtends(Long id);
    
    /**
     * 分页查询订单列表
     * @param recordAndExtendsQuery
     * @return
     */
    ResultPager<RecordAndExtends> queryPageRecordAndExtends(RecordAndExtendsQuery recordAndExtendsQuery);
    
    /**
     * 通过单号查询订单状态记录
     * @param rentRecordNo
     * @return
     */
    List<RentState> selectStateByRecordNo(String rentRecordNo);

    /**
     * 通过单号获取租赁记录
     * @param rentRecordNo
     * @return
     */
    RentRecord getByRentRecordNo(String rentRecordNo);

	/**
	 * 更新库存状态
	 * @param rentRecord
	 */
	void updateStockFlag(RentRecord rentRecord);

    /**
     * 通过订单号查询下单人经纬度轨迹
     * @param rentRecordNo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月9日
     */
    List<RentCoordinate> queryRentCoordinateByRecordNo(RentCoordinate dto);

    /**
     * 根据用户id查询各状态统计值
     * @param userId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月10日
     */
    List<RentOrderStateStatistics> queryRentRecordStatusStatistics(Long userId);

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
   * 按条件查询订单表和扩展表列表信息 不分页
   * 
   * @param rentRecordQuery
   * @return
   */
  public List<OrderDetailResp> queryOrderList(RentRecordQuery rentRecordQuery);

  /**
   * @description 从（归还中”、“提前解约中”、“强制归还中”、“退货中 16，30，11，33）变为（27定损完成”、“12
   * 提前解约”、“31 强制定损完成”、“34已退货 ）
   * @param m
   * @return
   */
  ResponseResult<String> sureReturned(UpdateOrderStateReq req);

  /**
   * @description 清算系统支付定损费完成调用 从 ForceDamageCheck(31, "
   * 强制定损完成"),DamageCheck(27, "定损完成"),变成 ForceRecycleEnd(32, " 强制归还完成"),
   * Recycle(20, "已归还"),
   * @param req
   * @return
   * @throws createBy:临时工 createDate:2018年3月16日
   */
  ResponseResult<String> alreadReturned(UpdateOrderStateReq req);
}
