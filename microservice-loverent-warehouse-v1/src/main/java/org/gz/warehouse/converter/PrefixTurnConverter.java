/**
 * 
 */
package org.gz.warehouse.converter;

import org.springframework.util.StringUtils;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月22日 上午10:21:36
 */
public class PrefixTurnConverter implements TurnConverter<String, String> {

	public static final String DEF_PREFIX = "99999";

	private final String prefix;

	public PrefixTurnConverter() {
		this(DEF_PREFIX);
	}

	public PrefixTurnConverter(String prefix) {
		if (StringUtils.hasText(prefix)) {
			this.prefix = prefix;
		} else {
			throw new IllegalArgumentException("prefix can't be empty");
		}
	}

	@Override
	public String transform(String source) {
		return prefix + source;
	}

	@Override
	public String revert(String target) {
		return target.substring(prefix.length());
	}
	
}
