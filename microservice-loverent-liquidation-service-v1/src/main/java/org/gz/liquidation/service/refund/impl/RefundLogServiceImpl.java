package org.gz.liquidation.service.refund.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RefundLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.RefundLog;
import org.gz.liquidation.mapper.RefundLogMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.refund.RefundLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RefundLogServiceImpl implements RefundLogService {
	@Resource
	private RefundLogMapper refundLogMapper;
	@Resource
	private AccountRecordService accountRecordService;
	
	@Transactional
	@Override
	public void insertSelective(RefundLog refundLog) {
		
		refundLogMapper.insertSelective(refundLog);
		
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setAccountCode(refundLog.getAccountCode());
		accountRecord.setAmount(refundLog.getAmount());
		accountRecord.setCreateBy(refundLog.getCreateBy());
		accountRecord.setCreateOn(new Date());
		accountRecord.setFlowType(LiquidationConstant.IN);
		accountRecord.setFundsSN(UUIDUtils.genDateUUID(refundLog.getAccountCode()));
		accountRecord.setTransactionSN(refundLog.getTransactionSN());
		accountRecord.setOrderSN(refundLog.getOrderSN());
		accountRecord.setRemark(refundLog.getRefundDesc());
		
		accountRecordService.addAccountRecord(accountRecord);
	}

	@Override
	public ResultPager<RefundLogResp> selectPage(QueryDto queryDto) {
		List<RefundLog> list = refundLogMapper.selectPage(queryDto);
		List<RefundLogResp> data = BeanConvertUtil.convertBeanList(list, RefundLogResp.class);
		
		Page page = queryDto.getPage();
		ResultPager<RefundLogResp> resultPager = new ResultPager<RefundLogResp>(page.getTotalNum(), page.getStart(), page.getPageSize(), data);
		
		return resultPager;
	}

	@Override
	public String getImgUrl(Long id) {
		RefundLog refundLog = refundLogMapper.selectByPrimaryKey(id);
		return refundLog.getImgURL();
	}

}
