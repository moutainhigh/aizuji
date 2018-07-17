package org.gz.liquidation.service.revenue.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.Enum.SourceTypeEnum;
import org.gz.liquidation.common.Enum.TransactionSourceEnum;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.OrderRepaymentResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.LateFeeEntity;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.mapper.RepaymentScheduleMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.revenue.RentCollectionService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.liquidation.utils.EntityUtils;
import org.gz.liquidation.utils.ManualSettleValidator;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RentCollectionServiceImpl implements RentCollectionService {

	@Autowired
	private RepaymentScheduleMapper repaymentScheduleMapper;

	@Autowired
	private AccountRecordService accountRecordService;

	@Autowired
	private TransactionRecordService transactionRecordService;

	@Resource
	private RepaymentScheduleService repaymentScheduleService;

	@Autowired
	private OrderService orderService;

	@Resource
	private LateFeeService lateFeeService;

	@Resource
	private ManualSettleValidator manualSettleValidator;
	
	@Resource
	private RepaymentService repaymentService;

	@Override
	public ResponseResult<OrderRepaymentResp> queryOrderRepmaymentDetail(String orderSN) {

		// 查询还款详情
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);

		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setOrderSN(orderSN);
		accountRecord.setFlowType(LiquidationConstant.IN);
		accountRecord.setAccountCode(AccountEnum.ZNJ.getAccountCode());
		// 已偿还滞纳金
		BigDecimal totalLateFees = accountRecordService.selectSumAmount(accountRecord);
		
		// 查询减免滞纳金
		accountRecord = new AccountRecord();
		accountRecord.setOrderSN(orderSN);
		accountRecord.setAccountCode(AccountEnum.ZNJJM.getAccountCode());
		// 已偿还滞纳金
		BigDecimal remissionLateFee = accountRecordService.selectSumAmount(accountRecord);

		// 应还滞纳金
		BigDecimal currentLateFees = new BigDecimal(0);
		LateFeeEntity param = new LateFeeEntity();
		param.setOrderSN(orderSN);
		Optional<LateFeeEntity> optional = lateFeeService.selectLateFee(param);
		if (optional.isPresent()) {
			currentLateFees = optional.get().getCurrentBalance();
		}

		// 已偿还租金
		BigDecimal totalRepaymentRent = repaymentService.selectSumActualRepayment(orderSN);

		repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		// 截止到当前应还租金
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		repaymentSchedule.setPaymentDueDate(new Date());

		BigDecimal currentRent = repaymentScheduleMapper.selectSumCurrentBalance(repaymentSchedule);
		if (currentRent == null) {
			currentRent = new BigDecimal(0);
		}

		// 查询最新的还款记录
		repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		RepaymentSchedule rs2 = repaymentScheduleMapper.selectLatest(repaymentSchedule);

		OrderRepaymentResp repaymentResp = new OrderRepaymentResp();
		repaymentResp.setOrderSN(orderSN);
		repaymentResp.setTotalLateFees(totalLateFees);
		repaymentResp.setRemissionLateFee(remissionLateFee);
		repaymentResp.setTotalRepaymentRent(totalRepaymentRent);
		repaymentResp.setCurrentLateFees(currentLateFees);
		repaymentResp.setCurrentRent(currentRent);
		// 设置最新结清的期数
		if (rs2 != null) {
			repaymentResp.setPeriodOf(rs2.getPeriodOf());
			repaymentResp.setPeriods(rs2.getPeriods());
		} else {
			repaymentResp.setPeriodOf(0);
			repaymentResp.setPeriods(0);
		}

		// 查询本期账单日
		ResponseResult<Date> rr = repaymentScheduleService.queryRepaymentDate(orderSN);
		if (rr.isSuccess()) {
			repaymentResp.setRepaymentDate(DateUtils.getDateString(rr.getData()));
		}

		// 查询订单详情
		ResponseResult<OrderDetailResp> responseResult = orderService.queryBackOrderDetail(orderSN);
		log.info(LiquidationConstant.LOG_PREFIX + "queryOrderRepmaymentDetail 调用订单服务查询订单详情:responseResult:{}",
				responseResult);
		if (responseResult.isSuccess()) {

			OrderDetailResp odr = responseResult.getData();
			repaymentResp.setUserId(odr.getUserId());
			repaymentResp.setRealName(odr.getRealName());
			repaymentResp.setPhone(odr.getPhoneNum());
			repaymentResp.setIdentityCard(odr.getIdNo());
			repaymentResp.setRentEndDate(odr.getBackTime());
			repaymentResp.setStateDesc(odr.getStateDesc());

		}

		return ResponseResult.buildSuccessResponse(repaymentResp);
	}

	@Override
	public ResponseResult<List<AccountRecordResp>> queryRepmaymentDetail(String orderSN) {

		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setOrderSN(orderSN);

		List<AccountRecord> list = accountRecordService.selectList(accountRecord);

		Iterator<AccountRecord> it = list.iterator();
		while (it.hasNext()) {

			AccountRecord ar = (AccountRecord) it.next();
			// 过滤out 类型
			if (ar.getFlowType().equals(LiquidationConstant.OUT)) {
				it.remove();
			}
		}

		List<AccountRecordResp> resultList = BeanConvertUtil.convertBeanList(list, AccountRecordResp.class);

		return ResponseResult.buildSuccessResponse(resultList);
	}

	@Override
	public ResponseResult<?> returnManualSettle(ManualSettleReq manualSettleReq) {
		// 执行归还手动清偿
		try {

			String orderSN = manualSettleReq.getOrderSN();
			String transactionSN = manualSettleReq.getTransactionSN();

			ResponseResult<?> rr = manualSettleValidator.validateReturn(orderSN);
			if (!rr.isSuccess()) {
				return rr;
			}

			RepaymentSchedule param = new RepaymentSchedule();
			param.setOrderSN(manualSettleReq.getOrderSN());
			param.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);

			List<RepaymentSchedule> rentList = repaymentScheduleService.selectRepaymentList(param);
			if (CollectionUtils.isEmpty(rentList)) {
				return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_RETURN_DATA_ERROR.getCode(),
						LiquidationErrorCode.MANUAL_SETTLE_RETURN_DATA_ERROR.getMessage(), null);
			}

			AccountRecord accountRecord = EntityUtils.createAccountRecord(manualSettleReq.getCreateBy(), orderSN,
					AccountEnum.GHJ.getAccountCode(), transactionSN, LiquidationConstant.IN,
					manualSettleReq.getAmount(), LiquidationConstant.REMARK_MANUAL_SETTLE_GHJ);
			
			// 归还金科目流水入账
			accountRecordService.addAccountRecord(accountRecord);

			// 记录交易流水
			RepaymentSchedule repaymentSchedule = rentList.get(0);
			TransactionRecord transactionRecord = EntityUtils.createTransactionRecord(manualSettleReq,
					SourceTypeEnum.SALES_ORDER.getCode(), TransactionSourceEnum.BACK_END.getCode(), LiquidationConstant.REMARK_MANUAL_SETTLE_RETURN,
					LiquidationConstant.STATE_SUCCESS, repaymentSchedule.getRealName(), repaymentSchedule.getPhone());
			transactionRecordService.addTransactionRecord(transactionRecord);

			// 调用订单服务更改订单状态
			UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
			updateOrderState.setCreateMan(manualSettleReq.getOperatorRealname());
			updateOrderState.setRentRecordNo(orderSN);
			updateOrderState.setCreateBy(manualSettleReq.getCreateBy());
			updateOrderState.setState(BackRentState.Recycle.getCode());
			ResponseResult<String> responseResult = orderService.updateOrderState(updateOrderState);

			if (!responseResult.isSuccess()) {
				log.error(LiquidationConstant.LOG_PREFIX + " returnManualSettle 调用订单服务返回值responseResult:{}",
						responseResult);
			} else {
				log.info(LiquidationConstant.LOG_PREFIX + " returnManualSettle 调用订单服务返回值responseResult:{}",
						responseResult);
			}

		} catch (Exception e) {
			log.error(LiquidationConstant.LOG_PREFIX + "手动清偿归还异常:{}", e.getLocalizedMessage());
			throw new ServiceException(ResponseStatus.DATABASE_ERROR.getCode(),
					ResponseStatus.DATABASE_ERROR.getMessage());
		}

		return ResponseResult.buildSuccessResponse();
	}

}
