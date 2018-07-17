package org.gz.overdue.service.overdueOrder.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.overdue.entity.OverdueOrder;
import org.gz.overdue.entity.OverdueOrderPage;
import org.gz.overdue.mapper.OverdueOrderMapper;
import org.gz.overdue.service.LiquidationService;
import org.gz.overdue.service.overdueOrder.OverdueOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OverdueOrderServiceImpl implements OverdueOrderService {

	/**
	 * 起始页
	 */
	private static final Integer startPage=1;
	/**
	 * 每页行数
	 */
	private static final Integer pageSize=10;
	/**
	 * 错误次数上限
	 */
	private static final Integer flagSize=60;
	
	@Autowired
	private LiquidationService liquidationService;
	
	@Resource
	private OverdueOrderMapper overdueOrderMapper;

	@Override
	public void addOverdueOrder(OverdueOrder overdueOrder) {
		overdueOrderMapper.add(overdueOrder);
	}

	@Override
	public void deleteOverdueOrder(String id) {
		overdueOrderMapper.deleteById(id);
	}

	@Override
	public void updateOverdueOrder(OverdueOrder overdueOrder) {
		overdueOrderMapper.update(overdueOrder);
	}

	@Override
	public Integer queryCount(OverdueOrderPage overdueOrderPage) {
		return overdueOrderMapper.queryCount(overdueOrderPage);
	}

	@Override
	public List<OverdueOrder> queryList(OverdueOrderPage overdueOrderPage) {
		return overdueOrderMapper.queryList(overdueOrderPage);
	}

	@Override
	public List<OverdueOrder> query(OverdueOrder overdueOrder) {
		return overdueOrderMapper.query(overdueOrder);
	}

	@Override
	public Integer deleteOverdueData(OverdueOrder overdueOrder) {
		return overdueOrderMapper.deleteOverdueData(overdueOrder);
	}
	
	@Override
	public boolean syncOrderData(){
		log.info("syncOrderData is begin.");
		try {
			//清除过期数据
			OverdueOrder overdueOrder = new OverdueOrder();
//			overdueOrder.setOrderStatus();
			deleteOverdueData(overdueOrder);
			//同步最新数据
			return syncOverdueData(startPage,pageSize,0);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("syncOrderData is error.{}",e);
			return false;
		}
	}
	
	/**
	 * 同步逾期记录数据
	 * @param startPage
	 * @param pageSize
	 * @param flag
	 * @return
	 */
	public boolean syncOverdueData(Integer startPage,Integer pageSize,Integer flag){
		log.info("同步数据动作开始，当前次数:{}",flag);
		if(flag>=flagSize){
			log.error("同步数据尝试次数超出最大限制次数，同步动作停止。当前次数/最大次数:{}/{}",flag,flagSize);
			return false;
		}
		Page page = new Page();
		page.setStart(startPage);
		page.setPageSize(pageSize);
		try {
			ResponseResult<ResultPager<RepaymentScheduleResp>> result = liquidationService.queryPage(page);
			//同步失败则递归重试
			if(result.getErrCode()!=0){
				log.error("同步数据失败。");
				Thread.sleep(1000);
				return syncOverdueData(startPage,pageSize,flag+1);
			}
			//处理数据
			ResultPager<RepaymentScheduleResp> resultPager = result.getData();
			int totalPages = resultPager.getTotalPages();
			List<RepaymentScheduleResp> list = resultPager.getData();
			for(RepaymentScheduleResp resp:list){
				log.info("syncOverdueData list is {}",JSON.toJSON(resp));
				OverdueOrder overdueOrder = new OverdueOrder();
				overdueOrder.setOrderSN(resp.getOrderSN());
				overdueOrder.setUserId(resp.getCreateBy()+"");
				overdueOrder.setUserName(resp.getRealName());
				overdueOrder.setPhone(resp.getPhone());
				overdueOrder.setSignTime(resp.getCreateOn());//签约时间
				overdueOrder.setOverdueDay(resp.getOverdueDay());
				overdueOrder.setDueAmount(resp.getDue());
				overdueOrder.setOverdueTime(resp.getPaymentDueDate());
				overdueOrder.setLeaseTimes(resp.getPeriods());
//				overdueOrder.setFollowPerson();//跟进人
				overdueOrder.setOrderStatus(0);//设为待处理状态
				addOverdueOrder(overdueOrder);
			}
			//如果当前页数小于最大页数则继续同步
			if(startPage<totalPages){
				return syncOverdueData(startPage+1,pageSize,flag);
			}
			return true;
		} catch (Exception e) {
				return syncOverdueData(startPage,pageSize,flag+1);
		}
	}
}
