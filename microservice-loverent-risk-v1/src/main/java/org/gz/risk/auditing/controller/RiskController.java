package org.gz.risk.auditing.controller;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.UserHistory;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.auditing.service.CreditRuleService;
import org.gz.risk.auditing.service.IThirdPartyDataService;
import org.gz.risk.auditing.service.outside.IRentRecordService;
import org.gz.risk.common.request.CreditNode;
import org.gz.risk.common.request.RiskReq;
import org.gz.risk.dao.util.JsonUtil;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {
	private static final Logger logger = LoggerFactory.getLogger(RiskController.class);
	@Autowired
	private IThirdPartyDataService thirdPartyDataService;
	@Autowired
	private IRentRecordService rentRecordService;
	@Autowired
	private UserService userService;
	@Autowired
	private CreditRuleService creditRuleService;
	

	/**
	 * 一审和三审进入分单队列
	 * 
	 * @param riskReq
	 * @return
	 */
	@RequestMapping(value = "/api/risk/processAutoCredit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> processAutoCredit(@RequestBody RiskReq riskReq) {
        logger.info(" RiskController.processAutoCredit req = {}",JsonUtil.toJson(riskReq));
        
		if (CreditNode.NODE_THIRD_CREDIT.equals(riskReq.getPhase()) || CreditNode.NODE_FIRST_CREDIT.equals(riskReq.getPhase())) { // 三审加入审批队列
			ResponseResult<UserHistory> ret = rentRecordService.queryUserHistory(riskReq.getLoanRecordId());
			if (ret.getErrCode() != 0) {
				logger.error("查询用户历史信息失败：{}",ret.getErrMsg());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
						ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		
			UserHistory userHistory = ret.getData();
			
			ResponseResult<AppUser> result = userService.queryUserById(userHistory.getUserId());
			if (result.getErrCode() != 0) {
				logger.error("查询用户信息失败：{}",ret.getErrMsg());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
						ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
			AppUser appUser = result.getData();
			User user = new User();
			
			ResponseResult<RentRecordExtends> result1 = rentRecordService.getByRentRecordNo(userHistory.getRentRecordNo());
			if (result1.getErrCode() == 0) {
				RentRecordExtends rentRecordExtends = result1.getData();
				user.setEmergencyContact(rentRecordExtends.getEmergencyContact());
				user.setEmergencyContactPhone(rentRecordExtends.getEmergencyContactPhone());
			}else{
				logger.error("查询订单扩展信息失败：{}",ret.getErrMsg());
			}
			ResponseResult<OrderDetailResp> res = rentRecordService.queryBackOrderDetail(riskReq.getLoanRecordId());
			if (res.getErrCode() == 0) {
				OrderDetailResp resp = res.getData();
				user.setChannelNo(resp.getChannelNo());
			}else{
				   logger.error("查询订单信息失败：{} = {}",riskReq.getLoanRecordId(),ret.getErrMsg());
			}
			user.setUserHistory(userHistory);
			user.setAppUser(appUser);
			user.setUserId(userHistory.getUserId());

			user.setLoanRecordId(riskReq.getLoanRecordId());
			thirdPartyDataService.addViolation(riskReq.getPhase(), riskReq.getLoanRecordId(), user);
			logger.info("加入审批队列成功");
			return ResponseResult.buildSuccessResponse("加入审批队列成功");
		}else {
			return ResponseResult.build(ResponseStatus.RISK_NOT_SUPPORT_TYPE.getCode(),
					ResponseStatus.RISK_NOT_SUPPORT_TYPE.getMessage(), null);
		}

	}
	
	/**
	 * 一审和三审进入分单队列
	 * 
	 * @param riskReq
	 * @return
	 */
	@RequestMapping(value = "/api/risk/updateRules", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> processAutoCredit() {
		ResponseResult<String> ret = null;
		try {
			creditRuleService.updateRules();
			ret = ResponseResult.buildSuccessResponse("重新加载规则成功");
		} catch (Exception e) {
		    logger.error("processAutoCredit fail:"+e);
		    ret =ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),
					ResponseStatus.DATA_UPDATED_ERROR.getMessage(), "");
	          throw e;
	          
		}
		return ret;

	}
}
