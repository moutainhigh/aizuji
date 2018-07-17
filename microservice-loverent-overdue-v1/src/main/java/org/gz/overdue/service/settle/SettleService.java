package org.gz.overdue.service.settle;


import java.util.List;

import org.gz.overdue.entity.SettleOrder;
import org.gz.overdue.entity.SettleOrderPage;

/**
 * 产品相关接口
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月14日 	Created
 */
public interface SettleService {

    /**
     * 添加记录
     * @param SettleOrderVo
     * @throws
     * createBy:hening            
     * createDate:2018年02月01日
     */
    void addSettle(SettleOrder settleOrder);

    /**
     * 删除记录
     * @param id
     */
    void deleteSettle(String id);
    
    /**
     * 根据ID修改记录
     * @param settleOrder
     */
    void updateSettle(SettleOrder settleOrder);
    
    /**
     * 根据条件查询总数
     * @param settleOrder
     * @return
     */
    Integer queryCount(SettleOrder settleOrder);
    
    /**
     * 根据条件查询记录(分页)
     * @param settleOrderPage
     * @return
     */
    List<SettleOrder> queryList(SettleOrderPage settleOrderPage);
}
