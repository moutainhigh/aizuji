package org.gz.thirdParty.service.lianlian;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.Md5Algorithm;
import org.gz.common.utils.TraderRSAUtil;
import org.gz.thirdParty.bean.lianlian.OrderQueryRespBean;
import org.gz.thirdParty.bean.lianlian.QuerySignRespBean;
import org.gz.thirdParty.bean.lianlian.UnsignRespBean;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LianlianService {

	private static String TRADER_MD5_KEY = "201608101001022519_test_20160810";
	
	//正式国智私钥
//    private final static String ZHENGSHI = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKMZjEv6hbzwrr/teNXfEqEzZX7ft14x5y1OInaFKCzz3M6Z/Odg3W1nUdDR55tzSfQ25K7tvGJ+AIeXqQX/an2qbbQOC1U9SXLh5OineZGRmQOUh02PVqixQOq8z6CZFHUmol88WOOj6p1u4S7dBpVwfyABGkXRPvffPhT5ySmvAgMBAAECgYEAgrwTzUM+uDhkmsuLNEPe9v/vt0c0AHkXyST8UuxhS8cdky2znvPyCaPr3OqkL+K1wN/PZriBbKF7YzSZ84jMni7A4lKXfe2pA5seKDUjtCDo79wbFVXkHqxzwSpwl554/sBFV9/0d1qOzsYblUuANq9Jv9Ok5/GIJVlq4R/2J4ECQQDP3R8s05Bhc6W6FFnaX0rHTS/oiAGzyhYYd3mN5pZaLiC7ITLh/AJZDDD53kH9/ODkQ6sZgCDbmtV3E1ITGIdRAkEAyN6qMCK7zvSdC+RsojMBvlKHVSlf2kFnxKdNXTeCIahMJhcIp7p3/yXgyT+IE/klKRvc7nDIUUbY0EwA+Nxg/wJAPX4xBtXf1LSdxprWqh+ew682CRiTSFj0iHBv6WbZ+/vBexqrLuea2jUdGA6Ef1scPOs35udc0RrRI9T3ZubOwQJBAJo9N+tcUF/6vriJ/syRZA5Taq1+5qY9wMNlP+eLHvZfN5Gr8C5y5X9bA38ktIW5ssodJglFOtwGDafsJHbzKdsCQCdUfwPOq0fZT8u4ZH7FPVTpJ2SNqwLvckuBpP+Rj/my7FVCx7v8XG0Dm9gAmouVF6GZdNkYjgm0mEgf5hdIaRY=";
    private final static String ZHENGSHI = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKKfdldBnKGe9RXHM/NU8juCzqT4cYk2WqjUyWmaFDy5UhBKrthbY+6ZKKI70OjwR5d0BkktVh3OjwgEL36voPabHkH6/z9VT3D5t786lgQH+1OFUiPAUGsodUoHZrSWmyVnJBseFWGIWulV5IKOjLDu67qjP16cRMab57Ri69czAgMBAAECgYBNDfvX2mnqFtSQLiRKfwyL8C4T8vhxCIUqjDCnTe+a2kCtIYX5VExkiMO9I1SYmwmBFOPJlqbAVthk9v6+K1+72FHBCXeen8XBiWB5ev8tOxaatkqhfHHZKgSFjmxT748WexlTq7syyAb3wgyqLUccEUSN5+hoYx6hAvpKae/DwQJBANH8xqhfs2vn8SOcFJHo22itj5KmUgXghm28+aeh0D9vl9P4xkcHhv7gj5PIvp2CpUUicDcKoJ5W6FgtNIDlJmECQQDGQcdWrIFbMVNgIwNgaZ9IpxtiY//m8YqXRgPL21Q+vjiehdXqWJj2bqux5bdP8QOW2l7r732JRMROu1IaLr4TAkEAm3aRfUadB16I4NxFPmEvT6hvixsnzsITxFsMWlcqXky4E28zHJMuFrUal0cgGG0I/s4oVhfAInolOmL9ZBBDAQJBAJu2+mwXHZqUiVnO5k4JZ3PW3GlRBaNMP4BFG6I36FlHao0HrVZcs/eKQQx+0pXVRO5tIXTKK51vB4iXFAtAf0UCQHibA7ls3F9Hyjz5s7Q2Weum+D3YGUnr/6BRQUTtcGqDZ1L+oj0OTWz5JBfEeMPQ7rw3zG/nOO4P6uhJFKVwnbY=";
//	测试国智私钥
//    private final static String ZHENGSHI = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    //正式商户号
//    private final static String storeId = "201701051001392503";
    //互联网
    private final static String storeId = "201712210001305154";
    //测试商户号
//    private final static String storeId = "201408071000001543";

	private String url = "https://repaymentapi.lianlianpay.com/agreenoauthapply.htm";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;

	public LianlianService(){  
	        httpClientUtil = new HttpClientUtil();  
	    }

	/**
	 * 查询订单交易状态
	 * @param orderNo 订单编号
	 * @param orderTime 订单时间yyyyMMddHHmmss 
	 */
	public static OrderQueryRespBean queryOrder(String orderNo,String orderTime){
		String url = "https://queryapi.lianlianpay.com/orderquery.htm";
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		
		createMap.put("no_order", orderNo);//商户唯一订单号* 
		createMap.put("dt_order", orderTime);//珊瑚订单时间*
//		createMap.put("dt_order", DateUtils.getString(new Date(), "yyyyMMddHHmmss"));//珊瑚订单时间*
		
		createMap.put("sign",genSign(createMap));//签名*
		String jsonString = JsonUtils.toJsonString(createMap);
		String httpOrgCreateTestRtn = null;
		try {
			httpOrgCreateTestRtn = HttpClientUtil.postParametersJson(url, jsonString);
			log.info("lianlian's queryOrder result:" + httpOrgCreateTestRtn);
			OrderQueryRespBean bean = JSON.parseObject(httpOrgCreateTestRtn, OrderQueryRespBean.class);
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("lianlian's queryOrder is error.",e);
			return null;
		}
	}
	
	public static QuerySignRespBean querySign(String userId){
		String url = "https://queryapi.lianlianpay.com/bankcardbindlist.htm";
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("version", "1.0");//
		createMap.put("user_id", userId);//
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		createMap.put("pay_type", "D");//签名方式*
		
		createMap.put("offset", "0");//签名方式*
		createMap.put("sign",genSign(createMap));//签名*
		String jsonString = JsonUtils.toJsonString(createMap);
		String httpOrgCreateTestRtn = null;
		try {
			httpOrgCreateTestRtn = HttpClientUtil.postParametersJson(url, jsonString);
			log.info("lianlian's queryOrder result:" + httpOrgCreateTestRtn);
			QuerySignRespBean bean = JSON.parseObject(httpOrgCreateTestRtn, QuerySignRespBean.class);
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("lianlian's queryOrder is error.",e);
			return null;
		}
	}
	
	public static void userSign(){//用户签约
		String url = "https://cashier.lianlianpay.com/payment/instalmentsign.htm";
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("version", "1.0");//
		createMap.put("user_id", "");//
		createMap.put("timestamp", "");//
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		
		createMap.put("url_return", "");//签约结束回显URL
		createMap.put("risk_item", "");//风控规则
		createMap.put("id_no", "");//证件号码
		createMap.put("acct_name", "");//银行账号姓名
		createMap.put("card_no", "");//银行卡号
	}
	
	public static UnsignRespBean unsign(String userId,String no_agree){
		String url = "https://traderapi.lianlianpay.com/bankcardunbind.htm";
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("user_id", userId);//
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		createMap.put("pay_type", "D");//签名方式*
		
		createMap.put("no_agree", no_agree==null?"2018011045857494":no_agree);//签约协议号
		createMap.put("sign",genSign(createMap));//签名*
		String jsonString = JsonUtils.toJsonString(createMap);
		String httpOrgCreateTestRtn = null;
		try {
			httpOrgCreateTestRtn = HttpClientUtil.postParametersJson(url, jsonString);
			log.info("lianlian's queryOrder result:" + httpOrgCreateTestRtn);
			UnsignRespBean bean = JSON.parseObject(httpOrgCreateTestRtn, UnsignRespBean.class);
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("lianlian's unsign is error.",e);
			return null;
		}
	}
	
	public static void main(String[] args) {
//		LianlianService test = new LianlianService();
//		OrderQueryRespBean resp = queryOrder("S1711220004450724-5222244","20171210120228");
//		System.out.println(JsonUtils.toJsonString(resp));
		//{"no_order":"20180108194225","oid_partner":"201408071000001543","repayment_no":"6214857554705543","busi_partner":"101001","sign_type":"RSA","notify_url":"http:\/\/test.yintong.com.cn:80\/apidemo\/API_DEMO\/notifyUrl.htm","acct_name":"唐经纬","name_goods":"龙禧大酒店中餐厅：2-3人浪漫套餐X1","money_order":"0.01","risk_item":"{\"user_info_bind_phone\":\"13958069593\",\"frms_ware_category\":\"4.0\",\"user_info_dt_register\":\"201407251110120\",\"request_imei\":\"1122111221\"}","sign":"DzQCmLTdVlG7\/MPifuL7xupO82Z62SOeZpkxyqqWa8kBQnaD6cheLlGmLZQd9v03bwSbFD0+RRxxiQJ8sbnavTOdRlRrBpWY10yrWts1mFU1IAbrLYHlxZFGsA+IHE6UrPBYKTxxR90M8WUMDv2tQr6WaybQNQxypERKzdOx2qo=","valid_order":"100","repayment_plan":"{\"repaymentPlan\":[{\"date\":\"2018-01-08\",\"amount\":\"0.01\"}]}","dt_order":"20180108194225","user_id":"55555","flag_modify":"1","sms_param":"{\"contact_way\":\"0571-56072600\",\"contract_type\":\"LianLianPay\"}","id_no":"430407198606291512"}
		String userId = "42";
		QuerySignRespBean res = querySign(userId);
		System.out.println("=========>>>>>>>>>>>>"+res.getAgreement_list());
		if(res.getAgreement_list()!=null&&res.getAgreement_list().size()>0){
			unsign(userId,res.getAgreement_list().get(0).getNo_agree());
		}
	}
	
	
    private static String genSign(Map<String, String> createMap) {
        String sign_type = createMap.get("sign_type");
        // 生成待签名串
        String sign_src = genSignDataNotEmpty(createMap);
        System.out.println(sign_src);
        if ("MD5".equals(sign_type)) {
            sign_src += "&key=" + TRADER_MD5_KEY;
            return signMD5(sign_src);
        }
        if ("RSA".equals(sign_type)) {
            return getSignRSA(sign_src);
        }
        return null;
    }
    
    private static String signMD5(String signSrc) {
        try {
            return Md5Algorithm.getInstance().md5Digest(
                    signSrc.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getSignRSA(String sign_src) {
        return TraderRSAUtil.sign(ZHENGSHI, sign_src);
    }
    
    private static String genSignDataNotEmpty(Map<String, String> createMap) {
        StringBuilder content = new StringBuilder();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<>(createMap.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            // sign 和ip_client 不参与签名
            if ("sign".equals(key)) {
                continue;
            }
            String value = createMap.get(key);
            // 空串不参与签名
            if (null == value || "".equals(value)) {
                continue;
            }
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }

        return signSrc;
    }
}
