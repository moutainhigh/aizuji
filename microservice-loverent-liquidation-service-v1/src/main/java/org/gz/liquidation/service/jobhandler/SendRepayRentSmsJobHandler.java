package org.gz.liquidation.service.jobhandler;

import javax.annotation.Resource;

import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
/**
 * 
 * @Description:交租短信提醒定时任务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月28日 	Created
 */
@JobHander(value="SendRepayRentSmsJobHandler")
@Component
public class SendRepayRentSmsJobHandler extends IJobHandler {

	@Resource
	private RepaymentService repaymentService;
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"sendRepayRentSms start ************************");
		repaymentService.sendRepayRentSms();
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"sendRepayRentSms end ************************");
		return ReturnT.SUCCESS;
	}

}
