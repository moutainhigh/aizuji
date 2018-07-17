package org.gz.risk.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gz.risk.constant.ViolationType;
import org.gz.risk.entity.Violation;
import org.gz.risk.intf.AFuAtomService;
import org.gz.risk.util.RC4_128_V2;
import org.gz.risk.util.RSA_1024_V2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @Description:阿福接口数据爬取实现类
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月17日 	Created
 */
@Service
public class AFuAtomServiceImpl implements AFuAtomService{

    private static final Logger logger = LoggerFactory.getLogger(AFuAtomServiceImpl.class);

    private static final String UID = "guozhikj";//账号

    private static String encryptedUserID = null;

    private static final String queryBlackList = "http://www.zhichengcredit.com/CreditPortal/api/queryBlackList/V2";

    private static final String queryLoan = "http://www.zhichengcredit.com/CreditPortal/api/queryLoan/V2";//正式环境阿福数据

    private static final String queryCreditScore = "http://www.zhichengcredit.com/CreditPortal/api/queryCreditScore/V2";//正式环境阿福数据

    private static final String getQueriedHistory = "http://www.zhichengcredit.com/CreditPortal/api/getQueriedHistory/V2";//正式环境阿福数据

    private static final String RC4KEY = "59103fd5a6cdf3a4";//密码

    /*static {
        //获取公钥，本示例为Java序列化的致诚公钥
        URL u1 = AFuAtomServiceImpl.class.getClassLoader().getResource("publicKey/ZC_PublicKey_V2.crt");
        try {
            RSAPublicKey rsaPublicKey = RSA_1024_V2.gainRSAPublicKeyFromCrtFile(u1.openStream());   //用公钥对uid进行RSA加密
            //加密uid
            encryptedUserID = RSA_1024_V2.encodeByPublicKey(rsaPublicKey, UID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public List<Violation> addQueryLoan(Long userId, String name, String idNo) throws IOException {
        MultiValueMap<String, String> paraMap = getMultiValueMap(name, idNo, null);
        RestTemplate restTemplate = new RestTemplate();
        String message = restTemplate.postForObject(queryLoan, paraMap, String.class);
        JSONObject str = JSONObject.fromObject(message);
        if (str.getString("errorcode").equals("0000")) {
            String dataString = str.getString("data");
            String decryptResult = RC4_128_V2.decode(dataString, RC4KEY);
            decryptResult = URLDecoder.decode(decryptResult, "utf-8");
            Map object = JSONObject.fromObject(decryptResult);
            JSONObject overdue = (JSONObject) object.get("overdue");
            Map<String, Object> map = JSONObject.fromObject(overdue);
            JSONArray loanRecords = (JSONArray) object.get("loanRecords");
            List<Violation> list = new ArrayList<>();
            if (loanRecords.size() != 0) {
                list.addAll(mapToList(map, ViolationType.AFU_QUERY_LOAN.getCode(), ViolationType.AFU_QUERY_LOAN.getMeal(), userId));
                for (int i = 0; i < loanRecords.size(); i++) {
                    Map<String, Object> map1 = loanRecords.getJSONObject(i);
                    list.addAll(mapToList(map1, ViolationType.AFU_QUERY_LOAN.getCode(), ViolationType.AFU_QUERY_LOAN.getMeal(), userId));
                }
            } else {
                list.add(toViolation(ViolationType.AFU_QUERY_LOAN.getMeal(), ViolationType.AFU_QUERY_LOAN.getCode(), userId));
            }
            return list;
        } else {
            logger.warn("Violation return error: " + str);
        }
        return Collections.emptyList();
    }

    public List<Violation> addQueryBlackList(Long userId, String name, String idNo, String mobile) throws IOException {
        MultiValueMap<String, String> paraMap = getMultiValueMap(name, idNo, mobile);
        RestTemplate restTemplate = new RestTemplate();
        String message = restTemplate.postForObject(queryBlackList, paraMap, String.class);
        JSONObject str = JSONObject.fromObject(message);
        if (str.getString("errorcode").equals("0000")) {
            String dataString = str.getString("data");
            String decryptResult = RC4_128_V2.decode(dataString, RC4KEY);
            decryptResult = URLDecoder.decode(decryptResult, "utf-8");
            JSONObject object = JSONObject.fromObject(decryptResult);
            JSONArray array = object.getJSONArray("riskItems");
            List<Violation> list = new ArrayList<>();
            if (array.size() != 0) {
                for (int i = 0; i < array.size(); i++) {
                    Map<String, Object> map = array.getJSONObject(i);
                    list.addAll(mapToList(map, ViolationType.AFU_BLACKLIST.getCode(), ViolationType.AFU_BLACKLIST.getMeal(), userId));
                }
            } else {
                list.add(toViolation(ViolationType.AFU_BLACKLIST.getMeal(), ViolationType.AFU_BLACKLIST.getCode(), userId));
            }
            return list;
        } else {
            logger.warn("Violation return error: " + str);
        }
        return Collections.emptyList();
    }

    public List<Violation> addQueryCreditScore(String name, String idNo, Long userId) throws IOException {
        MultiValueMap<String, String> paraMap = getMultiValueMap(name, idNo, null);
        RestTemplate restTemplate = new RestTemplate();
        String message = restTemplate.postForObject(queryCreditScore, paraMap, String.class);
        JSONObject str = JSONObject.fromObject(message);
        List<Violation> list = new ArrayList();
        if (str.getString("errorcode").equals("0000")) {
            String dataString = str.getString("data");
            String decryptResult = URLDecoder.decode(RC4_128_V2.decode(dataString, RC4KEY), "utf-8");
            Map<String, Object> map = JSONObject.fromObject(decryptResult);
            list.addAll(mapToList(map, ViolationType.AFU_QUERY_CREDIT_SCORE.getCode(), ViolationType.AFU_QUERY_CREDIT_SCORE.getMeal(), userId));
        } else {
            if (str.getString("errorcode").equals("0001")) {
                list.add(toViolation(ViolationType.AFU_QUERY_CREDIT_SCORE.getMeal(), ViolationType.AFU_QUERY_CREDIT_SCORE.getCode(), userId));
            } else {
                logger.warn("Violation return error: " + str);
            }
        }
        return list;
    }


    public List<Violation> addGetQueriedHistory(String name, String idNo, Long userId) throws IOException {
        MultiValueMap<String, String> paraMap = getMultiValueMap(name, idNo, null);
        RestTemplate restTemplate = new RestTemplate();
        String message = restTemplate.postForObject(getQueriedHistory, paraMap, String.class);
        JSONObject str = JSONObject.fromObject(message);
        if (str.getString("errorcode").equals("0000")) {
            String dataString = str.getString("data");
            String decryptResult = RC4_128_V2.decode(dataString, RC4KEY);
            decryptResult = URLDecoder.decode(decryptResult, "utf-8");
            JSONArray array = JSONObject.fromObject(decryptResult).getJSONArray("queryHistory");
            List<Violation> list = new ArrayList<>();
            if (array.size() != 0) {
                for (int i = 0; i < array.size(); i++) {
                    Map<String, Object> map = array.getJSONObject(i);
                    list.addAll(mapToList(map, ViolationType.AFU_QUERIEDHISTORY.getCode(), ViolationType.AFU_QUERIEDHISTORY.getMeal(), userId));
                }
            } else {
                list.add(toViolation(ViolationType.AFU_QUERIEDHISTORY.getMeal(), ViolationType.AFU_QUERIEDHISTORY.getCode(), userId));
            }
            return list;
        } else {
            logger.warn("Violation return error: " + str);
        }
        return Collections.emptyList();
    }

    private MultiValueMap getMultiValueMap(String name, String idNo, String mobile) throws IOException {
        MultiValueMap<String, String> paraMap = new LinkedMultiValueMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("idType", "101");//101代表身份证
        jsonObject.put("idNo", idNo);
        jsonObject.put("queryReason", "10");//10代表贷款前审核查询
        if (mobile != null) {
            jsonObject.put("mobile", mobile);
        }
        //获取公钥，本示例为Java序列化的致诚公钥
        if(encryptedUserID == null){
            //获取公钥，本示例为Java序列化的致诚公钥
            RSAPublicKey rsaPublicKey =RSA_1024_V2.gainRSAPublicKeyFromCrtFile(getResource("/conf/ZC_PublicKey_V2.crt"));
            //用公钥对uid进行RSA加密
            encryptedUserID = RSA_1024_V2.encodeByPublicKey(rsaPublicKey, UID);
        }
        paraMap.add("userid", encryptedUserID);
        paraMap.add("params", RC4_128_V2.encode(URLEncoder.encode(jsonObject.toString(), "utf-8"), RC4KEY));
        return paraMap;
    }
    
    
    private Violation toViolation(String Meal, int code, Long userId) {
        Violation Violation = new Violation();
        Violation.setMeal(Meal);
        Violation.setViolationKey("NOT_RESULTS");
        Violation.setViolationValue("4101");
        Violation.setUserId(userId);
        Violation.setBelong(code);
        Violation.setInputTime(new Date());
        return Violation;
    }
    
    
    private List<Violation> mapToList(Map<String, Object> map, int code, String meal, Long userId) {
        List<Violation> list = new ArrayList<>();
        Date nowDate = new Date();
        for (String key : map.keySet()) {
            Violation entity = new Violation();
            entity.setUserId(userId);
            entity.setMeal(meal);
            entity.setViolationKey(key);
            entity.setViolationValue(map.get(key).toString());
            entity.setBelong(code);
            entity.setInputTime(nowDate);
            list.add(entity);
        }
        return list;
    }
    
    /**
     * @Description: 读取加密文件
     * @param fileName
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年8月1日
     */
    public InputStream getResource(String fileName){
        InputStream is = this.getClass().getResourceAsStream(fileName);
        return is;
      }

}
