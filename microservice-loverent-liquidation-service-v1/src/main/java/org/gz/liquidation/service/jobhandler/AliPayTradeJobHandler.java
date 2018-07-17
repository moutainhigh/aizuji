package org.gz.liquidation.service.jobhandler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gz.liquidation.common.Enum.TransactionWayEnum;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
/**
 * 
 * @Description:支付宝交易订单主动查询定时任务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月28日 	Created
 */
@JobHander(value="AliPayTradeJobHandler")
@Component
public class AliPayTradeJobHandler extends IJobHandler {

	@Resource
	private TransactionRecordService transactionRecordService;
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"updateAlipayTrade start ************************");
		String str = "";
		if(params !=null){
			str = params[0];
		}
		
		TransactionRecord param = new TransactionRecord();
		
		if (StringUtils.isNotBlank(str)) {
			param.setOrderSN(str);
		}
		
		param.setTransactionWay(TransactionWayEnum.ALIPAY.getCode());
		param.setState(LiquidationConstant.STATE_TRADING);
		
		List<TransactionRecord> resultList = transactionRecordService.selectByTransactionRecord(param);
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"无交易中状态的支付宝订单 ************************");
		
		transactionRecordService.queryAlipayTrade(resultList);
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"updateAlipayTrade end ************************");
		return ReturnT.SUCCESS;
	}

}
