package org.gz.liquidation.backend.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.ITransactionRecordService;
import org.gz.liquidation.common.dto.DownpaymentStatisticsReq;
import org.gz.liquidation.common.dto.DownpaymentStatisticsResp;
import org.gz.liquidation.common.dto.TransactionRecordQueryReq;
import org.gz.liquidation.common.dto.TransactionRecordReq;
import org.gz.liquidation.common.dto.TransactionRecordResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	首期款管理控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月2日 	Created
 */
@RestController
@RequestMapping("/downPayment")
@Slf4j
public class DownPaymentController extends BaseController {
	@Resource
	private ITransactionRecordService iTransactionRecordService;
	
	@Autowired
	private Validator validator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	
	@PostMapping(value = "/transaction/list")
    public ResponseResult<?> transactionList(TransactionRecordQueryReq req) {
    	try {
    		log.info("transactionList {}",JsonUtils.toJsonString(req));
    		ResultPager<TransactionRecordResp> resultPager = iTransactionRecordService.queryPage(req);
    		return ResponseResult.buildSuccessResponse(resultPager);
    	} catch (Exception e) {
    		return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
    	}
    }
	
	/**
	 * 
	 * @Description: 交易记录明细
	 * @param id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月3日
	 */
	@PostMapping(value = "/transaction/detail/{id}")
	public ResponseResult<?> transactionDetail(@PathVariable("id") Long id) {
		try {
			ResponseResult<TransactionRecordResp> result = iTransactionRecordService.transactionRecordDetail(id);
			return ResponseResult.buildSuccessResponse(result);
		} catch (Exception e) {
			log.error("查询交易流水明细异常 {}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
	}
	@PostMapping(value = "/downpaymentStatistics")
	public ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatistics() {
		try {
			DownpaymentStatisticsReq downpaymentStatisticsReq = new DownpaymentStatisticsReq();
			downpaymentStatisticsReq.setDateEnd(new Date());
			return iTransactionRecordService.queryDownpaymentStatistics(downpaymentStatisticsReq);
		} catch (Exception e) {
			log.error("查询首期款统计异常 {}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
	}
	@PostMapping(value = "/downpaymentStatistics/today")
	public ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatisticsToday() {
		try {
			DownpaymentStatisticsReq downpaymentStatisticsReq = new DownpaymentStatisticsReq();
			Date date = new Date();
			downpaymentStatisticsReq.setDateStart(date);
			downpaymentStatisticsReq.setDateEnd(date);
			return iTransactionRecordService.queryDownpaymentStatistics(downpaymentStatisticsReq);
		} catch (Exception e) {
			log.error("查询首期款统计异常 {}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
	}
	@PostMapping(value = "/downpaymentStatistics/yestoday")
	public ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatisticsYestoday() {
		try {
			DownpaymentStatisticsReq downpaymentStatisticsReq = new DownpaymentStatisticsReq();
			Date yestoday = DateUtils.getDateWithDifferDay(-1);
			downpaymentStatisticsReq.setDateStart(yestoday);
			downpaymentStatisticsReq.setDateEnd(yestoday);
			return iTransactionRecordService.queryDownpaymentStatistics(downpaymentStatisticsReq);
		} catch (Exception e) {
			log.error("查询首期款统计异常 {}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
	}
	/**
	 * 
	 * @Description: 手动完成支付
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月9日
	 */
	@PostMapping(value = "/payCorrected")
	public ResponseResult<?> payCorrected(@Valid TransactionRecordReq req) {
		log.info("payCorrected >>>>>>>>> {}",JsonUtils.toJsonString(req));
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,req,Default.class);
		if(validateResult == null){
			try {
				ResponseResult<?> responseResult = iTransactionRecordService.payCorrected(req);
				return responseResult;
			} catch (Exception e) {
				log.error("手动支付异常 {}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
			}
		}
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
	}
	
}
