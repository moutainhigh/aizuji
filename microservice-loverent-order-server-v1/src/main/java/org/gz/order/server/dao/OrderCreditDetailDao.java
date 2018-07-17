package org.gz.order.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.entity.OrderCreditDetail;

/**
 * 订单审核dao
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月26日 	Created
 */
@Mapper
public interface OrderCreditDetailDao {

    /**
     * 插入数据
     * @param productInfo
     * @return
     */
    void add(OrderCreditDetail orderCreditDetail);

    /**
     * 根据id查询信息
     * @param id
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    OrderCreditDetail getById(Long id);

    /**
     * 更新信息
     * @param orderCreditDetail
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    void update(OrderCreditDetail orderCreditDetail);

    /**
     * 查询分页总条数
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    Integer queryPageCount(OrderCreditDetailQvo qvo);

    /**
     * 根据条件查询分页列表
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    List<OrderCreditDetail> queryList(OrderCreditDetailQvo qvo);

    /**
     * 根据id删除
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月22日
     */
    void delete(Long id);

    /**
     * 根据用户id查询对应审核通过的记录数
     * RentRecord RentState 
     */
    Integer queryCountByCustomerCreditList(OrderCreditDetailQvo qvo);

    /**
     * 分页查询审核记录
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月18日
     */
    List<OrderCreditDetail> queryCustomerCreditList(OrderCreditDetailQvo qvo);

    /**
     * 根据用户id查询历史审核订单（排除当前订单）
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月29日
     */
    List<OrderCreditDetail> queryHistoryCreditListByUserId(OrderCreditDetailQvo qvo);

    /**
     * 审核记录
     * @param orderNo
     * @return
     */
	List<OrderCreditDetail> customerCreditList(String orderNo);
}
