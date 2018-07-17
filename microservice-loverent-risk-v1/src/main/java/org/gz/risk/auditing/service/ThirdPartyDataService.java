package org.gz.risk.auditing.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.cache.commom.behavior.IRedisUtil;
import org.gz.cache.commom.constant.RedisConstant;
import org.gz.common.http.CommonUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.AssertUtils;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.UserHistory;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.auditing.service.outside.IRentRecordService;
import org.gz.risk.bean.Result;
import org.gz.risk.bean.VioUserHistoryReq;
import org.gz.risk.bean.ViolationResp;
import org.gz.risk.common.request.CreditNode;
import org.gz.risk.dao.util.JsonUtil;
import org.gz.risk.intf.ViolationService;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.jbpm.process.instance.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @author JarkimZhu Created on 2017/1/6.
 * @since jdk1.8
 */
@Service
public class ThirdPartyDataService implements IThirdPartyDataService {

	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyDataService.class);

	@Resource
	private ViolationService violationService;

	// @Resource
	// private ICreditRuleService creditRuleService;

	/*
	 * @Resource private NumberInformationProvider numberInformationProvider;
	 */

	/*
	 * @Resource private PhonecallDetailsProvider phonecallDetailsProvider;
	 */

	/*
	 * @Resource private IUserService userService;
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private IRedisUtil redisUtil;

	@Resource
	private IRentRecordService rentRecordService;

	@Override
	@Async("ThreadPoolTaskExecutor")
	public void addViolation(String phase, String loanRecordId, User user) {
		try {
		    logger.info(" ThirdPartyDataService req = {} = {} = {}",JsonUtil.toJson(phase),JsonUtil.toJson(loanRecordId),JsonUtil.toJson(user));
			VioUserHistoryReq req = BeanConvertUtil.convertBean(user, VioUserHistoryReq.class);
			req.setId(user.getUserHistory().getUserId());
			violationService.addAll(user);
			logger.info("addViolation...");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			processAutoCredit(phase, loanRecordId, user);
			logger.info("processAutoCredit finished...");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Scheduled(cron="0 0 1 * * ?")//每天零晨1点执行定时任务
	@Override
	public void autoCreditCompensation() {
		logger.info("autoCreditCompensation start");
		ResponseResult<List<RentRecord>> result = this.rentRecordService.queryRentRecordByState(BackRentState.ApprovalPending.getCode());
	     logger.info(" ThirdPartyDataService req = {} ",JsonUtil.toJson(result));
		if (result != null && result.isSuccess() && CollectionUtils.isNotEmpty(result.getData())) {
			List<RentRecord> recordList = result.getData();
			String loanRecordId = null;
			String phase=null;
			for (RentRecord record : recordList) {
				if (record != null && (record.getCreditState() ==1 ||record.getCreditState() ==3)) {//一审和三审才加队列
					loanRecordId = record.getRentRecordNo();
					ResponseResult<UserHistory> ret = rentRecordService.queryUserHistory(loanRecordId);
					if (ret.getErrCode() != 0) {
						logger.error("autoCreditCompensation 查询用户历史信息失败：{}",ret.getErrMsg());
						continue;
					}
				
					UserHistory userHistory = ret.getData();
					
					ResponseResult<AppUser> result2 = userService.queryUserById(userHistory.getUserId());
					if (result2.getErrCode() != 0) {
						logger.error("autoCreditCompensation 查询用户信息失败：{}",result2.getErrMsg());
						continue;
					}
					
				
					AppUser appUser = result2.getData();
					User user = new User();
					
					ResponseResult<RentRecordExtends> result1 = rentRecordService.getByRentRecordNo(userHistory.getRentRecordNo());
					if (result1.getErrCode() == 0) {
						RentRecordExtends rentRecordExtends = result1.getData();
						user.setEmergencyContact(rentRecordExtends.getEmergencyContact());
						user.setEmergencyContactPhone(rentRecordExtends.getEmergencyContactPhone());
					}else{
						logger.error("autoCreditCompensation 查询订单扩展信息失败：{}",result1.getErrMsg());
					}	
					
					ResponseResult<OrderDetailResp> res = rentRecordService.queryBackOrderDetail(loanRecordId);
					if (res.getErrCode() == 0) {
						OrderDetailResp resp = res.getData();
						user.setChannelNo(resp.getChannelNo());
					}else{
					   logger.error("autoCreditCompensation 查询订单信息失败：{} = {}",loanRecordId,ret.getErrMsg());
					}
					
					user.setUserHistory(userHistory);
					user.setAppUser(appUser);
					user.setUserId(userHistory.getUserId());

					user.setLoanRecordId(loanRecordId);
					phase=getCreditState(record.getCreditState());
					addViolation(phase,loanRecordId, user);
					}else {
						logger.error("autoCreditCompensation 订单服务异常或未查询到用户历史记录");
					}
				}
		}
		else {
			logger.error("autoCreditCompensation 订单服务异常或未查询到待审核的订单");
		}
		logger.info("autoCreditCompensation end");
	}

	/**
	 * 
	* @Description: 审核状态转码
	* @param  creditState	订单系统rent_record表中的creditState：1进入一审 2进入二审 3进入三审
	* @return String
	 */
	private String getCreditState(Integer creditState) {
		String result = null;
		if (AssertUtils.isPositiveNumber4Int(creditState)) {
			switch (creditState.intValue()) {
			case 1:
				result = CreditNode.NODE_FIRST_CREDIT;
				break;
			case 2:
				result = CreditNode.NODE_SEC_CREDIT;
				break;
			case 3:
				result = CreditNode.NODE_THIRD_CREDIT;
				break;
			default:
				break;
			}
		}
		return result;
	}
	
	// @Override
	// public void autoCreditCompensation() {
	// logger.info("autoCreditCompensation start");
	// List<Task> tasks = taskService.createTaskQuery()
	// .taskDefinitionKey(CreditNode.NODE_FIRST_CREDIT)
	// .taskUnassigned()
	// .list();
	// HashSet<String> processIds =
	// tasks.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toCollection(HashSet::new));
	//
	// List<ProcessInstance> instances = runtimeService
	// .createProcessInstanceQuery()
	// .processInstanceIds(processIds)
	// .orderByProcessInstanceId().asc()
	// .list();
	// List<List<ProcessInstance>> lists= SplitList(instances, 3);
	// for (int i = 0; i < lists.size(); i++) {
	// int finalI=i;
	// //taskExecutorTwo.execute(() -> {
	// for (ProcessInstance instance : lists.get(finalI)) {
	// try {
	// String procesId = instance.getProcessInstanceId();
	// String loanRecordId = historyService.createHistoricProcessInstanceQuery()
	// .processInstanceId(procesId)
	// .singleResult().getBusinessKey();
	// //LoanUser loanUser = loanRecordService.getLoanUser(loanRecordId);
	// LoanRecordReq loanReq = new LoanRecordReq();
	// loanReq.setLoanRecordId(loanRecordId);
	// Result result = loanRecordService.getLoanUser(loanReq);
	// LoanUserResp loanUser = (LoanUserResp) result.getData();
	// if (null==loanUser)
	// continue;
	// //cn.shebaodai.modules.user.entity.dto.User user =
	// userService.findUserById(loanUser.getHistoryId());
	// UserHistoryQueryReq userHistoryReq = new UserHistoryQueryReq();
	// userHistoryReq.setId(loanUser.getHistoryId());
	// Result result2 = userHistoryService.queryUserHistoryById(userHistoryReq);
	// UserHistoryResp resp = (UserHistoryResp) result2.getData();
	// try {
	// VioUserHistoryReq user = BeanConvertUtil.convertBean(resp,
	// VioUserHistoryReq.class);
	// violationService.addAll(user);
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// }
	// try {
	// User user = BeanConvertUtil.convertBean(resp, User.class);
	// user.setHistoryId(resp.getId());
	// processAutoCredit(loanRecordId, user);
	// } catch (Exception e) {
	// logger.error(e.getMessage(),e);
	// }
	// } catch (Exception e) {
	// logger.error("autoCreditCompensation {} error {}",finalI,e);
	// continue;
	// }
	// }
	// //});
	// logger.info("autoCreditCompensation {} end ",i);
	// }
	// logger.info("autoCreditCompensation all");
	// }

	public List<List<ProcessInstance>> SplitList(List<ProcessInstance> sList, int num) {
		List<List<ProcessInstance>> eList = new ArrayList<>();
		List<ProcessInstance> gList;
		int size = (sList.size()) / num;
		int size2 = (sList.size()) % num;
		int j;
		int xx = 0;
		for (int i = 0; i < num; i++) {
			gList = new ArrayList<>();

			for (j = xx; j < (size + xx); j++) {
				gList.add(sList.get(j));
			}
			xx = j;
			eList.add(gList);
		}
		if (size2 != 0) {
			gList = new ArrayList<>();
			for (int y = 1; y < size2 + 1; y++) {
				gList.add(sList.get(sList.size() - y));
			}
			eList.add(gList);
		}
		return eList;
	}

	private void processAutoCredit(String phase, String loanRecordId, User user)
			throws UnsupportedEncodingException {
		// QueryViolationListReq req = BeanConvertUtil.convertBean(user,
		// QueryViolationListReq.class);
		Result vioResult = violationService.getViolationListByUser(user);
		List<ViolationResp> violations = (List<ViolationResp>) vioResult.getData();
		if (CommonUtils.isBlank(violations)) {
			logger.info("processAutoCredit violationEntities is blank. loanRecordId: {}", loanRecordId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanRecordId", loanRecordId);
		map.put("user", user);
		map.put("violationEntities", violations);
		map.put("createTime", Calendar.getInstance().getTime());
		String key = "";
		if (CreditNode.NODE_THIRD_CREDIT.equals(phase)) { // 三审加入审批队列
			key = redisUtil.createKey(RedisConstant.RISK_MODULE, RedisConstant.THREE_ORDER_QUEUE);
			logger.info("加入三审缓存队列", loanRecordId);
		} else if (CreditNode.NODE_FIRST_CREDIT.equals(phase)) { // 一审加入审批队列
			key = redisUtil.createKey(RedisConstant.RISK_MODULE, RedisConstant.ONE_ORDER_QUEUE);
			logger.info("加入一审缓存队列", loanRecordId);
		}

		String datajson = JSON.toJSONString(map);
		redisUtil.lpush(key, datajson);
		logger.info("processAutoCredit...");
	}

}
