/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package test;

import java.io.UnsupportedEncodingException;

import org.gz.risk.RiskApplication;
import org.gz.risk.bean.QueryViolationListReq;
import org.gz.risk.bean.Result;
import org.gz.risk.dao.util.JsonUtil;
import org.gz.risk.dao.util.LogIdGenerator;
import org.gz.risk.intf.ViolationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 
 * @Description:TODO
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月15日 	Created
 */
@RunWith(SpringRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！ 
@SpringBootTest(classes=RiskApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class ViolationServiceTest{
    
    final String logSign = "combining";
    
    @Autowired
    private ViolationService violationService;
    
    /**
     * @throws UnsupportedEncodingException 
     * @Description: 用户信息查询第三方数据
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    @Test
    public void getViolationListByUser() throws UnsupportedEncodingException
    {
        QueryViolationListReq req = new QueryViolationListReq();
        req.setEmergencyContactPhone("15989506530");
        req.setHistoryId(5);
        req.setIdNum("37252619790727501X");
        req.setKinshipTel("13963534600");
        req.setPhoneNum("18898590628");
        req.setRealName("贾长亮");
        req.setSpouseTel("13417476960");
        String curLogSign = LogIdGenerator.generateCode(req.getLogSign(),logSign);
        req.setLogSign(curLogSign);
//        Result result = violationService.getViolationListByUser(req);
//        System.out.println(JsonUtil.toJson(result));
    }
    
}
