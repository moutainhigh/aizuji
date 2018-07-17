package org.gz.webank.supports;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.service.webank.WebankDataCacheService;
import org.gz.common.utils.StringUtils;
import org.gz.webank.service.WebankAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 检查app access token是否已存在
 * 
 * @author yangdaox
 */
@Service
@Slf4j
public class AccessTokenInitialChecker {

	@Autowired
	private WebankDataCacheService webankDataCacheService;
	
	@Autowired
	private WebankAccessTokenService accessTokenService;
	
	@PostConstruct
	public void check() {
		String value = webankDataCacheService.getAccessToken();
		if (StringUtils.isEmpty(value)) {
			log.info("AccessTokenInitialChecker.check() --> get access token is null, start request from webank.....");
			
			accessTokenService.requestTokenAndCache();
		}
	}
	
}
