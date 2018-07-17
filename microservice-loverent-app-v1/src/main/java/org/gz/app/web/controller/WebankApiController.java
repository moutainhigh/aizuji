package org.gz.app.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.gz.app.feign.UploadAliService;
import org.gz.common.constants.CommonConstant;
import org.gz.common.constants.OSSBucketConst;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.StringUtils;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.UserInfo;
import org.gz.user.service.UserInfoService;
import org.gz.user.service.UserService;
import org.gz.webank.service.WebankApiTicketService;
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
@RequestMapping("/webank")
@Slf4j
public class WebankApiController extends AppBaseController {

	@Autowired
	private UploadAliService uploadAliService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private WebankApiTicketService webankApiTicketService;
	
	private static final String OCR_API_URL = "https://ida.webank.com/api/paas/idcardocrapp";
	
	@RequestMapping(value="/getSignData", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<JSONObject> getSignData(HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		
		ResponseResult<JSONObject> result = webankApiTicketService.signData(userId);
		log.info("getSignData() --> sign success, signDate:{}", JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * api识别身份证 - h5
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadCard", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<AppUser> uploadCard(
			@RequestParam("cardType") String cardType, 
			@RequestParam("imageBase64") String imageBase64, 
			@RequestParam MultipartFile file,
			HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		
		int index = imageBase64.indexOf(",");
		imageBase64 = imageBase64.substring(index + 1);
		
		//上传图片
		uploadIdCardFile(imageBase64, userId, cardType);
		
		//调用ocr api失败身份证信息
		ResponseResult<JSONObject> signResult = webankApiTicketService.getOCRApiSign();
		if (signResult.getErrCode() != 0) {
			return ResponseResult.build(-1, "获取签名失败", null);
		}
		JSONObject signData = signResult.getData();
		signData.put("cardType", cardType);	//"0-正面,1-反面"
		
		//转换图片base64
		try {
			signData.put("idcardStr", imageBase64);
		} catch (Exception e) {
			log.error("convertFileToBase64Str failed, e: {}", e);
			return ResponseResult.build(-1, "获取图片base64失败", null);
		}
		
		//调用ocr api识别身份证
		return requestOcrApiAndProcessResult(signData, cardType, userId);
		
	}
	
	/**
	 * H5-上送身份信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getH5Faceid", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<JSONObject> getH5Faceid(@RequestBody JSONObject body, HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		
		body.put("userId", userId);
		ResponseResult<JSONObject> result = webankApiTicketService.getH5Faceid(body);
		log.info("---> getH5Faceid success, signDate:{}", JSONObject.toJSONString(result));
		if (result.getErrCode() == 0) {
			//success
			return result;
		}
		return ResponseResult.build(-1, "获取人脸识别登录签名失败", null); 
	}
	
	
	
	/**
	 * 请求OCR API失败身份证信息
	 * @param signData
	 * @param cardType 
	 * @param userId 
	 * @return
	 */
	private ResponseResult<AppUser> requestOcrApiAndProcessResult(JSONObject signData, 
			String cardType, Long userId) {
		try {
			String resp = HttpClientUtil.postParametersJson(
					OCR_API_URL,
					JSONObject.toJSONString(signData));
			log.info("---> requestOcrApiAndProcessResult, resp: {}", resp);
			if (!StringUtils.isEmpty(resp)) {
				JSONObject data = JSONObject.parseObject(resp);
				if ("0".equals(data.getString("code"))) {
					//request success
					JSONObject resultEle =data.getJSONObject("result");
					if (resultEle != null) {
						AppUser modifyUser = null;
						if ("0".equals(cardType)) {
							//正面
							String name = resultEle.getString("name");
							String sex = resultEle.getString("sex");		//男,女
							String birth = resultEle.getString("birth");	//19931119
							String address = resultEle.getString("address");	//住址
							String idcard = resultEle.getString("idcard");	//身份证号
							
							modifyUser = new AppUser();
							modifyUser.setUserId(userId);
							modifyUser.setRealName(name);
							modifyUser.setGender("男".equals(sex) ? 1 : 2);
							modifyUser.setResidenceAddress(address);
							modifyUser.setIdNo(idcard);
							userService.updateUser(modifyUser);
							
							if (!StringUtils.isEmpty(birth)) {
								UserInfo userInfo = new UserInfo();
								userInfo.setUserId(userId);
								userInfo.setBirthday(DateUtils.getDate(birth, new SimpleDateFormat("yyyyMMdd")));
								userInfoService.updateUserInfo(userInfo);
							}
						} else if ("1".equals(cardType)) {
							//反面
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							String validDate = resultEle.getString("validDate");	//"20170703-20270703"
							String authority = resultEle.getString("authority");	//xx公安局
							
							modifyUser = new AppUser();
							modifyUser.setUserId(userId);
							if (!StringUtils.isEmpty(validDate)) {
								if (validDate.indexOf("-") != -1) {
									String[] arr = validDate.split("-");
									String effectiveStartDate = arr[0];
									String effectiveEndDate = arr[1];
									if (!StringUtils.isEmpty(effectiveStartDate)) {
										modifyUser.setEffectiveStartDate(DateUtils.getDate(effectiveStartDate, format));
									}
									if (!StringUtils.isEmpty(effectiveEndDate)) {
										Date endDate = DateUtils.getDate(effectiveEndDate, format);
										if (endDate.getTime() <= System.currentTimeMillis()) {
											return ResponseResult.build(-1, "身份证已过期", null);
										}
										modifyUser.setEffectiveEndDate(endDate);
									}
								}
							}
							if (!StringUtils.isEmpty(authority)) {
								modifyUser.setIssuingAuthority(authority);
							}
							userService.updateUser(modifyUser);
						}
						return ResponseResult.build(0, "识别成功", modifyUser);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("---> WebankApiController.uploadCard, request ocr api failed, e:{}", e);
			return ResponseResult.build(-1, "获取图片base64失败", null);
		}
		return ResponseResult.build(-1, "身份证识别失败", null);
	}

	/**
	 * 上传身份证图片
	 * @param file
	 * @param userId
	 * @param cardType
	 * @return
	 */
	private ResponseResult<String> uploadIdCardFile(String imageBase64, Long userId, String cardType) {
		try {
			String idcardUrl = "";
			UploadBody dataMap = new UploadBody();

			dataMap.setFile(Base64.decodeBase64(imageBase64));
			dataMap.setBucketName(OSSBucketConst.IDCARD_BUCKETNAME);
			dataMap.setReturnPathType(2);
			dataMap.setFileType(".jpg");
			ResponseResult<String> uploadResult = uploadAliService.uploadToOSSFileInputStrem(dataMap);
			log.info("---> uploadIdCardFile, upload result:{}", JSONObject.toJSONString(uploadResult));
			if(0 == uploadResult.getErrCode()){
				idcardUrl = uploadResult.getData();
			}else{
				return ResponseResult.build(-1, "身份证上传失败", null);
			}
			
			AppUser user = new AppUser();
			user.setUserId(userId);
			if (CommonConstant.H5_IDCARD_FACE.equals(cardType)) {
				user.setCardFaceUrl(idcardUrl);
			} else if (CommonConstant.H5_IDCARD_BACK.equals(cardType)) {
				user.setCardBackUrl(idcardUrl);
			}
			ResponseResult<?> userResult =  userService.updateUser(user);
			if (userResult.getErrCode() != 0) {
				return ResponseResult.build(-1, "更新失败,用户服务不可用", null);
			}
			return ResponseResult.build(0, "上传成功", null);
		} catch (Exception e) {
			log.error("---> uploadIdCardFile failed, 上传文件失败：{}",e.getMessage());
        	return ResponseResult.build(-1, "附件上传失败", null);
		}
	}
	
	/**
	 * 将MultipartFile file转换为base64字符串
	 * @param in
	 * @return
	 */
	private String convertFileToBase64Str(InputStream in) {
		//根据原文件路径获取输入流
		byte[] data = convertInputStreamToByte(in);
		if (data == null) {
			log.error("convertInputStreamToByte failed, data is null.");
			throw new RuntimeException("文件转换失败");
		}
		//对字节数组Base64编码  
		String result = Base64.encodeBase64String(data);
		return result;
	}
	
	private byte[] convertInputStreamToByte(InputStream in) {
		if (in == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = null;
		// 读取图片字节数组  
		try {
			data = new byte[in.available()];
			int len = 0;
			while( (len = in.read(data)) != -1 ){ 
				out.write(data, 0,len);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}
	
}
