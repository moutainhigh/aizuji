package org.gz.app.ca.entity;

import java.time.LocalDate;
/**
 * 
 * @Description:TODO    待保全内容对象
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月14日 	Created
 */
public class PreserveEntity {
    /**
     * 原文保全预设截止日期
     */
    private LocalDate storageLife;
    /**
     * 待保全文档名称，如test.pdf
     */
    private String contentDescription;
    /**
     * 待保全文档大小，单位：字节
     */
    private String contentLength;
    /**
     * 待保全文档内容字节流MD5摘要的Base64编码值
     */
    private String contentBase64Md5;
    
    public LocalDate getStorageLife() {
        return storageLife;
    }
    
    public void setStorageLife(LocalDate storageLife) {
        this.storageLife = storageLife;
    }
    
    public String getContentDescription() {
        return contentDescription;
    }
    
    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }
    
    public String getContentLength() {
        return contentLength;
    }
    
    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }
    
    public String getContentBase64Md5() {
        return contentBase64Md5;
    }
    
    public void setContentBase64Md5(String contentBase64Md5) {
        this.contentBase64Md5 = contentBase64Md5;
    }
    
}
