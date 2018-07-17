package org.gz.risk.auditing.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.gz.cache.commom.behavior.IRedisUtil;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.Enum.AuditState;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.OrderCreditDetail;
import org.gz.order.common.entity.UserHistory;
import org.gz.risk.auditing.entity.Admin;
import org.gz.risk.auditing.entity.CreditAuditRuleLog;
import org.gz.risk.auditing.entity.CreditResult;
import org.gz.risk.auditing.entity.LoanUser;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.auditing.entity.Violation;
import org.gz.risk.auditing.rules.CreditModel;
import org.gz.risk.auditing.rules.CreditRuleProvider;
import org.gz.risk.auditing.service.outside.IRentRecordService;
import org.gz.risk.auditing.task.TaskConsumer;
import org.gz.risk.auditing.util.DateUtils;
import org.gz.risk.auditing.util.DroolsUtils;
import org.gz.risk.auditing.util.JsonUtils;
import org.gz.risk.bean.ViolationResp;
import org.gz.risk.common.request.CreditNode;
import org.gz.risk.dao.RiskRuleDao;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @author JarkimZhu Created on 2017/1/17.
 * @since jdk1.8
 */
@Service
public class CreditRuleService implements ServletContextAware {
	private static final Logger logger = LoggerFactory.getLogger("auto-credit-logger");

	public static void log(CreditResult creditResult) {
		// if(creditResult.getResult() != 0) {
		// logger.info("AutoCredit loanRecordId: {}, result: {}, hit rule: {}",
		// creditResult.getLoanRecordId(), creditResult.getResult(),
		// creditResult.getHitRule()
		// );
		// }
	}

	private static final String GROUP_ID = "org.gz.risk";
	private static final String ARTIFACT_ID = "credit-rules";
	private static final String VERSION = "0.9.0";

	private static final int ROWS = 10000;

	private KieServices kieServices;
	private KieContainer kieContainer;
	private ReleaseId releaseId;
	@Value("${credit_rule_file_path}")
	private String RULE_FILE_PATH;
	@Value("${load_loan_user_cache}")
	private boolean loadLoanUserCache;
	@Value("${violation_json_path}")
	private String violationJsonPath;
	// @Value("${RULE_REMOTE_URL}")
	// private String ruleRemoteUrl;
	// @Value("${LOG_KAFKA_URL}")
	// private String logKafkaUrl;
	private Map<String, String> violationMap;
	// @Resource
	// private RuntimeService runtimeService;
	/*
	 * 引用旧的userservice，新的userservice没有查询视图的方法
	 */
	// @Resource
	// private IUserService oldUserService;
	// @Resource
	// private ICreditLoanService creditLoanService;
	// @Resource
	// private LoanRecordService loanRecordService;
	@Resource
	private CreditRuleProvider creditRuleProvider;
	@Resource
	private IRentRecordService rentRecordService;
	@Resource
	private IRedisUtil redisUtil;
	@Resource
	RiskRuleDao riskRuleDao;

	@PostConstruct
	public void init() {
		try {
			kieServices = KieServices.Factory.get();
			releaseId = kieServices.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);

			File ruleFilePath = new File(RULE_FILE_PATH);
			File[] ruleFiles = ruleFilePath.listFiles();
			DroolsUtils.createKieJar(kieServices, releaseId, ruleFiles);
			kieContainer = kieServices.newKieContainer(releaseId);
			initLoanUserCache();
			new Thread(new TaskConsumer(CreditNode.NODE_FIRST_CREDIT, redisUtil, this)).start();
			new Thread(new TaskConsumer(CreditNode.NODE_THIRD_CREDIT,redisUtil,this)).start();
		} catch (Exception e) {
			logger.error("CreditRuleService init error", e.getMessage());

		}

	}

	public void initLoanUserCache() {
		logger.info("CreditRuleService initLoanUserCache start...");
		try {
			if (loadLoanUserCache) {
				creditRuleProvider.clearCache();
				if (!creditRuleProvider.exists()) {// 如果不存在缓存，就初始化加载缓存
					long startTime = System.currentTimeMillis();
					logger.info("initLoanUserCache start ");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -6);
					String cacheDate = DateUtils.getDateString(c.getTime());

					// long total =
					// loanRecordService.countAllLoanUserList(query);
					RentRecordQuery loanRecordReq = new RentRecordQuery();
					loanRecordReq.setStartApplyTime(cacheDate);
					logger.info("CreditRuleService initLoanUserCache countAllLoanUser start...");
					ResponseResult<Integer> result = rentRecordService.countByapplyTime(loanRecordReq);
					logger.info("CreditRuleService initLoanUserCache countAllLoanUser end...");
					Integer total = result.getData();
					logger.info("CreditRuleService initLoanUserCache total =" + total);
					logger.info("initLoanUserCache countAllLoanUserList end 耗时"
							+ (System.currentTimeMillis() - startTime) + "ms");
					int batch = (int) Math.ceil((double) total / ROWS);
					for (int i = 1; i <= batch; i++) {
						// List<LoanUser> loanUserList =
						// loanRecordService.findAllLoanUserList(query);
						loanRecordReq = new RentRecordQuery();
						loanRecordReq.setStartApplyTime(cacheDate);
						loanRecordReq.setCurrPage(i);
						loanRecordReq.setPageSize(ROWS);
						logger.info("CreditRuleService initLoanUserCache findAllLoanUserList start" + i);
						ResponseResult<List<UserHistory>> res = rentRecordService.queryUserHistoryList(loanRecordReq);
						logger.info("CreditRuleService initLoanUserCache findAllLoanUserList end" + i);
						if (null != res && res.getErrCode() == 0) {
							List<UserHistory> list = res.getData();
							List<LoanUser> loanUserList = new ArrayList<LoanUser>();
							for (UserHistory item : list) {
								LoanUser l1 = new LoanUser();
								l1.setUserHistory(item);
								loanUserList.add(l1);
							}
							logger.info("CreditRuleService initLoanUserCache addBatchToCache start，list="
									+ loanUserList.size() + "-" + loanUserList.size());
							creditRuleProvider.addBatchToCache(loanUserList);
							logger.info("CreditRuleService initLoanUserCache addBatchToCache end" + i);
						} else {
							logger.info("findAllLoanUserList;Result;Code:{},Message:{};", result.getErrCode(),
									result.getErrCode());
						}

					}
					logger.info(
							"initLoanUserCache taskExecutor end 耗时" + (System.currentTimeMillis() - startTime) + "ms");
				} else {// 如果存在key，进行初始化数据从redis缓存
					logger.info("CreditRuleService initLoanUserCache initDataFromRedis");
					creditRuleProvider.initDataFromRedis();
				}
			}

		} catch (Exception e) {
			logger.error("initLoanUserCache fail:" + e.getMessage());
		}

	}

	public void updateRules() {
		try {
			File ruleFilePath = new File(RULE_FILE_PATH);
			File[] ruleFiles = ruleFilePath.listFiles();
			DroolsUtils.createKieJar(kieServices, releaseId, ruleFiles);
			kieContainer.updateToVersion(releaseId);
		} catch (Exception e) {
			logger.error("updateRules fail:" + e);
			throw e;
		}

	}

	private void fireRule(String phase, String kSessionName, CreditModel creditModel, CreditResult creditResult) {
		KieSession kieSession = null;
		try {
			kieSession = kieContainer.newKieSession(kSessionName);
			kieSession.getAgenda().getAgendaGroup(phase).setFocus(); // 获得执行焦点
			kieSession.insert(creditModel);
			kieSession.insert(creditResult);
			kieSession.fireAllRules();
			// Utility.help(kieSession, "111");
			// StatefulKnowledgeSession statefulSession =
			// knowledgeBase.newStatefulKnowledgeSession();
			// statefulSession.getAgenda().getAgendaGroup("002").setFocus();
			// //获得执行焦点
			// statefulSession.fireAllRules();
			// statefulSession.dispose();
		} catch (Exception e) {
			logger.error("CreditRuleService fireRule:" + e);
			throw e;
		} finally {
			if (kieSession != null) {
				kieSession.dispose();
			}
		}
	}

	@Transactional
	public synchronized CreditResult autoCredit(String phase, String loanRecordId, User user,
			List<ViolationResp> violations) {
		CreditModel creditModel = new CreditModel();
		creditModel.setCreditRuleService(this);
		creditModel.setLoanRecordId(loanRecordId);
		creditModel.setUser(user);
		creditModel.setViolations(violations);
		List<CreditAuditRuleLog> creditAuditRuleLogs = new ArrayList<CreditAuditRuleLog>();
		creditModel.setCreditAuditRuleLogs(creditAuditRuleLogs);

		CreditResult creditResult = new CreditResult();
		creditResult.setLoanRecordId(loanRecordId);
		try {
			String ruleGroup = "";
			if (CreditNode.NODE_THIRD_CREDIT.equals(phase)) { // 三审加入审批队列
				ruleGroup = "3";
			} else if (CreditNode.NODE_FIRST_CREDIT.equals(phase)) { // 一审加入审批队列
				ruleGroup = "1";
			} else {
				logger.error("autoCredit fireRule fail " + "不支持这种分单类型");
				// throw new Exception("不支持这种分单类型");
			}

			long beginTime = System.currentTimeMillis();
			// logger.info("begin firerule "+ new
			// JSONObject().toJSONString(creditResult));
			fireRule(ruleGroup, "CreditRules", creditModel, creditResult);
			String endTimeS = (System.currentTimeMillis() - beginTime) + "毫秒";
			logger.info("after firerule time " + endTimeS + " " + loanRecordId + " "
					+ new JSONObject().toJSONString(creditResult));
			autoProcessFlow(creditModel, creditResult);
		} catch (Exception e) {
			logger.error("autoCredit fireRule fail " + e.getMessage());
			throw e;
		}
		// batchSendLog(creditModel.getCreditAuditRuleLogs());
		return creditResult;
	}

	public void batchSendLog(List<CreditAuditRuleLog> creditAuditRuleLogs) {
		// String
		// reqjson=com.alibaba.fastjson.JSONObject.toJSONString(creditAuditRuleLogs);
		// logger.info("batchSendLog req"+reqjson);
		// try {
		// String rep=
		// HttpUtils.doPostJson(logKafkaUrl+"/creditAuditRuleLog/batchSendLog",
		// reqjson);
		// logger.info("batchSendLog rep"+rep);
		// } catch (Exception e) {
		// logger.error("sendLog {}",reqjson,e);
		// }
	}

	//
	private void autoProcessFlow(CreditModel creditModel, CreditResult creditResult) {
//	     logger.info(" autoProcessFlow creditModel = {},creditResult = {} ",JsonUtil.toJson(creditModel),JsonUtil.toJson(creditResult));
		String loanRecordId = creditModel.getLoanRecordId();

		/*
		 * logger.info("CreditRuleService autoProcessFlow:"); log(creditResult);
		 */
		try {
			ResponseResult<OrderDetailResp> res = rentRecordService.queryBackOrderDetail(loanRecordId);
			if (res.getErrCode() == 0) {
				OrderDetailResp resp = res.getData();

				if (creditResult.getResult() == 2) { // 拒绝

//					Credit credit = new Credit();
//
//					credit.setProcessInstanceId(loanRecordId);
//					credit.setLoanRecordId(loanRecordId);
//					credit.setAction(2);
//					credit.setRemark(creditResult.getHitRule());
//					credit.setRefuseCategory(creditResult.getRefuseCategory());
//					credit.setReason("您好，根据您提供的资料综合评估，目前评分较低，暂时未达到批核要求，感谢您的申请，谢谢！");
//					credit.setLock(true);

					Admin admin = new Admin();
					admin.setUserId("system");
					UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
					updateOrderState.setRentRecordNo(loanRecordId);
					updateOrderState.setState(2);
					updateOrderState.setCreateBy(0L);
					updateOrderState.setCreateMan("system");
					updateOrderState.setRemark("");
					updateOrderState.setLng("");
					updateOrderState.setLat("");
					updateOrderState.setSealAgreementUrl("");
					updateOrderState.setEvid("");
					ResponseResult<String> ret = rentRecordService.audit(updateOrderState);
					if (ret.getErrCode() == 0) {
						OrderCreditDetail orderCreditDetail = new OrderCreditDetail();
						orderCreditDetail.setCreateBy(0L);
						orderCreditDetail.setCreateOn(new Date());
						orderCreditDetail.setCreditOn(new Date());
						orderCreditDetail.setUpdateBy(0L);
						orderCreditDetail.setUserId(creditModel.getLoanUser().getUserHistory().getUserId());
						orderCreditDetail.setOrderNo(loanRecordId);
						orderCreditDetail.setHitRule(creditResult.getHitRule());
						orderCreditDetail.setCreditType(resp.getCreditState().byteValue());
						orderCreditDetail.setCreditResult(AuditState.AuditRefusal.getCode().byteValue());

						rentRecordService.add(orderCreditDetail);
					} else {
						logger.error("autoProcessFlow ,error={}", res.getErrMsg());

					}

					// creditLoanService.processFlow(credit, admin);

				} else if (creditResult.getResult() == 1) { // 通过

//					Credit credit = new Credit();
//
//					credit.setProcessInstanceId(loanRecordId);
//					credit.setLoanRecordId(loanRecordId);
//					credit.setAction(1);
////					credit.setAmount(creditModel.getLoanUser().getApplyAmount().doubleValue());
//					credit.setRemark(creditResult.getHitRule());
//					credit.setRefuseCategory(creditResult.getRefuseCategory());
//					Admin admin = new Admin();
//					admin.setUserId("system");

					if (resp.getCreditState() == 1) {// 一审通过直接改为二审

						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("rentRecordNo", loanRecordId);
						map.put("creditState", 2);
						ResponseResult<String> ret = rentRecordService.updateCreditState(map);

						if (ret.getErrCode() == 0) {
							OrderCreditDetail orderCreditDetail = new OrderCreditDetail();
							orderCreditDetail.setCreateBy(0L);
							orderCreditDetail.setCreateOn(new Date());
							orderCreditDetail.setCreditOn(new Date());
							orderCreditDetail.setUpdateBy(0L);
							orderCreditDetail.setUserId(creditModel.getLoanUser().getUserHistory().getUserId());
							orderCreditDetail.setOrderNo(loanRecordId);
							orderCreditDetail.setHitRule(creditResult.getHitRule());
							orderCreditDetail.setCreditType(resp.getCreditState().byteValue());
							orderCreditDetail.setCreditResult(AuditState.AuditPass.getCode().byteValue());
							rentRecordService.add(orderCreditDetail);
						} else {
							logger.error("autoProcessFlow ,error={}", res.getErrMsg());
						}

					} else if (resp.getCreditState() == 3) { // 三审通过直接改为待支付
						UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
						updateOrderState.setRentRecordNo(loanRecordId);
						updateOrderState.setState(3);
						ResponseResult<String> ret = rentRecordService.audit(updateOrderState);
						if (ret.getErrCode() == 0) {
							OrderCreditDetail orderCreditDetail = new OrderCreditDetail();
							orderCreditDetail.setCreateBy(0L);
							orderCreditDetail.setCreateOn(new Date());
							orderCreditDetail.setCreditOn(new Date());
							orderCreditDetail.setUpdateBy(0L);
							orderCreditDetail.setUserId(creditModel.getLoanUser().getUserHistory().getUserId());
							orderCreditDetail.setOrderNo(loanRecordId);
							orderCreditDetail.setHitRule(creditResult.getHitRule());
							orderCreditDetail.setCreditType(resp.getCreditState().byteValue());
							orderCreditDetail.setCreditResult(AuditState.AuditPass.getCode().byteValue());
							rentRecordService.add(orderCreditDetail);
						} else {
							logger.error("autoProcessFlow ,error={}", res.getErrMsg());
						}
					} else {
						logger.error("autoProcessFlow ,resp.getCreditState()类型 error={}", resp.getCreditState());
					}

					// creditLoanService.processFlow(credit, admin);

					/*
					 * //按分配最少原则 分配给 CreditOfficerMachine Task task =
					 * taskService.createTaskQuery()
					 * .processInstanceBusinessKey(credit.getLoanRecordId())
					 * //.processDefinitionKey(CreditNode.NODE_SEC_CREDIT)
					 * .singleResult(); String officerId =
					 * assignApplyProvider.getToUserId(2);
					 * taskService.claim(task.getId(), officerId);
					 * assignApplyProvider.increaseOfficeCurrentNum(2,
					 * officerId);
					 */

				}
				// else if(creditResult.getResult() == 3) { // 复借通过 放款
				// Credit credit = new Credit();
				//
				// credit.setProcessInstanceId(loanRecordId);
				// credit.setLoanRecordId(loanRecordId);
				// credit.setAction(1);
				// credit.setAmount(creditModel.getLoanUser().getApplyAmount().doubleValue());
				// credit.setRemark(creditResult.getHitRule());
				// credit.setRefuseCategory(creditResult.getRefuseCategory());
				// Admin admin = new Admin();
				// admin.setUserId("system");
				// admin.setIp("borrow");
				// creditLoanService.processFlow(credit, admin);
				// creditLoanService.processFlow(credit, admin);
				// } else {
				// 自动分单
				// distributionOrderService.automaticDistribution(processInstance.getProcessInstanceId());
				// }
			} else {
				logger.error("autoProcessFlow ,error={}", res.getErrMsg());
			}
		} catch (Exception e) {
			logger.error("autoProcessFlow ,error={}", e.getMessage());
			throw e;
		}

	}

	/**
	 * 查询当前用户风控数据
	 */
	public LoanUser getLoanUser(String loanRecordId) {
		try {

			ResponseResult<UserHistory> result = rentRecordService.queryUserHistory(loanRecordId);
			if (null != result && result.getErrCode() == 0) {
				UserHistory userHistory =  result.getData();
				
				LoanUser loanUser = new LoanUser();
				loanUser.setUserHistory(userHistory);
				creditRuleProvider.addToCache(loanUser, loanRecordId);
				return loanUser;
			} else {
				logger.info("RecordService;getLoanUser;Code:{},Message:{}", result.getErrCode(), result.getErrMsg());
				return null;
			}
		} catch (Exception e) {
			logger.error("CreditRuleService getLoanUser " + e.getMessage(), e);
			throw e;
		}
	}

	public List<LoanUser> getAllLoanUserList(int field, int amount) {
		Date start = null;
		try {
			Calendar c = Calendar.getInstance();
			c.add(field, -amount);
			start = DateUtils.getDayStart(c.getTime());
			return creditRuleProvider.findAllLoanUserList(start);
		} catch (Exception e) {
			logger.error("CreditRuleService getAllLoanUserList start[" + start + "]" + e.getMessage(), e);
			throw e;
		}
	}

	public List<LoanUser> getAllLoanUserList() {
		return creditRuleProvider.findAllLoanUserList();
	}

	public List<Violation> getViolationList(List<ViolationResp> violations) {
		try {
			return violations.stream().map(entity -> {
				Violation violation = new Violation(entity);
				if (entity.getBelong() == 101) {
					violation.setContent(violationMap.get(entity.getViolationKey()));
				} else if (entity.getBelong() >= 201 && entity.getBelong() <= 204) {
					violation.setContent(violationMap.get(entity.getViolationKey()) + " " + entity.getViolationValue());
				} else {
					violation.setContent(entity.getViolationValue());
				}
				return violation;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("CreditRuleService getViolationList " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {

		violationMap = JsonUtils.parseObject(new File(violationJsonPath), new TypeReference<Map<String, String>>() {
		});
	}

	public void setViolationMap(Map<String, String> violationMap) {
		this.violationMap = violationMap;
	}

	/// 调用规则
	public boolean isAbnormalContactsKill(String idNo) throws Exception {
		try {
			return riskRuleDao.getAbnormalContactsKill(idNo);
		} catch (Exception e) {
			logger.error("CreditRuleService isAbnormalContactsKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isApplyKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getApplyKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isApplyKillCall " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isBlackTelKill(String idNo) throws Exception {
		try {
			return riskRuleDao.getBlackTelKill(idNo);
		} catch (Exception e) {
			logger.error("CreditRuleService isBlackTelKill {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isCertlengKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getCertlengKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isCertlengKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isChannelKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getChannelKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isChannelKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isContactKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getContactKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isContactKill {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isCoupleKill(String loanRecordId) throws Exception {
		try {
			return riskRuleDao.getCoupleKill(loanRecordId);
		} catch (Exception e) {
			logger.error("CreditRuleService isCoupleKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isDenullKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getDenullKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isDenullKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isDeviceShareKill(String deviceId) throws Exception {
		try {
			return riskRuleDao.getDeviceShareKill(deviceId);
		} catch (Exception e) {
			logger.error("CreditRuleService isDeviceShareKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isDiffMaterial(Long userId) throws Exception {
		try {
			return riskRuleDao.getDiffMaterial(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isDiffMaterial {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isDiffSpouseKill(String idNo) throws Exception {
		try {
			return riskRuleDao.getDiffSpouseKill(idNo);
		} catch (Exception e) {
			logger.error("CreditRuleService isDiffSpouseKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isDueContactKill(String phone) throws Exception {
		try {
			return riskRuleDao.getDueContactKill(phone);
		} catch (Exception e) {
			logger.error("CreditRuleService isDueContactKill " + e.getMessage(), e);
			throw e;
		}
	}
	public boolean isBairongAfublackKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getBairongAfublackKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isBairongAfublackKill " + e.getMessage(), e);
			throw e;
		}
	}
	
	public boolean isBairongBaifuKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getBairongBaifuKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService getBairongBaifuKill " + e.getMessage(), e);
			throw e;
		}
	}


	public boolean isEmergencyKill(String emergencyContactPhone) throws Exception {
		try {
			return riskRuleDao.getEmergencyKill(emergencyContactPhone);
		} catch (Exception e) {
			logger.error("CreditRuleService isEmergencKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isIdKill(String idNo) throws Exception {
		try {
			return riskRuleDao.getIdKill(idNo);
		} catch (Exception e) {
			logger.error("CreditRuleService isIdKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isIpKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getIpKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isCertlengKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isLoanContactsKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getLoanContactsKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isLoanContactsKill {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isOutlookKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getOutlookKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isPasswordKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isRejectKill(String phoneNum) throws Exception {
		try {
			return riskRuleDao.getRejectKill(phoneNum);
		} catch (Exception e) {
			logger.error("CreditRuleService isRejectKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameContactsIdKill(String emergencyContactPhone) throws Exception {
		try {
			return riskRuleDao.getSameContactsIdKill(emergencyContactPhone);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameContactsTelKill {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameContactsNameKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getSameContactsNameKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameContactsNameKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameContactsTelKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getSameContactsTelKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameContactsTelKill {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameCurrentAddKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getSameCurrentAddKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameCurrentAddrKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameIpKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getSameIpKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameIpKill " + e.getMessage(), e);
			throw e;
		}
	}

	public Integer getModelScore(long userId) throws Exception {
		try {
			return riskRuleDao.getScore(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService getModelScore " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isAbnormalInfoKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getAbnormalInfoKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isAbnormalInfoKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isBlackCompanyKilled(Long userId) throws Exception {
		try {
			return riskRuleDao.getBlackCompanyKilled(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isBlackCompanyKill {} {} ", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isCompanyCellNumkill(String companyContactNumber) throws Exception {
		try {
			return riskRuleDao.getCompanyCellNumkill(companyContactNumber);
		} catch (Exception e) {
			logger.error("CreditRuleService isCompanyCellNumkill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isIndustryKill(Long userId) throws Exception {
		try {
			return riskRuleDao.getIndustryKill(userId);
		} catch (Exception e) {
			logger.error("CreditRuleService isIndustryKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameCompanyAddKill(String companyAddress) throws Exception {
		try {
			return riskRuleDao.getSameCompanyAddKill(companyAddress);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameCompanyAddrKill " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean isSameCompanyPassword(String companyContactNumber) throws Exception {
		try {
			return riskRuleDao.getSameCompanyPassword(companyContactNumber);
		} catch (Exception e) {
			logger.error("CreditRuleService isSameCompanyPassword {} {} ", e.getMessage(), e);
			throw e;
		}
	}

}
