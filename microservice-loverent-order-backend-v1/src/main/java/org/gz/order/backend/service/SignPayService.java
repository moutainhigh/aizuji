package org.gz.order.backend.service;



import org.gz.cache.service.order.OrderCacheService;
import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.SaleOrderRepaymentInfoResp;
import org.gz.order.backend.feign.IliquidationService;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.RentInvoice;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentState;
import org.gz.order.server.service.RentInvoiceService;
import org.gz.order.server.service.RentRecordExtendsService;
import org.gz.order.server.service.RentRecordService;
import org.gz.sms.service.SmsSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignPayService {

	Logger logger = LoggerFactory.getLogger(SignPayService.class);
	@Autowired
	RentRecordService rentRecordService;
	@Autowired
	IliquidationService iliquidationService;
	@Autowired
	SmsSendService smsSendService;
	@Autowired
	RentRecordExtendsService rentRecordExtendsService;
	@Autowired
	DistributeService distributeService;
	@Autowired
	OrderCacheService orderCacheService;
	@Autowired
	RentInvoiceService rentInvoiceService;

	@Transactional
	public ResponseResult<?> pay(RentRecord rentRecord,AuthUser authUser) {
	    RentRecord rr= rentRecordService.getByRentRecordNo(rentRecord.getRentRecordNo());
	    //租赁 以租代购 验证清算接口 更新前需判断订单是否存在首期款成功支付的流水.并校验支付金额是否为足额首期款.   如果不存在支付流水或支付金额小于首期款金额则不予完成更新.
	    if (rr.getProductType()==1||rr.getProductType()==2) {
		    ResponseResult<Boolean> result = iliquidationService.queryDownPayment(rentRecord.getRentRecordNo());
			if (!result.isSuccess()) 
				return result;
		    if (!result.getData())
		      return ResponseResult.buildSuccessResponse("还没有首期款成功支付流水");
	    }
		//更新订单状态为待签约 
		UpdateOrderStateReq req=new UpdateOrderStateReq();
		req.setRentRecordNo(rentRecord.getRentRecordNo());
		if (rr.getProductType()==3) {
			req.setState(BackRentState.WaitPick.getCode());
		}else{
			req.setState(BackRentState.WaitSignup.getCode());
		}
		req.setCreateBy(authUser.getId());
		req.setCreateMan(authUser.getUserName());
		req.setProductType(rr.getProductType());
		distributeService.updateOrderState(req);
		//更新发票信息
		ResponseResult<SaleOrderRepaymentInfoResp> sorirResult= iliquidationService.querySaleOrderInfo(rr.getRentRecordNo());
		if (sorirResult.getErrCode() != 0) {
	          logger.error("updateOrderState 完成支付更新发票信息,rentRecordNo={},getErrCode={},getErrMsg={}",
	            req.getRentRecordNo(),
	            sorirResult.getErrCode(),
	            sorirResult.getErrMsg());
	    }else {
	    	SaleOrderRepaymentInfoResp sorir=sorirResult.getData();
			RentInvoice rentInvoice=new RentInvoice();
			rentInvoice.setFee(sorir.getSalePrice());
			rentInvoice.setFeeTwo(sorir.getInsurance());
			rentInvoice.setRentRecordNo(rr.getRentRecordNo());
			rentInvoiceService.update(rentInvoice);
		}
		
		//清除订单缓存
		Long hdelwaitPayOrder= orderCacheService.hdelwaitPayOrder(rr.getRentRecordNo());
		if (hdelwaitPayOrder==0) {
			logger.error("订单{}完成支付清除缓存失败",rentRecord.getRentRecordNo());
		}
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	public ResponseResult<?> cancel(RentState rentState,AuthUser authUser) {
		//更新订单状态为已取消 
		UpdateOrderStateReq req=new UpdateOrderStateReq();
		req.setRentRecordNo(rentState.getRentRecordNo());
		req.setState(BackRentState.Cancel.getCode());
		req.setCreateBy(authUser.getId());
		req.setCreateMan(authUser.getUserName());
		return distributeService.updateOrderState(req);
	}
   
}