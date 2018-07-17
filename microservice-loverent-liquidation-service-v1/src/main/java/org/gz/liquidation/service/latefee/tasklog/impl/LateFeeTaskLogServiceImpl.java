package org.gz.liquidation.service.latefee.tasklog.impl;

import javax.annotation.Resource;

import org.gz.liquidation.entity.LateFeeTaskLogEntity;
import org.gz.liquidation.mapper.LateFeeTaskLogMapper;
import org.gz.liquidation.service.latefee.tasklog.ILateFeeTaskLogService;
import org.springframework.stereotype.Service;
@Service
public class LateFeeTaskLogServiceImpl implements ILateFeeTaskLogService{

	@Resource
	private LateFeeTaskLogMapper lateFeeTaskLogMapper;
	
	@Override
	public void insert(LateFeeTaskLogEntity lateFeeTaskLogEntity) {
		lateFeeTaskLogMapper.insert(lateFeeTaskLogEntity);
	}

	@Override
	public boolean checkIsExecuted(LateFeeTaskLogEntity lateFeeTaskLogEntity) {
		boolean flag = false;
		int num = lateFeeTaskLogMapper.checkIsExecuted(lateFeeTaskLogEntity);
		if (num > 0) flag = true;
		return flag;
	}

}
