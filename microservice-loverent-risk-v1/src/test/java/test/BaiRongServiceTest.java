package test;

import java.util.List;

import org.gz.risk.RiskApplication;
import org.gz.risk.dao.util.JsonUtil;
import org.gz.risk.entity.Violation;
import org.gz.risk.intf.BaiRongAtomService;
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
public class BaiRongServiceTest {
    
    final String logSign = "combining";
    
    @Autowired
    private BaiRongAtomService baiRongService;
    
    /**
     * @Description: 身份二要素
     * @throws Exception
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void idCard() throws Exception{
        Long userId = 1l;
        String idCard = "513029199507053651";
        String name = "张国良";
        List<Violation> list = baiRongService.idCard(userId, idCard, name);
        System.out.println("idCard**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @Description: 百荣手机三要素 
     * @throws Exception
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void cellPhone() throws Exception{
        Long userId = 1l;
        String idCard = "513029199507053651";
        String cell = "13538078584";
        String name = "张国良";
        List<Violation> list = baiRongService.cellPhone(userId, idCard, cell, name);
        System.out.println("cellPhone**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @Description: 银行卡四要数 
     * @throws Exception
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void bankFour() throws Exception{
        Long userId = 1l;
        String idCard = "513029199507053651";
        String cell = "13538078584";
        String name = "张国良";
        String bankId = "6227007200150879114";
        List<Violation> list = baiRongService.bankFour(userId, idCard, cell, name, bankId);
        System.out.println("bankFour**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @Description: 百荣特殊名单 
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addViolation(){
        Long userId = 1l;
        String id = "513029199507053651";
        String cell = "13538078584";
        String name = "张国良";
        String kinshipTel = "18688775042";
        String spouseTel = "13807729198";
        String emergencyContactPhone = "13537885362";
        List<Violation> list = baiRongService.addViolation(userId, id, cell, name, kinshipTel, spouseTel, emergencyContactPhone);
        System.out.println("addViolation**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @Description: 百荣借款设备
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addLoanEquipment(){
        Long userId = 1l;
        String id = "513029199507053651";
        String cell = "13538078584";
        String name = "张国良";
        String af_swift_number = "4000013_20161110203535_0119";
        List<Violation> list = baiRongService.addLoanEquipment(userId, id, cell, name, af_swift_number);
        System.out.println("addLoanEquipment**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @Description: 百荣注册设备
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addRegisterEquipment(){
        Long userId = 1l;
        String cell = "13538078584";
        String af_swift_number = "4000013_20161110203535_0119";
        List<Violation> list = baiRongService.addRegisterEquipment(userId, cell, af_swift_number);
        System.out.println("addRegisterEquipment**********************************\n"+JsonUtil.toJson(list));
    }
    
    /**
     * @Description: 百荣登录设备
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月22日
     */
    @Test
    public void addSignEquipment(){
        Long userId = 1l;
        String af_swift_number = "4000013_20161110203535_0119";
        List<Violation> list = baiRongService.addSignEquipment(userId, af_swift_number);
        System.out.println("addSignEquipment**********************************\n"+JsonUtil.toJson(list));
    }
    
}
