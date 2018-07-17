package org.gz.order.api.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.gz.cache.service.order.OrderCacheService;
import org.gz.common.OrderBy;
import org.gz.common.constants.SmsType;
import org.gz.common.entity.ResultPager;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.coupon.CouponReturnReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.gz.order.api.service.IProductService;
import org.gz.order.api.service.IRentRecordService;
import org.gz.order.api.service.IShunFengService;
import org.gz.order.api.service.IUploadAliService;
import org.gz.order.api.service.IliquidationService;
import org.gz.order.api.web.controller.FreemarkerUtil;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.Enum.FrontRentState;
import org.gz.order.common.Enum.OrderResultCode;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.OrderDetailRespForWare;
import org.gz.order.common.dto.QueryInvoiceReq;
import org.gz.order.common.dto.QueryInvoiceRsp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.RentRecordReq;
import org.gz.order.common.dto.ResultCode;
import org.gz.order.common.dto.SaleOrderReq;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.dto.WorkOrderRsp;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.order.common.entity.RentInvoice;
import org.gz.order.common.entity.RentLogistics;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.RentState;
import org.gz.order.common.entity.UserHistory;
import org.gz.order.common.utils.StateConvert;
import org.gz.order.common.utils.UnqieCodeGenerator;
import org.gz.order.server.dao.RentCoordinateDao;
import org.gz.order.server.dao.RentInvoiceDao;
import org.gz.order.server.dao.RentLogisticsDao;
import org.gz.order.server.dao.RentRecordDao;
import org.gz.order.server.dao.RentRecordExtendsDao;
import org.gz.order.server.dao.RentStateDao;
import org.gz.order.server.dao.UserHistoryDao;
import org.gz.order.server.service.RentInvoiceService;
import org.gz.order.server.service.RentLogisticsService;
import org.gz.order.server.service.RentRecordService;
import org.gz.order.server.service.UserHistoryService;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.service.SmsSendService;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xxl.job.core.log.XxlJobLogger;
@Service
public class IRentRecordServiceImpl implements IRentRecordService {

  Logger logger = LoggerFactory.getLogger(IRentRecordServiceImpl.class);
  @Resource
  private RentRecordDao rentRecordDao;
  @Resource
  private RentRecordExtendsDao rentRecordExtendsDao;
  @Resource
  private RentStateDao rentStateDao;
  @Resource
  private RentCoordinateDao rentCoordinateDao;
  @Resource
  private IProductService iProductService;
  @Resource
  private IliquidationService iliquidationService;
  @Autowired
  private IUploadAliService uploadAliService;
  @Autowired
  SmsSendService smsSendService;
  @Autowired
  private RentRecordService rentRecordService;
  @Autowired
  private UserHistoryDao userHistoryDao;
  @Autowired
  private UserHistoryService userHistoryService;
  @Autowired
  private IShunFengService shunFengService;
  @Autowired
  private RentLogisticsService rentLogisticsService;
  @Resource
  private RentInvoiceDao rentInvoiceDao;
  @Resource
  private RentLogisticsDao rentLogisticsDao;
  @Resource
  private OrderCacheService orderCacheService;
  @Resource
  private RentInvoiceService rentInvoiceService;

  @Value("${agreement.path}")
  private String path;
  @Value("${oss.agreement.path}")
  private String osspath;
  @Value("${aliyun.oss.accessUrlPrefix}")
  private String aliyunpath;
  @Value("${cancelTime}")
  private String cancelTime;

  @Override
public ResponseResult<List<RentRecord>> queryRentRecordByState(Integer state) {
      try {
          logger.debug("queryRentRecordByState {}", state);
          List<RentRecord> list = rentRecordDao.queryRentRecordByState(state);
          return ResponseResult.buildSuccessResponse(list);
    } catch (Exception e) {
        logger.error("queryRentRecordByState {}  ", state,e.getMessage());
          return ResponseResult.build(OrderResultCode.SERVICE_EXCEPTION.getCode(),
                    OrderResultCode.SERVICE_EXCEPTION.getMessage(),null);
    }
}

@Transactional
  @Override
  public ResponseResult<String> add(AddOrderReq addOrderRequest) {
    // 1.先通过用户id查询订单表是否有状态为1待审批的订单，如果有拒绝提交
    Long userId = addOrderRequest.getUserId();
    RentRecord queryRentRecord = new RentRecord();
    queryRentRecord.setUserId(userId);
    queryRentRecord.setState(BackRentState.ApprovalPending.getCode());
    int queryCount = rentRecordDao.queryCount(queryRentRecord);
    if (queryCount > 0) {
      return ResponseResult.build(OrderResultCode.RENT_APPROVAL_PENDING.getCode(),
        OrderResultCode.RENT_APPROVAL_PENDING.getMessage(),
        null);
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

    // 3.添加订单表
    addOrderRequest.setApplyTime(new Date());
    addOrderRequest.setCreateOn(new Date());
    addOrderRequest.setProductType(pInfo.getProductType());
    // 3.1 获取订单主键id
    rentRecordDao.add(addOrderRequest);
    Long id = addOrderRequest.getId();
    String rentRecordNo = UnqieCodeGenerator.getCode("SO", id);

    // 3.2.添加扩展表 取到扩展表自增主键ID
    recordExtends.setRentRecordNo(rentRecordNo);
    recordExtends.setUserId(userId);
    rentRecordExtendsDao.add(recordExtends);
    Long extId = recordExtends.getId();
    // 4.将订单编号,扩展表id 更新到订单表
    RentRecord uprentRecord = new RentRecord();
    uprentRecord.setExtendId(extId);
    uprentRecord.setRentRecordNo(rentRecordNo);

    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(uprentRecord);

    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setId(id);
    updateDto.setUpdateWhere(whereCloumn);

    if (rentRecordDao.update(updateDto) > 0) {
      // 5.返回订单编号
      return ResponseResult.buildSuccessResponse(rentRecordNo);
    }
    return ResponseResult.build(1000, "数据库异常", null);
  }

  @Transactional
  @Override
  public ResponseResult<OrderDetailResp> submitOrder(SubmitOrderReq submitOrderReq) {

    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(submitOrderReq.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (!StateConvert.checkOrderState(BackRentState.ApprovalPending.getCode(), orderDetailResp.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }

    // 2.先通过用户id查询订单表是否有状态为1待审批的订单，如果有拒绝提交
    Long userId = submitOrderReq.getUserId();
    RentRecord queryRentRecord = new RentRecord();
    queryRentRecord.setUserId(userId);
    queryRentRecord.setState(BackRentState.ApprovalPending.getCode());
    int queryCount = rentRecordDao.queryCount(queryRentRecord);
    if (queryCount > 0) {
      return ResponseResult.build(OrderResultCode.RENT_APPROVAL_PENDING.getCode(),
        OrderResultCode.RENT_APPROVAL_PENDING.getMessage(),
        null);
    }

    RentRecord rentRecord = new RentRecord();
    // 1.更新订单状态为待审核
    rentRecord.setState(BackRentState.ApprovalPending.getCode());
    rentRecord.setCouponId(submitOrderReq.getCouponId());
    rentRecord.setCouponAmount(submitOrderReq.getCouponAmount());
    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(rentRecord);

    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setRentRecordNo(submitOrderReq.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);

    // 2.更新订单扩展表用户信息
    RentRecordExtends recordExtends = BeanConvertUtil.convertBean(submitOrderReq, RentRecordExtends.class);
    UpdateDto<RentRecordExtends> updateExtendsDto = new UpdateDto<RentRecordExtends>();
    recordExtends.setEmergencyContactPhone(formatPhoneNum(recordExtends.getEmergencyContactPhone()));
    updateExtendsDto.setUpdateCloumn(recordExtends);

    RentRecordExtends whereCloumnExtends = new RentRecordExtends();
    whereCloumnExtends.setRentRecordNo(submitOrderReq.getRentRecordNo());
    updateExtendsDto.setUpdateWhere(whereCloumnExtends);
    int i = rentRecordExtendsDao.update(updateExtendsDto);

    // 3添加历史用户信息表
    UserHistory userHistory = submitOrderReq.getUserHistory();
    userHistory.setRentRecordNo(submitOrderReq.getRentRecordNo());
    userHistory.setUserId(userId);
    userHistoryDao.add(userHistory);

    if (rentRecordDao.update(updateDto) > 0 && i > 0) {
      // 4 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(submitOrderReq.getUserId());
      rentState.setRentRecordNo(submitOrderReq.getRentRecordNo());
      rentState.setState(BackRentState.ApprovalPending.getCode());
      rentState.setCreateMan(submitOrderReq.getCreateMan());

      // 5添加经纬度
      RentCoordinate m = new RentCoordinate();
      m.setState(BackRentState.ApprovalPending.getCode());
      m.setLat(submitOrderReq.getLat());
      m.setLng(submitOrderReq.getLng());
      m.setPhoneModel(submitOrderReq.getPhoneModel());
      m.setImei(submitOrderReq.getImei());
      m.setCreateOn(new Date());
      m.setRentRecordNo(submitOrderReq.getRentRecordNo());
      rentCoordinateDao.add(m);

      if (rentStateDao.add(rentState) > 0) {
                // 8.查询订单详细信息返回
                ResponseResult<OrderDetailResp> resp = queryOrderDetail(submitOrderReq.getRentRecordNo());

                // 7. 调用接口发送通知短信
                SmsType smsType = SmsType.APPLY_SUCCESS;
                List<String> datas = new ArrayList<>();
                datas.add(orderDetailResp.getMaterielModelName());
                datas.add(submitOrderReq.getRentRecordNo());
                sendMsg(submitOrderReq.getRentRecordNo(), smsType, submitOrderReq.getPhoneNum(), datas);
                return resp;
      }
    }

    return ResponseResult.build(1000, "数据库异常", null);
  }

  @Transactional
  @Override
  public ResponseResult<String> updateOrderState(UpdateOrderStateReq req) {

    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }

    // 如果订单状态为履约完成，则要判断是不是以租代售订单，如果是则调用买断方法
    if (req.getState() == BackRentState.EndPerformance.getCode()) {
      if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 2) {
        req.setState(BackRentState.NormalBuyout.getCode());
        return this.buyOut(req);
      }
    }

    // 如果是产品类型为3表示售卖的产品 则判断状态流转方法用checkBuyOrderState
    if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) {
      if (!StateConvert.checkBuyOrderState(req.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    } else {
      if (!StateConvert.checkOrderState(req.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    }

    RentRecord rentRecord = new RentRecord();
    if (req.getState().equals(BackRentState.WaitPayment.getCode())) {
      rentRecord.setApprovalTime(new Date()); // 审核通过时间
    } else if (req.getState().equals(BackRentState.WaitSignup.getCode())) {
      rentRecord.setPayTime(new Date());// 支付完成时间
      // 调用接口发送通知短信
      SmsType smsType = SmsType.PAY_SUCCESS;
      List<String> datas = new ArrayList<>();
      datas.add(req.getRentRecordNo());
      sendMsg(req.getRentRecordNo(), smsType, orderDetailResp.getPhoneNum(), datas);
    } else if (req.getState().equals(BackRentState.PrematureTerminating.getCode())) {
      rentRecord.setTerminateApplyTime(new Date());// 提前解约申请时间
    } else if (req.getState().equals(BackRentState.WaitSend.getCode())) {
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
    } else if (req.getState().equals(BackRentState.ForceBuyIng.getCode())
               || req.getState().equals(BackRentState.ForcePerformanceIng.getCode())) {
      // 强制买断中 需要调用清算的接口判断是否有正在执行中的扣款操作 如果有不能进行
      ResponseResult<Boolean> queryTradeState = iliquidationService.queryTradeState(req.getRentRecordNo());
      if (!queryTradeState.isSuccess()) {
        logger.error("updateOrderState方法调用清算 queryTradeState异常,rentRecordNo={},getErrCode={},getErrMsg={}",
          req.getRentRecordNo(),
          queryTradeState.getErrCode(),
          queryTradeState.getErrMsg());
        return ResponseResult.build(queryTradeState.getErrCode(),
          queryTradeState.getErrMsg(),
          null);
      }
      if (!queryTradeState.getData()) {
        // 为false表示有在途的扣款交易不能更新订单状态
        return ResponseResult.build(OrderResultCode.PAY_ING.getCode(),
          OrderResultCode.PAY_ING.getMessage(),
          null);
      }

    } else if (req.getState().equals(BackRentState.NormalPerformance.getCode())) {// 确认收货状态需要更新订单还机时间
      // 通过订单编号到订单扩展表查询对应订单的 产品租期（月）
      RentRecordExtends recordExtends = rentRecordExtendsDao.getByRentRecordNo(req.getRentRecordNo());
      if (recordExtends == null) {
        return ResponseResult.build(OrderResultCode.NOT_RENTEXTENDS_RECORD.getCode(),
          OrderResultCode.NOT_RENTEXTENDS_RECORD.getMessage(),
          null);
      }
      Calendar cld = Calendar.getInstance();
      cld.setTime(new Date());
      cld.add(Calendar.MONTH, recordExtends.getLeaseTerm());
      Date d2 = cld.getTime();
      // 设置订单还机时间
      rentRecord.setBackTime(DateUtils.getLastMillion(DateUtils.addDate(d2, 1)));

      // 通知库存系统 ，由“出库在途”变更为“在租”；
      RentingReq rentingReq = new RentingReq();
      rentingReq.setImieNo(orderDetailResp.getImei());
      rentingReq.setMaterielBasicId(Long.parseLong(orderDetailResp.getMaterielNo()));
      rentingReq.setSnNo(orderDetailResp.getSnCode());
      rentingReq.setSourceOrderNo(orderDetailResp.getRentRecordNo());
      ResponseResult<RentingReq> signFailed = iProductService.renting(rentingReq);
      if (signFailed.getErrCode() != 0) {
        logger.error("signedOrder 通知库存系统更新库位失败,rentRecordNo={},getErrCode={},getErrMsg={}",
          req.getRentRecordNo(),
          signFailed.getErrCode(),
          signFailed.getErrMsg());
      }

    } else if (req.getState().equals(BackRentState.Cancel.getCode())) {// 取消售卖订单
      if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) {
        // 1.需要清除订单存在redis中的map缓存
        Long hdelwaitPayOrder = orderCacheService.hdelwaitPayOrder(req.getRentRecordNo());
        if (hdelwaitPayOrder == 0) {
          logger.error("updateOrderState 方法中取消订单清除redis缓存失败,{}", req.getRentRecordNo());
        }
        // 2.更新库存系统库位
        SigningQuery qsing = new SigningQuery();
        qsing.setMaterielBasicId(Long.parseLong(orderDetailResp.getMaterielNo()));
        qsing.setSourceOrderNo(req.getRentRecordNo());
        qsing.setSnNo(orderDetailResp.getSnCode());
        qsing.setImieNo(orderDetailResp.getImei());
        qsing.setProductId(orderDetailResp.getProductId().longValue());
        ResponseResult<SigningResult> signFailed = iProductService.signFailed(qsing);
        if (signFailed.getErrCode() != 0) {
          logger.error("updateOrderState 取消订单更新库存系统库位失败,rentRecordNo={},getErrCode={},getErrMsg={}",
            req.getRentRecordNo(),
            signFailed.getErrCode(),
            signFailed.getErrMsg());
        }
      } else {
        if (orderDetailResp.getState() >= BackRentState.WaitSignup.getCode()) {
          // 如果订单状态大于待签约 并且为租赁订单则需要通知通知清算退回优惠券与退款
          if (!StringUtils.isEmpty(orderDetailResp.getCouponId())) {
            CouponReturnReq couponReturnReq =null;
            String[] aa = orderDetailResp.getCouponId().split(",");
            for (String string : aa) {
              couponReturnReq = new CouponReturnReq();
              couponReturnReq.setCouponId(Long.parseLong(string));
              couponReturnReq.setOrderSN(orderDetailResp.getRentRecordNo());
              ResponseResult<?> returnCoupon = iliquidationService.returnCoupon(couponReturnReq);
              if (returnCoupon.getErrCode() != 0) {
                logger.error("updateOrderState 取消订单调用清算系统returnCoupon失败,rentRecordNo={},getErrCode={},getErrMsg={}",
                  req.getRentRecordNo(),
                  returnCoupon.getErrCode(),
                  returnCoupon.getErrMsg());
              }
          }
          }
        }
      }

    }
    rentRecord.setState(req.getState());
    rentRecord.setRentRecordNo(req.getRentRecordNo());

    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(rentRecord);

    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setRentRecordNo(rentRecord.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);

    if (rentRecordDao.update(updateDto) > 0) {
      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(req.getCreateBy());
      rentState.setRentRecordNo(req.getRentRecordNo());
      rentState.setState(req.getState());
      rentState.setCreateMan(req.getCreateMan());
      if (!StringUtils.isEmpty(req.getRemark())) {
        rentState.setRemark(req.getRemark());
      }
      if (rentStateDao.add(rentState) > 0) {
        return ResponseResult.buildSuccessResponse("");
      }
    }
    return ResponseResult.build(1000, "数据库异常", null);
  }

  @Transactional
  @Override
  public ResponseResult<String> applySign(UpdateOrderStateReq req) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }

    if (orderDetailResp.getState().intValue() != BackRentState.WaitSignup.getCode()
        && orderDetailResp.getState().intValue() != BackRentState.WaitAssembly.getCode()) {
      // 如果当前流程状态不等于待签约或者待配货 表示已经签约完成 不需要再签约
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    // 通过物料id调用库存服务查询是否有库存，有的话返回一条产品信息（包含SN和IMEI）
    String materielNo = orderDetailResp.getMaterielNo();
    String snCode = "";
    String imei = "";
    boolean repateSign = false;
    // 如果订单表里面已经存在imei和sncode 说明之前已经点击过申请签约已经锁定了库存则不需要重新去库存系统查询
    SigningQuery qsing = new SigningQuery();
    qsing.setMaterielBasicId(Long.parseLong(materielNo));
    qsing.setSourceOrderNo(req.getRentRecordNo());
    qsing.setProductId(orderDetailResp.getProductId().longValue());
    if (StringUtils.isEmpty(orderDetailResp.getImei()) && StringUtils.isEmpty(orderDetailResp.getSnCode())) {
      ResponseResult<SigningResult> signing = iProductService.signing(qsing);

      logger.info("applySign 查询库存,rentRecordNo={}, 响应信息={}", req.getRentRecordNo(), JsonUtils.toJsonString(signing));

      // 1.2 如果没有库存将订单状态改为待配货。
      if (signing.getErrCode() != 0) {
        RentRecord rentRecord = new RentRecord();
        rentRecord.setState(BackRentState.WaitAssembly.getCode());
        rentRecord.setStockFlag(2);
        rentRecord.setRentRecordNo(req.getRentRecordNo());
        // 更新订单状态为待配货
        if (UpdateOrder(rentRecord) > 0) {
          // 更新订单成功之后添加rent_state表信息
          RentState rentState = new RentState();
          rentState.setCreateOn(new Date());
          rentState.setCreateBy(req.getCreateBy());
          rentState.setRentRecordNo(req.getRentRecordNo());
          rentState.setState(rentRecord.getState());
          rentState.setCreateMan(req.getCreateMan());
          rentStateDao.add(rentState);
        }
        logger.info("applySign 查询库存,rentRecordNo={},getErrCode={},getErrMsg={}",
          req.getRentRecordNo(),
          signing.getErrCode(),
          signing.getErrMsg());
        return ResponseResult.build(OrderResultCode.NO_STOCK.getCode(),
          OrderResultCode.NO_STOCK.getMessage(),
          null);
      }

      SigningResult signingResult = signing.getData();
      snCode = signingResult.getSnNo();
      imei = signingResult.getImieNo();
    } else {
      snCode = orderDetailResp.getSnCode();
      imei = orderDetailResp.getImei();
      repateSign = true;
    }
    qsing.setSnNo(snCode);
    qsing.setImieNo(imei);

    // 2.生成合同pdf文件到指定文件夹下面，以订单号为文件名
    Map<String, Object> map = new HashMap<>();
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
    map.put("imei", imei + "/" + snCode);
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

    InputStream inputStream = null;
    String ossurl = "";
    try {
      String outputFile = FreemarkerUtil.generatePdfFromTemplate(path,
        req.getRentRecordNo(),
        "agreement",
        "userLease.ftl",
        map);

      // 上传文件到阿里云
      File f = new File(outputFile);
      inputStream = new FileInputStream(f);
      ossurl = osspath + req.getCreateBy().toString() + "/" + req.getRentRecordNo() + ".pdf";
      UploadBody dataMap = new UploadBody();
      dataMap.setFile(IOUtils.toByteArray(inputStream));
      dataMap.setFileName(ossurl);
      ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
      // 删除本地pdf文件
      f.delete();
    } catch (Exception e) {
      logger.error("applySign 申请签约生成合同失败,rentRecordNo={},e={}", req.getRentRecordNo(), e);
      // 如果是第一次生成合同失败，需要解冻库存
      if (!repateSign) {
        ResponseResult<SigningResult> signFailed = iProductService.signFailed(qsing);
        if (signFailed.getErrCode() != 0) {
          logger.error("applySign 撤销签约失败,rentRecordNo={},getErrCode={},getErrMsg={}",
            req.getRentRecordNo(),
            signFailed.getErrCode(),
            signFailed.getErrMsg());
        }
      }

      return ResponseResult.build(OrderResultCode.RENT_AGREEMENT_SAVE_ERROR.getCode(),
        e.getLocalizedMessage(),
        null);

    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
      }
    }

    RentRecord rentRecord = new RentRecord();
    rentRecord.setAgreementUrl(aliyunpath + ossurl);
    rentRecord.setImei(imei);
    rentRecord.setSnCode(snCode);
    rentRecord.setRentRecordNo(req.getRentRecordNo());
    try {
      UpdateOrder(rentRecord);
    } catch (Exception e) {
      // 如果是第一次生成合同失败，需要解冻库存
      if (!repateSign) {
        ResponseResult<SigningResult> signFailed = iProductService.signFailed(qsing);
        if (signFailed.getErrCode() != 0) {
          logger.error("applySign 撤销签约失败,rentRecordNo={},getErrCode={},getErrMsg={}",
            req.getRentRecordNo(),
            signFailed.getErrCode(),
            signFailed.getErrMsg());
        }
      }
      throw e;
    }

    // 返回阿里云服务url地址
    return ResponseResult.buildSuccessResponse(aliyunpath + ossurl);
  }

  @Transactional
  @Override
  public ResponseResult<String> buyOut(UpdateOrderStateReq req) {

    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    // 如果是产品类型为3表示售卖的产品 则判断状态流转方法用checkBuyOrderState
    if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) {
      if (!StateConvert.checkBuyOrderState(req.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    } else {
      if (!StateConvert.checkOrderState(req.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    }


    String materielNo = orderDetailResp.getMaterielNo();

    RentRecord rentRecord = new RentRecord();
    rentRecord.setState(req.getState());
    rentRecord.setRentRecordNo(req.getRentRecordNo());

    if (UpdateOrder(rentRecord) > 0) {
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



    SmsType smsType = null;
    if (orderDetailResp.getProductType() != 3) {
      // 调用接口发送通知短信
       smsType = SmsType.BUTOUT_SUCCESS;
    }else{
      smsType = SmsType.SAlEBUTOUT_SUCCESS;

      // 3更新物流表状态为已签收
      if (!StringUtils.isEmpty(orderDetailResp.getLogisticsNo())) {
        RentLogistics rentLogistics = new RentLogistics();
        rentLogistics.setState(1);
        UpdateDto<RentLogistics> updateDto = new UpdateDto<RentLogistics>();
        updateDto.setUpdateCloumn(rentLogistics);
        RentLogistics whereCloumn = new RentLogistics();
        whereCloumn.setLogisticsNo(orderDetailResp.getLogisticsNo());
        updateDto.setUpdateWhere(whereCloumn);
        rentLogisticsDao.update(updateDto);
      }

    }
    List<String> datas = new ArrayList<>();
    datas.add(orderDetailResp.getMaterielModelName());
    sendMsg(req.getRentRecordNo(), smsType, orderDetailResp.getPhoneNum(), datas);
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
        logger.error("buyout 调用 buyEnd异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          buyEnd.getErrCode(),
          buyEnd.getErrMsg());
      }
    } catch (Exception e) {
      logger.error("buyout 调用 buyEnd异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
    }

    return ResponseResult.buildSuccessResponse("");
  }

  @Transactional
  @Override
  public ResponseResult<String> sureSign(UpdateOrderStateReq req) {

    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }

    if (!StateConvert.checkOrderState(BackRentState.WaitPick.getCode(), orderDetailResp.getState())) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }

    RentRecord rentRecord = new RentRecord();
    rentRecord.setState(BackRentState.WaitPick.getCode());
    rentRecord.setSignTime(new Date());// 签约完成时间
    // 签约完成还需要更新订单表的 sealAgreementUrl 和evid
    rentRecord.setSealAgreementUrl(req.getSealAgreementUrl());
    rentRecord.setEvid(req.getEvid());
    rentRecord.setSignServiceId(req.getSignServiceId());
    rentRecord.setRentRecordNo(req.getRentRecordNo());
    if (UpdateOrder(rentRecord) > 0) {
      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(req.getCreateBy());
      rentState.setRentRecordNo(req.getRentRecordNo());
      rentState.setState(rentRecord.getState());
      rentState.setCreateMan(req.getCreateMan());
      rentStateDao.add(rentState);
    }

    // 如果签约的时候经纬度不为空还需要更新经纬度
    if (!StringUtils.isEmpty(req.getLat()) || !StringUtils.isEmpty(req.getLng())
        || !StringUtils.isEmpty(req.getPhoneModel()) || !StringUtils.isEmpty(req.getImei())) {
      // 将状态为2 的对应订单的经纬度设置为无效 (防止多次添加签约时的经纬度)
      UpdateDto<RentCoordinate> updateDto = new UpdateDto<RentCoordinate>();
      RentCoordinate updateCloumn = new RentCoordinate();
      updateCloumn.setValid(false);
      updateDto.setUpdateCloumn(updateCloumn);
      RentCoordinate whereCloumn = new RentCoordinate();
      whereCloumn.setRentRecordNo(req.getRentRecordNo());
      whereCloumn.setState(2);
      updateDto.setUpdateWhere(whereCloumn);
      rentCoordinateDao.update(updateDto);

      // 添加经纬度
      RentCoordinate m = new RentCoordinate();
      m.setState(2);
      m.setLat(req.getLat());
      m.setLng(req.getLng());
      m.setPhoneModel(req.getPhoneModel());
      m.setImei(req.getImei());
      m.setCreateOn(new Date());
      m.setRentRecordNo(req.getRentRecordNo());
      rentCoordinateDao.add(m);
    }

    // 调用清算系统生成还款计划
    try {
      RepaymentScheduleReq repaymentScheduleReq = new RepaymentScheduleReq();
      repaymentScheduleReq.setOrderSN(req.getRentRecordNo());
      repaymentScheduleReq.setUserId(orderDetailResp.getUserId());
      repaymentScheduleReq.setAmount(orderDetailResp.getSignContractAmount());
      repaymentScheduleReq.setRent(orderDetailResp.getLeaseAmount());
      repaymentScheduleReq.setProductNo(orderDetailResp.getProductNo());
      repaymentScheduleReq.setPeriods(orderDetailResp.getLeaseTerm());
      repaymentScheduleReq.setInsurance(orderDetailResp.getPremium());
      repaymentScheduleReq.setPremium(orderDetailResp.getFloatAmount());
      repaymentScheduleReq.setPhone(orderDetailResp.getPhoneNum());
      repaymentScheduleReq.setRealName(orderDetailResp.getRealName());
      repaymentScheduleReq.setGoodsValue(orderDetailResp.getShowAmount());
      ResponseResult<?> doSign = iliquidationService.doSign(repaymentScheduleReq);
      if (doSign.getErrCode() != 0) {
        logger.error("sureSign 调用liquidation doSign异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          doSign.getErrCode(),
          doSign.getErrMsg());
      }
    } catch (Exception e) {
      logger.error("sureSign 调用liquidation doSign异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
    }

    // 调用接口发送通知短信
    SmsType smsType = SmsType.SIGN_SUCCESS;
    List<String> datas = new ArrayList<>();
    datas.add(req.getRentRecordNo());
    sendMsg(req.getRentRecordNo(), smsType, orderDetailResp.getPhoneNum(), datas);

    return ResponseResult.buildSuccessResponse("");
  }

  private int UpdateOrder(RentRecord rentRecord) {
    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(rentRecord);
    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setRentRecordNo(rentRecord.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);
    int i = rentRecordDao.update(updateDto);
    return i;
  }

  /**
   * 0我司发货 ' 仓库系统确认发货调用接口，更新订单号与收货人地址等信息 需要传入订单号，状态（9,"发货中 ） 运单号等字段
   */
  @Transactional
  @Override
  public ResponseResult<String> SendOut(RentRecordReq rentRecord) {

    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecord.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    // 如果是产品类型为3表示售卖的产品 则判断状态流转方法用checkBuyOrderState
    if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) {
      if (!StateConvert.checkBuyOrderState(rentRecord.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    } else {
      if (!StateConvert.checkOrderState(rentRecord.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    }

    // 更新订单表
    if (UpdateOrder(rentRecord) > 0) {
      // 添加物流表发货
      RentLogistics rentLogistics = new RentLogistics();
      rentLogistics.setBusinessNo(rentRecord.getBusinessNo());
      rentLogistics.setCreateBy(rentRecord.getCreateBy());
      rentLogistics.setCreateMan(rentRecord.getCreateMan());
      rentLogistics.setCreateOn(new Date());
      rentLogistics.setLogisticsNo(rentRecord.getLogisticsNo());
      rentLogistics.setState(0);
      rentLogistics.setType(0);
      rentLogistics.setRentRecordNo(rentRecord.getRentRecordNo());
      rentLogisticsService.add(rentLogistics);

      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(rentRecord.getCreateBy());
      rentState.setRentRecordNo(rentRecord.getRentRecordNo());
      rentState.setState(rentRecord.getState());
      rentState.setCreateMan(rentRecord.getCreateMan());
      rentStateDao.add(rentState);

      if (rentRecord.getState().equals(BackRentState.SendOut.getCode())) {
        // 调用接口发送通知短信
        SmsType smsType = SmsType.RECIVE_NOTICE;
        List<String> datas = new ArrayList<>();
        datas.add(orderDetailResp.getMaterielModelName());
        sendMsg(rentRecord.getRentRecordNo(), smsType, orderDetailResp.getPhoneNum(), datas);
      }

      return ResponseResult.buildSuccessResponse("");
    }
    return ResponseResult.build(1000, "数据库异常", null);
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
      // 同时查询出订单物流状态
      ResultCode routeQuery = shunFengService.routeQuery(orderDetailResp.getLogisticsNo(), 1);
      if (routeQuery.getCode() == 0) {
        if (routeQuery.getData() != null) {
          List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) routeQuery.getData();
          if (CollectionUtils.isNotEmpty(list)) {
            if ("80".equals(list.get(0).get("opcode")) || "8000".equals(list.get(0).get("opcode"))) {
              orderDetailResp.setProductStateDesc("已签收");
            }
          }
        }

      } else {
        logger.error(
          "queryOrderDetail 调用shunFengService routeQuery异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          routeQuery.getCode(),
          routeQuery.getMessage());
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

    if (BackRentState.WaitPick.getCode() <= state.intValue()) {// 签约之后就能查询出账期
      // 通过订单编号查询当前的租机履约月份/总月份 (9/12) 已付滞纳金 已付租机 买断支付金
      ResponseResult<RepaymentDetailResp> repaymentDetail = iliquidationService.repaymentDetail(rentRecordNo);
      if (repaymentDetail.getErrCode() != 0) {
        logger.error(
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

    if (orderDetailResp.getProductType() != null && 3 == orderDetailResp.getProductType()) {

      // 加上待支付到期时间
      if (orderDetailResp.getState() == BackRentState.WaitPayment.getCode()) {
        orderDetailResp
          .setWaitPayTime(DateUtils.addSecond(orderDetailResp.getApplyTime(), Integer.parseInt(cancelTime)));
      }

      // 售卖订单 查询待支付总金额= 签约价值+碎屏保障金（可选）-优惠券
      BigDecimal waitPayAmount = orderDetailResp.getSignContractAmount();
      if (orderDetailResp.getBrokenScreenBuyed()) { // 如果购买了碎屏保障
        waitPayAmount = waitPayAmount.add(orderDetailResp.getBrokenScreenAmount());
      }
      if (!StringUtils.isEmpty(orderDetailResp.getCouponAmount())) {
        String[] aa = orderDetailResp.getCouponAmount().split(",");
        for (String string : aa) {
          waitPayAmount = waitPayAmount.subtract(new BigDecimal(string));
        }
      }
      orderDetailResp.setWaitPayAmount(waitPayAmount);

      orderDetailResp.setTipInfo(
        StateConvert.getFrontSaleStateByBackRentState(orderDetailResp.getState()).getCueWords());
      orderDetailResp.setStateDesc(
        StateConvert.getFrontSaleStateByBackRentState(orderDetailResp.getState()).getMessage());
      orderDetailResp
        .setState(StateConvert.getFrontSaleStateByBackRentState(orderDetailResp.getState()).getCode());
    } else {
      // 租赁订单 查询待支付总金额= 签约价值+意外保障金-优惠券
      BigDecimal waitPayAmount = orderDetailResp.getSignContractAmount().add(orderDetailResp.getPremium());
      if (!StringUtils.isEmpty(orderDetailResp.getCouponAmount())) {
        String[] aa = orderDetailResp.getCouponAmount().split(",");
        for (String string : aa) {
          waitPayAmount = waitPayAmount.subtract(new BigDecimal(string));
        }
      }
      orderDetailResp.setWaitPayAmount(waitPayAmount);
      orderDetailResp
        .setTipInfo(StateConvert.getFrontRentStateByBackRentState(state).getCueWords());
      orderDetailResp
        .setStateDesc(StateConvert.getFrontRentStateByBackRentState(state).getMessage());
      orderDetailResp.setState(StateConvert.getFrontRentStateByBackRentState(state).getCode());
    }

    // 如果订单状态为24,"履约完成" 则需要判断当前时间 是否 大于 该订单的还机时间 ，
    // 如果大于表示租赁到期 Expires(16, "租赁到期" ，
    if (state.intValue() == BackRentState.EndPerformance.getCode()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      orderDetailResp.setTipInfo(
        String.format(FrontRentState.EndPerformance.getCueWords(),
          sdf1.format(orderDetailResp.getBackTime())));

      try {
        Long iLong = DateUtils.betweenDay(sdf.format(orderDetailResp.getBackTime()), sdf.format(new Date()));
        if (iLong.intValue() <= 0) { // 说明当前日期大于还机日期 表示租赁到期
              // 如果已经结清
              orderDetailResp.setTipInfo(FrontRentState.Expires.getCueWords());
              orderDetailResp.setStateDesc(FrontRentState.Expires.getMessage());
              orderDetailResp.setState(FrontRentState.Expires.getCode());
        }
      } catch (Exception e) {
        logger.error("转换日期异常{}", e);
      }
    }
    return ResponseResult.buildSuccessResponse(orderDetailResp);
  }

  @Override
  public ResponseResult<List<OrderDetailResp>> queryOrderStateList(OrderDetailReq orderDetailReq) {
    List<Integer> backState = StateConvert.getBackRentStateByFrontOrderState(orderDetailReq.getState());
    orderDetailReq.setBackState(backState);
    List<OrderDetailResp> list = rentRecordDao.queryOrderStateList(orderDetailReq);
    if (CollectionUtils.isNotEmpty(list)) {
      for (OrderDetailResp orderDetailResp : list) {
        int state = orderDetailResp.getState();

        if (orderDetailResp.getProductType() != null && 3 == orderDetailResp.getProductType()) {
          orderDetailResp.setTipInfo(
            StateConvert.getFrontSaleStateByBackRentState(orderDetailResp.getState()).getCueWords());
          orderDetailResp.setStateDesc(
            StateConvert.getFrontSaleStateByBackRentState(orderDetailResp.getState()).getMessage());
          orderDetailResp
            .setState(StateConvert.getFrontSaleStateByBackRentState(orderDetailResp.getState()).getCode());
          // 加上待支付到期时间
          if (orderDetailResp.getState() == BackRentState.WaitPayment.getCode()) {
            orderDetailResp
              .setWaitPayTime(DateUtils.addSecond(orderDetailResp.getApplyTime(), Integer.parseInt(cancelTime)));
          }

          // 售卖订单 查询待支付总金额= 签约价值+碎屏保障金（可选）-优惠券
          BigDecimal waitPayAmount = orderDetailResp.getSignContractAmount();
          if (orderDetailResp.getBrokenScreenBuyed()) { // 如果购买了碎屏保障
            waitPayAmount = waitPayAmount.add(orderDetailResp.getBrokenScreenAmount());
          }
          if (!StringUtils.isEmpty(orderDetailResp.getCouponAmount())) {
            String[] aa = orderDetailResp.getCouponAmount().split(",");
            for (String string : aa) {
              waitPayAmount = waitPayAmount.subtract(new BigDecimal(string));
            }
          }
          orderDetailResp.setWaitPayAmount(waitPayAmount);

        } else {

          // 租赁订单 查询待支付总金额= 签约价值+意外保障金-优惠券
          BigDecimal waitPayAmount = orderDetailResp.getSignContractAmount().add(orderDetailResp.getPremium());
          if (!StringUtils.isEmpty(orderDetailResp.getCouponAmount())) {
            String[] aa = orderDetailResp.getCouponAmount().split(",");
            for (String string : aa) {
              waitPayAmount = waitPayAmount.subtract(new BigDecimal(string));
            }
          }
          orderDetailResp.setWaitPayAmount(waitPayAmount);

          orderDetailResp.setTipInfo(
            StateConvert.getFrontRentStateByBackRentState(orderDetailResp.getState()).getCueWords());
          orderDetailResp.setStateDesc(
            StateConvert.getFrontRentStateByBackRentState(orderDetailResp.getState()).getMessage());
          orderDetailResp
          .setState(StateConvert.getFrontRentStateByBackRentState(orderDetailResp.getState()).getCode());
        }

        if (state == BackRentState.EndPerformance.getCode()) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

          SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
          orderDetailResp.setTipInfo(
            String.format(FrontRentState.EndPerformance.getCueWords(),
              sdf1.format(orderDetailResp.getBackTime())));


          try {
            Long iLong = DateUtils.betweenDay(sdf.format(orderDetailResp.getBackTime()), sdf.format(new Date()));
            if (iLong.intValue() <= 0) { // 说明当前日期大于还机日期 表示租赁到期
              // 如果已经结清
              orderDetailResp.setTipInfo(FrontRentState.Expires.getCueWords());
              orderDetailResp.setStateDesc(FrontRentState.Expires.getMessage());
              orderDetailResp.setState(FrontRentState.Expires.getCode());
            }
          } catch (Exception e) {
            logger.error("转换日期异常{}", e);
          }
        }

      }
    }
    return ResponseResult.buildSuccessResponse(list);
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

  @Override
  public ResponseResult<ResultPager<RentRecord>> queryPageRentRecord(RentRecordQuery rentRecordQuery) {
    int totalNum = rentRecordDao.countRentRecord(rentRecordQuery);
    List<RentRecord> list = new ArrayList<RentRecord>(0);
    if (totalNum > 0) {
      list = rentRecordDao.queryPageRentRecord(rentRecordQuery);
    }
    ResultPager<RentRecord> data = new ResultPager<RentRecord>(totalNum,
      rentRecordQuery.getCurrPage(),
      rentRecordQuery.getPageSize(),
      list);
    return ResponseResult.buildSuccessResponse(data);
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
  public ResponseResult<List<RentCoordinate>> queryList(RentCoordinate dto) {
    dto.setValid(true);
    List<RentCoordinate> queryList = rentCoordinateDao.queryList(dto);
    return ResponseResult.buildSuccessResponse(queryList);
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
  public ResponseResult<Map<String, Object>> lookContract(String rentRecordNo) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    Map<String, Object> map = new HashMap<>();
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
    map.put("imei", orderDetailResp.getImei() + "/" + orderDetailResp.getSnCode());
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

  @Override
  public ResponseResult<List<String>> queryRentRecordNoByImei(List<String> imeiList) {
    List<String> queryRentRecordNoByImei = rentRecordDao.queryRentRecordNoByImei(imeiList);
    return ResponseResult.buildSuccessResponse(queryRentRecordNoByImei);
  }

  @Transactional
  @Override
  public ResponseResult<String> returnIng(UpdateOrderStateReq req) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }

    // 如果是产品类型为3表示售卖的产品 则判断状态流转方法用checkBuyOrderState
    if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) {
      if (!StateConvert.checkBuyOrderState(req.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    } else {
      if (!StateConvert.checkOrderState(req.getState(), orderDetailResp.getState())) {
        return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
          OrderResultCode.STATE_FALSE.getMessage(),
          null);
      }
    }

    RentRecord rentRecord = new RentRecord();
    rentRecord.setState(req.getState());
    rentRecord.setRentRecordNo(req.getRentRecordNo());
    rentRecord.setReturnLogisticsNo(req.getReturnLogisticsNo());

    if (UpdateOrder(rentRecord) > 0) {
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

    // 退货中不需要更新物流表
    if (req.getState() != BackRentState.ReturnGoodIng.getCode()) {
      // 添加物流表发货
      RentLogistics rentLogistics = new RentLogistics();
      rentLogistics.setBusinessNo(req.getBusinessNo());
      rentLogistics.setCreateBy(req.getCreateBy());
      rentLogistics.setCreateMan(req.getCreateMan());
      rentLogistics.setCreateOn(new Date());
      rentLogistics.setLogisticsNo(req.getReturnLogisticsNo());
      rentLogistics.setState(0);
      rentLogistics.setType(1);
      rentLogistics.setRentRecordNo(req.getRentRecordNo());
      rentLogisticsService.add(rentLogistics);
    }

    // 调用仓库系统更新库位
    try {
      ApplyReturnReq applyReturnReq = new ApplyReturnReq();
      applyReturnReq.setSourceOrderNo(req.getRentRecordNo());
      applyReturnReq.setSourceOrderStatus(rentRecord.getState());
      applyReturnReq.setImieNo(orderDetailResp.getImei());
      applyReturnReq.setMaterielBasicId(Long.parseLong(orderDetailResp.getMaterielNo()));
      applyReturnReq.setReturnApplyTime(new Date());
      applyReturnReq.setSnNo(orderDetailResp.getSnCode());
      applyReturnReq.setLogisticsNo(req.getReturnLogisticsNo());
      ResponseResult<ApplyReturnReq> buyEnd = iProductService.applyReturn(applyReturnReq);
      if (buyEnd.getErrCode() != 0) {
        logger.info("returnIng 调用 applyReturn异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          buyEnd.getErrCode(),
          buyEnd.getErrMsg());
      }
    } catch (Exception e) {
      logger.info("returnIng 调用 applyReturn异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
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
      logger.error(" 下发短信失败 rentRecordNo={},templateId={},ErrMsg={},ErrCode={}",
        rentRecordNo,
        smsType.getType(),
        result.getErrMsg(),
        result.getErrCode());
    }

  }

  @Override
  public ResponseResult<ResultPager<WorkOrderRsp>> queryPageWokerOrderList(RentRecordQuery rentRecordQuery) {
    int totalNum = this.rentRecordDao.countWokerOrderList(rentRecordQuery);
    if (totalNum <= 0) {
      return ResponseResult.buildSuccessResponse(new ResultPager<WorkOrderRsp>(totalNum,
        rentRecordQuery.getCurrPage(),
        rentRecordQuery.getPageSize(),
        null));
    }
    List<WorkOrderRsp> list = this.rentRecordDao.queryPageWokerOrderList(rentRecordQuery);
    for (WorkOrderRsp workOrderRsp : list) {
      workOrderRsp.setStateDesc(BackRentState.getBackByCode(workOrderRsp.getState()).getMessage());
    }

    ResultPager<WorkOrderRsp> data = new ResultPager<WorkOrderRsp>(totalNum,
      rentRecordQuery.getCurrPage(),
      rentRecordQuery.getPageSize(),
      list);
    return ResponseResult.buildSuccessResponse(data);
  }

  @Override
  public ResponseResult<String> audit(UpdateOrderStateReq req) {
    return updateOrderState(req);
  }

  @Override
  public ResponseResult<String> updateCreditState(HashMap<String, Object> map) {
    return rentRecordService.updateCreditState(map);
  }

  @Override
  public ResponseResult<UserHistory> queryUserHistory(String rentRecordNo) {
    UserHistory query = new UserHistory();
    query.setRentRecordNo(rentRecordNo);
    List<UserHistory> list = userHistoryDao.queryList(query);
    if(list.size() != 0){
        return ResponseResult.buildSuccessResponse(list.get(0));
    }else{
        return ResponseResult.build(OrderResultCode.LOAN_NOT_FOUNT.getCode(), 
                OrderResultCode.LOAN_NOT_FOUNT.getMessage(), null);
    }
  }

  @Override
  public ResponseResult<Integer> countByapplyTime(RentRecordQuery rentRecordQuery) {
    rentRecordQuery.setNotZero(0);
    int totalNum = rentRecordDao.countWareOrderList(rentRecordQuery);
    return ResponseResult.buildSuccessResponse(totalNum);
  }

  @Override
  public ResponseResult<List<UserHistory>> queryUserHistoryList(RentRecordQuery rentRecordQuery) {
    rentRecordQuery.setNotZero(0);
    List<String> queryPageOrderNos = rentRecordDao.queryPageOrderNos(rentRecordQuery);
    logger.info("================queryUserHistoryList size={}", queryPageOrderNos.size());
    if (CollectionUtils.isNotEmpty(queryPageOrderNos)) {
      List<UserHistory> queryInfoByRentRecordNos = userHistoryDao.queryInfoByRentRecordNos(queryPageOrderNos);
      logger.info("================queryUserHistoryList userHistory size={}", queryInfoByRentRecordNos.size());
      return ResponseResult.buildSuccessResponse(queryInfoByRentRecordNos);
    }
    return ResponseResult.buildSuccessResponse(null);
  }

  @Override
  public ResponseResult<List<String>> queryOrderNoList(RentRecordQuery rentRecordQuery) {
    List<String> queryOrderNos = rentRecordDao.queryOrderNos(rentRecordQuery);
    return ResponseResult.buildSuccessResponse(queryOrderNos);
  }

  @Override
  public ResponseResult<String> updateUserHistory(UserHistory userHistory) {
    userHistoryService.update(userHistory);
    return ResponseResult.buildSuccessResponse(null);
  }

  @Override
  public ResponseResult<List<OrderDetailResp>> queryOrderList(RentRecordQuery rentRecordQuery) {
    List<OrderDetailResp> queryOrderList = rentRecordService.queryOrderList(rentRecordQuery);
    if (CollectionUtils.isNotEmpty(queryOrderList)) {
      for (OrderDetailResp orderDetailResp : queryOrderList) {
        orderDetailResp.setStateDesc(BackRentState.getBackByCode(orderDetailResp.getState()).getMessage());
      }
    }
    return ResponseResult.buildSuccessResponse(queryOrderList);
  }

  @Transactional
  @Override
  public ResponseResult<String> batchUpdateOverDue(List<String> rentRecordNos) {
    RentRecord rentRecord = null;
    for (String rentRecordNo : rentRecordNos) {
      OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(rentRecordNo);
      if (orderDetailResp == null) {
        logger.error("batchUpdateOverDue 订单不存在,rentRecord={}",rentRecordNo);
        continue;
      }
      if (!StateConvert.checkOrderState(BackRentState.Overdue.getCode(), orderDetailResp.getState())) {
        logger.error("batchUpdateOverDue 订单状态流转有误,rentRecord={}",rentRecordNo);
        continue;
      }
      rentRecord = new RentRecord();
      rentRecord.setState(BackRentState.Overdue.getCode());
      rentRecord.setRentRecordNo(rentRecordNo);
      // 更新订单表
      if (UpdateOrder(rentRecord) > 0) {
        // 更新订单成功之后添加rent_state表信息
        RentState rentState = new RentState();
        rentState.setCreateOn(new Date());
        rentState.setCreateBy(1L);
        rentState.setRentRecordNo(rentRecord.getRentRecordNo());
        rentState.setState(rentRecord.getState());
        rentState.setCreateMan("批量更新逾期");
        rentStateDao.add(rentState);
      }
    }
    return ResponseResult.buildSuccessResponse("");
  }

  @Override
  public ResponseResult<ResultPager<OrderDetailResp>> queryPageOrderForLiquation(RentRecordQuery rentRecordQuery) {
    // 设置不查询出等于0的订单
    rentRecordQuery.setNotZero(0);
    int totalNum = rentRecordDao.countWareOrderList(rentRecordQuery);
    if (totalNum <= 0) {
      return ResponseResult.buildSuccessResponse(new ResultPager<OrderDetailResp>(totalNum,
        rentRecordQuery.getCurrPage(),
        rentRecordQuery.getPageSize(),
        null));
    }
    // 默认设置按申请顺序 倒序排序
    List<OrderBy> orderByList = new ArrayList<>();
    OrderBy orderBy2 = new OrderBy();
    orderBy2.setCloumnName("rr.id");
    orderBy2.setSequence("desc");
    orderByList.add(orderBy2);
    rentRecordQuery.setOrderBy(orderByList);

    List<OrderDetailResp> list = rentRecordDao.queryPageWareOrderList(rentRecordQuery);
    if (CollectionUtils.isEmpty(list)) {
      return ResponseResult.buildSuccessResponse(new ResultPager<OrderDetailResp>(totalNum,
        rentRecordQuery.getCurrPage(),
        rentRecordQuery.getPageSize(),
        null));
    }

    if (CollectionUtils.isNotEmpty(list)) {
      for (OrderDetailResp orderDetailResp : list) {
        orderDetailResp.setStateDesc(BackRentState.getBackByCode(orderDetailResp.getState()).getMessage());
      }
    }

    ResultPager<OrderDetailResp> data = new ResultPager<OrderDetailResp>(totalNum,
      rentRecordQuery.getCurrPage(),
      rentRecordQuery.getPageSize(),
      list);
    return ResponseResult.buildSuccessResponse(data);

  }

  @Override
  public ResponseResult<OrderDetailResp> queryOrderDetailForWork(String rentRecordNo) {
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
      // 同时查询出订单物流状态
      ResultCode routeQuery = shunFengService.routeQuery(orderDetailResp.getLogisticsNo(), 1);
      if (routeQuery.getCode() == 0) {
        if (routeQuery.getData() != null) {
          List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) routeQuery.getData();
          if (CollectionUtils.isNotEmpty(list)) {
            if ("80".equals(list.get(0).get("opcode")) || "8000".equals(list.get(0).get("opcode"))) {
              orderDetailResp.setProductStateDesc("已签收");
              String acceptTime = (String) list.get(0).get("acceptTime");
              if (!StringUtils.isEmpty(acceptTime)) {
                orderDetailResp.setReceivedTime(DateUtils.getDate(acceptTime));
              }
            }
          }
        }

      } else {
        logger.error(
          "queryOrderDetail 调用shunFengService routeQuery异常 RentRecordNo={},errocode={},errormsg={}",
          orderDetailResp.getRentRecordNo(),
          routeQuery.getCode(),
          routeQuery.getMessage());
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
    if (BackRentState.ForceBuyout.getCode() == state.intValue()) {
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
    if (BackRentState.WaitPick.getCode() <= state.intValue()) {// 签约之后就能查询出账期
      // 通过订单编号查询当前的租机履约月份/总月份 (9/12) 已付滞纳金 已付租机 买断支付金
      ResponseResult<RepaymentDetailResp> repaymentDetail = iliquidationService.repaymentDetail(rentRecordNo);
      if (repaymentDetail.getErrCode() != 0) {
        logger.error(
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
    orderDetailResp.setStateDesc(BackRentState.getBackByCode(orderDetailResp.getState()).getMessage());
    return ResponseResult.buildSuccessResponse(orderDetailResp);
  }

  /**
   * 格式化手机号码，去掉+86，替换非数字字符
   * 
   * @param saveList
   */
  private String formatPhoneNum(String rawPhone) {
      if (!StringUtils.isEmpty(rawPhone)) {
        rawPhone = rawPhone.replace("+86", "");
      rawPhone = rawPhone.replaceAll("[^\\d]", "");
      }
    return rawPhone;
    }

  @Override
  public ResponseResult<ResultPager<OrderDetailResp>> queryPageReturnOrderList(RentRecordQuery rentRecordQuery) {
    List<Integer> stateList = new ArrayList<>(4);
    // 归还中”、“提前解约中”、“强制归还中”、“退货中 (16,11,30, 33 )
    stateList.add(BackRentState.Recycling.getCode());
    stateList.add(BackRentState.PrematureTerminating.getCode());
    stateList.add(BackRentState.ForceRecycleIng.getCode());
    stateList.add(BackRentState.ReturnGoodIng.getCode());
    rentRecordQuery.setStateList(stateList);
    int totalNum = rentRecordDao.countReturnOrderList(rentRecordQuery);
    List<OrderDetailResp> list = new ArrayList<OrderDetailResp>(0);
    if (totalNum > 0) {
      list = rentRecordDao.queryReturnOrderList(rentRecordQuery);
    }
    ResultPager<OrderDetailResp> data = new ResultPager<OrderDetailResp>(totalNum,
      rentRecordQuery.getCurrPage(),
      rentRecordQuery.getPageSize(),
      list);
    return ResponseResult.buildSuccessResponse(data);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void SfStatusChangeService() {
    // 1.查询出物流表中物流类型为0我司发货 物流状态为 0发货中的 数据
    RentLogistics dto = new RentLogistics();
    dto.setState(0);
    dto.setType(0);
    List<RentLogistics> queryList = rentLogisticsService.queryList(dto);
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
                OrderDetailResp orderDetailResp = rentRecordDao
                  .getOrderDetailByRentRecordNo(rentLogistics.getRentRecordNo());

                UpdateOrderStateReq req = new UpdateOrderStateReq();
                req.setRentRecordNo(rentLogistics.getRentRecordNo());
                req.setCreateBy(1L);
                req.setCreateMan("system");
                logger.info("SfStatusChangeService  RentRecordNo={}", orderDetailResp.getRentRecordNo());
                if (orderDetailResp.getProductType() != null && orderDetailResp.getProductType() == 3) { // 出售
                  // 如果是出售 需要把订单状态改为 已买断
                  req.setState(BackRentState.NormalBuyout.getCode());
                  this.buyOut(req);
                } else {
                  // 租赁和以租代购把订单状态改为正常履约中
                  req.setState(BackRentState.NormalPerformance.getCode());
                  this.signedOrder(req);
                }
              }
            }
          }
        } else {
          XxlJobLogger.log("SfStatusJobHandler 调用shunFengService routeQuery异常 RentRecordNo={}"
                           + rentLogistics.getRentRecordNo() + ",errocode={}" +
                           routeQuery.getCode() + "errormsg={}" + routeQuery.getMessage());
        }

      }
    }

  }

  @Transactional
  @Override
  public ResponseResult<String> addSaleOrder(SaleOrderReq saleOrderReq) {
    // 1.先通过用户id查询订单表是否有状态为待支付的订单，如果有拒绝提交
    Long userId = saleOrderReq.getUserId();
    RentRecord queryRentRecord = new RentRecord();
    queryRentRecord.setUserId(userId);
    queryRentRecord.setState(BackRentState.WaitPayment.getCode());
    queryRentRecord.setProductType(3);
    int queryCount = rentRecordDao.queryCount(queryRentRecord);
    if (queryCount > 0) {
      return ResponseResult.build(OrderResultCode.PAY_WAIT.getCode(),
        OrderResultCode.PAY_WAIT.getMessage(),
        null);
    }
    // 2.通过产品编号去远程调用查询产品信息用于添加订单扩展表信息
    ProductInfo pInforeq = new ProductInfo();
    pInforeq.setProductNo(saleOrderReq.getProductNo());
    ResponseResult<ProductInfo> byIdOrPdtNo = iProductService.getByIdOrPdtNo(pInforeq);
    if (byIdOrPdtNo.getErrCode() != 0) {
      return ResponseResult.build(byIdOrPdtNo.getErrCode(), byIdOrPdtNo.getErrMsg(), null);
    }
    // 3.判断远程获取的产品信息是否已经下架，如果下架返回通知前端产品已经下架不可下单
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

    if (pInfo.getProductType() == null || pInfo.getProductType() != 3) {// 如果产品类型不为售卖类型则不能下单
      return ResponseResult.build(OrderResultCode.PRODUCT_NOTSUITABLE.getCode(),
        OrderResultCode.PRODUCT_NOTSUITABLE.getMessage(),
        null);
    }
    // 5.添加订单表
    queryRentRecord.setState(0);
    queryRentRecord.setApplyTime(new Date());
    queryRentRecord.setCreateOn(new Date());
    queryRentRecord.setSnCode(saleOrderReq.getSnCode());
    queryRentRecord.setImei(saleOrderReq.getImei());
    queryRentRecord.setApplyInvoice(saleOrderReq.getApplyInvoice());
    queryRentRecord.setChannelNo(saleOrderReq.getChannelNo());
    queryRentRecord.setChannelType(saleOrderReq.getChannelType());
    queryRentRecord.setProductType(pInfo.getProductType());
    queryRentRecord.setCouponId(saleOrderReq.getCouponId());
    queryRentRecord.setCouponAmount(saleOrderReq.getCouponAmount());
    // 5.1 获取订单主键id
    rentRecordDao.add(queryRentRecord);
    Long id = queryRentRecord.getId();
    String rentRecordNo = UnqieCodeGenerator.getCode("SO", id);

    // 4.通过imei 和 sncode 去查询当前产品是否还有可售库存
    SigningQuery qsing = new SigningQuery();
    qsing.setProductId(pInfo.getId());
    qsing.setMaterielBasicId(pInfo.getMaterielId());
    qsing.setSourceOrderNo(rentRecordNo);
    qsing.setImieNo(saleOrderReq.getImei());
    qsing.setSnNo(saleOrderReq.getSnCode());
    ResponseResult<SigningResult> signing = iProductService.signing(qsing);
    logger.info("addSaleOrder 查询库存,rentRecordNo={}, 响应信息={}", rentRecordNo, JsonUtils.toJsonString(signing));
    if (signing.getErrCode() != 0) {
      logger.info("addSaleOrder 查询库存,rentRecordNo={},getErrCode={},getErrMsg={}",
        rentRecordNo,
        signing.getErrCode(),
        signing.getErrMsg());
    }

    // 5.2.添加扩展表 取到扩展表自增主键ID
    RentRecordExtends recordExtends = convetProductInfo(pInfo);
    recordExtends.setRentRecordNo(rentRecordNo);
    recordExtends.setUserId(userId);
    recordExtends.setPhoneNum(formatPhoneNum(saleOrderReq.getPhoneNum()));
    recordExtends.setRealName(saleOrderReq.getRealName());
    recordExtends.setIdNo(saleOrderReq.getIdNo());
    recordExtends.setProv(saleOrderReq.getProv());
    recordExtends.setCity(saleOrderReq.getCity());
    recordExtends.setArea(saleOrderReq.getArea());
    recordExtends.setAddress(saleOrderReq.getAddress());
    recordExtends.setBrokenScreenBuyed(saleOrderReq.getBrokenScreenBuyed());
    rentRecordExtendsDao.add(recordExtends);
    Long extId = recordExtends.getId();

    // 6.将订单编号,扩展表id 更新到订单表
    RentRecord uprentRecord = new RentRecord();
    uprentRecord.setExtendId(extId);
    uprentRecord.setRentRecordNo(rentRecordNo);
    if (signing.getErrCode() == 0) {
      uprentRecord.setState(BackRentState.WaitPayment.getCode());
    }

    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(uprentRecord);

    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setId(id);
    updateDto.setUpdateWhere(whereCloumn);
    rentRecordDao.update(updateDto);
    // 7.添加历史用户信息表
    UserHistory userHistory = saleOrderReq.getUserHistory();
    userHistory.setRentRecordNo(rentRecordNo);
    userHistory.setUserId(userId);
    userHistoryDao.add(userHistory);
    // 8.添加rent_state表信息
    if (signing.getErrCode() == 0) {
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(userId);
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(BackRentState.WaitPayment.getCode());
    rentState.setCreateMan(saleOrderReq.getRealName());
    rentStateDao.add(rentState);
    }
    // 9.如果申请了开票还需要添加订单发票信息表
    if (saleOrderReq.getApplyInvoice()) {
      saleOrderReq.getRentInvoice().setRentRecordNo(rentRecordNo);
      rentInvoiceDao.add(saleOrderReq.getRentInvoice());
    }

    // 11.返回订单编号
    if (signing.getErrCode() != 0) {
      return ResponseResult.build(OrderResultCode.NO_STOCK.getCode(),
        OrderResultCode.NO_STOCK.getMessage(),
        null);
    }
    // 10.存入redis中
    Map<String, String> map = new HashMap<>();
    map.put(rentRecordNo, String.valueOf(System.currentTimeMillis()));
    boolean hsetwaitPayOrder = orderCacheService.hsetwaitPayOrder(map);
    if (!hsetwaitPayOrder) {
      logger.error("待支付订单塞入redis失败,{}", rentRecordNo);
    }
      return ResponseResult.buildSuccessResponse(rentRecordNo);
  }

  @Transactional
  @Override
  public ResponseResult<String> paySuccess(UpdateOrderStateReq req) {
    OrderDetailResp orderDetailResp = rentRecordDao.getOrderDetailByRentRecordNo(req.getRentRecordNo());
    if (orderDetailResp == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (orderDetailResp.getProductType() != 3) {
      return ResponseResult.build(OrderResultCode.PRODUCT_NOTSUITABLE.getCode(),
        OrderResultCode.PRODUCT_NOTSUITABLE.getMessage(),
        null);
    }
    if (orderDetailResp.getState() != BackRentState.WaitPayment.getCode()) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }

    RentRecord rentRecord = new RentRecord();
    rentRecord.setState(BackRentState.WaitPick.getCode());
    rentRecord.setRentRecordNo(req.getRentRecordNo());
    rentRecord.setPayTime(new Date());
    BigDecimal salePayAmount = req.getFee();
    if (null != req.getFeeTwo()) {
      salePayAmount = salePayAmount.add(req.getFeeTwo());
    }
    rentRecord.setSalePayAmount(salePayAmount);
    if (UpdateOrder(rentRecord) > 0) {
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

    // 更新发票信息表的金额
    if (req.getFee() != null) {
      RentInvoice rentInvoice = new RentInvoice();
      rentInvoice.setFee(req.getFee());
      rentInvoice.setFeeTwo(req.getFeeTwo());
      rentInvoice.setRentRecordNo(req.getRentRecordNo());
      rentInvoiceService.update(rentInvoice);
    }

    Long hdelwaitPayOrder = orderCacheService.hdelwaitPayOrder(req.getRentRecordNo());
    if (hdelwaitPayOrder == 0) {
      logger.error("paySuccess 方法中取消订单清除redis缓存失败,{}", req.getRentRecordNo());
    }
    return ResponseResult.buildSuccessResponse("");
  }

  @Override
  public void checkWatiPaymentJob() {
    // 1.先远程调用运营系统获取订单取消配置的时间间隔 单位为分钟
    int timeIntreval = Integer.parseInt(cancelTime); // 暂时从配置文件取之后从运营系统取
    long addTime = timeIntreval * 60 * 1000;
    long nowTime = System.currentTimeMillis();
    UpdateOrderStateReq updateOrderStateReq = null;
    // 2.从redis中取出当前所有的未支付的map
    Map<String, String> hgetAllwaitPayOrder = orderCacheService.hgetAllwaitPayOrder();
    if (hgetAllwaitPayOrder != null && !hgetAllwaitPayOrder.isEmpty()) {
      for (Map.Entry<String, String> m : hgetAllwaitPayOrder.entrySet()) {
        if (!StringUtils.isEmpty(m.getValue())) {
          logger.info("定时任务批量取消订单，订单编号====={}", m.getKey());
          Long startTimestr = Long.parseLong(m.getValue());
          // 如果超过15分钟则将该订单修改为取消
          long compaTime = nowTime - startTimestr;
          if (compaTime >= addTime) {
            if (!StringUtils.isEmpty(m.getKey())) {
              updateOrderStateReq = new UpdateOrderStateReq();
              updateOrderStateReq.setCreateBy(0L);
              updateOrderStateReq.setCreateMan("system");
              updateOrderStateReq.setRemark("定时任务批量取消订单");
              updateOrderStateReq.setState(BackRentState.Cancel.getCode());
              updateOrderStateReq.setRentRecordNo(m.getKey());
              this.updateOrderState(updateOrderStateReq);
            }
          }
        }
      }
    }
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
    if (!StateConvert.checkOrderState(state, orderDetailResp.getState())) {
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
    UpdateOrder(rentRecord);
    // 2添加订单状态表
    RentState rentState = new RentState();
    rentState.setCreateOn(new Date());
    rentState.setCreateBy(req.getCreateBy());
    rentState.setRentRecordNo(rentRecordNo);
    rentState.setState(state);
    rentState.setCreateMan(req.getCreateMan());
    rentStateDao.add(rentState);

    // 3更新物流表状态为已签收
    if (!StringUtils.isEmpty(orderDetailResp.getLogisticsNo())) {
      RentLogistics rentLogistics = new RentLogistics();
      rentLogistics.setState(1);
      UpdateDto<RentLogistics> updateDto = new UpdateDto<RentLogistics>();
      updateDto.setUpdateCloumn(rentLogistics);
      RentLogistics whereCloumn = new RentLogistics();
      whereCloumn.setLogisticsNo(orderDetailResp.getLogisticsNo());
      updateDto.setUpdateWhere(whereCloumn);
      rentLogisticsDao.update(updateDto);
    }
    try {
      // 通知库存系统 ，由“出库在途”变更为“在租”；
      RentingReq rentingReq = new RentingReq();
      rentingReq.setImieNo(orderDetailResp.getImei());
      rentingReq.setMaterielBasicId(Long.parseLong(orderDetailResp.getMaterielNo()));
      rentingReq.setSnNo(orderDetailResp.getSnCode());
      rentingReq.setSourceOrderNo(orderDetailResp.getRentRecordNo());
      ResponseResult<RentingReq> signFailed = iProductService.renting(rentingReq);
      if (signFailed.getErrCode() != 0) {
        logger.error("signedOrder 通知库存系统更新库位失败,rentRecordNo={},getErrCode={},getErrMsg={}",
          req.getRentRecordNo(),
          signFailed.getErrCode(),
          signFailed.getErrMsg());
      }
    } catch (Exception e) {
      logger.error("signedOrder 调用renting通知库存系统更新库位异常 RentRecordNo={},e={}", orderDetailResp.getRentRecordNo(), e);
    }
    return ResponseResult.buildSuccessResponse();
  }

  @Override
  public ResponseResult<ResultPager<QueryInvoiceRsp>> queryPageInvoice(QueryInvoiceReq queryInvoiceReq) {
    int totalNum = this.rentRecordDao.countInvoice(queryInvoiceReq);
    List<QueryInvoiceRsp> list = new ArrayList<QueryInvoiceRsp>(0);
    if (totalNum > 0) {
      list = this.rentRecordDao.queryPageInvoice(queryInvoiceReq);
    }
    ResultPager<QueryInvoiceRsp> data = new ResultPager<QueryInvoiceRsp>(totalNum,
      queryInvoiceReq.getCurrPage(),
      queryInvoiceReq.getPageSize(),
      list);

    return ResponseResult.buildSuccessResponse(data);
  }

  @Override
  public ResponseResult<String> invoiceEnd(String rentRecordNo) {
    RentRecord rentRecord = new RentRecord();
    rentRecord.setRentRecordNo(rentRecordNo);
    rentRecord.setInvoiceEnd(true);
    UpdateOrder(rentRecord);
    return ResponseResult.buildSuccessResponse(null);
  }

}
