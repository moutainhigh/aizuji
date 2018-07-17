package org.gz.overdue.service.settle.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.overdue.entity.SettleOrder;
import org.gz.overdue.entity.SettleOrderPage;
import org.gz.overdue.mapper.SettleOrderMapper;
import org.gz.overdue.service.settle.SettleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleServiceImpl implements SettleService {

	@Resource
	private SettleOrderMapper settleOrderMapper;
	
    @Override
    @Transactional
    public void addSettle(SettleOrder settleOrder) {
    	settleOrderMapper.add(settleOrder);
    }

	@Override
	public void deleteSettle(String id) {
		settleOrderMapper.deleteById(id);
		
	}

	@Override
	public void updateSettle(SettleOrder settleOrder) {
		settleOrderMapper.update(settleOrder);
	}

	@Override
	public Integer queryCount(SettleOrder settleOrder) {
		return settleOrderMapper.queryCount(settleOrder);
	}

	@Override
	public List<SettleOrder> queryList(SettleOrderPage settleOrderPage) {
		return settleOrderMapper.queryList(settleOrderPage);
	}
}
