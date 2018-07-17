package org.gz.overdue.service.overdueOrder;


import java.util.List;

import org.gz.overdue.entity.OverdueOrder;
import org.gz.overdue.entity.OverdueOrderPage;

/**
 * 产品相关接口
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月14日 	Created
 */
public interface OverdueOrderService {

    /**
     * 添加记录
     * @param SettleOrderVo
     * @throws
     * createBy:hening            
     * createDate:2018年02月01日
     */
    void addOverdueOrder(OverdueOrder overdueOrder);

    /**
     * 删除记录
     * @param id
     */
    void deleteOverdueOrder(String id);
    
    /**
     * 根据ID修改记录
     * @param settleOrder
     */
    void updateOverdueOrder(OverdueOrder overdueOrder);
    
    /**
     * 根据条件查询总数
     * @param settleOrder
     * @return
     */
    Integer queryCount(OverdueOrderPage overdueOrderPage);
    
    /**
     * 根据条件查询记录(分页)
     * @param settleOrderPage
     * @return
     */
    List<OverdueOrder> queryList(OverdueOrderPage overdueOrderPage);
    
    /**
     * 
     * @param orderSN
     * @return
     */
    List<OverdueOrder> query(OverdueOrder overdueOrder);
    
    /**
     * 删除过期逾期数据
     * @param overdueOrder
     * @return
     */
    Integer deleteOverdueData(OverdueOrder overdueOrder);
    
    /**
     * 同步订单逾期数据
     * @return
     */
    boolean syncOrderData();
}
