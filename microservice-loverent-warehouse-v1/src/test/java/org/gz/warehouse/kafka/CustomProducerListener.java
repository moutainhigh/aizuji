/**
 * 
 */
package org.gz.warehouse.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

/**
 * @Title: 用于处理出站消息的监听器
 * @author hxj
 * @Description: 
 * @date 2018年1月17日 下午2:19:20
 */
public class CustomProducerListener implements ProducerListener<Integer, String> {

	/**
	 * 消息发送成功之后(即broker确认了消息之后)回调
	 */
	@Override
	public void onSuccess(String topic, Integer partition, Integer key, String value, RecordMetadata recordMetadata) {
		System.err.println("====>onSuccess:"+"topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset()+",serializedKeySize="+recordMetadata.serializedKeySize()+",serializedValueSize="+recordMetadata.serializedValueSize()+",timestamp="+recordMetadata.timestamp());
	}

	/**
	 * 消息发送失败后回调
	 */
	@Override
	public void onError(String topic, Integer partition, Integer key, String value, Exception exception) {
		System.err.println("====>onError："+",topic="+topic+",partition="+partition+",key="+key+",value="+value+",exception="+exception.getLocalizedMessage());
	}

	@Override
	public boolean isInterestedInSuccess() {
		System.err.println("====>isInterestedInSuccess...");
		return true;
	}

}
