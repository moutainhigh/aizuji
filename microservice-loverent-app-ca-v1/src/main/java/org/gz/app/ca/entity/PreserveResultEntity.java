package org.gz.app.ca.entity;

/**
 * 
 * @Description:TODO    原文保全结果实体
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月15日 	Created
 */
public class PreserveResultEntity {
    /**
     * 文档保全上传Url
     */
    private String url;
    /**
     * 存证记录编号
     */
    private String evid;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getEvid() {
        return evid;
    }
    
    public void setEvid(String evid) {
        this.evid = evid;
    }
    
}
