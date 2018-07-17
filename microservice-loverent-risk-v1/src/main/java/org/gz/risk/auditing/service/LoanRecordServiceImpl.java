///**
// * Copyright © 2014 GZJF Corp. All rights reserved.
// * This software is proprietary to and embodies the confidential
// * technology of GZJF Corp.  Possession, use, or copying
// * of this software and media is authorized only pursuant to a
// * valid written license from GZJF Corp or an authorized sublicensor.
// */
//package org.gz.risk.auditing.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.gz.common.utils.BeanConvertUtil;
//import org.gz.order.common.Enum.OrderResultCode;
//import org.gz.order.common.entity.UserHistory;
//import org.gz.risk.auditing.entity.LoanUser;
//import org.gz.risk.auditing.param.LoanUserReq;
//import org.gz.risk.auditing.param.LoanUserResp;
//import org.gz.risk.bean.BusinessExcepton;
//import org.gz.risk.bean.Result;
//import org.gz.risk.constant.OrderConstant;
//import org.gz.risk.dao.util.JsonUtil;
//import org.gz.risk.dao.util.LogIdGenerator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.gzjf.order.dto.LoanUserDto;
//import com.gzjf.order.service.atom.LoanUserAtomService;
//import com.gzjf.order.service.atom.UserHistoryAtomService;
//
///**
// * 订单服务实现类
// * 
// * @author pengk 2017年7月12日
// */
//@Service
//public class LoanUserServiceImpl implements LoanUserService {
//
//    private static final Logger    logger = LoggerFactory.getLogger(LoanUserServiceImpl.class);
//
//    @Autowired
//    private LoanUserAtomService  LoanUserAtomService;
//
//    @Autowired
//    private UserHistoryAtomService userHistoryAtomService;
//
//    @Override
//    public Result countAllLoanUser(LoanUserReq req) {
//
//        // 返回结果对象
//        Result result = new Result();
//        
//        try {
//            Date applyTime = req.getApplyTime();
//            if (applyTime == null) {
//                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
//                result.setMessage("applyTime不能为空");
//                return result;
//            }
//            long count = LoanUserAtomService.countAllLoanUser(applyTime);
//            result.setData(count);
//        }  catch (Exception e) {
//            logger.error("LoanUserServiceImpl.countAllLoanUser is error,",e);
//            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
//            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
//        }
//        logger.info("logSign={},LoanUserServiceImpl.countAllLoanUser,rsp={}",
//            req.getLogSign(),
//            JsonUtil.toJson(result));
//        return result;
//    }
//    
//    @Override
//    public Result findAllLoanUserList(LoanUserReq req) {
//        logger.info("logSign={},LoanUserServiceImpl.findAllLoanUserList,req={}",
//            req.getLogSign(),
//            JsonUtil.toJson(req));
//
//        // 返回结果对象
//        Result result = new Result();
//        List<LoanUserResp> loanUserResps = new ArrayList<>();
//        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
//        
//        try {
//            Date applyTime = req.getApplyTime();
//            int offset = req.getOffset();
//            int rows = req.getRows();
//            if (applyTime==null || rows==0) {
//                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
//                result.setMessage("applyTime、rows不能为空");
//                return result;
//            }
//            LoanUserDto dto = new LoanUserDto();
//            dto.setApplyTime(applyTime);
//            dto.setOffset(offset);
//            dto.setRows(rows);
//            List<LoanUser> LoanUsers = LoanUserAtomService.findAllLoanUserList(dto);
//            List<Long> historyIds = new ArrayList<>();
//            for (LoanUser LoanUser : LoanUsers) {
//                historyIds.add(LoanUser.getHistoryId());
//            }
//            //通过historyId关联查UserHistory
//            List<UserHistory> userHistories = userHistoryAtomService.queryUserHistoryByIdlist(historyIds);
//            for (UserHistory userHistory : userHistories) {
//                for (LoanUser LoanUser : LoanUsers) {
//                    if (LoanUser.getHistoryId().equals(userHistory.getId())) {
//                        LoanUserResp loanUserResp = BeanConvertUtil.convertBean(userHistory, LoanUserResp.class);
//                        loanUserResp.setLoanUserId(LoanUser.getLoanUserId());
//                        loanUserResp.setApplyTime(LoanUser.getApplyTime());
//                        loanUserResp.setApplyAmount(LoanUser.getApplyAmount());
//                        loanUserResp.setApprovalResult(LoanUser.getApprovalResult());
//                        loanUserResps.add(loanUserResp);
//                        break;
//                    }
//                }
//            }
//            result.setData(loanUserResps);
//        } catch (BusinessExcepton e) {
//            logger.error("{} LoanUserServiceImpl.findAllLoanUserList is error,errorCode={},message={}.",
//                logSign,
//                e.getCode(),
//                e.getMessage(),
//                e);
//            result.setCode(e.getCode());
//            result.setMessage(e.getMessage());
//        } catch (Exception e) {
//            logger.error("{} LoanUserServiceImpl.findAllLoanUserList is error,", logSign, e);
//            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
//            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
//        }
//        logger.info("logSign={},LoanUserServiceImpl.findAllLoanUserList,rsp={}",
//            req.getLogSign(),
//            JsonUtil.toJson(result));
//        return result;
//    }
////    
////    @Override
////    public Result queryLoanUserByCode(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.queryLoanUserByCode,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        String LoanUserId = req.getLoanUserId();
////
////        try {
////
////            if (StringUtils.isEmpty(LoanUserId)) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId不能为空");
////            } else {
////                LoanUser LoanUser = LoanUserAtomService.queryLoanUserByCode(req.getLoanUserId());
////
////                if (LoanUser != null) {
////                    LoanUserResp LoanUserResp = BeanConvertUtil.convertBean(LoanUser, LoanUserResp.class);
////                    result.setData(LoanUserResp);
////                } else {
////                    result.setCode(OrderResultCode.NOT_LOAN_RECORD.getCode());
////                    result.setMessage(OrderResultCode.NOT_LOAN_RECORD.getMessage());
////                }
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} queryLoanUserByCode is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} queryLoanUserByCode is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.queryLoanUserByCode,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result addLoanUser(LoanUserReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.addLoanUser,req={}", req.getLogSign(), JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        try {
////            String orderCode = LoanUserAtomService.addLoanUser(BeanConvertUtil.convertBean(req, LoanUserDto.class));
////            result.setData(orderCode);
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} addLoanUser is error,errorCode={},message={}.", logSign, e.getCode(), e.getMessage(), e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} addLoanUser is error,errorCode={},message={}",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.addLoanUser,rsp={}", req.getLogSign(), JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result updateLoanUser(LoanUserReq req) {
////
////        logger.info("logSign={},LoanUserServiceImpl.updateLoanUser,req={}", req.getLogSign(), JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        try {
////            LoanUserAtomService.updateLoanUser(BeanConvertUtil.convertBean(req, LoanUserDto.class));
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.updateLoanUser is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.updateLoanUser is error,",
////                logSign,
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.updateLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result queryLoanUserByHistoryIds(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.queryLoanUserByHistoryIds,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        List<Long> historyIdlist = req.getHistoryIdlist();
////        try {
////
////            if (historyIdlist.size() == 0) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("historyIdlist不能为空");
////            } else {
////                List<LoanUser> list = LoanUserAtomService.queryLoanUserByHistoryIds(historyIdlist);
////                if (list.size() > 0) {
////                    List<LoanUserResp> LoanUserResp = BeanConvertUtil.convertBeanList(list, LoanUserResp.class);
////                    result.setData(LoanUserResp);
////                }
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} queryLoanUserByHistoryIds is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} queryLoanUserByHistoryIds is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.queryLoanUserByHistoryIds,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result queryUserLoanUser(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.queryUserLoanUser,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        Long userId = req.getUserId();
////        List<Long> historyIdlist = new ArrayList<Long>();
////        try {
////
////            if (userId == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("userId不能为空");
////            } else {
////                List<UserHistory> userHistoryList = userHistoryAtomService.queryUserHistoryByUserId(userId);
////                if (userHistoryList.size() > 0) {
////                    for (UserHistory userHistory : userHistoryList) {
////                        historyIdlist.add(userHistory.getId());
////                    }
////                } else {
////                    logger.info("LoanUserServiceImpl.queryUserLoanUser ,req={}没有对应的历史订单记录" + JsonUtil.toJson(req));
////                    return result;
////                }
////
////                List<LoanUser> list = LoanUserAtomService.queryLoanUserByHistoryIds(historyIdlist);
////
////                List<OrderResp> orderResplist = new ArrayList<OrderResp>();
////                if (list.size() > 0) {
////                    OrderResp orderResp = null;
////                    for (LoanUser LoanUser : list) {
////                        orderResp = BeanConvertUtil.convertBean(LoanUser, OrderResp.class);
////                        for (UserHistory userHistory : userHistoryList) {
////                            if (userHistory.getId().equals(orderResp.getHistoryId())) {
////                                orderResplist.add(conventOrderResp(orderResp, userHistory));
////                                break;
////                            }
////                        }
////
////                    }
////                    result.setData(orderResplist);
////                }
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.queryUserLoanUser is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.queryUserLoanUser is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.queryUserLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result findBatchByIdCard(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.findBatchByIdCard,req={}", req.getLogSign(), JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        String idNum = req.getIdNum();
////        List<Long> historyIdlist = new ArrayList<Long>();
////        try {
////
////            if (StringUtils.isEmpty(idNum)) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("idNum不能为空");
////            } else {
////                List<UserHistory> userHistoryList = userHistoryAtomService.queryUserHistoryByidNum(idNum);
////                if (userHistoryList.size() > 0) {
////                    for (UserHistory userHistory : userHistoryList) {
////                        historyIdlist.add(userHistory.getId());
////                    }
////                } else {
////                    logger.info("LoanUserServiceImpl.findBatchByIdCard ,req={}没有对应的历史订单记录" + JsonUtil.toJson(req));
////                    return result;
////                }
////
////
////                List<LoanUser> list = LoanUserAtomService.queryLoanUserByHistoryIdsNotOrder(historyIdlist);
////                List<OrderResp> orderResplist = new ArrayList<OrderResp>();
////                if (list.size() > 0) {
////                    OrderResp orderResp = null;
////                    for (LoanUser LoanUser : list) {
////                        orderResp = BeanConvertUtil.convertBean(LoanUser, OrderResp.class);
////                        for (UserHistory userHistory : userHistoryList) {
////                            if (userHistory.getId().equals(orderResp.getHistoryId())) {
////                                orderResplist.add(conventOrderResp(orderResp, userHistory));
////                                break;
////                            }
////                        }
////
////                    }
////                    result.setData(orderResplist);
////                }
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.findBatchByIdCard is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.findBatchByIdCard is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.findBatchByIdCard,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result getByLoanUserId(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.getByLoanUserId,req={}", req.getLogSign(), JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        String LoanUserId = req.getLoanUserId();
////        try {
////
////            if (StringUtils.isEmpty(LoanUserId)) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId不能为空");
////            } else {
////                LoanUser LoanUser = LoanUserAtomService.queryLoanUserByCode(LoanUserId);
////                if (LoanUser != null) {
////                    Long historyId = LoanUser.getHistoryId();
////                    UserHistory userHistory = userHistoryAtomService.queryUserHistoryById(historyId);
////                    OrderResp orderResp = BeanConvertUtil.convertBean(LoanUser, OrderResp.class);
////                    if (userHistory != null) {
////                        conventOrderResp(orderResp, userHistory);
////                    }
////                    result.setData(orderResp);
////                }
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.getByLoanUserId is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.getByLoanUserId is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.getByLoanUserId,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result findBatch(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.findBatch,req={}", req.getLogSign(), JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        List<String> LoanUserIds = req.getLoanUserIds();
////        List<Long> historyIdlist = new ArrayList<Long>();
////        try {
////
////            if (LoanUserIds == null || LoanUserIds.size() == 0) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserIds不能为空");
////            } else {
////
////                QueryDto queryDto = new QueryDto();
////                LoanUserDto LoanUserDto = new LoanUserDto();
////                LoanUserDto.setLoanUserIds(LoanUserIds);
////                queryDto.setQueryConditions(LoanUserDto);
////
////                List<LoanUser> list = LoanUserAtomService.queryLoanUserByLoanUserIdlist(queryDto);
////
////                List<OrderResp> orderResplist = new ArrayList<OrderResp>();
////                if (list.size() > 0) {
////                    OrderResp orderResp = null;
////                    for (LoanUser LoanUser : list) {
////                        orderResp = BeanConvertUtil.convertBean(LoanUser, OrderResp.class);
////                        historyIdlist.add(LoanUser.getHistoryId());
////                        orderResplist.add(orderResp);
////                    }
////                    if (historyIdlist.size() > 0) {
////                        List<UserHistory> userHistoryList = userHistoryAtomService.queryUserHistoryByIdlist(historyIdlist);
////                        if (userHistoryList.size() > 0) {
////
////                            for (OrderResp orderResp1 : orderResplist) {
////                                for (UserHistory userHistory : userHistoryList) {
////                                    if (orderResp1.getHistoryId().equals(userHistory.getId())) {
////                                        conventOrderResp(orderResp1, userHistory);
////                                        break;
////                                    }
////                                }
////                            }
////                        }
////                    }
////                    result.setData(orderResplist);
////                }
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.findBatch is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.findBatch is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.findBatch,rsp={}", req.getLogSign(), JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result findBatchByKeyValue(UserLoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.findBatchByKeyValue,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        String key = req.getKey();
////        if (StringUtils.isEmpty(key)) {
////            result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////            result.setMessage(OrderResultCode.REQ_PARA_ERROR.getMessage());
////            return result;
////        }
////        boolean flag = true;
////        UserHistoryDto dto = new UserHistoryDto();
////        switch (key) {
////            case "t1.loan_record_id":
////                flag = true;
////                break;
////            case "t1.apply_time":
////                flag = true;
////                break;
////            case "t2.real_name":
////                flag = false;
////                dto.setRealName(req.getValue());
////                break;
////            case "t2.phone_num":
////                flag = false;
////                dto.setPhoneNum(req.getValue());
////                break;
////            case "t2.id_num":
////                flag = false;
////                dto.setIdNum(req.getValue());
////                break;
////            case "t2.debit_card":
////                flag = false;
////                dto.setDebitCard(req.getValue());
////                break;
////            case "t2.company_cell_num":
////                flag = false;
////                dto.setCompanyCellNum(req.getValue());
////            case "t2.company_name":
////                flag = false;
////                dto.setCompanyName(req.getValue());
////                break;
////            case "t2.spouse_tel":
////                flag = false;
////                dto.setSpouseTel(req.getValue());
////            case "t2.kinship_tel":
////                flag = false;
////                dto.setKinshipTel(req.getValue());
////            case "t2.emergency_contact_phone":
////                flag = false;
////                dto.setEmergencyContactPhone(req.getValue());
////                break;
////            default:
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage(String.format(OrderResultCode.REQ_PARA_ERROR.getMessage(), "请求参数错误"));
////                return result;
////        }
////
////        try {
////            List<UserLoanUserQueryResp> respList = new ArrayList<>();
////
////            // 先查询出所有复借过的用户(将来加入redis后需要把此map放入redi中缓存)
////            List<Long> payedUsers = LoanUserAtomService.queryisPayedlist();
////            Map<Long, String> usermap = new HashMap<Long, String>();
////            if (payedUsers.size() > 0) {
////                for (Long long1 : payedUsers) {
////                    usermap.put(long1, null);
////                }
////            }
////
////            if (flag) {// 查询编号则先LoanUser表再查userHistory
////
////                QueryDto queryDto = new QueryDto();
////                LoanUserDto LoanUserDto = new LoanUserDto();
////
////                if ("t1.loan_record_id".equals(req.getKey())) {
////                    if (null!= req.getLoanUserIds() && req.getLoanUserIds().size() <= 0) {
////                        result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                        result.setMessage(OrderResultCode.REQ_PARA_ERROR.getMessage());
////                        return result;
////                    }
////                    // 根据订单编号查询记录
////                    LoanUserDto.setLoanUserIds(req.getLoanUserIds());
////                }
////                // 根据申请时间查询记录
////                if (StringUtils.isNotBlank(req.getStartTime())) {
////                    LoanUserDto.setStartTime(req.getStartTime());
////                }
////                if (StringUtils.isNotBlank(req.getEndTime())) {
////                    LoanUserDto.setEndTime(req.getEndTime());
////                }
////                // 加上分页
////                if (req.getStart() != null && req.getPageSize() != null) {
////                    Page page = new Page();
////                    page.setStart(req.getStart());
////                    page.setPageSize(req.getPageSize());
////                    queryDto.setPage(page);
////                }
////                queryDto.setQueryConditions(LoanUserDto);
////                /* List<OrderBy> orderBylist = new ArrayList<OrderBy>();
////                 OrderBy orderBy2 = new OrderBy();
////                 orderBy2.setCloumnName("apply_time");
////                 orderBy2.setOrder("desc");
////                 orderBylist.add(orderBy2);*/
////                queryDto.setOrderBy(req.getOrderBy());
////
////                List<LoanUser> LoanUserlist = LoanUserAtomService.queryLoanUserByLoanUserIdlist(queryDto);
////
////                if (LoanUserlist == null || LoanUserlist.isEmpty()) {
////                    return result;
////                }
////                List<Long> listHistroyId = new ArrayList<>();
////                for (LoanUser LoanUser : LoanUserlist) {
////                    listHistroyId.add(LoanUser.getHistoryId());
////                }
////
////                // 根据订单信息查询相关信息
////                List<UserHistory> userHistoryList = userHistoryAtomService.queryUserHistoryByIdlist(listHistroyId);
////                // 设置返回数据
////
////                for (LoanUser LoanUser : LoanUserlist) {
////                    for (UserHistory userHistory : userHistoryList) {
////                        if (LoanUser.getHistoryId() != null && LoanUser.getHistoryId().equals(userHistory.getId())) {
////                            UserLoanUserQueryResp resp = LoanUser2UserLoanUserQueryResp(LoanUser,
////                                userHistory,
////                                usermap);
////                            respList.add(resp);
////                            break;
////                        }
////                    }
////                }
////
////                result.setData(respList);
////                if(null!=queryDto.getPage()){
////                    result.setTotalNum(queryDto.getPage().getTotalNum());
////                }else{
////                    result.setTotalNum(respList.size());
////                }
////
////            } else {// 其余先查userHistory再查LoanUser
////                if (StringUtils.isEmpty(req.getValue())) {
////                    result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                    result.setMessage(OrderResultCode.REQ_PARA_ERROR.getMessage());
////                    return result;
////                }
////
////                // 根据条件查询
////                List<UserHistory> list = userHistoryAtomService.queryList(dto);
////                if (list != null && !list.isEmpty()) {
////                    // 根据用户信息查询记录
////                    List<Long> listId = new ArrayList<>();
////                    Map<Long, UserHistory> map = new HashMap<>();
////                    list.forEach(user -> {
////                        listId.add(user.getId());
////                        map.put(user.getId(), user);
////                    });
////                    List<LoanUser> LoanUserList = LoanUserAtomService.queryLoanUserByHistoryIdsNotOrder(listId);
////                    // 设置返回数据
////                    LoanUserList.forEach(loan -> {
////                        UserHistory user = map.get(loan.getHistoryId());
////                        UserLoanUserQueryResp resp = LoanUser2UserLoanUserQueryResp(loan, user, usermap);
////                        respList.add(resp);
////                    });
////                }
////                result.setData(respList);
////                result.setTotalNum(respList.size());
////            }
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.findBatchByKeyValue is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.findBatchByKeyValue is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        return result;
////    }
////
////    /**
////     * 根据LoanUser和UserHistory组合返回数据
////     * 
////     * @Description:
////     * @param loan
////     * @param userHistory
////     * @return
////     * @throws createBy:hening createDate:2017年7月17日
////     */
////    private UserLoanUserQueryResp LoanUser2UserLoanUserQueryResp(LoanUser loan, UserHistory userHistory,
////                                                                       Map<Long, String> usermap) {
////        UserLoanUserQueryResp resp = new UserLoanUserQueryResp();
////        BeanUtils.copyProperties(loan, resp);
////        resp.setRealName(userHistory.getRealName());
////        resp.setDebitCard(userHistory.getDebitCard());
////        resp.setPhoneNum(userHistory.getPhoneNum());
////        resp.setCardIssuingBank(userHistory.getCardIssuingBank());
////        resp.setIdNum(userHistory.getIdNum());
////        resp.setNoAgree(userHistory.getNoAgree());
////        resp.setUserId(userHistory.getUserId());
////        if (!usermap.isEmpty() && usermap.containsKey(userHistory.getUserId())) {
////            resp.setRepated(1);
////        }
////
////        return resp;
////    }
////
////    @Override
////    public Result isRepeatLoan(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.isRepeatLoan,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        Long userId = req.getUserId();
////        try {
////
////            if (userId == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("userId不能为空");
////                return result;
////            } else {
////                QueryDto queryDto = new QueryDto();
////                LoanUserDto LoanUserDto = new LoanUserDto();
////                LoanUserDto.setUserId(userId);
////                queryDto.setQueryConditions(LoanUserDto);
////                List<OrderBy> orderBylist = new ArrayList<OrderBy>();
////                OrderBy orderBy2 = new OrderBy();
////                orderBy2.setCloumnName("apply_time");
////                orderBy2.setOrder("desc");
////                orderBylist.add(orderBy2);
////                queryDto.setOrderBy(orderBylist);
////                boolean isrepate = LoanUserAtomService.isRepeatLoan(queryDto);
////                result.setData(isrepate);
////            }
////
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.isRepeatLoan is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.isRepeatLoan is error,errorCode={},message={}.",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.isRepeatLoan,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result forUpdateLoanUserFromCredit(ForUpdateReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.LoanUserServiceImpl.forUpdateLoanUserFromCredit,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        try {
////
////            LoanUser LoanUser = BeanConvertUtil.convertBean(req, LoanUser.class);
////            LoanDetail loanDetail = BeanConvertUtil.convertBean(req.getLoanDetail(), LoanDetail.class);
////            boolean flag = LoanUserAtomService.forUpdateLoanUserFromCredit(loanDetail, LoanUser);
////            result.setData(flag);
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.forUpdateLoanUserFromCredit is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.forUpdateLoanUserFromCredit is error,errorCode={},message={}",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.forUpdateLoanUserFromCredit.addLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result submintStepOne(UserHistoryReq req) {
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        logger.info("logSign={},LoanUserServiceImpl.submintStepOne.forUpdateLoanUserFromCredit,req={}",
////            logSign,
////            JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////
////
////        try {
////
////            if (req.getUserId() == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("userId");
////                return result;
////            }
////            UserHistoryDto userHistoryDto = BeanConvertUtil.convertBean(req, UserHistoryDto.class);
////            Long histroyId = LoanUserAtomService.submintStepOne(userHistoryDto);
////            result.setData(histroyId);
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.submintStepOne is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.submintStepOne is error,errorCode={},message={}",
////                logSign,
////                OrderResultCode.SERVICE_EXCEPTION.getCode(),
////                OrderResultCode.SERVICE_EXCEPTION.getMessage(),
////                e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.submintStepOne.addLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result submintStepTwo(LoanUserReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.submintStepTwo.submintStepTwo,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        try {
////            if (req.getLoanUserId() == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId不能为空");
////                return result;
////            }
////            UserHistory userHistory = LoanUserAtomService.submintStepTwo(BeanConvertUtil.convertBean(req,
////                LoanUserDto.class));
////            result.setData(BeanConvertUtil.convertBean(userHistory, UserHistoryResp.class));
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.submintStepTwo is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.submintStepTwo is error", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.submintStepTwo,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result forGetLockTime(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.forGetLockTime.submintStepTwo,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        try {
////            Long userId = req.getUserId();
////            if (userId == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("userId不能为空");
////                return result;
////            }
////            List<LoanUser> LoanUserList = LoanUserAtomService.queryLoanUserByUserIDSort(userId);
////            if (LoanUserList != null && LoanUserList.size() > 0) {
////                result.setData(BeanConvertUtil.convertBean(LoanUserList.get(0), LoanUserResp.class));
////            }
////            // 业务异常处理
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.forGetLockTime is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.forGetLockTime is error", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.forGetLockTime,rsp={}", req.getLogSign(), JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result forSiginnUpdate(ForUpdateReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.forSiginnUpdate.,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        try {
////            String LoanUserId = req.getLoanUserId();
////            Integer approvalResult = req.getApprovalResult();
////            if (StringUtils.isBlank(LoanUserId) || approvalResult == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId与approvalResult不能为空");
////                return result;
////            }
////            LoanUserDto LoanUserDto = new LoanUserDto();
////            LoanUserDto.setLoanUserId(LoanUserId);
////            LoanUserDto.setApprovalResult(approvalResult);
////            LoanDetail loanDetail = BeanConvertUtil.convertBean(req.getLoanDetail(), LoanDetail.class);
////            LoanUserAtomService.forSiginnUpdate(LoanUserDto, loanDetail);
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.forSiginnUpdate is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////            // 程序异常处理
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.forSiginnUpdate is error,", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.forSiginnUpdate.addLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result forSiginnUpdateLoanUser(ForUpdateReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.forSiginnUpdateLoanUser,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        try {
////            String LoanUserId = req.getLoanUserId();
////            Integer approvalResult = req.getApprovalResult();
////            if (StringUtils.isBlank(LoanUserId) || approvalResult == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId与approvalResult不能为空");
////                return result;
////            }
////            LoanUserDto LoanUserDto = new LoanUserDto();
////            LoanUserDto.setLoanUserId(LoanUserId);
////            LoanUserDto.setApprovalResult(approvalResult);
////            LoanUserAtomService.updateLoanUser(LoanUserDto);
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.forSiginnUpdateLoanUser is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.forSiginnUpdateLoanUser is error,", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.forSiginnUpdateLoanUser.addLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result recordMessage(LoanUserQueryReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.recordMessage,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////
////        try {
////            Long userId = req.getUserId();
////            if (userId == null) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("userId不能为空");
////                return result;
////            }
////            int num = LoanUserAtomService.recordMessage(userId);
////            result.setData(num);
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.recordMessage is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.recordMessage is error,", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.recordMessage.addLoanUser,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    @Override
////    public Result getLoanUser(LoanUserReq req) {
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        logger.info("logSign={},LoanUserServiceImpl.getLoanUser,req={}",
//// logSign,
////            JsonUtil.toJson(req));
////        // 返回结果对象
////        Result result = new Result();
////
////        
////        try {
////            String LoanUserId = req.getLoanUserId();
////            if (StringUtils.isBlank(LoanUserId)) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId不能为空");
////                return result;
////            }
////            LoanUser LoanUser = LoanUserAtomService.queryLoanUserByCode(LoanUserId);
////            if (LoanUser != null) {
////                LoanUserResp loanUserResp = new LoanUserResp();
////                if (null!=LoanUser.getHistoryId()) {
////                    UserHistory userHistory = userHistoryAtomService.queryUserHistoryById(LoanUser.getHistoryId());
////                    loanUserResp = BeanConvertUtil.convertBean(userHistory, LoanUserResp.class);
////                }
////                loanUserResp.setHistoryId(LoanUser.getHistoryId());
////                loanUserResp.setLoanUserId(LoanUserId);
////                loanUserResp.setApplyTime(LoanUser.getApplyTime());
////                loanUserResp.setApplyAmount(LoanUser.getApplyAmount());
////                loanUserResp.setApprovalResult(LoanUser.getApprovalResult());
////                result.setData(loanUserResp);
////            }
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.getLoanUser is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.getLoanUser is error,", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.getLoanUser,rsp={}",
//// logSign,
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////   
////
////    @Override
////    public Result findSelfLoanUserList(LoanUserReq req) {
////        logger.info("logSign={},LoanUserServiceImpl.findSelfLoanUserList,req={}",
////            req.getLogSign(),
////            JsonUtil.toJson(req));
////
////        // 返回结果对象
////        Result result = new Result();
////        List<LoanUserResp> loanUserResps = new ArrayList<>();
////        String logSign = LogIdGenerator.generateCode(req.getLogSign(), OrderConstant.LOGSIGN_STRING);
////        
////        try {
////            Long userId = req.getUserId();
////            String LoanUserId = req.getLoanUserId();
////            if (StringUtils.isBlank(LoanUserId) || userId == 0) {
////                result.setCode(OrderResultCode.REQ_PARA_ERROR.getCode());
////                result.setMessage("LoanUserId、userId不能为空");
////                return result;
////            }
////            List<LoanUser> LoanUsers = LoanUserAtomService.queryListByUserId(userId);
////            //没有historyid!=NULL的数据，直接返回
////            if(LoanUsers==null || LoanUsers.size()==0){
////                result.setData(loanUserResps);
////                return result;
////            }
////            List<Long> historyIds = new ArrayList<>();
////            for (LoanUser LoanUser : LoanUsers) {
////                historyIds.add(LoanUser.getHistoryId());
////            }
////            List<UserHistory> userHistories = userHistoryAtomService.queryUserHistoryByIdlist(historyIds);
////            for (UserHistory userHistory : userHistories) {
////                for (LoanUser LoanUser : LoanUsers) {
////                    if (userHistory.getId().equals(LoanUser.getHistoryId())) {
////                        LoanUserResp loanUserResp = BeanConvertUtil.convertBean(userHistory, LoanUserResp.class);
////                        loanUserResp.setLoanUserId(LoanUserId);
////                        loanUserResp.setApplyTime(LoanUser.getApplyTime());
////                        loanUserResp.setApplyAmount(LoanUser.getApplyAmount());
////                        loanUserResp.setApprovalResult(LoanUser.getApprovalResult());
////                        loanUserResps.add(loanUserResp);
////                        break;
////                    }
////                }
////            }
////            result.setData(loanUserResps);
////        } catch (BusinessExcepton e) {
////            logger.error("{} LoanUserServiceImpl.findSelfLoanUserList is error,errorCode={},message={}.",
////                logSign,
////                e.getCode(),
////                e.getMessage(),
////                e);
////            result.setCode(e.getCode());
////            result.setMessage(e.getMessage());
////        } catch (Exception e) {
////            logger.error("{} LoanUserServiceImpl.findSelfLoanUserList is error,", logSign, e);
////            result.setCode(OrderResultCode.SERVICE_EXCEPTION.getCode());
////            result.setMessage(OrderResultCode.SERVICE_EXCEPTION.getMessage());
////        }
////        logger.info("logSign={},LoanUserServiceImpl.findSelfLoanUserList,rsp={}",
////            req.getLogSign(),
////            JsonUtil.toJson(result));
////        return result;
////    }
////
////    /**
////     * @Description:往order对象中set userhisy表中的的部分字段属性
////     * @param orderResp
////     * @param userHistory
////     * @return OrderResp
////     * @throws createBy:pengk createDate:2017年7月24日
////     */
////    private OrderResp conventOrderResp(OrderResp orderResp, UserHistory userHistory) {
////        orderResp.setIdNum(userHistory.getIdNum());
////        orderResp.setCardIssuingBank(userHistory.getCardIssuingBank());
////        orderResp.setNoAgree(userHistory.getNoAgree());
////        orderResp.setUserId(userHistory.getUserId());
////        orderResp.setRealName(userHistory.getRealName());
////        orderResp.setCardIssuingBank(userHistory.getCardIssuingBank());
////        orderResp.setPhoneNum(userHistory.getPhoneNum());
////        orderResp.setDebitCard(userHistory.getDebitCard());
////        return orderResp;
////    }
//
//    
//
//
//}
