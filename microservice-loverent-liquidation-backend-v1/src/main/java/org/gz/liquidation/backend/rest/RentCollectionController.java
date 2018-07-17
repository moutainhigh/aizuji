package org.gz.liquidation.backend.rest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.gz.common.entity.AuthUser;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.IRentCollectionService;
import org.gz.liquidation.backend.feign.IRepaymentScheduleService;
import org.gz.liquidation.backend.feign.ITransactionRecordService;
import org.gz.liquidation.common.dto.AccountRecordReq;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.AfterRentOrderDetailReq;
import org.gz.liquidation.common.dto.AfterRentOrderDetailResp;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.OrderRepaymentResp;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.liquidation.common.dto.RevenueStatisticsResp;
import org.gz.liquidation.common.dto.TransactionRecordQueryReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:租后控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月11日 	Created
 */
@Slf4j
@RestController
@RequestMapping("/rentCollection")
public class RentCollectionController extends BaseController {

	@Autowired
	private IRentCollectionService rentCollectionService;
	
	@Autowired
	private IRepaymentScheduleService repaymentScheduleService;
	
	@Resource
	private ITransactionRecordService iTransactionRecordService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	
	/**
	 * 
	 * @Description: 清算记录分页查询
	 * @param accountRecordReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月11日
	 */
	@PostMapping(value = "/chargeOff/page", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> chargeOffList(AccountRecordReq accountRecordReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"chargeOffList:{}",JsonUtils.toJsonString(accountRecordReq));
		
		return rentCollectionService.chargeOffList(accountRecordReq);
		
	}
	
	/**
	 * 
	 * @Description: 收款管理->租后清算记录 （清算总览）
	 * @param type ：all 统计所有 today 今日
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	@PostMapping(value = "/revenueStatistics/{type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RevenueStatisticsResp> queryRevenueStatistics(@PathVariable("type")String type){
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryRevenueStatistics:{}",JsonUtils.toJsonString(type));
		
		return rentCollectionService.queryRevenueStatistics(type);
	}
	/**
	 * 
	 * @Description: 收款管理->租后收款->订单详情
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	@PostMapping(value = "/queryOrderDetail/{orderSN}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderRepaymentResp> queryOrderDetail(@PathVariable("orderSN") String orderSN){
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryOrderDetail orderSN:{}",orderSN);
		return rentCollectionService.queryOrderDetail(orderSN);
	}
	
	/**
	 * 
	 * @Description: 租后收款->订单详情->订单还款明细查询
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	@PostMapping(value = "/repmayment/detail/{orderSN}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryRepmaymentDetail(@PathVariable("orderSN") String orderSN){
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryRepmaymentDetail orderSN:{}",orderSN);
		ResponseResult<List<AccountRecordResp>> responseResult = rentCollectionService.queryRepmaymentDetail(orderSN);
		ResultPager<AccountRecordResp> rr = new ResultPager<>(responseResult.getData().size(), 1, responseResult.getData().size(), responseResult.getData());
		
		return ResponseResult.buildSuccessResponse(rr);
	}
	/**
	 * 
	 * @Description: 收款管理->租后收款（分页查询租后订单列表）
	 * @param afterRentOrderDetailReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/order/pageList")
	public ResponseResult<?> queryOrderList(AfterRentOrderDetailReq afterRentOrderDetailReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryOrderList afterRentOrderDetailReq:{}",afterRentOrderDetailReq);
		ResultPager<AfterRentOrderDetailResp> resultPager = rentCollectionService.queryOrderList(afterRentOrderDetailReq);
		
		return ResponseResult.buildSuccessResponse(resultPager);
	}
	/**
	 * 
	 * @Description: 手动清偿
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/manualSettle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> manualSettle(@Valid ManualSettleReq manualSettleReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"manualSettle manualSettleReq:{}",JsonUtils.toJsonString(manualSettleReq));
		//AuthUser admin = getAuthUser();
		manualSettleReq.setCreateBy(1L);
		manualSettleReq.setOperatorRealname("后台管理人员");
		
		return rentCollectionService.manualSettle(manualSettleReq);
		
	}
	/**
	 * 
	 * @Description: 减免滞纳金接口
	 * @param lateFeeRemissionReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月13日
	 */
	@PostMapping(value = "/lateFee/remission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"doLateFeeRemission lateFeeRemissionReq:{}",JsonUtils.toJsonString(lateFeeRemissionReq));
		//AuthUser user =  getAuthUser();
		lateFeeRemissionReq.setCreateBy(1L);
		// TODO 权限未完善，暂时写死
		lateFeeRemissionReq.setCreateByRealname("后台业务人员");
		
		if(StringUtils.isNotBlank(lateFeeRemissionReq.getRemissionDesc()) && lateFeeRemissionReq.getRemissionDesc().length()>128){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),"减免原因描述不能超过128个字符", null);
		}
		
		try {
			return rentCollectionService.doLateFeeRemission(lateFeeRemissionReq);
		} catch (Exception e) {
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), e.getMessage(), null);
		}
	}
	
	/**
	 * 
	 * @Description: 查询租赁计划
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月2日
	 */
	@PostMapping(value = "/rentSchedule/{orderSN}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> rentSchedule(@PathVariable("orderSN") String orderSN){
		
		log.info(LiquidationConstant.LOG_PREFIX+"rentSchedule orderSN:{}",orderSN);
		ResponseResult<List<RepaymentScheduleResp>> responseResult = repaymentScheduleService.rentRepaymentSchedule(orderSN);
		ResultPager<RepaymentScheduleResp> rr = new ResultPager<>(responseResult.getData().size(), 1, responseResult.getData().size(), responseResult.getData());
		
		return ResponseResult.buildSuccessResponse(rr);
	}
	/**
	 * 
	 * @Description: 查询已还滞纳金和未还滞纳金
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月5日
	 */
	@PostMapping(value = "/lateFees/detail/{orderSN}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> selectLateFees(@PathVariable("orderSN") String orderSN){
		
		log.info(LiquidationConstant.LOG_PREFIX+"selectLateFees orderSN:{}",orderSN);
		ResponseResult<Map<String,BigDecimal>> responseResult = repaymentScheduleService.selectLateFees(orderSN);
		log.info(LiquidationConstant.LOG_PREFIX+"selectLateFees responseResult:{}",JsonUtils.toJsonString(responseResult));
		
		return responseResult;
		
	}
	/**
	 * 
	 * @Description: 交易信息统计
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月22日
	 */
	@PostMapping(value = "/transactionRecord/statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryStatistics(TransactionRecordQueryReq req){
		
		log.info("******************************* queryStatistics :{}",JsonUtils.toJsonString(req));
		return iTransactionRecordService.queryStatistics(req);
		
	}
}
