package org.gz.liquidation.web.controller.transactionrecord;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.TransactionRecordQueryReq;
import org.gz.liquidation.common.dto.TransactionRecordReq;
import org.gz.liquidation.common.dto.TransactionRecordResp;
import org.gz.liquidation.common.dto.TransactionRecordUpdateReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.order.common.dto.OrderDetailResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	交易流水记录控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月23日 	Created
 */
@RestController
@RequestMapping("/transactionRecord")
@Slf4j
public class TransactionRecordController extends BaseController {
	@Resource
	private TransactionRecordService transactionRecordService;
	
	@Autowired
	private OrderService orderService;
	
	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	
	@Autowired
	private Validator validator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	
	/**
	 * 
	 * @Description: 分页查询交易流水
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月24日
	 */
	@PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultPager<TransactionRecordResp> queryPage(@RequestBody TransactionRecordQueryReq req){
		log.info("******************************* queryPage :{}",JsonUtils.toJsonString(req));
		QueryDto queryDto = new QueryDto();
		Page page = new Page();
		page.setStart(req.getCurrPage());
		page.setPageSize(req.getPageSize());
		queryDto.setPage(page);
		queryDto.setQueryConditions(req);
		return transactionRecordService.selectPage(queryDto);
		
	}
	
	@PostMapping(value = "/detail/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<TransactionRecordResp> transactionRecordDetail(@PathVariable Long id){
		
		if(id == null || id <=0 ){
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(),ResponseStatus.PARAMETER_ERROR.getMessage(), null);
		}
		TransactionRecord transactionRecord = transactionRecordService.selectOneById(id);
		TransactionRecordResp resp = BeanConvertUtil.convertBean(transactionRecord, TransactionRecordResp.class);
		ResponseResult<OrderDetailResp> responseResult = orderService.queryBackOrderDetail(transactionRecord.getOrderSN());
		if(responseResult.isSuccess()){
			OrderDetailResp orderDetailResp = responseResult.getData();
			resp.setRent(orderDetailResp.getLeaseAmount());
			resp.setPeriods(orderDetailResp.getLeaseTerm());
			resp.setPremium(orderDetailResp.getFloatAmount());
		}
		return ResponseResult.buildSuccessResponse(resp);
		
	}
	/**
	 * 
	 * @Description: 更新交易流水状态(作废交易流水)
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月31日
	 */
	@ApiOperation(value = "更新交易流水状态(作废交易流水)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/update/state", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> updateState(@RequestBody TransactionRecordUpdateReq req){
		log.info("******************************* updateState :{}",JsonUtils.toJsonString(req));
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,req,Default.class);
		if(validateResult == null){
			
			try {
				
				 TransactionRecord transactionRecord = new TransactionRecord();
				 transactionRecord.setTransactionSN(req.getTransactionSN());
				 transactionRecord.setUpdateUsername(req.getUpdateUsername());
				 transactionRecord.setState(req.getState());
				 transactionRecordService.updateState(transactionRecord);
				 
				 return ResponseResult.buildSuccessResponse();
			} catch (Exception e) {
				
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		}
		
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
		
	}
	
	@ApiOperation(value = "查询交易统计", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/tradeStatistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> queryTradeStatistics(@RequestBody TransactionRecordReq req){
		return null;
		
	}
	
	@ApiOperation(value = "验证是否可以进行交易", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 true 是 false 否",
            response = ResponseResult.class)
	@PostMapping(value = "/state/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Boolean> queryTradeState(@PathVariable("orderSN") String orderSN){
		
		Boolean flag = transactionRecordService.validateTrade(orderSN);
		return ResponseResult.buildSuccessResponse(flag);
		
	}
	
	/**
	 * 
	 * @Description: 根据订单号查询最新的的交易记录
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	@ApiOperation(value = "根据订单号查询最新的的交易记录", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "api/latest/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<TransactionRecordResp> queryLatest(@PathVariable("orderSN") String orderSN){
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryLatest param orderSN:{}",orderSN);
		
		return transactionRecordService.queryLatest(orderSN);
		
	}
	@ApiOperation(value = "交易统计:(成交记录数和成交金额):清算后台系统调用", httpMethod = "POST",
			notes = "操作成功返回 errorCode=0 ",
			response = ResponseResult.class)
	@PostMapping(value = "/statistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryStatistics(@RequestBody TransactionRecordQueryReq req){
		
		log.info("******************************* queryPage :{}",JsonUtils.toJsonString(req));
		return transactionRecordService.queryStatistics(req);
		
	}
}
