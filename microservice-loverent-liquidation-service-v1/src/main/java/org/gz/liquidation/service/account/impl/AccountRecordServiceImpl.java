package org.gz.liquidation.service.account.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.DownpaymentStatisticsReq;
import org.gz.liquidation.common.dto.DownpaymentStatisticsResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RevenueStatisticsResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.mapper.AccountRecordMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.order.common.dto.RentRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AccountRecordServiceImpl implements AccountRecordService{
	@Resource
	private AccountRecordMapper accountRecordMapper;
	@Resource
	private TransactionRecordService transactionRecordService;
	@Autowired
	private OrderService orderService;
	
	@Override
	public ResponseResult<AccountRecord> addAccountRecord(AccountRecord accountRecord) {
		int size = accountRecordMapper.insertSelective(accountRecord);
		if (size > 0) {
			return ResponseResult.buildSuccessResponse(accountRecord);
		}
		return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), accountRecord);
	}

	@Override
	public ResponseResult<AccountRecord> selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public List<AccountRecord> selectList(AccountRecord accountRecord) {
		return accountRecordMapper.selectList(accountRecord);
	}

	@Override
	public void insertBatch(List<AccountRecord> list) {
		for (AccountRecord accountRecord : list) {
			if (accountRecord.getRemark() == null) {
				accountRecord.setRemark("");
			}
		}
		accountRecordMapper.insertBatch(list);
	}

	@Override
	public BigDecimal selectSumAmount(AccountRecord accountRecord) {
		BigDecimal lateFees = accountRecordMapper.selectSumAmount(accountRecord);
		if(lateFees == null){
			lateFees = new BigDecimal(0);
		}
		return lateFees;
	}

	@Override
	public List<AccountRecord> selectAmount(AccountRecord accountRecord) {
		return accountRecordMapper.selectAmount(accountRecord);
	}

	@Override
	public ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatistics(DownpaymentStatisticsReq downpaymentStatisticsReq) {
		DownpaymentStatisticsResp dsp = new DownpaymentStatisticsResp();
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionType(TransactionTypeEnum.FIRST_RENT.getCode());
		RentRecordQuery rentRecordQuery = new RentRecordQuery();
		if(null != downpaymentStatisticsReq.getDateStart()){
			transactionRecord.setStartDate(downpaymentStatisticsReq.getDateStart());
			rentRecordQuery.setSignStartTime(DateUtils.getDateString(downpaymentStatisticsReq.getDateStart()));
		}
		if(null != downpaymentStatisticsReq.getDateEnd()){
			transactionRecord.setEndDate(downpaymentStatisticsReq.getDateEnd());
			rentRecordQuery.setSignEndTime(DateUtils.getDateString(downpaymentStatisticsReq.getDateEnd()));
		}
		
		rentRecordQuery.setGreateZero(7);
		ResponseResult<List<String>> responseResult = orderService.queryOrderNoList(rentRecordQuery);
		log.info("调用订单服务查询签约成功订单 responseResult:{}",responseResult);
		if(!responseResult.isSuccess()){
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "查询签约成功订单服务异常", null);
		}
		
		transactionRecord.setOrderSnList(responseResult.getData());
		// 首期支付总笔数
		transactionRecord.setState(LiquidationConstant.STATE_SUCCESS);
		transactionRecord.setTransactionType(TransactionTypeEnum.FIRST_RENT.getCode());
		int totalTransactions = transactionRecordService.selectCountStatistics(transactionRecord);
		dsp.setTotalTransactions(totalTransactions);
		
		// 待完成首期支付笔数
		transactionRecord.setState(LiquidationConstant.STATE_TRADING);
		int pendingDownpaymentPayments = transactionRecordService.selectCountStatistics(transactionRecord);
		dsp.setPendingDownpaymentPayments(pendingDownpaymentPayments);
		downpaymentStatisticsReq.setOrderSnList(responseResult.getData());
		List<DownpaymentStatisticsResp> list = accountRecordMapper.selectDownpaymentStatistics(downpaymentStatisticsReq);
		
		BigDecimal zero = new BigDecimal(0);
		// 首期款总收入（交易完成）
		DownpaymentStatisticsResp d0 = list.get(0);
		BigDecimal totalDownpayment = d0.getTotalDownpayment() == null ? zero :d0.getTotalDownpayment();
		dsp.setTotalDownpayment(totalDownpayment);
		
		// 首期租金总收入
		DownpaymentStatisticsResp d1 = list.get(1);
		BigDecimal totalFirstRent = d1.getTotalFirstRent() == null ? zero :d1.getTotalFirstRent();
		dsp.setTotalFirstRent(totalFirstRent);
		
		// 保障金总收入
		DownpaymentStatisticsResp d2 = list.get(2);
		BigDecimal totalInsurance = d2.getTotalInsurance() == null ? zero :d2.getTotalInsurance();
		dsp.setTotalInsurance(totalInsurance);
		
		// 溢价金总收入
		DownpaymentStatisticsResp d3 = list.get(3);
		BigDecimal totalPremium = d3.getTotalPremium() == null ? zero :d3.getTotalPremium();
		dsp.setTotalPremium(totalPremium);
		
		transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionType(TransactionTypeEnum.FIRST_RENT.getCode());
		transactionRecord.setState(LiquidationConstant.STATE_WAITING);
		BigDecimal receivableDownpayment = transactionRecordService.selectSumAmount(transactionRecord);
		dsp.setPendingDownpayment(receivableDownpayment == null ? zero: receivableDownpayment);
		return ResponseResult.buildSuccessResponse(dsp);
	}

	@Override
	public ResultPager<AccountRecordResp> selectPage(QueryDto queryDto) {
		List<AccountRecord> list = accountRecordMapper.selectPage(queryDto);
		List<AccountRecordResp> data = BeanConvertUtil.convertBeanList(list, AccountRecordResp.class);
		
		Page page = queryDto.getPage();
		ResultPager<AccountRecordResp> resultPager =  new ResultPager<>(page.getTotalNum(), page.getStart(), page.getPageSize(), data);
		return resultPager;
	}

	@Override
	public ResponseResult<RevenueStatisticsResp> selectRevenue(Map<String, Object> map) {
		RevenueStatisticsResp revenueStatisticsResp = new RevenueStatisticsResp();
		// 查询科目流水记录集合
		List<AccountRecord> list = accountRecordMapper.selectRevenueList(map);
		BigDecimal rentIn = new BigDecimal(0);
		BigDecimal rentOut = new BigDecimal(0);
		// 滞纳金收入
		BigDecimal lateFeesIn = new BigDecimal(0);
		// 滞纳金退回
		BigDecimal lateFeesOut = new BigDecimal(0);
		// 买断金收入
		BigDecimal buyoutAmountIn = new BigDecimal(0);
		// 买断金退回
		BigDecimal buyoutAmountOut = new BigDecimal(0);
		// 折旧费收入
		BigDecimal depreciationExpenseIn = new BigDecimal(0);
		// 折旧费退回
		BigDecimal depreciationExpenseOut = new BigDecimal(0);
		// 销售金收入
		BigDecimal saleAmountIn = new BigDecimal(0);
		// 销售金退回
		BigDecimal saleAmountOut = new BigDecimal(0);
		for (AccountRecord ar : list) {
			String accountCode = ar.getAccountCode();
			String flowType = ar.getFlowType();
			AccountEnum accountEnum = AccountEnum.getAccountEnum(accountCode);
			switch (accountEnum) {
			case ZJ:
				
				if(flowType.equalsIgnoreCase(LiquidationConstant.IN)){
					rentIn = rentIn.add(ar.getAmount());
				}else if (flowType.equalsIgnoreCase(LiquidationConstant.OUT)) {
					rentOut = rentOut.add(ar.getAmount());
				}
				continue;
			case ZNJ:
				
				if(flowType.equalsIgnoreCase(LiquidationConstant.IN)){
					lateFeesIn = lateFeesIn.add(ar.getAmount());
				}else if (flowType.equalsIgnoreCase(LiquidationConstant.OUT)) {
					lateFeesOut = lateFeesOut.add(ar.getAmount());
				}
				continue;
			case MDJ:
				
				if(flowType.equalsIgnoreCase(LiquidationConstant.IN)){
					buyoutAmountIn = buyoutAmountIn.add(ar.getAmount());
				}else if (flowType.equalsIgnoreCase(LiquidationConstant.OUT)) {
					buyoutAmountOut = buyoutAmountOut.add(ar.getAmount());
				}
				continue;
			case ZJWYJ:
				
				if(flowType.equalsIgnoreCase(LiquidationConstant.IN)){
					depreciationExpenseIn = depreciationExpenseIn.add(ar.getAmount());
				}else if (flowType.equalsIgnoreCase(LiquidationConstant.OUT)) {
					depreciationExpenseOut = depreciationExpenseOut.add(ar.getAmount());
				}
				continue;
			case XSJ:
				
				if(flowType.equalsIgnoreCase(LiquidationConstant.IN)){
					saleAmountIn = saleAmountIn.add(ar.getAmount());
				}else if (flowType.equalsIgnoreCase(LiquidationConstant.OUT)) {
					saleAmountOut = saleAmountOut.add(ar.getAmount());
				}
				continue;
			default:
				continue;
			}
			
		}
		
		revenueStatisticsResp.setRent(rentIn.subtract(rentOut));
		revenueStatisticsResp.setBuyoutAmount(buyoutAmountIn.subtract(buyoutAmountOut));
		revenueStatisticsResp.setLateFees(lateFeesIn.subtract(lateFeesOut));
		revenueStatisticsResp.setDepreciationExpense(depreciationExpenseIn.subtract(depreciationExpenseOut));
		revenueStatisticsResp.setSaleAmount(saleAmountIn.subtract(saleAmountOut));
		return ResponseResult.buildSuccessResponse(revenueStatisticsResp);
	}

	@Override
	public List<AccountRecord> sumLatefee(List<String> list) {
		return accountRecordMapper.sumLatefee(list);
	}

	@Override
	public BigDecimal queryRemissionFee(String orderSN) {
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setOrderSN(orderSN);
		accountRecord.setAccountCode(AccountEnum.ZNJJM.getAccountCode());
		// 查询减免金额
		BigDecimal remissionFee = this.selectSumAmount(accountRecord);
		if(remissionFee == null){
			remissionFee = new BigDecimal(0);
		}
		return remissionFee;
	}

}
