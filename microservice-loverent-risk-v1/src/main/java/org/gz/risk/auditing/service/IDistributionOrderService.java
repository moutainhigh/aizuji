package org.gz.risk.auditing.service;

import java.util.Map;

/**
 * @author JarkimZhu
 *         Created on 2016/11/22.
 * @since jdk1.8
 */
public interface IDistributionOrderService {


    //手动分单    proceIds="1111;2222;3333;"    letterUser="wanzi"
    public void manualAllocation(String proceIds, String letterUser);


    //返回用户组是否包含 一个用户
    public boolean isContains(String creditGroup, String userId);


    //自动分配订单
    public void automaticDistribution(String processInstanceId);

    //主动获取  点击之后  分配订单
    public void submitAcquisition(Long assignId, int applyNum, String creditUserID);

    //定时将未分配的订单按分配最少原则分配
    public void timingDistribution();

  
    public Map<String, String> getDistributionCache();

}
