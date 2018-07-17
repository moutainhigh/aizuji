package org.gz.common.utils;
/**
 * 断言工具类
 * @author hxj
 *
 */
public final class AssertUtils {

	/**
	 * 判断输入数据是否为正数
	 * 
	 * @param input
	 * @return
	 */
	public static final boolean isPositiveNumber4Long(Long input) {
		if (input != null && input.longValue() > 0L) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入数据是否为正数
	 * 
	 * @param input
	 * @return
	 */
	public static final boolean isPositiveNumber4Int(Integer input) {
		if (input != null && input.intValue() > 0) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.err.println(isPositiveNumber4Long(null));
	}
}
