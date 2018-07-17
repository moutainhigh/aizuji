package org.gz.app.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.gz.app.configure.LianPayConfigure;
import org.gz.app.entity.LianPayCallBack;
import org.gz.app.entity.OrderQueryRespEntity;
import org.gz.app.entity.SignRespEntity;
import org.gz.app.feign.CouponServiceClient;
import org.gz.app.feign.PaymentServiceClient;
import org.gz.app.feign.RentRecordServiceClient;
import org.gz.cache.service.lianpay.LianPayCityCodeCacheService;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.LianPayUtils;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponUserQuery;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.CardInfo;
import org.gz.user.service.CardInfoService;
import org.gz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/lianpay")
@Slf4j
public class LianPayController extends AppBaseController {

	//正式互联网私钥
	private final static String RSAKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKKfdldBnKGe9RXHM/NU8juCzqT4cYk2WqjUyWmaFDy5UhBKrthbY+6ZKKI70OjwR5d0BkktVh3OjwgEL36voPabHkH6/z9VT3D5t786lgQH+1OFUiPAUGsodUoHZrSWmyVnJBseFWGIWulV5IKOjLDu67qjP16cRMab57Ri69czAgMBAAECgYBNDfvX2mnqFtSQLiRKfwyL8C4T8vhxCIUqjDCnTe+a2kCtIYX5VExkiMO9I1SYmwmBFOPJlqbAVthk9v6+K1+72FHBCXeen8XBiWB5ev8tOxaatkqhfHHZKgSFjmxT748WexlTq7syyAb3wgyqLUccEUSN5+hoYx6hAvpKae/DwQJBANH8xqhfs2vn8SOcFJHo22itj5KmUgXghm28+aeh0D9vl9P4xkcHhv7gj5PIvp2CpUUicDcKoJ5W6FgtNIDlJmECQQDGQcdWrIFbMVNgIwNgaZ9IpxtiY//m8YqXRgPL21Q+vjiehdXqWJj2bqux5bdP8QOW2l7r732JRMROu1IaLr4TAkEAm3aRfUadB16I4NxFPmEvT6hvixsnzsITxFsMWlcqXky4E28zHJMuFrUal0cgGG0I/s4oVhfAInolOmL9ZBBDAQJBAJu2+mwXHZqUiVnO5k4JZ3PW3GlRBaNMP4BFG6I36FlHao0HrVZcs/eKQQx+0pXVRO5tIXTKK51vB4iXFAtAf0UCQHibA7ls3F9Hyjz5s7Q2Weum+D3YGUnr/6BRQUTtcGqDZ1L+oj0OTWz5JBfEeMPQ7rw3zG/nOO4P6uhJFKVwnbY=";
	//测试国智私钥
//  private final static String RSAKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
	private final static String storeId = "201712210001305154";//互联网
  //测试商户号
//  private final static String storeId = "201408071000001543";
	//连连公钥
	private final static String LIANRSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CardInfoService cardInfoService;
	
	@Autowired
	private LianPayConfigure lianPayConfigure;
	
	@Autowired
	private PaymentServiceClient paymentServiceClient;
	
	@Autowired
	private RentRecordServiceClient rentRecordServiceClient;
	
	@Autowired
	private LianPayCityCodeCacheService lianPayCityCodeCacheService;
	
	@Autowired
	private CouponServiceClient couponServiceClient;
	
	/**
	 * 连连银行卡绑定回调入口
	 * @param cardNo
	 * @param signRespEntity
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/bindingCallBack/{cardNo}")
	public void bindingCallBack(@PathVariable("cardNo") String cardNo,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("lianpay bindingCallBack is begin.");
		System.err.println("conentType:"+request.getContentType());
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String nameString = names.nextElement();
			System.err.println(nameString+"="+request.getParameter(nameString));
		}
		String result = request.getParameter("result");
		ObjectMapper objectMapper = new ObjectMapper();
		SignRespEntity signRespEntity = objectMapper.readValue(result, SignRespEntity.class);
		log.info("lianpay bindingCallBack is begin.{}",JSON.toJSONString(signRespEntity));
		String no_agree = signRespEntity.getNo_agree();//签约协议号
		String user_id = signRespEntity.getUser_id();//用户ID
		System.out.println("cardNo:"+cardNo);
		CardInfo cardInfo = new CardInfo();
		cardInfo.setUserId(Long.parseLong(user_id));
		cardInfo.setCardNo(cardNo);
		cardInfo.setNoAgree(no_agree);
		ResponseResult<String> res = cardInfoService.updateCardByUserId(cardInfo);
		log.info("lianpay bindingCallBack is end.{}",JSON.toJSONString(res));
		if(res.getErrCode()==0){
			//跳转前台提示成功页面
			response.sendRedirect(lianPayConfigure.getSignSuccessUrl());
		}else{//跳转错误页面
			response.sendRedirect(lianPayConfigure.getSignFailureUrl());
		}
	}
	/**
	 * 连连银行卡绑定回调入口
	 * @param cardNo
	 * @param signRespEntity
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	@PostMapping(value = "/bindingCallBack/{cardNo}")
	public void bindingCallBack(@PathVariable("cardNo") String cardNo,SignRespEntity signRespEntity,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("lianpay bindingCallBack is begin.{}",JSON.toJSONString(signRespEntity));
		String no_agree = signRespEntity.getNo_agree();//签约协议号
		String user_id = signRespEntity.getUser_id();//用户ID
		System.out.println("cardNo:"+cardNo);
		CardInfo cardInfo = new CardInfo();
		cardInfo.setUserId(Long.parseLong(user_id));
		cardInfo.setCardNo(cardNo);
		cardInfo.setNoAgree(no_agree);
		ResponseResult<String> res = cardInfoService.updateCardByUserId(cardInfo);
		log.info("lianpay bindingCallBack is end.{}",JSON.toJSONString(res));
		if(res.getErrCode()==0){
			//跳转前台提示成功页面
			response.sendRedirect(lianPayConfigure.getSignSuccessUrl());
		}else{//跳转错误页面
			response.sendRedirect(lianPayConfigure.getSignFailureUrl());
		}
	}
	
	/**
	 * 连连支付回调入口(测试使用)
	 * @param lianPayCallBack
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/LianPayCallBack")
	public Map<String, String> lianPayCallback(@RequestBody String object){
		log.info("lianPayCallback is begin.{}"+JSON.toJSONString(object));
		JSONObject jsonObject = JSON.parseObject(object);
		LianPayCallBack lianPayCallBack = JSON.toJavaObject(jsonObject,LianPayCallBack.class);
		String order = lianPayCallBack.getNo_order();
		String result_pay = lianPayCallBack.getResult_pay();
		System.out.println("lianPayCallback order:"+order+" result_pay:"+result_pay);
        //连连要求返回格式
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        log.info("lianPayCallback is end.{}",JSON.toJSONString(map));
		return map;
	}
	
	/**
	 * 上传签约协议号
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/uploadNoAgree", method = RequestMethod.POST)
	public ResponseResult<?> uploadNoAgree(@RequestBody JSONObject body){
		Long user_id = body.getLong("user_id");
		if(user_id==null){
			return ResponseResult.build(9527, "user_id为空", body);
		}
		String card_no = body.getString("card_no");
		if(StringUtils.isBlank(card_no)){
			return ResponseResult.build(9527, "card_no为空", body);
		}
		String no_agree = body.getString("no_agree");
		if(StringUtils.isBlank(no_agree)){
			return ResponseResult.build(9527, "no_agree为空", body);
		}
		CardInfo cardInfo = new CardInfo();
		cardInfo.setUserId(user_id);
		cardInfo.setCardNo(card_no);
		cardInfo.setNoAgree(no_agree);
		ResponseResult<String> res = cardInfoService.updateCardByUserId(cardInfo);
		return res;
	}
	
	/**
	 * 获取连连支付签名
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/getPaySign", method = RequestMethod.POST)
	public ResponseResult<?> getPaySign(@RequestBody JSONObject body, HttpServletRequest request){
		log.info("lianpay getPaySign is begin.{}",JSON.toJSONString(body));
		List<String> list = getUserFields(request, "userId", "phoneNum");
    	Long user_id = Long.valueOf(list.get(0));
    	log.info("lianpay getPaySign start query user info, userId: {}", user_id);
    	ResponseResult<AppUser> result = userService.queryUserById(user_id);
    	log.info("lianpay getPaySign query user info end, result: {}", JSONObject.toJSONString(result));
    	if(result.getData()==null){
    		return result;
    	}
    	
    	//检查优惠券是否已使用
    	Long couponId = body.getLong("couponId");
		if (couponId != null) {
			try {
				checkCoupon(couponId, user_id);
			} catch (Exception e) {
				ResponseResult<?> errRst = new ResponseResult<>();
				errRst.setErrCode(-1);
				errRst.setErrMsg(e.getMessage());
				return errRst;
			}
		}
    	
    	AppUser user = result.getData();
    	Integer type = body.getInteger("type");
    	PayReq req = buildPayReq(body, user_id, list.get(1), user);
    	log.info("lianpay getPaySign start call preparePay");
    	ResponseResult<PreparePayResp> payResult = paymentServiceClient.preparePay(req);
    	log.info("lianpay getPaySign call preparePay end, result: {}", JSONObject.toJSONString(payResult));
    	if (payResult.getErrCode() != 0) {
    		return payResult;
    	}
    	PreparePayResp preparePayResp = payResult.getData(); //获取支付流水号
    	
    	BigDecimal payMoney = new BigDecimal(body.getString("money_order")).setScale(2, RoundingMode.UP);
    	
		Map<String, String> createMap = new HashMap<String, String>();
		//通用参数
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		createMap.put("busi_partner", "101001");//商户业务类型*虚拟商品销售：101001 实物商品销售：109001
		createMap.put("no_order",preparePayResp.getTransactionSN());//商户唯一订单号*
		createMap.put("dt_order", preparePayResp.getTimestamp());//商户订单时间 *
		createMap.put("money_order", payMoney.toString());//交易金额 *
		createMap.put("notify_url", lianPayConfigure.getPayNotifyUrl());//清算服务器异步通知地址*
		
		JSONObject mRiskItem = new JSONObject();
        mRiskItem.put("frms_ware_category", "2037");
        mRiskItem.put("user_info_mercht_userno", user.getUserId());
        mRiskItem.put("user_info_dt_register", DateUtils.getString(user.getCreateTime(), DateUtils.FMT_yyyyMMddHHmmss));
        mRiskItem.put("user_info_bind_phone", user.getPhoneNum());
        mRiskItem.put("user_info_identify_state", "1");
        mRiskItem.put("user_info_identify_type", "1");
        mRiskItem.put("user_info_full_name", user.getRealName());
        mRiskItem.put("user_info_id_no", user.getIdNo());
        mRiskItem.put("user_info_id_type", "0");
        
        mRiskItem.put("goods_name", body.getString("name_goods"));//商品名称
        mRiskItem.put("delivery_phone", user.getPhoneNum());//收货人联系手机
        mRiskItem.put("logistics_mode", "5");//物流方式 1:邮局平邮； 2:普通快递； 3:特快专递； 4:物流货运公司； 5:物流配送公司 6:国际快递 7:航运快运 8:海运
        mRiskItem.put("delivery_cycle", "24h");//发货时间 12h: 12小时内； 24h:24小时内； 48h:48小时内； 72h:72小时内； Other:3天后 
        
        //查询订单详情
        String rentRecordNo = body.getString("order_no");	//订单编号
        ResponseResult<OrderDetailResp> orderResult = rentRecordServiceClient.queryOrderDetail(rentRecordNo);
        OrderDetailResp detailResp = orderResult.getData();
        if (detailResp == null) {
        	return ResponseResult.build(-1, "未获取到订单信息", null);
        }
        
        String provinceStr = buildProvinceCacheKey(detailResp.getProv());
        String cityStr = buildCityCacheKey(detailResp.getCity());
        String provCode = lianPayCityCodeCacheService.getCodeByKey(provinceStr);	//省份
    	String cityCode = lianPayCityCodeCacheService.getCodeByKey(provinceStr + cityStr);	//城市
        if(provCode==null||provCode.equals("")){
        	log.info("--->getPaySign get provCode is null, provinceStr: {}", provinceStr);
        	return ResponseResult.build(-1, "未获取到收货地址省级编码["+detailResp.getProv()+"]", null);
        }
        if(cityCode==null||cityCode.equals("")){
        	log.info("--->getPaySign get cityCode is null, cityStr: {}", cityStr);
        	return ResponseResult.build(-1, "未获取到收货地址市级编码["+ detailResp.getProv() + "," + detailResp.getCity() +"]", null);
        }
        //待修改参数
        mRiskItem.put("delivery_addr_province", provCode);//收货地址省级编码
        mRiskItem.put("delivery_addr_city", cityCode);//收货地址市级编码
        
        
    	if(type==null||type!=1){//h5才有的参数
    		//H5参数
    		//转换风控参数
    		createMap.put("risk_item", mRiskItem.toJSONString());//风控规则
    		createMap.put("version", "1.0");//
    		createMap.put("pay_type", "D");//支付方式
    		createMap.put("app_request", "3");//支付方式
    		createMap.put("timestamp", DateUtils.getString(new Date(), DateUtils.FMT_yyyyMMddHHmmss));//
    		createMap.put("url_return", body.getString("url_return"));//支付结束回显 url 
    		//以下参数参与H5签名
    		createMap.put("user_id", user_id.toString());//
    		createMap.put("id_type", "0");//证件类型 *
    		createMap.put("id_no", body.getString("id_no"));//证件号码*
    		createMap.put("acct_name", body.getString("acct_name"));//银行账号姓名*
    		createMap.put("card_no", body.getString("card_no"));//银行卡号 *
    		createMap.put("no_agree", body.getString("no_agree"));//签约协议号
    		createMap.put("name_goods", body.getString("name_goods"));//商品名称*
            //生成密钥
            createMap.put("sign",LianPayUtils.genSign(createMap,RSAKey,null));//签名*
            //H5转换格式
            createMap.put("risk_item", createMap.get("risk_item").replaceAll("\"", "\\\\\""));
    	}else{//android,IOS 部分参数不参与签名
    		//转换风控参数
    		createMap.put("risk_item", mRiskItem.toJSONString());//风控规则
    		//生成密钥
            createMap.put("sign",LianPayUtils.genSign(createMap,RSAKey,null));//签名*
            //android,IOS不需签名参数
    		createMap.put("user_id", user_id.toString());//
    		createMap.put("id_type", "0");//证件类型 *
    		createMap.put("id_no", body.getString("id_no"));//证件号码*
    		createMap.put("acct_name", body.getString("acct_name"));//银行账号姓名*
//    		createMap.put("name_goods", body.getString("name_goods"));//商品名称*
    		createMap.put("card_no", body.getString("card_no"));//银行卡号 *
    		createMap.put("no_agree", body.getString("no_agree"));//签约协议号
    	}
        log.info("lianpay getPaySign is end.{}",JSON.toJSONString(createMap));
		return ResponseResult.buildSuccessResponse(createMap);
	}
	
	private void checkCoupon(Long couponId, Long userId) {
		CouponUserQuery cuq = new CouponUserQuery();
		cuq.setCouponId(couponId);
		cuq.setUserId(userId);
		log.info("--->【优惠券校验】--start query coupon detail, params: {}", JSONObject.toJSONString(cuq));
		ResponseResult<Coupon> rst = couponServiceClient.queryCouponDetail(cuq);
		log.info("--->【优惠券校验】--query coupon detail success, rst:{}", JSONObject.toJSONString(rst));
		if (rst.getErrCode() == 0) {
			Coupon coupon = rst.getData();
			if (coupon != null) {
				byte used = 1;
				byte status = coupon.getStatus();
				if (status == used) {
					throw new RuntimeException("优惠券已使用");	
				}
				
				Date validEndTime = coupon.getValidityEndTime(); //有效结束时间
				if (validEndTime.getTime() < System.currentTimeMillis()) {
					throw new RuntimeException("优惠券已过期");
				}
			} else {
				throw new RuntimeException("获取优惠券信息失败");
			}
		} else {
			throw new RuntimeException("获取优惠券信息失败");
		}
	}
	
	private String buildCityCacheKey(String city) {
		city = city.trim();
		String suffix = city.substring(city.length()-1, city.length());
		if ("市".equals(suffix)) {
			return city;
		}
		
		if ("北京".equals(city) 
				|| "上海".equals(city)
				|| "天津".equals(city)
				|| "重庆".equals(city)) {
			return city + "市";
		}
		return city + "市";
	}
	private String buildProvinceCacheKey(String prov) {
		prov = prov.trim();
		String suffix = prov.substring(prov.length()-1, prov.length());
		if ("市".equals(suffix) || "省".equals(suffix)) {
			return prov;
		}
		
		if ("北京".equals(prov) 
				|| "上海".equals(prov)
				|| "天津".equals(prov)
				|| "重庆".equals(prov)) {
			return prov + "市";
		} else if ("西藏".equals(prov)
				|| "内蒙古".equals(prov)
				|| "宁夏".equals(prov)
				|| "新疆".equals(prov)) {
			return prov;
		}
		return prov + "省";
	}
	/**
	 * 获取支付流水号实体
	 * @param body
	 * @param userId
	 * @param phoneNum
	 * @param user 
	 * @return
	 */
	private PayReq buildPayReq(JSONObject body, Long userId, String phoneNum, AppUser user) {
		PayReq payReq = new PayReq();
		payReq.setAmount(new BigDecimal(body.getString("money_order")));	//交易金额
		payReq.setOrderSN(body.getString("order_no"));		//app订单编号
		payReq.setPhone(phoneNum);	//手机号
		payReq.setRealName(user.getRealName());	//付款人姓名
		payReq.setSourceType(body.getString("sourceType"));	//销售订单
		payReq.setTransactionSource(body.getString("transactionSource"));	//交易入口
		payReq.setTransactionType(body.getString("transactionType"));	//交易类型 首期款交易/租金/回收/买断/未收货违约
		payReq.setTransactionWay("LianLian");		//交易方式  连连
		payReq.setUserId(userId);	//APP用户ID
		payReq.setUsername(phoneNum);//用户名
		payReq.setFromAccount(body.getString("card_no"));	//付款方账号
		
		Long couponId = body.getLong("couponId");
		if (couponId != null) {
			payReq.setCouponId(couponId);	//优惠券ID
			payReq.setCouponFee(new BigDecimal(body.getString("couponFee")).setScale(2, RoundingMode.UP));	//优惠券金额
		}
		return payReq;
	}

	/**
	 * 获取连连签约签名
	 * @param body type=1 android|IOS,type=0 H5
	 * @return
	 */
    @RequestMapping(value = "/getSign", method = RequestMethod.POST)
	public ResponseResult<?> getSign(@RequestBody JSONObject body, HttpServletRequest request){
    	log.info("lianpay getSign is begin.{}",JSON.toJSONString(body));
    	//组装数据
    	List<String> list = getUserFields(request, "userId", "phoneNum");
    	Long user_id = Long.valueOf(list.get(0));
    	ResponseResult<AppUser> result = userService.queryUserById(user_id);
    	if(result.getData()==null){
    		return result;
    	}
    	Integer type = body.getInteger("type");
    	//查询用户是否签约
    	OrderQueryRespEntity resp = querySign(user_id.toString(),body.getString("card_no"));
    	if(resp==null){//查询失败则返回
    		return ResponseResult.build(9527, "查询用户信息失败", null);
    	}
    	//判断用户是否签约
    	if(resp.getRet_code().equals("0000")){
    		return ResponseResult.buildSuccessResponse(resp.getAgreement_list().get(0));
    	}
    	//没有签约会提示8901  没有记录
    	
    	Map<String, String> createMap = new HashMap<String, String>();
    	if(type==null||type!=1){//h5才有的参数
    		createMap.put("version", "1.1");//
    		createMap.put("app_request", "3");//
    		createMap.put("id_type", "0");//
    		createMap.put("pay_type", "I");//
//    		createMap.put("timestamp", DateUtils.getString(new Date(), DateUtils.FMT_yyyyMMddHHmmss));//
    		String callbackUrl = lianPayConfigure.getSignNotifyUrl() + body.getString("card_no");
    		createMap.put("url_return", callbackUrl);//签约结束回显URL
    	}
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		
		createMap.put("user_id", user_id.toString());//
		createMap.put("id_no", body.getString("id_no"));//证件号码
		createMap.put("acct_name", body.getString("acct_name"));//银行账号姓名
		createMap.put("card_no", body.getString("card_no"));//银行卡号
        //add riskitem 风控规则
        JSONObject mRiskItem = new JSONObject();
        mRiskItem.put("frms_ware_category", "2037");
        mRiskItem.put("user_info_mercht_userno", result.getData().getUserId());
        mRiskItem.put("user_info_bind_phone", result.getData().getPhoneNum());
        mRiskItem.put("user_info_dt_register", DateUtils.getString(result.getData().getCreateTime(), DateUtils.FMT_yyyyMMddHHmmss));
        mRiskItem.put("user_info_full_name", body.getString("acct_name"));
        mRiskItem.put("user_info_id_no", body.getString("id_no"));
        mRiskItem.put("user_info_id_type", "0");
        mRiskItem.put("user_info_identify_type", "1");
        //转换风控参数
        createMap.put("risk_item", mRiskItem.toJSONString());//风控规则
        //生成密钥
        createMap.put("sign",LianPayUtils.genSign(createMap,RSAKey,null));//签名*
        //h5需要转换格式
        if(type==null||type!=1){
        	createMap.put("risk_item", createMap.get("risk_item").replaceAll("\"", "\\\\\""));//风控规则
        }
        log.info("lianpay getSign is end.{}",JSON.toJSONString(createMap));
        
        //添加银行卡绑卡记录--未生效
        CardInfo cardInfo = new CardInfo();
        cardInfo.setAccountName(body.getString("acct_name"));
        cardInfo.setCardCode("");
        cardInfo.setCardIssuing("");
        cardInfo.setCardNo(body.getString("card_no"));
        cardInfo.setCardStatus(2);
        cardInfo.setCreateTime(new Date());
        cardInfo.setIdNo(body.getString("id_no"));
        cardInfo.setNoAgree("");
        cardInfo.setReservedPhoneNum(list.get(1));
        cardInfo.setUserId(user_id);
        
        cardInfoService.addCardIfNotExist(cardInfo);
        
		return ResponseResult.buildSuccessResponse(createMap);
	}
    
    
    public OrderQueryRespEntity querySign(String userId,String cardNo){
    	String url = "https://queryapi.lianlianpay.com/bankcardbindlist.htm";
    	Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("version", "1.0");//
		createMap.put("user_id", userId);//
		createMap.put("oid_partner", storeId);//交易结算商户编号*
		createMap.put("sign_type", "RSA");//签名方式*
		createMap.put("pay_type", "D");//签名方式*
		
		createMap.put("card_no", cardNo);//签约银行卡号
		createMap.put("offset", "0");//签名方式*
		createMap.put("sign",LianPayUtils.genSign(createMap,RSAKey,null));//签名*
		String jsonString = JsonUtils.toJsonString(createMap);
		String httpOrgCreateTestRtn = null;
		try {
			httpOrgCreateTestRtn = HttpClientUtil.postParametersJson(url, jsonString);
			log.info("lianlian's queryOrder result:" + httpOrgCreateTestRtn);
			OrderQueryRespEntity bean = JSON.parseObject(httpOrgCreateTestRtn, OrderQueryRespEntity.class);
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("lianlian's queryOrder is error.",e);
			return null;
		}
    }
}
