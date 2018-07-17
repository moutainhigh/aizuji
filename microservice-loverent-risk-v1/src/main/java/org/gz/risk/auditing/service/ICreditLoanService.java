package org.gz.risk.auditing.service;

import java.util.List;

import org.gz.risk.auditing.entity.Admin;
import org.gz.risk.auditing.entity.Credit;

/**
 * @author JarkimZhu
 *         Created on 2016/11/22.
 * @since jdk1.8
 */
public interface ICreditLoanService {
    /**
     * 向信审后台提交贷款申请记录
     * @param historyUserId 用户ID
     * @param loanRecordId 贷款申请id
     * @return 流程ID
     */
    String applyLoan(String loanRecordId, long historyUserId);

    /**
     * 签约
     * @param historyUserId 用户id
     * @param loanRecordId 贷款申请id
     */
    void sign(String loanRecordId, long historyUserId);

    /**
     * 取消签约
     * @param historyUserId 用户ID
     * @param loanRecordId 贷款申请id
     * @param remark 备注
     */
    void cancelSign(String loanRecordId, long historyUserId, String remark);
    
	public void processFlow(Credit credit, Admin admin) ;
    public List<Credit> findCreditDetails(String processInstanceId) ;
    public Credit getLastCredit(String processInstanceId) ;

}
