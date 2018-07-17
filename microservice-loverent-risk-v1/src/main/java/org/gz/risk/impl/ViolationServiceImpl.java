package org.gz.risk.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.bean.BusinessExcepton;
import org.gz.risk.bean.Result;
import org.gz.risk.bean.ViolationReq;
import org.gz.risk.bean.ViolationResp;
import org.gz.risk.constant.CombiningResultCode;
import org.gz.risk.dao.util.JsonUtil;
import org.gz.risk.entity.Violation;
import org.gz.risk.intf.AFuAtomService;
import org.gz.risk.intf.BaiRongAtomService;
import org.gz.risk.intf.ViolationAtomService;
import org.gz.risk.intf.ViolationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:第三方服务违规数据接口实现类
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月15日 	Created
 */
@Service
public class ViolationServiceImpl implements ViolationService {

    private static final Logger logger = LoggerFactory.getLogger(ViolationServiceImpl.class);
    
    @Autowired
    private AFuAtomService aFuAtomService;
    
    @Autowired
    private BaiRongAtomService baiRongService;
    
    @Autowired
    private ViolationAtomService violationAtomService;
    

	@Override
    public Result getViolationListByUser(User req) throws UnsupportedEncodingException {
        logger.info("ViolationService.getViolationListByUser req = {}",JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        //用户查询对象
        Violation dto = new Violation();
        long userId = req.getUserHistory().getId();
        dto.setUserId(userId);
        List<Violation> list = new ArrayList<>();
        try{
            //保存本次违规数据并返回所有该userid的违规数据
            list.addAll(aFuAtomService.addGetQueriedHistory(req.getUserHistory().getRealName(), req.getUserHistory().getIdNo(), userId));
            list.addAll(aFuAtomService.addQueryBlackList(userId, req.getUserHistory().getRealName(), req.getUserHistory().getIdNo(), req.getUserHistory().getPhoneNum()));
            list.addAll(aFuAtomService.addQueryCreditScore(req.getUserHistory().getRealName(), req.getUserHistory().getIdNo(), userId));
            list.addAll(aFuAtomService.addQueryLoan(userId, req.getUserHistory().getRealName(), req.getUserHistory().getIdNo()));
          
            if(StringUtils.isBlank(req.getUserHistory().getRealName()) ){//如果用户名称没有，不调用百荣接口
                logger.error("getViolationListByUser is error,name={}LoanRecordId={}.",req.getUserHistory().getRealName(),req.getLoanRecordId());
            }else{
            	  list.addAll(baiRongService.addViolation(userId, req.getUserHistory().getIdNo(), req.getUserHistory().getPhoneNum(), req.getUserHistory().getRealName()
            	          , req.getUserHistory().getCompanyContactNumber(), null,null));
            }
            violationAtomService.addBatch(list);
            if (userId > 0) {
                List<ViolationResp> listResp = BeanConvertUtil.convertBeanList(violationAtomService.queryList(dto), ViolationResp.class);
                result.setData(listResp);
            } else {
                result.setData(Collections.emptyList());
            }
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("getViolationListByUser is error,errorCode={},message={}.",e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("getViolationListByUser is error,errorCode={},message={}.",CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }
    
    @Override
    public Result queryListByUserId(ViolationReq req) {
        logger.info("{} ViolationService.queryListByUserId req = {}",req.getLogSign(),JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        long userId = req.getUserId();
        //取出业务日志标识
        String logSign = req.getLogSign();
        try{
            List<Violation> list = violationAtomService.queryListByUserId(userId);
            if(null == list)
            {
                result.setData(null);
            }else
            {
                result.setData(list);
            }
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} queryListByUserId is error,errorCode={},message={}.",logSign,e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} queryListByUserId is error,errorCode={},message={}.",logSign,CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }

    @Override
    public Result selectByMeal(ViolationReq req) {
        logger.info("{} ViolationService.selectByMeal req = {}",req.getLogSign(),JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        //取出业务日志标识
        String logSign = req.getLogSign();
        try{
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", req.getUserId());
            map.put("key", req.getViolationKey());
            List<Violation> list = violationAtomService.selectByMeal(map);
            if(null == list)
            {
                result.setData(null);
            }else
            {
                result.setData(list);
            }
            result.setData(BeanConvertUtil.convertBeanList(list, ViolationResp.class));
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} selectByMeal is error,errorCode={},message={}.",logSign,e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} selectByMeal is error,errorCode={},message={}.",logSign,CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }
    
    @Override
    public Result selectVio(ViolationReq req) {
        logger.info("{} ViolationService.selectVio req = {}",req.getLogSign(),JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        String logSign = req.getLogSign();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", req.getUserId());
        map.put("key", req.getViolationKey());
        try{
            Violation violation = violationAtomService.selectByVio(map);
            if(null == violation)
            {
                result.setData(null);
            }else
            {
                result.setData(BeanConvertUtil.convertBean(violation, ViolationResp.class));
            }
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} selectVio is error,errorCode={},message={}.",logSign,e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} selectVio is error,errorCode={},message={}.",logSign,CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }

    @Override
    public Result add(ViolationReq req) {
        logger.info("{} ViolationService.add req = {}",req.getLogSign(),JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        String logSign = req.getLogSign();
        try{
            violationAtomService.add(BeanConvertUtil.convertBean(req, Violation.class));
            result.setData(CombiningResultCode.DEAL_SUCCESS.getMessage());
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} add is error,errorCode={},message={}.",logSign,e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} add is error,errorCode={},message={}.",logSign,CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }
    
    @Transactional
    public Result addAll(User req) throws IOException {
        logger.info(" ViolationService.addAll req = {}",JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        long userhistoryId = req.getUserHistory().getId();
        try{
            List<Violation> list = new ArrayList<>();
            list.addAll(aFuAtomService.addGetQueriedHistory(req.getUserHistory().getRealName(), req.getUserHistory().getIdNo(), userhistoryId));
            list.addAll(aFuAtomService.addQueryBlackList(userhistoryId, req.getUserHistory().getRealName(), req.getUserHistory().getIdNo(), req.getUserHistory().getPhoneNum()));
            list.addAll(aFuAtomService.addQueryCreditScore(req.getUserHistory().getRealName(), req.getUserHistory().getIdNo(), userhistoryId));
            list.addAll(aFuAtomService.addQueryLoan(userhistoryId, req.getUserHistory().getRealName(), req.getUserHistory().getIdNo()));
            if(StringUtils.isBlank(req.getUserHistory().getRealName()) ){//如果用户名称没有，不调用百荣接口
                logger.error("getViolationListByUser is error,name={}LoanRecordId={}.",req.getUserHistory().getRealName(),req.getLoanRecordId());
            }else{
            	  list.addAll(baiRongService.addViolation(userhistoryId, req.getUserHistory().getIdNo(), req.getUserHistory().getPhoneNum(), req.getUserHistory().getRealName()
            	          , req.getUserHistory().getCompanyContactNumber(), null,null));
            }
          
            violationAtomService.addBatch(list);
            result.setData(CombiningResultCode.DEAL_SUCCESS.getMessage());
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} addAll is error,errorCode={},message={}.",e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} addAll is error,errorCode={},message={}.",CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }

    @Override
    public Result update(ViolationReq req) {
        logger.info("{} ViolationService.update req = {}",req.getLogSign(),JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        String logSign = req.getLogSign();
        try{
            violationAtomService.update(req.getUserId(), req.getViolationKey(), req.getViolationValue());
            result.setData(CombiningResultCode.DEAL_SUCCESS.getMessage());
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} update is error,errorCode={},message={}.",logSign,e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} update is error,errorCode={},message={}.",logSign,CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }

    @Override
    public Result selectByMealNum(ViolationReq req) {
        logger.info("{} ViolationService.selectByMealNum req = {}",req.getLogSign(),JsonUtil.toJson(req));
        //返回结果对象
        Result result = new Result();
        String logSign = req.getLogSign();
        try{
            Integer keyNum = violationAtomService.selectByMealNum(req.getUserId(), req.getViolationKey());
            result.setData(keyNum);
            result.setCode(CombiningResultCode.DEAL_SUCCESS.getCode());
            result.setMessage(CombiningResultCode.DEAL_SUCCESS.getMessage());
            //业务异常处理
        } catch (BusinessExcepton e) {
            logger.error("{} selectByMealNum is error,errorCode={},message={}.",logSign,e.getCode(),e.getMessage());
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            //程序异常处理
        } catch (Exception e){
            e.printStackTrace();
            logger.error("{} selectByMealNum is error,errorCode={},message={}.",logSign,CombiningResultCode.SERVICE_EXCEPTION.getCode(),CombiningResultCode.SERVICE_EXCEPTION.getMessage(),e);
            result.setCode(CombiningResultCode.SERVICE_EXCEPTION.getCode());
            result.setMessage(CombiningResultCode.SERVICE_EXCEPTION.getMessage());
        }
        //返回结果集
        return result;
    }
    
}
