package org.gz.liquidation.backend.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.IAccountRecordService;
import org.gz.liquidation.common.dto.AccountRecordReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:还款记录控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月6日 	Created
 */
@Slf4j
@RestController
@RequestMapping("/repayment")
public class RepaymentController extends BaseController {

	@Autowired
	private IAccountRecordService accountRecordService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	
	/**
	 * 
	 * @Description: 分页查询科目流水记录（清算记录）
	 * @param accountRecordReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月6日
	 */
	@PostMapping(value = "/accoutRecord/pageList")
	public ResponseResult<?> queryaccoutRecord(AccountRecordReq accountRecordReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"repaymentList :{}",JsonUtils.toJsonString(accountRecordReq));
		return accountRecordService.queryPage(accountRecordReq);
		
	}
}
