package org.gz.liquidation.service.coupon;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.coupon.CouponReturnReq;
import org.gz.liquidation.entity.TransactionRecord;

/**
 * 
 * @Description:优惠券服务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月26日 	Created
 */
public interface CouponService {
	/**
	 * 
	 * @Description: 使用优惠券
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月26日
	 */
	ResponseResult<?> use(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 退回优惠券
	 * @param orderSN 订单号
	 * @param couponId 优惠券id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月27日
	 */
	ResponseResult<?> returnCoupon(CouponReturnReq couponReturnReq);
}
