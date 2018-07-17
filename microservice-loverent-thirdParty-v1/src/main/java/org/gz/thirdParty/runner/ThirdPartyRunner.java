/**
 * 
 */
package org.gz.thirdParty.runner;

import org.gz.thirdParty.constant.SFConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThirdPartyRunner implements CommandLineRunner {

	@Value("${enable.sandbox}")
	private boolean enableSandbox;

	@Override
	public void run(String... args) throws Exception {
		if (enableSandbox) {//沙盒
			SFConstant.appKey = "24A4E4FA525BCE4A19C8AE6577D76116";
			SFConstant.URL_HEAD = "https://open-sbox.sf-express.com";
		} else {//正式
			SFConstant.appKey = "E6BB0C2CB081BA0B88327424C57C5D3D";
			SFConstant.URL_HEAD = "https://open-prod.sf-express.com";
		}
		log.info("appKey:{}",SFConstant.appKey);
		log.info("URL_HEAD:{}",SFConstant.URL_HEAD);
	}
}
