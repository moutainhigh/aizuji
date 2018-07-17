/**
 * 
 */
package org.gz.warehouse.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @Title:生产者发送记录前的拦截器
 * @author hxj
 * @Description:
 * @date 2018年1月17日 上午10:45:21
 */
public class CustomProducerInterceptor implements ProducerInterceptor<Integer, String> {

	@Override
	public void configure(Map<String, ?> configs) {
		System.err.println("====>configure...");
		for(String key:configs.keySet()) {
			System.err.println(key+"="+configs.get(key));
		}
	}

	@Override
	public ProducerRecord<Integer, String> onSend(ProducerRecord<Integer, String> record) {
		System.err.println("====>onSend:"+"topic="+record.topic()+",partition="+record.partition()+",key="+record.key()+",value="+record.value()+",timestamp="+record.timestamp());
		return record;
	}

	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		metadata.timestamp();
		System.err.println("====>onAcknowledgement:"+"topic="+metadata.topic()+",partition="+metadata.partition()+",offset="+metadata.offset()+",serializedKeySize="+metadata.serializedKeySize()+",serializedValueSize="+metadata.serializedValueSize()+",timestamp="+metadata.timestamp());
		if(exception!=null) {
			System.err.println("====>error:"+exception.getLocalizedMessage());
		}
	}

	@Override
	public void close() {
		System.err.println("====>close...");
	}

}
