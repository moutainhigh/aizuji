package org.gz.overdue.web.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.gz.common.OrderBy;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.overdue.entity.user.User;
import org.gz.overdue.entity.vo.InstallmentInfoVo;
import org.gz.overdue.entity.vo.OverdueOrderInfoVo;
import org.gz.overdue.entity.vo.UserInfoVo;
import org.gz.overdue.service.LiquidationService;
import org.gz.overdue.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 产品信息控制器
 * @Description:TODO
 * Author	Version		Date		Changes
 * hening 	1.0  		2018年2月1日 	Created
 */
@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController extends BaseController {
	
	@Autowired
	private LiquidationService liquidationService;
	@Autowired
	private OrderService orderService;

	 /**
	  * 获取用户订单列表
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/getUserOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> getUserOrderList(@Valid @RequestBody UserInfoVo userInfoVo, BindingResult bindingResult){
		 log.info("/api/order/getUserOrderList is begin:{}",JSON.toJSONString(userInfoVo));
		 //验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 RentRecordQuery rentRecordQuery = new RentRecordQuery();
	     OrderBy orderBy = new OrderBy();
	     orderBy.setCloumnName("rr.id");
	     orderBy.setSequence("desc");
	     List<OrderBy> orderBylist = new ArrayList<>();
	     orderBylist.add(orderBy);
	     rentRecordQuery.setOrderBy(orderBylist);
	     rentRecordQuery.setNotZero(5);
	     rentRecordQuery.setGreateZero(1);
	     rentRecordQuery.setUserId(userInfoVo.getUserId());
	     ResponseResult<List<OrderDetailResp>> result = orderService.queryOrderList(rentRecordQuery);
	     log.info("/api/order/getUserOrderList is end:{}",JSON.toJSONString(result));
		 return result;
	 }
	 
	 /**
	  * 获取用户分期详情
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/getUserInstallmentInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> getUserInstallmentInfo(@Valid @RequestBody OverdueOrderInfoVo OverdueOrderInfoVo, BindingResult bindingResult){
		 log.info("/api/order/getUserInstallmentInfo is begin:{}",JSON.toJSONString(OverdueOrderInfoVo));
		 // 验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 //调用庆吉接口查询分期详情
		 ResponseResult<List<RepaymentScheduleResp>> result =  liquidationService.queryRepaymentList(OverdueOrderInfoVo.getOrderSN());
		 if(result.getErrCode()==0){
			 List<RepaymentScheduleResp> resList = result.getData();
			 //获取总期数
			 int period = resList.get(0).getPeriods();
			 List<InstallmentInfoVo> list = new ArrayList<>();
			 //初始化分期详情
			 for(int i=0;i<period;i++){
				 InstallmentInfoVo vo = new InstallmentInfoVo();
				 vo.setPeriods(i+1);
				 vo.setOverdueFee(BigDecimal.ZERO);
				 vo.setRentFee(BigDecimal.ZERO);
				 vo.setBuyFee(BigDecimal.ZERO);
				 list.add(vo);
			 }
			 //数据处理
			 resList.forEach(l->{
				 System.out.println(JSON.toJSONString(l));
				 InstallmentInfoVo vo = list.get(l.getPeriodOf()-1);
				 if(vo.getOrderSN()==null){
					 vo.setOrderSN(l.getOrderSN());
					 vo.setPaymentDueDate(DateUtils.getString(l.getPaymentDueDate(),DateUtils.FMT_LONG_DATE));
					 vo.setSettleFlag(l.getSettleFlag());
					 vo.setSettleDate(DateUtils.getString(l.getSettleDate(),DateUtils.FMT_LONG_DATE));
					 vo.setOverdueDay(l.getOverdueDay());
					 vo.setBuyFee(l.getBuyoutAmount());
				 }
				 //1 租金 2滞纳金
				 if(l.getRepaymentType()==1){
					 vo.setRentFee(l.getDue());
				 }
				 if(l.getRepaymentType()==2){
					 vo.setOverdueFee(vo.getOverdueFee().add(l.getDue())); 
				 }
			 });
			 log.info("/api/order/getUserInstallmentInfo is end:{}",JSON.toJSONString(result));
			 return ResponseResult.buildSuccessResponse(list);
		 }
		 log.info("/api/order/getUserInstallmentInfo is end:{}",JSON.toJSONString(result));
		 return result;
	 }
	 
	 /**
	  * 强制买断
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/compulsiveBuying", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> compulsiveBuying(@Valid @RequestBody OverdueOrderInfoVo overdueOrderInfoVo, BindingResult bindingResult){
		 log.info("/api/order/compulsiveBuying is begin:{}",JSON.toJSONString(overdueOrderInfoVo));
		 // 验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 //暂时没有获取登录用户，待后续修改
		 User user = new User();
		 //修改订单状态
		 UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
		 updateOrderState.setRentRecordNo(overdueOrderInfoVo.getOrderSN());
		 updateOrderState.setState(BackRentState.ForceBuyIng.getCode());
		 updateOrderState.setCreateBy(user.getId());
		 updateOrderState.setCreateMan(user.getUserName());
		 ResponseResult<String> result = orderService.updateOrderState(updateOrderState);
		 log.info("/api/order/compulsiveBuying is end:{}",JSON.toJSONString(result));
		 return result;
	 }
	 
	 /**
	  * 强制履约
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/compulsoryPerformance", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> compulsoryPerformance(@Valid @RequestBody OverdueOrderInfoVo overdueOrderInfoVo, BindingResult bindingResult){
		 log.info("/api/order/compulsoryPerformance is begin:{}",JSON.toJSONString(overdueOrderInfoVo));
		 // 验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 //暂时没有获取登录用户，待后续修改
		 User user = new User();
		 //修改订单状态
		 UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
		 updateOrderState.setRentRecordNo(overdueOrderInfoVo.getOrderSN());
		 updateOrderState.setState(BackRentState.ForcePerformanceIng.getCode());
		 updateOrderState.setCreateBy(user.getId());
		 updateOrderState.setCreateMan(user.getUserName());
		 ResponseResult<String> result = orderService.updateOrderState(updateOrderState);
		 log.info("/api/order/compulsoryPerformance is end:{}",JSON.toJSONString(result));
		 return result;
	 }
	 
	 /**
	  * 获取扣款金额(租金，买断金，定损费)
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/getUserPayAmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> getUserPayAmount(@Valid @RequestBody OverdueOrderInfoVo overdueOrderInfoVo, BindingResult bindingResult){
		 log.info("/api/order/getUserPayAmount is begin:{}",JSON.toJSONString(overdueOrderInfoVo));
		 if(StringUtils.isBlank(overdueOrderInfoVo.getOrderSN())){
			return ResponseResult.build( 9527,"订单编号不能为null",null);
		 }
		 if(overdueOrderInfoVo.getPaymentType()==null){
			return ResponseResult.build( 9527,"金额类型不能为null",null);
		 }
		 ResponseResult<?> result = null;
		 Map<String,Object> map = new HashMap<>();
		 map.put("paymentType", overdueOrderInfoVo.getPaymentType());
		 switch(overdueOrderInfoVo.getPaymentType()){
		 	 //租金
			 case 1:
				 RepaymentReq repaymentReq_1 = new RepaymentReq();
				 repaymentReq_1.setOrderSN(overdueOrderInfoVo.getOrderSN());
				 repaymentReq_1.setRepaymentType(1);
				 ResponseResult<BigDecimal> result_1 = liquidationService.queryRepaymentAmount(repaymentReq_1);
				 map.put("amount", result_1.getData());
				 result = ResponseResult.buildSuccessResponse(map);
				 break;
			 //买断金
			 case 2:
				 RepaymentReq repaymentReq_2 = new RepaymentReq();
				 repaymentReq_2.setOrderSN(overdueOrderInfoVo.getOrderSN());
				 repaymentReq_2.setRepaymentType(2);
				 ResponseResult<BigDecimal> result_2 = liquidationService.queryRepaymentAmount(repaymentReq_2);
				 map.put("amount", result_2.getData());
				 result =  ResponseResult.buildSuccessResponse(map);
				 break;
			 //归还金
			 case 3:
				 //根据用户ID查询用户紧急联系人
	        	 ResponseResult<OrderDetailResp> orderResult = orderService.queryBackOrderDetail(overdueOrderInfoVo.getOrderSN());
	        	 OrderDetailResp orderDetailResp = orderResult.getData();
	        	 map.put("amount", orderDetailResp.getDiscountFee());
	        	 result = ResponseResult.buildSuccessResponse(map);
				 break;
			 default: log.error("支付金额类型未知");
			 	 result = ResponseResult.build( 9527,"支付金额类型未知",null);
				 break;
		 }
		 log.info("/api/order/getUserPayAmount is end:{}",JSON.toJSONString(overdueOrderInfoVo));
		 return result;
	 }
	 
	 /**
	  * 获取滞纳金金额
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/getUserOverdueFee", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> getUserOverdueFee(@Valid @RequestBody OverdueOrderInfoVo overdueOrderInfoVo, BindingResult bindingResult){
		 log.info("/api/order/getUserOverdueFee is begin:{}",JSON.toJSONString(overdueOrderInfoVo));
		 // 验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 ResponseResult<Map<String,BigDecimal>> result = liquidationService.selectLateFees(overdueOrderInfoVo.getOrderSN());
		 log.info("/api/order/getUserOverdueFee is end:{}",JSON.toJSONString(result));
		 return result;
	 }
	 
	 /**
	  * 滞纳金减免
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/overdueFeeReduction", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> overdueFeeReduction(@Valid @RequestBody OverdueOrderInfoVo overdueOrderInfoVo, BindingResult bindingResult){
		 log.info("/api/order/overdueFeeReduction is begin:{}",JSON.toJSONString(overdueOrderInfoVo));
		 // 验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 //暂时没有获取登录用户，待后续修改
		 User user = new User();
		 //滞纳金减免
		 LateFeeRemissionReq lateFeeRemissionReq = new LateFeeRemissionReq();
		 lateFeeRemissionReq.setOrderSN(overdueOrderInfoVo.getOrderSN());
		 lateFeeRemissionReq.setRemissionAmount(overdueOrderInfoVo.getRemissionAmount());
		 lateFeeRemissionReq.setCreateBy(user.getId());
		 lateFeeRemissionReq.setCreateByRealname(user.getUserName());
		 ResponseResult<?> result = liquidationService.doLateFeeRemission(lateFeeRemissionReq);
		 log.info("/api/order/overdueFeeReduction is end:{}",JSON.toJSONString(result));
		 return result;
	 }
}
