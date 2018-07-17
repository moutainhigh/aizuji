package org.gz.order.backend.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.constants.SmsType;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.order.backend.auth.AuthUserHelper;
import org.gz.order.backend.service.DistributeService;
import org.gz.order.backend.service.OrderCreditDetailService;
import org.gz.order.backend.service.RiskProxy;
import org.gz.order.common.Enum.AuditState;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.Enum.OrderResultCode;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.OrderCreditDetail;
import org.gz.order.server.dao.OrderCreditDetailDao;
import org.gz.order.server.dao.RentRecordDao;
import org.gz.order.server.dao.RentStateDao;
import org.gz.order.server.service.RentRecordService;
import org.gz.risk.common.request.CreditNode;
import org.gz.risk.common.request.RiskReq;
import org.gz.sms.service.SmsSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderCreditDetailServiceImpl implements OrderCreditDetailService {

    public static final Logger   logger = LoggerFactory.getLogger(OrderCreditDetailServiceImpl.class);
    @Resource
    private OrderCreditDetailDao ocdDao;

    @Resource
    private RentRecordDao        rentRecordDao;

    @Resource
    private RentStateDao         rentStateDao;

    @Autowired
    SmsSendService               smsSendService;

    @Autowired
    private RentRecordService    rentRecordService;

    @Autowired
    private RiskProxy            riskProxy;
    
    @Autowired
    DistributeService distributeService;

    @Override
    public void add(OrderCreditDetail orderCreditDetail) {
        ocdDao.add(orderCreditDetail);
    }

    @Override
    public OrderCreditDetail getById(Long id) {
        return ocdDao.getById(id);
    }

    @Override
    public void update(OrderCreditDetail orderCreditDetail) {
        ocdDao.update(orderCreditDetail);
    }

    @Override
    public Integer queryPageCount(OrderCreditDetailQvo qvo) {
        return ocdDao.queryPageCount(qvo);
    }

    @Override
    public ResultPager<OrderCreditDetail> queryList(OrderCreditDetailQvo qvo) {
        int totalNum = this.queryPageCount(qvo);
        List<OrderCreditDetail> list = new ArrayList<OrderCreditDetail>();
        if (totalNum > 0) {
            list = this.ocdDao.queryList(qvo);
            for (OrderCreditDetail ocd : list) {
                ocd.setCreateName(AuthUserHelper.getUserNameById(ocd.getCreateBy()));
                ocd.setUpdateName(AuthUserHelper.getUserNameById(ocd.getUpdateBy()));
            }
        }
        ResultPager<OrderCreditDetail> data = new ResultPager<OrderCreditDetail>(totalNum,
            qvo.getCurrPage(),
            qvo.getPageSize(),
            list);
        return data;
    }

    @Override
    public void delete(Long id) {
        ocdDao.delete(id);
    }

    @Override
    @Transactional
    public void orderAudit(OrderCreditDetail orderCreditDetail) {
        this.add(orderCreditDetail);

        // 判断订单状态
        OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(orderCreditDetail.getOrderNo());
        if (orderDetailResp.getState() != BackRentState.ApprovalPending.getCode()) {
            throw new ServiceException(OrderResultCode.FLOW_STATUS_ERROR.getCode(),
                OrderResultCode.FLOW_STATUS_ERROR.getMessage());
        }
        if (orderCreditDetail.getCreditResult().equals(AuditState.AuditPass.getCode().byteValue())) {
            // 进入订单审核通过流程
            this.AuditPassHandle(orderCreditDetail);
        } else if (orderCreditDetail.getCreditResult().equals(AuditState.AuditRefusal.getCode().byteValue())) {
            // 进入订单审核拒绝流程
            this.AuditRefusalHandle(orderCreditDetail);
        } else {
            throw new ServiceException(OrderResultCode.REQ_PARA_ERROR.getCode(), "参数错误");
        }
    }

    /**
     * 订单审核拒绝处理
     * @param orderCreditDetail
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月30日
     */
    private void AuditRefusalHandle(OrderCreditDetail orderCreditDetail) {
        UpdateOrderStateReq req = new UpdateOrderStateReq();
        req.setCreateBy(orderCreditDetail.getUpdateBy());
        req.setCreateMan(orderCreditDetail.getUpdateName());
        req.setRentRecordNo(orderCreditDetail.getOrderNo());
        req.setState(BackRentState.Refuse.getCode());
        ResponseResult<String> res = distributeService.updateOrderState(req);
        if (!res.isSuccess()) {
            logger.error("AuditRefusalHandle updateOrderState error..{}", res.getErrMsg());
            throw new ServiceException(res.getErrCode(), res.getErrMsg());
        }
    }

    /**
     * 订单审核通过处理
     * @param orderCreditDetail
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月30日
     */
    private void AuditPassHandle(OrderCreditDetail orderCreditDetail) {
        // 更新订单审核状态为三审
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rentRecordNo", orderCreditDetail.getOrderNo());
        map.put("creditState", 3);// 进入三审
        ResponseResult<String> res = rentRecordService.updateCreditState(map);
        if(!res.isSuccess()){
            logger.error("AuditPassHandle updateCreditState error..{}",res.getErrMsg());
            throw new ServiceException(res.getErrCode(), res.getErrMsg());
        }
        
        // 发送消息通知信审系统
        RiskReq req = new RiskReq();
        req.setLoanRecordId(orderCreditDetail.getOrderNo());
        req.setPhase(CreditNode.NODE_THIRD_CREDIT);
        logger.info("AuditPassHandle processAutoCredit begin.. the param:{}", JsonUtils.toJsonString(req));
        res = riskProxy.processAutoCredit(req);
        logger.info("AuditPassHandle processAutoCredit end.. the returnVal:{}", JsonUtils.toJsonString(res));
        if (!res.isSuccess()) {
            logger.error("AuditPassHandle processAutoCredit error..{}", res.getErrMsg());
            throw new ServiceException(res.getErrCode(), res.getErrMsg());
        }
    }

    @Override
    public Integer queryCountByCustomerCreditList(OrderCreditDetailQvo qvo) {
        return ocdDao.queryCountByCustomerCreditList(qvo);
    }

    @Override
    public ResultPager<OrderCreditDetail> queryCustomerCreditList(OrderCreditDetailQvo qvo) {
        int totalNum = this.queryCountByCustomerCreditList(qvo);
        List<OrderCreditDetail> list = new ArrayList<OrderCreditDetail>();
        if (totalNum > 0) {
            list = this.ocdDao.queryCustomerCreditList(qvo);
            for (OrderCreditDetail ocd : list) {
                ocd.setCreateName(AuthUserHelper.getUserNameById(ocd.getCreateBy()));
                ocd.setUpdateName(AuthUserHelper.getUserNameById(ocd.getUpdateBy()));
            }
        }
        ResultPager<OrderCreditDetail> data = new ResultPager<OrderCreditDetail>(totalNum,
            qvo.getCurrPage(),
            qvo.getPageSize(),
            list);
        return data;
    }

    private void sendMsg(String rentRecordNo, SmsType smsType, String phoneNum, List<String> datas) {
        // 调用接口发送通知短信
        // SmsDto dto = new SmsDto();
        // dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
        // dto.setPhone(phoneNum);
        // dto.setSmsType(smsType.getType());
        // dto.setTemplateId(smsType.getType());
        // dto.setDatas(datas);
        // ResponseResult<String> result =
        // smsSendService.sendSmsStockSignInform(dto);
        // if (!result.isSuccess()) {
        // logger.info(" 下发短信失败 rentRecordNo={},templateId={},ErrMsg={},ErrCode={}",
        // rentRecordNo,
        // smsType.getType(),
        // result.getErrMsg(),
        // result.getErrCode());
        // }
    }

	@Override
	public ResultPager<OrderCreditDetail> customerCreditList(String orderNo) {
		List<OrderCreditDetail> list = new ArrayList<OrderCreditDetail>();
        list = this.ocdDao.customerCreditList(orderNo);
        for (OrderCreditDetail ocd : list) {
            ocd.setCreateName(AuthUserHelper.getUserNameById(ocd.getCreateBy()));
            ocd.setUpdateName(AuthUserHelper.getUserNameById(ocd.getUpdateBy()));
        }
        ResultPager<OrderCreditDetail> data = new ResultPager<OrderCreditDetail>(list.size(),
                0,
                0,
                list);
        return data;
	}

}
