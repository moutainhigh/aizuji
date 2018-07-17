package org.gz.order.backend.service;

import org.gz.common.entity.ResultPager;
import org.gz.order.common.dto.OrderPhoneVerifyInfoQvo;
import org.gz.order.common.entity.OrderPhoneVerifyInfo;

public interface OrderPhoneVerifyInfoService {

    /**
     * 插入数据
     * @param productInfo
     * @return
     */
    void add(OrderPhoneVerifyInfo orderPhoneVerifyInfo);

    /**
     * 根据id查询信息
     * @param id
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    OrderPhoneVerifyInfo getById(Long id);

    /**
     * 更新信息
     * @param orderCreditDetail
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    void update(OrderPhoneVerifyInfo orderPhoneVerifyInfo);

    /**
     * 查询分页总条数
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    Integer queryPageCount(OrderPhoneVerifyInfoQvo qvo);

    /**
     * 根据条件查询分页列表
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    ResultPager<OrderPhoneVerifyInfo> queryList(OrderPhoneVerifyInfoQvo qvo);

    /**
     * 根据id删除
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月22日
     */
    void delete(Long id);
}
