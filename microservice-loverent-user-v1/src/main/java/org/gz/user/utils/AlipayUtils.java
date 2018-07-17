package org.gz.user.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.user.commons.AlipayConfig;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

@Slf4j
public class AlipayUtils {
	
	private static AlipayClient alipayClient = null;
	
	static {
		alipayClient = new DefaultAlipayClient(
				AlipayConfig.GATEWAY_URL, 
				AlipayConfig.APP_ID, 
				AlipayConfig.APP_PRIVATE_KEY, 
				"json", 
				AlipayConfig.CHARSET, 
				AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA2");
	}

	/**
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken(String authCode) {
		String accessToken = null;
		try {
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setGrantType("authorization_code");
			request.setCode(authCode);
			AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
			log.info("AlipayUtils.getAccessToken() -- > response: {}", response.getBody());
			return response.getAccessToken();
		} catch (Exception e) {
			log.error("get alipay access_token failed, e: {}", e);
		}
	
		return accessToken;
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public static Map<String, String> getUserShareInfo(String accessToken) {
		Map<String, String> shareInfo = null;
		try {
			AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
			AlipayUserInfoShareResponse response = alipayClient.execute(request,accessToken);
			if(response.isSuccess()){
				shareInfo = new HashMap<>();
				/*out.write("<br/>UserId:" + userinfoShareResponse.getUserId() + "\n");//用户支付宝ID
		        out.write("UserType:" + userinfoShareResponse.getUserType() + "\n");//用户类型
		        out.write("UserStatus:" + userinfoShareResponse.getUserStatus() + "\n");//用户账户动态
		        out.write("Email:" + userinfoShareResponse.getEmail() + "\n");//用户Email地址
		        out.write("IsCertified:" + userinfoShareResponse.getIsCertified() + "\n");//用户是否进行身份认证
		        out.write("IsStudentCertified:" + userinfoShareResponse.getIsStudentCertified() + "\n");//用户是否进行学生认证*/
				log.info("get userShareInfo success, response: {}", response.getBody());
				shareInfo.put("userId", response.getUserId());
				shareInfo.put("avatar", response.getAvatar());
				shareInfo.put("nickName", response.getNickName());
			}
			else {
				log.error("get getUserShareInfo failed!, code + msg: ", response.getCode() + ", "+response.getMsg());
			}
		} catch (Exception e) {
			log.error("get alipay userShareInfo failed, e: {}", e);
		}
		return shareInfo;
	}
	
	
	
}
