package org.gz.app.ca.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.timevale.esign.sdk.tech.service.EsignsdkService;
import com.timevale.esign.sdk.tech.service.factory.EsignsdkServiceFactory;
import com.timevale.tech.sdk.bean.HttpConnectionConfig;
import com.timevale.tech.sdk.bean.ProjectConfig;
import com.timevale.tech.sdk.bean.SignatureConfig;

/**
 * esign SDK初始化
 * 
 * @author yangdx
 *
 */
@Configuration
public class EsignConfigure {
    @Value("${tech.getAPIInfoUrl}")
    private String apisUrl;
    
    @Value("${tech.http.config.retry}")  
    private String retryTimes;
    
    @Value("${tech.projectId}")  
    private String projectId; 
    
    @Value("${tech.projectSecret}")  
    private String projectSecret;
    
    /**
	 * 获取保全路径url
	 */
    @Value("${tech.preserveUrl}")  
	private String preserveUrl;
	
	/**
	 * 上传存证url
	 */
    @Value("${tech.relateUrl}")  
	private String relateUrl;
    
    /**
     * 初始化esign edk
     * @return
     */
    @Bean
    public EsignsdkService initTechSdk(){
    	System.err.println("++++++++++++++++++++++++++++++++++++");
        EsignsdkService SDK = EsignsdkServiceFactory.instance();
        ProjectConfig projectConfig = new ProjectConfig();
        projectConfig.setItsmApiUrl(apisUrl);
        projectConfig.setProjectId(projectId);
        projectConfig.setProjectSecret(projectSecret);
        
        HttpConnectionConfig httpConfig = new HttpConnectionConfig();
        //httpConfig.setProxyIp();
       // httpConfig.setProxyPort(proxyPort);
        httpConfig.setRetry(Integer.valueOf(retryTimes));
        
        SignatureConfig signConfig = new SignatureConfig();
        
        SDK.init(projectConfig, httpConfig, signConfig);
        return SDK;
    }

	public String getApisUrl() {
		return apisUrl;
	}

	public void setApisUrl(String apisUrl) {
		this.apisUrl = apisUrl;
	}

	public String getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(String retryTimes) {
		this.retryTimes = retryTimes;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectSecret() {
		return projectSecret;
	}

	public void setProjectSecret(String projectSecret) {
		this.projectSecret = projectSecret;
	}

	public String getPreserveUrl() {
		return preserveUrl;
	}

	public void setPreserveUrl(String preserveUrl) {
		this.preserveUrl = preserveUrl;
	}

	public String getRelateUrl() {
		return relateUrl;
	}

	public void setRelateUrl(String relateUrl) {
		this.relateUrl = relateUrl;
	}
    
}
