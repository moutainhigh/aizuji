package org.gz.liquidation.service.jobhandler;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.utils.DateUtils;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
/**
 * 
 * @Description:芝麻订单自动扣款
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月3日 	Created
 */
@JobHander(value="AutomaticDeductionsJobHandler")
@Component
public class AutomaticDeductionsJobHandler extends IJobHandler {

	@Resource
	private RepaymentService repaymentService;
	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		XxlJobLogger.log("AutomaticDeductionsJobHandler start ************************");
		Date date = DateUtils.getDayStart();
		String str = "";
		if(params !=null){
			str = params[0];
			date = DateUtils.getDay(str);
		}
		
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		repaymentSchedule.setPaymentDueDate(date);
		repaymentSchedule.setOrderType(LiquidationConstant.ORDER_TYPE_ZM);
		List<RepaymentSchedule> list = repaymentScheduleService.selectRepaymentList(repaymentSchedule);
		
		repaymentService.doAutomaticDeductions(list);
		XxlJobLogger.log("AutomaticDeductionsJobHandler end ************************");
		return ReturnT.SUCCESS;
	}

}
