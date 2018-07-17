package org.gz.liquidation.service.revenue;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.OrderRepaymentResp;

/**
 * 
 * @Description:	租后收款服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月19日 	Created
 */
public interface RentCollectionService {
	/**
	 * 
	 * @Description: 查询订单还款详情
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月19日
	 */
	public ResponseResult<OrderRepaymentResp> queryOrderRepmaymentDetail(String orderSN);
	/**
	 * 
	 * @Description: 查询订单还款科目明细
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月19日
	 */
	public ResponseResult<List<AccountRecordResp>> queryRepmaymentDetail(String orderSN);
	/**
	 * 
	 * @Description: 手动清偿-> 归还
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月7日
	 */
	public ResponseResult<?> returnManualSettle(ManualSettleReq manualSettleReq);
}
