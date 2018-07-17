package org.gz.risk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.risk.constant.ViolationType;
import org.gz.risk.dao.ViolationDao;
import org.gz.risk.entity.Violation;
import org.gz.risk.intf.BaiRongAtomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bfd.facade.MerchantServer;

import net.sf.json.JSONObject;

/**
 * @Description:百荣接口数据爬取实现类
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月17日 	Created
 */
@Service
public class BaiRongAtomServiceImpl implements BaiRongAtomService{

    private static final Logger logger = LoggerFactory.getLogger(BaiRongAtomServiceImpl.class);

    private static final String ACCOUNT = "shebaodai";//百荣正式账号

    private static final String PASSWORD = "shebaodai";//百荣正式密码

    private static final String API_NAME = "BankServer3Api";//打包调用APIName

    private static final String SEPARATE_API = "HainaApi";//单独调用

    private static MerchantServer ms;//百荣请求对象

    @Resource
    private ViolationDao violationDao;

    private MerchantServer getMerchant() {
        if (ms == null) {
            ms = new MerchantServer();
        }
        return ms;
    }

    private JSONObject jsonToMap(String json) {
        System.out.println("****************"+json);
        JSONObject map = JSONObject.fromObject(json);
        int code = Integer.parseInt(map.getString("code"));
        if (code == 0 || code == 600000) {
            return JSONObject.fromObject(json).getJSONObject("product");
        }
        return null;
    }

    /**
     * 身份二要数
     *
     * @param userId
     * @param idCard
     * @param name
     * @return
     * @throws Exception
     */
    public List<Violation> idCard(Long userId, String idCard, String name) throws Exception {
        String login_result = getMerchant().login(ACCOUNT, PASSWORD);
        JSONObject json = JSONObject.fromObject(login_result);
        String tokenid = json.getString("tokenid");
        JSONObject jso = new JSONObject();
        JSONObject reqData = new JSONObject();
        jso.put("apiName", SEPARATE_API);
        jso.put("tokenid", tokenid);
        reqData.put("id", idCard.toUpperCase());
        reqData.put("name", name);
        reqData.put("meal", ViolationType.HONOR_ID_CARD.getMeal());
        jso.put("reqData", reqData);
        String portrait_result1 = getMerchant().getApiData(jso.toString());
        Map IdTwo_zMap = jsonToMap(portrait_result1);
        List<Violation> list = new ArrayList<>();
        if (IdTwo_zMap != null) {
            list.addAll(mapToList(IdTwo_zMap, ViolationType.HONOR_ID_CARD.getCode(), ViolationType.HONOR_ID_CARD.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_ID_CARD.getMeal(), ViolationType.HONOR_ID_CARD.getCode(), userId));
        }
        violationDao.addBatch(list);
        return list;
    }

   /* public static void main(String[] args){
        try {
            //List<ViolationEntity> list= new BaiRongService().idCard(11L,"422202199211214215","郝耀峰");

            //List<ViolationEntity> list= new BaiRongService().cellPhone(11L,"422202199211214215","15623663851","郝耀峰");

            List<ViolationEntity> list= new BaiRongService().bankFour(11L,"422202199211214215","15623663850","郝耀峰","6217002870004834154");

            System.out.println(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 添加百荣手机三要素
     *
     * @param userId
     * @param idCard
     * @param cell
     * @param name
     * @throws Exception
     */
    public List<Violation> cellPhone(Long userId, String idCard, String cell, String name) throws Exception {
        String login_result = getMerchant().login(ACCOUNT, PASSWORD);
        JSONObject json = JSONObject.fromObject(login_result);
        String tokenid = json.getString("tokenid");
        JSONObject jso = new JSONObject();
        JSONObject reqData = new JSONObject();
        jso.put("apiName", SEPARATE_API);
        jso.put("tokenid", tokenid);
        reqData.put("id", idCard.toUpperCase());
        reqData.put("cell", cell);
        reqData.put("name", name);
        reqData.put("meal", ViolationType.HONOR_CELLPHONE.getMeal());
        jso.put("reqData", reqData);
        List<Violation> list = new ArrayList<>();
        String portrait_result = getMerchant().getApiData(jso.toString());
        Map telCheckMap = jsonToMap(portrait_result);
        if (telCheckMap != null) {
            list.addAll(mapToList(telCheckMap, ViolationType.HONOR_CELLPHONE.getCode(), ViolationType.HONOR_CELLPHONE.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_CELLPHONE.getMeal(), ViolationType.HONOR_CELLPHONE.getCode(), userId));
        }
        violationDao.addBatch(list);
        return list;
    }

    /**
     * 百荣银行卡四要数
     *
     * @param userId
     * @param idCard
     * @param cell
     * @param name
     * @param bankId
     * @return
     * @throws Exception
     */
    public List<Violation> bankFour(Long userId, String idCard, String cell, String name, String bankId) throws Exception {
        String login_result = getMerchant().login(ACCOUNT, PASSWORD);
        JSONObject json = JSONObject.fromObject(login_result);
        String tokenid = json.getString("tokenid");
        JSONObject jso = new JSONObject();
        JSONObject reqData = new JSONObject();
        jso.put("apiName", SEPARATE_API);
        jso.put("tokenid", tokenid);
        reqData.put("id", idCard.toUpperCase());
        reqData.put("cell", cell);
        reqData.put("name", name);
        reqData.put("bank_id", bankId);
        reqData.put("meal", ViolationType.HONOR_BANK_FOUR.getMeal());
        jso.put("reqData", reqData);
        List<Violation> list = new ArrayList<>();
        String portrait_result2 = getMerchant().getApiData(jso.toString());
        Map BankFourMap = jsonToMap(portrait_result2);
        if (BankFourMap != null) {
            list.addAll(mapToList(BankFourMap, ViolationType.HONOR_BANK_FOUR.getCode(), ViolationType.HONOR_BANK_FOUR.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_BANK_FOUR.getMeal(), ViolationType.HONOR_BANK_FOUR.getCode(), userId));
        }
        violationDao.addBatch(list);
        return list;
    }

    public static void main(String[] a) throws Exception {
        new BaiRongAtomServiceImpl().bankFour(123L, "430621199805219073", "18692186540", "曹邦", "6212261001057474014");
    }

    /**
     * 添加百荣特殊名单
     *
     * @param userId
     * @param id
     * @param cell
     * @param name
     */
    public List<Violation> addViolation(Long userId, String id, String cell, String name, String kinshipTel, String spouseTel, String emergencyContactPhone) {
        List<String> cellList = new ArrayList<>();
        cellList.add(cell);
        List<String> cells = new ArrayList();
        cells.add(kinshipTel);
//        cells.add(spouseTel);
//        cells.add(emergencyContactPhone);
        String result;
        try {
            String login_result = getMerchant().login(ACCOUNT, PASSWORD);
            JSONObject json = JSONObject.fromObject(login_result);
            String tokenId = json.getString("tokenid");
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", API_NAME);
            reqData.put("meal", ViolationType.HONOR_BLACKLlST.getMeal());
            jso.put("tokenid", tokenId);
            reqData.put("id", id.toUpperCase());
            reqData.put("cell", cellList);
            reqData.put("linkman_cell", cells);
            reqData.put("name", name);
            jso.put("reqData", reqData);
            result = getMerchant().getApiData(jso.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
        Map<String, Object> map = JSONObject.fromObject(result);
        List<Violation> list = new ArrayList<>();
        if ((map.get("code").equals("00") && map.get("flag_specialList_c").equals("1")) || (map.get("code").equals("100002"))) {
            list.addAll(mapToList(map, ViolationType.HONOR_BLACKLlST.getCode(), ViolationType.HONOR_BLACKLlST.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_BLACKLlST.getMeal(), ViolationType.AFU_QUERY_LOAN.getCode(), userId));
        }
        return list;
    }

    public List<Violation> addLoanEquipment(Long userId, String id, String cell, String name, String af_swift_number) {
        List<String> cellList = new ArrayList<>();
        cellList.add(cell);
        String result;
        try {
            String login_result = getMerchant().login(ACCOUNT, PASSWORD);
            JSONObject json = JSONObject.fromObject(login_result);
            String tokenId = json.getString("tokenid");
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", API_NAME);
            jso.put("tokenid", tokenId);
            reqData.put("meal", ViolationType.HONOR_LOAN_EQUIPMENT.getMeal());
            reqData.put("id", id.toUpperCase());
            reqData.put("cell", cellList);
            reqData.put("name", name);
            reqData.put("af_swift_number", af_swift_number);
            reqData.put("event", "antifraud_lend");
            jso.put("reqData", reqData);
            result = getMerchant().getApiData(jso.toString());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        Map<String, Object> map = JSONObject.fromObject(result);
        List<Violation> list = new ArrayList<>();
        if ((map.get("code").equals("00") && map.get("flag_specialList_c").equals("1")) || (map.get("code").equals("100002"))) {
            list.addAll(mapToList(map, ViolationType.HONOR_LOAN_EQUIPMENT.getCode(), ViolationType.HONOR_LOAN_EQUIPMENT.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_LOAN_EQUIPMENT.getMeal(), ViolationType.HONOR_LOAN_EQUIPMENT.getCode(), userId));
        }
        return list;
    }

    public List<Violation> addRegisterEquipment(Long userId, String cell, String af_swift_number) {
        List<String> cellList = new ArrayList<>();
        cellList.add(cell);
        String result;
        try {
            String login_result = getMerchant().login(ACCOUNT, PASSWORD);
            JSONObject json = JSONObject.fromObject(login_result);
            String tokenId = json.getString("tokenid");
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", API_NAME);
            jso.put("tokenid", tokenId);
            reqData.put("meal", ViolationType.HONOR_REGISTER_EQUIPMENT.getMeal());
            reqData.put("af_swift_number", af_swift_number);
            reqData.put("cell", cellList);
            reqData.put("event", "antifraud_register");
            jso.put("reqData", reqData);
            result = getMerchant().getApiData(jso.toString());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        Map<String, Object> map = JSONObject.fromObject(result);
        List<Violation> list = new ArrayList<>();
        if ((map.get("code").equals("00") && map.get("flag_specialList_c").equals("1")) || (map.get("code").equals("100002"))) {
            list.addAll(mapToList(map, ViolationType.HONOR_REGISTER_EQUIPMENT.getCode(), ViolationType.HONOR_REGISTER_EQUIPMENT.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_REGISTER_EQUIPMENT.getMeal(), ViolationType.HONOR_REGISTER_EQUIPMENT.getCode(), userId));
        }
        return list;
    }

    public List<Violation> addSignEquipment(Long userId, String af_swift_number) {
        String result;
        try {
            String login_result = getMerchant().login(ACCOUNT, PASSWORD);
            JSONObject json = JSONObject.fromObject(login_result);
            String tokenId = json.getString("tokenid");
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", API_NAME);
            jso.put("tokenid", tokenId);
            reqData.put("meal", ViolationType.HONOR_SIGN_EQUIPMENT.getMeal());
            reqData.put("af_swift_number", af_swift_number);
            reqData.put("event", "antifraud_login");
            jso.put("reqData", reqData);
            result = getMerchant().getApiData(jso.toString());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        Map<String, Object> map = JSONObject.fromObject(result);
        List<Violation> list = new ArrayList<>();
        if ((map.get("code").equals("00") && map.get("flag_specialList_c").equals("1")) || (map.get("code").equals("100002"))) {
            list.addAll(mapToList(map, ViolationType.HONOR_SIGN_EQUIPMENT.getCode(), ViolationType.HONOR_SIGN_EQUIPMENT.getMeal(), userId));
        } else {
            list.add(toViolation(ViolationType.HONOR_SIGN_EQUIPMENT.getMeal(), ViolationType.HONOR_SIGN_EQUIPMENT.getCode(), userId));
        }
        return list;
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
    
}
