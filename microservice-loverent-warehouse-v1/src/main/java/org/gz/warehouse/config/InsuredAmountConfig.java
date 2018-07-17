/**
 * 
 */
package org.gz.warehouse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class InsuredAmountConfig {

	@Value("${insuredAmount_0}")
	private String insuredAmount_0;

	@Value("${insuredAmount_2000}")
	private String insuredAmount_2000;

	@Value("${insuredAmount_5000}")
	private String insuredAmount_5000;

	public String getInsuredAmount_0() {
		return insuredAmount_0;
	}

	public void setInsuredAmount_0(String insuredAmount_0) {
		this.insuredAmount_0 = insuredAmount_0;
	}

	public String getInsuredAmount_2000() {
		return insuredAmount_2000;
	}

	public void setInsuredAmount_2000(String insuredAmount_2000) {
		this.insuredAmount_2000 = insuredAmount_2000;
	}

	public String getInsuredAmount_5000() {
		return insuredAmount_5000;
	}

	public void setInsuredAmount_5000(String insuredAmount_5000) {
		this.insuredAmount_5000 = insuredAmount_5000;
	}

	public String monthlyCardNum(String insuredAmount) {
		if (StringUtils.hasText(insuredAmount)) {
			if (insuredAmount.equals("0")) {
				return insuredAmount_0;
			} else if (insuredAmount.equals("2000")) {
				return insuredAmount_2000;
			} else if (insuredAmount.equals("5000")) {
				return insuredAmount_5000;
			}
		}
		return null;
	}
}
