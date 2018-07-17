package org.gz.risk.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2016/11/16.
 */
@Repository
@Slf4j
public class RiskRuleDao  {
    
    @Resource
    private SimpleDaoSpringImpl dao;

	
	///调用规则
	public boolean getAbnormalContactsKill(String idNo) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(idNo);
        List<Map<String, Object>> list = null;
        list= dao.callProc("mapper.ViolationEntityMapper.getAbnormalContactsKill", params);
        log.info("getAbnormalContactsKill  {} result {}",idNo,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getApplyKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getApplyKill", params);
        log.info("getApplyKillCall userId {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getBlackTelKill(String idNo) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(idNo);
        List<Map<String, Object>> list=null;
        list = dao.callProc("mapper.ViolationEntityMapper.getBlackTelKill", params);
        log.info("getBlackTelKill userId {} result {}",idNo,list);
        if (null!=list && list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getCertlengKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getCertlengKill", params);
        log.info("getCertlengKill idNum {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getChannelKill(Long userId)  throws Exception{
		List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list = dao.callProc("mapper.ViolationEntityMapper.getChannelKill", params);
        log.info("getBlackCompanyKill userId {} result {}",userId,list);
        if (null!=list && list.size()>0) {
            return true;
        }
        return false;
	}
	public boolean getContactKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getContactKill", params);
        log.info("getContactKill userId {} result {}",userId,list);
        if (null!=list && list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getCoupleKill(String rentRecordNo) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(rentRecordNo);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getCoupleKill", params);
        log.info("getCoupleKill userId {} result {}",rentRecordNo,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getDenullKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getDenullKill", params);
        log.info("getDenullKill userId {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getDeviceShareKill(String deviceId) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(deviceId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getDeviceShareKill", params);
        log.info("list {} ",list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getDiffMaterial(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getDiffMaterial", params);
        log.info("getDiffMaterial userId {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getDiffSpouseKill(String idNo) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(idNo);
        List<Map<String, Object>> list = null;
        list= dao.callProc("mapper.ViolationEntityMapper.getDiffSpouseKill", params);
        log.info("getDiffSpouseKill idNo {} result {}",idNo,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getDueContactKill(String phone) throws Exception  {
        List<String> params=new ArrayList<>();
        params.add(phone);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getDueContactKill", params);
        log.info("getDueContactKill userId {}  result {}",phone,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getBairongAfublackKill(Long userId) throws Exception {
	    List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getBairongAfublackKill", params);
        log.info("getDueContactKill userId {}  result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
	}
	
	public boolean getBairongBaifuKill(Long userId) throws Exception {
	    List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getBairongBaifuKill", params);
        log.info("getDueContactKill userId {}  result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
	}
	public boolean getEmergencyKill(String emergencyContactPhone) throws Exception  {
        List<String> params=new ArrayList<>();
        params.add(emergencyContactPhone);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getEmergencyKill", params);
        log.info("getEmergencyKill EmergencyContactPhone {} result {}",emergencyContactPhone,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getIdKill(String idNo) throws Exception{
		List<String> params=new ArrayList<>();
        params.add(idNo);
        List<Map<String, Object>> list=null;
        list = dao.callProc("mapper.ViolationEntityMapper.getIdKill", params);
        log.info("getBlackCompanyKill userId {} result {}",idNo,list);
        if (null!=list && list.size()>0) {
            return true;
        }
        return false;
	}
	public boolean getIpKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getIpKill", params);
        log.info("getIpKill idNum {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getLoanContactsKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getLoanContactsKill", params);
        log.info("getLoanContactsKill userId {} result {}",userId,list);
        if (null!=list && list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getOutlookKill(Long userId)  throws Exception{
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getOutlookKill", params);
        log.info("getOutlookKill userId {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getRejectKill(String phoneNum) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(phoneNum);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getRejectKill", params);
        log.info("getRejectKill userId {} result {}",phoneNum,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameContactsIdKill(String emergencyContactPhone) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(emergencyContactPhone);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getSameContactsIdKill", params);
        log.info("getSameContactsIdKill emergencyContactPhone {} result {}",
            emergencyContactPhone,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameContactsNameKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getSameContactsNameKill", params);
        log.info("getSameContactsNameKill userId {}  result {}",
            userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameContactsTelKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getSameContactsTelKill", params);
        log.info("getSameContactsTelKill userId {}  result {}",
           userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameCurrentAddKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list = null;
        list= dao.callProc("mapper.ViolationEntityMapper.getSameCurrentAddKill", params);
        log.info("getSameCurrentAddrKill ip {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameIpKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list= dao.callProc("mapper.ViolationEntityMapper.getSameIpKill", params);
        log.info("getSameIpKill ip {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public Integer getScore(long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getScoreApplyScoring", params);
        if (null!=list&&list.size()>0) {
            Map<String, Object> map= list.get(0);
            Double score= (Double) map.get("score");
            return score.intValue();
        }
        return null;
    }
	public boolean getAbnormalInfoKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getAbnormalInfoKill", params);
        log.info("getAbnormalInfoKill userId {} result {}",userId,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getBlackCompanyKilled(Long userId) throws Exception {
		List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list = dao.callProc("mapper.ViolationEntityMapper.getBlackCompanyKilled", params);
        log.info("getBlackCompanyKill userId {} result {}",userId,list);
        if (null!=list && list.size()>0) {
            return true;
        }
        return false;
	}
	public boolean getCompanyCellNumkill(String companyContactNumber) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(companyContactNumber);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getCompanyCellNumkill", params);
        log.info("getCompanyCellNumkill companyCellNum {} result {}",companyContactNumber,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getIndustryKill(Long userId) throws Exception {
        List<Long> params=new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getIndustryKill", params);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameCompanyAddKill(String companyAddress) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(companyAddress);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getSameCompanyAddKill", params);
        log.info("getSameCompanyAddrKill companyDetailedAddress {} result {}",companyAddress,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
	public boolean getSameCompanyPassword(String CompanyContactNumber) throws Exception {
        List<String> params=new ArrayList<>();
        params.add(CompanyContactNumber);
        List<Map<String, Object>> list=null;
        list=dao.callProc("mapper.ViolationEntityMapper.getSameCompanyPassword", params);
        log.info("getSameCompanyPassword companyCellNum {} result {}",CompanyContactNumber,list);
        if (null!=list&&list.size()>0) {
            return true;
        }
        return false;
    }
}
