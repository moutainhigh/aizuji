package org.gz.app.ca.entity;

/**
 * 
 * @Description:TODO    证件参数实体
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月15日 	Created
 */
public class CertificateEntity {
    /**
     * 证件类型
     */
    private String type;
    /**
     * 证件号码
     */
    private String number;
    /**
     * 用户/企业/组织名称
     */
    private String name;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
