/**
 * 
 */
package org.gz.warehouse.converter;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月22日 上午10:10:08
 */
public interface TurnConverter<S, T> {
	/**
	 * 
	 * @Description: 将源对象转换成目标对象
	 * @param source 源对象
	 * @return T
	 */
	T transform(S source);

	/**
	 * 
	* @Description: 将目标对象转换成源对象
	* @param target
	* @return S
	 */
	S revert(T target);
}
