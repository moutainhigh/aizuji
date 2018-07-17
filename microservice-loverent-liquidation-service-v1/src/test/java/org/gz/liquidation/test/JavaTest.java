package org.gz.liquidation.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.UUIDUtils;
import org.gz.common.utils.WeiXinSignUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaTest {
	
	@Test
	public void testDivide(){
		BigDecimal amount = new BigDecimal(9125);
		
		BigDecimal divisor = new BigDecimal(12);
		BigDecimal val = amount.divide(divisor,2, BigDecimal.ROUND_HALF_UP);
		BigDecimal remain = amount.subtract(amount.divide(divisor,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(11)));
		System.out.println("前11期还款:"+val);
		System.out.println("最后一期还款:"+remain);
		
		System.out.println("验证计算结果:"+(val.multiply(new BigDecimal(11)).add(remain)));
		
		System.out.println("ZJ2017122309264957100000".length());
	}
	@Test
	public void testDate(){
		Date date = DateUtils.getDay("2017-1-30");
		DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//字符串转换成LocalDate类型
		LocalDate localDate = LocalDate.parse("2017-01-30", ymd);
		System.out.println(localDate.plusMonths(1));
		
		
		Date yestoday = DateUtils.getDateWithDifferDay(-1);
		System.out.println("yestoday:"+yestoday);
		System.out.println("yestoday start:"+DateUtils.getDayStart(yestoday));
		System.out.println("yestoday end:"+DateUtils.getDayEnd(yestoday));
	}
	
	@Test
	public void testWeChatSign(){
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String charset = "utf-8";
		
		String appId = "wxd930ea5d5a258f4f";
		String body = "首期款";
		String mchId = "10000100";
		String nonceStr = "ibuaiVcKdpRxkhJA";
		String outTradeNo = UUIDUtils.genDateUUID("");
		String totalFee = "1000";
		String ip = "119.139.199.234";
		String notifyUrl = "http://baidu.com/";
		String tradeType = "APP";
		
		
		SortedMap<String, Object> sortMap = new TreeMap<>();
		sortMap.put("appid", appId);
		sortMap.put("mch_id", mchId);
		//sortMap.put("device_info", "1000");
		sortMap.put("body",body);
		sortMap.put("nonce_str", nonceStr);
		sortMap.put("out_trade_no", outTradeNo);
		sortMap.put("total_fee", totalFee);
		sortMap.put("spbill_create_ip", ip);
		sortMap.put("notify_url", notifyUrl);
		sortMap.put("trade_type", tradeType);
		
		String sign = WeiXinSignUtils.createSign("UTF-8", sortMap, "192006250b4c09247ec02edce69f6a2d");
		log.info(">>>>>>>>>>>>>>>>>> sign:{}",sign);
		
		Document document = DocumentHelper.createDocument();  
		Element xml = document.addElement("xml");
		//document.
		Element appidE = xml.addElement("appid");
		appidE.setText(appId);
		// 商品描述
		Element bodyE = xml.addElement("body");
		bodyE.setText(body);
		// 商户id
		Element mch_idE = xml.addElement("mch_id");
		mch_idE.setText(mchId);
		// 随机字符串
		Element nonce_strE = xml.addElement("nonce_str");
		nonce_strE.setText(nonceStr);
		// 签名
		Element signE = xml.addElement("sign");
		signE.setText(sign);
		// 商户订单号
		Element out_trade_noE = xml.addElement("out_trade_no");
		out_trade_noE.setText(outTradeNo);
		// 订单总金额
		Element total_feeE = xml.addElement("total_fee");
		total_feeE.setText(totalFee);
		// 用户端实际ip
		Element spbill_create_ipE = xml.addElement("spbill_create_ip");
		spbill_create_ipE.setText(ip);
		// 通知地址
		Element notify_url = xml.addElement("notify_url");
		notify_url.setText(notifyUrl);
		// 用支付类型
		Element trade_type = xml.addElement("trade_type");
		trade_type.setText(tradeType);
		
		String xmlData = document.getRootElement().asXML();
		log.info(">>>>>>>>>>>>>>>>> xmlData {}",xmlData);
		try {
			String str  = HttpClientUtil.post(url, xmlData, MediaType.APPLICATION_XML_VALUE, charset , null, null);
			log.info(">>>>>>>>>>>>>>>>>>>>>>> str {}",str);
		} catch (Exception e) {
			log.error("统一下单异常:{}",e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testXMl(){
		Document document = DocumentHelper.createDocument(); 
		document.addElement("xml");
		Element rootElement = document.getRootElement();
		Element a = rootElement.addElement("a");
		a.setText("123");
		Element b = rootElement.addElement("b");
		b.setText("456");
		String xmlData = document.asXML();
		log.info(">>>>>>>>>>>>>>>>>>>>>> xmlData {}",xmlData);
		
		log.info(">>>>>>>>>>>>>>>>>>>>>> getRootElement {}",document.getRootElement().asXML());
	}
}
