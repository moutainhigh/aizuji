package org.gz.risk.auditing.service;

import java.util.ArrayList;

import org.gz.risk.auditing.entity.DeviceShare;

/**
 * @author JarkimZhu
 *         Created on 2016/11/23.
 * @since jdk1.8
 */
public interface IUserService {

    ArrayList<DeviceShare> findDeviceShare(long historyId);
    
    Integer findScore(long historyUserId)throws Exception;
    
    String findRepeatLoanResult(Long userId)throws Exception;
    
    int getMatchKilledList(String value, String type);

    /*void saveLockRefuse(String loanRecordId);*/

	int findCountBairongKilled(Long historyId);

	int findCountBlackCompanyKill(String idNum);
	
	int findCountBairongAfublackKill(Long historyId);
	
	int findCountBairongBaifuKill(Long historyId);
	
	int findCountBairongImcellKill(Long historyId);

	int findCountApplyKill(Long userId);
	
	boolean getApplyKillCall(Long userId)throws Exception;

	int findCountDeviceShareKill(Long userId);
	
    boolean findCountDeviceShareKillCall(Long userId) throws Exception;

	int findCountIndustryKill(Long userId);
	
	boolean getIndustryKill(Long userId)throws Exception;
	
	void changeUserPhoneNum(Long userId,String oldPhoneNum,String phoneNum);
	
	int findCountIdKill(Long userId);

	int findCountChannelKill(Long userId);

	boolean getCoupleKill(Long userId)throws Exception;

	boolean getFraudKill(Long userId)throws Exception;

	boolean getSameqqKill(String qqNum)throws Exception;

	boolean getRejectKill(Long userId)throws Exception;

	boolean getDueContactKill(Long userId)throws Exception;

	boolean getEmergencKill(String emergencyContactPhone)throws Exception;

	boolean getPasswordKill(Long userId)throws Exception;

	boolean getOutlookKill(Long userId)throws Exception;

	boolean getDenullKill(Long userId)throws Exception;

	boolean getXuewrongKill(Long userId)throws Exception;

	boolean getCertlengKill(String idNum)throws Exception;
	
	boolean getIpKill(Long userId)throws Exception;
	
	boolean getAbnormalInfoKill(Long userId)throws Exception;
	
	boolean getCompanyCellNumkill(String companyCellNum)throws Exception;
	
	boolean getSameIpKill(String companyCellNum)throws Exception;
	boolean getAbnormalContactsKill(String idNum)throws Exception;
	boolean getSameCurrentAddrKill(String address)throws Exception;
	boolean getDiffSpouseKill(String idNum)throws Exception;
	boolean getSameCompanyIncomeKill(String companyCellNum)throws Exception;
	boolean getSameCompanyAddrKill(String companyDetailedAddress)throws Exception;
	boolean getSameContactsNameKill(String emergencyContact,String kinshipName,String spouseName)throws Exception;
	boolean getSameContactsTelKill(String emergencyContactPhone,String kinshipTel,String spouseTel)throws Exception;
	boolean getSameContactsIdKill(String emergencyContactPhone,String kinshipTel,String spouseTel)throws Exception;
	/**
	 * 
	 * @Description: TODO 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年10月27日
	 */
	boolean getDiffMaterial(Long userId)throws Exception;
	boolean getSameCompanyPassword(String companyCellNum)throws Exception;
	/**
	 * 检查用户联系人手机号是否小于6个
	 * @Description: TODO 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年11月6日
	 */
	boolean getContactsKill(Long userId)throws Exception;
	boolean getLoanContactsKill(Long userId)throws Exception;
	/**
	 * 
	 * @Description: TODO 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年11月15日
	 */
	boolean getBlackTelKill(Long userId)throws Exception;
	boolean getDiffMaterialKill2(Long userId)throws Exception;

}
