package org.gz.user.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="aliyun.oss")
public class OssConfigureProperties {

	private String accessUrlPrefix;

	public String getAccessUrlPrefix() {
		return accessUrlPrefix;
	}

	public void setAccessUrlPrefix(String accessUrlPrefix) {
		this.accessUrlPrefix = accessUrlPrefix;
	}
	
}
