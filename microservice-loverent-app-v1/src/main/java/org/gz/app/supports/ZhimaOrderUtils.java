package org.gz.app.supports;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.configure.AlipayXCXConfigure;
import org.gz.app.vo.ZhimaOrderDetailVo;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZhimaMerchantOrderConfirmRequest;
import com.alipay.api.response.ZhimaMerchantOrderConfirmResponse;

/**
 * 芝麻信用租赁util
 * 
 * @author yangdx
 *
 */
@Slf4j
public class ZhimaOrderUtils {

	public static ZhimaOrderDetailVo queryZhimaOrderDetail(String orderNo) {
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(
					AlipayXCXConfigure.GATE_WAY, 
					AlipayXCXConfigure.APPID, 
					AlipayXCXConfigure.RSA_PRIVATE_KEY, 
					AlipayXCXConfigure.FORMAT, 
					AlipayXCXConfigure.CHARSET, 
					AlipayXCXConfigure.ALIPAY_PUBLIC_KEY, 
					AlipayXCXConfigure.SIGNTYPE);
			
			String transactionId = buildTransactionId();
			
			ZhimaMerchantOrderConfirmRequest request = new ZhimaMerchantOrderConfirmRequest();
			request.setBizContent("{" +
			"\"order_no\":\""+orderNo+"\"," +
			"\"transaction_id\":\""+transactionId+"\"" +
			"  }");
			ZhimaMerchantOrderConfirmResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				JSONObject bodyResp = JSONObject.parseObject(response.getBody());
				JSONObject orderResp = bodyResp.getJSONObject("zhima_merchant_order_confirm_response");
				if (orderResp != null) {
					ZhimaOrderDetailVo vo = new ZhimaOrderDetailVo();
					vo.setCertNo(orderResp.getString("cert_no"));
					vo.setChannelId(orderResp.getString("channel_id"));
					vo.setCreditAmout(orderResp.getString("credit_amount"));
					vo.setHouse(orderResp.getString("house"));
					vo.setMobile(orderResp.getString("mobile"));
					vo.setName(orderResp.getString("name"));
					vo.setUserId(orderResp.getString("user_id"));
					vo.setZmFace(orderResp.getString("zm_face"));
					vo.setZmGrade(orderResp.getString("zm_grade"));
					vo.setZmScore(orderResp.getString("zm_score"));
					return vo;
				}
			}
		} catch (Exception e) {
			log.error("---->【芝麻订单查询】--query faild, e: {}", e);
		}
		return null;
	}
	
	/**
	 * transcation id
	 * @return
	 */
	private static String buildTransactionId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
