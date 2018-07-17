package org.gz.liquidation.service.latefee.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.LateFeeEntity;
import org.gz.liquidation.entity.LateFeeTaskLogEntity;
import org.gz.liquidation.entity.RepaymentDetailEntity;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.RepaymentStatistics;
import org.gz.liquidation.entity.SystemCommonConfigEntity;
import org.gz.liquidation.mapper.LateFeeMapper;
import org.gz.liquidation.mapper.RepaymentScheduleMapper;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.config.SystemCommonConfigService;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.latefee.tasklog.ILateFeeTaskLogService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.repayment.RepaymentStatisticsService;
import org.gz.liquidation.service.repayment.detail.RepaymentDetailService;
import org.gz.liquidation.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxl.job.core.log.XxlJobLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LateFeeServiceImpl implements org.gz.liquidation.service.latefee.LateFeeService {

	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	
	@Resource
	private RepaymentScheduleMapper repaymentScheduleMapper;
	
	@Resource
	private SystemCommonConfigService systemCommonConfigService;
	
	@Autowired
	private OrderService orderService;
	
	@Resource
	private RepaymentService repaymentService;
	
	@Resource
	private AccountRecordService accountRecordService;
	
	@Resource
	private RepaymentStatisticsService repaymentStatisticsService;
	
	@Resource
	private LateFeeMapper lateFeeMapper;
	
	@Resource
	private LateFeeService lateFeeService;
	@Resource
	private ILateFeeTaskLogService lateFeeTaskLogService;
	@Resource
	private RepaymentDetailService repaymentDetailService;
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void calculateLateFee() {
		
		// 计算逾期订单产生的滞纳金
		long start = System.currentTimeMillis();
		log.info(LiquidationConstant.LOG_PREFIX+"calculateLateFee 开始执行滞纳金计算定时任务！");
		
		// 查还款日是昨天，未还金额大于0的还款数据
		Date yestoday = DateUtils.getDateWithDifferDay(-1);
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setPaymentDueDate(yestoday);
		repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		
		List<RepaymentSchedule> list = repaymentService.selectOverdueList(yestoday);
		log.info(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日应还租金记录共{}条！",list.size());
		
		if(CollectionUtils.isNotEmpty(list)){
			
			// 逾期数据集合列表
			List<RepaymentSchedule> overdueList = new ArrayList<>();
			
			// 新产生的逾期记录
			List<String> newOverdueList = new ArrayList<>();
			List<RepaymentStatistics> statList = new ArrayList<>();
			
			Date date = new Date();
			
			SystemCommonConfigEntity scc = new SystemCommonConfigEntity();
			scc.setConfigGroupCode("overdue");
			
			// 查询滞纳金利率配置
			List<SystemCommonConfigEntity> configList = systemCommonConfigService.selectList(scc);
			if(CollectionUtils.isEmpty(configList) || configList.size()<2){
				log.error(LiquidationConstant.LOG_PREFIX+"calculateLateFee 滞纳金利率配置错误！");
				return ;
			}
			
			// 15日以下逾期违约利率
			BigDecimal under15 = new BigDecimal(configList.get(0).getConfigContent());
			BigDecimal over15 = new BigDecimal(configList.get(1).getConfigContent());
			
			// 逾期订单号集合
			List<String> overdueOrderSN = new ArrayList<>();
			for (RepaymentSchedule rs : list) {
				
				if(!overdueOrderSN.contains(rs.getOrderSN())){
					overdueOrderSN.add(rs.getOrderSN());
				}
				
			}
			
			// 按照订单号分组
			Map<String,List<RepaymentSchedule>> map = list.stream().collect(Collectors.groupingBy((RepaymentSchedule::getOrderSN)));
			BigDecimal one = new BigDecimal(1);
			
			LateFeeTaskLogEntity logEntity = new LateFeeTaskLogEntity();
			logEntity.setStartDate(DateUtils.getDayStart());
			logEntity.setEndDate(DateUtils.getDayEnd());
			
			map.forEach((k,v)->{
				
				BigDecimal lateFee = new BigDecimal(0);
				String orderSN = null;
				Long userId = null;
				Date billDate = null;
				RepaymentSchedule rs = null;
				
				int size = v.size();
				if(size > 1){// 超过两期逾期，只计算最后一期的逾期天数，前面的不计算逾期天数
					
					rs = v.get(v.size()-1);// 取最新一期逾期的数据
					orderSN = rs.getOrderSN();
					userId = rs.getCreateBy();
					billDate = rs.getPaymentDueDate();
					
					logEntity.setBillDate(billDate);
					logEntity.setOrderSN(orderSN);
					if(lateFeeTaskLogService.checkIsExecuted(logEntity)){
						log.warn("订单号:{} 今天已经进行过滞纳金计算!",orderSN);
						return;
					}
					
					// 本期逾期天数
					int days = DateUtils.getDifferDay(rs.getPaymentDueDate(), date)-1;
					
					if(days==1){// 第一次逾期
						newOverdueList.add(orderSN);
					}
					
					// 产生的滞纳金 
					// 逾期天数超过15天
					lateFee = rs.getGoodsValue().multiply(over15).multiply(one);
				
					// 修改租金的逾期天数
					rs.setOverdueDay(days);
					
				}else{// 只逾期一期
					
					rs = v.get(0);
					orderSN = rs.getOrderSN();
					userId = rs.getCreateBy();
					billDate = rs.getPaymentDueDate();
					
					logEntity.setBillDate(billDate);
					logEntity.setOrderSN(orderSN);
					if(lateFeeTaskLogService.checkIsExecuted(logEntity)){
						log.warn("订单号:{} 今天已经进行过滞纳金计算!",orderSN);
						return;
					}
					
					lateFee = this.overdueOnePeroid(rs, under15, over15);
					
					if(rs.getOverdueDay()==1){
						newOverdueList.add(orderSN);
					}
					
					
				}
				
				rs.setState(LiquidationConstant.BREACH_STATE_YES);
				log.info(LiquidationConstant.LOG_PREFIX+"订单号:{} 逾期天数:{},滞纳金:{}",orderSN,rs.getOverdueDay(),lateFee);
				overdueList.add(rs);
				LateFeeEntity lff = this.createLateFeeEntity(lateFee, userId, orderSN, billDate);
				updateLateData(rs, lff);
				
			});
			
			
			if(!map.isEmpty()){
				
				log.info(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日违约订单共{}条！",map.size());
				
				XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日生成昨日违约订单共"+map.size()+"条！");
				
			}else{
				
				log.info(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日无违约订单！");
				XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日无违约订单！");
			}
			
			XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日新产生违约订单共"+newOverdueList.size()+"条！");
			log.info(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日新产生违约订单共{}条！"
					,newOverdueList.size());
			
			// 更新违约次数
			if(!statList.isEmpty()){
				repaymentStatisticsService.insertBreachCountBatch(statList);
			}
			
			// 调用订单服务更新订单状态为逾期
			if(CollectionUtils.isNotEmpty(newOverdueList)){
				
				try {
					
					ResponseResult<String> responseResult = orderService.batchUpdateOverDue(newOverdueList);
					
					XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+" 调用订单服务更新订单状态为逾期返回结果responseResult:  "
							+JsonUtils.toJsonString(responseResult));
					log.info(LiquidationConstant.LOG_PREFIX+" 调用订单服务更新订单状态为逾期返回结果responseResult: {}"
							,JsonUtils.toJsonString(responseResult));
					
					StringBuffer sb = new StringBuffer();
				    for (String str : newOverdueList) {
				    	sb.append(str);
				    	sb.append(" ;");
					}
				     
				    log.info(LiquidationConstant.LOG_PREFIX+"新产生逾期订单:{}"
							,sb.toString());
					
				} catch (Exception e) {
					
					log.error(LiquidationConstant.LOG_PREFIX+"calculateLateFee 调用订单服务更新订单状态异常!:{}",e.getLocalizedMessage());
					
				}
			
			}
			
		}else{
			XxlJobLogger.log(LiquidationConstant.LOG_PREFIX+"calculateLateFee 昨日无违约订单产生！");
		}
		
		long end = System.currentTimeMillis();
		log.info(LiquidationConstant.LOG_PREFIX+"calculateLateFee 执行滞纳金计算定时任务结束！共耗时{}毫秒！",end-start);
	}

	private void updateLateData(RepaymentSchedule rs, LateFeeEntity lff) {
		this.saveOrUpdate(lff);
		rs.setActualRepayment(null);
		rs.setCurrentBalance(null);
		rs.setSettleFlag(null);
		rs.setUpdateOn(new Date());
		repaymentScheduleService.updateByPrimaryKeySelective(rs);
		addTaskLog(rs.getOrderSN(), rs.getPaymentDueDate());
	}

	@Override
	public void updateOverdueDayBatch(List<RepaymentSchedule> list) {
		repaymentScheduleMapper.updateOverdueDayBatch(list);
	}

	@Override
	public List<LateFeeEntity> selectListByOrderSNs(List<String> list) {
		return lateFeeMapper.selectListByOrderSNs(list);
	}
	
	/**
	 * 
	 * @Description: 只逾期一期
	 * @param repaymentSchedule
	 * @param under15 低于15日滞纳金利率
	 * @param over15  超过15日滞纳金利率
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月9日
	 */
	private BigDecimal overdueOnePeroid(RepaymentSchedule repaymentSchedule,BigDecimal under15,BigDecimal over15){
		
		Date date = new Date();
		int days = DateUtils.getDifferDay(repaymentSchedule.getPaymentDueDate(), date)-1;
		
		repaymentSchedule.setOverdueDay(days);
		BigDecimal overdueDay = new BigDecimal(days);
		
		BigDecimal fifteen = new BigDecimal(15);
		BigDecimal lateFee = new BigDecimal(0);
		BigDecimal one = new BigDecimal(1);
		
		if(overdueDay.compareTo(fifteen)==1){// 逾期天数超过15天
			// 产生的滞纳金
			lateFee = repaymentSchedule.getGoodsValue().multiply(over15).multiply(one);
		}else{
			lateFee = repaymentSchedule.getGoodsValue().multiply(under15).multiply(one);
		}
		
		return lateFee;
		
	}

	@Override
	public void batchInsert(List<LateFeeEntity> list) {
		lateFeeMapper.batchInsert(list);
	}

	@Override
	public void batchUpdate(List<LateFeeEntity> list) {
		lateFeeMapper.batchUpdate(list);
	}

	@Override
	public void batchUpdateEnableFlag(List<LateFeeEntity> list) {
		for (LateFeeEntity lateFeeEntity : list) {
			lateFeeEntity.setEnableFlag(0);
		}
		lateFeeMapper.batchUpdateEnableFlag(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(List<LateFeeEntity> list) {
		// 更新的数据
		List<LateFeeEntity> updateList = new ArrayList<>();
		// 新增的数据
		List<LateFeeEntity> addList = new ArrayList<>();
		
		List<String> orderSNs = new ArrayList<>(list.size());
		list.stream().forEach(e -> orderSNs.add(e.getOrderSN()));
		
		// 已经存在数据的订单号
		List<LateFeeEntity> poList = batchQueryIsExist(orderSNs);
		if(poList.isEmpty()){
			// 都是新增数据，直接批量插入
			this.batchInsert(list);
			return ;
		}else{
			
			addList = (List<LateFeeEntity>) CollectionUtils.subtract(list, poList);
			updateList = (List<LateFeeEntity>) CollectionUtils.subtract(list, addList);
			
		}
		
		if(!addList.isEmpty()){
			this.batchInsert(addList);
		}
		
		if(!updateList.isEmpty()){
			this.batchUpdate(updateList);
		}
		
	}

	@Override
	public List<LateFeeEntity> batchQueryIsExist(List<String> list) {
		return lateFeeMapper.batchQueryIsExist(list);
	}

	@Override
	public BigDecimal lateFeePayable(String orderSN) {
		BigDecimal amount = lateFeeMapper.lateFeePayable(orderSN);
		if(null == amount){
			amount = new BigDecimal(0);
		}
		return amount;
	}

	@Override
	public Optional<LateFeeEntity> selectLateFee(LateFeeEntity lateFeeEntity) {
		return Optional.ofNullable(lateFeeMapper.selectLateFeeInfo(lateFeeEntity));
	}
	
	private LateFeeEntity createLateFeeEntity(BigDecimal lateFee,Long userId,String orderSN,Date billDate){
		LateFeeEntity lfe = new LateFeeEntity();
		lfe.setAmount(lateFee);
		Date date = new Date();
		lfe.setCreateOn(date);
		lfe.setCurrentBalance(lateFee);
		lfe.setRepayAmount(new BigDecimal(0));
		lfe.setBillDate(billDate);
		lfe.setOrderSN(orderSN);
		lfe.setUserId(userId);
		lfe.setUpdateBy(userId);
		lfe.setUpdateOn(date);
		lfe.setEnableFlag(1);
		return lfe;
		
	}

	@Override
	public ResponseResult<String> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq) {
		log.info(LiquidationConstant.LOG_PREFIX+"doLateFeeRemission:{}",JsonUtils.toJsonString(lateFeeRemissionReq));
		LateFeeEntity lateFeeEntity = new LateFeeEntity();
		lateFeeEntity.setOrderSN(lateFeeRemissionReq.getOrderSN());
		Optional<LateFeeEntity> optional = this.selectLateFee(lateFeeEntity);
		
		if(!optional.isPresent()){
			return ResponseResult.build(LiquidationErrorCode.LATE_FEE_DOREMISSION_DATA_ERROR.getCode(), 
					LiquidationErrorCode.LATE_FEE_DOREMISSION_DATA_ERROR.getMessage(),null);
		}
		
		lateFeeEntity = optional.get();
		
		if(lateFeeRemissionReq.getRemissionAmount().compareTo(lateFeeEntity.getCurrentBalance())==1){
			return ResponseResult.build(LiquidationErrorCode.LATE_FEE_DOREMISSION_MAX_AMOUNT_ERROR.getCode(), 
					LiquidationErrorCode.LATE_FEE_DOREMISSION_MAX_AMOUNT_ERROR.getMessage(),null);
		}
		
		log.info(LiquidationConstant.LOG_PREFIX+"减免之前:{}",JsonUtils.toJsonString(lateFeeEntity));
		
		// 滞纳金核销
		List<LateFeeEntity> list = this.selectUnsettledList(lateFeeRemissionReq.getOrderSN());
		RepaymentReq repaymentReq = new RepaymentReq();
		repaymentReq.setAmount(lateFeeRemissionReq.getRemissionAmount());
		repaymentReq.setUserId(lateFeeRemissionReq.getCreateBy());
		
		Optional<List<LateFeeEntity>> optional2 = this.writeOffLateFee(repaymentReq , list);
		if(optional2.isPresent()){
			List<LateFeeEntity> settledList = optional2.get();
			// 批量更新
			batchUpdate(settledList);
			
			List<RepaymentDetailEntity> repayDetailList = new ArrayList<>();
			for (LateFeeEntity e : settledList) {
				repayDetailList.add(EntityUtils.createRepaymentDetailEntity(e.getUserId(), e.getOrderSN(),
						AccountEnum.ZNJJM.getAccountCode(), null, e.getBillDate(), e.getRepayAmount(),
						LiquidationConstant.REMARK_LATEFEE_REMISSION));
			}
			
			// 记录还款明细
			repaymentDetailService.insertBatch(repayDetailList);
		}
		
		
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public void updateByPrimaryKeySelective(LateFeeEntity lateFeeEntity) {
		lateFeeMapper.updateByPrimaryKeySelective(lateFeeEntity);
	}

	@Override
	public void repayLateFee(RepaymentReq repaymentReq, Long userId, String orderSN) {
		LateFeeEntity lateFeeEntity = new LateFeeEntity();
		lateFeeEntity.setOrderSN(orderSN);
		Optional<LateFeeEntity> optional = this.selectLateFee(lateFeeEntity);
		
		if(!optional.isPresent()){
			return;
		}
		
		lateFeeEntity = optional.get();
		
		if(lateFeeEntity.getCurrentBalance().compareTo(new BigDecimal(0)) == 0){// 未还滞纳金为0
			return;
		}
		
		// 滞纳金核销
		List<LateFeeEntity> list = this.selectUnsettledList(orderSN);
		repaymentReq.setUserId(userId);
		Optional<List<LateFeeEntity>> optional2 = this.writeOffLateFee(repaymentReq , list);
		if(optional2.isPresent()){
			List<LateFeeEntity> settledList = optional2.get();
			// 批量更新
			batchUpdate(settledList);
			
			List<RepaymentDetailEntity> repayDetailList = new ArrayList<>();
			for (LateFeeEntity e : settledList) {
				repayDetailList.add(EntityUtils.createRepaymentDetailEntity(e.getUserId(), e.getOrderSN(),
						AccountEnum.ZNJ.getAccountCode(), repaymentReq.getTransactionSN(), e.getBillDate(), e.getRepayAmount(),
						LiquidationConstant.REMARK_REPAY_LATEFEE));
			}
			
			// 记录还款明细
			repaymentDetailService.insertBatch(repayDetailList);
		}
	}
	/**
	 * 
	 * @Description: 保存或者更新
	 * @param lateFeeEntity
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	private void saveOrUpdate(LateFeeEntity lateFeeEntity){
		LateFeeEntity e = this.selectByCondition(lateFeeEntity);
		if(e == null){
			this.insertSelective(lateFeeEntity);
		}else {
			lateFeeEntity.setId(e.getId());
			this.updateByPrimaryKeySelective(lateFeeEntity);
		}
	}

	@Override
	public LateFeeEntity selectByCondition(LateFeeEntity lateFeeEntity) {
		return lateFeeMapper.selectByCondition(lateFeeEntity);
	}

	@Override
	public void insertSelective(LateFeeEntity lateFeeEntity) {
		lateFeeMapper.insertSelective(lateFeeEntity);
	}

	@Override
	public List<LateFeeEntity> selectUnsettledList(String orderSN) {
		return lateFeeMapper.selectUnsettledList(orderSN);
	}

	@Override
	public Optional<List<LateFeeEntity>> writeOffLateFee(RepaymentReq repaymentReq, List<LateFeeEntity> list) {
		BigDecimal amount = repaymentReq.getAmount();

		List<LateFeeEntity> resultList = new ArrayList<>();
		if (CollectionUtils.isEmpty(list)) {
			return Optional.ofNullable(null);
		}
		
		Date date = new Date();
		for (LateFeeEntity lff : list) {
			if (amount.compareTo(new BigDecimal(0)) == 0) {
				break;
			}
			// 未还金额
			BigDecimal currentBalance = lff.getCurrentBalance();
			int number = amount.compareTo(currentBalance);
			// 偿还金大于等于未偿还金额
			if (number >= 0) {
				amount = amount.subtract(currentBalance);
				lff.setRepayAmount(currentBalance);
				lff.setCurrentBalance(new BigDecimal(0));
			} else {// 偿还金额小于未偿还金额
				BigDecimal balance = currentBalance.subtract(amount);
				lff.setRepayAmount(amount);
				lff.setCurrentBalance(balance);
				// 剩余0
				amount = new BigDecimal(0);
			}

			lff.setUpdateBy(repaymentReq.getUserId());
			lff.setUpdateOn(date);
			lff.setCreateOn(date);
			
			resultList.add(lff);
		}

		repaymentReq.setAmount(amount);
		return Optional.ofNullable(resultList);
	}
	
	private void addTaskLog(String orderSN,Date billDate){
		LateFeeTaskLogEntity lateFeeTaskLogEntity = new LateFeeTaskLogEntity();
		lateFeeTaskLogEntity.setBillDate(billDate);
		lateFeeTaskLogEntity.setOrderSN(orderSN);
		lateFeeTaskLogEntity.setCreateOn(new Date());
		// 记录执行新增定时任务日志
		lateFeeTaskLogService.insert(lateFeeTaskLogEntity);
	}
	
}
