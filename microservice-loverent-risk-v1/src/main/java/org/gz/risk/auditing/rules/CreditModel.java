package org.gz.risk.auditing.rules;

import java.io.Serializable;
import java.util.List;

import org.gz.risk.auditing.entity.CreditAuditRuleLog;
import org.gz.risk.auditing.entity.LoanUser;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.auditing.entity.Violation;
import org.gz.risk.auditing.service.CreditRuleService;
import org.gz.risk.bean.ViolationResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author JarkimZhu
 *         Created on 2017/1/16.
 * @since jdk1.8
 */
public class CreditModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger("auto-credit-logger");
	
    
    private static final String CERT = "CERT";
    private static final String QQ = "QQ";
    private static final String PHONE = "PHONE";
    private static final String CHANNEL_NO_H5= "h5";
    
	
    /**
     * 个人历史申请
     */
    private List<LoanUser> loanUserHistories;
    /**
     * 复借评分结果
     */
    private String repeatLoanResult;

    /**
     * 申请单号
     */
    private String loanRecordId;
    /**
     * 用户信息
     */
    private User user;
    /**
     * 第三方原始数据
     */
    private List<ViolationResp> violations;
    /**
     * 当前申请记录
     */
    private LoanUser loanUser;
    /**
     * 第三方数据
     */
    private List<Violation> violationList;
    /**
     * 模型评分
     */
    private int modelScore = -1;
    /**
     * 依赖注入
     */
    private CreditRuleService creditRuleService;
    
    private List<CreditAuditRuleLog> creditAuditRuleLogs;
    private Long unit = 1000L;
    

    public List<CreditAuditRuleLog> getCreditAuditRuleLogs() {
		return creditAuditRuleLogs;
	}

	public void setCreditAuditRuleLogs(List<CreditAuditRuleLog> creditAuditRuleLogs) {
		this.creditAuditRuleLogs = creditAuditRuleLogs;
	}

	public String getLoanRecordId() {
        return loanRecordId;
    }

    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    public void setCreditRuleService(CreditRuleService creditRuleService) {
        this.creditRuleService = creditRuleService;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setViolations(List<ViolationResp> violations) {
        this.violations = violations;
    }
    public LoanUser getLoanUser() {
        if (loanUser == null) {
            loanUser = creditRuleService.getLoanUser(loanRecordId);
        }
        return loanUser;
    }
//    public boolean isRepeatLoan() {
//        long beginTime = System.currentTimeMillis();
//        List<LoanUser> his = getLoanUserHistories();
//        for (LoanUser u : his) {
//            if (u.getApprovalResult() != null && u.getApprovalResult() == 31) {
//                return true;
//            }
//        }
//        Long endTimeS = (System.currentTimeMillis() - beginTime);
//        if (endTimeS > unit) {
//            logger.info("isRepeatLoan {} = {}", loanRecordId, endTimeS + "毫秒");
//        }
//        return false;
//    }
//    public List<LoanUser> getLoanUserHistories() {
//        long beginTime = System.currentTimeMillis();
//        if (loanUserHistories == null) {
//            loanUserHistories = creditRuleService.findSelfLoanUserHistories(loanRecordId, user.getUserId());
//        }
//        Long endTimeS = (System.currentTimeMillis() - beginTime);
//        if (endTimeS > unit) {
//            logger.info("getLoanUserHistories {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
//        }
//        return loanUserHistories;
//    }
    /**
     * 获取日期范围内的所有申请信息
     *
     * @param field  时间单位
     * @param amount 时间值
     * @return 用户申请信息
     */
    public List<LoanUser> getAllLoanUserList(int field, int amount) {
        long beginTime = System.currentTimeMillis();
        List<LoanUser> list = creditRuleService.getAllLoanUserList(field, amount);

        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("getAllLoanUserList {} = {}= {}= {}", loanRecordId, endTimeS + "毫秒", field, amount);
        }
        return list;
    }
    
    public List<LoanUser> getAllLoanUserListNoParam() {
        long beginTime = System.currentTimeMillis();
        List<LoanUser> list = creditRuleService.getAllLoanUserList();
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("getAllLoanUserListNoParam {} = {}", loanRecordId, endTimeS + "毫秒");
        }
        return list;
    }
   
    public List<Violation> getViolationList() {
        long beginTime = System.currentTimeMillis();
        if (violationList == null) {
            violationList = creditRuleService.getViolationList(violations);
        }
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("getViolationList {} = {}", loanRecordId, endTimeS + "毫秒");
        }
        return violationList;
    }

    /*******************以下为调用存储过程*************************/
    
    public boolean isAbnormalContactsKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isAbnormalContactsKill(user.getAppUser().getIdNo());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isAbnormalContactsKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getAppUser().getIdNo());
        }
        return check;
    }
    
    public boolean isApplyKill() throws Exception {
      	 long beginTime = System.currentTimeMillis();
           boolean check = creditRuleService.isApplyKill(user.getUserId());
           Long endTimeS = (System.currentTimeMillis() - beginTime);
           if (endTimeS > unit) {
               logger.info("applyKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
           }
           return check;
    }
    
    public boolean isBlackTelKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isBlackTelKill(user.getAppUser().getIdNo());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isBlackTelKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getAppUser().getIdNo());
        }
        return check;
    }
    
    public boolean isCertlengKill() throws Exception {
      	 long beginTime = System.currentTimeMillis();
           boolean check = creditRuleService.isCertlengKill(user.getUserId());
           Long endTimeS = (System.currentTimeMillis() - beginTime);
           if (endTimeS > unit) {
               logger.info("isCertlengKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
           }
           return check;
    }
    
    public boolean isChannelKill()  throws Exception{
     	 long beginTime = System.currentTimeMillis();
          boolean check = creditRuleService.isChannelKill(user.getUserId());
          Long endTimeS = (System.currentTimeMillis() - beginTime);
          if (endTimeS > unit) {
              logger.info("isChannelKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
          }
          return check;
     }
    
    public boolean isContactKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isContactKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isContactsKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
    }
    
    public boolean isCoupleKill() throws Exception {
     	 long beginTime = System.currentTimeMillis();
          boolean check = creditRuleService.isCoupleKill(user.getLoanRecordId());
          Long endTimeS = (System.currentTimeMillis() - beginTime);
          if (endTimeS > unit) {
              logger.info("isCoupleKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
          }
          return check;
   }
    
    public boolean isDenullKill() throws Exception {
   	 long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isDenullKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isDenullKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
   }
    
    public boolean isDeviceShareKill() throws Exception {
      	 long beginTime = System.currentTimeMillis();
           boolean check = creditRuleService.isDeviceShareKill(user.getAppUser().getDeviceId());
           Long endTimeS = (System.currentTimeMillis() - beginTime);
           if (endTimeS > unit) {
               logger.info("isDeviceShareKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
           }
           return check;
     }
    
    public boolean isDiffMaterial() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isDiffMaterial(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isDiffMaterial {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
    }
    
    public boolean isDiffSpouseKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isDiffSpouseKill(user.getAppUser().getIdNo());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isDiffSpouseKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getAppUser().getIdNo());
        }
        return check;
    }
    
    public boolean isDueContactKill() throws Exception {
     	 long beginTime = System.currentTimeMillis();
          boolean check = creditRuleService.isDueContactKill(user.getAppUser().getPhoneNum());
          Long endTimeS = (System.currentTimeMillis() - beginTime);
          if (endTimeS > unit) {
              logger.info("isDueContactKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
          }
          return check;
     }
    
    public boolean isBairongAfublackKill() throws Exception {
    	 long beginTime = System.currentTimeMillis();
         boolean check = creditRuleService.isBairongAfublackKill(user.getUserId());
         Long endTimeS = (System.currentTimeMillis() - beginTime);
         if (endTimeS > unit) {
             logger.info("isBairongAfublackKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
         }
         return check;
    }
    
    public boolean isBairongBaifuKill() throws Exception {
    	 long beginTime = System.currentTimeMillis();
         boolean check = creditRuleService.isBairongAfublackKill(user.getUserId());
         Long endTimeS = (System.currentTimeMillis() - beginTime);
         if (endTimeS > unit) {
             logger.info("isBairongBaifuKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
         }
         return check;
    }
    public boolean isEmergencyKill() throws Exception {
     	 long beginTime = System.currentTimeMillis();
          boolean check = creditRuleService.isEmergencyKill(user.getEmergencyContactPhone());
          Long endTimeS = (System.currentTimeMillis() - beginTime);
          if (endTimeS > unit) {
              logger.info("isEmergencKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getEmergencyContactPhone());
          }
          return check;
     }
    
    public boolean isIdKill() throws Exception{
     	 long beginTime = System.currentTimeMillis();
          boolean check = creditRuleService.isIdKill(user.getAppUser().getIdNo());
          Long endTimeS = (System.currentTimeMillis() - beginTime);
          if (endTimeS > unit) {
              logger.info("isIdKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getAppUser().getIdNo());
          }
          return check;
     }
    
    public boolean isIpKill() throws Exception {
    	long beginTime = System.currentTimeMillis();
    	boolean check = creditRuleService.isIpKill(user.getUserId());
    	Long endTimeS = (System.currentTimeMillis() - beginTime);
    	if (endTimeS > unit) {
    		logger.info("isIpKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
    	}
    	return check;
    }
    
    public boolean isLoanContactsKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isLoanContactsKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isLoanContactsKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
    }
    
    public boolean isOutlookKill() throws Exception {
    	 long beginTime = System.currentTimeMillis();
    	 if(CHANNEL_NO_H5.equals(user.getChannelNo())){
    		   logger.info("isOutlookKill 命中h5规则  {} =  {}", loanRecordId,  user.getChannelNo());
    		   return false;
    	 }
         boolean check = creditRuleService.isOutlookKill(user.getUserId());
         Long endTimeS = (System.currentTimeMillis() - beginTime);
         if (endTimeS > unit) {
             logger.info("isOutlookKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
         }
         return check;
    }
    
    public boolean isRejectKill() throws Exception {
      	 long beginTime = System.currentTimeMillis();
           boolean check = creditRuleService.isRejectKill(user.getAppUser().getPhoneNum());
           Long endTimeS = (System.currentTimeMillis() - beginTime);
           if (endTimeS > unit) {
               logger.info("isRejectKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getAppUser().getPhoneNum());
           }
           return check;
      }
    
    public boolean isSameContactsIdKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameContactsIdKill(user.getEmergencyContactPhone());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameContactsIdKill {} = {} = {} = {} = {} ", loanRecordId, endTimeS + "毫秒",
                user.getEmergencyContactPhone());
        }
        return check;
    }
    
    public boolean isSameContactsNameKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameContactsNameKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameContactsNameKill {} = {} = {} = {} = {} ", loanRecordId, endTimeS + "毫秒",
                user.getUserId());
        }
        return check;
    }
    
    public boolean isSameContactsTelKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameContactsTelKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameContactsTelKill {} = {} = {} = {} = {} ", loanRecordId, endTimeS + "毫秒",
                user.getUserId());
        }
        return check;
    }
    
    public boolean isSameCurrentAddKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameCurrentAddKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameCurrentAddKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
    }
    
    public boolean isSameIpKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameIpKill(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameIpKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
    }
    
    public double getModelScore() throws Exception {
        long beginTime = System.currentTimeMillis();
        if (modelScore == -1) {
        	modelScore = creditRuleService.getModelScore(user.getUserId());
        }
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("getModelScore {} = {} = {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return modelScore;
    }
    
    public boolean isAbnormalInfoKill() throws Exception {
    	long beginTime = System.currentTimeMillis();
    	boolean check = creditRuleService.isAbnormalInfoKill(user.getUserId());
    	Long endTimeS = (System.currentTimeMillis() - beginTime);
    	if (endTimeS > unit) {
    		logger.info("isAbnormalInfoKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
    	}
    	return check;
    }

    public boolean isBlackCompanyKilled()  throws Exception{
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isBlackCompanyKilled(user.getUserId());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isBlackCompanyKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
        }
        return check;
    }
    
    public boolean isCompanyCellNumKill() throws Exception {
    	long beginTime = System.currentTimeMillis();
    	boolean check = creditRuleService.isCompanyCellNumkill(user.getUserHistory().getCompanyContactNumber());
    	Long endTimeS = (System.currentTimeMillis() - beginTime);
    	if (endTimeS > unit) {
    		logger.info("isCompanyCellNumkill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserHistory().getCompanyContactNumber());
    	}
    	return check;
    }
    
    public boolean isIndustryKill() throws Exception {
      	 long beginTime = System.currentTimeMillis();
           boolean check = creditRuleService.isIndustryKill(user.getUserId());
           Long endTimeS = (System.currentTimeMillis() - beginTime);
           if (endTimeS > unit) {
               logger.info("isIndustryKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserId());
           }
           return check;
    }
    
    public boolean isSameCompanyAddKill() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameCompanyAddKill(user.getUserHistory().getCompanyAddress());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameCompanyAddrKill {} = {}= {}", loanRecordId, endTimeS + "毫秒", user.getUserHistory().getCompanyAddress());
        }
        return check;
    }
    
    public boolean isSameCompanyPassword() throws Exception {
        long beginTime = System.currentTimeMillis();
        boolean check = creditRuleService.isSameCompanyPassword(user.getUserHistory().getCompanyContactNumber());
        Long endTimeS = (System.currentTimeMillis() - beginTime);
        if (endTimeS > unit) {
            logger.info("isSameCompanyPassword {} = {}= {}", loanRecordId, endTimeS + "毫秒",user.getUserHistory().getCompanyContactNumber());
        }
        return check;
    }

    

}
