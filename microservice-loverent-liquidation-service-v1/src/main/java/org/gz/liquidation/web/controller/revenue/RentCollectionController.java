package org.gz.liquidation.web.controller.revenue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.common.utils.DateUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.AfterRentOrderDetailReq;
import org.gz.liquidation.common.dto.AfterRentOrderDetailResp;
import org.gz.liquidation.common.dto.OrderRepaymentResp;
import org.gz.liquidation.common.dto.RevenueStatisticsResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.revenue.RentCollectionService;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
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
 * @Description:	租后收款管理控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月19日 	Created
 */
@Slf4j
@RestController
@RequestMapping("/rentCollection")
public class RentCollectionController extends BaseController{
	
	@Resource
	private AccountRecordService accountRecordService;
	
	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	
	@Resource
	private RentCollectionService rentCollectionService;

	@Autowired
	private Validator validator;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 
	 * @Description: 分页查询租后订单列表
	 * @param repaymentScheduleReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月19日
	 */
	@ApiOperation(value = "收款管理->租后收款（分页查询租后订单列表）", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/order/pageList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultPager<AfterRentOrderDetailResp> queryOrderList(@RequestBody AfterRentOrderDetailReq param, BindingResult bindingResult) {
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryOrderList:{}",param);
		
		RentRecordQuery rentRecordQuery = new RentRecordQuery();
		rentRecordQuery.setCurrPage(param.getCurrPage());
		rentRecordQuery.setPageSize(param.getPageSize());
		
		if(StringUtils.isNotBlank(param.getOrderSN())){
			rentRecordQuery.setRentRecordNo(param.getOrderSN().trim());
		}else {
			
			if(StringUtils.isNotBlank(param.getIdentityCard())){
				rentRecordQuery.setIdNo(param.getIdentityCard().trim());
			}
			
			if(StringUtils.isNotBlank(param.getRealName())){
				rentRecordQuery.setRealName(param.getRealName().trim());
			}
			
		}
		
		try {
			
			ResponseResult<ResultPager<OrderDetailResp>> responseResult = orderService.queryPageOrderForLiquation(rentRecordQuery);
			ResultPager<OrderDetailResp> rp = responseResult.getData();
			List<AfterRentOrderDetailResp> data = BeanConvertUtil.convertBeanList(rp.getData(), AfterRentOrderDetailResp.class);
			ResultPager<AfterRentOrderDetailResp> result = new ResultPager<>(rp.getTotalNum(), rp.getCurrPage(), rp.getPageSize(), data);
			
			return result;
			
		} catch (Exception e) {
			
			log.error(LiquidationConstant.LOG_PREFIX+"queryOrderList 异常:{}",e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * 
	 * @Description: 查看订单收款详情
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月19日
	 */
	@ApiOperation(value = "租后收款-->订单详情(租后后台系统)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/queryOrderDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderRepaymentResp> queryOrderDetail(@PathVariable("orderSN") String orderSN){
		log.info(LiquidationConstant.LOG_PREFIX+"queryOrderDetail:{}",orderSN);
		ResponseResult<OrderRepaymentResp> responseResult = rentCollectionService.queryOrderRepmaymentDetail(orderSN);
		return responseResult;
		
	}
	
	@ApiOperation(value = "收入统计", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/revenueStatistics/{type}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RevenueStatisticsResp> queryRevenueStatistics(@PathVariable("type") String type){
		Map<String,Object> map = new HashMap<>();
		if(!type.equalsIgnoreCase("today") && !type.equalsIgnoreCase("all")){
			return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), ResponseStatus.PARAMETER_ERROR.getMessage(), null);
		}
		if(type.equalsIgnoreCase("today")){// 统计今天所有数据
			map.put("startDate", DateUtils.getDayStart());
			map.put("endDate", DateUtils.getDayEnd());
		}
		ResponseResult<RevenueStatisticsResp> responseResult = accountRecordService.selectRevenue(map);
		return responseResult;
		
	}
	
	/**
	 * 
	 * @Description: 手动结算
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	@ApiOperation(value = "手动清偿", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/manualSettle", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> manualSettle(@RequestBody ManualSettleReq manualSettleReq){
		//log.info(LiquidationConstant.LOG_PREFIX+" manualSettle:{}",JsonUtils.toJsonString(manualSettleReq));
		
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,manualSettleReq,Default.class);
		if(validateResult != null){
			return validateResult;
		}
		
		return repaymentScheduleService.manualSettle(manualSettleReq);
		
	}
	
	@ApiOperation(value = "租后收款->订单详情->订单还款明细", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/repmayment/detail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<AccountRecordResp>> queryRepmaymentDetail(@PathVariable("orderSN") String orderSN) {
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryRepmaymentDetail --> orderSN:{}",orderSN);
		return rentCollectionService.queryRepmaymentDetail(orderSN);
	}
}
