package org.gz.aliOrder.service.jobhandler;

import org.gz.aliOrder.service.AliRentRecordService;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 定时任务查询我司发货的单是否已经签收 Author Version Date Changes 临时工 1.0 2018年3月8日 Created
 */
@JobHander(value = "AliSfStatusJobHandler")
@Component

public class AliSfStatusJobHandler extends IJobHandler {

  @Autowired
  private AliRentRecordService rentRecordService;


	@Override
	public ReturnT<String> execute(String... params) throws Exception {

    XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "AliSfStatusJobHandler start ************************");
    rentRecordService.SfStatusChangeService();
    XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "AliSfStatusJobHandler end ************************");
		
		return ReturnT.SUCCESS;
	}

}
