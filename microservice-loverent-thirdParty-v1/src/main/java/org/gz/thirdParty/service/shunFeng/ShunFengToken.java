package org.gz.thirdParty.service.shunFeng;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.gz.thirdParty.constant.SFConstant;

import com.sf.aries.core.common.util.DateUtils;
import com.sf.openapi.common.entity.AppInfo;
import com.sf.openapi.common.entity.HeadMessageReq;
import com.sf.openapi.common.entity.MessageReq;
import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.express.sample.security.dto.AccessTokenReqDto;
import com.sf.openapi.express.sample.security.dto.TokenReqDto;
import com.sf.openapi.express.sample.security.dto.TokenRespDto;
import com.sf.openapi.express.sample.security.tools.SecurityTools;

public class ShunFengToken {

	public static void main(String[] args) throws Exception {
		MessageResp<TokenRespDto> res = AccessTokenApply();
		res = AccessTokenQuery();
		AccessTokenRefresh(res.getBody().getAccessToken(),res.getBody().getRefreshToken());
	}

	/**
	 * 
	* @Description: 申请令牌
	* @return MessageResp<TokenRespDto>
	* @throws Exception
	 */
	public static MessageResp<TokenRespDto> AccessTokenApply() throws Exception {
		String url = SFConstant.URL_HEAD+"/public/v1.0/security/access_token/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);

		MessageReq<TokenReqDto> req = new MessageReq<TokenReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(301);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);

		System.out.println("传入参数" + ToStringBuilder.reflectionToString(req));
		MessageResp<TokenRespDto> response = SecurityTools.applyAccessToken(url, appInfo, req);
		System.out.println("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}

	/**
	 * 
	* @Description: 查询令牌
	* @return MessageResp<TokenRespDto>
	* @throws Exception
	 */
	public static MessageResp<TokenRespDto> AccessTokenQuery() throws Exception {
		String url = SFConstant.URL_HEAD+"/public/v1.0/security/access_token/query/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);

		MessageReq<AccessTokenReqDto> req = new MessageReq<AccessTokenReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(300);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);

		System.out.println("传入参数" + ToStringBuilder.reflectionToString(req));
		MessageResp<TokenRespDto> response = SecurityTools.queryAccessToken(url, appInfo, req);
		System.out.println("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}
	
	
	/**
	 * 
	* @Description: 刷新令牌
	* @return MessageResp<TokenRespDto>
	* @throws Exception
	 */
	public static MessageResp<TokenRespDto> AccessTokenRefresh(String accessToken,String refreshToken) throws Exception {
		String url = SFConstant.URL_HEAD+"/public/v1.0//security/refresh_token/access_token/{access_token}/refresh_token/{refresh_token}/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);
		appInfo.setAccessToken(accessToken);
		appInfo.setRefreshToken(refreshToken);

		MessageReq<AccessTokenReqDto> req = new MessageReq<AccessTokenReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(302);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);

		System.out.println("传入参数" + ToStringBuilder.reflectionToString(req));
		MessageResp<TokenRespDto> response = SecurityTools.queryAccessToken(url, appInfo, req);
		System.out.println("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}
	
	

}
