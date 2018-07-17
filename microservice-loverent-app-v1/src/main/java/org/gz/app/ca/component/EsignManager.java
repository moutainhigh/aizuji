package org.gz.app.ca.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.ca.config.EsignConfigure;
import org.gz.app.ca.dto.ResultDto;
import org.gz.app.ca.entity.CertificateEntity;
import org.gz.app.ca.entity.PreserveEntity;
import org.gz.app.ca.entity.PreserveResultEntity;
import org.gz.app.ca.entity.TypeEntity;
import org.gz.app.ca.utils.AlgorithmHelper;
import org.gz.app.ca.utils.HmacUtils;
import org.gz.app.ca.utils.HttpClientUtil;
import org.gz.app.feign.UploadAliService;
import org.gz.cache.service.sign.SignDataCacheService;
import org.gz.common.entity.UploadBody;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timevale.esign.sdk.tech.bean.OrganizeBean;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.PosBean;
import com.timevale.esign.sdk.tech.bean.SignPDFStreamBean;
import com.timevale.esign.sdk.tech.bean.UpdateOrganizeBean;
import com.timevale.esign.sdk.tech.bean.UpdatePersonBean;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.bean.result.Result;
import com.timevale.esign.sdk.tech.bean.seal.OrganizeTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.PersonTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.SealColor;
import com.timevale.esign.sdk.tech.impl.constants.DeleteParamType;
import com.timevale.esign.sdk.tech.impl.constants.OrganRegType;
import com.timevale.esign.sdk.tech.impl.constants.OrganType;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import com.timevale.esign.sdk.tech.service.AccountService;
import com.timevale.esign.sdk.tech.service.MobileService;
import com.timevale.esign.sdk.tech.service.SealService;
import com.timevale.esign.sdk.tech.service.UserSignService;
import com.timevale.esign.sdk.tech.service.factory.AccountServiceFactory;
import com.timevale.esign.sdk.tech.service.factory.MobileServiceFactory;
import com.timevale.esign.sdk.tech.service.factory.SealServiceFactory;
import com.timevale.esign.sdk.tech.service.factory.UserSignServiceFactory;

/**
 * esign util
 * 
 * @author yangdx
 *
 */
@Service
@Slf4j
public class EsignManager {

	/**企业名称*/
	private static final String ORGANIZE_NAME = "深圳市国智互联网科技有限公司";
	
	/**企业编码*/
	private static final String ORGANIZE_CODE = "91440300MA5DDX4D0C";
	
	@Autowired
	private EsignConfigure esignConfigure;
	
	
	@Autowired
	private UploadAliService uploadAliService;
	
	@Autowired
	private SignDataCacheService signDataCacheService;
	
	
	/**
     * 账号服务接口
     */
    private AccountService accountService = AccountServiceFactory.instance();
    /**
     * 印章服务接口
     */
    private SealService sealService = SealServiceFactory.instance();
    /**
     * 用户签署服务
     */
    private UserSignService userSignService = UserSignServiceFactory.instance();
    /**
     * 签署身份认证服务
     */
    private MobileService mobileService = MobileServiceFactory.instance();
    
    
    /**
     * @Description: 
     * 			创建e签宝个人账号
     * @param name  
     * 			姓名
     * @param idNo  
     * 			身份证
     * @param mobile 
     * 			手机号
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月13日
     */
    public AddAccountResult addPersonAccount(String name,String idNo,String mobile){
        PersonBean personBean = new PersonBean();
        personBean.setName(name);
        personBean.setIdNo(idNo);
        personBean.setMobile(mobile);
        AddAccountResult accountResult = accountService.addAccount(personBean);
        if(0 == accountResult.getErrCode()){
            log.info("addAccount AccountId:{}",accountResult.getAccountId());
        }
        return accountResult;
    }
    
    /**
     * @Description: 
     * 				更新e签宝个人账户
     * @param accountId 
     * 				e签宝用户账户id  
     * @param person    
     * 				更新对象
     * @param deleteParamType  
     * 				需要重置的属性集合（可空）
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月13日
     */
    public Result updatePersonAccount(String accountId, UpdatePersonBean person, List<DeleteParamType> deleteParamType){
        Result result = accountService.updateAccount(accountId, person, deleteParamType);
        Gson gson = new GsonBuilder().create();
        log.info("updateAccount result:{}",gson.toJson(result));
        if(0 == result.getErrCode()){
            log.info("更新账号信息成功！updateAccount");
        }
        return result;
    }
    
    /**
     * @Description: 
     * 			创建e签宝企业账号
     * @param name  
     * 			组织机构名称
     * @param organCode 
     * 			组织机构代码
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    public AddAccountResult addOrganizeAccount(String name,String organCode){
        OrganizeBean organizeBean = new OrganizeBean();
        // 机构名称
        organizeBean.setName(name);
        organizeBean.setOrganCode(organCode);
        organizeBean.setOrganType(OrganType.Nomal.type());
        organizeBean.setRegType(OrganRegType.MERGE);
        AddAccountResult accountResult = accountService.addAccount(organizeBean);
        if(0 == accountResult.getErrCode()){
            log.info("addOrganize AccountId:{}",accountResult.getAccountId());
        }
        return accountResult;
    }
    
    /**
     * @Description: 
     * 			更新e签宝企业账号
     * @param accountId  
     * 			待更新账号的标识
     * @param organize 
     * 			更新的企业信息详情
     * @param deleteParamType
     * 			待置空的属性集合(可空)
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    public Result updateOrganizeAccount(String accountId, UpdateOrganizeBean organize, List<DeleteParamType> deleteParamType){
    	Result result = accountService.updateAccount(accountId, organize, deleteParamType);
        Gson gson = new GsonBuilder().create();
        log.info("updateOrganizeAccount result:{}",gson.toJson(result));
        if(0 == result.getErrCode()){
            log.info("更新账号信息成功！updateAccount");
        }
        return result;
    }
    
    
    /**
     * @Description: 
     * 			创建个人模板印章
     * @param accountId 
     * 			e签宝用户账号id
     * @param templateType  
     * 			模板类型
     * @param color
     * 			生成印章的颜色
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    public AddSealResult addPersonTemplateSeal(String accountId, PersonTemplateType templateType, SealColor color){
        AddSealResult result = sealService.addTemplateSeal(accountId, templateType, color);
        if(0 == result.getErrCode()){
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>创建个人模板印章成功!");
            log.info("addTemplateSeal SealData:{}",result.getSealData());
        }
        return result;
    }
    
    /**
     * @Description: 
     * 			创建企业模板印章
     * @param accountId 
     * 			e签宝企业账号id
     * @param templateType  
     * 			模板类型
     * @param color
     * 			生成印章的颜色
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    public AddSealResult addOrganizeTemplateSeal(String accountId, OrganizeTemplateType templateType, SealColor color){
        AddSealResult result = sealService.addTemplateSeal(accountId, templateType, color, null, null);
        if(0 == result.getErrCode()){
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>创建企业模板印章成功!");
            log.info("addTemplateSeal SealData:{}",result.getSealData());
        }
        return result;
    }
    
    
    /**
     * @Description: 
     * 			给账号绑定的手机号发送签署验证码
     * @param userId 
     * 			账号id
     * @param realname
     * 			真是姓名
     * @param idNo
     * 			身份证
     * @param mobile
     * 			手机号
     * @return  
     * 			短信验证码
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月13日
     */
    public ResponseResult<String> sendMessageOfSign(Long userId, String realname, String idNo, String mobile){
    	ResponseResult<String> result = new ResponseResult<>();
    	String userAccountId = signDataCacheService.getPersonAccountByUserId(userId);
    	if (StringUtils.isEmpty(userAccountId)) {
    		AddAccountResult accountResult = this.addPersonAccount(realname, idNo, mobile);
    		if (accountResult.getErrCode() != 0) {
    			log.error("add user account failed, realname: {}, idNo: {}, mobile: {}, accountResult: {}", realname, idNo, 
    					mobile, JSONObject.toJSONString(accountResult));
    			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SIGN_ADD_USER_ACC_FAILED);
    			return result;
    		}
    		userAccountId = accountResult.getAccountId();
    		signDataCacheService.setPersonAccountByUserId(userId, userAccountId);
    	}
        Result sendResult = mobileService.sendSignMobileCode(userAccountId);
        if (0 != sendResult.getErrCode()) {
        	ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SMS_SEND_FAILED);
        }
        return result;
    }
    
    /**
     * 签约
     * @param userId
     * 			用户ID
     * @param realname
     * 			用户姓名
     * @param idNo
     * 			用户身份证
     * @param mobile
     * 			用户手机号
     * @param contactPDFUrl
     * 			合同模板路径
     * @param smsCode
     * 			签约短信验证码
     * @return
     */
    public ResponseResult<Map<String, String>> sign(Long userId, String realname, String idNo, 
    		String mobile, String contactPDFUrl, String smsCode) {
    	ResponseResult<Map<String, String>> result = new ResponseResult<>();
    	
    	//检查账户及签约印章数据
    	result = checkAccountAndSealData(userId, realname, mobile, idNo);
    	if (result.getErrCode() != 0) {
    		return result;
    	}
    	
    	//发起签约
    	Map<String, String> dataMap = result.getData();
    	Map<String, String> signResult = this.sign(
    		dataMap,
    		realname,
    		idNo,
    		contactPDFUrl, 
    		smsCode, 
    		userId
    	);
    	result.setData(signResult);
    	return result;
    }
    
    /**
     * 检查账户及签约数据
     * @param userId
     * 			用户ID
     * @param realname
     * 			用户姓名
     * @param mobile
     * 			手机号
     * @param idNo
     * 			身份证号
     * @return
     */
    private ResponseResult<Map<String, String>> checkAccountAndSealData(Long userId, String realname, String mobile, String idNo) {
    	ResponseResult<Map<String, String>> result = new ResponseResult<>();
    	Map<String, String> dataMap = new HashMap<>();
    	
    	//获取企业账户ID
    	String organizeAccountId = signDataCacheService.getOrganizeAccount();
    	if (StringUtils.isEmpty(organizeAccountId)) {
    		AddAccountResult accountResult = this.addOrganizeAccount(ORGANIZE_NAME, ORGANIZE_CODE);
    		if (accountResult.getErrCode() != 0) {
    			log.error("add organize account failed, accountResult: {}", JSONObject.toJSONString(accountResult));
    			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SIGN_ADD_ORG_ACC_FAILED);
    			return result;
    		}
    		organizeAccountId = accountResult.getAccountId();
    		signDataCacheService.setOrganizeAccount(organizeAccountId);
    	}
    	dataMap.put("organizeAccountId", organizeAccountId);
    	
    	//获取企业印章数据
    	String organizeSealData = signDataCacheService.getOrganizeTemplateSeal();
    	if (StringUtils.isEmpty(organizeSealData)) {
    		AddSealResult sealResult = this.addOrganizeTemplateSeal(organizeAccountId, OrganizeTemplateType.STAR, SealColor.RED);
    		if(result.getErrCode() != 0) {
    			log.error("add organize sealdata failed, accountResult: {}", JSONObject.toJSONString(sealResult));
    			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SIGN_ADD_ORG_SEAL_FAILED);
    			return result;
    		}
    		organizeSealData = sealResult.getSealData();
    		signDataCacheService.setOrganizeTemplateSeal(organizeSealData);
    	}
    	dataMap.put("organizeSealData", organizeSealData);
    	
    	//获取用户账户ID
    	String userAccountId = signDataCacheService.getPersonAccountByUserId(userId);
    	if (StringUtils.isEmpty(userAccountId)) {
    		AddAccountResult accountResult = this.addPersonAccount(realname, idNo, mobile);
    		if (accountResult.getErrCode() != 0) {
    			log.error("add user account failed, realname: {}, idNo: {}, mobile: {}, accountResult: {}", realname, idNo, 
    					mobile, JSONObject.toJSONString(accountResult));
    			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SIGN_ADD_USER_ACC_FAILED);
    			return result;
    		}
    		userAccountId = accountResult.getAccountId();
    		signDataCacheService.setPersonAccountByUserId(userId, userAccountId);
    	}
    	dataMap.put("userAccountId", userAccountId);
    	
    	//获取用户印章数据
    	String userSealData = signDataCacheService.getPersonSealDataByUserId(userId);
    	if (StringUtils.isEmpty(userSealData)) {
    		AddSealResult sealResult = this.addPersonTemplateSeal(userAccountId, PersonTemplateType.RECTANGLE, SealColor.RED);
    		if(result.getErrCode() != 0) {
    			log.error("add person sealdata failed, accountResult: {}", JSONObject.toJSONString(sealResult));
    			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SIGN_ADD_USER_SEAL_FAILED);
    			return result;
    		}
    		userSealData = sealResult.getSealData();
    		signDataCacheService.setPersonSealDataByUserId(userId, userSealData);
    	}
    	dataMap.put("userSealData", userSealData);
    	result.setData(dataMap);
    	return result;
	}

	/**
     * @Description: 
     * 				签署合同
     * @param dataMap 
     * 				账户及印章数据
     * @param realname  
     * 				用户姓名
     * @param idNo
     * 				身份证
     * @param srcPdfFile  
     * 				签约模板pdf路径
     * @param smsCode 
     * 				短信验证码
     * @param userId 
     * 				用户app注册ID
     * @return  
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    public Map<String, String> sign(Map<String, String> dataMap, String realname, 
    		String idNo, String srcPdfFile, String smsCode, Long userId){
    	Map<String, String> result = new HashMap<>();
        //Stram bean
    	
    	//用户账户ID
    	String userAccountId = dataMap.get("userAccountId"); 
    	//用户印章base64编码
    	String userSealData = dataMap.get("userSealData");
    	//企业账户id
    	String organizeAccountId = dataMap.get("organizeAccountId"); 
    	//企业印章base64编码
    	String organizeSealData = dataMap.get("organizeSealData");
    	
        SignPDFStreamBean streamBean = new SignPDFStreamBean();
        
        ResponseResult<byte[]> d = uploadAliService.getByteArray(srcPdfFile);
    	if(0 != d.getErrCode()){ //上传失败直接提示
    		result.put("status", "-1");
        	result.put("message", "获取签约文件失败");
    		return result;
		}else{
			 streamBean.setStream(d.getData());
		}

        //签约模板的文件名
        String rawPdfName = srcPdfFile.substring(srcPdfFile.lastIndexOf("/") + 1);
        
        // 签章位置
        PosBean posBean = new PosBean();
        posBean.setPosType(1);
        posBean.setKey("签字");
        posBean.setPosPage("4");
        
        // 单页签章
        SignType signType = SignType.Key;
        
        try {
        	FileDigestSignResult userSignResult = userSignService.localSafeSignPDF(userAccountId, userSealData, 
        			streamBean, posBean, signType, smsCode);
            if(0 == userSignResult.getErrCode()){
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>个人签署印章成功!");
                log.info("sign 签署记录id:{}",userSignResult.getSignServiceId());
                
                //企业签署
                FileDigestSignResult organizeSignResult = organizeSign(organizeAccountId, organizeSealData, userSignResult);
                if(0 == organizeSignResult.getErrCode()){
                	String signServiceId = organizeSignResult.getSignServiceId();
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>企业签署印章成功!");
                    log.info("sign 签署记录id:{}", signServiceId);
                    
                    byte[] signStream = organizeSignResult.getStream();
                    
                    //签约成功
                    result.put("status", "30");
                    result.put("signServiceId", signServiceId);
                    
					//缓存签约记录
					signDataCacheService.cacheSignRecord(userId, signServiceId);
					//上传文件到oss
					String signedPdfPath = "pdf/agreement/sign/" + userId + "/" + rawPdfName;
					UploadBody uploadBody = new UploadBody();
					uploadBody.setFile(signStream);
					uploadBody.setFileName(signedPdfPath);
					ResponseResult<String> uploadResult = uploadAliService.uploadToOSSFileInputStrem(uploadBody);
					if (uploadResult.getErrCode() == 0) {
						//上传oss成功
						result.put("status", "40");
						result.put("signedPdfPath", uploadAliService.getAccessUrlPrefix() + signedPdfPath);	
					} else {
						log.error("sign success, and upload signed pdf to oss failed, "
								+ "idNo: {}, signServiceId: {}", idNo, signServiceId);
						result.put("status", "-1");
	                	result.put("message", "签约失败");
	                	return result;
					}
					
					try {
						//获取存证路径
						PreserveEntity preserveEntity = new PreserveEntity();
						LocalDate storageLife = LocalDate.of(2027, 12, 31);
						preserveEntity.setStorageLife(storageLife);
						preserveEntity.setContentLength(String.valueOf(signStream.length));
						String contentBase64Md5 = AlgorithmHelper.getStringContentMD5(signStream);
						preserveEntity.setContentBase64Md5(contentBase64Md5);
						preserveEntity.setContentDescription(rawPdfName);
						TypeEntity typeEntity = new TypeEntity();
						typeEntity.setType("0");
						typeEntity.setValue(signServiceId);
						List<TypeEntity> eSignIds = new ArrayList<>();
						eSignIds.add(typeEntity);
						PreserveResultEntity preserveResult = getPreserveUrl("gz", preserveEntity, eSignIds, null);
						log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>获取存证地址URL成功, preserveResult: {}",
								JSONObject.toJSONString(preserveResult));
						String preserveUrl = preserveResult.getUrl();
						String evid = preserveResult.getEvid();
						//上传保全文件
						boolean uploadFlag = uploadPreserveFile(preserveUrl,
								signStream);
						if (uploadFlag) {
							CertificateEntity org = new CertificateEntity();
							org.setNumber(ORGANIZE_CODE);
							org.setName(ORGANIZE_NAME);
							org.setType("CODE_USC");
							List<CertificateEntity> list = new ArrayList<>();
							list.add(org);

							CertificateEntity person = new CertificateEntity();
							person.setName(realname);
							person.setNumber(idNo);
							person.setType("ID_CARD");
							list.add(person);

							ResultDto dto = relate(evid, list);
							if (dto.getErrCode() == 0) {
								//原文存证成功
								result.put("evid", evid);
								result.put("status", "50");
								//缓存签约记录
								signDataCacheService.removeSignRecord(userId,
										signServiceId);
							}
						}
					} catch (Exception e) {
						log.error("---> sign success, preserve failed, idNo: {}, signServiceId:{}", idNo, signServiceId);
					}
                } else {
                	//企业签约失败
                	log.error("企业签约失败, userId: {}, rst: {}", userId, JSONObject.toJSONString(organizeSignResult));
                	result.put("status", "-1");
                	result.put("message", "签约失败");
                }
            } else {
            	//个人签约失败
            	log.error("用户签约失败, userId: {}, rst: {}", userId, JSONObject.toJSONString(userSignResult));
            	result.put("status", "-1");
            	result.put("message", "签约失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        	result.put("status", "-1");
        	result.put("message", "签约失败");
        }
        return result;
    }

	/**
     * 上传文件保全
     * 
     * @param preserveUrl2
     * @param str
     * @param signStream 
     */
    private boolean uploadPreserveFile(String preserveUrl, byte[] signStream) {
    	try {
            String contentBase64Md5 = AlgorithmHelper.getStringContentMD5(signStream);
            Map<String,String> headers = new HashMap<String, String>();
            headers.put("Content-MD5",contentBase64Md5);
            headers.put("Content-Type","application/octet-stream");
            boolean flag = HttpClientUtil.doPut(preserveUrl, headers, signStream);
            if(flag){
                log.info("待保全文档上传成功！");  
                return true;
            }
            log.info("uploadFile flag:{}",flag);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    	
	}

	/**
     * @Description: 
     * 				用户签署合同
     * @param accountId 
     * 				账号id
     * @param sealData  
     * 				用户印章base64编码
     * @param srcPdfFile 
     * 				待签署文档路径
     * @param code 
     * 				短信验证码
     * @return  
     * 				true 成功 false 失败
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    @Deprecated
    public FileDigestSignResult userSign(String accountId, String sealData, String srcPdfFile, String code){
        //Stram bean
        SignPDFStreamBean streamBean = new SignPDFStreamBean();
        ResponseResult<byte[]> d = uploadAliService.getByteArray(srcPdfFile);
       	if(0 != d.getErrCode()){ //上传失败直接提示
       		return null;
   		}else{
   			 streamBean.setStream(d.getData());
   		}
        
        // 签章位置
        PosBean posBean = new PosBean();
        posBean.setPosX(400);
        posBean.setPosY(100);
        posBean.setPosPage("1");
        
        // 单页签章
        SignType signType = SignType.Single;
        
        try {
        	FileDigestSignResult fileDigestSignResult = userSignService.localSafeSignPDF(accountId, sealData, streamBean, posBean, signType,code);
            Gson gson = new GsonBuilder().create();
            log.info("fileDigestSignResult:{}",gson.toJson(fileDigestSignResult));
            if(0 == fileDigestSignResult.getErrCode()){
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>个人签署印章成功!");
                log.info("sign 签署记录id:{}",fileDigestSignResult.getSignServiceId());
                return fileDigestSignResult;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * @Description: 
     * 				企业签署合同
     * @param accountId 
     * 				账号id
     * @param sealData  
     * 				企业印章base64编码
     * @param code 
     * 				短信验证码
     * @return  
     * 				true 成功 false 失败
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月12日
     */
    public FileDigestSignResult organizeSign(String accountId, String sealData, FileDigestSignResult userSignResult){
        //Stram bean
        SignPDFStreamBean signPDFStreamBean = new SignPDFStreamBean();
        signPDFStreamBean.setStream(userSignResult.getStream());
        
        // 签章位置
        PosBean arg3 = new PosBean();
        //arg3.setPosX(200);
        //arg3.setPosY(100);
        arg3.setPosPage("4");
        arg3.setPosType(1);
        arg3.setKey("盖章");
        // 单页签章
        SignType signType = SignType.Key;
        
        try {
        	FileDigestSignResult fileDigestSignResult = userSignService.localSignPDF(accountId, sealData, signPDFStreamBean, arg3, signType);
            Gson gson = new GsonBuilder().create();
            log.info("fileDigestSignResult:{}",gson.toJson(fileDigestSignResult));
            if(0 == fileDigestSignResult.getErrCode()){
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>企业签署印章成功!");
                log.info("sign 签署记录id:{}",fileDigestSignResult.getSignServiceId());
                
                byte [] streams = fileDigestSignResult.getStream();
                //上传文件到oss
                UploadBody uploadBody = new UploadBody();
    			uploadBody.setFile(streams);
    			uploadBody.setFileName("xxx/aa.pdf");
    			ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(uploadBody);
                
                return fileDigestSignResult;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
   
	
	/**
     * @Description: 获取保全url
     * @param eviName  存证名称
     * @param preserveEntity    待保全内容对象
     * @param eSignIds  电子签名证据ID列表
     * @param bizIds    E签宝业务ID列表
     * @return  PreserveResultEntity 原文保全结果实体
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月14日
     */
    public PreserveResultEntity getPreserveUrl(String eviName,PreserveEntity preserveEntity,List<TypeEntity> eSignIds,List<TypeEntity> bizIds){
        
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("eviName", eviName);
        paramMap.put("content", preserveEntity);
        paramMap.put("eSignIds", eSignIds);
        paramMap.put("bizIds", bizIds);
        
        String jsonText = JSON.toJSONString(paramMap);
        
        log.info(">>>>>>>>>>>>>>>>>>>>>> getPreserveUrl jsonText:{}",jsonText);
        
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("X-timevale-project-id", esignConfigure.getProjectId());
        headers.put("X-timevale-signature-algorithm", "hmac-sha256");
        headers.put("X-timevale-signature", HmacUtils.hmac(esignConfigure.getProjectSecret(), jsonText));
        headers.put("X-timevale-mode", "package");
        headers.put("Content-Type", "application/json");
        
        String resultStr = HttpClientUtil.doPostJson(esignConfigure.getPreserveUrl(), headers, jsonText);
        log.info(">>>>>>>>>>>>>>>>>>>>>> getPreserveUrl resultStr:{}",resultStr);
        PreserveResultEntity preserveResultEntity = JSON.parseObject(resultStr, PreserveResultEntity.class);
        return preserveResultEntity;
    }
    
    /**
     * @Description: TODO 存证文件上传
     * @param url   上传URL(通过获取原文保全Url接口获取)
     * @param filePath  待上传文件路径
     * @return
     * @throws Exception 
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月14日
     */
    public boolean uploadFile(String url,String filePath) throws Exception{
        try {
            //String contentBase64Md5 = EncryptionUtil.getStringContentMD5(new String(bytes));
            String contentBase64Md5 = AlgorithmHelper.getContentMD5(filePath);
            Map<String,String> headers = new HashMap<String, String>();
            headers.put("Content-MD5",contentBase64Md5);
            headers.put("Content-Type","application/octet-stream");
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            boolean flag = HttpClientUtil.doPut(url, headers, b);
            if(flag){
                log.info("待保全文档上传成功！");  
                return true;
            }
            log.info("uploadFile flag:{}",flag);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * @Description:  
     * @param evid  存证编号
     * @param list  用户证件信息
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月15日
     */
    public ResultDto relate(String evid,List<CertificateEntity> list){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("evid", evid);
        paramMap.put("certificates", list);
        
        String jsonText = JSON.toJSONString(paramMap);
        log.info(">>>>>>>>>>>>>>>>>>>>>> relate jsonText:{}",jsonText);
        
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("X-timevale-project-id", esignConfigure.getProjectId());
        headers.put("X-timevale-signature-algorithm", "hmac-sha256");
        headers.put("X-timevale-signature", HmacUtils.hmac(esignConfigure.getProjectSecret(),jsonText));
        headers.put("X-timevale-mode", "package");
        headers.put("Content-Type", "application/json");
        
        String resultStr = HttpClientUtil.doPostJson(esignConfigure.getRelateUrl(), headers, jsonText);
        log.info(">>>>>>>>>>>>>>>>>>>>>> relate resultStr:{}",resultStr);
        ResultDto resultDto = JSON.parseObject(resultStr, ResultDto.class);
        return resultDto;
    }
}
