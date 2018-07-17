/**
 * 
 */
package org.gz.risk.auditing.service;

import org.gz.common.resp.ResponseResult;

/**
 * @author pengk
 * 2017年7月14日
 */
public interface OrderAggregateService {


	
    
    /**
     * @Description: 根据订单号查询LoanUser的接口
     * @param req
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月24日
     */
	ResponseResult getLoanUserByRecordId(String loanRecord);
    
//    /**
//     * @Description: 根据订单号和userId查询List<LoanUser>
//     * @param req
//     * @return
//     * @throws
//     * createBy:zhangguoliang            
//     * createDate:2017年7月25日
//     */
//    Result findSelfLoanUserList(LoanUserReq req);

   
}
