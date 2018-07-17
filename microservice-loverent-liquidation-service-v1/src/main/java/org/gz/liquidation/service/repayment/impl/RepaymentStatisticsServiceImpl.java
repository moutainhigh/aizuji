package org.gz.liquidation.service.repayment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.liquidation.common.dto.RepaymentStatisticsResp;
import org.gz.liquidation.entity.RepaymentStatistics;
import org.gz.liquidation.mapper.RepaymentStatisticsMapper;
import org.gz.liquidation.service.repayment.RepaymentStatisticsService;
import org.springframework.stereotype.Service;

@Service
public class RepaymentStatisticsServiceImpl implements RepaymentStatisticsService {
	@Resource
	private RepaymentStatisticsMapper repaymentStatisticsMapper;
	
	@Override
	public void add(RepaymentStatistics repaymentStatistics) {
		repaymentStatisticsMapper.insertSelective(repaymentStatistics);
	}

	@Override
	public void insertPerformanceCountBatch(List<RepaymentStatistics> list) {
		repaymentStatisticsMapper.insertPerformanceCountBatch(list);
	}
	@Override
	public void insertBreachCountBatch(List<RepaymentStatistics> list) {
		repaymentStatisticsMapper.insertBreachCountBatch(list);
	}

	@Override
	public ResponseResult<List<RepaymentStatisticsResp>> queryList(List<String> list) {
		List<RepaymentStatistics> sourceList = repaymentStatisticsMapper.queryList(list);
		List<RepaymentStatisticsResp> resultList = BeanConvertUtil.convertBeanList(sourceList, RepaymentStatisticsResp.class);
		return ResponseResult.buildSuccessResponse(resultList);
	}

}
