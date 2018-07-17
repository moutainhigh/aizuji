package org.gz.oss.backend.task;


import javax.annotation.Resource;

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
@JobHander(value="SupplementJobHandler")
@Component
public class SupplementJobHandler extends IJobHandler {

	@Resource
	private SupplementTaskUtils supplementTaskUtils;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		// 补仓任务
		XxlJobLogger.log("supplementTaskUtils 补仓任务开始运行......");

		supplementTaskUtils.supplementTask();
		
		XxlJobLogger.log("supplementTaskUtils 补仓任务开始结束......");
		
		return ReturnT.SUCCESS;
	}

}
