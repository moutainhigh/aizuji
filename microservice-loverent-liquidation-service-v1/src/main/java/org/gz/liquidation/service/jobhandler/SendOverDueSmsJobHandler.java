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
 * @Description:逾期短信提醒定时任务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月28日 	Created
 */
@JobHander(value="SendOverDueSmsJobHandler")
@Component
public class SendOverDueSmsJobHandler extends IJobHandler {

	@Resource
	private RepaymentService repaymentService;
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"sendOverdueSms start ************************");
		if(params != null && params.length==3){
			String str = params[0];
			String[] array = str.split("@");
			int minDay = Integer.valueOf(params[1]);
			int maxDay = Integer.valueOf(params[2]);
			XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"sendOverdueSms 参数: "+str+" "+minDay+" "+maxDay);
			repaymentService.sendOverdueSms(array, minDay, maxDay);
		}else{
			XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"sendOverdueSms 无参数传入 ************************");
			repaymentService.sendOverdueSms(null, null, null);
		}
		
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"sendOverdueSms end ************************");
		return ReturnT.SUCCESS;
	}

}
