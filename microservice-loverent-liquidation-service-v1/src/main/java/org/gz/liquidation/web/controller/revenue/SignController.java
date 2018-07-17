package org.gz.liquidation.web.controller.revenue;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.dto.BuyoutResp;
import org.gz.liquidation.common.entity.BuyoutPayReq;
import org.gz.liquidation.common.entity.BuyoutReq;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	签约控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月23日 	Created
 */
@RestController
@RequestMapping("/sign")
@Slf4j
public class SignController extends BaseController{
	@Autowired
	private RepaymentScheduleService repaymentScheduleService;
	@Resource
	private AccountRecordService accountRecordServiceImpl;
	
	@Autowired
	private Validator validator;
	/**
	 * 
	 * @Description: 确认签约
	 * @param repaymentScheduleReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	@ApiOperation(value = "确认签约,生成还款计划", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/doSign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doSign(@Valid @RequestBody RepaymentScheduleReq repaymentScheduleReq) {
		log.info(">>>>>>>>>>>>>>>>>>>>>> SignController doSign:{}",JsonUtils.toJsonString(repaymentScheduleReq));
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,repaymentScheduleReq,Default.class);
		if (validateResult == null) {
			try {
				return repaymentScheduleService.addRepaymentSchedule(repaymentScheduleReq);
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		}
		return validateResult;
		
	}
	/**
	 * 
	 * @Description: 计算买断需要金额
	 * @param repaymentScheduleReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	@ApiOperation(value = "计算买断需要支付金额", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/readyBuyout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> readyBuyout(@Valid @RequestBody ReadyBuyoutReq readyBuyoutReq, BindingResult bindingResult) {
		log.info(LiquidationConstant.LOG_PREFIX+" readyBuyoutReq:{}",readyBuyoutReq);
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			
			try {
				
				ResponseResult<?> responseResult = repaymentScheduleService.validBuyout(readyBuyoutReq.getOrderSN(),readyBuyoutReq.getType());
				if(!responseResult.isSuccess()){
					return responseResult;
				}
				
				ResponseResult<BuyoutResp> result = repaymentScheduleService.readyBuyout(readyBuyoutReq);
				return result;
				
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		}
		return validateResult;
		
	}
	/**
	 * 
	 * @Description: 执行买断
	 * @param repaymentScheduleReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	@ApiOperation(value = "执行买断", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/doBuyout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doBuyout(@Valid @RequestBody BuyoutReq buyoutReq, BindingResult bindingResult) {
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				
				Long userId = buyoutReq.getUserId();
				String userName = buyoutReq.getUserName();
				String orderSN = buyoutReq.getOrderSN();
				BigDecimal amount = buyoutReq.getAmount();
				String transactionSource = buyoutReq.getTransactionSource();
				String transactionWay = buyoutReq.getTransactionWay();
				Integer type = buyoutReq.getType();
				log.info("userId:{} userName:{} orderSN:{} transactionSource:{} transactionWay:{} amount:{} type:{}",
						userId,userName,orderSN,transactionSource,transactionWay,amount,type);
				
				ResponseResult<?> responseResult = repaymentScheduleService.validBuyout(buyoutReq.getOrderSN(),buyoutReq.getType());
				if(!responseResult.isSuccess()) return responseResult;
				return null;
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), buyoutReq);
			}
		}
		return validateResult;
		
	}
	/**
	 * 
	 * @Description: TODO 处理app或者后台发起支付请求
	 * @param buyoutPayReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	@PostMapping(value = "/doBuyoutForBackEnd", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doBuyoutForBackEnd(@Valid @RequestBody BuyoutPayReq buyoutPayReq, BindingResult bindingResult) {
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				
				Long userId = buyoutPayReq.getUserId();
				String orderSN = buyoutPayReq.getOrderSN();
				String transactionWay = buyoutPayReq.getTransactionWay();
				String transactionSN = buyoutPayReq.getTransactionSN();
				Integer type = buyoutPayReq.getType();
				log.info("transactionSN:{} userId:{} orderSN:{} transactionWay:{} type:{}",transactionSN,userId,
						orderSN,transactionWay,type);
				
				BuyoutReq buyoutReq = new BuyoutReq();
				buyoutReq.setOrderSN(buyoutPayReq.getOrderSN());
				buyoutReq.setType(buyoutPayReq.getType());
				ResponseResult<?> responseResult = repaymentScheduleService.validBuyout(orderSN,type);
				if(!responseResult.isSuccess()) return responseResult;
				return repaymentScheduleService.doBuyoutPay(buyoutPayReq);
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), buyoutPayReq);
			}
		}
		return validateResult;
		
	}
}
