package org.gz.liquidation.service.jobhandler;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.liquidation.common.Enum.TransactionWayEnum;
import org.gz.liquidation.common.dto.lianlian.LianlianOrderQueryResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.feign.ILianlianService;
import org.gz.liquidation.service.repayment.ReceivablesService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;


/**
 * 提额定时任务Handler的（Bean模式）
 * 1、新建一个继承com.xxl.job.core.handler.IJobHandler的Java类；
 * 2、该类被Spring容器扫描为Bean实例，如加“@Component”注解；
 * 3、添加 “@JobHander(value="自定义jobhandler名称")”注解，注解的value值为自定义的JobHandler名称，该名称对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 * @Description: 连连支付定时任务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年10月24日 	Created
 */
@JobHander(value="LianlianPayQueryJobHandler")
@Component
public class LianlianPayQueryJobHandler extends IJobHandler {
	
    @Autowired
	private TransactionRecordService transactionRecordService;
    @Autowired
    private ReceivablesService receivablesService;
    @Autowired
    private ILianlianService iLianlianService;
    
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		XxlJobLogger.log("LianlianPayQueryJobHandler start ************************");
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setState(LiquidationConstant.STATE_TRADING);
		transactionRecord.setTransactionWay(TransactionWayEnum.LIANLIAN.getCode());
		// 查询交易状态未完成的流水
		List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(transactionRecord);
		for (TransactionRecord tr : list) {
			ResponseResult<LianlianOrderQueryResp> rr = iLianlianService.queryorder(tr.getOrderSN(), DateUtils.getString(tr.getCreateOn(), "yyyyMMddHHmmss"));
			LianlianOrderQueryResp orderQueryRespBean = rr.getData();
			XxlJobLogger.log("返回结果:"+JsonUtils.toJsonString(orderQueryRespBean));
			if(orderQueryRespBean != null ){
				String state = orderQueryRespBean.getResult_pay();
				if(LiquidationConstant.SUCCESS.equalsIgnoreCase(state) || LiquidationConstant.FAILURE.equalsIgnoreCase(state)){// 只处理支付成功或失败
					receivablesService.payCallBackHandler(tr, state);
				}
			}
		}
		XxlJobLogger.log("LianlianPayQueryJobHandler end ************************");
		return ReturnT.SUCCESS;
	}
	
}
