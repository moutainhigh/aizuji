package org.gz.thirdParty.web.shunFeng;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.thirdParty.bean.ApplyOrderRequest;
import org.gz.thirdParty.bean.ResultCode;
import org.gz.thirdParty.bean.shunFeng.RoutePushBodyReq;
import org.gz.thirdParty.bean.shunFeng.RoutePushReq;
import org.gz.thirdParty.bean.shunFeng.RoutePushResp;
import org.gz.thirdParty.service.shunFeng.ShunFengOrder;
import org.gz.thirdParty.service.shunFeng.ShunFengRoute;
import org.gz.thirdParty.service.shunFeng.ShunFengWayBill;
import org.gz.thirdParty.utils.Base64Trans;
import org.gz.thirdParty.utils.SfTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sf.aries.core.common.util.DateUtils;
import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.express.sample.order.dto.OrderQueryRespDto;
import com.sf.openapi.express.sample.order.dto.OrderRespDto;
import com.sf.openapi.express.sample.route.dto.RouteRespDto;
import com.sf.openapi.express.sample.waybill.dto.WaybillRespDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShunFengController {

	@Autowired
	private SfTokenUtils tokenUtils;

	@PostMapping(value = "/routePush")
	public RoutePushResp routePush(@RequestBody RoutePushReq req) {
		System.out.println("routePush is coming." + JSON.toJSONString(req));
		List<RoutePushBodyReq> list = req.getBody();
		String successString = null;
		String failedString = null;
		// 业务处理
		for (RoutePushBodyReq body : list) {
			// 业务处理成功 ID加入处理成功订单
			if (successString == null) {
				successString = body.getId() + "";
			} else {
				successString = successString + "," + body.getId();
			}
			// 业务处理失败 ID加入处理失败订单
			if (failedString == null) {
				failedString = body.getId() + "";
			} else {
				failedString = failedString + "," + body.getId();
			}
		}

		RoutePushResp resp = new RoutePushResp();
		RoutePushResp.headMessageResp head = new RoutePushResp.headMessageResp();
		head.setCode("EX_CODE_OPENAPI_0200");
		head.setMessage("路由推送成功");
		Random random = new Random();
		head.setTransMessageId(DateUtils.format(new Date(), DateUtils.YYYYMMDD) + random.nextInt(999999999));
		head.setTransType(4500);
		resp.setHead(head);
		RoutePushResp.bodyMessageResp body = new RoutePushResp.bodyMessageResp();
		body.setId(successString);
		body.setIdError(failedString);
		resp.setBody(body);
		System.out.println("routePush is end." + JSON.toJSONString(req));
		return resp;
	}

	/**
	 * 快速下单接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultCode order(@RequestBody ApplyOrderRequest req) throws Exception {
		if (StringUtils.isBlank(req.getOrderId())) {
			return new ResultCode(9998, "订单号不能为空");
		}
		if (StringUtils.isBlank(req.getCustId())) {
			return new ResultCode(9998, "custId不能为空");
		}
		if (StringUtils.isBlank(req.getPayArea())) {
			return new ResultCode(9998, "payArea不能为空");
		}
		if (StringUtils.isBlank(req.getCountry())) {
			return new ResultCode(9998, "country不能为空");
		}
		if (StringUtils.isBlank(req.getProvince())) {
			return new ResultCode(9998, "province不能为空");
		}
		if (StringUtils.isBlank(req.getCity())) {
			return new ResultCode(9998, "city不能为空");
		}
		if (StringUtils.isBlank(req.getCounty())) {
			return new ResultCode(9998, "county不能为空");
		}
		if (StringUtils.isBlank(req.getAddress())) {
			return new ResultCode(9998, "address不能为空");
		}
		if (StringUtils.isBlank(req.getCompany())) {
			return new ResultCode(9998, "company不能为空");
		}
		if (StringUtils.isBlank(req.getContact())) {
			return new ResultCode(9998, "contact不能为空");
		}
		if (StringUtils.isBlank(req.getCargo())) {
			return new ResultCode(9998, "cargo不能为空");
		}
		if (StringUtils.isBlank(req.getTel()) && StringUtils.isBlank(req.getMobile())) {
			return new ResultCode(9998, "tel或者mobile不能两者为空");
		}
		req.setPayMethod(new Short("3"));// 第三方月结卡号支付
		req.setIsGenBillNo(new Short("1"));// 是否申请运单号 1生成
		MessageResp<OrderRespDto> resp = ShunFengOrder.order(this.tokenUtils.getAccessToken(), req);
		return new ResultCode(resp);
	}

	/**
	 * 快速下单接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/order2", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultCode order2(@RequestBody ApplyOrderRequest req) throws Exception {
		if (StringUtils.isBlank(req.getOrderId())) {
			return new ResultCode(9998, "订单号不能为空");
		}
		if (StringUtils.isBlank(req.getCustId())) {
			return new ResultCode(9998, "custId不能为空");
		}
		if (StringUtils.isBlank(req.getPayArea())) {
			return new ResultCode(9998, "payArea不能为空");
		}
		if (StringUtils.isBlank(req.getCountry())) {
			return new ResultCode(9998, "country不能为空");
		}
		if (StringUtils.isBlank(req.getProvince())) {
			return new ResultCode(9998, "province不能为空");
		}
		if (StringUtils.isBlank(req.getCity())) {
			return new ResultCode(9998, "city不能为空");
		}
		if (StringUtils.isBlank(req.getCounty())) {
			return new ResultCode(9998, "county不能为空");
		}
		if (StringUtils.isBlank(req.getAddress())) {
			return new ResultCode(9998, "address不能为空");
		}
		if (StringUtils.isBlank(req.getCompany())) {
			return new ResultCode(9998, "company不能为空");
		}
		if (StringUtils.isBlank(req.getContact())) {
			return new ResultCode(9998, "contact不能为空");
		}
		if (StringUtils.isBlank(req.getCargo())) {
			return new ResultCode(9998, "cargo不能为空");
		}
		if (StringUtils.isBlank(req.getTel()) && StringUtils.isBlank(req.getMobile())) {
			return new ResultCode(9998, "tel或者mobile不能两者为空");
		}
		req.setPayMethod(new Short("3"));// 第三方月结卡号支付
		req.setIsGenBillNo(new Short("1"));// 是否申请运单号 1生成
		try {
			log.info("调用顺丰下单参数：{}",JsonUtils.toJsonString(req));
			String accessToken = this.tokenUtils.getAccessToken();
			//1.先根据订单号查询是否已下单
			MessageResp<OrderQueryRespDto> queryResp = ShunFengOrder.orderQuery(accessToken, req.getOrderId());
			log.info("调用顺丰查询是否已下单：{}",JsonUtils.toJsonString(queryResp));
			if(queryResp.getHead().getCode().equals("EX_CODE_OPENAPI_0200")) {//若已下单则直接返回，防止重复下单
				List<String> data = new ArrayList<String>();
				data.add(queryResp.getBody().getMailNo());//运单号
				data.add(queryResp.getBody().getReturnTrackingNo());//返单号
				log.info("调用顺丰查单返回结果：{}",JsonUtils.toJsonString(data));
				return new ResultCode(data);
			}else {//若未下单，则走下单流程
				MessageResp<OrderRespDto> resp = ShunFengOrder.order(accessToken, req);
				log.info("调用顺丰下单响应结果：{}",JsonUtils.toJsonString(resp));
				if(resp.getHead().getCode().equals("EX_CODE_OPENAPI_0200")) {
					Thread.sleep(1000);//延迟一定时间，否则有可能查询不出数据
					queryResp = ShunFengOrder.orderQuery(accessToken, req.getOrderId());
					log.info("调用顺丰查单响应结果：{}",JsonUtils.toJsonString(queryResp));
					if(queryResp.getHead().getCode().equals("EX_CODE_OPENAPI_0200")) {
						List<String> data = new ArrayList<String>();
						data.add(queryResp.getBody().getMailNo());//运单号
						data.add(queryResp.getBody().getReturnTrackingNo());//返单号
						log.info("调用顺丰查单返回结果：{}",JsonUtils.toJsonString(data));
						return new ResultCode(data);
					}else {
						return new ResultCode(9998, "查询下单失败:"+queryResp.getHead().getMessage());
					}
				}else {
					return new ResultCode(9998, "顺丰下单失败:"+resp.getHead().getMessage());
				}
			}
		} catch (Exception e) {
			log.error("快速下单失败：{}", e.getLocalizedMessage());
			return ResultCode.SERVER_ERROR;
		}

	}

	/**
	 * 订单图片下载
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderDownload")
	public ResultCode orderDownload(String orderId) throws Exception {
		if (StringUtils.isBlank(orderId)) {
			return new ResultCode(9998, "订单号不能为空");
		}
		return new ResultCode(ShunFengWayBill.WaybillDownload(this.tokenUtils.getAccessToken(), orderId));
	}

	@GetMapping("/orderDownload2/{orderId}")
	public ResultCode orderDownload2(@PathVariable("orderId")String orderId) throws Exception {
		if (StringUtils.isBlank(orderId)) {
			return new ResultCode(9998, "订单号不能为空");
		}
		ResultCode resultCode = null;
		MessageResp<WaybillRespDto> resp = ShunFengWayBill.WaybillDownload(this.tokenUtils.getAccessToken(), orderId);
		if (resp.getHead().getCode().equals("EX_CODE_OPENAPI_0200")) {
			String[] imgDatas = resp.getBody().getImages();
			if (imgDatas != null && imgDatas.length > 0) {
				List<String> decodeDataList = new ArrayList<String>();
				for (String imgData : imgDatas) {
					decodeDataList.add(imgData);
				}
				resultCode=new ResultCode(decodeDataList);
			}else {
				resultCode = new ResultCode(9998,"无相关的最子运单图片信息");
			}
		}else {
			resultCode = new ResultCode(9998,resp.getHead().getMessage());
		}
		return resultCode;
	}
	
	/**
	 * 订单图片下载
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadOrderImg")
	public void downloadOrderImg(String orderId, HttpServletResponse response) throws Exception {
		boolean downloadResult = false;
		String errorMsg = "";
		if (StringUtils.isBlank(orderId)) {
			errorMsg = JsonUtils.toJsonString(new ResultCode(9998, "订单号不能为空"));
		} else {
			MessageResp<WaybillRespDto> resp = ShunFengWayBill.WaybillDownload(this.tokenUtils.getAccessToken(),orderId);
			if (resp.getHead().getCode().equals("EX_CODE_OPENAPI_0200")) {
				String[] imgDatas = resp.getBody().getImages();
				if (imgDatas != null && imgDatas.length > 0) {
					response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
					response.setHeader("content-disposition", "attachment;filename=" + orderId + ".zip");
					ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
					BufferedOutputStream bos = new BufferedOutputStream(zos);
					int index = 1;
					try {
						for (String imgData : imgDatas) {
							byte[] decodeData = Base64Trans.GenerateImageByte(imgData);
							BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(decodeData));
							zos.putNextEntry(new ZipEntry(orderId + "_" + (index++) + ".jpg"));
							int len = 0;
							byte[] buf = new byte[10 * 1024];
							while ((len = bis.read(buf, 0, buf.length)) != -1) {
								bos.write(buf, 0, len);
							}
							bis.close();
							bos.flush();
						}
						bos.close();
						downloadResult = true;
					} catch (Exception e) {
						log.error("生成压缩包失败：{}", e.getLocalizedMessage());
						errorMsg = JsonUtils.toJsonString(new ResultCode(9998, "请稍候下载！"));
					}
				}else {
					errorMsg = JsonUtils.toJsonString(new ResultCode(9998, "该订单未生成电子运单！"));
				}
			} else {
				errorMsg = JsonUtils.toJsonString(new ResultCode(9998, resp.getHead().getMessage()));
			}
		}
		if (downloadResult == false) {
			response.reset();
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write(errorMsg);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 寄回快速下单接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/orderBack", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultCode orderBack(@RequestBody ApplyOrderRequest req) throws Exception {
		if (StringUtils.isBlank(req.getOrderId())) {
			return new ResultCode(9998, "订单号不能为空");
		}
		if (StringUtils.isBlank(req.getCustId())) {
			return new ResultCode(9998, "custId不能为空");
		}
		if (StringUtils.isBlank(req.getPayArea())) {
			return new ResultCode(9998, "payArea不能为空");
		}
		if (StringUtils.isBlank(req.getCountry())) {
			return new ResultCode(9998, "country不能为空");
		}
		if (StringUtils.isBlank(req.getProvince())) {
			return new ResultCode(9998, "province不能为空");
		}
		if (StringUtils.isBlank(req.getCity())) {
			return new ResultCode(9998, "city不能为空");
		}
		if (StringUtils.isBlank(req.getCounty())) {
			return new ResultCode(9998, "county不能为空");
		}
		if (StringUtils.isBlank(req.getAddress())) {
			return new ResultCode(9998, "address不能为空");
		}
		if (StringUtils.isBlank(req.getCompany())) {
			return new ResultCode(9998, "company不能为空");
		}
		if (StringUtils.isBlank(req.getContact())) {
			return new ResultCode(9998, "contact不能为空");
		}
		if (StringUtils.isBlank(req.getCargo())) {
			return new ResultCode(9998, "cargo不能为空");
		}
		if (StringUtils.isBlank(req.getTel()) && StringUtils.isBlank(req.getMobile())) {
			return new ResultCode(9998, "tel或者mobile不能两者为空");
		}
		req.setPayMethod(new Short("3"));// 第三方月结卡号支付
		req.setIsGenBillNo(new Short("1"));// 是否申请运单号 1生成
		return new ResultCode(ShunFengOrder.orderBack(this.tokenUtils.getAccessToken(), req));
	}

	/**
	 * 订单结果查询接口
	 * 
	 * @param orderId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderQuery")
	public ResultCode orderQuery(String orderId) throws Exception {
		return new ResultCode(ShunFengOrder.orderQuery(this.tokenUtils.getAccessToken(), orderId));
	}

	/**
	 * 查询物流信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/routeQuery")
	public ResultCode routeQuery(String orderId, Integer type) {
		log.info("testRouteQuery is begin,orderId:" + orderId);
		if (StringUtils.isBlank(orderId)) {
			return ResultCode.ORDER_NUMBER_IS_NULL;
		}
		if (type == null) {// 如果没有值则默认查订单号
			type = 2;
		}
		if (type < 1 || type > 2) {
			return ResultCode.ORDER_TYPE_ERROR;
		}
		String code = null;
		MessageResp<List<RouteRespDto>> response = null;
		try {
			// 调用路由查询接口
			response = ShunFengRoute.RouteQuery(this.tokenUtils.getAccessToken(), orderId, type);
			code = response.getHead().getCode();
		} catch (Exception e) {
			return ResultCode.SERVER_ERROR;
		}
		// 判断查询结果
		if (code.equals("EX_CODE_OPENAPI_0200")) {
			log.info("routequery seccuess orderId:" + orderId);
			List<RouteRespDto> list = response.getBody();
			list.forEach(l -> System.out.println("orderId:" + l.getOrderId() + " mailNo:" + l.getMailNo()
					+ " acceptTime:" + l.getAcceptTime() + " acceptAddress" + l.getAcceptAddress() + " opCode:"
					+ l.getOpcode() + " remark:" + l.getRemark()));
			// 反向排序
			Collections.reverse(list);
			return new ResultCode(list);
		} else {
			log.info("ShunFengRoute.RouteQuery is error,code:" + code + " message:" + response.getHead().getMessage());
			return new ResultCode(9999, response.getHead().getMessage());
		}
	}
}
