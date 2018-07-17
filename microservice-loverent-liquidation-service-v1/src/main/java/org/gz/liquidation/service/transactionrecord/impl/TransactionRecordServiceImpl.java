package org.gz.liquidation.service.transactionrecord.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.Enum.TransactionSourceEnum;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.Enum.alipay.AlipayTradeStatusEnum;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PaymentReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.TransactionRecordQueryReq;
import org.gz.liquidation.common.dto.TransactionRecordReq;
import org.gz.liquidation.common.dto.TransactionRecordResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.feign.IAlipayService;
import org.gz.liquidation.mapper.TransactionRecordMapper;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.repayment.ReceivablesService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.liquidation.utils.ManualSettleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.response.AlipayTradeQueryResponse;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class TransactionRecordServiceImpl implements TransactionRecordService {
	
	@Resource
	private TransactionRecordMapper transactionRecordMapper;
	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	@Resource
	private ReceivablesService receivablesService;
	@Resource
	private RepaymentService repaymentService;
	@Resource
	private LateFeeService lateFeeService;
	@Resource
	private ManualSettleValidator manualSettleValidator;
	@Autowired
	private IAlipayService iAlipayService;
	@Override
	public void addTransactionRecord(TransactionRecord transactionRecord) {
		transactionRecordMapper.insertSelective(transactionRecord);
	}

	@Override
	public void updateState(TransactionRecord transactionRecord) {
		transactionRecordMapper.updateStateSelective(transactionRecord);
	}

	@Override
	public ResultPager<TransactionRecordResp> selectPage(QueryDto queryDto) {
		List<TransactionRecord> resultList = transactionRecordMapper.selectPage(queryDto);
		List<TransactionRecordResp> list = BeanConvertUtil.convertBeanList(resultList, TransactionRecordResp.class);
		Page page = queryDto.getPage();
		ResultPager<TransactionRecordResp> resultPager =  new ResultPager<>(page.getTotalNum(), page.getStart(), page.getPageSize(), list);
		return resultPager;
	}

	@Override
	public List<TransactionRecord> selectByTransactionRecord(TransactionRecord transactionRecord) {
		return transactionRecordMapper.selectByTransactionRecord(transactionRecord);
	}

	@Override
	public TransactionRecord selectOneById(Long id) {
		return transactionRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int selectCountStatistics(TransactionRecord transactionRecord) {
		return transactionRecordMapper.selectCountStatistics(transactionRecord);
	}

	@Override
	public BigDecimal selectSumAmount(TransactionRecord transactionRecord) {
		return transactionRecordMapper.selectSumAmount(transactionRecord);
	}

	@Transactional
	@Override
	public ResponseResult<?> payCorrected(TransactionRecordReq req) {
		ResponseResult<?> responseResult = new ResponseResult<>();
		TransactionRecord transactionRecord = selectOneById(req.getId());
		
		String transactionType = transactionRecord.getTransactionType();
		TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.getEnum(transactionType);
		switch (transactionTypeEnum) {
		case BUYOUT:// 买断
			
			PaymentReq paymentReq = new PaymentReq();
			paymentReq.setTransactionSN(transactionRecord.getTransactionSN());
			// 设置支付状态为成功
			paymentReq.setState(LiquidationConstant.STATE_SUCCESS);
			paymentReq.setRemark(req.getRemark());
			paymentReq.setFinishTime(req.getEndDate());
			responseResult = repaymentScheduleService.buyoutCallback(paymentReq);
			break;
		case FIRST_RENT:// 首期款
			
			responseResult = receivablesService.payDownpayment(transactionRecord);
			break;
		case RENT:// 租金缴纳
			
			responseResult = repaymentService.repayRent(transactionRecord);
			break;

		default:
			
			break;
		}
			
		Date now = new Date();
		transactionRecord.setState(LiquidationConstant.STATE_SUCCESS);
		transactionRecord.setUpdateOn(now);
		transactionRecord.setFinishTime(req.getEndDate());
		transactionRecord.setRemark(req.getRemark());
		this.updateState(transactionRecord);
		return responseResult;
	}

	@Override
	public ResponseResult<?> verifyTransaction(TransactionRecord transactionRecord) {
		List<TransactionRecord> list = this.selectByTransactionRecord(transactionRecord);
		
		String transactionType = transactionRecord.getTransactionType();
		String orderSN = transactionRecord.getOrderSN();
		
		TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.getEnum(transactionType);
		switch (transactionTypeEnum) {
		case FIRST_RENT:// 首期款
			
			for (TransactionRecord tr : list) {
				if (tr.getState() == 2) {
					return ResponseResult.build(LiquidationErrorCode.PAYMENT_DOWNPAYMENT_REPEAT_ERROR.getCode(),
							LiquidationErrorCode.PAYMENT_DOWNPAYMENT_REPEAT_ERROR.getMessage(), null);
				}
			}
			break;
		case BUYOUT:
			
			for (TransactionRecord tr : list) {
				if (tr.getState() == 2) {
					return ResponseResult.build(LiquidationErrorCode.PAYMENT_BUYOUT_REPEAT_ERROR.getCode(),
							LiquidationErrorCode.PAYMENT_BUYOUT_REPEAT_ERROR.getMessage(), null);
				}
			}
			break;
		case RENT:// 租金支付
			
			return manualSettleValidator.validateRepayRent(orderSN, transactionRecord.getTransactionAmount());
		case SALE:// TODO 校验待加
			
			break;

		default:
			
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<PreparePayResp> preparePay(PayReq payReq) {
		PreparePayResp preparePayResp = new PreparePayResp();
		// 验证交易是否合理
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setOrderSN(payReq.getOrderSN());
		transactionRecord.setTransactionType(payReq.getTransactionType());
		transactionRecord.setTransactionAmount(payReq.getAmount());
		
		ResponseResult<?> responseResult = this.verifyTransaction(transactionRecord);
		if(!responseResult.isSuccess()){
			return ResponseResult.build(responseResult.getErrCode(), responseResult.getErrMsg(), null);
		}
		
		// 生成交易流水记录
		Date date = new Date();
		transactionRecord.setTransactionAmount(payReq.getAmount());
		transactionRecord.setSourceType(payReq.getSourceType());
		transactionRecord.setTransactionSource(payReq.getTransactionSource());
		transactionRecord.setTransactionWay(payReq.getTransactionWay());
		transactionRecord.setCreateBy(payReq.getUserId());
		transactionRecord.setRealName(payReq.getRealName());
		transactionRecord.setPhone(payReq.getPhone());
		transactionRecord.setCreateUsername(payReq.getUsername());
		transactionRecord.setUpdateUsername(payReq.getUsername());
		transactionRecord.setFromAccount(payReq.getFromAccount());
		// 判断是否有优惠券
		if(payReq.getCouponId() != null){
			transactionRecord.setAttr1(String.valueOf(payReq.getCouponId()));
		}
		if(payReq.getCouponFee() !=null ){
			transactionRecord.setCouponFee(payReq.getCouponFee());
		}
		
		// 生成交易流水号
		String transactionSN = UUIDUtils.genDateUUID("");
		preparePayResp.setTransactionSN(transactionSN);
		transactionRecord.setTransactionSN(transactionSN);
		transactionRecord.setVersion(UUIDUtils.genDateUUID(""));
		transactionRecord.setCreateOn(date);
		transactionRecord.setUpdateOn(date);
		transactionRecord.setState(LiquidationConstant.STATE_TRADING);
		this.addTransactionRecord(transactionRecord);
		
		preparePayResp.setTimestamp(DateUtils.getString(date, "yyyyMMddHHmmss"));
		return ResponseResult.buildSuccessResponse(preparePayResp);
	}

	@Override
	public Boolean validateTrade(String orderSN) {
		
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setOrderSN(orderSN);
		transactionRecord.setState(LiquidationConstant.STATE_TRADING);
		List<TransactionRecord> list = selectByTransactionRecord(transactionRecord);
		
		if(CollectionUtils.isNotEmpty(list)){
			return false;
		}
		
		return true;
	}

	@Override
	public ResponseResult<TransactionRecordResp> queryLatest(String orderSN) {
		
		TransactionRecord transactionRecord = transactionRecordMapper.queryLatest(orderSN);
		TransactionRecordResp transactionRecordResp = BeanConvertUtil.convertBean(transactionRecord, 
				TransactionRecordResp.class);
		
		return ResponseResult.buildSuccessResponse(transactionRecordResp);
	}

	@Override
	public ResponseResult<?> queryStatistics(TransactionRecordQueryReq req) {
		
		TransactionRecord transactionRecord = BeanConvertUtil.convertBean(req, TransactionRecord.class);
		transactionRecord.setState(LiquidationConstant.STATE_SUCCESS);
		int count = transactionRecordMapper.selectCount(transactionRecord);
		BigDecimal amount = selectSumAmount(transactionRecord);
		
		Map<String,Object> map = new HashMap<>();
		map.put("count", count);
		map.put("amount", amount);
		
		return ResponseResult.buildSuccessResponse(map);
	}

	@Override
	public void queryAlipayTrade(List<TransactionRecord> list) {
		for (TransactionRecord tr : list) {
				
			ResponseResult<?> rr = iAlipayService.queryTrade(tr.getTransactionSN(), "", tr.getTransactionSource());
			log.info(LiquidationConstant.LOG_PREFIX+"queryAlipayTrade response:{}",JsonUtils.toJsonString(rr));
			
			if (rr.isSuccess()) {
				AlipayTradeQueryResponse response = (AlipayTradeQueryResponse) rr.getData();
					if(response.isSuccess()){
						
						if(response.getCode().equals(LiquidationConstant.ALIPAY_GATEWAY_CODE_10000)){
							
							AlipayTradeStatusEnum alipayTradeEnum = AlipayTradeStatusEnum.getEnum(response.getTradeStatus());
							switch (alipayTradeEnum) {
							case TRADE_SUCCESS:
								
								TransactionSourceEnum transactionSourceEnum = TransactionSourceEnum.getEnum(tr.getTransactionSource());
								switch (transactionSourceEnum) {
								case APP:
									
									receivablesService.payCallBackHandler(tr, LiquidationConstant.SUCCESS);
									break;
								case ALI_APPLET:
									
									// TODO 需要调用订单服务查询订单信息，生成还款计划
									log.info(LiquidationConstant.LOG_PREFIX+"transactionSN:{} orderSN:{}",tr.getTransactionSN(),tr.getOrderSN());
									ZmRepaymentScheduleReq zmRepaymentScheduleReq = new ZmRepaymentScheduleReq();
									repaymentService.addZmRepaymentSchedule(zmRepaymentScheduleReq);
									break;

								default:
									break;
								}
								
								break;
							case TRADE_CLOSED:
								// 作废交易信息
								tr.setFailureDesc(response.getMsg());
								cancelTrade(tr);
								break;

							default:
								break;
							}
							
						}else if(response.getCode().equals(LiquidationConstant.ALIPAY_GATEWAY_CODE_40004)){
							
							if(response.getSubCode().equals(LiquidationConstant.ALIPAY_SUB_CODE_TRADE_NOT_EXIST)){// 交易不存在
								tr.setFailureDesc(response.getSubMsg());
								tr.setResultCode(response.getSubCode());
								// 作废交易信息
								cancelTrade(tr);
							}
						}
						
					}
						
			}
		}
	}
	
	private void cancelTrade(TransactionRecord transactionRecord){
		transactionRecord.setState(LiquidationConstant.STATE_CANCEL);
		updateState(transactionRecord);
	}

}
