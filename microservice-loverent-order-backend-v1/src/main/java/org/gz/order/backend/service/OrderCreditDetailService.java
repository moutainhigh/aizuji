package org.gz.order.backend.service;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.entity.OrderCreditDetail;

public interface OrderCreditDetailService {

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
    ResultPager<OrderCreditDetail> queryList(OrderCreditDetailQvo qvo);

    /**
     * 根据id删除
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月22日
     */
    void delete(Long id);

    /**
     * 订单审核
     * @param orderCreditDetail
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月7日
     */
    void orderAudit(OrderCreditDetail orderCreditDetail);

    /**
     * 根据用户id查询对应审核通过的记录数
     * RentRecord RentState 
     */
    Integer queryCountByCustomerCreditList(OrderCreditDetailQvo qvo);

    /**
     * 分页查询审核记录
     */
    ResultPager<OrderCreditDetail> queryCustomerCreditList(OrderCreditDetailQvo qvo);

    /**
     * 查询审核记录
     * @param orderNo
     * @return
     */
    ResultPager<OrderCreditDetail> customerCreditList(String orderNo);
}
