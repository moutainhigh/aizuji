///**
// * Copyright © 2014 GZJF Corp. All rights reserved.
// * This software is proprietary to and embodies the confidential
// * technology of GZJF Corp.  Possession, use, or copying
// * of this software and media is authorized only pursuant to a
// * valid written license from GZJF Corp or an authorized sublicensor.
// */
//package org.gz.risk.auditing.service;
//
//import org.gz.risk.auditing.param.LoanRecordReq;
//import org.gz.risk.bean.Result;
//
///**
// * 订单接口
// * 
// * @author pengk 2017年7月11日
// */
//public interface LoanRecordService {
//    
//	 /**
//     * 查询订单总数
//     * @param req
//     * @return
//     * @throws
//     * createBy:zhangguoliang            
//     * createDate:2017年7月25日
//     */
//    Result countAllLoanUser(LoanRecordReq req);
//    
//    
//    /**
//     * 通过history_id，apply_time分页查询所有订单
//     * @param dto
//     * @return
//     * @throws
//     * createBy:zhangguoliang            
//     * createDate:2017年7月26日
//     */
//    Result findAllLoanUserList(LoanRecordReq req);
////    
////	    /**
////     * 通过订单编号查询订单对象
////     * 
////     * @param String loan_record_id
////     * @return LoanRecordResp loanRecordResp
////     */
////	Result queryLoanRecordByCode(LoanRecordQueryReq req);
////
////	    /**
////     * 添加订单信息
////     * 
////     * @param LoanRecordReq req
////     * @return String LoanRecordId
////     */
////	Result addLoanRecord(LoanRecordReq req);
////
////	/**
////	 * 更新订单信息
////	 * 
////	 * @param LoanRecordReq req
////	 * @return
////	 */
////	Result updateLoanRecord(LoanRecordReq req);
////
////	        /**
////     * 通过historyidList查询对应的历史用户信息用于关联loanrecord表提供信息
////     * 
////     * @param List<Long> historyidList
////     * @return List<LoanRecordResp> loanRecordResp
////     */
////	Result queryLoanRecordByHistoryIds(LoanRecordQueryReq req);
////
////	    /**
////     * 通过用户id查询对应的历史用户信息与订单表信息 消费者：第三方、贷后、用户、app_domain
////     * 
////     * @param Long userId
////     * @return List<OrderResp> orderResplist
////     */
////	Result queryUserLoanRecord(LoanRecordQueryReq req);
////
////	    /**
////     * 通过身份证号码查询对应的历史用户信息与订单表信息 消费者：信审
////     * 
////     * @param String idNum
////     * @return List<OrderResp> orderResplist
////     */
////	Result findBatchByIdCard(LoanRecordQueryReq req);
////
////	    /**
////     * 通过订单编号查询对应的历史用户信息与订单表信息 消费者：信审 、贷后、清算、用户、appdomain
////     * 
////     * @param String loanRecordId
////     * @return OrderResp orderResp
////     */
////	Result getByLoanRecordId(LoanRecordQueryReq req);
////
////	    /**
////     * 通过订单编号list字符串查询对应的历史用户信息与订单表信息 消费者：信审、贷后、清算
////     * 
////     * @param List<String> loanRecordId
////     * @return List<OrderResp> orderResplist
////     */
////	Result findBatch(LoanRecordQueryReq req);
////	
////	        /**
////     * 根据key-value键值对查询对应数据
////     * 
////     * @Description:
////     * @param req(key值可选:loan_record_id,real_name,phone_num,id_num)
////     * @return List<UserLoanRecordQueryResp> respList
////     * @throws createBy:hening createDate:2017年7月17日
////     */
////	Result findBatchByKeyValue(UserLoanRecordQueryReq req);
////
////    /**
////     * @Description: 通过userId查看是否是复借单
////     * @param req
////     * @return boolean isrepate
////     * @throws createBy:pengk createDate:2017年7月19日
////     */
////    Result isRepeatLoan(LoanRecordQueryReq req);
////
////
////
////    /**
////     * @Description: 提交订单原子服务第二步，1修改订单信息，2查询订单信息 3 查询历史用户信息 4添加loandetail表
////     * @param LoanRecord 对象
////     * @return UserHistoryResp
////     * @throws createBy:pengk createDate:2017年7月20日
////     */
////    Result submintStepTwo(LoanRecordReq req);
////
////    /**
////     * @Description: 为聚合服务GetLockTime 提供原子方法
////     * @param Long userId
////     * @return LoanRecordResp
////     * @throws createBy:pengk createDate:2017年7月21日
////     */
////    Result forGetLockTime(LoanRecordQueryReq req);
////
////
////
////    /**
////     * @Description: 查询出当前用户签约的总数
////     * @param Long userId
////     * @return int count(*)
////     * @throws createBy:pengk createDate:2017年7月22日
////     */
////    Result recordMessage(LoanRecordQueryReq req);
////    
////    /**
////     * 根据订单号查询LoanUser的接口 
////     * @param String loanRecordId
////     * @return
////     * @throws
////     * createBy:zhangguoliang            
////     * createDate:2017年7月24日
////     */
////    Result getLoanUser(LoanRecordReq req);
////    
////
////    
////    /**
////     * 根据订单号和userId查询List
////     * @param req
////     * @return
////     * @throws
////     * createBy:zhangguoliang            
////     * createDate:2017年7月25日
////     */
////    Result findSelfLoanUserList(LoanRecordReq req);
//    
//  
//}
