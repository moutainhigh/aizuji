package org.gz.risk.auditing.rules;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.gz.cache.commom.behavior.IRedisUtil;
import org.gz.cache.commom.constant.RedisConstant;
import org.gz.risk.auditing.entity.LoanUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

/**
 * @author JarkimZhu Created on 2017/2/22.
 * @since jdk1.8
 */
@Repository
public class CreditRuleProvider {
    private static final Logger logger = LoggerFactory.getLogger("auto-credit-logger");
	private List<LoanUser> cache = new ArrayList<LoanUser>(1000000);
	@Resource
	private IRedisUtil redisUtil;
	private static final int ROWS = 100000;
	private String creditRuleCache = "CreditRuleProvider_CreditRuleCache";
	private int maxTaskTotal = 1;
	private int currentTaskTotal = 0;
	
	public List<LoanUser> findAllLoanUserList(Date start) {
		ArrayList<LoanUser> result = new ArrayList<LoanUser>();
		ArrayList<LoanUser> removeList = new ArrayList<LoanUser>();
		for (LoanUser loanUser :cache) {
			if (loanUser != null && loanUser.getApplyTime() != null && start != null){
				if(loanUser.getApplyTime().after(start)){
					result.add(loanUser);
				}
			}else{
				removeList.add(loanUser);
				logger.warn("findAllLoanUserList get object fail loanUser："+loanUser + "start:"+start+"loanUser:"+(loanUser!=null?ReflectionToStringBuilder.toString(loanUser,ToStringStyle.DEFAULT_STYLE):null));
			}
		}
		if(removeList.size() != 0){//清理空对象
			cache.removeAll(removeList);
		}
		return result;
	}
	public void addToCache(LoanUser loanUser,String loanRecordId) {
		try {
			if(loanUser !=null){
				String key = redisUtil.createKey(RedisConstant.RISK_MODULE,creditRuleCache);
				redisUtil.zadd(key, loanUser.getUserHistory().getUserId(), JSON.toJSONString(loanUser));
				cache.add(loanUser);
			}else{
				String key = redisUtil.createKey(RedisConstant.RISK_MODULE, "FailLoanRecordId");
				redisUtil.listAdd(key, loanRecordId);
				logger.warn("addToCache 增加空对象进入缓存addToCache:"+loanRecordId);
			}
		} catch (Exception e) {
			logger.error("CreditRuleProvider addToCache " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}

	public void addToCache(LoanUser loanUser) {
		addToCache(loanUser,null);
	}

	public void addBatchToCache(List<LoanUser> loanUserList) {
	
		try {
			long startTime = System.currentTimeMillis();
			String key = redisUtil.createKey(RedisConstant.RISK_MODULE,creditRuleCache);

			logger.info("CreditRuleProvider addBatchToCache begin" + startTime);

			Map<String, Double> scoreMembers = new HashMap<String, Double>();
			for (LoanUser loanUser : loanUserList) {
				scoreMembers.put(JSON.toJSONString(loanUser),loanUser.getUserHistory().getUserId().doubleValue());
			}
			redisUtil.zadd(key, scoreMembers);
			cache.addAll(loanUserList);
			logger.info("CreditRuleProvider addBatchToCache end 耗时" + (System.currentTimeMillis() - startTime) + "ms");
		} catch (Exception e) {
			logger.error("CreditRuleProvider addBatchToCache " + e.getMessage());
		}
	}

	public void clearCache() {
		cache.clear();
	}

	public List<LoanUser> findAllLoanUserList() {
		return cache;
	}

	public void initDataFromRedis() {
		List<LoanUser> result = null;
		long startTime = System.currentTimeMillis();
		try {
			String key = redisUtil.createKey(RedisConstant.RISK_MODULE,creditRuleCache);
			Long total = redisUtil.zcard(key);
			int batch = (int) Math.ceil((double)total / ROWS);
			 for (int i = 1; i <= batch; i++) {
				result = new ArrayList<LoanUser>();
				int begin = (i-1)*ROWS;
				int end = i*ROWS-1;
				
				Set<String> set = redisUtil.zrange(key, begin, end);
				logger.info("CreditRuleProvider initDataFromRedis 数据总数" + set.size() + "begin"  +begin + "end"  +end);
				for (String string : set) {
					try {
						result.add(JSON.parseObject(string, LoanUser.class));
					} catch (Exception e) {
						logger.error("CreditRuleProvider initDataFromRedis error " + string + e.getMessage());
					}

				}
				
				cache.addAll(result);
				logger.info("CreditRuleProvider initDataFromRedis 数据总数" + set.size() + "end 耗时"
						+ (System.currentTimeMillis() - startTime) + "ms");
				
			}
		
		
		} catch (Exception e) {
			logger.error("CreditRuleProvider findAllLoanUserList" + e.getMessage());
		}

	}

	

	public synchronized boolean isFull() throws Exception{
		boolean isFull = false;
		try {
			
			if(currentTaskTotal >= maxTaskTotal){
				isFull = true;
			}
		} catch (Exception e) {
			logger.error("CreditRuleProvider isFull fail:" + e.getMessage());
			throw e;
		}

		return isFull;
	}

	public void incrQueueSize() {
		currentTaskTotal++;
	 }

	public void decrQueueSize() {
		currentTaskTotal--;
	}

	public boolean exists() {
		boolean check = false;
		try {
			String key = redisUtil.createKey(RedisConstant.RISK_MODULE, creditRuleCache);
			check = redisUtil.exists(key);
		} catch (Exception e) {
			logger.error("CreditRuleProvider exists " + e.getMessage());
		}
		return check;
	}

	
}
