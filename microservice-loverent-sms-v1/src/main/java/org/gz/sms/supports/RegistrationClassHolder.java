package org.gz.sms.supports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yangdx
 *
 */
public class RegistrationClassHolder {

	/**
	 * 保存所有的接口
	 */
	private static Map<String, List<Object>> registerClazzMap = new HashMap<String, List<Object>>();

	/**
	 * 并发锁
	 */
	private static final Object lock = new Object();

	/**
	 * 增加注册器
	 * @param clazz
	 * @param impl void 返回类型
	 * @throws 异常说明
	 */
	public static void putClassInstance(Class<?> clazz, Object impl) {
		String name = clazz.getName();

		List<Object> implementList = registerClazzMap.get(name);
		if (implementList == null) {
			synchronized (lock) {
				implementList = registerClazzMap.get(name);
				if (implementList == null) {
					implementList = new ArrayList<Object>();
					registerClazzMap.put(name, implementList);
				}
			}
		}

		implementList.add(impl);
	}

	/**
	 * 根据接口获取所有的实现类
	 * @param clazz
	 * @return List<T> 返回类型
	 * @throws 异常说明
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getImplementClazz(Class<T> interfaceClazz) {
		List<T> resultList = new ArrayList<T>();
		String name = interfaceClazz.getName();

		List<Object> implementList = registerClazzMap.get(name);
		if (implementList != null) {
			for (Object obj : implementList) {

				T imp = (T) obj;
				resultList.add(imp);
			}
		}

		return resultList;
	}
}
