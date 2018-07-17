package org.gz.thirdParty.service.alipay;

import java.math.BigDecimal;

import org.gz.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.ZhimaMerchantOrderCreditPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.ZhimaMerchantOrderCreditPayResponse;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:支付宝支付服务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月28日 	Created
 */
@Slf4j
public class AlipayService {
	
	private static String APP_ID = "2017120800456351";
	@Value("alipay.private.key")
	private static String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0tYX/3l+D7waZkX0cJL7NcWnEBe7i+NUt4+L1+hQ8nv1zzyzwBrP12b516Ksi7bu2nGssUfJbYkMLdgFBhS5B2nz+hlyGrvK+MgR6jb/ES5hwloNorGism9giJAJi3hgQ3QkWHTrRmeeRtDw2pPiQ2MXgwcKUUxab8A2mcTeolm5Dcr2xizbp1pkXxR1lzCc43rcZf8JwQ1+qGfy1I+dCPTM1UZmxKvQdTil+JTQ6afN2exNaP9vhY4cQzRctH2byReuaRxatqnBGU1V2WRLUIFWLt6l/hFqiriFwviOjaAj8yDrosx58sfap9gVmEGf8jZVHUdH160eXMJEWoBzxAgMBAAECggEBAKFboQ5K6vDswvDnN1ak/PaHOZC+V4O1+uyiGuvW5LEIqnn5WT/uTEzPpEC2g/GJY974Jr6JYaX1xLX8Jbzyhc5poMjJAt+EYd4w3UztO3m0oqaNz0LtB/5wnfpGOxc7BzQvmkhzyyyTh3HUBRwqDIWR+obpnKxC0OJm5FllqPRP84LNZ1amaqSIfefbN8DcFWfGrquSuBQkVAaPClutnZBifPG/EX0qzkGpI3/BawNDhxDYbq1QEqYiMuMX3HesZuuRC5Tiottd/1uwXR3LPlEIPdIkMJ9yD+yK9pkPg6OhPJKR8bUjwUjYpHc7KsfcnKdBQstbuma7H4QcnwPISG0CgYEA87JqoH7FFZrljDgyZjlvmw7o824iNb2F8rUnEBvl2ENqiUL+QneyvlFB4qgWAoXqg+yp0xtPbO8Eju2Gxbe3Gw+4kz6ZwucJrSe1V8kBTfszoWqdm3JF/SIQpToNrRy+ABncGfQJdK49tEQMjMwh8aGS78RXtEDH4Q3GUGe3W3MCgYEAvdUKU7cHhEWYxbj4fk+wAGLCkxr2AxBLXD3mdzBMkpW1adyBN3RryMdfxUFUmDaUd7IIBg0Qn0kkjNbrseZW6dGR5OX56TSAlEBBSeMdTAJ33J+tgx1RRDf6oSzHvebzY06Nz2PZ5FYsbAKipl5lK6Zt04+S3vx6TUSNmWmWVQsCgYEA5ab9VMIieHnAWKOc2zjFNBqY7M6c7ofr8w1EAqpHr8XYYAICGiEzEKcCr9Y256nLlwcwAwF5l+6nHQo6N5kptRbbmeckf8e6FrOU8WU7FYHk5fjTDepiimnPxeUvfjPX2cCE6vBO+OY6v+Zg7U5xroPVNJ6TCAsEUdHpUVxsLoECgYA6S9hWCRk315+o6Lr3H0WveKTERD1Uh4Z9F01VQPHi+jKyBdNw0kHfcMrKatutuuUxNO0d3plCMVYWIlJTWadLOt6Q6scqhMMPxAPIywROOVvR1v+UCkbQqlg/ct8MRIZt8su96q4ZXxVZPh3w9YAgPYXwuGCkNejhHbJWcISILQKBgBDhCQDesaLKLMI/jmK8rqNqQEvyvd7sAa7Q6Yp/GfIOuiJ7VYUSwngLdBvXYUs3lAah9bvPMG8T9VqQG9mP37UKiyg6F/glRkVk74X0z0EvB8/kR098kBF72mRU/Y4eGy72+EdjMTbNIarida1OrP6U2z2vq4vNkSWlHzroZ/hD";
	@Value("alipay.public.key")
	private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlm9CmqVNdVtUtTe4cyRFfXZXy3t2d4ox9bKKTk7wKLN4KuYIeoKkxs6aksyacox1Kf9vrNX8fHA7L0BTQderuv0d8tsn7R5DumoNB8lxd2ST+PyYdTkhUX46r7CuTQZkndHsgwUE82SqFqeZbhbHHR0sBhb/AxhWWsrSG7lSKmUBIxpdv4hIeUi48BPvkVOuZjJM5g5O/h02Lsoo/q0U5l/MNoUutUm65NWD+Ye/8YqX6yniiNiQW10vwJzeNwuC9OJ6XO4UyqZbPlrXYBcz3a9ClCZMCSpI09yMciBatFQHNgge2eMOGZrGdxAmFCQ5ntp5MBMjigqiZoNDiUUg5wIDAQAB";
	/**
	 * 小程序应用id
	 */
	@Value("applet.alipay.app.id")
	private static String APPLET_APP_ID = "2018031602388641";
	/**
	 * 小程序私钥
	 */
	@Value("applet.alipay.private.key")
	private static String APPLET_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBqOqEDgkQQqlufF4PKMl4z2n/JcdDFjYYiDptliH+Lkv9nTD2fxhvUy8IQhYM8JuklNJa27kKG3UjkQMVMPQMzumu6eLhk1FNCxbtHkohI0pQHfM+YL4HWmnXv5qwPEpn6f/ELI3B1v/mKCDEB596Hzk/3RrvX+9DZ4VdCiZ72gQ+aEFFkeRy70OYWg5xjDsv8jflNMReH9bYM3C1IEOOSpTsFHlxGJR4Kr0n3XVcdoCHtG5BBG3yclesKrrfk38M2OS0dXYPTdCReSnzc1D6jJj4VYKecEBgJ2Bez+V3mN9z+OFp5FAFsqNSXr6GvUabW1Ta1ADYHqntvp7c1ejbAgMBAAECggEACw8zYawozcBB33CwPS7HcIyQh/ja9DfyyUjlYu+JWrrsNGeyA3R3yxcXTecFJnlnbTRcdO+E6v3Ikg4Oa0JPrr//YrIEbiU4rA+kFvmcbRMrhYXod4uc6dema4amVR3vYsmszTjXpo8GKMWHdfEIg0wif/YGFCDksaUk1PjGq42fYdb/wog6o2BDjPUyEKghKt2lxjwvMSIqVLvXI7WuHEKiml8pZwEGq2vbZBhHajicu30FcdV5x0vk68wtH0gWQ/uHhk68xMXHQqGHO3zG7NyK0JEXX8SgdkbgMYT9IA8zRzpbzHeF/NAYYz5SIpEjpox7zU6fP+duBlx4dSYPgQKBgQDc+dNlGbFhhJ0yIIZXEdDf/M+ndoM1AmZ8+JUsrz3GKHUiMBAlH++V6bQrVyE6FDBWJ+6xaDSG10KaCIJOKCcUoG09TWZBcVm/s6MlsBCpVZN/1C6ijCX4juzEAeOPX7WambEw4b4pR04IN7m4Qq+x2vlYMV81H5t3waojkXPsIQKBgQCWNejG2hXymY56AqWBzKBAxo+fADxBuCt/19M2NpL/AN14ACwXN62LCdBK7N6CbS85TIyiEBd0Nty/k6uuC+hcUK1wi94QGBy7NYuO+pG23YKAUJQOST7SK+971GWTIyOBYBzeeSix8senUO0izUqSSvScIie9xk/Q6THv0SzVewKBgAbH17fDeRDv+87c9qJXVpx46npOBaqrvy9YhWNQoUmiVi3NYcFx4G0uNawayVc55V3PbeCdvPbiRhXp6u2xB7n3TkLSTC/35XQfdAMlCu3XRWZnwWKVgFjHMfa1xCDTJARxcOF6G9aybG+OHjHMQg3oFTyijzRXGvMqyyi6Pt9hAoGAdPcVg4JehMI9x5Q3/JKyVr5HJ7CvOx2GAc61kQl9/UNXMRIxQwMAB7xkjXNU8IrEswGAoNGW8c+mpScAEE8FaJ0J8RF4oV+lP8G6ImPLA3IJJJGaOGWgnVfI68vALIq4Iw4MbNA7eBVskkTZ4reo0bG8N7gA6i16CVsMn4lqN/ECgYAc31GcZnlEirxk0NzhjXhWOsVAOww64HcBFY+F13GQeAkQJGaRRcg/K03AGhSaso5QDRiS81RBpu2dQf6GgKURHsCRC3JetEwu9ucxpC2KSVHgwEaecfLIWFpzE3gXVEMZvhVzt0+D1R4nNzITSy7h5hLzS/Ia7sDrFSBOLphCbg==";
	/**
	 * 小程序公钥
	 */
	@Value("applet.alipay.public.key")
	private static String APPLET_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlm9CmqVNdVtUtTe4cyRFfXZXy3t2d4ox9bKKTk7wKLN4KuYIeoKkxs6aksyacox1Kf9vrNX8fHA7L0BTQderuv0d8tsn7R5DumoNB8lxd2ST+PyYdTkhUX46r7CuTQZkndHsgwUE82SqFqeZbhbHHR0sBhb/AxhWWsrSG7lSKmUBIxpdv4hIeUi48BPvkVOuZjJM5g5O/h02Lsoo/q0U5l/MNoUutUm65NWD+Ye/8YqX6yniiNiQW10vwJzeNwuC9OJ6XO4UyqZbPlrXYBcz3a9ClCZMCSpI09yMciBatFQHNgge2eMOGZrGdxAmFCQ5ntp5MBMjigqiZoNDiUUg5wIDAQAB";
	/**
	 * 
	 * @Description: APP交易查询
	 * @param outTradeNo 商户订单号 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 tradeNo,outTradeNo如果同时存在优先取tradeNo
	 * @param tradeNo	支付宝交易号
	 * @param transactionSource	交易来源: ALI_APPLET:小程序订单 其他都认为是APP订单
	 * @return
	 * @throws AlipayApiException 
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月28日
	 */
	public static AlipayTradeQueryResponse queryTrade(String outTradeNo,String tradeNo,String transactionSource) throws AlipayApiException{
		String appId = APP_ID;
		String privateKey = PRIVATE_KEY;
		String alipayPublicKey = ALIPAY_PUBLIC_KEY;
		if(transactionSource.equals("ALI_APPLET")){
			appId = APPLET_APP_ID;
			privateKey = APPLET_PRIVATE_KEY;
			alipayPublicKey = APPLET_PUBLIC_KEY;
		}
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,privateKey,"json","GBK",alipayPublicKey,"RSA2");
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		
		request.setBizContent("{" +
		"\"out_trade_no\":\""+outTradeNo+"\"," +
		"\"trade_no\":\""+tradeNo+"\"" +
		"  }");
		
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		log.info("queryTrade response:{}",JsonUtils.toJsonString(response));
		return response;
	}
	/**
	 * 
	 * @Description: 芝麻信用支付
	 * @param orderOperateType 	订单操作类型，包括取消(CANCEL)、完结(FINISH)
	 * @param zmOrderNo			芝麻订单号
	 * @param outOrderNo		外部订单号,包含字母、数字、下划线，调用方需要保证订单号唯一
	 * @param outTransNo		外部资金订单号，可包含字母、数字、下划线
	 * @param payAmount			支付总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]，无支付行为时为空，例如订单取消或者支付金额为0
	 * @param remark			订单操作说明
	 * @return
	 * @throws AlipayApiException
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月30日
	 */
	public static ZhimaMerchantOrderCreditPayResponse creditPay(String orderOperateType, String zmOrderNo, String outOrderNo,
			String outTransNo, BigDecimal payAmount,String remark) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APPLET_APP_ID,APPLET_PRIVATE_KEY,"json","GBK",APPLET_PUBLIC_KEY,"RSA2");
		ZhimaMerchantOrderCreditPayRequest request = new ZhimaMerchantOrderCreditPayRequest();
		request.setBizContent("{" +
		"\"order_operate_type\":\""+orderOperateType+"\"," +
		"\"out_order_no\":\""+outOrderNo+"\"," +
		"\"zm_order_no\":\""+zmOrderNo+"\"," +
		"\"out_trans_no\":\""+outTransNo+"\"," +
		"\"pay_amount\":"+payAmount.toPlainString()+"," +
		"\"remark\":\""+remark+"\"" +
		"  }");
		
		ZhimaMerchantOrderCreditPayResponse response = alipayClient.execute(request);
		log.info("creditPay response:{}",JsonUtils.toJsonString(response));
		return response;
	}
	
	
}
