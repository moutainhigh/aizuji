package org.gz.thirdParty.service.shunFeng;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.gz.thirdParty.constant.SFConstant;

import com.sf.aries.core.common.util.DateUtils;
import com.sf.openapi.common.entity.AppInfo;
import com.sf.openapi.common.entity.HeadMessageReq;
import com.sf.openapi.common.entity.MessageReq;
import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.express.sample.waybill.dto.WaybillReqDto;
import com.sf.openapi.express.sample.waybill.dto.WaybillRespDto;
import com.sf.openapi.express.sample.waybill.tools.WaybillDownloadTools;

public class ShunFengWayBill {
	
	  public static MessageResp<WaybillRespDto> WaybillDownload(String accessToken,String orderId) throws Exception
			  {
			    String url = SFConstant.URL_HEAD+"/rest/v1.0/waybill/image/access_token/{access_token}/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
			    AppInfo appInfo = new AppInfo();
			    appInfo.setAppId(SFConstant.appId);
				appInfo.setAppKey(SFConstant.appKey);
				appInfo.setAccessToken(accessToken);
				
			    MessageReq<WaybillReqDto> req = new MessageReq<WaybillReqDto>();
			    HeadMessageReq head = new HeadMessageReq();
			    head.setTransType(205);
			    Random random = new Random();
				head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
			    req.setHead(head);
			    
			    WaybillReqDto reqBody = new WaybillReqDto();
			    
			    reqBody.setOrderId(orderId);
			    req.setBody(reqBody);
			    
			    System.out.println("传入参数:" + ToStringBuilder.reflectionToString(req));
			    MessageResp<WaybillRespDto> response = WaybillDownloadTools.waybillDownload(url, appInfo, req);
			    System.out.println(response.getHead().getTransType());
			    System.out.println("返回参数:" + ToStringBuilder.reflectionToString(response));
			    return response;
			  }
}
