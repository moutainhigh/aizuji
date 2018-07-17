package org.gz.warehouse.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class String2IntListConverter implements Converter<String, List<Integer>> {

	/**
	 * 字符串分隔符
	 */
	private final String delimiter;

	/**
	 * 是否需要排除空值
	 */
	private final boolean excludeEmptyValue;
	
	public String2IntListConverter() {
		this(",");
	}
	
	public String2IntListConverter(String delimiter) {
		this(delimiter, true);
	}

	public String2IntListConverter(String delimiter, boolean excludeEmptyValue) {
		this.delimiter = delimiter;
		this.excludeEmptyValue = excludeEmptyValue;
	}

	@Override
	public List<Integer> convert(String source) {
		if (StringUtils.hasText(source)) {
			String[] arr = source.split(delimiter);
			if (ArrayUtils.isNotEmpty(arr)) {
				List<Integer> list = new ArrayList<Integer>();
				for (String s : arr) {
					if (excludeEmptyValue) {
						if (StringUtils.hasText(s)) {
							list.add(Integer.valueOf(s));// 只加非空值
						}
					} else {
						// 全加
						list.add(StringUtils.hasText(s) ? Integer.valueOf(s) : null);
					}
				}
				return list;
			}
		}
		return new ArrayList<Integer>(0);
	}

}
