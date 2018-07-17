package org.gz.liquidation.service.jobhandler;

import javax.annotation.Resource;

import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 
 * @Description:	滞纳金计算定时任务处理器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月9日 	Created
 */
@JobHander(value="LateFeeJobHandler")
@Component
public class LateFeeJobHandler extends IJobHandler {
	
	@Resource
	private LateFeeService lateFeeService;
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		// 计算滞纳金
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"LateFeeJobHandler start ************************");
		
		lateFeeService.calculateLateFee();
		
		XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"LateFeeJobHandler end ************************");
		
		return ReturnT.SUCCESS;
	}

}
