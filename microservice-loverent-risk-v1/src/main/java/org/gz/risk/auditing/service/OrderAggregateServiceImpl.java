///**
// * 
// */
//package org.gz.risk.auditing.service;
//
//import org.gz.common.resp.ResponseResult;
//import org.gz.risk.auditing.service.outside.IRentRecordService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
///**
// * @author pengk 2017年7月14日
// */
//public class OrderAggregateServiceImpl implements OrderAggregateService {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderAggregateServiceImpl.class);
//    @Autowired
//    private IRentRecordService    rentRecordService;
//    
//    @Autowired
//    private LoanRecordService loanRecordService;
//    @Override
//    public ResponseResult getLoanUserByRecordId(String loanRecordId) {
//        return rentRecordService.queryBackOrderDetail(loanRecordId);
//    }
//    
////    @SuppressWarnings("unchecked")
////    @Override
////    public Result findSelfLoanUserList(LoanUserReq req) {
////        logger.info("logSign={},OrderAggregateServiceImpl.findSelfLoanUserList,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        List<LoanUserResp> loanUserList = new ArrayList<>();
////        result.setData(false);
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        try {
////            Long userId = req.getUserId();
////            LoanRecordReq req2 = new LoanRecordReq();
////            req2.setLoanRecordId(req.getLoanRecordId());
////            req2.setUserId(userId);
////            Result queryResult = loanRecordService.findSelfLoanUserList(req2);
////            //服务调用结果判定，错误抛出异常
////            if (queryResult.getCode() != 0) {
////                return queryResult;
////            }
////            List<LoanUserResp> loanUserResps = (List<LoanUserResp>) queryResult.getData();
////            TokenReq tokenReq = new TokenReq();
////            tokenReq.setUserId(userId);
////            queryResult = tokenService.getById(tokenReq);
////            //服务调用结果判定，错误抛出异常
////            if (queryResult.getCode() != 0) {
////                return queryResult;
////            }
////            TokenResp tokenResp = BeanConvertUtil.convertBean(queryResult.getData(), TokenResp.class);
////            //遍历数据集合，给每个对象ip字段赋值
////            for (LoanUserResp loanUserResp : loanUserResps) {
////                loanUserResp.setIp(tokenResp.getIp());
////                loanUserList.add(loanUserResp);
////            }
////            result.setData(loanUserList);
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} OrderAggregateServiceImpl.findSelfLoanUserList is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////        } catch (Exception e) {
////            logger.error("{} OrderAggregateServiceImpl.findSelfLoanUserList is error.", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},OrderAggregateServiceImpl.findSelfLoanUserList,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
//    
//}
