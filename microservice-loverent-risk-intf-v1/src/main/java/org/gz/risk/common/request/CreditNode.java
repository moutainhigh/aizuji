package org.gz.risk.common.request;

/**
 * @author JarkimZhu
 *         Created on 2016/12/2.
 * @since jdk1.8
 */
public interface CreditNode {
    /**
     * 审批
     */
    int APPROVAL = 1;
    /**
     * 签约
     */
    int SIGN = 2;
    /**
     * 放款
     */
    int PAY = 3;
    /**
     * 拒绝
     */
    int REFUSE = 4;
    /**
     * 取消签约
     */
    int CANCEL_SIGN = 5;

    /**
     * 还款
     */
    int REFUND = 6;

    String NODE_FIRST_CREDIT = "first_credit";
    String NODE_SEC_CREDIT = "sec_credit";
    String NODE_SEC_CREDIT_1 = "sec_credit_1";
    String NODE_THIRD_CREDIT = "third_credit";
    String NODE_LOAN_SIGN = "loan_sign";
    String NODE_LOAN_APPLY = "loan_apply";
    String NODE_LOAN_LEND = "loan_lend";
}
