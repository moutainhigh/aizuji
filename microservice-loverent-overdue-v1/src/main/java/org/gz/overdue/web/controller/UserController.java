package org.gz.overdue.web.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.overdue.entity.OverdueOrder;
import org.gz.overdue.entity.vo.OverdueOrderInfoVo;
import org.gz.overdue.entity.vo.UserBaseInfoVo;
import org.gz.overdue.entity.vo.UserInfoVo;
import org.gz.overdue.service.OrderService;
import org.gz.overdue.service.overdueOrder.OverdueOrderService;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.ContactInfo;
import org.gz.user.service.ContactInfoService;
import org.gz.user.service.UserService;
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
@RequestMapping("/api/user")
@Slf4j
public class UserController extends BaseController {
	 
	@Resource
    private OverdueOrderService overdueOrderService;
	
	@Autowired
	private ContactInfoService contactInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	/**
	 * 根据订单编号查询用户信息
	 * @param overdueOrderInfoVo
	 * @param bindingResult
	 * @return
	 */
	 @PostMapping(value = "/getUserInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> getUserInfo(@Valid @RequestBody OverdueOrderInfoVo overdueOrderInfoVo, BindingResult bindingResult){
		// 验证数据
        ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
        	try {
                //查询用户Id
        		OverdueOrder overdueOrder = new OverdueOrder();
        		overdueOrder.setOrderSN(overdueOrderInfoVo.getOrderSN());
        		List<OverdueOrder> list = overdueOrderService.query(overdueOrder);
        		if(list==null||list.size()==0){
        			return ResponseResult.build(9527, "该笔订单不存在", null);
        		}
        		OverdueOrder order = list.get(0);
                //根据用户ID查询用户信息
        		AppUser user = new AppUser();
        		user.setUserId(Long.valueOf(order.getUserId()));
        		ResponseResult<UserInfoDto> userResult = userService.queryByCondition(user);
        		if(!userResult.isSuccess()){
        			return ResponseResult.build(9527, "获取用户信息失败", null);
        		}
        		UserInfoDto userDto = userResult.getData();
                //根据订单号查询用户紧急联系人
        		ResponseResult<OrderDetailResp> orderResult = orderService.queryBackOrderDetail(overdueOrderInfoVo.getOrderSN());
        		OrderDetailResp orderDetailResp = orderResult.getData();
        		System.out.println(JSON.toJSONString(orderDetailResp));
        		UserBaseInfoVo userVo = new UserBaseInfoVo();
        		//用户接口赋值
        		userVo.setUserId(userDto.getUserId());
        		userVo.setRealName(userDto.getRealName());
        		userVo.setGender(userDto.getGender());
        		userVo.setAge(userDto.getAge());
        		userVo.setPhone(userDto.getPhoneNum());
        		userVo.setIdNo(userDto.getIdNo());
        		userVo.setResidenceAddress(userDto.getResidenceAddress());
        		userVo.setJob(userDto.getJob());
        		userVo.setIndustry(userDto.getIndustry());
        		userVo.setNearAddress(userDto.getAddrProvince()+userDto.getAddrCity()+userDto.getAddrDetail());
        		//订单接口赋值
        		userVo.setOrderAddress(orderDetailResp.getProv() + orderDetailResp.getCity() + orderDetailResp.getArea() + orderDetailResp.getAddress());
        		userVo.setEmergencyContact(orderDetailResp.getEmergencyContact());
        		userVo.setEmergencyContactPhone(orderDetailResp.getEmergencyContactPhone());
        		userVo.setOrderState(orderDetailResp.getState());
        		userVo.setOrderStateDesc(orderDetailResp.getStateDesc());
                validateResult = ResponseResult.buildSuccessResponse(userVo);
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("新增记录失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
	 }
	 
	 /**
	  * 获取用户手机通讯录
	  * @param overdueOrderInfoVo
	  * @param bindingResult
	  * @return
	  */
	 @PostMapping(value = "/getUserPhoneAddreeList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> getUserPhoneAddreeList(@Valid @RequestBody UserInfoVo userInfoVo, BindingResult bindingResult){
		 // 验证数据
	     ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
	     if(validateResult!=null){
	    	 return validateResult;
	     }
		 ContactInfoQueryDto queryDto = new ContactInfoQueryDto();
		 queryDto.setUserId(userInfoVo.getUserId());
		 queryDto.setCurrPage(userInfoVo.getCurrPage());
		 queryDto.setPageSize(userInfoVo.getPageSize());
		 ResponseResult<ContactInfoQueryDto> result = contactInfoService.queryContactInfoByPage(queryDto);
		 if(result.getErrCode()==0){
			 ContactInfoQueryDto dto = result.getData();
			 ResultPager<ContactInfo> resultPager = new ResultPager<>(dto.getTotalNum().intValue(),dto.getCurrPage(),dto.getPageSize(),dto.getData()); 
			 validateResult = ResponseResult.buildSuccessResponse(resultPager);
			 return validateResult;
		 }
		 return result;
	 }
}
