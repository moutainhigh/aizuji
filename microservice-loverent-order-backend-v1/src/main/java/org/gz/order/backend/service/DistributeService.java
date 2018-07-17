package org.gz.order.backend.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gz.cache.service.order.OrderCacheService;
import org.gz.common.constants.SmsType;
import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.liquidation.common.dto.coupon.CouponReturnReq;
import org.gz.order.backend.feign.IMaterielService;
import org.gz.order.backend.feign.IShunFengService;
import org.gz.order.backend.feign.IliquidationService;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.Enum.OrderResultCode;
import org.gz.order.common.dto.LogisticsNodes;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentLogisticsDto;
import org.gz.order.common.dto.RentLogisticsDtoPage;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.RentLogistics;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.RentState;
import org.gz.order.common.utils.StateConvert;
import org.gz.order.server.dao.RentLogisticsDao;
import org.gz.order.server.dao.RentRecordDao;
import org.gz.order.server.dao.RentRecordExtendsDao;
import org.gz.order.server.dao.RentStateDao;
import org.gz.order.server.service.RentLogisticsService;
import org.gz.order.server.service.RentRecordExtendsService;
import org.gz.order.server.service.RentRecordService;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.service.SmsSendService;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.common.vo.WarehousePickQuery;
import org.gz.warehouse.common.vo.WarehousePickResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
public class DistributeService {

	Logger logger = LoggerFactory.getLogger(DistributeService.class);
	@Autowired
	RentRecordService rentRecordService;
	@Autowired
	RentRecordExtendsService rentRecordExtendsService;
	@Autowired
    IShunFengService shunFengService;
	@Autowired
	IMaterielService materielService;
	@Autowired
	IliquidationService iliquidationService;
	@Autowired
	SmsSendService smsSendService;
	@Autowired
	RentLogisticsService rentLogisticsService;
	@Autowired
	RentRecordDao rentRecordDao;
	@Autowired
	RentRecordExtendsDao rentRecordExtendsDao;
	@Autowired
	RentStateDao rentStateDao;
	@Autowired
	RentLogisticsDao rentLogisticsDao;
	@Resource
	private IMaterielService iProductService;
	@Resource
	private OrderCacheService orderCacheService;
	
	
	@Transactional
	public ResponseResult<?> inventoryupdate(RentRecord rentRecord) {
		RentRecordExtends extends1= rentRecordExtendsService.getByRentRecordNo(rentRecord.getRentRecordNo());
		//库存接口 获取商品库存情况
		WarehouseMaterielCountQuery q=new WarehouseMaterielCountQuery();
		q.setMaterielBasicId(Long.valueOf(extends1.getMaterielNo()));
		ResponseResult<WarehouseMaterielCount> result=  materielService.queryWarehouseMaterielCount(q);
		logger.info("DistributeService inventoryupdate : {}",JSON.toJSONString(result));
		if (!result.isSuccess()) 
			return result;
		//更新订单表库存情况
		if (null!=result.getData()) {
			if (result.getData().getMaterielCount().intValue()>0) 
				rentRecord.setStockFlag(1);
			else if (result.getData().getMaterielCount().intValue()==0) {
				rentRecord.setStockFlag(0);
			}else {
				rentRecord.setStockFlag(2);
			}
		}
		rentRecordService.updateStockFlag(rentRecord);
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	public ResponseResult<?> signnotice(RentRecord rentRecord,AuthUser authUser) {
		RentRecordExtends extends1= rentRecordExtendsService.getByRentRecordNo(rentRecord.getRentRecordNo());
		//调用接口发送签约通知短信
		SmsType smsType = SmsType.STOCK_SIGN_INFORM;
		SmsDto dto = new SmsDto();
		dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
		dto.setPhone(extends1.getPhoneNum());
		dto.setSmsType(smsType.getType());
		dto.getDatas().add(extends1.getMaterielModelName());
		ResponseResult<String> result= smsSendService.sendSmsStockSignInform(dto);
		if (!result.isSuccess()) 
			return result;
		//订单状态由待配货更新为待签约
		UpdateOrderStateReq req=new UpdateOrderStateReq();
		req.setRentRecordNo(rentRecord.getRentRecordNo());
		req.setState(BackRentState.WaitSignup.getCode());
		req.setCreateBy(authUser.getId());
		req.setCreateMan(authUser.getUserName());
		updateOrderState(req);
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	public ResponseResult<?> updatestate(RentRecord rentRecord,AuthUser authUser) {
		WarehousePickQuery q=new WarehousePickQuery();
		q.setSourceOrderNo(rentRecord.getRentRecordNo());
		//库存接口 查询配货状态
		ResponseResult<WarehousePickResult> result= materielService.pickQuery(q);
		if (!result.isSuccess()) 
			return result;
		//更新订单配货状态
		UpdateOrderStateReq req=new UpdateOrderStateReq();
		req.setRentRecordNo(rentRecord.getRentRecordNo());
		req.setState(result.getData().getStatusFlag());
		req.setCreateBy(authUser.getId());
		req.setCreateMan(authUser.getUserName());
		updateOrderState(req);
		return ResponseResult.buildSuccessResponse();
	}

	public ResponseResult<RentLogisticsDtoPage> logistics(RentRecord rentRecord) {
		if (null!=rentRecord) {
			RentLogistics r=new RentLogistics();
			r.setRentRecordNo(rentRecord.getRentRecordNo());
			List<RentLogistics> list= rentLogisticsService.queryList(r);
			List<RentLogisticsDto> dtolist= BeanConvertUtil.convertBeanList(list, RentLogisticsDto.class);
			for (RentLogisticsDto rentLogisticsDto : dtolist) {
				//查询物流信息
				List<LogisticsNodes> logisticsNodes= (List<LogisticsNodes>) shunFengService.routeQuery(rentLogisticsDto.getLogisticsNo(), 1).getData();
				rentLogisticsDto.setLogisticsNodes(logisticsNodes);
			}
			RentLogisticsDtoPage rldp=new RentLogisticsDtoPage();
			rldp.setData(dtolist);
			return ResponseResult.buildSuccessResponse(rldp);
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	public ResponseResult<?> terminationapply(RentRecord rentRecord,AuthUser authUser) {
		//TODO 库存接口 通知库存订单申请提前解约
		
		//更新订单状态为提前解约中 添加状态记录
		UpdateOrderStateReq req=new UpdateOrderStateReq();
		req.setRentRecordNo(rentRecord.getRentRecordNo());
		req.setState(BackRentState.PrematureTerminating.getCode());
		req.setCreateBy(authUser.getId());
		req.setCreateMan(authUser.getUserName());
		return updateOrderState(req);
	}

	@Transactional
	public ResponseResult<?> confirmreceive(RentRecord rentRecord,AuthUser authUser) {
//		RentRecordExtends extends1= rentRecordExtendsService.getByRentRecordNo(rentRecord.getRentRecordNo());
		RentRecord record= rentRecordService.getByRentRecordNo(rentRecord.getRentRecordNo());
//		//库存接口 通知库存订单确认收货
//		ConfirmSignVO q=new ConfirmSignVO();
//		q.setSourceOrderNo(rentRecord.getRentRecordNo());
//		q.setMaterielBasicId(Long.valueOf(extends1.getMaterielNo()));
//		q.setSnNo(record.getSnCode());
//		q.setImieNo(record.getImei());
//		ResponseResult<ConfirmSignVO> result= materielService.confirmSign(q);
//		if (!result.isSuccess()) 
//			return result;
//		  
		UpdateOrderStateReq req=new UpdateOrderStateReq();
		req.setCreateBy(authUser.getId());
		req.setCreateMan(authUser.getUserName());
		req.setRentRecordNo(rentRecord.getRentRecordNo());
		if (record.getProductType()==3) { //售卖订单更新状态为已买断
			req.setState(BackRentState.NormalBuyout.getCode());
			buyOut(req);
		}else { //租赁订单更新状态为正常履约中
			req.setState(BackRentState.NormalPerformance.getCode());
			signedOrder(req);
		}
		return ResponseResult.buildSuccessResponse();
	}
	
	/**
	 * 租赁和以租代购订单确认签收调用接口
	 * @param req
	 * @return
	 */
	@Transactional
	private ResponseResult<String> signedOrder(UpdateOrderStateReq req) {
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
	
	/** 更新买断状态 EarlyBuyout(17,"提前买断"), NormalBuyout(19,"正常买断") ,ForceBuyout(26,
			   * "强制买断") ;
			   */
	private ResponseResult<String> buyOut(UpdateOrderStateReq req) {

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
	
	private int UpdateOrder(RentRecord rentRecord) {
	    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
	    updateDto.setUpdateCloumn(rentRecord);
	    RentRecord whereCloumn = new RentRecord();
	    whereCloumn.setRentRecordNo(rentRecord.getRentRecordNo());
	    updateDto.setUpdateWhere(whereCloumn);
	    int i = rentRecordDao.update(updateDto);
	    return i;
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
	
	@Transactional
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
   
}