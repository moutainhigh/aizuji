package org.gz.liquidation.service.repayment.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.Enum.SourceTypeEnum;
import org.gz.liquidation.common.Enum.TransactionSourceEnum;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.dto.BuyoutResp;
import org.gz.liquidation.common.dto.DuesDetailResp;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.PaymentReq;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.liquidation.common.entity.BuyoutPayReq;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.LateFeeEntity;
import org.gz.liquidation.entity.RepaymentDetailEntity;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.RepaymentStatistics;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.mapper.RepaymentScheduleMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.ReceivablesService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.repayment.RepaymentStatisticsService;
import org.gz.liquidation.service.repayment.detail.RepaymentDetailService;
import org.gz.liquidation.service.revenue.RentCollectionService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.liquidation.utils.EntityUtils;
import org.gz.liquidation.utils.ManualSettleValidator;
import org.gz.liquidation.utils.PeriodComparator;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RepaymentScheduleServiceImpl implements RepaymentScheduleService {
	@Resource
	private RepaymentScheduleMapper repaymentScheduleMapper;

	@Autowired
	private AccountRecordService accountRecordService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private TransactionRecordService transactionRecordService;

	@Autowired
	private RepaymentStatisticsService repaymentStatisticsService;

	@Resource
	private RentCollectionService rentCollectionService;

	@Resource
	private RepaymentService repaymentService;

	@Resource
	private LateFeeService lateFeeService;
	
	@Resource
	private RepaymentDetailService repaymentDetailService;
	
	@Resource
	private ReceivablesService receivablesService;
	
	@Resource
	private ManualSettleValidator manualSettleValidator;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseResult<RepaymentScheduleReq> addRepaymentSchedule(RepaymentScheduleReq repaymentScheduleReq) {
		Long userId = repaymentScheduleReq.getUserId();
		String orderSN = repaymentScheduleReq.getOrderSN();

		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setOrderSN(orderSN);
		accountRecord.setAccountCode(AccountEnum.SQK.getAccountCode());
		// 查询首期款流水号
		List<AccountRecord> accountRecordlist = accountRecordService.selectList(accountRecord);
		if (CollectionUtils.isEmpty(accountRecordlist)) {
			return ResponseResult.build(LiquidationErrorCode.SIGN_DOSIGN_DOWNPAYMENT_ERROR.getCode(),
					LiquidationErrorCode.SIGN_DOSIGN_DOWNPAYMENT_ERROR.getMessage(), repaymentScheduleReq);
		}

		Date date = new Date();
		AccountRecord a = accountRecordlist.get(0);
		// 交易流水号
		String transactionSN = a.getTransactionSN();
		// 首期款科目出账
		a.setFlowType(LiquidationConstant.OUT);
		a.setCreateBy(repaymentScheduleReq.getUserId());
		a.setCreateOn(date);
		accountRecordService.addAccountRecord(a);

		// 首期支付租金
		BigDecimal rent = a.getAmount();

		// 科目入账集合
		List<AccountRecord> addAccountRecordList = new ArrayList<>();
		// 溢价金入账 如果有
		if (repaymentScheduleReq.getPremium() != null
				&& repaymentScheduleReq.getPremium().compareTo(new BigDecimal(0)) == 1) {
			AccountRecord accountRecord2 = EntityUtils.createAccountRecord(userId, orderSN,
					AccountEnum.YJJ.getAccountCode(), transactionSN, LiquidationConstant.IN,
					repaymentScheduleReq.getPremium(), null);
			addAccountRecordList.add(accountRecord2);

			rent = rent.subtract(repaymentScheduleReq.getPremium());
		}
		// 保障金入账 如果有
		if (repaymentScheduleReq.getInsurance() != null
				&& repaymentScheduleReq.getInsurance().compareTo(new BigDecimal(0)) == 1) {
			AccountRecord accountRecord2 = EntityUtils.createAccountRecord(userId, orderSN,
					AccountEnum.BZJ.getAccountCode(), transactionSN, LiquidationConstant.IN,
					repaymentScheduleReq.getInsurance(), null);
			addAccountRecordList.add(accountRecord2);

			rent = rent.subtract(repaymentScheduleReq.getInsurance());
		}

		Integer periods = repaymentScheduleReq.getPeriods();

		List<RepaymentSchedule> list = new ArrayList<>(periods);
		// 计算还款日期
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		// 首期租金入账
		AccountRecord ar = new AccountRecord();
		ar.setAccountCode(AccountEnum.ZJ.getAccountCode());
		ar.setAmount(rent);
		ar.setOrderSN(orderSN);
		ar.setFlowType(LiquidationConstant.IN);
		ar.setCreateOn(new Date());
		ar.setTransactionSN(transactionSN);
		ar.setFundsSN(UUIDUtils.genDateUUID(AccountEnum.ZJ.getAccountCode()));
		ar.setCreateBy(repaymentScheduleReq.getUserId());
		ar.setRemark(LiquidationConstant.FIRST_RENT_REMARK);
		addAccountRecordList.add(ar);

		// 生成还款计划数据
		for (int i = 0; i < periods; i++) {
			c.add(Calendar.MONTH, i);
			Date paymentDueDate = c.getTime();
			RepaymentSchedule repaymentSchedule = EntityUtils.createRepaymentSchedule(repaymentScheduleReq, i + 1,
					paymentDueDate);
			list.add(repaymentSchedule);

			c.setTime(new Date());
		}

		RepaymentReq repaymentReq = new RepaymentReq();
		repaymentReq.setAmount(rent);
		// 核销还款计划
		List<RepaymentSchedule> resultList = this.chargeOff(repaymentReq, list, LiquidationConstant.SETTLEWAY_RENT);
		// 未进行核销的数据集合
		List<RepaymentSchedule> sourceList = (List<RepaymentSchedule>) CollectionUtils.subtract(list, resultList);
		List<RepaymentSchedule> addList = (List<RepaymentSchedule>) CollectionUtils.union(sourceList, resultList);
		addList.sort(new PeriodComparator());

		this.addRepaymentScheduleBatch(addList);
		accountRecordService.insertBatch(addAccountRecordList);

		boolean flag = true;
		// 判断是否结清还款计划
		for (RepaymentSchedule repaymentSchedule : addList) {
			if (repaymentSchedule.getSettleFlag() == LiquidationConstant.SETTLE_FLAG_NO) {
				flag = false;
			}
		}

		if (flag) {// 首期支付交完所有费用则调用订单服务修改订单状态为履约完成
			try {
				this.callOrderServiceUpdateState(orderSN, BackRentState.EndPerformance.getCode(),
						repaymentScheduleReq.getUserId(), repaymentScheduleReq.getRealName());
			} catch (Exception e) {
				log.error(LiquidationConstant.LOG_PREFIX + "确认签约修改订单状态异常:{}", e.getMessage());
			}
		}

		// 更新履约次数
		RepaymentStatistics repaymentStatistics = new RepaymentStatistics();
		repaymentStatistics.setUserId(repaymentScheduleReq.getUserId());
		repaymentStatistics.setPerformanceCount(1);
		repaymentStatistics.setOrderSN(orderSN);
		repaymentStatisticsService.add(repaymentStatistics);

		return ResponseResult.buildSuccessResponse(repaymentScheduleReq);
	}

	@Override
	public List<RepaymentSchedule> selectList(RepaymentSchedule repaymentSchedule) {
		return repaymentScheduleMapper.selectList(repaymentSchedule);
	}

	@Override
	public ResponseResult<BuyoutResp> readyBuyout(ReadyBuyoutReq readyBuyoutReq) {
		BuyoutResp buyoutResp = new BuyoutResp();
		String orderSN = readyBuyoutReq.getOrderSN();

		// 计算滞纳金 默认为0
		BigDecimal lateFees = new BigDecimal(0);
		lateFees = lateFeeService.lateFeePayable(orderSN);

		// 查询还款计划
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(readyBuyoutReq.getOrderSN());
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		List<RepaymentSchedule> repaymentList = selectList(repaymentSchedule);

		if (CollectionUtils.isNotEmpty(repaymentList)) {

			ResponseResult<OrderDetailResp> responseResult = orderService.queryBackOrderDetail(orderSN);

			if (!responseResult.isSuccess()) {
				return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_ORDER_ERROR.getCode(),
						LiquidationErrorCode.REPAYMENT_BUYOUT_ORDER_ERROR.getMessage(), null);
			}
			OrderDetailResp orderDetailResp = responseResult.getData();

			BigDecimal signContractAmount = orderDetailResp.getSignContractAmount();

			// 计算已结清的租金
			BigDecimal settledRent = repaymentService.selectSumActualRepayment(orderSN);

			BigDecimal buyoutAmount = signContractAmount.add(lateFees).subtract(settledRent);
			buyoutResp.setBuyoutAmount(buyoutAmount);
			return ResponseResult.buildSuccessResponse(buyoutResp);

		} else {
			return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_QUERY_ERROR.getCode(),
					LiquidationErrorCode.REPAYMENT_BUYOUT_QUERY_ERROR.getMessage(), null);
		}

	}

	/**
	 * 
	 * @Description: 比较两个日期相差多少个月
	 * @param start
	 * @param end
	 * @return
	 * @throws createBy:liaoqingji
	 *             createDate:2017年12月26日
	 */
	public int differMonth(Date start, Date end) {
		int startYear = DateUtils.getYear(start);
		int endYear = DateUtils.getYear(end);
		int startMonth = DateUtils.getMonth(start);
		int endMonth = DateUtils.getMonth(end);
		return (endYear - startYear) * 12 + (endMonth - startMonth);
	}

	@Override
	public ResponseResult<?> doBuyout(TransactionRecord transactionRecord) {
		String rentRecordNo = transactionRecord.getOrderSN();

		RepaymentScheduleReq repaymentScheduleReq = new RepaymentScheduleReq();
		repaymentScheduleReq.setAmount(transactionRecord.getTransactionAmount());
		repaymentScheduleReq.setOrderSN(rentRecordNo);
		repaymentScheduleReq.setTransactionSN(transactionRecord.getTransactionSN());
		repaymentScheduleReq.setUserId(transactionRecord.getCreateBy());

		Integer orderState = BackRentState.EarlyBuyout.getCode();
		int type = LiquidationConstant.BUYOUT_TYPE_NOMAL;
		if (transactionRecord.getTransactionType().equals(TransactionTypeEnum.OVERDUE_BUYOUT.getCode())) {
			type = LiquidationConstant.BUYOUT_TYPE_OVERDUE;
			orderState = BackRentState.ForceBuyout.getCode();
		}

		// 判断是否履约完成
		boolean flag = repaymentService.queryOrderIsSettled(rentRecordNo);
		if(flag){
			orderState = BackRentState.NormalBuyout.getCode();
		}
		
		// 买断核销
		ResponseResult<?> responseResult = this.buyoutWriteOff(repaymentScheduleReq, type);
		if (!responseResult.isSuccess()) {
			return responseResult;
		}
		
		try {
			UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
			updateOrderState.setRentRecordNo(rentRecordNo);
			updateOrderState.setState(orderState);
			updateOrderState.setCreateBy(transactionRecord.getCreateBy());
			updateOrderState.setCreateMan(transactionRecord.getRealName());
			// 更改订单状态
			ResponseResult<String> responseResult2 = orderService.buyOut(updateOrderState);
			log.info(LiquidationConstant.LOG_PREFIX+"doBuyout responseResult2：{}", responseResult2);
		} catch (Exception e) {
			log.error(LiquidationConstant.LOG_PREFIX+"doBuyout 调用订单服务修改订单状态为买断异常",e.getMessage());
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public ResponseResult<?> buyoutCallback(PaymentReq paymentReq) {
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionSN(paymentReq.getTransactionSN());

		List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(transactionRecord);
		if (CollectionUtils.isNotEmpty(list)) {

			TransactionRecord tr = list.get(0);
			transactionRecord.setState(LiquidationConstant.STATE_SUCCESS);
			return this.doBuyout(tr);

		}

		return ResponseResult.buildSuccessResponse();

	}

	@Override
	public ResponseResult<Date> queryRepaymentDate(String orderSN) {
		RepaymentSchedule repaymentSchedule = repaymentScheduleMapper.queryRepaymentDate(orderSN);
		if (null == repaymentSchedule) {// 未找到对应的还款计划
			return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_QUERY_ERROR.getCode(),
					LiquidationErrorCode.REPAYMENT_BUYOUT_QUERY_ERROR.getMessage(), null);
		}

		Date cureDate = new Date();
		Date firstRepaymentDate = repaymentSchedule.getPaymentDueDate();
		int num = differMonth(firstRepaymentDate, cureDate);
		// 本期还款日
		Date curRepaymentDate = DateUtils.getDateWithDifferMonth(firstRepaymentDate, num);

		return ResponseResult.buildSuccessResponse(curRepaymentDate);
	}

	@Override
	public ResponseResult<?> validBuyout(String orderSN, int type) {
		// 判断订单是否终结状态
		ResponseResult<OrderDetailResp> rr = orderService.queryBackOrderDetail(orderSN);
		if (!rr.isSuccess()) {
			log.warn("doBuyout 查询订单服务异常");
			return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_ORDER_ERROR.getCode(),
					LiquidationErrorCode.REPAYMENT_BUYOUT_ORDER_ERROR.getMessage(), null);
		}

		OrderDetailResp orderDetail = rr.getData();
		log.info(LiquidationConstant.LOG_PREFIX + " validBuyout orderDetail:{}", JsonUtils.toJsonString(orderDetail));
		Integer state = (Integer) orderDetail.getState();

		boolean returnFlag = false;
		if (LiquidationConstant.BUYOUT_TYPE_NOMAL == type) {// 正常买断
			// 判断当期还款是否结清
			ResponseResult<Date> resultResult = this.queryRepaymentDate(orderSN);
			Date curRepaymentDate = resultResult.getData();
			RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
			repaymentSchedule.setOrderSN(orderSN);
			repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
			List<RepaymentSchedule> list = this.selectList(repaymentSchedule);

			for (RepaymentSchedule rs : list) {
				if (rs.getPaymentDueDate().equals(curRepaymentDate)) {
					if (rs.getSettleFlag().intValue() == LiquidationConstant.SETTLE_FLAG_NO) {
						return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_UNSETTLED_ERROR.getCode(),
								LiquidationErrorCode.REPAYMENT_BUYOUT_UNSETTLED_ERROR.getMessage(), null);
					}
					break;
				}
			}

			if (state != BackRentState.NormalPerformance.getCode() && state != BackRentState.EndPerformance.getCode()) {
				returnFlag = true;
			}
		}

		if (LiquidationConstant.BUYOUT_TYPE_OVERDUE == type) {// 逾期买断
			if (state != BackRentState.Overdue.getCode() && state != BackRentState.ForceBuyIng.getCode()) {
				returnFlag = true;
			}
		}

		if (returnFlag) {
			// 订单状态不满足买断条件
			return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_STATE_ERROR.getCode(),
					LiquidationErrorCode.REPAYMENT_BUYOUT_STATE_ERROR.getMessage(), null);
		}

		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	@Override
	public ResponseResult<?> doBuyoutPay(BuyoutPayReq buyoutPayReq) {
		// TODO 调用支付发起支付申请

		// 更新交易流水状态为处理中
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionSN(buyoutPayReq.getTransactionSN());
		transactionRecord.setState(LiquidationConstant.STATE_TRADING);
		transactionRecordService.updateState(transactionRecord);
		return null;
	}

	@Override
	public void updateBatch(List<RepaymentSchedule> list) {
		repaymentScheduleMapper.updateBatch(list);
	}

	@Override
	public List<RepaymentSchedule> chargeOff(RepaymentReq repaymentReq, List<RepaymentSchedule> list,String settleWay) {
		BigDecimal amount = repaymentReq.getAmount();
		List<RepaymentSchedule> resultList = new ArrayList<>();
		if (CollectionUtils.isEmpty(list)) {
			return resultList;
		}

		Date date = new Date();
		for (RepaymentSchedule rs : list) {
			if (amount.compareTo(new BigDecimal(0)) == 0) {
				break;
			}
			// 未还金额
			BigDecimal currentBalance = rs.getCurrentBalance();
			int number = amount.compareTo(currentBalance);
			// 偿还金大于等于未偿还金额
			if (number >= 0) {
				amount = amount.subtract(currentBalance);
				rs.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
				rs.setSettleWay(settleWay);
				rs.setState(LiquidationConstant.BREACH_STATE_NO);
				rs.setRepaymentTime(date);
				rs.setActualRepayment(currentBalance);
				rs.setCurrentBalance(new BigDecimal(0));
				rs.setSettleDate(date);
			} else {// 偿还金额小于未偿还金额
				BigDecimal balance = currentBalance.subtract(amount);
				rs.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
				rs.setActualRepayment(amount);
				rs.setCurrentBalance(balance);
				// 剩余0
				amount = new BigDecimal(0);
			}

			rs.setUpdateOn(date);
			rs.setCreateOn(date);
			resultList.add(rs);
		}

		repaymentReq.setAmount(amount);
		return resultList;

	}

	@Override
	public void addRepaymentScheduleBatch(List<RepaymentSchedule> list) {

		for (RepaymentSchedule repaymentSchedule : list) {

			if (repaymentSchedule.getOverdueDay() == null) {
				// 设置逾期天数默认值
				repaymentSchedule.setOverdueDay(0);
			}

			if (repaymentSchedule.getState() == null) {
				repaymentSchedule.setState(LiquidationConstant.STATE_NORMAL);
			}

			if (repaymentSchedule.getRealName() == null) {
				repaymentSchedule.setRealName("");
			}

			if (repaymentSchedule.getPhone() == null) {
				repaymentSchedule.setPhone("");
			}
			
			if (repaymentSchedule.getOrderType() == null){
				// 默认APP订单
				repaymentSchedule.setOrderType(0);
			}
		}

		repaymentScheduleMapper.addRepaymentScheduleBatch(list);
	}

	@Override
	public ResponseResult<RepaymentDetailResp> queryRepaymentDetail(String orderSN) {
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setOrderSN(orderSN);
		List<RepaymentSchedule> list = repaymentScheduleMapper.selectList(repaymentSchedule);
		RepaymentDetailResp repaymentDetailResp = new RepaymentDetailResp();
		if (CollectionUtils.isNotEmpty(list)) {
			BigDecimal settledLateFees = new BigDecimal(0);
			BigDecimal settledRent = new BigDecimal(0);

			Integer periodOf = null;
			Integer periods = null;

			for (RepaymentSchedule rs : list) {
				int repaymentType = rs.getRepaymentType();
				if (repaymentType == LiquidationConstant.REPAYMENT_TYPE_RENT) {
					settledRent = settledRent.add(rs.getActualRepayment());
				} else if (repaymentType == LiquidationConstant.REPAYMENT_TYPE_LATE_FEES) {
					settledLateFees = settledLateFees.add(rs.getActualRepayment());
				}

			}

			repaymentDetailResp.setSettledLateFees(settledLateFees);
			repaymentDetailResp.setSettledRent(settledRent);

			RepaymentSchedule data = repaymentService.selectLatestSettledRent(orderSN);
			periodOf = data.getPeriodOf();
			periods = data.getPeriods();
			repaymentDetailResp.setState(data.getState());

			repaymentDetailResp.setPeroidOf(periodOf);
			repaymentDetailResp.setPeroids(periods);

			StringBuffer sb = new StringBuffer();
			sb.append(periodOf);
			sb.append("/");
			sb.append(periods);
			// 账期格式 3/12
			repaymentDetailResp.setBill(sb.toString());
		}

		// 设置默认买断金额0
		repaymentDetailResp.setBuyoutAmount(new BigDecimal(0));

		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setOrderSN(orderSN);
		transactionRecord.setTransactionType(TransactionTypeEnum.BUYOUT.getCode());
		// 查询是否进行买断记录
		List<TransactionRecord> resultList = transactionRecordService.selectByTransactionRecord(transactionRecord);
		if (CollectionUtils.isNotEmpty(resultList)) {
			repaymentDetailResp.setBuyoutAmount(resultList.get(0).getTransactionAmount());
		}
		return ResponseResult.buildSuccessResponse(repaymentDetailResp);
	}

	@Override
	public ResponseResult<DuesDetailResp> queryDuesDetail(String orderSN) {
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setOrderSN(orderSN);
		List<RepaymentSchedule> list = repaymentScheduleMapper.selectList(repaymentSchedule);

		DuesDetailResp duesDetailResp = null;
		if (CollectionUtils.isNotEmpty(list)) {

			duesDetailResp = new DuesDetailResp();
			// 应付租金 （截止到当前账期未偿还的租金总和）
			BigDecimal rentPayable = new BigDecimal(0);
			// 查询账期
			ResponseResult<Date> rr = this.queryRepaymentDate(orderSN);
			Date repaymentDate = rr.getData();

			Integer periodOf = null;
			Integer periods = null;
			// 获取最新结清的账期
			RepaymentSchedule data = repaymentService.selectLatestSettledRent(orderSN);
			if (data != null) {
				periodOf = data.getPeriodOf();
			}

			if (data.getPeriodOf() != data.getPeriods()) {// 履约未完成

				if (data.getPaymentDueDate().compareTo(repaymentDate) == 0) {// 本期账期已经结清
					repaymentDate = DateUtils.getDateWithDifferMonth(repaymentDate, 1);
				}

			} else {
				repaymentDate = data.getPaymentDueDate();
			}

			// 账单开始日期
			duesDetailResp.setBillStart(list.get(0).getPaymentDueDate());
			duesDetailResp.setBillEnd(list.get(list.size() - 1).getPaymentDueDate());

			for (RepaymentSchedule rs : list) {

				int repaymentType = rs.getRepaymentType();
				if (repaymentType == LiquidationConstant.REPAYMENT_TYPE_RENT
						&& rs.getSettleFlag() == LiquidationConstant.SETTLE_FLAG_NO) {
					// 未缴纳的租金
					rentPayable = rentPayable.add(rs.getCurrentBalance());
				}

				if (repaymentDate.equals(rs.getPaymentDueDate())
						&& repaymentType == LiquidationConstant.REPAYMENT_TYPE_RENT) {
					// 本月租金
					duesDetailResp.setRent(rs.getDue());
					duesDetailResp.setState(rs.getState());
					duesDetailResp.setPaymentDueDate(repaymentDate);
					periodOf = rs.getPeriodOf();
					periods = rs.getPeriods();

					break;
				}

			}

			BigDecimal lateFees = lateFeeService.lateFeePayable(orderSN);
			duesDetailResp.setLateFees(lateFees);
			duesDetailResp.setRentPayable(rentPayable);
			// 本期账单欠款
			duesDetailResp.setCurrentBalance(rentPayable.add(lateFees));

			// 截止到本期逾期天数
			repaymentSchedule.setPaymentDueDate(repaymentDate);
			int overdueDay = repaymentService.sumOverdueDay(repaymentSchedule);
			duesDetailResp.setOverdueDay(overdueDay);

			StringBuffer sb = new StringBuffer();
			sb.append(periodOf);
			sb.append("/");
			sb.append(periods);
			// 账期格式 3/12
			duesDetailResp.setBill(sb.toString());

			// 所有未结清的租金
			BigDecimal outstandingRent = this.selectSumCurrentBalance(orderSN, LiquidationConstant.REPAYMENT_TYPE_RENT);
			duesDetailResp.setOutstandingRent(outstandingRent);
			// 账单余额
			duesDetailResp.setStatementBalance(outstandingRent.add(lateFees));

		}

		return ResponseResult.buildSuccessResponse(duesDetailResp);
	}

	@Override
	public ResponseResult<?> buyoutWriteOff(RepaymentScheduleReq repaymentScheduleReq, int type) {
		// 买断核销
		
		String orderSN = repaymentScheduleReq.getOrderSN();
		Long userId = repaymentScheduleReq.getUserId();
		String transactionSN = repaymentScheduleReq.getTransactionSN();

		BigDecimal amount = repaymentScheduleReq.getAmount();
		if (amount == null || amount.compareTo(new BigDecimal(0)) == -1 || amount.compareTo(new BigDecimal(0)) == 0) {
			return ResponseResult.build(LiquidationErrorCode.REPAYMENT_SETTLE_AMOUT_ERROR.getCode(),
					LiquidationErrorCode.REPAYMENT_SETTLE_AMOUT_ERROR.getMessage(), repaymentScheduleReq);
		}

		BigDecimal zero = new BigDecimal(0);
		String accountCode = AccountEnum.MDJ.getAccountCode();
		String remark = LiquidationConstant.REMARK_NORMAL_BUYOUT_AMOUNT;
		if (LiquidationConstant.BUYOUT_TYPE_NOMAL == type) {// 正常买断,分两种情况,履约完成买断和未履约完成正常买断

			// 判断是否履约完成
			boolean flag = repaymentService.queryOrderIsSettled(orderSN);
			if (!flag) {
				// 从当期开始到最后一期 全部变成买断结清
				RepaymentSchedule param = new RepaymentSchedule();
				param.setOrderSN(repaymentScheduleReq.getOrderSN());
				param.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
				param.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
				param.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
				List<RepaymentSchedule> rentList = this.selectList(param);

				if (CollectionUtils.isEmpty(rentList)) {
					return ResponseResult.build(LiquidationErrorCode.REPAYMENT_BUYOUT_QUERY_ERROR.getCode(),
							LiquidationErrorCode.REPAYMENT_BUYOUT_QUERY_ERROR.getMessage(), null);
				}

				for (RepaymentSchedule repaymentSchedule : rentList) {
					repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
					repaymentSchedule.setSettleWay(TransactionTypeEnum.BUYOUT.getCode());
					repaymentSchedule.setSettleDate(new Date());
					repaymentSchedule.setActualRepayment(zero);
					repaymentSchedule.setCurrentBalance(zero);
					repaymentSchedule.setDue(zero);
				}

				// 更新数据
				this.updateBatch(rentList);
			}
		} else if (LiquidationConstant.BUYOUT_TYPE_OVERDUE == type) {// 逾期买断

			TransactionRecord transactionRecord = new TransactionRecord();
			transactionRecord.setOrderSN(orderSN);
			transactionRecord.setTransactionSN(transactionSN);
			transactionRecord.setCreateBy(userId);
			transactionRecord.setTransactionAmount(amount);
			repaymentService.repay(transactionRecord,LiquidationConstant.SETTLEWAY_BUYOUT);
			amount = transactionRecord.getTransactionAmount();
			
			RepaymentSchedule param = new RepaymentSchedule();
			param.setOrderSN(repaymentScheduleReq.getOrderSN());
			param.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
			param.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
			// 未结清的租金列表
			List<RepaymentSchedule> rentList = this.selectList(param);
				
			if(!rentList.isEmpty()){
				this.buyoutRepaymentScheduleUpdate(rentList);
				this.updateBatch(rentList);
			}
			
			remark = LiquidationConstant.REMARK_OVERDUE_BUYOUT_AMOUNT;
		}

		AccountRecord accountRecord = EntityUtils.createAccountRecord(userId, orderSN, accountCode, transactionSN,
				LiquidationConstant.IN, amount, remark);
		
		// 买断金科目入账
		accountRecordService.addAccountRecord(accountRecord);
		// 科目还款明细
		RepaymentDetailEntity repaymentDetailEntity = EntityUtils.createRepaymentDetailEntity(userId, orderSN,
				accountCode, transactionSN, null, amount,
				LiquidationConstant.REMARK_NORMAL_BUYOUT_AMOUNT);
		repaymentDetailService.insertSelective(repaymentDetailEntity);
		
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<RepaymentDetailResp> queryBuyoutOrderDetail(String orderSN) {
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		List<RepaymentSchedule> list = repaymentScheduleMapper.selectList(repaymentSchedule);

		RepaymentDetailResp repaymentDetailResp = new RepaymentDetailResp();
		if (CollectionUtils.isNotEmpty(list)) {
			Integer periodOf = null;
			Integer periods = null;
			Integer overdueDay = 0;

			for (int i = 0; i < list.size(); i++) {
				RepaymentSchedule rs = list.get(i);

				// 取租赁计划开始日期和结束日期
				if (i == 0) {
					repaymentDetailResp.setBillStart(rs.getPaymentDueDate());
				} else if (i == list.size() - 1) {
					repaymentDetailResp.setBillEnd(rs.getPaymentDueDate());
				}

				if(rs.getSettleFlag()== LiquidationConstant.SETTLE_FLAG_NO){// 只统计为结清账单的逾期天数
					overdueDay += rs.getOverdueDay();
				}
				
			}

			RepaymentSchedule data = repaymentService.selectLatestSettledRent(orderSN);
			periodOf = data.getPeriodOf();
			periods = data.getPeriods();
			repaymentDetailResp.setState(data.getState());

			repaymentDetailResp.setOverdueDay(overdueDay);
			repaymentDetailResp.setPeroidOf(periodOf);
			repaymentDetailResp.setPeroids(periods);

			StringBuffer sb = new StringBuffer();
			sb.append(periodOf);
			sb.append("/");
			sb.append(periods);
			// 账期格式 3/12
			repaymentDetailResp.setBill(sb.toString());
		}

		// 设置默认买断金额0
		repaymentDetailResp.setBuyoutAmount(new BigDecimal(0));

		// 查询滞纳金
		BigDecimal lateFees = lateFeeService.lateFeePayable(orderSN);
		repaymentDetailResp.setLateFees(lateFees);

		// 已偿还租金
		BigDecimal settledRent = repaymentService.selectSumActualRepayment(orderSN);
		repaymentDetailResp.setSettledRent(settledRent);

		// 查询减免金额
		BigDecimal remissionLateFees = accountRecordService.queryRemissionFee(orderSN);
		BigDecimal remissionAmount = new BigDecimal(0);
		remissionAmount = remissionAmount.add(remissionLateFees);
		repaymentDetailResp.setRemissionAmount(remissionAmount);

		ReadyBuyoutReq readyBuyoutReq = new ReadyBuyoutReq();
		readyBuyoutReq.setOrderSN(orderSN);

		// 查询买断金
		ResponseResult<BuyoutResp> responseResult = this.readyBuyout(readyBuyoutReq);

		if (responseResult.isSuccess()) {
			BuyoutResp buyoutResp = responseResult.getData();
			repaymentDetailResp.setBuyoutAmount(buyoutResp.getBuyoutAmount());
		} else {
			ResponseResult.build(responseResult.getErrCode(), responseResult.getErrMsg(), repaymentDetailResp);
		}

		return ResponseResult.buildSuccessResponse(repaymentDetailResp);
	}

	@Override
	public ResultPager<RepaymentScheduleResp> queryPage(QueryDto queryDto) {
		List<RepaymentSchedule> sourceList = repaymentScheduleMapper.queryPage(queryDto);
		List<RepaymentScheduleResp> list = BeanConvertUtil.convertBeanList(sourceList, RepaymentScheduleResp.class);
		Page page = queryDto.getPage();
		return new ResultPager<RepaymentScheduleResp>(page.getTotalNum(), page.getStart(), page.getPageSize(), list);
	}

	@Override
	public BigDecimal selectSumCurrentBalance(String orderSN, Integer repaymentType) {
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(repaymentType);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		BigDecimal currentLateFees = repaymentScheduleMapper.selectSumCurrentBalance(repaymentSchedule);

		if (currentLateFees == null) {
			currentLateFees = new BigDecimal(0);
		}
		return currentLateFees;
	}

	@Override
	public Map<String, BigDecimal> selectLateFees(String orderSN) {
		Map<String, BigDecimal> map = new HashMap<>();
		LateFeeEntity lateFeeEntity = new LateFeeEntity();
		lateFeeEntity.setOrderSN(orderSN);
		Optional<LateFeeEntity> optional = lateFeeService.selectLateFee(lateFeeEntity);
		// 应付滞纳金
		BigDecimal unclearedLateFees = new BigDecimal(0);

		// 已偿还滞纳金
		BigDecimal clearedLateFees = new BigDecimal(0);

		if (optional.isPresent()) {
			unclearedLateFees = optional.get().getCurrentBalance();
			clearedLateFees = optional.get().getRepayAmount();
		}

		map.put("unclearedLateFees", unclearedLateFees);
		map.put("clearedLateFees", clearedLateFees);
		return map;
	}

	@Override
	public List<RepaymentSchedule> selectRepaymentList(RepaymentSchedule repaymentSchedule) {
		return repaymentScheduleMapper.selectRepaymentList(repaymentSchedule);
	}

	@Override
	public void batchUpdateEnableFlag(List<RepaymentSchedule> list) {
		repaymentScheduleMapper.batchUpdateEnableFlag(list);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseResult<?> manualSettle(ManualSettleReq manualSettleReq) {
		
		TransactionRecord param = new TransactionRecord();
		param.setTransactionSN(manualSettleReq.getTransactionSN());
		// 交易交易流水号是否存在
		List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(param);
		if(CollectionUtils.isNotEmpty(list)){
			return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_TRADE_SN_ERROR.getCode(),
					LiquidationErrorCode.MANUAL_SETTLE_TRADE_SN_ERROR.getMessage(), null);
		}
		
		// 手动结算
		TransactionTypeEnum typeEnum = TransactionTypeEnum.getEnum(manualSettleReq.getTransactionType());

		switch (typeEnum) {

		case BUYOUT:// 买断
			
			return manualSettleBuyout(manualSettleReq);
		case OVERDUE_BUYOUT:// 逾期买断
			
			return manualSettleBuyout(manualSettleReq);
		case RENT:// 租金

			return this.manualSettleRent(manualSettleReq);

		case RETURN:// 归还

			return rentCollectionService.returnManualSettle(manualSettleReq);
		case FIRST_RENT:// 首期款支付
			
			return manualSettleDownPayment(manualSettleReq);

		default:
			log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 对应的业务交易类型! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");

		}

		return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_TRADE_TYPE_ERROR.getCode(),
				LiquidationErrorCode.MANUAL_SETTLE_TRADE_TYPE_ERROR.getMessage(), null);
	}

	@Override
	public ResponseResult<?> manualSettleRent(ManualSettleReq manualSettleReq) {
		try {

			String orderSN = manualSettleReq.getOrderSN();

			ResponseResult<?> rr = manualSettleValidator.validateRepayRent(orderSN,manualSettleReq.getAmount());
			if(!rr.isSuccess()){
				return rr; 
			}
			
			RepaymentSchedule param = new RepaymentSchedule();
			param.setOrderSN(orderSN);
			param.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
			List<RepaymentSchedule> rentList = this.selectList(param);
			// 记录交易流水
			RepaymentSchedule repaymentSchedule = rentList.get(0);

			TransactionRecord transactionRecord = EntityUtils.createTransactionRecord(manualSettleReq,
					SourceTypeEnum.SALES_ORDER.getCode(), TransactionSourceEnum.BACK_END.getCode(),
					LiquidationConstant.REMARK_MANUAL_SETTLE_RENT, LiquidationConstant.STATE_SUCCESS,
					repaymentSchedule.getRealName(), repaymentSchedule.getPhone());
			repaymentService.repayRent(transactionRecord);

			transactionRecordService.addTransactionRecord(transactionRecord);

		} catch (Exception e) {

			log.error("手动结算租金异常:{}", e.getLocalizedMessage());
			throw new ServiceException(ResponseStatus.DATABASE_ERROR.getCode(),
					ResponseStatus.DATABASE_ERROR.getMessage());
		}

		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<?> manualSettleBuyout(ManualSettleReq manualSettleReq) {
		
		String orderSN = manualSettleReq.getOrderSN();
		
		ResponseResult<?> rr = manualSettleValidator.validateBuyout(manualSettleReq);
		if(!rr.isSuccess()){
			return rr; 
		}
		
		// 查询还款计划获取用户手机号
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		List<RepaymentSchedule> rentList = this.selectRepaymentList(repaymentSchedule);
		String phone = "";
		String realName = "";
		if (CollectionUtils.isNotEmpty(rentList)) {
			RepaymentSchedule rs =  rentList.get(0);
			realName = rs.getRealName();
			phone = rs.getPhone();
		}
		
		TransactionRecord transactionRecord = EntityUtils.createTransactionRecord(manualSettleReq,
				SourceTypeEnum.SALES_ORDER.getCode(), TransactionSourceEnum.BACK_END.getCode(),
				LiquidationConstant.BUYOUT_OVERDUE_REMARK, LiquidationConstant.STATE_SUCCESS, realName, phone);

		// 执行买断
		ResponseResult<?> responseResult = this.doBuyout(transactionRecord);
		log.info(LiquidationConstant.LOG_PREFIX + " manualSettleBuyout doBuyout responseResult:{}", responseResult);

		transactionRecordService.addTransactionRecord(transactionRecord);
		return responseResult;
	}

	@Transactional
	@Override
	public void settleDataHandle(List<RepaymentSchedule> updateList, List<RepaymentSchedule> addList,
			String settleWay) {
		Date date = new Date();
		// 失效旧数据，新增新数据
		for (RepaymentSchedule rs : updateList) {
			rs.setEnableFlag(LiquidationConstant.ENABLE_FLAG_NO);
			rs.setUpdateOn(date);
		}

		this.batchUpdateEnableFlag(updateList);

		for (RepaymentSchedule rs2 : addList) {

			rs2.setSettleDate(null);
			if (rs2.getSettleFlag() == LiquidationConstant.SETTLE_FLAG_YES) {
				rs2.setSettleWay(settleWay);
				rs2.setState(LiquidationConstant.STATE_NORMAL);
				rs2.setSettleDate(date);
			}

			// 实际还款时间
			rs2.setRepaymentTime(date);
		}
		this.addRepaymentScheduleBatch(addList);
	}

	/**
	 * 
	 * @Description: 调用订单服务修改订单状态
	 * @param orderSN
	 *            订单号
	 * @param state
	 *            订单状态
	 * @param createBy
	 *            修改人id
	 * @param realName
	 *            修改人姓名
	 * @throws createBy:liaoqingji
	 *             createDate:2018年3月2日
	 */
	private void callOrderServiceUpdateState(String orderSN, Integer state, Long createBy, String realName) {
		UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
		updateOrderState.setRentRecordNo(orderSN);
		updateOrderState.setState(state);
		updateOrderState.setCreateBy(createBy);
		updateOrderState.setCreateMan(realName);
		ResponseResult<String> responseResult2 = orderService.updateOrderState(updateOrderState);
		log.info(LiquidationConstant.LOG_PREFIX + "repayRent 调用订单服务更新订单状态返回结果:{}",
				JsonUtils.toJsonString(responseResult2));
	}
	/**
	 * 
	 * @Description: 买断还款计划数据更新
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月15日
	 */
	private void buyoutRepaymentScheduleUpdate(List<RepaymentSchedule> list){
		Date date = new Date();
		BigDecimal zero = new BigDecimal(0); 
		for (RepaymentSchedule rs : list) {
			rs.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
			// 结清方式-买断
			rs.setSettleWay(TransactionTypeEnum.OVERDUE_BUYOUT.getCode());
			rs.setSettleDate(date);
			rs.setUpdateOn(date);
			rs.setActualRepayment(zero);
			rs.setCurrentBalance(zero);
			rs.setDue(zero);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(RepaymentSchedule repaymentSchedule) {
		repaymentScheduleMapper.updateByPrimaryKeySelective(repaymentSchedule);
	}

	@Override
	public ResponseResult<?> manualSettleDownPayment(ManualSettleReq manualSettleReq) {
		
		TransactionRecord transactionRecord = EntityUtils.createTransactionRecord(manualSettleReq,
				SourceTypeEnum.SALES_ORDER.getCode(), TransactionSourceEnum.BACK_END.getCode(),
				LiquidationConstant.REMARK_MANUAL_SETTLE_RENT, LiquidationConstant.STATE_SUCCESS, manualSettleReq.getRealName(), manualSettleReq.getPhone());
		
		receivablesService.payDownpayment(transactionRecord);
		transactionRecordService.addTransactionRecord(transactionRecord);
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void deductSuccessfully(String transactionSN, RepaymentSchedule repaymentSchedule) {
		Date date = new Date();
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setState(LiquidationConstant.STATE_SUCCESS);
		transactionRecord.setUpdateOn(date);
		transactionRecordService.updateState(transactionRecord);
		
		repaymentSchedule.setState(LiquidationConstant.STATE_NORMAL);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_YES);
		repaymentSchedule.setActualRepayment(repaymentSchedule.getCurrentBalance());
		repaymentSchedule.setUpdateOn(date);
		repaymentSchedule.setSettleDate(date);
		this.updateByPrimaryKeySelective(repaymentSchedule);
		
		AccountRecord accountRecord = EntityUtils.createAccountRecord(repaymentSchedule.getCreateBy(),
				repaymentSchedule.getOrderSN(), AccountEnum.ZJ.getAccountCode(), transactionSN, LiquidationConstant.IN,
				repaymentSchedule.getCurrentBalance(), LiquidationConstant.REMARK_INSTALLMENT);
		accountRecordService.addAccountRecord(accountRecord);
		
		// 记录还款明细
		RepaymentDetailEntity repaymentDetailEntity = EntityUtils.createRepaymentDetailEntity(
				repaymentSchedule.getCreateBy(), repaymentSchedule.getOrderSN(), AccountEnum.ZJ.getAccountCode(),
				transactionSN, repaymentSchedule.getPaymentDueDate(), repaymentSchedule.getCurrentBalance(),
				LiquidationConstant.REMARK_INSTALLMENT);
		repaymentDetailService.insertSelective(repaymentDetailEntity);
		
	}
	
}
