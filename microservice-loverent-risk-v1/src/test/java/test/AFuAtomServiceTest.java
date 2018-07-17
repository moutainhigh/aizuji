package test;

import java.io.IOException;
import java.util.List;

import org.gz.risk.RiskApplication;
import org.gz.risk.dao.util.JsonUtil;
import org.gz.risk.entity.Violation;
import org.gz.risk.intf.AFuAtomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description:TODO
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月22日 	Created
 */
@RunWith(SpringRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！ 
@SpringBootTest(classes=RiskApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class AFuAtomServiceTest {

    final String logSign = "combining";
    
    @Autowired
    private AFuAtomService AFuAtomService;
    
    /**
     * @throws IOException 
     * @Description: 阿福借款情况
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addQueryLoan() throws IOException{
        Long userId = 1l;
        String name = "张国良";
        String idNo = "513029199507053651";
        List<Violation> list = AFuAtomService.addQueryLoan(userId, name, idNo);
        System.out.println("addQueryLoan**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @throws IOException 
     * @Description: 阿福黑名单
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addQueryBlackList() throws IOException{
        Long userId = 1l;
        String name = "张国良";
        String idNo = "513029199507053651";
        String mobile = "13538078584";
        List<Violation> list = AFuAtomService.addQueryBlackList(userId, name, idNo, mobile);
        System.out.println("addQueryBlackList**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @throws IOException 
     * @Description: 阿福被查询情况
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addGetQueriedHistory() throws IOException{
        Long userId = 1l;
        String name = "张国良";
        String idNo = "513029199507053651";
        List<Violation> list = AFuAtomService.addGetQueriedHistory(name, idNo, userId);
        System.out.println("addGetQueriedHistory**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @throws IOException 
     * @Description: 阿福致诚分
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addQueryCreditScore() throws IOException{
        Long userId = 1l;
        String name = "张国良";
        String idNo = "513029199507053651";
        List<Violation> list = AFuAtomService.addQueryCreditScore(name, idNo, userId);
        System.out.println("addQueryCreditScore**********************************\n"+JsonUtil.toJson(list));
    }
    
}
