/**
 * 
 */
package org.gz.mq.converter.base;

import org.gz.mq.exception.ConvertException;

/**
 * 
 * @author hxj
 * @date 2017年11月9日下午3:52:36
 * @param <S>
 *            the source type
 * @param <T>
 *            the target type
 */
public interface BaseConverter<S, T> {

	/**
	 * 将S类型的源对象转换成T类型的目标对象
	 * 
	 * @param source
	 * @return
	 */
	public T convert(S source) throws ConvertException;
}
