/**
 * 
 */
package org.gz.warehouse.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年1月17日 下午4:12:46
 */
public class CustomAcknowledgingMessageListener implements AcknowledgingMessageListener<Integer, String> {

	@Override
	public void onMessage(ConsumerRecord<Integer, String> data, Acknowledgment acknowledgment) {
	}

}
