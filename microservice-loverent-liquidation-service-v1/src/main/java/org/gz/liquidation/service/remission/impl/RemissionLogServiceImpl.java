package org.gz.liquidation.service.remission.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RemissionLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.RemissionLog;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.mapper.RemissionLogMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.remission.RemissionLogService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RemissionLogServiceImpl implements RemissionLogService{
	@Resource
	private RemissionLogMapper remissionLogMapper;
	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	@Resource
	private AccountRecordService accountRecordService;
	
	@Resource
	private LateFeeService lateFeeService;
	
	@Override
	public void insertSelective(RemissionLog remissionLog) {
		remissionLogMapper.insertSelective(remissionLog);
	}

	@Override
	public ResultPager<RemissionLogResp> selectPage(QueryDto queryDto) {
		
		List<RemissionLog> list = remissionLogMapper.selectPage(queryDto);
		List<RemissionLogResp> data = BeanConvertUtil.convertBeanList(list, RemissionLogResp.class);
		
		Page page = queryDto.getPage();
		ResultPager<RemissionLogResp> resultPager = new ResultPager<RemissionLogResp>(page.getTotalNum(), page.getStart(), page.getPageSize(), data);
		
		return resultPager;
	}

	@Transactional
	@Override
	public ResponseResult<String> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq) {
		
		if(lateFeeRemissionReq.getRemissionAmount().compareTo(new BigDecimal(0))  <= 0){
			return ResponseResult.build(LiquidationErrorCode.LATE_FEE_DOREMISSION_MIN_AMOUNT_ERROR.getCode(),
					LiquidationErrorCode.LATE_FEE_DOREMISSION_MIN_AMOUNT_ERROR.getMessage(), null);
		}
		
		ResponseResult<String> responseResult = lateFeeService.doLateFeeRemission(lateFeeRemissionReq);
		if(!responseResult.isSuccess()){
			return responseResult;
		}
		
		String orderSN = lateFeeRemissionReq.getOrderSN();
		BigDecimal amount = lateFeeRemissionReq.getRemissionAmount();
		Long createBy = lateFeeRemissionReq.getCreateBy();
		Date date = new Date();
		
		// 记录滞纳金减免科目流水
		AccountRecord accountRecord = new AccountRecord();
	    accountRecord.setAccountCode(AccountEnum.ZNJJM.getAccountCode());
	    accountRecord.setFlowType(LiquidationConstant.OUT);
	    accountRecord.setFundsSN(UUIDUtils.genDateUUID(AccountEnum.ZNJJM.getAccountCode()));
	    accountRecord.setOrderSN(orderSN);
	    accountRecord.setCreateBy(createBy);
	    
	    accountRecord.setTransactionSN(UUIDUtils.genDateUUID(""));
	    accountRecord.setAmount(amount);
	    accountRecord.setCreateOn(date);
	    accountRecord.setRemark(lateFeeRemissionReq.getRemissionDesc());
		accountRecordService.addAccountRecord(accountRecord);
		
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		List<RepaymentSchedule> list = repaymentScheduleService.selectList(repaymentSchedule);
		RepaymentSchedule rs = list.get(0);
		
		// 新增滞纳金减免记录
		RemissionLog remissionLog = new RemissionLog();
		remissionLog.setAmount(amount);
		remissionLog.setCreateBy(createBy.intValue());
		remissionLog.setCreateOn(date);
		remissionLog.setOperator(lateFeeRemissionReq.getCreateByRealname());
		remissionLog.setOrderSN(orderSN);
		remissionLog.setRemissionDesc(lateFeeRemissionReq.getRemissionDesc());
		remissionLog.setUserId(rs.getCreateBy());
		remissionLog.setUserRealName(rs.getRealName());
		
		this.insertSelective(remissionLog);
		
		return ResponseResult.buildSuccessResponse();
	}

}
