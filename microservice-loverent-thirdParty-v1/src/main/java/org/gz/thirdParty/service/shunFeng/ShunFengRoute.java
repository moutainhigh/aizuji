package org.gz.thirdParty.service.shunFeng;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.gz.thirdParty.constant.SFConstant;

import com.sf.aries.core.common.util.DateUtils;
import com.sf.openapi.common.entity.AppInfo;
import com.sf.openapi.common.entity.HeadMessageReq;
import com.sf.openapi.common.entity.MessageReq;
import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.express.sample.route.dto.RouteReqDto;
import com.sf.openapi.express.sample.route.dto.RouteRespDto;
import com.sf.openapi.express.sample.route.tools.RouteTools;

public class ShunFengRoute {

	/**
	 * 路由查询接口
	 * @param accessToken 令牌
	 * @param orderId 物流单号
	 * @param type 1:运单号 2:订单号
	 * @return
	 * @throws Exception
	 */
	public static MessageResp<List<RouteRespDto>> RouteQuery(String accessToken,String orderId,Integer type) throws Exception {
		String url = SFConstant.URL_HEAD+"/rest/v1.0/route/query/access_token/{access_token}/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);
		appInfo.setAccessToken(accessToken);

		MessageReq<RouteReqDto> req = new MessageReq<RouteReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(501);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);

		RouteReqDto routeReqDto = new RouteReqDto();
		routeReqDto.setMethodType(Integer.valueOf(1));// 标准查询
		routeReqDto.setTrackingType(type);// 根据运单号查询1:运单号 2:订单号
		routeReqDto.setTrackingNumber(orderId);//查询号（订单号/运单号）

		req.setBody(routeReqDto);
		MessageResp<List<RouteRespDto>> response = RouteTools.routeQuery(url, appInfo, req);
		System.out.println("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}
}
