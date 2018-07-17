///**
// * Copyright © 2014 GZJF Corp. All rights reserved.
// * This software is proprietary to and embodies the confidential
// * technology of GZJF Corp.  Possession, use, or copying
// * of this software and media is authorized only pursuant to a
// * valid written license from GZJF Corp or an authorized sublicensor.
// */
//package org.gz.app.test;
//
//import java.util.Map;
//
//import org.gz.app.ca.component.EsignManager;
//import org.gz.cache.manager.RedisManager;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.alibaba.fastjson.JSONObject;
//import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
//import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
//import com.timevale.esign.sdk.tech.bean.seal.OrganizeTemplateType;
//import com.timevale.esign.sdk.tech.bean.seal.PersonTemplateType;
//import com.timevale.esign.sdk.tech.bean.seal.SealColor;
//
///**
// * @Description:TODO
// * Author	Version		Date		Changes
// * zhuangjianfa 	1.0  		2017年7月1日 	Created
// */
//@RunWith(SpringRunner.class)
////@SpringBootTest(classes = CAApplication.class)
//@WebAppConfiguration
//public class EsignTest {
//	@Autowired
//    private EsignManager manager;
//	
//	@Autowired
//	private RedisManager redisManager;
//    
//	@Test
//	public void addPersonAccount(){
//	    AddAccountResult accountResult = manager.addPersonAccount("郦灵欣", "150901199105299694", "18813579506");
//	    System.out.println("user account: " + accountResult.getAccountId());
//	}
//    
//    @Test
//	public void addPersonTemplateSeal(){
//    	String accountId = "6E2E4683791A4BDCA44D92095E178DF8";
//    	AddSealResult result = manager.addPersonTemplateSeal(accountId, PersonTemplateType.RECTANGLE, SealColor.RED);
//	    System.out.println("user seal data: " + result.getSealData());
//	}
//    
//    @Test
//	public void addOrganizeAccount(){
//    	String name = "深圳市国智网络科技有限公司";
//	    String organCode = "MA5DPHR10";
//	    AddAccountResult accountResult = manager.addOrganizeAccount(name, organCode);
//	    System.out.println("organize account: " + accountResult.getAccountId());
//	}
//    
//    @Test
//	public void addOrganizeTemplateSeal(){
//    	String accountId = "8C4837DCEDA9454CB803E6F9B277159E";
//    	AddSealResult result = manager.addOrganizeTemplateSeal(accountId, OrganizeTemplateType.STAR, SealColor.RED);
//	    System.out.println("organize seal data: " + result.getSealData());
//	}
//    
//    @Test
//	public void testSendMessage(){
//		String accountId = "6E2E4683791A4BDCA44D92095E178DF8";
//		String rst = manager.sendMessageOfSign(accountId);
//		System.out.println("rst: " + rst);
//	}
//    
//    
//	@Test
//    public void userSign(){
//		String accountId = "6E2E4683791A4BDCA44D92095E178DF8";
//		String sealData = "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";
//		String srcPdfFile = "pdf/agreement/temp/unsign.pdf";
//		String code = "763388";
//		String organizeAccount = "8C4837DCEDA9454CB803E6F9B277159E";
//		String organizeSealData = "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";
//    
//		Map<String, String> map = manager.sign(accountId, sealData, organizeAccount, organizeSealData, srcPdfFile, code, 100L);
//		
//		System.out.println(JSONObject.toJSONString(map));
//	}
//	
//	@Test
//	public void xxx(){
//		System.out.println("1111111111");
//		System.out.println(redisManager.get("xxx"));
//	}
//}
