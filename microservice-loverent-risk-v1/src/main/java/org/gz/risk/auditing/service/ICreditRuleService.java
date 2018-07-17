package org.gz.risk.auditing.service;

import java.util.List;
import java.util.Map;

import org.gz.risk.auditing.entity.CreditAuditRuleLog;
import org.gz.risk.auditing.entity.CreditResult;
import org.gz.risk.auditing.entity.LoanUser;
import org.gz.risk.auditing.entity.User;
import org.gz.risk.auditing.entity.Violation;
import org.gz.risk.bean.ViolationResp;

public interface ICreditRuleService {

    public void updateRules() ;
    public CreditResult autoCredit(String loanRecordId, User user, List<ViolationResp> violations) ;
    public LoanUser getLoanUser(String loanRecordId) ;

    public List<LoanUser> findSelfLoanUserHistories(String loanRecordId, long userId) ;

    public List<LoanUser> getAllLoanUserList(int field, int amount) ;
    
    public Integer getModelScore(long historyId) throws Exception;
    
    public int matchKilledList(String value, String type);

    public String getRepeatLoanResult(Long userId)throws Exception ;

    public List<Violation> getViolationList(List<ViolationResp> violationEntities) ;


    public void setViolationMap(Map<String, String> violationMap);
	public boolean isBairongKill(Long userId) ;
	public boolean isBlackCompanyKill(String idNum) ;
	public List<LoanUser> getAllLoanUserList() ;
	public boolean isBairongAfublackKill(Long historyId) ;
	public boolean isBairongBaifuKill(Long historyId) ;
	public boolean isBairongImcellKill(Long historyId) ;
	
	public boolean callDetails1Kill(Long userId);
	public boolean callDetails2Kill(Long userId);
	public boolean callDetails3Kill(Long userId);
	public boolean callDetails4Kill(Long userId);
	public boolean callDetails5Kill(Long userId);
	public boolean callDetails6Kill(Long userId);
	
	public boolean deviceSharingRefuse(Long historyId);
	public boolean isCallInformation1Kill(Long userId);
	public boolean isCallInformation2Kill(Long userId);
	public boolean isCallSms1Kill(Long userId);
	public boolean isCallSms2Kill(Long userId);
	public boolean checkIsExistsRecord(Long userId);
	public boolean isApplyKill(Long userId);
	public boolean isApplyKillCall(Long userId)throws Exception;
	public boolean isDeviceShareKill(Long userId);
	public boolean isDeviceShareKillCall(Long userId) throws Exception;
	public boolean isIndustryKill(Long userId);
	public boolean isIndustryKillCall(Long userId)throws Exception;
	public boolean isIdKill(Long userId);
	public boolean isChannelKill(Long userId);
	public boolean isCoupleKill(Long userId)throws Exception;
	public boolean isFraudKill(Long historyId)throws Exception;
	public boolean isSameqqKill(String qqNum)throws Exception;
	public boolean isRejectKill(Long userId)throws Exception;
	public boolean isDueContactKill(Long userId)throws Exception;
	public boolean isEmergencKill(String emergencyContactPhone)throws Exception;
	public boolean isPasswordKill(Long userId)throws Exception;
	public boolean isOutlookKill(Long userId)throws Exception;
	public boolean isDenullKill(Long userId)throws Exception;
	public boolean isXuewrongKill(Long userId)throws Exception;
	public boolean isCertlengKill(String idNum)throws Exception;
	public boolean isIpKill(Long userId)throws Exception;
	public boolean isAbnormalInfoKill(Long userId)throws Exception;
	public boolean isCompanyCellNumkill(String companyCellNum)throws Exception;
    boolean isSameIpKill(String ip) throws Exception;
    boolean isAbnormalContactsKill(String idNum) throws Exception;
    boolean isSameCurrentAddrKill(String address) throws Exception;
    boolean isDiffSpouseKill(String idNum) throws Exception;
    boolean isSameCompanyIncomeKill(String companyCellNum) throws Exception;
    boolean isSameContactsNameKill(String emergencyContact, String kinshipName, String spouseName) throws Exception;
    boolean isSameCompanyAddrKill(String companyDetailedAddress) throws Exception;
    boolean isSameContactsTelKill(String emergencyContactPhone, String kinshipTel, String spouseTel) throws Exception;
    boolean isSameContactsIdKill(String emergencyContactPhone, String kinshipTel, String spouseTel) throws Exception;
    /**
     * 
     * @Description: TODO 
     * @param companyCellNum
     * @return
     * @throws Exception
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年10月27日
     */
    boolean isSameCompanyPassword(String companyCellNum) throws Exception;
    boolean isDiffMaterial(Long userId) throws Exception;
    /**
     * 检查用户联系手机号是否小于6个
     * @Description: TODO 
     * @param userId
     * @return
     * @throws Exception
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年11月6日
     */
    boolean isContactsKill(Long userId) throws Exception;
    boolean isLoanContactsKill(Long userId) throws Exception;
    /**
     * 
     * @Description: TODO 是否为电话黑名单
     * @param userId
     * @return
     * @throws Exception
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年11月15日
     */
    boolean isBlackTelKill(Long userId) throws Exception;
    boolean isDiffMaterialKill2(Long userId) throws Exception;
    void batchSendLog(List<CreditAuditRuleLog> creditAuditRuleLogs);

}

