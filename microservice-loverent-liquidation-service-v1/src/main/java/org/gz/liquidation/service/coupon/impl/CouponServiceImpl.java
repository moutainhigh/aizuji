package org.gz.liquidation.service.coupon.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.dto.coupon.CouponReturnReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.CouponUseLogEntity;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.feign.ICouponService;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.coupon.CouponService;
import org.gz.liquidation.service.coupon.CouponUseLogService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;
import org.gz.oss.common.entity.CouponUseParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private ICouponService iCouponService;
	
	@Autowired
	private AccountRecordService accountRecordService;
	
	@Resource
	private CouponUseLogService couponUseLogService;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public ResponseResult<?> use(TransactionRecord transactionRecord) {
		Long couponId = Long.valueOf(transactionRecord.getAttr1());
		CouponQuery cq = new CouponQuery();
		cq.setCouponId(couponId);
		cq.setUserId(transactionRecord.getCreateBy());
		// 查询优惠券
		ResponseResult<Coupon> responseResult = iCouponService.queryCouponDetail(cq);
		log.info(LiquidationConstant.LATE_FEE_REMISSION_REMARK+"调用运营服务查询优惠券状态返回:{}",JsonUtils.toJsonString(responseResult));
		
		if(responseResult.isSuccess()){
			Coupon coupon = responseResult.getData();
			
			Long userId = transactionRecord.getCreateBy();
			String orderSN = transactionRecord.getOrderSN();
			// 记录优惠券科目
			String fundsSN = UUIDUtils.genDateUUID(AccountEnum.YHQ.getAccountCode());
			AccountRecord accountRecord = new AccountRecord();
			accountRecord.setAmount(transactionRecord.getCouponFee());
			accountRecord.setCreateBy(userId);
			accountRecord.setCreateOn(new Date());
			accountRecord.setTransactionSN(transactionRecord.getTransactionSN());
			accountRecord.setOrderSN(orderSN);
			accountRecord.setAccountCode(AccountEnum.YHQ.getAccountCode());
			accountRecord.setFlowType(LiquidationConstant.OUT);
			accountRecord.setFundsSN(fundsSN);
			accountRecord.setRemark(LiquidationConstant.REMARK_COUPON);
			accountRecordService.addAccountRecord(accountRecord);
			
			CouponUseLogEntity couponUseLogEntity = new CouponUseLogEntity();
			couponUseLogEntity.setCouponId(couponId);
			couponUseLogEntity.setOrderSN(orderSN);
			couponUseLogEntity.setUserId(userId);
			couponUseLogEntity.setAmount(transactionRecord.getCouponFee());
			couponUseLogEntity.setRealName(transactionRecord.getRealName());
			couponUseLogEntity.setPhone(transactionRecord.getPhone());
			// 优惠使用场景
			couponUseLogEntity.setUsageScenario(coupon.getCouponType().intValue());
			couponUseLogEntity.setFundsSN(fundsSN);
			ResponseResult<OrderDetailResp> rr = orderService.queryBackOrderDetail(transactionRecord.getOrderSN());
			if(rr.isSuccess()){
				couponUseLogEntity.setProductModel(rr.getData().getMaterielModelName());
				couponUseLogEntity.setProductType(rr.getData().getProductType().byteValue());
			}
			// 记录优惠券使用记录()
			couponUseLogService.insertSelective(couponUseLogEntity);
			
			CouponUseParam couponUseParam = new CouponUseParam();
			couponUseParam.setUserId(userId);
			couponUseParam.setCouponId(couponId);
			couponUseParam.setUserName(transactionRecord.getPhone());
			couponUseParam.setOrderNo(orderSN);
			// 调用运营服务修改优惠券为已使用
			ResponseResult<String>  responseResult2 = iCouponService.useCoupon(couponUseParam);
			log.info(LiquidationConstant.LATE_FEE_REMISSION_REMARK+"调用运营服务修改优惠券状态返回:{}",JsonUtils.toJsonString(responseResult2));
			
		}else{
			
			throw new ServiceException(LiquidationErrorCode.DOWN_PAYMENT_COUPON_ERROR.getCode(),
					LiquidationErrorCode.DOWN_PAYMENT_COUPON_ERROR.getMessage());
		}
		
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<?> returnCoupon(CouponReturnReq couponReturnReq) {
		CouponUseLogEntity couponUseLogEntity = new CouponUseLogEntity();
		couponUseLogEntity.setCouponId(couponReturnReq.getCouponId());
		couponUseLogEntity.setOrderSN(couponReturnReq.getOrderSN());
		// 查询优惠券记录
		List<CouponUseLogEntity> list = couponUseLogService.selectByCondition(couponUseLogEntity);
		
		if(list.isEmpty() || list.size() > 1){
			return ResponseResult.build(LiquidationErrorCode.COUPON_RETURN_ERROR.getCode(),
					LiquidationErrorCode.COUPON_RETURN_ERROR.getMessage(), null);
		}
		
		CouponUseLogEntity e = list.get(0);
		// 退还
		e.setUsageScenario(LiquidationConstant.COUPON_USAGE_SCENARIO_RETURN);
		e.setCreateOn(new Date());
		
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setFundsSN(e.getFundsSN());
		
		List<AccountRecord> accountRecordList = accountRecordService.selectList(accountRecord);
		if(accountRecordList.isEmpty()){
			return ResponseResult.build(LiquidationErrorCode.COUPON_RETURN_ERROR.getCode(),
					LiquidationErrorCode.COUPON_RETURN_ERROR.getMessage(), null);
		}
		
		couponUseLogService.insertSelective(e);
		
		// 新增优惠券退还科目流水
		AccountRecord data = accountRecordList.get(0);
		data.setFlowType(LiquidationConstant.IN);
		data.setCreateOn(new Date());
		accountRecordService.addAccountRecord(data);
		
		CouponUseParam cup = new CouponUseParam();
		cup.setCouponId(couponReturnReq.getCouponId());
		cup.setUserId(e.getUserId());
		// 调用运营退还用户优惠券
		iCouponService.useReturnCoupon(cup);
		return ResponseResult.buildSuccessResponse();
	}

}
