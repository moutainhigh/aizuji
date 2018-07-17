package org.gz.user.service.atom.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.constant.CacheTTLConst;
import org.gz.cache.commom.constant.UserLoginCacheType;
import org.gz.cache.service.captche.CaptcheCacheService;
import org.gz.cache.service.user.UserCacheService;
import org.gz.common.constants.CommonConstant;
import org.gz.common.constants.SmsType;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.StringUtils;
import org.gz.user.commons.UserConstants;
import org.gz.user.commons.UserStatus;
import org.gz.user.configure.AlipayConfigure;
import org.gz.user.configure.AlipayXCXConfigure;
import org.gz.user.configure.OssConfigureProperties;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AddressInfo;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.BrushFaceRecord;
import org.gz.user.entity.ContactInfo;
import org.gz.user.entity.LoginLog;
import org.gz.user.entity.ThirdInfoAlipay;
import org.gz.user.entity.UserInfo;
import org.gz.user.exception.UserServiceException;
import org.gz.user.feign.CouponServiceClient;
import org.gz.user.mapper.AddressInfoMapper;
import org.gz.user.mapper.AppUserMapper;
import org.gz.user.mapper.BrushFaceRecordMapper;
import org.gz.user.mapper.ContactInfoMapper;
import org.gz.user.mapper.LoginLogMapper;
import org.gz.user.mapper.ThirdInfoAlipayMapper;
import org.gz.user.mapper.UserInfoMapper;
import org.gz.user.service.atom.UserAtomService;
import org.gz.user.service.atom.UserInfoAtomService;
import org.gz.user.service.outside.IUploadAliService;
import org.gz.user.supports.EncryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

@Service
@Slf4j
public class UserAtomServiceImpl implements UserAtomService {

	@Autowired
	private UserCacheService userCacheService;
	
	@Autowired
	private IUploadAliService uploadAliService;
	@Autowired
	private CaptcheCacheService captcheCacheService;
	
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Autowired
	private LoginLogMapper loginLogMapper;
	
	@Autowired
	private ContactInfoMapper contactInfoMapper;
	
	@Autowired
	private AddressInfoMapper addressInfoMapper;
	
	@Autowired
	private BrushFaceRecordMapper brushFaceRecordMapper;
	
	@Autowired
	private OssConfigureProperties ossConfigureProperties;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserInfoAtomService userInfoAtomService;
	
	@Autowired
	private ThirdInfoAlipayMapper thirdInfoAlipayMapper;
	
	@Autowired
	private CouponServiceClient couponServiceClient;
	

	@Override
	public String login(JSONObject body) {
		//登录方式 1-密码登录 2-验证码登录
		String loginType = body.getString("loginType");
		String phoneNum = body.getString("phoneNum");
		
		AppUser user = null;
		//检查手机号格式
		checkTelFormatIsValid(phoneNum);
		
		if (!checkTelIsRegisted(phoneNum)) {
			log.error("tel is exist,tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_NOT_REGISTED.getCode(),
					ResponseStatus.TEL_NOT_REGISTED.getMessage());
		}
		
		if (CommonConstant.USER_LOGIN_TYPE_SMS.equals(loginType)) {
			//短信验证码登录
			String smsCode = body.getString("smsCode");
			//检查短信验证码是否正确
			checkSmsCodeIsValid(phoneNum, smsCode, SmsType.LOGIN);
		} else if (CommonConstant.USER_LOGIN_TYPE_PWD.equals(loginType)) {
			//密码登录
			String password = body.getString("loginPassword");
			user = appUserMapper.findUserByTel(phoneNum);
			if (!user.getLoginPassword().equals(EncryUtils.encryptSHA256(password))) {
				throw new UserServiceException(ResponseStatus.LOGIN_FAILED.getCode(), 
						ResponseStatus.LOGIN_FAILED.getMessage()); 
			}
		} else {
			throw new UserServiceException(ResponseStatus.DATA_INPUT_ERROR.getCode(), 
					ResponseStatus.DATA_INPUT_ERROR.getMessage());
		}
		
		if (user == null) {
			user = appUserMapper.findUserByTel(phoneNum);
		}
		
		//补充未完整信息、设备编号、操作系统等属性
		supplementUserInfo(user, body);
		
		//缓冲用户信息
		String token = cacheUserInfo(user, UserLoginCacheType.LOGIN, 
				CacheTTLConst.LOGIN_PWD_CACHE_TTL, CommonConstant.LOGIN_TYPE_PWD);
		
		//添加登录日志
		addLoginLog(user, body, token);
		
		//删除短信登录验证码
		captcheCacheService.removeTelCaptcha(phoneNum, SmsType.LOGIN);
		
		return token;
	}

	private void supplementUserInfo(AppUser user, JSONObject body) {
		try {
			boolean needModify = false;
			
			String deviceId = body.getString("deviceId");
			String deviceType = body.getString("deviceType");
			String osType = body.getString("osType");
			String appVersion = body.getString("appVersion");
			String sourceType = body.getString("sourceType");
			String channelType = body.getString("channelType");
			
			AppUser modufyUser = new AppUser();
			modufyUser.setUserId(user.getUserId());
			
			if (StringUtils.isEmpty(user.getDeviceId())
					&& !StringUtils.isEmpty(deviceId)) {
				needModify = true;
				modufyUser.setDeviceId(deviceId);
			}
			if (StringUtils.isEmpty(user.getDeviceType())
					&& !StringUtils.isEmpty(deviceType)) {
				needModify = true;
				modufyUser.setDeviceType(deviceType);
			}
			if (StringUtils.isEmpty(user.getOsType())
					&& !StringUtils.isEmpty(osType)) {
				needModify = true;
				modufyUser.setOsType(osType);
			}
			if (StringUtils.isEmpty(user.getAppVersion())
					&& !StringUtils.isEmpty(appVersion)) {
				needModify = true;
				modufyUser.setAppVersion(appVersion);
			}
			if (StringUtils.isEmpty(user.getSourceType())
					&& !StringUtils.isEmpty(sourceType)) {
				needModify = true;
				modufyUser.setSourceType(sourceType);
			}
			if (StringUtils.isEmpty(user.getChannelType())
					&& !StringUtils.isEmpty(channelType)) {
				needModify = true;
				modufyUser.setChannelType(channelType);
			}
			
			if (needModify) {
				appUserMapper.updateByPrimaryKeySelective(modufyUser);
			}
		} catch (Exception e) {
			log.error("--> supplementUserInfo failed, e: {}", e);
		}
	}

	/**
	 * 添加登录日志
	 * @param user
	 * @param body
	 * @param token 
	 */
	private void addLoginLog(AppUser user, JSONObject body, String token) {
		try {
			LoginLog log = new LoginLog();
			log.setAppVersion(body.getString("appVersion"));
			log.setChannelType(body.getString("channelType"));
			log.setCreateTime(new Date());
			log.setDeviceId(body.getString("deviceId"));
			log.setDeviceType(body.getString("deviceType"));
			log.setIpAddr(body.getString("ipAddr"));
			log.setOsType(body.getString("osType"));
			log.setToken(token);
			log.setUserId(user.getUserId());
			loginLogMapper.insertSelective(log);
		} catch (Exception e) {
			log.error("addLoginLog failed, e: {}", e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public String register(JSONObject body) {
		String phoneNum = body.getString("phoneNum");	//手机号
		String smsCode = body.getString("smsCode");		//短信验证码
		String password = body.getString("loginPassword");	//登录密码
		
		if (StringUtils.isEmpty(phoneNum)) {
			log.error("phoneNum is empty.");
			throw new UserServiceException(ResponseStatus.DATA_INPUT_ERROR.getCode(), 
					ResponseStatus.DATA_INPUT_ERROR.getMessage());
		}
		if (StringUtils.isEmpty(smsCode)) {
			log.error("smsCode is empty.");
			throw new UserServiceException(ResponseStatus.DATA_INPUT_ERROR.getCode(), 
					ResponseStatus.DATA_INPUT_ERROR.getMessage());
		}
		if (StringUtils.isEmpty(password)) {
			log.error("password is empty.");
			throw new UserServiceException(ResponseStatus.DATA_INPUT_ERROR.getCode(), 
					ResponseStatus.DATA_INPUT_ERROR.getMessage());
		}
		
		//检查手机号格式
		checkTelFormatIsValid(phoneNum);
		
		//检查密码格式
		checkPasswordFormatIsValid(password);
		
		//检查短信验证码
		checkSmsCodeIsValid(phoneNum, smsCode, SmsType.REGISTER);
		
		//检查手机号是否已注册
		if (checkTelIsRegisted(phoneNum)) {
			log.error("tel is exist,tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_REGISTED.getCode(),
					ResponseStatus.TEL_REGISTED.getMessage());
		}
		
		//保存注册用户
		AppUser user = saveRegisterUser(phoneNum, password, body, null, null);
		
		//缓存用户
		String token = cacheUserInfo(user, UserLoginCacheType.REGISTER, 
				CacheTTLConst.LOGIN_PWD_CACHE_TTL, CommonConstant.LOGIN_TYPE_PWD);
		
		//添加登录日志
		addLoginLog(user, body, token);
		
		//删除注册短信验证码
		captcheCacheService.removeTelCaptcha(phoneNum, SmsType.REGISTER);
		
		//添加注册优惠券
		try {
			saveUserRegisterCoupon(user.getUserId(), phoneNum);
		} catch (Exception e) {
			log.error("----->>【用户注册】---添加注册优惠券失败");
		}
		return token;
	}
	
	@Override
	public void logout(String token) {
		userCacheService.removeUserLoginCache(token);
	}
	
	@Override
	public void modifyPassword(JSONObject body) {
		Long userId = body.getLong("userId");
		String phoneNum = body.getString("phoneNum");	//手机号
		String smsCode = body.getString("smsCode");		//短信验证码
		String password = body.getString("loginPassword");	//新登录密码
		String passwordConfirm = body.getString("loginPasswordConfirm");	//确认登录密码
		
		if (StringUtils.isEmpty(password) 
				|| StringUtils.isEmpty(passwordConfirm) 
				|| !password.equals(passwordConfirm)) {
			throw new UserServiceException(ResponseStatus.PASSWORD_CONFIRM_ERROR.getCode(),
					ResponseStatus.PASSWORD_CONFIRM_ERROR.getMessage());
		}
		
		//检查手机号格式
		checkTelFormatIsValid(phoneNum);
		
		//检查密码格式
		checkPasswordFormatIsValid(password);
		
		//检查短信验证码
		checkSmsCodeIsValid(phoneNum, smsCode, SmsType.MODIFY_PWD);
		
		//修改密码
		AppUser user = appUserMapper.selectByPrimaryKey(userId);
		if (user != null) {
			user.setLoginPassword(EncryUtils.encryptSHA256(password));
			appUserMapper.updateByPrimaryKeySelective(user);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateUser(AppUser user) {
		appUserMapper.updateByPrimaryKeySelective(user);
		
		if (!StringUtils.isEmpty(user.getAddrProvince())) {
			AddressInfo addressInfo = addressInfoMapper.queryAddressByUserId(user.getUserId());
			
			String addrProvince = user.getAddrProvince();
			String addrCity = user.getAddrCity();
			String addArea = user.getAddrArea();
			String addrDetail = user.getAddrDetail();
			
			if (addressInfo == null) {
				addressInfo = new AddressInfo();
				addressInfo.setUserId(user.getUserId());
				
				addressInfo.setAddrProvince(StringUtils.isEmpty(addrProvince) ? "" : addrProvince);
				addressInfo.setAddrCity(StringUtils.isEmpty(addrCity) ? "" : addrCity);
				addressInfo.setAddrDetail(StringUtils.isEmpty(addrDetail) ? "" : addrDetail);
				addressInfo.setAddrArea(user.getAddrArea());
				addressInfo.setAddrStatus(1);
				addressInfo.setCreateTime(new Date());
				addressInfoMapper.insert(addressInfo);
			} else {
				addressInfo.setAddrProvince(StringUtils.isEmpty(addrProvince) ? "" : addrProvince);
				addressInfo.setAddrCity(StringUtils.isEmpty(addrCity) ? "" : addrCity);
				addressInfo.setAddrArea(StringUtils.isEmpty(addArea) ? "" : addArea);
				addressInfo.setAddrDetail(StringUtils.isEmpty(addrDetail) ? "" : addrDetail);
				addressInfo.setUpdateTime(new Date());
				addressInfoMapper.updateAddressByUserId(addressInfo);	
			}
		}
		
		userCacheService.updateUserInfoCache(user.getUserId().toString(), tidyUserCacheField(user));
	}

	@Override
	public AppUser queryUserById(Long userId) {
		log.info("execute queryUserById, userId: {}", userId);
		AppUser user = appUserMapper.selectByPrimaryKey(userId);
		AddressInfo addressInfo = addressInfoMapper.queryAddressByUserId(userId);
		if (addressInfo != null) {
			user.setAddrProvince(addressInfo.getAddrProvince());
			user.setAddrCity(addressInfo.getAddrCity());
			user.setAddrDetail(addressInfo.getAddrDetail());
		}
		if (!StringUtils.isEmpty(user.getAvatar()) && !user.getAvatar().contains("alipay")) {
			user.setAvatar(uploadAliService.getAccessUrlPrefix() + user.getAvatar());
		}
		return user;
	}
	
	/**
	 * 缓存用户信息
	 * @param user
	 */
	private String cacheUserInfo(AppUser user, Integer loginCacheType, int ttl, String loginType) {
		String token = UUID.randomUUID().toString().replace("-", "");
		userCacheService.setLoginUserCache(token, tidyUserCacheField(user), loginCacheType, ttl, loginType);
		return token;
	}

	/**
	 * 保存注册用户信息
	 * @param phoneNum
	 * 		手机号
	 * @param password
	 * 		密码
	 * @param body
	 * 		其他属性
	 * @param empowerType
	 * 		授权类型
	 * @param empowerId
	 * 		授权唯一标识
	 * @return
	 */
	private AppUser saveRegisterUser(String phoneNum, String password, JSONObject body, String empowerType, String empowerId) {
		AppUser user = new AppUser();
		user.setPhoneNum(phoneNum);
		user.setLoginPassword(EncryUtils.encryptSHA256(password));
		user.setUserStatus(UserStatus.NORMAL.getCode());
		
		String deviceId = body.getString("deviceId");
		String deviceType = body.getString("deviceType");
		String osType = body.getString("osType");
		String appVersion = body.getString("appVersion");
		String sourceType = body.getString("sourceType");
		String channelType = body.getString("channelType");
		
		user.setDeviceId(StringUtils.isEmpty(deviceId) ? "" : deviceId);
		user.setDeviceType(StringUtils.isEmpty(deviceType) ? "" : deviceType);
		user.setOsType(StringUtils.isEmpty(osType) ? "" : osType);
		user.setAppVersion(StringUtils.isEmpty(appVersion) ? "" : appVersion);
		user.setSourceType(StringUtils.isEmpty(sourceType) ? "" : sourceType);
		user.setChannelType(StringUtils.isEmpty(channelType) ? "" : channelType);
		
		if (UserConstants.EMPOWER_TYPE_WEIXIN.equals(empowerType)) {
			user.setAuthWeixinOpenId(empowerId);
		} else if (UserConstants.EMPOWER_TYPE_ALIPAY.equals(empowerType)) {
			user.setAuthAlipayUserId(empowerId);
		}
		user.setCreateTime(new Date());
		appUserMapper.insertSelective(user);
		return user;
	}
	
	/**
	 * 检查手机号格式
	 * @param phoneNum
	 * @return
	 */
	private void checkTelFormatIsValid(String phoneNum) {
		if (!StringUtils.checkTel(phoneNum)) {
			log.error("register phoneNum is invild,tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_FORMAT_ERROR.getCode(),
					ResponseStatus.TEL_FORMAT_ERROR.getMessage());
		}
	}
	
	/**
	 * 检查手机号是否已注册
	 * @param phoneNum
	 */
	private boolean checkTelIsRegisted(String phoneNum) {
		AppUser user = appUserMapper.findUserByTel(phoneNum);
		return user != null;
	}
	
	/**
	 * 检查登录密码格式
	 * @param password
	 */
	private void checkPasswordFormatIsValid(String password) {
		if (!StringUtils.checkPassword(password)) {
			log.error("password format error, pwd :{}", password);
			throw new UserServiceException(ResponseStatus.PASSWORD_FORMAT_ERROR.getCode(),
					ResponseStatus.PASSWORD_FORMAT_ERROR.getMessage());
		}
	}
	
	/**
	 * 检查短信验证码
	 * @param smsCode
	 * @param login 
	 */
	private void checkSmsCodeIsValid(String phoneNum, String smsCode, SmsType smsType) {
		String value = captcheCacheService.getTelCaptcha(phoneNum, smsType);
		
		if (StringUtils.isEmpty(value)) {
			throw new UserServiceException(ResponseStatus.CAPTCHA_EXPIRED.getCode(),
					ResponseStatus.CAPTCHA_EXPIRED.getMessage());
		}
		if (!smsCode.equals(value)) {
			throw new UserServiceException(ResponseStatus.CAPTCHA_ERROR.getCode(),
					ResponseStatus.CAPTCHA_ERROR.getMessage());
		}
		
	}
	
	/**
	 * 获取用户缓存字段
	 * @param user
	 * @return
	 */
	private Map<String, String> tidyUserCacheField(AppUser user) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", user.getUserId().toString());
		if (!StringUtils.isEmpty(user.getPhoneNum())) {
			map.put("phoneNum", user.getPhoneNum());
		}
		if (!StringUtils.isEmpty(user.getAvatar())) {
			map.put("avatar", user.getAvatar());
		}
		if (!StringUtils.isEmpty(user.getNickName())) {
			map.put("nickName", user.getNickName());
		}
		if (!StringUtils.isEmpty(user.getRealName())) {
			map.put("realName", user.getRealName());
		}
		if (!StringUtils.isEmpty(user.getIdNo())) {
			map.put("idNo", user.getIdNo());
		}
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void saveContacts(JSONObject body) {
		Long userId = body.getLong("userId");
		if (!body.containsKey("contacts")) {
			return ;
		}
		String contactContent = body.getString("contacts");
		if (StringUtils.isEmpty(contactContent)) {
			return ;
		}
		log.info("saveContacts==>  contactContent: {}", contactContent);
		JSONArray contactArr = body.getJSONArray("contacts");
		List<ContactInfo> saveList = new ArrayList<>();
		
		List<ContactInfo> contactList = JSON.parseObject(
				contactArr.toJSONString(), 
				new TypeReference<List<ContactInfo>>(){}.getType()
		);
		if (contactList == null || contactList.size() == 0) {
			return ;
		}
		//格式化手机格式
		formatContacts(contactList);
		
		List<ContactInfo> dbSaves = contactInfoMapper.selectContactByUserId(userId);
		
		for (ContactInfo contact : contactList) {
			if (StringUtils.isEmpty(contact.getContactPhone())) {
				continue;
			}
			if (StringUtils.isEmpty(contact.getContactName())) {
				contact.setContactName("unknow");
			}
			if (dbSaves != null && dbSaves.size() > 0) {
				boolean isFind = false;
	            for (ContactInfo dbContact : dbSaves) {
	                if (contact != null && contact.getContactName().equals(dbContact.getContactName()) &&
	                        contact.getContactPhone().equals(dbContact.getContactPhone())) {
	                    isFind = true;
	                    break;
	                }
	            }
	            if (!isFind && contact != null) {
	                contact.setUserId(userId);
	                contact.setCreateTime(new Date());
	                saveList.add(contact);
	            }	
			} else {
				contact.setUserId(userId);
                contact.setCreateTime(new Date());
                saveList.add(contact);
			}
        }
		
		if (saveList.size() > 0) {
        	//去掉手机号中的 +86, -, 空格
            //formatContacts(saveList);
            
        	int contactSize = saveList.size();
        	log.info("wait save contact list size : {}", contactSize);
        	
        	int batchNum = contactSize % UserConstants.CONTACT_SAVE_BATCH_SIZE == 0 ? 
        			contactSize / UserConstants.CONTACT_SAVE_BATCH_SIZE : (contactSize / UserConstants.CONTACT_SAVE_BATCH_SIZE + 1);
        	log.info("save contact by batch, batchNum: {}", batchNum);
        	
        	for (int i=0; i<batchNum; i++) {
        		int fromindex = i * UserConstants.CONTACT_SAVE_BATCH_SIZE;
        		int endindex = (i + 1) * UserConstants.CONTACT_SAVE_BATCH_SIZE;
        		if (endindex > contactSize) {
        			endindex = contactSize;
        		}
        		
        		List<ContactInfo> partList = saveList.subList(fromindex, endindex);
        		contactInfoMapper.insertBatch(partList);
        	}
        }
	}

	/**
	 * 格式化手机号码，去掉+86，替换非数字字符
	 * @param saveList
	 */
	private void formatContacts(List<ContactInfo> saveList) { 
    	for (ContactInfo contact : saveList) {
    		String contactName = contact.getContactName();
    		if (!StringUtils.isEmpty(contactName)) {
    			if (contactName.length() > UserConstants.CONTACT_NAME_MAX_LENGTH) {
    				contact.setContactName(contactName.substring(0, UserConstants.CONTACT_NAME_MAX_LENGTH));
    			}
    		}
    		
    		String rawPhone = contact.getContactPhone();
    		if (!StringUtils.isEmpty(rawPhone)) {
    			rawPhone = rawPhone.replace("+86", "");
    			rawPhone = rawPhone.replaceAll("[^\\d]", "");
    			
    			if (rawPhone.length() > UserConstants.CONTACT_PHONE_MAX_LENGTH) {
    				rawPhone = rawPhone.substring(0, UserConstants.CONTACT_PHONE_MAX_LENGTH);
    			}
    			contact.setContactPhone(rawPhone);
    		}
    	}
    }

	@Override
	public Map<String, String> thirdPartyEmpower(JSONObject body) {
		String empowerType = body.getString("empowerType");
		if (UserConstants.EMPOWER_TYPE_WEIXIN.equals(empowerType)) {
			return null;
		} else if (UserConstants.EMPOWER_TYPE_ALIPAY.equals(empowerType)) {
			return processAlipayEmpower(body);
		} else {
			throw new UserServiceException(ResponseStatus.DATA_INPUT_ERROR.getCode(), 
					ResponseStatus.DATA_INPUT_ERROR.getMessage());
		}
	}
	
	private Map<String, String> processAlipayEmpower(JSONObject body) {
		log.info("=========>" + body.toJSONString());
		Map<String, String> map = new HashMap<>();
		map.put("empowerType", UserConstants.EMPOWER_TYPE_ALIPAY);
		
		String authCode = body.getString("auth_code");
		
		Map<String, String> userData = requestShareUserInfo(authCode);
		
		String alipayUserId = userData.get("alipayUserId");
		map.put("empowerId", alipayUserId);
		//status 1-登录成功  2-未登录
		AppUser user = appUserMapper.selectByAlipayUserId(alipayUserId);
		if (user == null) {
			//未绑定
			map.put("status", UserConstants.EMPOWER_STATUS_BIND);
			map.put("avatar", userData.get("avatar"));
			map.put("nickName", userData.get("nickName"));
		} else {
			//已绑定,直接登录
			String token = cacheUserInfo(user, UserLoginCacheType.LOGIN,
					CacheTTLConst.LOGIN_THIRDPARTY_CACHE_TTL, CommonConstant.LOGIN_TYPE_THIRD_PARTY);
			map.put("status", UserConstants.EMPOWER_STATUS_SUCCESS);
			map.put("token", token);
			
			//补充未完整信息、设备编号、操作系统等属性
			supplementUserInfo(user, body);
			
			//添加登录日志
			addLoginLog(user, body, token);
		}
		return map;
	}

	/**
	 * 获取支付宝用户信息
	 * @param auth_code
	 * @param isXcx
	 * 			是否为小程序
	 * @return
	 */
	private Map<String, String> requestShareUserInfo(String auth_code) {
		Map<String, String> userData = new HashMap<>();
		//获取用户信息授权
		AlipayClient alipayClient = new DefaultAlipayClient(
				AlipayConfigure.GATE_WAY, 
				AlipayConfigure.APPID, 
				AlipayConfigure.RSA_PRIVATE_KEY, 
				AlipayConfigure.FORMAT, 
				AlipayConfigure.CHARSET, 
				AlipayConfigure.ALIPAY_PUBLIC_KEY, 
				AlipayConfigure.SIGNTYPE);
	    
		AlipaySystemOauthTokenRequest requestLogin2 = new AlipaySystemOauthTokenRequest();
	    requestLogin2.setCode(auth_code);
	    requestLogin2.setGrantType("authorization_code");
	    try {
	    	AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(requestLogin2);
	    	log.info("--->【支付宝登录】--execute AlipaySystemOauthTokenResponse, get AccessToken: {}", oauthTokenResponse.getAccessToken());
	    	if (StringUtils.isEmpty(oauthTokenResponse.getAccessToken())) {
	    		throw new UserServiceException(-1, "授权码code无效");
	    	}
	    	
	    	//调用接口获取用户信息
	    	AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
	    	try {
	    		AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
	    		//头像
	    		String avatar = userinfoShareResponse.getAvatar();
	    		//userId
	    		String userId = userinfoShareResponse.getUserId();
	    		//昵称
	    		String nickName = userinfoShareResponse.getNickName();
	    		//性别  f：女性；m：男性
	    		String gender = userinfoShareResponse.getGender();
	    		//是否通过实名认证 T-是
	    		String isCertified = userinfoShareResponse.getIsCertified();
	    		//省份
	    		String province = userinfoShareResponse.getProvince();
	    		//城市
	    		String city = userinfoShareResponse.getCity();
	    		//是否是学生 T-是
	    		String isStudentCertified = userinfoShareResponse.getIsStudentCertified();
	    		
	    		AppUser user = appUserMapper.selectByAlipayUserId(userId);
	    		if (user != null) {
	    			boolean updateUser = false;
	    			AppUser modifyUser = new AppUser();
	    			if (StringUtils.isEmpty(user.getAvatar())
	    					&& !StringUtils.isEmpty(avatar)) {
	    				updateUser = true;
	    				modifyUser.setAvatar(avatar);
	    			}
	    			if (StringUtils.isEmpty(user.getNickName())
	    					&& !StringUtils.isEmpty(nickName)) {
	    				updateUser = true;
	    				modifyUser.setNickName(nickName);
	    			}
	    			if (updateUser) {
	    				modifyUser.setUserId(user.getUserId());
	    				appUserMapper.updateByPrimaryKeySelective(modifyUser);
	    			}
	    		}
	    		
	    		boolean isUpdate = false;
	    		ThirdInfoAlipay record = thirdInfoAlipayMapper.selectByAlipayUserId(userId);
	    		if (record == null) {
	    			record = new ThirdInfoAlipay();
	    			record.setCreateTime(new Date());
	    		} else {
	    			isUpdate = true;
	    			record.setUpdateTime(new Date());
	    		}
	    		record.setAlipayUserId(userId);
	    		record.setAvatar(avatar);
	    		record.setCity(city);
	    		record.setGender(gender);
	    		record.setIsCertified(isCertified);
	    		record.setIsStudentCertified(isStudentCertified);
	    		record.setNickName(nickName);
	    		record.setProvince(province);	
	    		
	    		if (isUpdate) {
	    			thirdInfoAlipayMapper.updateByPrimaryKeySelective(record);
	    		} else {
	    			thirdInfoAlipayMapper.insertSelective(record);
	    		}
	    		
	    		userData.put("alipayUserId", userId);
	    		userData.put("avatar", avatar);
	    		userData.put("nickName", nickName);
	    	} catch (AlipayApiException e) {
	    		log.error("--->【支付宝登录】 ---query alipay user share info failed, e: {}", e);
	    		throw new UserServiceException(-1, "授权失败");
	    	}
	    } catch (Exception e) {
	    	log.error("--->【支付宝登录】 ---execute requestShareUserInfo failed, e: {}", e);
	    	throw new UserServiceException(-1, "授权失败");
	    }
	    return userData;
	}

	@Override
	public Map<String, String> bindPhone(JSONObject body) {
		Map<String, String> map = new HashMap<>();
		//授权类型
		String empowerType = body.getString("empowerType");
		//手机号
		String phoneNum = body.getString("phoneNum");
		//授权唯一标识
		String empowerId = body.getString("empowerId");
		//短信验证码
		String smsCode = body.getString("smsCode");
		//登录密码
		String loginPassword = body.getString("loginPassword");
		
		//检查手机号格式
		checkTelFormatIsValid(phoneNum);
		
		//检查短信验证码
		checkSmsCodeIsValid(phoneNum, smsCode, SmsType.THIRD_LOGIN);
		
		try {
			AppUser user = appUserMapper.findUserByTel(phoneNum);
			if (user == null) {
				//未注册-注册
				user = saveRegisterUser(phoneNum, loginPassword, body, empowerType, empowerId);
				
				//添加注册优惠券
				try {
					saveUserRegisterCoupon(user.getUserId(), phoneNum);
				} catch (Exception e) {
					log.error("----->>【用户注册】---添加注册优惠券失败");
				}
			} else {
				//已注册-绑定
				AppUser bindUser = new AppUser();
				bindUser.setUserId(user.getUserId());
				if (UserConstants.EMPOWER_TYPE_WEIXIN.equals(empowerType)) {
					bindUser.setAuthWeixinOpenId(empowerId);
				} else if (UserConstants.EMPOWER_TYPE_ALIPAY.equals(empowerType)) {
					bindUser.setAuthAlipayUserId(empowerId);
				}
				appUserMapper.updateByPrimaryKeySelective(bindUser);
			}
			
			updateThirdInfo(empowerType, phoneNum, empowerId, user.getUserId());
			
			//登录信息
			String token = cacheUserInfo(user, UserLoginCacheType.LOGIN,
					CacheTTLConst.LOGIN_THIRDPARTY_CACHE_TTL,
					CommonConstant.LOGIN_TYPE_THIRD_PARTY);
			//补充未完整信息、设备编号、操作系统等属性
			supplementUserInfo(user, body);
			
			//添加登录日志
			addLoginLog(user, body, token);
			
			map.put("token", token);
			map.put("status", UserConstants.BIND_PHONE_SUCCESS);
		} catch (Exception e) {
			log.error("bindPhone failed, e: {}", e);
			map.put("status", UserConstants.BIND_PHONE_FAILED);
			throw new UserServiceException(-1, "手机号绑定失败");
		}
		return map;
	}

	/**
	 * 更新第三方信息表
	 * @param empowerType
	 * 			登录类型
	 * @param phoneNum
	 * 			手机号
	 * @param empowerId
	 * 			登录唯一标识 支付宝userId/微信openId
	 * @param userId
	 */
	private void updateThirdInfo(String empowerType, String phoneNum,
			String empowerId, Long userId) {
		ThirdInfoAlipay record = null;
		if (UserConstants.EMPOWER_TYPE_ALIPAY.equals(empowerType)) {
			record = thirdInfoAlipayMapper.selectByAlipayUserId(empowerId);
			record.setUserId(userId);
			record.setUpdateTime(new Date());
			thirdInfoAlipayMapper.updateByPrimaryKeySelective(record);	
		}
		AppUser user = appUserMapper.selectByPrimaryKey(userId);
		
		boolean updateUser = false;
		AppUser modifyUser = new AppUser();
		if (StringUtils.isEmpty(user.getAvatar())
				&& !StringUtils.isEmpty(record.getAvatar())) {
			updateUser = true;
			modifyUser.setAvatar(record.getAvatar());
		}
		if (StringUtils.isEmpty(user.getNickName())
				&& !StringUtils.isEmpty(record.getNickName())) {
			updateUser = true;
			modifyUser.setNickName(record.getNickName());
		}
		if (updateUser) {
			modifyUser.setUserId(user.getUserId());
			appUserMapper.updateByPrimaryKeySelective(modifyUser);
		}
	}

	@Override
	public void saveOcrResult(JSONObject body) {
		log.info("execute saveOcrResult, params: {}", body.toJSONString());
		
		Long userId = body.getLong("userId");
		String residenceAddress = body.getString("residenceAddress");
		String issuingAuthority = body.getString("issuingAuthority");
		String effectiveStartDate = body.getString("effectiveStartDate");
		String effectiveEndDate = body.getString("effectiveEndDate");
		String orderNo = body.getString("orderNo");
		String mobile = body.getString("mobile");
		String realName = body.getString("realName");
		String idNo = body.getString("idNo");
		
		AppUser user = new AppUser();
		user.setUserId(userId);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(idNo)) {
			user.setIdNo(idNo);
		}
		if (!StringUtils.isEmpty(realName)) {
			user.setRealName(realName);
		}
		if (!StringUtils.isEmpty(residenceAddress)) {
			user.setResidenceAddress(residenceAddress);
		}
		if (!StringUtils.isEmpty(issuingAuthority)) {
			user.setIssuingAuthority(issuingAuthority);
		}
		if (!StringUtils.isEmpty(effectiveStartDate)) {
			user.setEffectiveStartDate(DateUtils.getDate(effectiveStartDate, format));
		}
		if (!StringUtils.isEmpty(effectiveEndDate)) {
			Date endDate = DateUtils.getDate(effectiveEndDate, format);
			if (endDate.getTime() <= System.currentTimeMillis()) {
				throw new UserServiceException(1521, "身份证已过期");
			}
			user.setEffectiveEndDate(endDate);
		}
		user.setRealnameCertState(1);
		
		String cardSexFlag = idNo.substring(idNo.length()-2, idNo.length()-1);
		Integer sexFlag = Integer.valueOf(cardSexFlag);
		Integer modVal = sexFlag % 2;
		//1-男2-女
		if (modVal == 0) {
			user.setGender(2);
		} else {
			user.setGender(1);
		}
		appUserMapper.updateByPrimaryKeySelective(user);
		
		//update user extend info
		updateUserExtendInfo(userId, idNo);
		
		//添加ocr记录
		if (!StringUtils.isEmpty(orderNo)) {
			BrushFaceRecord record = new BrushFaceRecord();
			record.setBrushStatus(1);
			record.setBrushTime(new Date());
			record.setFetchState(0);
			record.setMobile(mobile);
			record.setOrderNo(orderNo);
			record.setUserId(userId);
			brushFaceRecordMapper.insert(record);
		}
	}
	
	/**
	 * 更新用户信息-user_info
	 * @param idNo
	 */
	private void updateUserExtendInfo(Long userId, String idNo) {
		Date birthday = processIdNoToBirthday(idNo);
		if (birthday == null) {
			return ;
		}
		
		Calendar start = Calendar.getInstance();
		start.setTime(birthday);
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		
		int age = now.get(Calendar.YEAR) - start.get(Calendar.YEAR);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setBirthday(birthday);
		userInfo.setAge(age);
		
		userInfoMapper.updateByUserIdSelective(userInfo);
	}

	/**
	 * 从身份证获取出生年月日
	 * @param idNo
	 * @return
	 */
	private Date processIdNoToBirthday(String idNo) {
		try {
			if (!StringUtils.isEmpty(idNo)) {
				String birthday = idNo.substring(6, 14);
				if (birthday != null && birthday.length() == 8) {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					return DateUtils.getDate(birthday, format);
				}
			}
		} catch (Exception e) {
			log.error("---> processIdNoToBirthday failed, idNo: {}", idNo);
		}
		return null;
	}

	@Override
	public void resetPassword(AppUser user) {
		if (user.getUserId() == null) {
			throw new UserServiceException("用户ID为null");
		}
		
		String resetPwd = user.getLoginPassword();
		if (StringUtils.isEmpty(resetPwd)) {
			throw new UserServiceException("重置密码为null");
		}
		
		user.setLoginPassword(EncryUtils.encryptSHA256(resetPwd));
		appUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public UserInfoDto queryByCondition(AppUser condition) {
		List<AppUser> users = appUserMapper.queryByCondition(condition);
		if (users != null && !users.isEmpty()) {
			AppUser user = users.get(0);
		
			return userInfoAtomService.queryByUserId(user.getUserId());
		}
		return null;
	}

	@Override
	public AppUser queryUserByMobile(String mobile) {
		return appUserMapper.queryUserByMobile(mobile);
	}

	@Override
	public void saveH5FaceResult(JSONObject body) {
		Long userId = body.getLong("userId");
		String orderNo = body.getString("orderNo");
		String mobile = body.getString("mobile");
		
		AppUser modifyUser = new AppUser();
		modifyUser.setUserId(userId);
		modifyUser.setRealnameCertState(1);	//认证成功
		appUserMapper.updateByPrimaryKeySelective(modifyUser);
		
		//添加ocr记录
		if (!StringUtils.isEmpty(orderNo)) {
			BrushFaceRecord record = new BrushFaceRecord();
			record.setBrushStatus(1);
			record.setBrushTime(new Date());
			record.setFetchState(0);
			record.setMobile(mobile);
			record.setOrderNo(orderNo);
			record.setUserId(userId);
			brushFaceRecordMapper.insert(record);
		}
	}
	
	/**
	 * 保存用户注册优惠券
	 * @param userId
	 * @param phoneNum
	 */
	private void saveUserRegisterCoupon(Long userId, String phoneNum) {
		JSONObject body = new JSONObject();
		body.put("userId", userId);
		body.put("phoneNum", phoneNum);
		couponServiceClient.useRegisterGiveCoupon(body);
	}

	@Override
	public Map<String, String> thirdPartyXcxEmpower(JSONObject body) {
		log.info("=========>execute thirdPartyXcxEmpower, body: {}", body.toJSONString());
		Map<String, String> map = new HashMap<>();
		
		String authCode = body.getString("auth_code");
		AlipayClient alipayClient = new DefaultAlipayClient(
				AlipayXCXConfigure.GATE_WAY, 
				AlipayXCXConfigure.APPID, 
				AlipayXCXConfigure.RSA_PRIVATE_KEY, 
				AlipayXCXConfigure.FORMAT, 
				AlipayXCXConfigure.CHARSET, 
				AlipayXCXConfigure.ALIPAY_PUBLIC_KEY, 
				AlipayXCXConfigure.SIGNTYPE);
		
		AlipaySystemOauthTokenRequest requestLogin2 = new AlipaySystemOauthTokenRequest();
	    requestLogin2.setCode(authCode);
	    requestLogin2.setGrantType("authorization_code");
		
	    try {
	    	AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(requestLogin2);
	    	log.info("--->【支付宝登录】--execute AlipaySystemOauthTokenResponse, resp: {}", JSONObject.toJSONString(oauthTokenResponse));
	    	if (!oauthTokenResponse.isSuccess()) {
	    		throw new UserServiceException(-1, "获取支付宝用户ID失败");
	    	}
	    	map.put("alipayUserId", oauthTokenResponse.getUserId());
	    } catch (Exception e) {
	    	log.error("--->【支付宝登录】 ---execute requestShareUserInfo failed, e: {}", e);
	    	throw new UserServiceException(-1, "授权失败");
	    }
		return map;
	}
}
