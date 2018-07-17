package org.gz.liquidation.service.repayment.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gz.common.constants.SmsType;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.Enum.SourceTypeEnum;
import org.gz.liquidation.common.Enum.TransactionSourceEnum;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.Enum.TransactionWayEnum;
import org.gz.liquidation.common.Enum.alipay.AlipayOrderOperateType;
import org.gz.liquidation.common.Enum.alipay.AlipayStatusEnum;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.dto.SettleDetailResp;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayResponse;
import org.gz.liquidation.common.dto.repayment.ZmStatementDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.RepaymentDetailEntity;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.feign.IAliOrderService;
import org.gz.liquidation.feign.IAlipayService;
import org.gz.liquidation.mapper.RepaymentScheduleMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.repayment.detail.RepaymentDetailService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.liquidation.utils.EntityUtils;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.ZhimaMerchantOrderCreditPayResponse;
import com.xxl.job.core.log.XxlJobLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RepaymentServiceImpl implements RepaymentService {

	@Resource
	private RepaymentScheduleMapper repaymentScheduleMapper;

	@Resource
	private RepaymentScheduleService repaymentScheduleService;

	@Autowired
	private AccountRecordService accountRecordService;

	@Autowired
	private OrderService orderService;

	@Resource
	private LateFeeService lateFeeService;

	@Resource
	private RepaymentDetailService repaymentDetailService;

	@Autowired
	private SmsSendService smsSendService;

	@Resource
	private TransactionRecordService transactionRecordService;

	@Autowired
	private IAliOrderService iAliOrderService;
	
	@Autowired
	private IAlipayService iAlipayService;
	
	@Override
	public int queryCurrentRentIsSettled(RepaymentSchedule repaymentSchedule) {
		return repaymentScheduleMapper.queryCurrentRentIsSettled(repaymentSchedule);
	}

	@Transactional
	@Override
	public ResponseResult<?> repayRent(TransactionRecord transactionRecord) {
		log.info(LiquidationConstant.LOG_PREFIX + "repayRent param：{}", transactionRecord);
		String orderSN = transactionRecord.getOrderSN();
		Date date = new Date();

		this.repay(transactionRecord, LiquidationConstant.SETTLEWAY_RENT);

		// 修改订单状态
		ResponseResult<Date> responseResult = repaymentScheduleService.queryRepaymentDate(orderSN);
		// 本期还款日期
		Date dueDate = responseResult.getData();
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setPaymentDueDate(dueDate);
		int count = this.queryCurrentRentIsSettled(repaymentSchedule);

		if (count > 0) {

			try {
				// 判断是否履约完成
				boolean flag = queryOrderIsSettled(orderSN);

				UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
				updateOrderState.setRentRecordNo(transactionRecord.getOrderSN());
				updateOrderState.setState(BackRentState.NormalPerformance.getCode());
				if (flag) {
					// 履约完成
					updateOrderState.setState(BackRentState.EndPerformance.getCode());
				}
				updateOrderState.setCreateBy(transactionRecord.getCreateBy());
				updateOrderState.setCreateMan(transactionRecord.getRealName());
				// 更改订单状态为正常履约
				ResponseResult<String> responseResult2 = orderService.updateOrderState(updateOrderState);

				log.info(LiquidationConstant.LOG_PREFIX + "repayRent 调用订单服务更新订单状态返回结果:{}",
						JsonUtils.toJsonString(responseResult2));
			} catch (Exception e) {
				log.error(LiquidationConstant.LOG_PREFIX + "repayRent 调用订单服务更新订单状态异常:{}", e.getMessage());
			}

		} else {// 更改订单状态为逾期

			try {

				if (dueDate.compareTo(date) == 0) {// 如果本日是交租日
					List<String> rentRecordNos = new ArrayList<>();
					rentRecordNos.add(orderSN);
					ResponseResult<String> responseResult2 = orderService.batchUpdateOverDue(rentRecordNos);
					log.info(LiquidationConstant.LOG_PREFIX + "repayRent 调用订单服务更新订单状态为逾期返回结果:{}",
							JsonUtils.toJsonString(responseResult2));
				}

			} catch (Exception e) {
				log.error(LiquidationConstant.LOG_PREFIX + "repayRent 调用订单服务更新订单状态为逾期异常:{}", e.getMessage());
			}
		}

		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<?> sell(TransactionRecord transactionRecord) {
		// 记录销售金科目入账
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setAccountCode(AccountEnum.XSJ.getAccountCode());
		accountRecord.setAmount(transactionRecord.getTransactionAmount());
		accountRecord.setCreateBy(transactionRecord.getCreateBy());
		accountRecord.setRemark(LiquidationConstant.REMARK_SALE);
		accountRecord.setFlowType(LiquidationConstant.IN);
		accountRecord.setCreateOn(new Date());
		accountRecord.setFundsSN(UUIDUtils.genDateUUID(AccountEnum.XSJ.getAccountCode()));
		accountRecord.setTransactionSN(transactionRecord.getTransactionSN());
		accountRecord.setOrderSN(transactionRecord.getOrderSN());
		// 销售金科目流水入账
		accountRecordService.addAccountRecord(accountRecord);

		// 调用订单服务
		try {

			UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
			updateOrderState.setRentRecordNo(transactionRecord.getOrderSN());
			updateOrderState.setCreateBy(transactionRecord.getCreateBy());
			updateOrderState.setCreateMan(transactionRecord.getRealName());

			ResponseResult<String> responseResult2 = orderService.paySuccess(updateOrderState);
			log.info(LiquidationConstant.LOG_PREFIX + "repayRent 调用订单服务更新订单状态返回结果:{}",
					JsonUtils.toJsonString(responseResult2));
		} catch (Exception e) {
			log.error(LiquidationConstant.LOG_PREFIX + "sell 调用订单服务异常:{}", e.getMessage());
		}

		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public void batchDisableLateFeeData(List<RepaymentSchedule> list) {
		repaymentScheduleMapper.batchDisableLateFeeData(list);
	}

	@Override
	public boolean queryOrderIsSettled(String orderSN) {
		boolean flag = false;
		int num = repaymentScheduleMapper.queryOrderIsSettled(orderSN);

		if (num == 0) {
			flag = true;
		}

		return flag;
	}

	@Override
	public RepaymentSchedule selectLatestSettledRent(String orderSN) {
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);

		return repaymentScheduleMapper.selectLatest(repaymentSchedule);
	}

	@Override
	public int sumOverdueDay(RepaymentSchedule repaymentSchedule) {
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		return repaymentScheduleMapper.sumOverdueDay(repaymentSchedule);
	}

	@Override
	public List<RepaymentSchedule> selectOverdueList(Date date) {
		return repaymentScheduleMapper.selectOverdueList(date);
	}

	@Override
	public BigDecimal selectSumActualRepayment(String orderSN) {
		BigDecimal amount = repaymentScheduleMapper.selectSumActualRepayment(orderSN);
		if (amount == null) {
			amount = new BigDecimal(0);
		}
		return amount;
	}

	/**
	 * 
	 * @Description: 构建科目流水实体
	 * @param userId
	 * @param orderSN
	 * @param accountCode
	 * @param transactionSN
	 * @param flowType
	 * @param amount
	 * @param remark(非必填)
	 * @return
	 * @throws createBy:liaoqingji
	 *             createDate:2018年3月13日
	 */
	private AccountRecord createAccountRecord(Long userId, String orderSN, String accountCode, String transactionSN,
			String flowType, BigDecimal amount, String remark) {
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setTransactionSN(transactionSN);
		accountRecord.setOrderSN(orderSN);
		accountRecord.setFundsSN(UUIDUtils.genDateUUID(accountCode));
		accountRecord.setCreateBy(userId);
		accountRecord.setAccountCode(accountCode);
		accountRecord.setCreateOn(new Date());
		accountRecord.setFlowType(flowType);
		accountRecord.setAmount(amount);
		if (StringUtils.isNotBlank(remark)) {
			accountRecord.setRemark(remark);
		}
		return accountRecord;

	}

	@Override
	public List<RepaymentSchedule> repay(TransactionRecord transactionRecord, String settleWay) {
		// 偿还租金和滞纳金,先偿还滞纳金再偿还租金
		String orderSN = transactionRecord.getOrderSN();
		String transactionSN = transactionRecord.getTransactionSN();
		Long userId = transactionRecord.getCreateBy();

		List<AccountRecord> accountList = new ArrayList<>();
		List<RepaymentDetailEntity> repaymentDetailList = new ArrayList<>();
		// 本次支付金额
		BigDecimal amount = transactionRecord.getTransactionAmount();
		RepaymentReq repaymentReq = new RepaymentReq();
		repaymentReq.setAmount(amount);
		repaymentReq.setTransactionSN(transactionSN);
		// 偿还滞纳金
		lateFeeService.repayLateFee(repaymentReq, userId, orderSN);
		if (repaymentReq.getAmount().compareTo(amount) != 0) {// 进行过偿还滞纳金,记录滞纳金科目入账

			BigDecimal lateFessUse = amount.subtract(repaymentReq.getAmount());
			AccountRecord accountRecord = this.createAccountRecord(userId, orderSN, AccountEnum.ZNJ.getAccountCode(),
					transactionSN, LiquidationConstant.IN, lateFessUse, LiquidationConstant.REMARK_REPAY_LATEFEE);
			accountList.add(accountRecord);

		}

		BigDecimal balance = repaymentReq.getAmount();
		List<RepaymentSchedule> settledRentList = new ArrayList<>();
		if (balance.compareTo(new BigDecimal(0)) == 1) {// 金额还有剩余则继续偿还租金

			RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
			repaymentSchedule.setOrderSN(orderSN);
			repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
			repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
			repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
			List<RepaymentSchedule> rentList = repaymentScheduleService.selectList(repaymentSchedule);

			if (CollectionUtils.isNotEmpty(rentList)) {

				settledRentList = repaymentScheduleService.chargeOff(repaymentReq, rentList, settleWay);
				// 核销租金使用金额
				BigDecimal rentUsed = balance.subtract(repaymentReq.getAmount());
				// 更新金额
				transactionRecord.setTransactionAmount(repaymentReq.getAmount());

				if (CollectionUtils.isNotEmpty(settledRentList)) {
					// 批量更新还款计划
					repaymentScheduleService.updateBatch(settledRentList);

					for (RepaymentSchedule rs : settledRentList) {
						RepaymentDetailEntity e = EntityUtils.createRepaymentDetailEntity(userId, orderSN,
								AccountEnum.ZJ.getAccountCode(), transactionSN, rs.getPaymentDueDate(),
								rs.getActualRepayment(), LiquidationConstant.REMARK_REPAY_RENT);
						// 租金还款明细
						repaymentDetailList.add(e);
					}
				}

				// 记录租金科目入账
				AccountRecord accountRecord = this.createAccountRecord(userId, orderSN, AccountEnum.ZJ.getAccountCode(),
						transactionSN, LiquidationConstant.IN, rentUsed, LiquidationConstant.REMARK_REPAY_RENT);
				accountList.add(accountRecord);

			}

		}

		// 新增科目记录
		if (!accountList.isEmpty()) {
			accountRecordService.insertBatch(accountList);
		}
		// 新增还款明细
		if (!repaymentDetailList.isEmpty()) {
			repaymentDetailService.insertBatch(repaymentDetailList);
		}

		return settledRentList;
	}

	@Override
	public ResponseResult<SettleDetailResp> settleDetail(String orderSN) {
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setOrderSN(orderSN);
		List<RepaymentSchedule> list = repaymentScheduleMapper.selectList(repaymentSchedule);

		SettleDetailResp settleDetailResp = null;
		if (CollectionUtils.isNotEmpty(list)) {

			settleDetailResp = new SettleDetailResp();
			// 查询账期
			ResponseResult<Date> rr = repaymentScheduleService.queryRepaymentDate(orderSN);
			Date repaymentDate = rr.getData();

			Integer periodOf = null;
			Integer periods = null;

			// 账单开始日期
			settleDetailResp.setBillStart(list.get(0).getPaymentDueDate());
			settleDetailResp.setBillEnd(list.get(list.size() - 1).getPaymentDueDate());
			BigDecimal rentPayable = this.sumRent(orderSN);

			for (RepaymentSchedule rs : list) {

				int repaymentType = rs.getRepaymentType();

				if (repaymentDate.equals(rs.getPaymentDueDate())
						&& repaymentType == LiquidationConstant.REPAYMENT_TYPE_RENT) {
					settleDetailResp.setState(rs.getState());
					periodOf = rs.getPeriodOf();
					periods = rs.getPeriods();

					break;
				}

			}

			// 查询减免金额
			BigDecimal remissionFee = accountRecordService.queryRemissionFee(orderSN);
			settleDetailResp.setRemissionFee(remissionFee);

			BigDecimal lateFee = lateFeeService.lateFeePayable(orderSN);
			settleDetailResp.setLateFee(lateFee.add(remissionFee));
			// 应付租金总额
			settleDetailResp.setTotalRentPayable(rentPayable);

			// 截止到本期逾期天数
			repaymentSchedule.setPaymentDueDate(repaymentDate);
			int overdueDay = sumOverdueDay(repaymentSchedule);
			settleDetailResp.setOverdueDay(overdueDay);

			StringBuffer sb = new StringBuffer();
			sb.append(periodOf);
			sb.append("/");
			sb.append(periods);
			// 账期格式 3/12
			settleDetailResp.setBill(sb.toString());

			// 已付租金总额
			BigDecimal totalRentPaid = this.selectSumActualRepayment(orderSN);
			// 账单余额
			settleDetailResp.setTotalRentPaid(totalRentPaid);

			// 应付总额= 应付租金总额-实付租金总额+滞纳金
			BigDecimal currentBalance = new BigDecimal(0);
			currentBalance = rentPayable.subtract(totalRentPaid).add(settleDetailResp.getLateFee());
			settleDetailResp.setCurrentBalance(currentBalance);

			settleDetailResp.setStatementBalance(currentBalance.subtract(remissionFee));

		}

		return ResponseResult.buildSuccessResponse(settleDetailResp);
	}

	@Override
	public BigDecimal sumRent(String orderSN) {
		return repaymentScheduleMapper.sumRent(orderSN);
	}

	@Override
	public ResponseResult<?> addZmRepaymentSchedule(ZmRepaymentScheduleReq zmRepaymentScheduleReq) {
		Integer periods = zmRepaymentScheduleReq.getPeriods();
		List<RepaymentSchedule> list = new ArrayList<>(periods);

		// 计算还款日期
		Calendar c = Calendar.getInstance();
		c.setTime(zmRepaymentScheduleReq.getCreateDate());
		// 生成还款计划数据
		for (int i = 0; i < periods; i++) {
			c.add(Calendar.MONTH, i);
			Date paymentDueDate = c.getTime();
			RepaymentSchedule repaymentSchedule = EntityUtils.createRepaymentSchedule(zmRepaymentScheduleReq, i + 1,
					paymentDueDate);
			list.add(repaymentSchedule);

			c.setTime(zmRepaymentScheduleReq.getCreateDate());
		}

		repaymentScheduleService.addRepaymentScheduleBatch(list);
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public void doAutomaticDeductions(List<RepaymentSchedule> list) {
			
		for (RepaymentSchedule repaymentSchedule : list) {
			String transactionSN = UUIDUtils.genDateUUID("");

			TransactionRecord transactionRecord = EntityUtils.createTransactionRecord(repaymentSchedule,
					transactionSN, TransactionTypeEnum.INSTALLMENT.getCode(), TransactionWayEnum.ALIPAY.getCode(),
					TransactionSourceEnum.BACK_END.getCode(), SourceTypeEnum.SALES_ORDER.getCode(),
					LiquidationConstant.REMARK_INSTALLMENT, LiquidationConstant.STATE_TRADING);
			transactionRecordService.addTransactionRecord(transactionRecord);

			ResponseResult<ZhimaMerchantOrderCreditPayResponse> rr = (ResponseResult<ZhimaMerchantOrderCreditPayResponse>) executeDeductions(repaymentSchedule, transactionSN);
			if(rr.isSuccess()){
				ZhimaMerchantOrderCreditPayResponse response = rr.getData();
				log.info(LiquidationConstant.LOG_PREFIX + "doAutomaticDeductions response:{}",
						JsonUtils.toJsonString(response));
				if (response.isSuccess()) {

					if (response.getCode().equals(LiquidationConstant.ALIPAY_GATEWAY_CODE_10000)) {

						AlipayStatusEnum alipayTradeEnum = AlipayStatusEnum.getEnum(response.getPayStatus());

						switch (alipayTradeEnum) {
						case PAY_FAILED:

							transactionRecord.setUpdateOn(new Date());
							transactionRecord.setState(LiquidationConstant.STATE_FAILURE);
							transactionRecordService.updateState(transactionRecord);
							break;
						case PAY_SUCCESS:

							repaymentScheduleService.deductSuccessfully(transactionSN, repaymentSchedule);
							break;

						default:
							break;
						}
						
					}
					
				}

			}
		}

	}

	/**
	 * 
	 * @Description: 调用支付宝芝麻信用支付执行扣款
	 * @param rs
	 * @param transactionSN
	 * @return
	 * @throws AlipayApiException
	 * @throws createBy:liaoqingji
	 *             createDate:2018年3月31日
	 */
	private ResponseResult<?> executeDeductions(RepaymentSchedule rs, String transactionSN){
		ZhimaOrderCreditPayReq zhimaOrderCreditPayReq = new ZhimaOrderCreditPayReq();
		zhimaOrderCreditPayReq.setOrderOperateType(AlipayOrderOperateType.INSTALLMENT.getCode());
		zhimaOrderCreditPayReq.setOrderSN(rs.getOrderSN());
		zhimaOrderCreditPayReq.setOutTransNo(transactionSN);
		zhimaOrderCreditPayReq.setZmOrderNo(rs.getZmOrderNo());
		zhimaOrderCreditPayReq.setPayAmount(rs.getCurrentBalance());
		zhimaOrderCreditPayReq.setRemark(LiquidationConstant.REMARK_INSTALLMENT);
		
		return iAlipayService.zhimaOrderCreditPay(zhimaOrderCreditPayReq);
	}

	@Override
	public List<RepaymentSchedule> selectOverdueList(String[] array, int minDay, int maxDay) {
		return repaymentScheduleMapper.queryOverdueList(array, minDay, maxDay);
	}

	@Override
	public void sendOverdueSms(String[] array, Integer minDay, Integer maxDay) {
		int min = 1;
		int max = 4;
		if (minDay != null) {
			min = minDay;
		}
		if (maxDay != null) {
			max = maxDay;
		}

		String[] arrays = null;
		if (array != null && array.length > 0) {
			arrays = array;
		}

		List<RepaymentSchedule> resultList = selectOverdueList(arrays, min, max);
		SmsType smsType = SmsType.OVERDUE_NOTICE;

		if (!resultList.isEmpty()) {

			XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "sendOverdueSms 逾期天数1-4天的数据共{0}条!",
					resultList.size() + "");

			for (RepaymentSchedule repaymentSchedule : resultList) {
				if (StringUtils.isNotBlank(repaymentSchedule.getPhone())) {
					SmsDto smsDto = EntityUtils.createSmsDto(smsType, repaymentSchedule.getPhone(),
							String.valueOf(repaymentSchedule.getCurrentBalance()));

					ResponseResult<String> result = smsSendService.sendSmsStockSignInform(smsDto);
					XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "发送短信result" + JsonUtils.toJsonString(result));
					if (!result.isSuccess()) {
						log.error(
								LiquidationConstant.LOG_PREFIX
										+ "提交发送逾期提醒短信失败 orderSN={},templateId={},ErrMsg={},ErrCode={}",
								repaymentSchedule.getOrderSN(), smsType.getType(), result.getErrMsg(),
								result.getErrCode());
					}
				}

			}
		} else {
			XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "sendOverdueSms 无逾期天数1-4天的数据!");
		}

	}

	@Override
	public void sendRepayRentSms() {
		Date date = DateUtils.addDate(new Date(), 3);
		List<RepaymentSchedule> resultList = repaymentScheduleMapper.queryRepaymentRentList(DateUtils.getDayStart(date));
		SmsType smsType = SmsType.RENTPAY_NOTICE;

		if (!resultList.isEmpty()) {

			for (RepaymentSchedule repaymentSchedule : resultList) {
				if (StringUtils.isNotBlank(repaymentSchedule.getPhone())) {
					SmsDto smsDto = EntityUtils.createSmsDto(smsType, repaymentSchedule.getPhone(),
							String.valueOf(repaymentSchedule.getCurrentBalance()));

					ResponseResult<String> result = smsSendService.sendSmsStockSignInform(smsDto);
					XxlJobLogger.log(LiquidationConstant.LOG_PREFIX + "发送短信result:" + JsonUtils.toJsonString(result));
					if (!result.isSuccess()) {
						log.error(
								LiquidationConstant.LOG_PREFIX
										+ "提交发送交租提醒短信失败 orderSN={},templateId={},ErrMsg={},ErrCode={}",
								repaymentSchedule.getOrderSN(), smsType.getType(), result.getErrMsg(),
								result.getErrCode());
					}
				}
			}
		}
	}

	@Transactional
	@Override
	public ResponseResult<?> zhimaOrderCreditPayCallback(ZhimaOrderCreditPayResponse zhimaOrderCreditPayResponse) {
		String transactionSN = zhimaOrderCreditPayResponse.getOutTransNo();
		String orderSN = zhimaOrderCreditPayResponse.getOutOrderNo();
		Date date = new Date();
		
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionSN(transactionSN);
		List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(transactionRecord);

		if (list.isEmpty()) {
			return ResponseResult.build(LiquidationErrorCode.CREDIT_PAY_TRANSACTION_ERROR.getCode(),
					LiquidationErrorCode.CREDIT_PAY_TRANSACTION_ERROR.getMessage(), null);
		}
		
		TransactionRecord transaction = list.get(0);
		// 如果交易记录的交易状态已经是成功或者失败，不进行任何处理
		if(transaction.getState() == LiquidationConstant.STATE_SUCCESS || transaction.getState() == LiquidationConstant.STATE_FAILURE){
			return ResponseResult.buildSuccessResponse();
		}
		
		AlipayStatusEnum alipayStatusEnum = AlipayStatusEnum.getEnum(zhimaOrderCreditPayResponse.getPayStatus());
		
		switch (alipayStatusEnum) {
		case PAY_SUCCESS:
			
			// 查询芝麻订单信息
			ResponseResult<org.gz.aliOrder.dto.OrderDetailResp> rr = iAliOrderService.queryBackOrderDetail(orderSN);
			if(!rr.isSuccess()){
				return ResponseResult.build(LiquidationErrorCode.CREDIT_PAY_ORDER_ERROR.getCode(),
						LiquidationErrorCode.CREDIT_PAY_ORDER_ERROR.getMessage(), null);
			}
			
			org.gz.aliOrder.dto.OrderDetailResp orderDetailResp = rr.getData();
			ZmRepaymentScheduleReq zmRepaymentScheduleReq = new ZmRepaymentScheduleReq();
			zmRepaymentScheduleReq.setOrderSN(orderSN);
			zmRepaymentScheduleReq.setZmOrderNo(zhimaOrderCreditPayResponse.getZmOrderNo());
			zmRepaymentScheduleReq.setPeriods(orderDetailResp.getLeaseTerm());
			zmRepaymentScheduleReq.setPhone(orderDetailResp.getPhoneNum());
			zmRepaymentScheduleReq.setRealName(orderDetailResp.getRealName());
			zmRepaymentScheduleReq.setRent(orderDetailResp.getLeaseAmount());
			zmRepaymentScheduleReq.setUserId(orderDetailResp.getUserId());
			zmRepaymentScheduleReq.setGoodsValue(orderDetailResp.getShowAmount());
			zmRepaymentScheduleReq.setCreateDate(date);
			addZmRepaymentSchedule(zmRepaymentScheduleReq);
			
			// 更新交易流水状态
			transaction.setState(LiquidationConstant.STATE_SUCCESS);
			transaction.setUpdateOn(date);
			transaction.setResultCode(alipayStatusEnum.getCode());
			transactionRecordService.updateState(transaction);
			// 调用订单服务修改订单状态
			updateOrderPaySuccess(orderSN, transaction.getCreateBy(), transaction.getRealName());
			break;
		case PAY_FAILED:
			
			// 更新交易流水状态
			transaction.setState(LiquidationConstant.STATE_FAILURE);
			transaction.setUpdateOn(date);
			transaction.setResultCode(alipayStatusEnum.getCode());
			transactionRecordService.updateState(transaction);
			break;

		default:
			break;
		}
		
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<?> zhimaOrderCreditPay(ZhimaOrderCreditPayReq zhimaOrderCreditPayReq,String transactionSN) {
		ResponseResult<ZhimaMerchantOrderCreditPayResponse> rr = iAlipayService.zhimaOrderCreditPay(zhimaOrderCreditPayReq);
		log.info(LiquidationConstant.LOG_PREFIX+"zhimaOrderCreditPay response:{}",JsonUtils.toJsonString(rr));
		if(rr.isSuccess()){
			ZhimaMerchantOrderCreditPayResponse response = rr.getData();
			
			if (response.isSuccess()) {

				if (response.getCode().equals(LiquidationConstant.ALIPAY_GATEWAY_CODE_10000)) {

					AlipayStatusEnum alipayTradeEnum = AlipayStatusEnum.getEnum(response.getPayStatus());
					
					TransactionRecord transactionRecord = new TransactionRecord();
					transactionRecord.setTransactionSN(transactionSN);
					transactionRecord.setUpdateOn(new Date());
					
					switch (alipayTradeEnum) {
					case PAY_FAILED:
						transactionRecord.setState(LiquidationConstant.STATE_FAILURE);
						transactionRecordService.updateState(transactionRecord);
						break;
					case PAY_SUCCESS:
						transactionRecord.setState(LiquidationConstant.STATE_SUCCESS);
						transactionRecordService.updateState(transactionRecord);
						this.updateOrderPaySuccess(zhimaOrderCreditPayReq.getOrderSN(),Long.valueOf(zhimaOrderCreditPayReq.getUserId()), zhimaOrderCreditPayReq.getRealName());
						break;

					default:
						break;
					}
				}else if(response.getCode().equals(LiquidationConstant.ALIPAY_GATEWAY_CODE_40004)){
					log.error(LiquidationConstant.LOG_PREFIX+"zhimaOrderCreditPay msg:{} subCode：{} subMsg:{}",response.getMsg(),response.getSubCode(),response.getSubMsg());
				}
			}
		}
		
		return ResponseResult.buildSuccessResponse();
	}
	
	private void updateOrderPaySuccess(String orderSN,Long userId,String realName){
		org.gz.aliOrder.dto.UpdateOrderStateReq updateOrderStateReq = new org.gz.aliOrder.dto.UpdateOrderStateReq();
		updateOrderStateReq.setRentRecordNo(orderSN);
		updateOrderStateReq.setCreateBy(userId);
		updateOrderStateReq.setCreateMan(realName);
		updateOrderStateReq.setCallType(1);
		iAliOrderService.paySuccess(updateOrderStateReq);
	}

	@Override
	public ResponseResult<ZmStatementDetailResp> queryZmStatementDetail(String orderSN) {
		// 总租金
		BigDecimal totalRent = this.sumRent(orderSN);
		// 已偿还租金
		BigDecimal totalRepayRent = this.selectSumActualRepayment(orderSN);
		ZmStatementDetailResp zmStatementDetailResp = new ZmStatementDetailResp();
		zmStatementDetailResp.setTotalRent(totalRent);
		zmStatementDetailResp.setTotalRepayRent(totalRepayRent);
		zmStatementDetailResp.setTotalRentPayable(totalRent.subtract(totalRepayRent));
		return ResponseResult.buildSuccessResponse(zmStatementDetailResp);
	}

}
