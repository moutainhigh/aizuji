package org.gz.app.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.gz.app.feign.UploadAliService;
import org.gz.common.constants.CommonConstant;
import org.gz.common.constants.OSSBucketConst;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.IPUtil;
import org.gz.common.utils.SessionUtil;
import org.gz.common.utils.StringUtils;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends AppBaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UploadAliService uploadAliService;
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> login(@RequestBody JSONObject body, 
			HttpServletRequest request, HttpServletResponse response) {
		body.put("ipAddr", IPUtil.getRemoteAddress(request));

		ResponseResult<String> result = userService.login(body);
		
		SessionUtil.addCookie(result.getData(), response);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> register(@RequestBody JSONObject body, 
			HttpServletRequest request, HttpServletResponse response) {
		body.put("ipAddr", IPUtil.getRemoteAddress(request));
		
		ResponseResult<String> result =  userService.register(body);
		
		SessionUtil.addCookie(result.getData(), response);
		
		return result;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> logout(HttpServletRequest request) {
		
		String token = SessionUtil.getToken(request);
		
		ResponseResult<String> result =  userService.logout(token);
		
		return result;
	}
	
	@RequestMapping(value="/loadUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<AppUser> loadUser(HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		ResponseResult<AppUser> result =  userService.queryUserById(Long.valueOf(list.get(0)));
		return result;
	}
	
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> updateUser(@RequestBody AppUser user, HttpServletRequest request) {
		if (!StringUtils.isEmpty(user.getNickName())
				&& user.getNickName().length() > 15) {
			return ResponseResult.build(-1, "昵称长度过长", null);
		}
		
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		user.setUserId(userId);
		ResponseResult<?> result =  userService.updateUser(user);
		
		return result;
	}
	
	@RequestMapping(value="/modifyPassword", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> modifyPassword(@RequestBody JSONObject body, HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		body.put("userId", userId);
		ResponseResult<?> result =  userService.modifyPassword(body);
		
		return result;
	}
	
	/**
	 * 上传用户头像
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadAvatar", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> uploadAvatar(@RequestParam MultipartFile file, 
			HttpServletRequest request) {	
		ResponseResult<?> result = null;
		try {
			//上传图片
			String avatar = "";
			UploadBody dataMap = new UploadBody();
			String originalFilename = file.getOriginalFilename();// 原文件名
			String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));   
			
			dataMap.setFile(IOUtils.toByteArray(file.getInputStream()));
			dataMap.setBucketName(OSSBucketConst.AVATAR_BUCKETNAME);
			dataMap.setReturnPathType(2);
			dataMap.setFileType(fileType);
			ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
			if(0 == d.getErrCode()){ 
				avatar = d.getData();
			}else{//上传失败直接提示
				return d;
			}
			
			List<String> list = getUserFields(request, "userId");
			Long userId = Long.valueOf(list.get(0));
			
			AppUser user = new AppUser();
			user.setUserId(userId);
			user.setAvatar(avatar);
			result = userService.updateUser(user);
			if (result.getErrCode() == 0) {
				//success
				ResponseResult<String> result2 = new ResponseResult<>();
				String fullUrl = uploadAliService.getAccessUrlPrefix() + avatar;
				result2.setData(fullUrl);
				return result2;
			}
		} catch (Exception e) {
			log.error("uploadAvatar上传文件失败：{}",e.getMessage());
        	return ResponseResult.build(2000, "附件上传失败", null);
		}
		
		return result;
	}
	
	/**
	 * 上传身份证
	 * @param cardType
	 * 			身份证类型 f-正面 b-反面
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadCard", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> uploadCard(
				@RequestParam("cardType") String cardType, 
				@RequestParam MultipartFile file, 
				HttpServletRequest request) {
		ResponseResult<?> result = null;
		try {
			//上传图片
			String idcardUrl = "";
			UploadBody dataMap = new UploadBody();
			String originalFilename = file.getOriginalFilename();// 原文件名
			String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));   
			
			dataMap.setFile(IOUtils.toByteArray(file.getInputStream()));
			dataMap.setBucketName(OSSBucketConst.IDCARD_BUCKETNAME);
			dataMap.setReturnPathType(2);
			dataMap.setFileType(fileType);
			ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
			if(0 == d.getErrCode()){
				idcardUrl = d.getData();
			}else{//上传失败直接提示
				return d;
			}
			
			List<String> list = getUserFields(request, "userId");
			Long userId = Long.valueOf(list.get(0));
			
			AppUser user = new AppUser();
			user.setUserId(userId);
			if (CommonConstant.IDCARD_FACE.equals(cardType)) {
				user.setCardFaceUrl(idcardUrl);
			} else if (CommonConstant.IDCARD_BACK.equals(cardType)) {
				user.setCardBackUrl(idcardUrl);
			}
			result =  userService.updateUser(user);
		} catch (Exception e) {
			log.error("uploadCard上传文件失败：{}",e.getMessage());
        	return ResponseResult.build(2000, "附件上传失败", null);
		}
		
		return result;
	}
	
	/**
	 * 保存用户通讯录
	 * @param body
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveContacts", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> saveContacts(@RequestBody JSONObject body, HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		body.put("userId", userId);
		ResponseResult<?> result =  userService.saveContacts(body);
		
		return result;
	}
	
	/**
	 * 保存用户通讯录
	 * @param body
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveOcrResult", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> saveOcrResult(@RequestBody JSONObject body, HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId", "phoneNum");
		body.put("userId", Long.valueOf(list.get(0)));
		body.put("mobile", list.get(1));
		
		ResponseResult<?> result =  userService.saveOcrResult(body);
		
		return result;
	}
	
	/**
	 * 保存用户通讯录
	 * @param body
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveH5FaceResult", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> saveH5FaceResult(@RequestBody JSONObject body, HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId", "phoneNum");
		body.put("userId", Long.valueOf(list.get(0)));
		body.put("mobile", list.get(1));
		
		ResponseResult<?> result =  userService.saveH5FaceResult(body);
		
		return result;
	}

	/**
	 * 根据手机号查询用户
	 * @param body
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryUserByMobile", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<AppUser> queryUserByMobile(@RequestBody JSONObject body) {
		
		String mobile = body.getString("phoneNum");
		
		ResponseResult<AppUser> result =  userService.queryUserByMobile(mobile);
		
		return result;
	}
}
