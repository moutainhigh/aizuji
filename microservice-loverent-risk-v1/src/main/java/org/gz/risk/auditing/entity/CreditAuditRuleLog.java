package org.gz.risk.auditing.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author phd
 * @date 2017年11月9日
 * @version 1.0
 */
public class CreditAuditRuleLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  /**
	   * 日志记录时间
	   */
	  private Long logTime;
	
	  /**
	   * 规则编码
	   */
	  private String ruleCode;
	  /**
	   * 规则名
	   */
	  private String ruleName;
	  /**
	   * 用户id
	   */
	  private String userId;
	  /**
	   * 订单号
	   */
	  private String orderNo;
	
	  /**
	   * 入参
	   */
	  private HashMap<String, Object> params;
	
	  /**
	   * 结果代码
	   */
	  private String resultCode;

	public Long getLogTime() {
		return logTime;
	}

	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	  
	
}
