/**
 * 
 */
package org.gz.common.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月5日 下午7:54:02
 */
public final class StringHelper {

	/**
	 * 将列表拼接成以delimiter分隔的字符串
	 * 
	 * @Description:
	 * @param objs
	 * @param delimiter
	 * @return String objs为空，则返回""
	 */
	public static String join(List<?> objs, String delimiter) {
		if (!StringUtils.hasText(delimiter)) {
			delimiter = ",";
		}
		if (!CollectionUtils.isEmpty(objs)) {
			StringBuilder res = new StringBuilder();
			int size = objs.size();
			Object obj = null;
			for (int i = 0; i < size; i++) {
				obj = objs.get(i);
				if (obj != null) {
					res.append(obj.toString()).append(delimiter);
				}
			}
			if (res.length() > 0) {
				return res.toString().substring(0, res.length() - 1);
			}
		}
		return "";
	}

	public static void main(String[] args) {
		List<String> list = Arrays.asList(new String[] { "1", "2", null, "3", "4" });
		System.out.println(join(list, null));
	}
}
