package org.gz.order.api.service.jobhandler;

import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.order.api.service.IRentRecordService;
import org.gz.order.api.service.IShunFengService;
import org.gz.order.server.service.RentLogisticsService;
import org.gz.order.server.service.RentRecordExtendsService;
import org.gz.order.server.service.RentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 定时任务查询我司发货的单是否已经签收 Author Version Date Changes 临时工 1.0 2018年3月8日 Created
 */
@JobHander(value = "SfStatusJobHandler")
@Component

public class SfStatusJobHandler extends IJobHandler {

  @Autowired
  private IRentRecordService rentRecordService;


	@Override
	public ReturnT<String> execute(String... params) throws Exception {

    XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "SfStatusJobHandler start ************************");
    rentRecordService.SfStatusChangeService();
    XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "SfStatusJobHandler end ************************");
		
		return ReturnT.SUCCESS;
	}

}
