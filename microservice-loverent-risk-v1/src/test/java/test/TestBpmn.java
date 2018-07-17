package test;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.order.common.entity.RentRecord;
import org.gz.risk.auditing.entity.Admin;
import org.gz.risk.auditing.entity.AppUser;
import org.gz.risk.auditing.entity.Credit;
import org.gz.risk.auditing.service.CreditRuleService;
import org.gz.risk.auditing.service.ICreditLoanService;
import org.gz.risk.auditing.service.IThirdPartyDataService;
import org.gz.risk.auditing.service.IUserService;
import org.gz.risk.auditing.service.outside.IRentRecordService;
import org.gz.risk.auditing.util.JsonUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.task.TaskService;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;

/**
 * @author JarkimZhu
 *         Created on 2016/11/22.
 * @since jdk1.8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TestBpmn {

    @Resource
    private ICreditLoanService creditLoanService;
//    @Resource
//    private ProcessEngine processEngine;
    @Resource
    private CreditRuleService creditRuleService;
    @Resource
    private TaskService taskService;
//    @Resource
//    private HistoryService historyService;
//    @Resource
//    private ILoanRecordService loanRecordService;
    @Resource
    private IUserService userService;
//    @Resource
//    private IAppUserService appUserService;
    @Resource
    private IThirdPartyDataService thirdPartyDataService;
    @Resource
    private IRentRecordService rentRecordService;
    
//    @Resource
//    private RuntimeService runtimeService;

    @Test
    @Transactional
    @Commit
    public void apply() {
        creditLoanService.applyLoan("S201704170449540", 122968);
    }

    @Test
    @Transactional
    @Commit
    @Ignore
    public void sign() {
        creditLoanService.sign("S201612120000260", 430);
    }

//    @Test
//    @Transactional
//    @Rollback
//    @Ignore
//    public void processFlow() {
//        String processId = creditLoanService.applyLoan("S201612050000079", 415);
//        String loanRecordId = "S201612050000079";
//        TaskService taskService = processEngine.getTaskService();
//
//        Admin admin1 = new Admin();
//        admin1.setGroupId("CreditAttache");
//        admin1.setUserId("JarkimZhu");
//
//        Task t1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println(t1);
//
//        Credit c1 = new Credit();
//        c1.setProcessInstanceId(processId);
//        c1.setLoanRecordId(loanRecordId);
//        c1.setTaskId(t1.getId());
//        c1.setAction(2);
//        c1.setAmount(3000d);
//        c1.setRemark("初审通过");
//        creditLoanService.processFlow(c1, admin1);
//
//        Credit lastCredit = creditLoanService.getLastCredit(processId);
//        System.out.println(lastCredit.getAssignee() + ":" + lastCredit.getAction() + ":" + lastCredit.getAmount());
//
//        Task t2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println(t2);
//
//        Admin admin2 = new Admin();
//        admin2.setGroupId("CreditOfficer");
//        admin2.setUserId("wuxunbo");
//
//        Credit c2 = new Credit();
//        c2.setProcessInstanceId(processId);
//        c2.setLoanRecordId(loanRecordId);
//        c2.setTaskId(t2.getId());
//        c2.setAction(3);
//        c2.setAmount(2000d);
//        c2.setRemark("二审退回");
//        creditLoanService.processFlow(c2, admin2);
//
//        Task t3 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println(t3);
//
//        Credit c3 = new Credit();
//        c3.setProcessInstanceId(processId);
//        c3.setLoanRecordId(loanRecordId);
//        c3.setTaskId(t3.getId());
//        c3.setAction(1);
//        c3.setAmount(2000d);
//        c3.setRemark("重新上报");
//        creditLoanService.processFlow(c3, admin1);
//
//        Task t4 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println(t4);
//
//        Credit c4 = new Credit();
//        c4.setProcessInstanceId(processId);
//        c4.setLoanRecordId(loanRecordId);
//        c4.setTaskId(t4.getId());
//        c4.setAction(1);
//        c4.setAmount(2000d);
//        c4.setRemark("通过");
//        creditLoanService.processFlow(c4, admin2);
//
//        Task t5 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println(t5);
//
//        creditLoanService.sign("S201612050000079", 415L);
//
//        Task t6 = taskService.createTaskQuery().processInstanceId(processId)
//                .taskCandidateGroup("Payment")
//                .singleResult();
//        System.out.println(t6);
//
//        Assert.assertNotNull(t6);
//    }

    @Test
    @Transactional
    @Rollback
    @Ignore
    public void testRefuse() {
        Admin admin2 = new Admin();
        admin2.setGroupId("CreditOfficer");
        admin2.setUserId("wuxunbo");

        Credit c2 = new Credit();
        c2.setProcessInstanceId("155055");
        c2.setLoanRecordId("");
        c2.setAction(2);
        c2.setAmount(2000d);
        c2.setRemark("JarkimZhu Test");
        creditLoanService.processFlow(c2, admin2);

    }

    @PostConstruct
    public void init() {
        Map<String, String> violationMap = JsonUtils.parseObject(new File("/Users/jarkimzhu/Programs/GZJF/SheBaoDai/Server/admin-domain/branches/dev/src/main/webapp/json/ViolationValue.json"),
                new TypeReference<Map<String, String>>() {
                });
        creditRuleService.setViolationMap(violationMap);
    }
    
    @Test
    public void testPassSystem(){
//    	ResponseResult<List<RentRecord>> ret = rentRecordService.queryRentRecordByState(1);
//    	List<RentRecord> list = ret.getData();
//        for (RentRecord instance : list) {
//                try {
//                String loanRecordId = instance.getRentRecordNo();
//                ResponseResult<AppUser> result = AppUser.getUser(instance.getUserId());
//                AppUser loanUser = result.getData();
//                if (null==loanUser) {
//					continue;
//				}
//                
//                	thirdPartyDataService.addViolation(loanRecordId, loanUser);
//                }catch (Exception e){
//                	continue;
//                }
//        }

    }

}
