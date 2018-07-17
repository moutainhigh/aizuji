package org.gz.liquidation.service.repayment.detail.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.liquidation.entity.RepaymentDetailEntity;
import org.gz.liquidation.mapper.RepaymentDetailMapper;
import org.gz.liquidation.service.repayment.detail.RepaymentDetailService;
import org.springframework.stereotype.Service;
@Service
public class RepaymentDetailServiceImpl implements RepaymentDetailService {

	@Resource
	private RepaymentDetailMapper repaymentRecordMapper;
	
	@Override
	public void insertSelective(RepaymentDetailEntity repaymentDetailEntity) {
		repaymentRecordMapper.insertSelective(repaymentDetailEntity);
	}

	@Override
	public List<RepaymentDetailEntity> selectList(RepaymentDetailEntity repaymentDetailEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBatch(List<RepaymentDetailEntity> list) {
		repaymentRecordMapper.insertBatch(list);
	}

}
