package org.gz.aliOrder.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.gz.aliOrder.Enum.BackRentState;
import org.gz.aliOrder.Enum.OrderResultCode;
import org.gz.aliOrder.commons.UpdateDto;
import org.gz.aliOrder.dao.RentLogisticsDao;
import org.gz.aliOrder.dao.RentRecordDao;
import org.gz.aliOrder.dao.RentRecordExtendsDao;
import org.gz.aliOrder.dao.RentStateDao;
import org.gz.aliOrder.dto.AddOrderReq;
import org.gz.aliOrder.dto.AddOrderResp;
import org.gz.aliOrder.dto.LogisticsNodes;
import org.gz.aliOrder.dto.OrderDetailReq;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.OrderDetailRespForWare;
import org.gz.aliOrder.dto.RentLogisticsDto;
import org.gz.aliOrder.dto.RentRecordQuery;
import org.gz.aliOrder.dto.ResultCode;
import org.gz.aliOrder.dto.SubmitOrderReq;
import org.gz.aliOrder.dto.UpdateCreditAmountReq;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.dto.ZhimaOrderCreditPayReq;
import org.gz.aliOrder.entity.RentLogistics;
import org.gz.aliOrder.entity.RentRecord;
import org.gz.aliOrder.entity.RentRecordExtends;
import org.gz.aliOrder.entity.RentState;
import org.gz.aliOrder.service.AliRentRecordService;
import org.gz.aliOrder.service.outside.IProductService;
import org.gz.aliOrder.service.outside.IShunFengService;
import org.gz.aliOrder.service.outside.IliquidationService;
import org.gz.aliOrder.utils.StateConvert;
import org.gz.aliOrder.utils.UnqieCodeGenerator;
import org.gz.common.constants.SmsType;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.service.SmsSendService;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.UndoPickReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxl.job.core.log.XxlJobLogger;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AliRecordServiceImpl implements AliRentRecordService {

  @Resource
  private RentRecordDao rentRecordDao;
  @Resource
  private RentRecordExtendsDao rentRecordExtendsDao;
  @Resource
  private RentStateDao rentStateDao;
  @Resource
  private RentLogisticsDao rentLogisticsDao;
  @Resource
  private IProductService iProductService;
  @Autowired
  private IShunFengService shunFengService;
  @Autowired
  private IliquidationService iliquidationService;
  @Autowired
  private SmsSendService smsSendService;

  
  @Transactional
  @Override
  public ResponseResult<AddOrderResp> add(AddOrderReq addOrderRequest) {
    // 1.先通过用户id查询订单表是否有状态为1待审批的订单，如果有拒绝提交
    OrderDetailReq orderDetailReq = new OrderDetailReq();
    orderDetailReq.setZmUserId(addOrderRequest.getZmUserId());
    List<Integer>     backState = new ArrayList<>();
    backState.add(BackRentState.WaitPayment.getCode());
    backState.add(BackRentState.WaitPick.getCode());
    backState.add(BackRentState.WaitSend.getCode());
    backState.add(BackRentState.SendOut.getCode());
    backState.add(BackRentState.NormalPerformance.getCode());
    orderDetailReq.setBackState(backState);
    int countRentOrders = rentRecordDao.countRentOrders(orderDetailReq);
    if(countRentOrders>0){
      return ResponseResult.build( OrderResultCode.RENT_APPROVAL_PENDING.getCode(),  OrderResultCode.RENT_APPROVAL_PENDING.getMessage(), null);
    }
    // 2.通过产品编号去远程调用查询产品信息用于添加订单扩展表信息
    ProductInfo pInforeq = new ProductInfo();
    pInforeq.setProductNo(addOrderRequest.getProductNo());
    ResponseResult<ProductInfo> byIdOrPdtNo = iProductService.getByIdOrPdtNo(pInforeq);
    if (byIdOrPdtNo.getErrCode() != 0) {
      return ResponseResult.build(byIdOrPdtNo.getErrCode(), byIdOrPdtNo.getErrMsg(), null);
    }
    // 判断远程获取的产品信息是否已经下架，如果下架返回通知前端产品已经下架不可下单
    if (byIdOrPdtNo.getData() == null) {
      return ResponseResult.build(OrderResultCode.NOT_PRODUCT.getCode(),
        OrderResultCode.NOT_PRODUCT.getMessage(),
        null);
    }
    ProductInfo pInfo = (ProductInfo) byIdOrPdtNo.getData();
    if (pInfo.getIsDeleted() == 1) {
      return ResponseResult.build(OrderResultCode.PRODUCT_DELETE.getCode(),
        OrderResultCode.PRODUCT_DELETE.getMessage(),
        null);
    }
    RentRecordExtends recordExtends = convetProductInfo(pInfo);

    RentRecord rentRecord = new RentRecord();
    // 3.添加订单表
    rentRecord.setApplyTime(new Date());
    rentRecord.setCreateOn(new Date());
    rentRecord.setUserId(addOrderRequest.getUserId());
    rentRecord.setZmUserId(addOrderRequest.getZmUserId());
    rentRecord.setProductType(pInfo.getProductType());
    // 3.1 获取订单主键id
    rentRecordDao.add(rentRecord);
    Long id = rentRecord.getId();
    String rentRecordNo = UnqieCodeGenerator.getCode("SA", id);
    String transactionId = UnqieCodeGenerator.getTransctionId(id);

    // 3.2.添加扩展表 取到扩展表自增主键ID
    recordExtends.setRentRecordNo(rentRecordNo);
    recordExtends.setUserId(addOrderRequest.getUserId());
    rentRecordExtendsDao.add(recordExtends);
    Long extId = recordExtends.getId();
    // 4.将订单编号,扩展表id 更新到订单表
    RentRecord uprentRecord = new RentRecord();
    uprentRecord.setExtendId(extId);
    uprentRecord.setRentRecordNo(rentRecordNo);
    uprentRecord.setTransactionId(transactionId);

    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(uprentRecord);

    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setId(id);
    updateDto.setUpdateWhere(whereCloumn);

    if (rentRecordDao.update(updateDto) > 0) {
      // 5.返回订单编号
      AddOrderResp addOrderResp = new AddOrderResp();
      addOrderResp.setRentRecordNo(rentRecordNo);
      addOrderResp.setTransactionId(transactionId);
      // 设置逾期时间
      Calendar cld = Calendar.getInstance();
      cld.setTime(new Date());
      cld.add(Calendar.MONTH, 1);
      Date d2 = cld.getTime();
      Date lastMillion = DateUtils.getLastMillion(DateUtils.addDate(d2, 1));
      addOrderResp.setOverdueTime(DateUtils.getString(lastMillion));
      return ResponseResult.buildSuccessResponse(addOrderResp);
    }
    return ResponseResult.build(1000, "数据库异常", null);
  }

  private RentRecordExtends convetProductInfo(ProductInfo productInfo) {
    RentRecordExtends recordExtends = new RentRecordExtends();
    recordExtends.setProductId(productInfo.getId().intValue());
    recordExtends.setProductNo(productInfo.getProductNo());
    recordExtends.setMaterielClassId(productInfo.getMaterielClassId());
    recordExtends.setMaterielClassName(productInfo.getClassName());
    recordExtends.setMaterielBrandId(productInfo.getMaterielBrandId());
    recordExtends.setMaterielBrandName(productInfo.getBrandName());
    recordExtends.setMaterielModelId(productInfo.getMaterielModelId());
    recordExtends.setMaterielModelName(productInfo.getModelName());
    recordExtends.setMaterielSpecName(productInfo.getSpecBatchNoValues());
    recordExtends.setLeaseTerm(productInfo.getTermValue());
    recordExtends.setLeaseAmount(productInfo.getLeaseAmount());
    recordExtends.setPremium(productInfo.getPremium());
    recordExtends.setFloatAmount(productInfo.getFloatAmount());
    recordExtends.setSignContractAmount(productInfo.getSignContractAmount());
    recordExtends.setSesameCredit(productInfo.getSesameCredit());
    recordExtends.setMaterielNo(productInfo.getMaterielId().toString());
    recordExtends.setMatreielName(productInfo.getMaterielName());
    recordExtends.setThumbnail(productInfo.getThumbnailUrl());
    recordExtends.setShowAmount(productInfo.getShowAmount());
    recordExtends.setMaterielNewConfigId(productInfo.getMaterielNewConfigId());
    recordExtends.setConfigValue(productInfo.getConfigValue());
    recordExtends.setBrokenScreenAmount(productInfo.getBrokenScreenAmount());
    return recordExtends;

  }

  @Transactional
  @Override
  public ResponseResult<String> updateCreditAmount(UpdateCreditAmountReq updateCreditAmountReq) {
    // 更新订单表
    RentRecord uprentRecord = new RentRecord();
    uprentRecord.setZmOrderNo(updateCreditAmountReq.getZmOrderNo());
    uprentRecord.setRentRecordNo(updateCreditAmountReq.getRentRecordNo());
    updateOrder(uprentRecord);
    // 更新扩展表
    RentRecordExtends uprecordExtends = new RentRecordExtends();
    uprecordExtends.setFundType(updateCreditAmountReq.getFundType());
    uprecordExtends.setCreditAmount(updateCreditAmountReq.getCreditAmount());
    uprecordExtends.setRentRecordNo(updateCreditAmountReq.getRentRecordNo());
    updateOrderExtends(uprecordExtends);
    return ResponseResult.buildSuccessResponse();
  }

  /**
   * 通过订单编号更新rentRecord表
   * 
   * @param rentRecord
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  private int updateOrder(RentRecord rentRecord) {
    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(rentRecord);
    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setRentRecordNo(rentRecord.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);
    int i = rentRecordDao.update(updateDto);
    return i;
  }

  /**
   * 通过订单编号更新RentRecordExtends表
   * 
   * @param rentRecordExtends
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  private int updateOrderExtends(RentRecordExtends rentRecordExtends) {
    // 更新扩展表
    UpdateDto<RentRecordExtends> updateExtendsDto = new UpdateDto<RentRecordExtends>();
    updateExtendsDto.setUpdateCloumn(rentRecordExtends);
    RentRecordExtends whereExtendsCloumn = new RentRecordExtends();
    whereExtendsCloumn.setRentRecordNo(rentRecordExtends.getRentRecordNo());
    updateExtendsDto.setUpdateWhere(whereExtendsCloumn);
    int i = rentRecordExtendsDao.update(updateExtendsDto);
    return i;
  }

  @Transactional
  @Override
  public ResponseResult<String> submitOrder(SubmitOrderReq submitOrderReq) {
    String rentRecordNo = submitOrderReq.getRentRecordNo();
    Integer i = rentRecordDao.getStateByRentRecordNo(rentRecordNo);
    if (i == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (BackRentState.ApplyPending.getCode() != i) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }

    // 1.更新订单表状态
    RentRecord rentRecord = new RentRecord();
    rentRecord.setZmOrderNo(submitOrderReq.getZmOrderNo());
    rentRecord.setRentRecordNo(rentRecordNo);
    rentRecord.setState(BackRentState.WaitPayment.getCode());
    updateOrder(rentRecord);
    // 2.更新订单扩展表信息
    RentRecordExtends rentRecordExtends = BeanConvertUtil.convertBean(submitOrderReq, RentRecordExtends.class);
    updateOrderExtends(rentRecordExtends);
    // 添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(0L);
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(BackRentState.WaitPayment.getCode());
    rentState.setCreateMan(submitOrderReq.getRealName());
    rentStateDao.add(rentState);
    return ResponseResult.buildSuccessResponse();
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public ResponseResult<String> cancleOrder(UpdateOrderStateReq req) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    
    if(req.getCallType()==1){ //0表示小程序取消订单成功异步通知订单。 1表示订单后台主动发起取消订单操作，
      //主动通知小程序去掉订单
          ZhimaOrderCreditPayReq zhimaOrderCreditPayReq = new ZhimaOrderCreditPayReq();
          zhimaOrderCreditPayReq.setOrderOperateType("CANCEL");
          zhimaOrderCreditPayReq.setOrderSN(orderDetailResp.getRentRecordNo());
          zhimaOrderCreditPayReq.setPayAmount(new BigDecimal(0));
          zhimaOrderCreditPayReq.setZmOrderNo(orderDetailResp.getZmOrderNo());
          zhimaOrderCreditPayReq.setOutTransNo(UUID.randomUUID().toString());
          zhimaOrderCreditPayReq.setPhone(orderDetailResp.getPhoneNum());
          zhimaOrderCreditPayReq.setRealName(orderDetailResp.getRealName());
          zhimaOrderCreditPayReq.setRemark("");
          zhimaOrderCreditPayReq.setUserId(orderDetailResp.getZmUserId());
          ResponseResult<?> zhimaOrderCreditPay = shunFengService.zhimaOrderCreditPay(zhimaOrderCreditPayReq);
      log.info(JsonUtils.toJsonString(zhimaOrderCreditPay));
          if(zhimaOrderCreditPay.getErrCode()!=0){
           log.error("cancleOrder zhimaOrderCreditPay,RentRecordNo()={}, response:{}",orderDetailResp.getRentRecordNo(),JsonUtils.toJsonString(zhimaOrderCreditPay));
           return ResponseResult.build(zhimaOrderCreditPay.getErrCode(), zhimaOrderCreditPay.getErrMsg(), null);
         }else{
           HashMap<String, Object> map=   (HashMap<String, Object>) zhimaOrderCreditPay.getData();
           if(!map.get("code").equals("10000")){
             log.error("cancleOrder zhimaOrderCreditPay,RentRecordNo()={}, response:{}",orderDetailResp.getRentRecordNo(),JsonUtils.toJsonString(zhimaOrderCreditPay));
             return ResponseResult.build(1000, map.get("subMsg").toString(), null);
           }
         }
    }
    
    
    // 1.更新订单表状态
    RentRecord rentRecord = new RentRecord();
    rentRecord.setRentRecordNo(req.getRentRecordNo());
    rentRecord.setState(BackRentState.Cancel.getCode());
    updateOrder(rentRecord);
    // 添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(null==req.getCreateBy()?0l:req.getCreateBy());
    rentState.setRentRecordNo(req.getRentRecordNo());
    rentState.setState(BackRentState.Cancel.getCode());
    rentState.setCreateMan(StringUtils.isEmpty(req.getCreateMan())?orderDetailResp.getRealName():req.getCreateMan());
    rentState.setRemark(req.getRemark());
    rentStateDao.add(rentState);
    
   //如果是待发货状态的取消订单 还得通知库存取消拣货
    if(orderDetailResp.getState() == BackRentState.WaitSend.getCode()){
      UndoPickReq q = new UndoPickReq();
      q.setMaterielBasicId(Long.parseLong(orderDetailResp.getMaterielNo()));
      q.setOrderSource(2);
      q.setProductId(orderDetailResp.getProductId().longValue());
      q.setSourceOrderNo(orderDetailResp.getRentRecordNo());
      ResponseResult<UndoPickReq> undoPick = iProductService.undoPick(q);
      if(undoPick.getErrCode()!= 0){
        log.error("cancleOrder undoPick,RentRecordNo()={}, response:{}",orderDetailResp.getRentRecordNo(),JsonUtils.toJsonString(undoPick));
      }
    }
    
    return ResponseResult.buildSuccessResponse();
  }

  @Transactional
  @Override
  public ResponseResult<String> updateOrderState(UpdateOrderStateReq req) {
    String rentRecordNo = req.getRentRecordNo();
    int state = req.getState();
    Integer i = rentRecordDao.getStateByRentRecordNo(rentRecordNo);
    if (i == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkAliOrderState(state, i)) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    // 1.更新订单表状态
    RentRecord rentRecord = new RentRecord();
    rentRecord.setRentRecordNo(rentRecordNo);
    rentRecord.setState(state);
    updateOrder(rentRecord);
    // 添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(req.getCreateBy());
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(state);
    rentState.setCreateMan(req.getCreateMan());
    rentStateDao.add(rentState);
    
    if (req.getState().equals(BackRentState.WaitSend.getCode())) {
      OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
      SmsType smsType = null;
      // 调用接口发送通知短信
      if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) {
        smsType = SmsType.SAlESEND_NOTICE;
      } else {
        smsType = SmsType.SEND_NOTICE;
      }

      List<String> datas = new ArrayList<>();
      datas.add(orderDetailResp.getMaterielModelName());
      sendMsg(req.getRentRecordNo(), smsType, orderDetailResp.getPhoneNum(), datas);
    }

    return ResponseResult.buildSuccessResponse();
  }

  @Transactional
  @Override
  public ResponseResult<String> signedOrder(UpdateOrderStateReq req) {
    String rentRecordNo = req.getRentRecordNo();
    int state = req.getState();
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkAliOrderState(state, orderDetailResp.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    // 1.更新订单表状态
    RentRecord rentRecord = new RentRecord();
    // 通过订单编号到订单扩展表查询对应订单的 产品租期（月）
    Calendar cld = Calendar.getInstance();
    cld.setTime(new Date());
    cld.add(Calendar.MONTH, orderDetailResp.getLeaseTerm());
    Date d2 = cld.getTime();
    // 设置订单还机时间
    rentRecord.setBackTime(DateUtils.getLastMillion(DateUtils.addDate(d2, 1)));
    rentRecord.setRentRecordNo(rentRecordNo);
    rentRecord.setState(state);
    updateOrder(rentRecord);
    // 2添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(Long.parseLong(orderDetailResp.getZmUserId()));
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(state);
    rentState.setCreateMan(orderDetailResp.getRealName());
    rentStateDao.add(rentState);

    // 3更新物流表状态为已签收
    if (StringUtils.isNotEmpty(orderDetailResp.getLogisticsNo())) {
      RentLogistics rentLogistics = new RentLogistics();
      rentLogistics.setState(1);
      UpdateDto<RentLogistics> updateDto = new UpdateDto<RentLogistics>();
      updateDto.setUpdateCloumn(rentLogistics);
      RentLogistics whereCloumn = new RentLogistics();
      whereCloumn.setLogisticsNo(orderDetailResp.getLogisticsNo());
      updateDto.setUpdateWhere(whereCloumn);
      rentLogisticsDao.update(updateDto);
    }
    
    
    try{
    //  通知库存系统 ，由“出库在途”变更为“在租”；
    RentingReq rentingReq = new RentingReq();
    rentingReq.setImieNo(orderDetailResp.getImei());
    rentingReq.setMaterielBasicId(Long.parseLong(orderDetailResp.getMaterielNo()));
    rentingReq.setSnNo(orderDetailResp.getSnCode());
    rentingReq.setSourceOrderNo(orderDetailResp.getRentRecordNo());
    ResponseResult<RentingReq> signFailed = iProductService.renting(rentingReq);
    if (signFailed.getErrCode() != 0) {
      log.error("signedOrder 通知库存系统更新库位失败,rentRecordNo={},getErrCode={},getErrMsg={}",
        req.getRentRecordNo(),
        signFailed.getErrCode(),
        signFailed.getErrMsg());
    }
  } catch (Exception e) {
    log.error("signedOrder 调用renting通知库存系统更新库位异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
  }
    return ResponseResult.buildSuccessResponse();
  }
  @Transactional
  @Override
  public ResponseResult<String> SendOut(UpdateOrderStateReq req) {
    String rentRecordNo = req.getRentRecordNo();
    int state = req.getState();
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkAliOrderState(state, orderDetailResp.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    // 1.更新订单表状态
    RentRecord rentRecord = new RentRecord();
    rentRecord.setRentRecordNo(rentRecordNo);
    rentRecord.setImei(req.getImei());
    rentRecord.setSnCode(req.getSnCode());
    rentRecord.setLogisticsNo(req.getReturnLogisticsNo());
    rentRecord.setState(state);
    updateOrder(rentRecord);
    // 添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(req.getCreateBy());
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(state);
    rentState.setCreateMan(req.getCreateMan());
    rentStateDao.add(rentState);

    // 添加物流表发货
    RentLogistics rentLogistics = new RentLogistics();
    rentLogistics.setBusinessNo(req.getBusinessNo());
    rentLogistics.setCreateBy(req.getCreateBy());
    rentLogistics.setCreateMan(req.getCreateMan());
    rentLogistics.setCreateOn(new Date());
    rentLogistics.setLogisticsNo(req.getReturnLogisticsNo());
    rentLogistics.setState(0);
    rentLogistics.setType(0);
    rentLogistics.setRentRecordNo(rentRecord.getRentRecordNo());
    rentLogisticsDao.add(rentLogistics);
    
    // 调用接口发送通知短信
    SmsType smsType = SmsType.RECIVE_NOTICE;
    List<String> datas = new ArrayList<>();
    datas.add(orderDetailResp.getMaterielModelName());
    sendMsg(rentRecord.getRentRecordNo(), smsType, orderDetailResp.getPhoneNum(), datas);

    return ResponseResult.buildSuccessResponse();
  }
  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public ResponseResult<String> paySuccess(UpdateOrderStateReq req) {
    String rentRecordNo = req.getRentRecordNo();
    int state = req.getState();
    OrderDetailResp orderDetail = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetail == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkAliOrderState(state, orderDetail.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    // 1.更新订单表状态
    RentRecord rentRecord = new RentRecord();
    rentRecord.setPayTime(new Date());// 支付完成时间
    rentRecord.setRentRecordNo(rentRecordNo);
    rentRecord.setState(state);
    updateOrder(rentRecord);
    // 2添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(req.getCreateBy());
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(state);
    rentState.setCreateMan(req.getCreateMan());
    rentStateDao.add(rentState);
    //  通知清算系统 ，生成还款计划并且支付首期款；
    if(req.getCallType() == 0){//app调用的就需要通知清算更新状态
        ZmRepaymentScheduleReq liquationreq = new ZmRepaymentScheduleReq();
        liquationreq.setCreateDate(rentRecord.getPayTime());
        liquationreq.setGoodsValue(orderDetail.getShowAmount());
        liquationreq.setOrderSN(rentRecord.getRentRecordNo());
        liquationreq.setPeriods(orderDetail.getLeaseTerm());
        liquationreq.setPhone(orderDetail.getPhoneNum());
        liquationreq.setRealName(orderDetail.getRealName());
        liquationreq.setRent(orderDetail.getLeaseAmount());
        liquationreq.setUserId(Long.parseLong(orderDetail.getZmUserId()));
        liquationreq.setZmOrderNo(orderDetail.getZmOrderNo());
        ResponseResult<String> addZmRepaymentSchedule = (ResponseResult<String>) iliquidationService.addZmRepaymentSchedule(liquationreq);
        if (addZmRepaymentSchedule.getErrCode() != 0) {
          log.error("addZmRepaymentSchedule 通知清算系统支付首期款失败,rentRecordNo={},getErrCode={},getErrMsg={}",
            req.getRentRecordNo(),
            addZmRepaymentSchedule.getErrCode(),
            addZmRepaymentSchedule.getErrMsg());
        }
     
    }
    return ResponseResult.buildSuccessResponse();
  }

  @Transactional
  @Override
  public void SfStatusChangeService() {
 // 1.查询出物流表中物流类型为0我司发货 物流状态为 0发货中的 数据
    RentLogistics dto = new RentLogistics();
    dto.setState(0);
    dto.setType(0);
    List<RentLogistics> queryList = rentLogisticsDao.queryList(dto);
    if (CollectionUtils.isNotEmpty(queryList)) {
      for (RentLogistics rentLogistics : queryList) {
        // 2.通过物流编号去第三方顺丰查询是否已经签收
        ResultCode routeQuery = shunFengService.routeQuery(rentLogistics.getLogisticsNo(), 1);
        if (routeQuery.getCode() == 0) {
          if (routeQuery.getData() != null) {
            List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) routeQuery.getData();
            if (CollectionUtils.isNotEmpty(list)) {
              if ("80".equals(list.get(0).get("opcode")) || "8000".equals(list.get(0).get("opcode"))) {
                // 4.更新订单状态为已签收
                UpdateOrderStateReq req = new UpdateOrderStateReq();
                req.setRentRecordNo(rentLogistics.getRentRecordNo());
                req.setCreateBy(1L);
                req.setCreateMan("system");
                  // 租赁和以租代购把订单状态改为正常履约中
                  req.setState(BackRentState.NormalPerformance.getCode());
                  this.signedOrder(req);
              }
            }
          }
        } else {
          XxlJobLogger.log("AliSfStatusJobHandler 调用shunFengService routeQuery异常 RentRecordNo={}"
                           + rentLogistics.getRentRecordNo() + ",errocode={}" +
                           routeQuery.getCode() + "errormsg={}" + routeQuery.getMessage());
        }

      }
    }
    
  }

  @Transactional
  @Override
  public ResponseResult<String> buyOut(UpdateOrderStateReq req) {
    String rentRecordNo = req.getRentRecordNo();
    int state = req.getState();
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkAliOrderState(state, orderDetailResp.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }


    String materielNo = orderDetailResp.getMaterielNo();

    RentRecord rentRecord = new RentRecord();
    rentRecord.setState(req.getState());
    rentRecord.setRentRecordNo(req.getRentRecordNo());

    if (updateOrder(rentRecord) > 0) {
      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(req.getCreateBy());
      rentState.setRentRecordNo(req.getRentRecordNo());
      rentState.setState(rentRecord.getState());
      rentState.setCreateMan(req.getCreateMan());
      rentState.setRemark(req.getRemark());
      rentStateDao.add(rentState);
    }

    // 调用仓库系统更新库位
    try {
      BuyEndVO q = new BuyEndVO();
      q.setImieNo(orderDetailResp.getImei());
      q.setMaterielBasicId(Long.parseLong(materielNo));
      q.setSnNo(orderDetailResp.getSnCode());
      q.setSourceOrderNo(orderDetailResp.getRentRecordNo());
      q.setProductId(orderDetailResp.getProductId().longValue());
      ResponseResult<BuyEndVO> buyEnd = iProductService.buyEnd(q);
      if (buyEnd.getErrCode() != 0) {
        log.error("buyout 调用 buyEnd异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          buyEnd.getErrCode(),
          buyEnd.getErrMsg());
      }
    } catch (Exception e) {
      log.error("buyout 调用 buyEnd异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
    }

    return ResponseResult.buildSuccessResponse("");
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public ResponseResult<OrderDetailResp> queryOrderDetail(String rentRecordNo) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (orderDetailResp.getBackTime() != null) {
      Calendar cld = Calendar.getInstance();
      cld.setTime(orderDetailResp.getBackTime());
      cld.add(Calendar.MONTH, -orderDetailResp.getLeaseTerm());
      Date d2 = cld.getTime();
      // 设置订单起租时间 （通过还机时间 - 租机总月份）
      orderDetailResp.setStartRentTime(d2);
    }

    BigDecimal frozenAmount = orderDetailResp.getShowAmount().subtract(orderDetailResp.getCreditAmount());
    orderDetailResp.setFrozenAmount(frozenAmount);
    
    Integer state = orderDetailResp.getState();

    RentState dto = new RentState();
    dto.setRentRecordNo(rentRecordNo);
    if (BackRentState.SendOut.getCode() <= state.intValue()) {
      // 如果订单状态大于等于发货中 ，则需要查询出发货日期
      dto.setState(BackRentState.SendOut.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setSendOutTime(queryList.get(0).getCreateOn());
      }
      orderDetailResp.setProductStateDesc("已发货");
    }

    if (BackRentState.NormalPerformance.getCode() <= state.intValue()) {
      // 如果买断成功需要查询出买断时间
      dto.setState(BackRentState.NormalPerformance.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setProductStateDesc("已签收");
        orderDetailResp.setReceivedTime(queryList.get(0).getCreateOn());
      }
    }
    
    if (BackRentState.EarlyBuyout.getCode() == state.intValue()) {
      // 如果买断成功需要查询出买断时间
      dto.setState(BackRentState.EarlyBuyout.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setBuyOutTime(queryList.get(0).getCreateOn());
      }
    }
    if (BackRentState.ForceBuyout.getCode() == state.intValue()
    ) {
      // 如果买断成功需要查询出买断时间
      dto.setState(BackRentState.ForceBuyout.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setBuyOutTime(queryList.get(0).getCreateOn());
      }
    }
    if (BackRentState.NormalBuyout.getCode() == state.intValue()) {
      // 如果买断成功需要查询出买断时间
      dto.setState(BackRentState.NormalBuyout.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setBuyOutTime(queryList.get(0).getCreateOn());
      }
    }
    
    if (BackRentState.Cancel.getCode() == state.intValue()) {
      // 如果取消订单需要查询出取消时间
      dto.setState(BackRentState.Cancel.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setCancleTime(queryList.get(0).getCreateOn());
      }
    }

    if (BackRentState.WaitPick.getCode() <= state.intValue()) {// 签约之后就能查询出账期
      // 通过订单编号查询当前的租机履约月份/总月份 (9/12) 已付滞纳金 已付租机 买断支付金
      ResponseResult<RepaymentDetailResp> repaymentDetail = iliquidationService.repaymentDetail(rentRecordNo);
      if (repaymentDetail.getErrCode() != 0) {
        log.error(
          "queryOrderDetail 调用liquidation repaymentDetail异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          repaymentDetail.getErrCode(),
          repaymentDetail.getErrMsg());
      } else {
        if (repaymentDetail.getData() != null) {
          RepaymentDetailResp repaymentDetailResp = repaymentDetail.getData();
          orderDetailResp.setBill(repaymentDetailResp.getBill());
          orderDetailResp.setBuyoutAmount(repaymentDetailResp.getBuyoutAmount());
          orderDetailResp.setSettledLateFees(repaymentDetailResp.getSettledLateFees());
          orderDetailResp.setSettledRent(repaymentDetailResp.getSettledRent());
        }
      }
    }

    if (BackRentState.Recycling.getCode() == state.intValue()
        || BackRentState.Recycle.getCode() == state.intValue()) {
      // 如果订单状态为归还中 需要查询出归还提交时间
      dto.setState(BackRentState.Recycling.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setRecycleTime(queryList.get(0).getCreateOn());
      }
    }
    if (BackRentState.EarlyRecycle.getCode() == state.intValue()
        || BackRentState.EarlyRecycing.getCode() == state.intValue()) {
      // 如果订单状态为提前归还中 需要查询出提前归还提交时间
      dto.setState(BackRentState.EarlyRecycing.getCode());
      List<RentState> queryList = rentStateDao.queryList(dto);
      if (CollectionUtils.isNotEmpty(queryList)) {
        orderDetailResp.setRecycleTime(queryList.get(0).getCreateOn());
      }
    }

      orderDetailResp
        .setTipInfo(StateConvert.getFrontAliStateByBackRentState(state).getCueWords());
      orderDetailResp
        .setStateDesc(StateConvert.getFrontAliStateByBackRentState(state).getMessage());
      orderDetailResp.setState(StateConvert.getFrontAliStateByBackRentState(state).getCode());

    // 如果订单状态为24,"履约完成" 则需要判断当前时间 是否 大于 该订单的还机时间 ，
    // 如果大于表示租赁到期 Expires(16, "租赁到期" ，
    return ResponseResult.buildSuccessResponse(orderDetailResp);
  }

  
  @Override
  public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageWareOrderList(RentRecordQuery rentRecordQuery) {
    // 如果没有传订单状态过来 则默认查询以下三种状态的订单信息
    if (rentRecordQuery.getState() == null) {
      List<Integer> stateList = new ArrayList<>();
      // WaitPick(7,"待拣货"),
      // WaitSend(8,"待发货"),
      stateList.add(BackRentState.WaitPick.getCode());
      stateList.add(BackRentState.WaitSend.getCode());
      rentRecordQuery.setStateList(stateList);
    }
    int totalNum = rentRecordDao.countWareOrderList(rentRecordQuery);
    if (totalNum <= 0) {
      return ResponseResult.buildSuccessResponse(null);
    }

    List<OrderDetailResp> queryPageWareOrderList = rentRecordDao.queryPageWareOrderList(rentRecordQuery);
    if (CollectionUtils.isEmpty(queryPageWareOrderList)) {
      return ResponseResult.buildSuccessResponse(null);
    }
    List<OrderDetailRespForWare> convertBeanList = BeanConvertUtil.convertBeanList(queryPageWareOrderList,
      OrderDetailRespForWare.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 循环遍历塞入 拣货日期，拣货人，填单日期，填单人
    for (OrderDetailRespForWare orderDetailRespForWare : convertBeanList) {
      orderDetailRespForWare.setApplyTimes(sdf.format(orderDetailRespForWare.getApplyTime()));
      // 如果当前订单状态大于待拣货
      if (orderDetailRespForWare.getState().intValue() > BackRentState.WaitPick.getCode()) {
        RentState rentState = new RentState();
        rentState.setRentRecordNo(orderDetailRespForWare.getRentRecordNo());
        rentState.setState(BackRentState.WaitSend.getCode());
        List<RentState> queryList = rentStateDao.queryList(rentState);

        if (!CollectionUtils.isEmpty(queryList)) {
          orderDetailRespForWare.setCheckMan(queryList.get(0).getCreateMan());
          orderDetailRespForWare.setCheckDate(sdf.format(queryList.get(0).getCreateOn()));
          //
          if (orderDetailRespForWare.getState().intValue() > BackRentState.WaitSend.getCode()) {
            rentState.setState(BackRentState.SendOut.getCode());
            List<RentState> sendqueryList = rentStateDao.queryList(rentState);
            if (!CollectionUtils.isEmpty(sendqueryList)) {
              orderDetailRespForWare.setSendMan(sendqueryList.get(0).getCreateMan());
              orderDetailRespForWare.setSendDate(sdf.format(sendqueryList.get(0).getCreateOn()));
            }
          }
        }
      }

    }
    ResultPager<OrderDetailRespForWare> data = new ResultPager<OrderDetailRespForWare>(totalNum,
      rentRecordQuery.getCurrPage(),
      rentRecordQuery.getPageSize(),
      convertBeanList);
    return ResponseResult.buildSuccessResponse(data);
  }

  @Override
  public ResponseResult<String> endPerformance(UpdateOrderStateReq req) {
    String rentRecordNo = req.getRentRecordNo();
    int state = req.getState();
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkAliOrderState(state, orderDetailResp.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }


    String materielNo = orderDetailResp.getMaterielNo();

    RentRecord rentRecord = new RentRecord();
    if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 2) {
      req.setState(BackRentState.NormalBuyout.getCode());
    }
    rentRecord.setState(req.getState());
    
    rentRecord.setRentRecordNo(req.getRentRecordNo());

    if (updateOrder(rentRecord) > 0) {
      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(req.getCreateBy());
      rentState.setRentRecordNo(req.getRentRecordNo());
      rentState.setState(rentRecord.getState());
      rentState.setCreateMan(req.getCreateMan());
      rentState.setRemark(req.getRemark());
      rentStateDao.add(rentState);
    }

    //如果产品类型为2 以租代售，履约完成需要改成正常买断，更新库存系统库位
    if(BackRentState.NormalBuyout.getCode() ==req.getState() ){
   // 调用仓库系统更新库位
      try {
        BuyEndVO q = new BuyEndVO();
        q.setImieNo(orderDetailResp.getImei());
        q.setMaterielBasicId(Long.parseLong(materielNo));
        q.setSnNo(orderDetailResp.getSnCode());
        q.setSourceOrderNo(orderDetailResp.getRentRecordNo());
        q.setProductId(orderDetailResp.getProductId().longValue());
        ResponseResult<BuyEndVO> buyEnd = iProductService.buyEnd(q);
        if (buyEnd.getErrCode() != 0) {
          log.error("buyout 调用 buyEnd异常 RentRecordNo={},errocode={},errormsg={}",
            orderDetailResp.getRentRecordNo(),
            buyEnd.getErrCode(),
            buyEnd.getErrMsg());
        }
      } catch (Exception e) {
        log.error("buyout 调用 buyEnd异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
      }
      
    }

    return ResponseResult.buildSuccessResponse("");
  }
  
  
  private void sendMsg(String rentRecordNo, SmsType smsType, String phoneNum, List<String> datas) {
    // 调用接口发送通知短信
    SmsDto dto = new SmsDto();
    dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
    dto.setPhone(phoneNum);
    dto.setSmsType(smsType.getType());
    dto.setTemplateId(smsType.getType());
    dto.setDatas(datas);
    ResponseResult<String> result = smsSendService.sendSmsStockSignInform(dto);
    if (!result.isSuccess()) {
      log.error(" 下发短信失败 rentRecordNo={},templateId={},ErrMsg={},ErrCode={}",
        rentRecordNo,
        smsType.getType(),
        result.getErrMsg(),
        result.getErrCode());
    }
  }
  
  @Override
  public ResponseResult<OrderDetailResp> queryBackOrderDetail(String rentRecordNo) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    orderDetailResp.setStateDesc(BackRentState.getBackByCode(orderDetailResp.getState()).getMessage());
    return ResponseResult.buildSuccessResponse(orderDetailResp);
  }

  @Override
  public ResponseResult<List<OrderDetailResp>>  queryOrderStateList(OrderDetailReq orderDetailReq) {
    List<OrderDetailResp> list = rentRecordDao.queryOrderStateList(orderDetailReq);
    if (CollectionUtils.isNotEmpty(list)) {
      for (OrderDetailResp orderDetailResp : list) {
          orderDetailResp.setTipInfo(
            StateConvert.getFrontAliStateByBackRentState(orderDetailResp.getState()).getCueWords());
          orderDetailResp.setStateDesc(
            StateConvert.getFrontAliStateByBackRentState(orderDetailResp.getState()).getMessage());
          orderDetailResp
          .setState(StateConvert.getFrontAliStateByBackRentState(orderDetailResp.getState()).getCode());
      }
      }
    return ResponseResult.buildSuccessResponse(list);
  }

	@Override
	public ResponseResult<ResultPager<OrderDetailResp>> queryRentRecordList(RentRecordQuery rentRecordQuery) {
		int totalNum = rentRecordDao.countWareOrderList(rentRecordQuery);
	    if (totalNum <= 0) {
	    	return ResponseResult.buildSuccessResponse(null);
	    }
	    List<OrderDetailResp> queryPageWareOrderList = rentRecordDao.queryPageWareOrderList(rentRecordQuery);
	    	if (CollectionUtils.isEmpty(queryPageWareOrderList)) {
	    	return ResponseResult.buildSuccessResponse(null);
	    }
	    ResultPager<OrderDetailResp> data = new ResultPager<OrderDetailResp>(totalNum,
	    	      rentRecordQuery.getCurrPage(),
	    	      rentRecordQuery.getPageSize(),
	    	      queryPageWareOrderList);
	    return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<ResultPager<RentState>> selectRentState(String rentRecordNo) {
		RentState rr=new RentState();
		rr.setRentRecordNo(rentRecordNo);
		List<RentState> rentStates = rentStateDao.queryList(rr);
		ResultPager<RentState> resultPager=new ResultPager<>();
		resultPager.setData(rentStates);
		return ResponseResult.buildSuccessResponse(resultPager);
	}

	@SuppressWarnings("unchecked")
  @Override
	public ResponseResult<ResultPager<RentLogisticsDto>> selectLogistics(String rentRecordNo) {
		RentLogistics r=new RentLogistics();
		r.setRentRecordNo(rentRecordNo);
		List<RentLogistics> list= rentLogisticsDao.queryList(r);
		List<RentLogisticsDto> dtolist= BeanConvertUtil.convertBeanList(list, RentLogisticsDto.class);
		for (RentLogisticsDto rentLogisticsDto : dtolist) {
			//查询物流信息
			List<LogisticsNodes> logisticsNodes= (List<LogisticsNodes>) shunFengService.routeQuery(rentLogisticsDto.getLogisticsNo(), 1).getData();
			rentLogisticsDto.setLogisticsNodes(logisticsNodes);
		}
		ResultPager<RentLogisticsDto> resultPager=new ResultPager<>();
		resultPager.setData(dtolist);
		return ResponseResult.buildSuccessResponse(resultPager);
	
	}
	
	 @Override
	  public ResponseResult<Map<String, Object>> lookContract(String rentRecordNo) {
	    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
	    if (orderDetailResp == null) {
	      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
	        OrderResultCode.NOT_RENT_RECORD.getMessage(),
	        null);
	    }
	    Map<String, Object> map = new HashMap<>();
	    
	    // 甲方
      map.put("companyName", "深圳市国智互联网科技有限公司");
      // 甲方联系电话
      map.put("companyContact", "0755-26609148");
      // 甲方地址
      map.put("companyAddress", "深圳市南山区高新南一道中科大厦18B");
	    
	    
	    // 乙方
	    map.put("realName", orderDetailResp.getRealName());
	    // 身份证
	    map.put("idNo", orderDetailResp.getIdNo());
	    // 收货地址
	    map.put("address",
	      orderDetailResp.getProv() + orderDetailResp.getCity() + orderDetailResp.getArea() + orderDetailResp.getAddress());
	    // 联系电话
	    map.put("phoneNum", orderDetailResp.getPhoneNum());
	    // 产品名称
	    map.put("matreielName", orderDetailResp.getMatreielName());
	    // 签约金额
	    map.put("signContractAmount", orderDetailResp.getSignContractAmount());
	    String materielSpecName = orderDetailResp.getMaterielSpecName();
	    String[] mStrings = materielSpecName.split(",");
	    // 规格
	    StringBuffer sBuffer = null;
	    for (int i = 0; i < mStrings.length; i++) {
	      sBuffer = new StringBuffer();
	      sBuffer.append("materielSpecName");
	      sBuffer.append(i + 1);
	      map.put(sBuffer.toString(), mStrings[i]);
	    }
	    // 租期
	    map.put("leaseTerm", orderDetailResp.getLeaseTerm());
	    // IMEI/SN
	   //map.put("imei", orderDetailResp.getImei() + "/" + orderDetailResp.getSnCode());
	    // 租金
	    map.put("leaseAmount", orderDetailResp.getLeaseAmount());

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    // 租用日期 起
	    map.put("RentStartTime", sdf.format(new Date()));

	    Calendar cld = Calendar.getInstance();
	    cld.setTime(new Date());
	    cld.add(Calendar.MONTH, orderDetailResp.getLeaseTerm());
	    Date d2 = cld.getTime();
	    // 设置订单结束租时间 （通过租用日期 + 租机总月份）
	    map.put("RentEndTime", sdf.format(d2));
	    // 意外保障服务费
	    map.put("premium", orderDetailResp.getPremium());

	    return ResponseResult.buildSuccessResponse(map);
	  }
}
