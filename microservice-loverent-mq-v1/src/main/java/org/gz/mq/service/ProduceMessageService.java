/**
 * 
 */
package org.gz.mq.service;

import org.gz.mq.entity.resp.ResponseResult;
import org.gz.mq.exception.MessageException;

/**
 * 
 * @author hxj
 * @date 2017年11月9日下午3:16:41
 */
public interface ProduceMessageService {

	public ResponseResult sendMessage(String topic, String data)throws MessageException;
}
