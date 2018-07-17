package org.gz.app.supports;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.gz.cache.service.lianpay.LianPayCityCodeCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yangdx
 *
 */
@Service
public class LianPayCityCodeCacheInitChecker {
	
	private Logger logger = LoggerFactory.getLogger(LianPayCityCodeCacheInitChecker.class);
	
	@Autowired
	private LianPayCityCodeCacheService lianPayCityCodeCacheService;
	
	
	@PostConstruct
    public void init() {
		initLianPayCityCodeCache();
    }
	
	private void initLianPayCityCodeCache() {
    	logger.info("-----LianPayCityCodeCacheInitChecker.initLianPayCityCodeCache() --> start init city code----");
		if(!lianPayCityCodeCacheService.checkLianPayCityCodeIsInit()) {
			readAndCacheFromTxtFile();
		}
		logger.info("-----LianPayCityCodeCacheInitChecker.initLianPayCityCodeCache() --> init city code success----");	
	}
	
	
	private void readAndCacheFromTxtFile() {
		BufferedReader reader = null;
		Map<String, String> data = new HashMap<>();
		InputStream is = LianPayCityCodeCacheInitChecker.class.getClassLoader().getResourceAsStream("config/lianPayCityCode.txt");
		if (is != null) {
			try {
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				
				String info = null;
				while((info = reader.readLine()) != null) {
					info = info.trim();
					if (info.contains(",")) {
						String[] arr = info.split(",");
						data.put(arr[0], arr[1]);
					}
					if (data.size() == 300) {
						lianPayCityCodeCacheService.cacheLianPayCityCode(data);
						data.clear();
					}
				}
				if (data.size() > 0) {
					lianPayCityCodeCacheService.cacheLianPayCityCode(data);
					data.clear();	
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (Exception e2) {
					logger.error("SensitiveWordInitChecker.readAndCacheFromTxtFile() --> close io stream failed, e: {}", e2);
				}
			}
		}
	}
}
