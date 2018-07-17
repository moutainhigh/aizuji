package org.gz.order.api.service.jobhandler;

import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.order.api.service.IRentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 定时任务查询我司发货的单是否已经签收 Author Version Date Changes 临时工 1.0 2018年3月13日 Created
 */
@JobHander(value = "CancelNotPayOrderJobHandler")
@Component

public class CancelNotPayOrderJobHandler extends IJobHandler {

  @Autowired
  private IRentRecordService rentRecordService;


	@Override
	public ReturnT<String> execute(String... params) throws Exception {
    XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "CancelNotPayOrderJobHandler start ************************");
    long starttime = System.currentTimeMillis();
    rentRecordService.checkWatiPaymentJob();
    long endTime = System.currentTimeMillis() - starttime;
    XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "CancelNotPayOrderJobHandler end 耗时" + endTime
                     + "ms************************");
		return ReturnT.SUCCESS;
	}

}
