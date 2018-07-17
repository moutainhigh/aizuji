package org.gz.risk.auditing.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gz.cache.commom.behavior.IRedisUtil;
import org.gz.cache.commom.constant.RedisConstant;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.auditing.service.CreditRuleService;
import org.gz.risk.bean.ViolationResp;
import org.gz.risk.common.request.CreditNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author dell
 *
 */
public class TaskConsumer implements Runnable {
	private IRedisUtil redisUtil;
	private CreditRuleService creditRuleService;
	private String phase;
	private static final Logger logger = LoggerFactory.getLogger("auto-credit-logger");

	public TaskConsumer(String phase,IRedisUtil redisUtil, CreditRuleService creditRuleService) {
		super();
		this.redisUtil = redisUtil;
		this.creditRuleService = creditRuleService;
		this.phase=phase;
	}

	public IRedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(IRedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	public CreditRuleService getCreditRuleService() {
		return creditRuleService;
	}

	public void setCreditRuleService(CreditRuleService creditRuleService) {
		this.creditRuleService = creditRuleService;
	}

	public void run() {
	  	String key = "";
//	  	String succKeyInit ="";
	  	String succTotalKey ="";
	  	String failKeyInit ="";
			if(CreditNode.NODE_THIRD_CREDIT.equals(phase)){  //三审加入审批队列
				key = redisUtil.createKey(RedisConstant.RISK_MODULE, RedisConstant.THREE_ORDER_QUEUE);
//				succKeyInit = redisUtil.createKey(key,RedisConstant.SUCCESS);
				failKeyInit = redisUtil.createKey(key,RedisConstant.FAID);
				succTotalKey = redisUtil.createKey(key,RedisConstant.SUCCESS);
			}else if(CreditNode.NODE_FIRST_CREDIT.equals(phase)){ //一审加入审批队列
				key = redisUtil.createKey(RedisConstant.RISK_MODULE, RedisConstant.ONE_ORDER_QUEUE);
//				succKeyInit = redisUtil.createKey(key,RedisConstant.SUCCESS);
				failKeyInit = redisUtil.createKey(key,RedisConstant.FAID);
				succTotalKey = redisUtil.createKey(key,RedisConstant.SUCCESS);
			}	
		User user = null;
		List<ViolationResp> violationResps = null;
		String taskid = null;
		while (true) {
			boolean isSuccess = false;
			String loanRecordId = null;
			Date createTime=null;
			Calendar c = Calendar.getInstance();
			SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
            String curDate = s.format(c.getTime());  //当前日期
        	//记录当天成功的单
//    		 String succKey = redisUtil.createKey(succKeyInit,curDate);
    		//记录当天成功的单
    		 String failKey = redisUtil.createKey(failKeyInit,curDate);
    		
			try {
//				creditRuleService.autoCredit(null,null, null,null);
				// 从任务队列"task-queue"中获取一个任务，并将该任务放入暂存队列"tmp-queue"
				taskid = redisUtil.rpop(key);
				if (StringUtils.isBlank(taskid)) {
					continue;
				}
	
				Map<String, Object> map = JSON.parseObject(taskid, Map.class);
				loanRecordId = (String) map.get("loanRecordId");
				logger.info(phase + " begin handle loanRecordId:  {}" ,loanRecordId);
//				createTime=JSON.parseObject(JSON.toJSONString(map.get("createTime")),Date.class) ;
//				long time=(Calendar.getInstance().getTime().getTime()-createTime.getTime())/1000/60;
//				if (time<5l) { //进件时间在5分钟之内的单暂停5分钟后处理
//					logger.info(phase + " autocredit whthin five minutes task begin sleep loanRecordId:{},time:{}",loanRecordId,time);
//					Thread.sleep(60*1000*5);
//				}
				String violationStr = JSON.toJSONString(map.get("violationEntities"));
				
				String userStr = JSON.toJSONString(map.get("user"));
				if (StringUtils.isBlank(loanRecordId)) {
					continue;
				}
				//避免重复跑单，记录所有成功的单，不再跑
				if(redisUtil.hexists(succTotalKey, loanRecordId)){
					logger.info(phase + " 重复的订单  loanRecordId:{} ", loanRecordId);
					isSuccess = true;
					continue;
				}
				logger.info(violationStr);
				if (StringUtils.isNotEmpty(violationStr)) {
					violationResps = JSONArray.parseArray(violationStr, ViolationResp.class);
				}else{
					violationResps = new ArrayList<ViolationResp>();
				}
				
				if (StringUtils.isNotEmpty(userStr)) {
					user = JSON.parseObject(userStr, User.class);
				}
				if (null==violationResps) {
					violationResps=new ArrayList<>();
				}
				creditRuleService.autoCredit(phase,loanRecordId, user,violationResps);
				isSuccess = true;
			} catch (Exception e) {
				isSuccess = false;
				logger.error(phase + " handle fail {} = {} = {}", loanRecordId, user,violationResps, e);
//				 处理任务----纯属业务逻辑，模拟一下：睡觉
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					logger.error(e.getMessage(), e1);
				}
			} finally {
				if (StringUtils.isEmpty(loanRecordId)) { //如果没有任务
					logger.info(phase +" queue not has task begin sleep");
					// 处理任务----纯属业务逻辑，模拟一下：睡觉
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
				}else{//如果有任务
			
					if (!isSuccess) {
						// 处理失败，重新放入队列
						redisUtil.lpush(key, taskid);
						//记录当天失败的单
						redisUtil.hset(failKey, loanRecordId, taskid);
						
						logger.info(phase +" handle fail,rollback to queue loanRecordId:{} ", loanRecordId);
					} else {// 处理成功，打印日志
				
//						redisUtil.hset(succKey, loanRecordId, taskid);
						redisUtil.hset(succTotalKey, loanRecordId, taskid);
						logger.info(phase + " handle success,loanRecordId: {} ", loanRecordId);
					}
				}
				
			}

		}

	}
	public static void main(String[] args) {
		System.out.println(-234/5);
	}
}