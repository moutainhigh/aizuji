package org.gz.liquidation.service.repayment;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.entity.TransactionRecord;

/**
 * 
 * @Description:TODO	应收账款服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月13日 	Created
 */
public interface ReceivablesService {
	/**
	 * 
	 * @Description: TODO 首期支付
	 * @param transactionRecord
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月13日
	 */
	ResponseResult<?> payDownpayment(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: TODO 支付回调或主动查询订单支付状态
	 * @param transactionRecord
	 * @param state
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月23日
	 */
	public ResponseResult<?> payCallBackHandler(TransactionRecord transactionRecord,String state);
}
