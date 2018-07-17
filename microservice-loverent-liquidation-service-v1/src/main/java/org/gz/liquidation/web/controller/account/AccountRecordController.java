package org.gz.liquidation.web.controller.account;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.dto.AccountRecordReq;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.SaleOrderRepaymentInfoResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.service.account.AccountRecordService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:科目流水控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月6日 	Created
 */
@RestController
@RequestMapping("/accountRecord")
@Slf4j
public class AccountRecordController extends BaseController{

	@Resource
	private AccountRecordService accountRecordService;
	
	@ApiOperation(value = "分页查询科目流水记录", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryPage(@RequestBody AccountRecordReq accountRecordReq){
		log.info(">>>>>>>>>>>>>>>> queryPage:{}",JsonUtils.toJsonString(accountRecordReq));
		
		try {
			
			QueryDto queryDto = new QueryDto();
			Page page = new Page();
			page.setStart(accountRecordReq.getCurrPage());
			page.setPageSize(accountRecordReq.getPageSize());
			queryDto.setPage(page);
			queryDto.setQueryConditions(accountRecordReq);
			
			ResultPager<AccountRecordResp> resultPager = accountRecordService.selectPage(queryDto);
			
			return ResponseResult.buildSuccessResponse(resultPager);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
		}
		
	}
	
	@ApiOperation(value = "查询售卖订单销售金和保障金(订单服务调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/querySaleOrderInfo/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> querySaleOrderInfo(@PathVariable("orderSN") String orderSN){
		log.info(">>>>>>>>>>>>>>>> querySaleOrderInfo orderSN:{}",orderSN);
		
		BigDecimal salePrice = new BigDecimal(0);
		BigDecimal insurance = new BigDecimal(0);
		
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setOrderSN(orderSN);
		accountRecord.setAccountCode(AccountEnum.XSJ.getAccountCode());
		accountRecord.setFlowType(LiquidationConstant.IN);
		List<AccountRecord> list = accountRecordService.selectList(accountRecord);
		if(!list.isEmpty()){
			AccountRecord ar = list.get(0);
			salePrice = ar.getAmount();
		}
		
		accountRecord.setAccountCode(AccountEnum.BZJ.getAccountCode());
		List<AccountRecord> resultList = accountRecordService.selectList(accountRecord);
		if(!resultList.isEmpty()){
			AccountRecord ar = resultList.get(0);
			insurance = ar.getAmount();
		}
		
		SaleOrderRepaymentInfoResp resp = new SaleOrderRepaymentInfoResp();
		resp.setInsurance(insurance);
		resp.setSalePrice(salePrice);
		return ResponseResult.buildSuccessResponse(resp);
		
	}
}
