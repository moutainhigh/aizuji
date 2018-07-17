package org.gz.thirdParty.service.shunFeng;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.thirdParty.bean.ApplyOrderRequest;
import org.gz.thirdParty.bean.shunFeng.OrderDownload;
import org.gz.thirdParty.constant.SFConstant;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sf.aries.core.common.util.DateUtils;
import com.sf.openapi.common.entity.AppInfo;
import com.sf.openapi.common.entity.HeadMessageReq;
import com.sf.openapi.common.entity.MessageReq;
import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.common.utils.SFOpenClient;
import com.sf.openapi.express.sample.order.dto.CargoInfoDto;
import com.sf.openapi.express.sample.order.dto.DeliverConsigneeInfoDto;
import com.sf.openapi.express.sample.order.dto.OrderQueryReqDto;
import com.sf.openapi.express.sample.order.dto.OrderQueryRespDto;
import com.sf.openapi.express.sample.order.dto.OrderReqDto;
import com.sf.openapi.express.sample.order.dto.OrderRespDto;
import com.sf.openapi.express.sample.order.tools.OrderTools;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ShunFengOrder {

	public static void main(String[] args) throws Exception {
		String url = "https://open-sbox.sf-express.com/rest/v1.0/waybill/image/access_token/295DCA6F3DA271C68BE5355359D24035/sf_appid/00039681/sf_appkey/24A4E4FA525BCE4A19C8AE6577D76116";
		MessageReq<OrderQueryReqDto> req = new MessageReq<OrderQueryReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(205);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);
		
		OrderQueryReqDto orderReqDto = new OrderQueryReqDto();
		orderReqDto.setOrderId("1111234549906");
		req.setBody(orderReqDto);
		String param = JsonUtils.toJsonString(req);
		log.info(param);
		String resp = HttpClientUtil.postParametersJson(url,param);
		log.info(resp);
		OrderDownload download = JSON.parseObject(resp,OrderDownload.class);
		log.info(download.getHead().getCode());
		log.info(download.getBody().getImages().size()+"");
		log.info(download.getBody().getImages().get(0));
		
	}

	public static MessageResp<OrderRespDto> order(String accessToken,ApplyOrderRequest applyOrderRequest) throws Exception {
		String url = SFConstant.URL_HEAD+"/rest/v1.0/order/access_token/{access_token}"
				+ "/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);
		appInfo.setAccessToken(accessToken);

		MessageReq<OrderReqDto> req = new MessageReq<OrderReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(200);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);

		OrderReqDto orderReqDto = new OrderReqDto();
		orderReqDto.setOrderId(applyOrderRequest.getOrderId());
		orderReqDto.setExpressType(applyOrderRequest.getExpressType());
		orderReqDto.setPayMethod(applyOrderRequest.getPayMethod());
		orderReqDto.setNeedReturnTrackingNo(applyOrderRequest.getNeedReturnTrackingNo());
		orderReqDto.setIsDoCall(applyOrderRequest.getIsDoCall());
		orderReqDto.setIsGenBillNo(applyOrderRequest.getIsGenBillNo());
		orderReqDto.setCustId(applyOrderRequest.getCustId());
		orderReqDto.setPayArea(applyOrderRequest.getPayArea());
		orderReqDto.setSendStartTime(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS));
		orderReqDto.setRemark(applyOrderRequest.getRemark());

//		DeliverConsigneeInfoDto deliverInfoDto = new DeliverConsigneeInfoDto();
		//寄方信息，不填则取默认
//		deliverInfoDto.setAddress("上地");
//		deliverInfoDto.setCity("朝阳");
//		deliverInfoDto.setCompany("京东");
//		deliverInfoDto.setContact("李四");
//		deliverInfoDto.setCountry("中国");
//		deliverInfoDto.setProvince("北京");
//		deliverInfoDto.setShipperCode("787564");
//		deliverInfoDto.setTel("010-95123669");
//		deliverInfoDto.setMobile("13612822894");

		DeliverConsigneeInfoDto consigneeInfoDto = new DeliverConsigneeInfoDto();
		//收方信息 必填
		consigneeInfoDto.setAddress(applyOrderRequest.getAddress());
		consigneeInfoDto.setCity(applyOrderRequest.getCity());
		consigneeInfoDto.setCompany(applyOrderRequest.getCompany());
		consigneeInfoDto.setContact(applyOrderRequest.getContact());
		consigneeInfoDto.setCountry(applyOrderRequest.getCountry());
		consigneeInfoDto.setProvince(applyOrderRequest.getProvince());
		consigneeInfoDto.setShipperCode(applyOrderRequest.getShipperCode());
		consigneeInfoDto.setTel(applyOrderRequest.getTel());
		consigneeInfoDto.setMobile(applyOrderRequest.getMobile());

		CargoInfoDto cargoInfoDto = new CargoInfoDto();
		cargoInfoDto.setCargo(applyOrderRequest.getCargo());// 货物名称

//		List<AddedServiceDto> addedServiceDtos = new ArrayList<AddedServiceDto>();
//		AddedServiceDto addedServiceDto = new AddedServiceDto();
//		addedServiceDto.setName("");
//		addedServiceDto.setValue("");// 代收货款值上限为20000
//		addedServiceDtos.add(addedServiceDto);

//		orderReqDto.setDeliverInfo(deliverInfoDto);
		orderReqDto.setConsigneeInfo(consigneeInfoDto);
		orderReqDto.setCargoInfo(cargoInfoDto);
//		orderReqDto.setAddedServices(addedServiceDtos);

		req.setBody(orderReqDto);
		log.info("传入参数" + ToStringBuilder.reflectionToString(req));
		MessageResp<OrderRespDto> response = OrderTools.order(url, appInfo, req);
		log.info("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}
	
	
	
	public static MessageResp<OrderRespDto> orderDownLoad(String accessToken,String orderId) throws Exception{
		String url = SFConstant.URL_HEAD+"/rest/v1.0/waybill/image/access_token/{access_token}/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);
		appInfo.setAccessToken(accessToken);
		
		MessageReq<OrderQueryReqDto> req = new MessageReq<OrderQueryReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(205);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);
		
		OrderQueryReqDto orderReqDto = new OrderQueryReqDto();
		orderReqDto.setOrderId(orderId);
		req.setBody(orderReqDto);
		
		log.info("传入参数" + ToStringBuilder.reflectionToString(req));
		Map<String, String> paramMap = new HashMap();
	    paramMap.put("sf_appid", appInfo.getAppId());
	    paramMap.put("sf_appkey", appInfo.getAppKey());
	    paramMap.put("access_token", appInfo.getAccessToken());
	    SFOpenClient client = SFOpenClient.getInstance();
	    MessageResp response = client.doPost(url, req, new TypeReference() {}, paramMap);
		log.info("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}
	
	
	public static MessageResp<OrderRespDto> orderBack(String accessToken,ApplyOrderRequest applyOrderRequest) throws Exception {
		String url = SFConstant.URL_HEAD+"/rest/v1.0/order/access_token/{access_token}"
				+ "/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);
		appInfo.setAccessToken(accessToken);
		
		MessageReq<OrderReqDto> req = new MessageReq<OrderReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(200);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);
		
		OrderReqDto orderReqDto = new OrderReqDto();
		orderReqDto.setOrderId(applyOrderRequest.getOrderId());
		orderReqDto.setExpressType(applyOrderRequest.getExpressType());
		orderReqDto.setPayMethod(applyOrderRequest.getPayMethod());
		orderReqDto.setNeedReturnTrackingNo(applyOrderRequest.getNeedReturnTrackingNo());
		orderReqDto.setIsDoCall(applyOrderRequest.getIsDoCall());
		orderReqDto.setIsGenBillNo(applyOrderRequest.getIsGenBillNo());
		orderReqDto.setCustId(SFConstant.account);
		orderReqDto.setPayArea(applyOrderRequest.getPayArea());
		if(StringUtils.isBlank(applyOrderRequest.getSendStartTime())){
			applyOrderRequest.setSendStartTime(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS));
		}
		orderReqDto.setSendStartTime(applyOrderRequest.getSendStartTime());
		orderReqDto.setRemark(applyOrderRequest.getRemark());
		
		DeliverConsigneeInfoDto deliverInfoDto = new DeliverConsigneeInfoDto();
		//寄方信息，不填则取默认
		deliverInfoDto.setAddress(applyOrderRequest.getAddress());
		deliverInfoDto.setCity(applyOrderRequest.getCity());
		deliverInfoDto.setCompany(applyOrderRequest.getCompany());
		deliverInfoDto.setContact(applyOrderRequest.getContact());
		deliverInfoDto.setCountry(applyOrderRequest.getCountry());
		deliverInfoDto.setProvince(applyOrderRequest.getProvince());
		deliverInfoDto.setShipperCode(applyOrderRequest.getShipperCode());
		deliverInfoDto.setTel(applyOrderRequest.getTel());
		deliverInfoDto.setMobile(applyOrderRequest.getMobile());
		
		DeliverConsigneeInfoDto consigneeInfoDto = new DeliverConsigneeInfoDto();
		//收方信息 必填
		consigneeInfoDto.setCountry("中国");
		consigneeInfoDto.setProvince("广东省");
		consigneeInfoDto.setCity("深圳市");
		consigneeInfoDto.setAddress("南山区高新科技园南区高新南一道中科大厦18B");
		consigneeInfoDto.setCompany("国智互联网科技有限公司");
		consigneeInfoDto.setContact("阳红林");//联系人
		consigneeInfoDto.setShipperCode("518000");
		consigneeInfoDto.setTel("075586708466");
		consigneeInfoDto.setMobile("");
		
		CargoInfoDto cargoInfoDto = new CargoInfoDto();
		cargoInfoDto.setCargo(applyOrderRequest.getCargo());// 货物名称
		
//		List<AddedServiceDto> addedServiceDtos = new ArrayList<AddedServiceDto>();
//		AddedServiceDto addedServiceDto = new AddedServiceDto();
//		addedServiceDto.setName("");
//		addedServiceDto.setValue("");// 代收货款值上限为20000
//		addedServiceDtos.add(addedServiceDto);
		
		orderReqDto.setDeliverInfo(deliverInfoDto);
		orderReqDto.setConsigneeInfo(consigneeInfoDto);
		orderReqDto.setCargoInfo(cargoInfoDto);
//		orderReqDto.setAddedServices(addedServiceDtos);
		
		req.setBody(orderReqDto);
		log.info("传入参数" + ToStringBuilder.reflectionToString(req));
		MessageResp<OrderRespDto> response = OrderTools.order(url, appInfo, req);
		log.info("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}

	public static MessageResp<OrderQueryRespDto> orderQuery(String accessToken,String orderId) throws Exception {
		String url = SFConstant.URL_HEAD
				+ "/rest/v1.0/order/query/access_token/{access_token}/sf_appid/{sf_appid}/sf_appkey/{sf_appkey}";
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(SFConstant.appId);
		appInfo.setAppKey(SFConstant.appKey);
		appInfo.setAccessToken(accessToken);

		MessageReq<OrderQueryReqDto> req = new MessageReq<OrderQueryReqDto>();
		HeadMessageReq head = new HeadMessageReq();
		head.setTransType(203);
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		req.setHead(head);

		OrderQueryReqDto rrderQueryReqDto = new OrderQueryReqDto();
		rrderQueryReqDto.setOrderId(orderId);

		req.setBody(rrderQueryReqDto);

		log.info("传入参数" + ToStringBuilder.reflectionToString(req));
		MessageResp<OrderQueryRespDto> response = OrderTools.orderQuery(url, appInfo, req);
		log.info("返回参数" + ToStringBuilder.reflectionToString(response));
		return response;
	}
}
