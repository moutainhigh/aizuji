package org.gz.app.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.exception.AppServiceException;
import org.gz.app.feign.RentRecordServiceClient;
import org.gz.app.supports.AlipayZhimaScoreUtils;
import org.gz.cache.commom.constant.CacheTTLConst;
import org.gz.cache.service.apply.OrderApplyCacheService;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.ResultUtil;
import org.gz.common.utils.StringUtils;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.UserHistory;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AddressInfo;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.UserInfo;
import org.gz.user.service.AddressInfoService;
import org.gz.user.service.UserInfoService;
import org.gz.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController(value="appOrderController")
@RequestMapping("/order")
@Slf4j
public class OrderController extends AppBaseController {

	@Autowired
	private OrderApplyCacheService orderApplyCacheService;
	
	@Autowired
	private RentRecordServiceClient rentRecordServiceClient;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressInfoService addressInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 申请订单,获取订单号
	 * @param body
	 * @return
	 */
	@RequestMapping(value="/applyOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> applyOrder(@RequestBody AddOrderReq addOrderReq, HttpServletRequest request) {
		log.info("--> execute applyOrder, params: {}", JSONObject.toJSONString(addOrderReq));
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0)); 
		
		addOrderReq.setUserId(userId);
		
		log.info("execute applyOrder , userId: {}, param: {}", userId, addOrderReq);
		
		ResponseResult<String> result = rentRecordServiceClient.add(addOrderReq);
		
		log.info("add order result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 暂存订单申请信息
	 * @param body
	 * @return
	 */
	@RequestMapping(value="/saveTempOrderApply", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> saveTempOrderApply(@RequestBody JSONObject body, HttpServletRequest request) {
		ResponseResult<String> result = new ResponseResult<String>();
		try {
			List<String> list = getUserFields(request, "userId");
			String userId = list.get(0);

			log.info("saveTempApplyInfo data , userId: {}, data: {}", userId, body.toJSONString());
			
			validationApplyData(body);
			
			orderApplyCacheService.cacheOrderApplyData(userId, body.toJSONString(), CacheTTLConst.ORDER_APPLY_DATA_TTL);
		} catch (AppServiceException e) {
			log.error("saveTempApplyInfo failed, AppServiceException: {}", e);
			result.setErrCode(e.getCode());
			result.setErrMsg(e.getMessage());
		} catch (Exception e2) {
			log.error("saveTempApplyInfo failed, Exception: {}", e2);
			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;    
	}
	
	/**
	 * 人脸识别后提交订单
	 * @param body
	 * @return
	 */
	@RequestMapping(value="/certFinishAndSubmitOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<OrderDetailResp> certFinishAndSubmitApplyInfo(HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		String userId = list.get(0);
		
		String data = orderApplyCacheService.getOrderApplyData(userId);
		if (StringUtils.isEmpty(data)) {
			ResponseResult<OrderDetailResp> result = new ResponseResult<>();
			result.setErrCode(-1);
			result.setErrMsg("订单申请数据异常");
			return result;
		} else {
			//call order service
			JSONObject body = JSONObject.parseObject(data);
			SubmitOrderReq submitOrderReq = convertSubmitOrderReq(Long.valueOf(userId), body);
			if (submitOrderReq == null) {
				ResponseResult<OrderDetailResp> result = new ResponseResult<>();
				result.setErrCode(-1);
				result.setErrMsg("订单提交异常");
				return result;
			}
			ResponseResult<OrderDetailResp> result = rentRecordServiceClient.submitOrder(submitOrderReq);
			if (result.getErrCode() == 0) {
				//更新用户最新地址
				updateUserAddress(Long.valueOf(userId), body);
			}
			return result;
		}
	}
	
	/**
	 * 提交订单
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value="/submitOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<OrderDetailResp> submitApplyInfo(@RequestBody JSONObject body, HttpServletRequest request) {
		log.info("--> execute submitApplyInfo, params: {}", JSONObject.toJSONString(body));
		// call order service
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		
		//校验芝麻粉是否需要刷新
		String minZhimaScore = body.getString("zhimaScore");
		if (!StringUtils.isEmpty(minZhimaScore)) {
			if (Integer.valueOf(minZhimaScore) > 0) {
				validationZhimaScore(userId, Integer.valueOf(minZhimaScore));
			}
		}
		
		//校验参数
		validationApplyData(body);
		
		SubmitOrderReq submitOrderReq = convertSubmitOrderReq(Long.valueOf(userId), body);
		log.info("---> submitOrder, submitOrderReq: {}", JSONObject.toJSONString(submitOrderReq));
		if (submitOrderReq == null) {
			ResponseResult<OrderDetailResp> result = new ResponseResult<>();
			result.setErrCode(-1);
			result.setErrMsg("订单提交异常");
			return result;
		}
		
		ResponseResult<OrderDetailResp> result = rentRecordServiceClient.submitOrder(submitOrderReq);
		if (result.getErrCode() == 0) {
			try {
				//更新用户最新地址
				updateUserAddress(Long.valueOf(userId), body);
				//更新学历
				updateUserInfo(Long.valueOf(userId), body);
			} catch (Exception e) {
				log.error("-->submitApplyInfo success, update user info failed, e: {}", e);
			}
		}
		return result;
	}
	

	/**
	 * 校验芝麻分
	 * @param userId
	 */
	private void validationZhimaScore(Long userId, int minScore) {
		ResponseResult<AppUser> userResp = userService.queryUserById(userId);
		if (userResp.getErrCode() == 0) {
			AppUser user = userResp.getData();
			
			//刷新芝麻分
			boolean refreshScore = false;
			
			Date lastRefreshTime = user.getZhimaScoreRefreshTime();
			if (lastRefreshTime != null) {
				int diff = DateUtils.getDifferDay(lastRefreshTime, new Date());
				if (diff > 30) {
					refreshScore = true;
				}
			} else {
				refreshScore = true;
			}
			
			Integer userZhimaScore = user.getZhimaScore();
			if (userZhimaScore == null) {
				userZhimaScore = 0;
			}
			if (userZhimaScore == 0 || userZhimaScore < minScore) {
				refreshScore = true;
			}
			
			AppUser modifyUser = new AppUser();
			modifyUser.setUserId(userId);
			if (refreshScore) {
				Integer zhimaScore = AlipayZhimaScoreUtils.queryZhimaScore(
						user.getRealName(), 
						user.getIdNo(), 
						minScore);
			
				if (zhimaScore > userZhimaScore) {
					modifyUser.setZhimaScore(zhimaScore);
				}
				modifyUser.setZhimaScoreRefreshTime(new Date());
			}
			
			Integer level = AlipayZhimaScoreUtils.queryWatchListValue(
					user.getRealName(), 
					user.getIdNo());
			modifyUser.setWatchListValue(level);
			userService.updateUser(modifyUser);
		}
	}

	//组装订单提交数据
	private SubmitOrderReq convertSubmitOrderReq(Long userId, JSONObject body) {
		
		ResponseResult<AppUser> userRst = userService.queryUserById(userId);
		
		if (userRst.getErrCode() != 0) {
			log.error("convertSubmitOrderReq failed, userRst :{}", JSONObject.toJSONString(userRst));
			return null;
		}
		
		SubmitOrderReq submitOrderReq = new SubmitOrderReq();
		
		UserHistory userHistory = new UserHistory();
		ResponseResult<UserInfoDto> userInfoRst = userInfoService.queryByUserId(userId);
		UserInfoDto dto = userInfoRst.getData();
		if (dto != null) {
			log.info("---> convertSubmitOrderReq.query UserInfoDto: {}", JSONObject.toJSONString(dto));
			BeanUtils.copyProperties(dto, userHistory);	
		}
		userHistory.setId(null);
		log.info("---> convertSubmitOrderReq convert userHistory: {}", JSONObject.toJSONString(userHistory));
		log.info("---> convertSubmitOrderReq convert DeviceType: {}", userHistory.getDeviceType());
		log.info("---> convertSubmitOrderReq convert OsType: {}", userHistory.getOsType());
		submitOrderReq.setUserHistory(userHistory);
		
		String receiverUserName = body.getString("receiverUserName");
		String receiverPhoneNum = body.getString("receiverPhoneNum");
		
		AppUser user = userRst.getData();
		//用户信息
		submitOrderReq.setUserId(userId);
		submitOrderReq.setPhoneNum(StringUtils.isEmpty(receiverPhoneNum) ? user.getPhoneNum() : receiverPhoneNum);	//手机号
		submitOrderReq.setRealName(StringUtils.isEmpty(receiverUserName) ? user.getRealName() : receiverUserName);	//收货人
		submitOrderReq.setIdNo(user.getIdNo());						//身份证
		submitOrderReq.setCreateMan(user.getRealName());
		//订单信息
		submitOrderReq.setRentRecordNo(body.getString("rentRecordNo"));	//订单编号
		submitOrderReq.setProv(body.getString("addrProvince"));		//收货地址-省
		submitOrderReq.setCity(body.getString("addrCity"));			//收货地址-市
		submitOrderReq.setArea(body.getString("addrArea"));			//收货地址-区域
		submitOrderReq.setAddress(body.getString("addrDetail"));	//详细地址
		submitOrderReq.setEmergencyContact(body.getString("emergencyContact"));		//紧急联系人
		submitOrderReq.setEmergencyContactPhone(body.getString("emergencyContactPhone"));	//紧急联系人电话
		submitOrderReq.setRelationship(body.getInteger("emergencyRelation"));		//紧急联系人关系
		submitOrderReq.setLat(body.getString("lat"));	//经度	
		submitOrderReq.setLng(body.getString("lng"));	//纬度
		
		submitOrderReq.setImei(body.getString("deviceId"));					//设备imei
		submitOrderReq.setPhoneModel(body.getString("deviceType"));			//机型
		submitOrderReq.setCouponId(body.getString("couponId"));				//优惠券ID
		String couponAmount = body.getString("couponAmount");	//优惠券金额
		if (!StringUtils.isEmpty(couponAmount)) {
			submitOrderReq.setCouponAmount(couponAmount);
		}
		
		return submitOrderReq;
	}
	
	/**
	 * 校验订单申请数据
	 * @param body
	 */
	private void validationApplyData(JSONObject body) {
		//收货地址-省
		String addrProvince = body.getString("addrProvince");
		//收货地址-市
		String addrCity = body.getString("addrCity");
		//收货地址-地区
		String addrArea = body.getString("addrArea");
		//收货地址-详细地址
		String addrDetail = body.getString("addrDetail");
		//紧急联系人
		String emergencyContact = body.getString("emergencyContact");
		//紧急联系人号码
		String emergencyContactPhone = body.getString("emergencyContactPhone");
		//紧急联系人关系	Integer
		Integer emergencyRelation = body.getInteger("emergencyRelation");
		
		//经度
		String lat = body.getString("lat");
		//纬度
		String lng = body.getString("lng");
		//学历
		Integer education = body.getInteger("education");
		
		if (StringUtils.isEmpty(addrProvince)
				|| StringUtils.isEmpty(addrCity)
//				|| StringUtils.isEmpty(addrArea)
				|| StringUtils.isEmpty(addrDetail)
				|| StringUtils.isEmpty(emergencyContact)
				|| StringUtils.isEmpty(emergencyContactPhone)
				|| StringUtils.isEmpty(emergencyRelation)
				|| emergencyRelation == null) {
			throw new AppServiceException(ResponseStatus.DATA_INPUT_ERROR.getCode(), 
					ResponseStatus.DATA_INPUT_ERROR.getMessage());
		}
		
	}
	
	private void updateUserAddress(Long userId, JSONObject body) {
		//收货地址-省
		String addrProvince = body.getString("addrProvince");
		//收货地址-市
		String addrCity = body.getString("addrCity");
		//收货地址-地区
		String addrArea = body.getString("addrArea");
		//收货地址-详细地址
		String addrDetail = body.getString("addrDetail");
		
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setUserId(userId);
		addressInfo.setAddrCity(addrCity);
		addressInfo.setAddrDetail(addrDetail);
		addressInfo.setAddrProvince(addrProvince);
		
		addressInfoService.updateAddressByUserId(addressInfo);
	}
	
	private void updateUserInfo(Long userId, JSONObject body) {
		Integer education = body.getInteger("education");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setEducation(education);
		
		userInfoService.updateUserInfo(userInfo);
	}
	
	/**
	 * 查询订单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryOrderStateList", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<List<OrderDetailResp>> queryOrderStateList(@RequestBody OrderDetailReq orderDetailReq, HttpServletRequest request) {
		log.info("---> execute queryOrderStateList, params: {}", JSONObject.toJSONString(orderDetailReq));
		List<String> list = getUserFields(request, "userId");

		orderDetailReq.setUserId(list.get(0));
	
		ResponseResult<List<OrderDetailResp>> result = rentRecordServiceClient.queryOrderStateList(orderDetailReq);
		log.info("---> execute queryOrderStateList success, result: {}", JSONObject.toJSONString(result));
		return result;
	}
	
	@RequestMapping(value="/queryOrderDetail", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<OrderDetailResp> queryOrderDetail(@RequestBody JSONObject body) {
		log.info("---> execute queryOrderDetail, params: {}", body.toJSONString());
		ResponseResult<OrderDetailResp> result = rentRecordServiceClient.queryOrderDetail(body.getString("rentRecordNo"));
		log.info("---> execute queryOrderDetail success, result: {}", JSONObject.toJSONString(result));
		return result;
	}
	
	@RequestMapping(value="/updateOrderState", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<String> updateOrderState(@RequestBody UpdateOrderStateReq req, HttpServletRequest request) {
		log.info("---> execute updateOrderState, params: {}", JSONObject.toJSONString(req));
		List<String> list = getUserFields(request, "userId", "realName");
		req.setCreateBy(Long.valueOf(list.get(0)));
		req.setCreateMan(list.get(1));
		
		ResponseResult<String> result = rentRecordServiceClient.updateOrderState(req);
		log.info("---> execute updateOrderState success, result: {}", JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * 查看合同信息
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/lookContract", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<Map<String, Object>> lookContract(@RequestBody JSONObject body) {
		log.info("---> execute lookContract start, params: {}", body.toJSONString());
		
		ResponseResult<Map<String, Object>> result = rentRecordServiceClient.lookContract(body.getString("orderNo"));
		log.info("---> execute lookContract end, result: {}", JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * 查看合同信息
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/applySign", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<String> applySign(@RequestBody JSONObject body, HttpServletRequest request) {
		log.info("---> execute applySign start, params: {}", body.toJSONString());
		
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		ResponseResult<AppUser> userResult = userService.queryUserById(userId);
		if (userResult.getErrCode() != 0) {
			return ResponseResult.build(-1, "获取用户信息失败", null);
		}
		AppUser user = userResult.getData();
		
		String lat = body.getString("lat");
		String lng = body.getString("lng");
		String deviceId = body.getString("deviceId");
		String orderNo = body.getString("orderNo");
		
		UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
		updateOrderState.setCreateBy(userId);
		updateOrderState.setCreateMan(user.getRealName());
		updateOrderState.setLat(lat);
		updateOrderState.setLng(lng);
		updateOrderState.setImei(deviceId);
		updateOrderState.setRentRecordNo(orderNo);
		
		ResponseResult<String> result = rentRecordServiceClient.applySign(updateOrderState);
		log.info("---> execute applySign end, result: {}", JSONObject.toJSONString(result));
		return result;
	}
}
