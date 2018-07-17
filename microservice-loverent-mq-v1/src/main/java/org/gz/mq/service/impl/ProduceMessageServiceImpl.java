/**
 * 
 */
package org.gz.mq.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.gz.mq.entity.resp.ResponseResult;
import org.gz.mq.exception.MessageException;
import org.gz.mq.service.ProduceMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 
 * @author hxj
 * @date 2017年11月9日下午3:42:04
 */
@Service
public class ProduceMessageServiceImpl implements ProduceMessageService {

	private static Logger log = LoggerFactory.getLogger(ProduceMessageServiceImpl.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public ResponseResult sendMessage(String topic, String data)throws MessageException{
		ResponseResult res = ResponseResult.OPT_SEND_FAILED;
		try {
			ListenableFuture<SendResult<String, String>> result = this.kafkaTemplate.send(topic, data);
			logSendResult(result);
			res=ResponseResult.OPT_SUCCESS;
		} catch (Exception e) {
			log.error("send message failed:{}", e.getLocalizedMessage());
		}
		return res;
	}

	/**
	 * @param result
	 */
	private void logSendResult(ListenableFuture<SendResult<String, String>> result)throws MessageException {
		if (result != null) {
			try {
				ProducerRecord<String, String> pr = result.get().getProducerRecord();
				log.info("send key:" + pr.key());
				log.info("send value:" + pr.value());
				RecordMetadata md = result.get().getRecordMetadata();
				log.info("target topic：" + md.topic());
				log.info("target partion:" + md.partition());
				log.info("offset:" + md.offset());
				log.info("timestamp:" + md.timestamp());
				log.info("serializedKeySize:" + md.serializedKeySize());
				log.info("serializedValueSize:" + md.serializedValueSize());
			} catch (Exception e) {
				throw new MessageException(e.getLocalizedMessage());
			}
		}
	}

}
