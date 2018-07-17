package org.gz.liquidation.web.controller.repayment;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.BuyoutResp;
import org.gz.liquidation.common.dto.DuesDetailResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.liquidation.common.dto.RepaymentStatisticsReq;
import org.gz.liquidation.common.dto.SettleDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmStatementDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleResp;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.repayment.RepaymentStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	还款控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月2日 	Created
 */
@RestController
@RequestMapping("/repayment")
@Slf4j
public class RepaymentController extends BaseController{
	@Autowired
	private RepaymentScheduleService repaymentScheduleService;
	
	@Autowired
	private RepaymentStatisticsService repaymentStatisticsService;
	
	@Autowired
	private Validator validator;
	
	@Resource
	private RepaymentService repaymentService;
	
	/**
	 * 
	 * @Description: 根据订单号集合查询履约次数、违约次数
	 * @param repaymentStatisticsReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	@ApiOperation(value = "根据订单号集合查询履约次数、违约次数", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/repaymentStatistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryRepaymentStatistics(@RequestBody RepaymentStatisticsReq repaymentStatisticsReq, BindingResult bindingResult){
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return repaymentStatisticsService.queryList(repaymentStatisticsReq.getOrderSN());
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		}
		return validateResult;
		
	}
	/**
	 * 
	 * @Description: 订单是否结清
	 * @param orderSN
	 * @return true 是 false 否
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	@PostMapping(value = "/{orderSN}/state", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Boolean> repaymentState(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		boolean flag = repaymentService.queryOrderIsSettled(orderSN);
		return ResponseResult.buildSuccessResponse(flag);
		
	}
	
	/**
	 * 
	 * @Description: 查询订单还款详情(订单服务调用)
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	@ApiOperation(value = "查询订单还款详情", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/{orderSN}/detail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RepaymentDetailResp> repaymentDetail(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		ResponseResult<RepaymentDetailResp> responseResult = repaymentScheduleService.queryRepaymentDetail(orderSN);
		return responseResult;
		
	}
	/**
	 * 
	 * @Description: 根据订单查询还款计划(只需查询还款类型是租金的)
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月3日
	 */
	@ApiOperation(value = "查询还款计划", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/repaymentSchedule/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RepaymentScheduleResp>> repaymentSchedule(@PathVariable("orderSN") String orderSN){
		
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		
		List<RepaymentSchedule> sourceList = repaymentScheduleService.selectList(repaymentSchedule);
		List<RepaymentScheduleResp> list = BeanConvertUtil.convertBeanList(sourceList, RepaymentScheduleResp.class);
		
		ReadyBuyoutReq readyBuyoutReq = new ReadyBuyoutReq();
		readyBuyoutReq.setOrderSN(orderSN);
		// 查询买断金
		ResponseResult<BuyoutResp> responseResult = repaymentScheduleService.readyBuyout(readyBuyoutReq);
		if(responseResult.isSuccess()){
			
			if(CollectionUtils.isNotEmpty(list)){
				
				Date firstRepaymentDate = list.get(0).getPaymentDueDate();
				Date currentDate = new Date();
				int num = repaymentScheduleService.differMonth(firstRepaymentDate, currentDate);
				
				// 本期还款日
				Date dueDate = DateUtils.getDateWithDifferMonth(firstRepaymentDate, num);
				
				for (RepaymentScheduleResp rsr : list) {
					
					if(rsr.getPaymentDueDate().compareTo(dueDate) == 0){
						rsr.setBuyoutAmount(responseResult.getData().getBuyoutAmount());
					}else {
						rsr.setBuyoutAmount(new BigDecimal(0));
					}
					
				}
			}
		}
		
		return ResponseResult.buildSuccessResponse(list);
		
	}
	/**
	 * 
	 * @Description: 查询本期应缴纳费用
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月3日
	 */
	@PostMapping(value = "/duesDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<DuesDetailResp> queryDuesDetail(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		ResponseResult<DuesDetailResp> responseResult = repaymentScheduleService.queryDuesDetail(orderSN);
		return responseResult;
		
	}
	
	@ApiOperation(value = "买断支付页面订单详情(app调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/buyout/orderDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RepaymentDetailResp> queryBuyoutOrderDetail(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		ResponseResult<RepaymentDetailResp> responseResult = repaymentScheduleService.queryBuyoutOrderDetail(orderSN);
		return responseResult;
		
	}
	
	@ApiOperation(value = "分页查询逾期数据(供租后系统调用)", httpMethod = "POST",
			notes = "操作成功返回分页对象 ",
			response = ResponseResult.class)
	@PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<RepaymentScheduleResp>> queryPage(@RequestBody Page page){
		log.info(LiquidationConstant.LOG_PREFIX+" queryPage page{}:",page);
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,page,Default.class);
		
		if (page.getStart() <1) {
			page.setStart(1);
		}
		
		if(validateResult == null){
			
			QueryDto queryDto = new QueryDto();
			queryDto.setPage(page);
			
			RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
			repaymentSchedule.setOverdueDay(1);
			repaymentSchedule.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
			repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
			queryDto.setQueryConditions(repaymentSchedule);
			
			ResultPager<RepaymentScheduleResp> resultPager = repaymentScheduleService.queryPage(queryDto);
			
			return ResponseResult.buildSuccessResponse(resultPager);
		}
		
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
	}
	
	@ApiOperation(value = "查询应还金额(租后系统调用)", httpMethod = "POST",
			notes = "操作成功返回 errorCode=0 ",
			response = ResponseResult.class)
	@PostMapping(value = "/queryRepaymentAmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<BigDecimal> queryRepaymentAmount(@RequestBody RepaymentReq repaymentReq){
		log.info(LiquidationConstant.LOG_PREFIX+" queryRepaymentAmount:{}",JsonUtils.toJsonString(repaymentReq));
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,repaymentReq,Default.class);
		
		if(validateResult == null){
			
			if(1 == repaymentReq.getRepaymentType()){// 查询应还租金
				
				BigDecimal amount = repaymentScheduleService.selectSumCurrentBalance(repaymentReq.getOrderSN(), repaymentReq.getRepaymentType());
				return ResponseResult.buildSuccessResponse(amount);
				
			}else if(2 == repaymentReq.getRepaymentType()){// 查询强制买断金(逾期)
				
				ResponseResult<?> responseResult = repaymentScheduleService.validBuyout(repaymentReq.getOrderSN(),repaymentReq.getRepaymentType());
				if(!responseResult.isSuccess()){
					return ResponseResult.build(responseResult.getErrCode(), responseResult.getErrMsg(), null);
				}
				
				ReadyBuyoutReq readyBuyoutReq = new ReadyBuyoutReq();
				readyBuyoutReq.setOrderSN(repaymentReq.getOrderSN());
				ResponseResult<BuyoutResp> result = repaymentScheduleService.readyBuyout(readyBuyoutReq );
				return ResponseResult.buildSuccessResponse(result.getData().getBuyoutAmount());
				
			}else{
				return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
			}
			
		}
		
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
		
	}
	
	@ApiOperation(value = "查询滞纳金总额和应还滞纳金", httpMethod = "POST",
			notes = "操作成功返回 errorCode=0 data:{\"clearedLateFees\"：100,\"unClearedLateFees\":200}",
			response = ResponseResult.class)
	@PostMapping(value = "/lateFees/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Map<String,BigDecimal>> selectLateFees(@PathVariable("orderSN") String orderSN){
		log.info(LiquidationConstant.LOG_PREFIX+" selectLateFees param-->orderSN:{}",orderSN);
		Map<String,BigDecimal> map = repaymentScheduleService.selectLateFees(orderSN);
		return ResponseResult.buildSuccessResponse(map);
		
	}
	
	@ApiOperation(value = "查询逾期数据", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/queryRepaymentList/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RepaymentScheduleResp>> queryRepaymentList(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		List<RepaymentSchedule> sourceList = repaymentScheduleService.selectList(repaymentSchedule);
		List<RepaymentScheduleResp> list = BeanConvertUtil.convertBeanList(sourceList, RepaymentScheduleResp.class);
		
		ReadyBuyoutReq readyBuyoutReq = new ReadyBuyoutReq();
		readyBuyoutReq.setOrderSN(orderSN);
		// 查询逾期买断金
		ResponseResult<BuyoutResp> responseResult = repaymentScheduleService.readyBuyout(readyBuyoutReq);
		if(responseResult.isSuccess()){
			
			if(CollectionUtils.isNotEmpty(list)){
				
				Date firstRepaymentDate = list.get(0).getPaymentDueDate();
				Date currentDate = new Date();
				int num = repaymentScheduleService.differMonth(firstRepaymentDate, currentDate);
				
				// 本期还款日
				Date dueDate = DateUtils.getDateWithDifferMonth(firstRepaymentDate, num);
				
				for (RepaymentScheduleResp rsr : list) {
					
					if(rsr.getPaymentDueDate().compareTo(dueDate) == 0){
						rsr.setBuyoutAmount(responseResult.getData().getBuyoutAmount());
					}else {
						rsr.setBuyoutAmount(new BigDecimal(0));
					}
				}
				
			}
			
		}
		
		return ResponseResult.buildSuccessResponse(list);
		
	}
	/**
	 * 
	 * @Description:偿还租金详情(APP支付租金页面调用)
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月28日
	 */
	@ApiOperation(value = "APP支付租金详情", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/repayRent/detail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<DuesDetailResp> repayRentDetail(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		ResponseResult<DuesDetailResp> responseResult = repaymentScheduleService.queryDuesDetail(orderSN);
		return responseResult;
		
	}
	
	/**
	 * 
	 * @Description: 根据订单号查询租金还款计划
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月2日
	 */
	@ApiOperation(value = "查询租金还款计划(清算后台系统调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/rentSchedule/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RepaymentScheduleResp>> rentRepaymentSchedule(@PathVariable("orderSN") String orderSN){
		
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		
		List<RepaymentSchedule> sourceList = repaymentScheduleService.selectList(repaymentSchedule);
		List<RepaymentScheduleResp> list = new ArrayList<RepaymentScheduleResp>();
		
		if(CollectionUtils.isNotEmpty(sourceList)){
			list = BeanConvertUtil.convertBeanList(sourceList, RepaymentScheduleResp.class);
		}
		
		return ResponseResult.buildSuccessResponse(list);
		
	}
	
	@ApiOperation(value = "APP结清支付详情查询接口", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/settle/detail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<SettleDetailResp> settleDetail(@PathVariable("orderSN") String orderSN){
		log.info(LiquidationConstant.LOG_PREFIX+"settleDetail orderSN:{}",orderSN);
		return repaymentService.settleDetail(orderSN);
		
	}
	
	@ApiOperation(value = "生成芝麻订单还款计划(订单服务调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/add/zmRepaymentSchedule", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> addZmRepaymentSchedule(@RequestBody ZmRepaymentScheduleReq req){
		log.info(LiquidationConstant.LOG_PREFIX+" 生成芝麻订单还款计划 addZmRepaymentSchedule:{}",JsonUtils.toJsonString(req));
		
		return repaymentService.addZmRepaymentSchedule(req);
		
	}
	
	@ApiOperation(value = "查询芝麻订单还款计划(APP调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/zmOrder/repaymentSchedule/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<ZmRepaymentScheduleResp>> zmRepaymentSchedule(@PathVariable("orderSN") String orderSN){
		
		RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
		repaymentSchedule.setOrderSN(orderSN);
		repaymentSchedule.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		repaymentSchedule.setEnableFlag(LiquidationConstant.ENABLE_FLAG_YES);
		repaymentSchedule.setOrderType(LiquidationConstant.ORDER_TYPE_ZM);
		
		List<RepaymentSchedule> sourceList = repaymentScheduleService.selectList(repaymentSchedule);
		List<ZmRepaymentScheduleResp> list = new ArrayList<ZmRepaymentScheduleResp>();
		
		if(CollectionUtils.isNotEmpty(sourceList)){
			list = BeanConvertUtil.convertBeanList(sourceList, ZmRepaymentScheduleResp.class);
		}
		
		return ResponseResult.buildSuccessResponse(list);
	}
	
	@ApiOperation(value = "查询芝麻订单账单详情(APP调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/zmOrder/statementDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ZmStatementDetailResp> queryZmStatementDetail(@PathVariable("orderSN") String orderSN) {
		log.info(LiquidationConstant.LOG_PREFIX+"",orderSN);
		return repaymentService.queryZmStatementDetail(orderSN);
		
	}
}
