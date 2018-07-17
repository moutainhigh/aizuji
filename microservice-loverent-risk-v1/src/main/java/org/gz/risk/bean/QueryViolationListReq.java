package org.gz.risk.bean;

import java.io.Serializable;

/**
 * @Description:用户信息查询第三方违规数据参数
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月17日 	Created
 */
public class QueryViolationListReq extends BaseReq implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 用户历史记录id
     */
    private long historyId;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 身份证
     */
    private String idNum;
    /**
     * 手机号码
     */
    private String phoneNum;
    /**
     * 亲属电话
     */
    private String kinshipTel;
    /**
     * 配偶电话
     */
    private String spouseTel;
    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;
    
    public long getHistoryId() {
        return historyId;
    }
    
    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }
    
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getIdNum() {
        return idNum;
    }
    
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    
    public String getPhoneNum() {
        return phoneNum;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public String getKinshipTel() {
        return kinshipTel;
    }
    
    public void setKinshipTel(String kinshipTel) {
        this.kinshipTel = kinshipTel;
    }
    
    public String getSpouseTel() {
        return spouseTel;
    }
    
    public void setSpouseTel(String spouseTel) {
        this.spouseTel = spouseTel;
    }
    
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }
    
    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }
    
}
